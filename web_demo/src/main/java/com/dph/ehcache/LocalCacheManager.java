package com.dph.ehcache;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.store.chm.ConcurrentHashMap;

/**
 * 本地缓存管理器
 * 
 * @author root
 *
 */
@Service
public class LocalCacheManager {

	@Autowired
	private CacheManager cacheManager;

	private Map<String, LocalCache<?>> cacheMap = new ConcurrentHashMap<String, LocalCache<?>>();
	
	static {
		System.setProperty(net.sf.ehcache.CacheManager.ENABLE_SHUTDOWN_HOOK_PROPERTY,"true");
	}

	/**
	 * 创建缓存
	 * 
	 * @param name：缓存名称
	 * @return：缓存
	 */
	private <T> LocalCache<T> createCache(String name, Class<T> clazz) {
		Cache cache = new Cache(new CacheConfiguration(name, 1024 * 100)
				.eternal(false)
				.timeToIdleSeconds(1800)
				.timeToLiveSeconds(1800)
				.overflowToDisk(false)
				.diskPersistent(false));

		cacheManager.addCache(cache);
		LocalCache<T> localCache = new LocalCache<T>(cache, clazz);
		cacheMap.put(name, localCache);

		return localCache;
	}
	
	/**
	 * 创建持久化缓存
	 * 
	 * @param name：缓存名称
	 * @return：缓存
	 */
	private <T> LocalCache<T> createPersistentCache(String name, Class<T> clazz) {
		Cache cache = new Cache(new CacheConfiguration(name, 1024 * 100)
				.eternal(false)
				.timeToIdleSeconds(1800)
				.timeToLiveSeconds(1800)
				.overflowToDisk(true)
				.maxElementsOnDisk(1024 * 1024)
				.diskPersistent(true));
		
		cacheManager.addCache(cache);
		LocalCache<T> localCache = new LocalCache<T>(cache, clazz);
		cacheMap.put(name, localCache);

		return localCache;
	}

	/**
	 * 依据名称获得缓存或持久化缓存
	 * @param name:缓存名称
	 * @param clazz:缓存操作的类类型
	 * @param createIfNull:true时，没有缓存则创建，否则没有缓存时不创建缓存并返回空
	 * @param persistent:是否支持持久化
	 * @return :缓存对象
	 */
	@SuppressWarnings("unchecked")
	private <T> LocalCache<T> getCache(String name, Class<T> clazz, boolean createIfNull, boolean persistent) {
		LocalCache<T> localCache = (LocalCache<T>) cacheMap.get(name);
		if (localCache == null) {
			Cache cache = cacheManager.getCache(name);
			if (cache != null) {
				localCache = new LocalCache<T>(cache, clazz);
				cacheMap.put(name, localCache);
			}
		}

		if (localCache == null && createIfNull == true) {
			if (persistent) {
				localCache = createPersistentCache(name, clazz);
			} else {
				localCache = createCache(name, clazz);				
			}
		}

		return localCache;
	}
	
	/**
	 * 依据名称构造并获得缓存
	 * @param name:缓存名称
	 * @param clazz:缓存操作的类类型
	 * @return:缓存对象
	 */
	public <T> LocalCache<T> getAndCreateCache(String name, Class<T> clazz) {
		return getCache(name, clazz, true, false);
	}

	/**
	 * 依据名称获得缓存
	 * @param name：缓存名称
	 * @param clazz:缓存操作的类类型
	 * @return：缓存，有可能返回空
	 */
	public <T> LocalCache<T> getCache(String name, Class<T> clazz) {
		return getCache(name, clazz, false, false);
	}
	
	/**
	 * 依据类类型构造并获得缓存
	 * @param clazz:缓存操作的类类型，缓存名称为类全名
	 * @return:缓存对象
	 */
	public <T> LocalCache<T> getAndCreateCache(Class<T> clazz) {
		return getCache(clazz.getName(), clazz, true, false);
	}
	
	
	/**
	 * 依据类类型获得缓存
	 * @param clazz:缓存操作的类类型,缓存名称为类全名
	 * @return：缓存，有可能返回空
	 */
	public <T> LocalCache<T> getCache(Class<T> clazz) {
		return getCache(clazz.getName(), clazz, false, false);
	}
	
	/**
	 * 依据名称构造并获得持久化缓存
	 * @param name:缓存名称
	 * @param clazz:缓存操作的类类型
	 * @return:持久化缓存对象
	 */
	public <T> LocalCache<T> getAndCreatePersistentCache(String name, Class<T> clazz) {
		return getCache(name, clazz, true, true);
	}

	/**
	 * 依据名称获得持久化缓存
	 * @param name：缓存名称
	 * @param clazz:缓存操作的类类型
	 * @return：持久化缓存，有可能返回空
	 */
	public <T> LocalCache<T> getPersistentCache(String name, Class<T> clazz) {
		return getCache(name, clazz, false, true);
	}
	
	/**
	 * 依据类类型构造并获得持久化缓存
	 * @param clazz:缓存操作的类类型，缓存名称为类全名
	 * @return:持久化缓存对象
	 */
	public <T> LocalCache<T> getAndCreatePersistentCache(Class<T> clazz) {
		return getCache(clazz.getName(), clazz, true, true);
	}
	
	
	/**
	 * 依据类类型获得持久化缓存
	 * @param clazz:缓存操作的类类型,缓存名称为类全名
	 * @return：持久化缓存，有可能返回空
	 */
	public <T> LocalCache<T> getPersistentCache(Class<T> clazz) {
		return getCache(clazz.getName(), clazz, false, true);
	}
}
