package com.dph.common.cache.local;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.dph.common.cache.CacheDao;
import com.dph.common.cache.annotation.LinkTo;
import com.dph.common.entity.BaseEntity;
import com.dph.common.persistence.BaseDao;
import com.dph.common.utils.StringUtils;
import com.dph.ehcache.LocalCache;
import com.dph.ehcache.LocalCacheManager;
import com.dph.system.context.SpringContextHolder;

public abstract class LocalCacheDao<T extends BaseEntity<T>, Mapper extends BaseDao<T>> implements CacheDao<T> {

	private final static Logger logger = Logger.getLogger(LocalCacheDao.class);

	@Autowired
	protected Mapper mapper;

	@Value("${cache.level:3}")
	protected int level;

	@Autowired
	private LocalCacheManager cacheManager;
	protected LocalCache<T> localCache;

	private Map<String, Method> getLinkFieldMethodMap = new HashMap<String, Method>();
	private Map<String, Class<? extends CacheDao<?>>> daoClassMap = new HashMap<String, Class<? extends CacheDao<?>>>();
	private Map<String, Field> fieldMap = new HashMap<String, Field>();

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		boolean success = doInit();
		if (!success) {
			Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
			localCache = cacheManager.getCache("Object:" + entityClass.getName(), entityClass, true);

			Class<?> clazz = entityClass;
			while (clazz != null) {
				for (Field field : clazz.getDeclaredFields()) {
					LinkTo linkTo = field.getDeclaredAnnotation(LinkTo.class);
					if (linkTo != null) {
						String key = clazz.getName() + "." + field.getName();
						String linkFieldName = linkTo.field();
						Class<? extends CacheDao<?>> daoClazz = linkTo.dao();
						// 1.getLinkField() method
						if (StringUtils.isBlank(linkFieldName)) {
							// link field can not be none
							logger.debug(String.format("class \"%s\" field \"%s\" @LinkTo field is empty", clazz.getName(), field.getName()));
							continue;
						}

						String linkFieldGetMethodName = "get" + StringUtils.capitalize(linkFieldName);
						Method linkFieldGetMethod = null;

						try {
							linkFieldGetMethod = clazz.getMethod(linkFieldGetMethodName);
						} catch (NoSuchMethodException e) {
							logger.debug(String.format("class \"%s\" has no such method named \"%s\"", clazz.getName(), linkFieldGetMethodName), e);
						} catch (SecurityException e) {
							logger.debug(String.format("can not access class \"%s\" method named \"%s\"", clazz.getName(), linkFieldGetMethodName), e);
						}

						Class<?> linkFieldGetMethodReturnType = linkFieldGetMethod.getReturnType();
						if (!String.class.isAssignableFrom(linkFieldGetMethodReturnType)) {
							logger.debug(String.format("class \"%s\" method \"%s\" return type is not String.class", clazz.getName(), linkFieldGetMethodName));
							continue;
						}

						// 2.check whether the dao class has method selectByPrimaryKey(String, int)
						try {
							daoClazz.getMethod("selectByPrimaryKey", String.class, int.class);
						} catch (NoSuchMethodException | SecurityException e) {
							logger.debug(String.format("dao class \"%s\" has no such method named \"selectByPrimaryKey(String, int)\"", daoClazz.getName()), e);
							continue;
						}

						getLinkFieldMethodMap.put(key, linkFieldGetMethod);
						daoClassMap.put(key, daoClazz);
						fieldMap.put(key, field);
					}
				}

				clazz = clazz.getSuperclass();
			}
		}
	}

	protected boolean doInit() {
		return false;
	}

	// 动态加载数据类属性
	protected void loadProperties(T t, int level) {
		if (t == null) {
			return;
		}

		level--;
		if (level <= 0) {
			logger.debug(String.format("stop to load properties, class name \"%s\"", t.getClass()));
			return;
		}

		for (String key : fieldMap.keySet()) {
			Field field = fieldMap.get(key);
			Method getLinkFieldMethod = getLinkFieldMethodMap.get(key);
			Class<? extends CacheDao<?>> daoClass = daoClassMap.get(key);
			// 1.get link field value through getLinkField() method
			String linkFieldValue = null;
			try {
				linkFieldValue = (String) getLinkFieldMethod.invoke(t);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				logger.warn(String.format("fail to invoke class \"%s\" method \"%s\"", getLinkFieldMethod.getDeclaringClass(), getLinkFieldMethod.getName()), e);
				continue;
			}

			if (StringUtils.isBlank(linkFieldValue)) {
				logger.debug(String.format("the instance of class \"%s\" link field \"%s\" value is empty", getLinkFieldMethod.getDeclaringClass(), getLinkFieldMethod.getName()));
				continue;
			}
			// 2.get field value through CacheDao object and link field value
			CacheDao<?> cacheDao = SpringContextHolder.getBean(daoClass);
			Object fieldValue = cacheDao.selectByPrimaryKey(linkFieldValue, level);
			if (fieldValue == null) {
				continue;
			}

			// 3.set field value
			Class<?> fieldType = field.getType();
			if (!fieldType.isAssignableFrom(fieldValue.getClass())) {
				logger.debug(String.format("cache dao \"%s\" method \"selectByPrimaryKey(String, int)\" return type \"%s\" is not match with class \"%s\" field \"%s\" type \"%s\"", daoClass.getName(), fieldValue.getClass().getName(), field.getDeclaringClass().getName(), field.getName(), fieldType.getName()));
				continue;
			}

			field.setAccessible(true);
			try {
				field.set(t, fieldValue);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				logger.warn(String.format("fail to set class \"%s\" field \"%s\" value", field.getDeclaringClass().getName(), field.getName()), e);
				continue;
			}
		}
	}

	@Override
	public int insert(T t) {
		return mapper.insert(t);
	}

	@Override
	public int insertSelective(T t) {
		return mapper.insertSelective(t);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		int result = mapper.deleteByPrimaryKey(id);
		// remove cache
		localCache.remove(id);
		return result;
	}

	@Override
	public int updateByPrimaryKeySelective(T t) {
		int result = mapper.updateByPrimaryKeySelective(t);
		// remove cache
		localCache.remove(t.getId());
		return result;
	}

	@Override
	public int updateByPrimaryKey(T t) {
		int result = mapper.updateByPrimaryKey(t);
		// remove cache
		localCache.remove(t.getId());
		return result;
	}

	@Override
	public T selectByPrimaryKey(String id) {
		T t = selectByPrimaryKey(id, level);

		return t;
	}

	@Override
	public T selectByPrimaryKey(String id, int level) {
		if (StringUtils.isBlank(id)) {
			return null;
		}
		// get from cache, if null, get from db and put into cache
		T t = null;
		if (localCache.contains(id)) {
			t = localCache.getValue(id);
		} else {// get from db and put into cache
			t = mapper.selectSimpleByPrimaryKey(id);
			localCache.putValue(id, t);
		}
		// join cache or db
		loadProperties(t, level);

		return t;
	}

	@Override
	public T selectSimpleByPrimaryKey(String id) {
		if (StringUtils.isBlank(id)) {
			return null;
		}
		// get from cache, if null, get from db and put into cache
		T t = null;
		if (localCache.contains(id)) {
			t = localCache.getValue(id);
		} else {// get from db and put into cache
			t = mapper.selectSimpleByPrimaryKey(id);
			localCache.putValue(id, t);
		}

		return t;
	}

	@Override
	public List<T> select(T t) {
		List<T> list = mapper.selectSimple(t);
		for (T tt : list) {
			loadProperties(tt, level);
		}

		return list;
	}

	@Override
	public List<T> selectSimple(T t) {
		List<T> list = mapper.selectSimple(t);

		return list;
	}

}
