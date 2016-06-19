package com.dph.common.cache.local;

import java.io.Serializable;

import com.dph.common.json.JsonUtil;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

/**
 * 本地缓存
 * 
 * @author root
 *
 */
public class LocalCache<T> {
	/**
	 * 对ehcahce缓存引用，必须非空
	 */
	private Cache cache;
	private Class<T> entityClass;

	public LocalCache(Cache cache, Class<T> clazz) {
		if (cache == null) {
			throw new RuntimeException("cache object must not be null.");
		}
		this.cache = cache;
		this.entityClass = clazz;
	}

	private void check() {
		if (cache == null) {
			throw new RuntimeException("cache object is null.");
		}
	}

	/**
	 * 增加（key，元素）对
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean putValue(Serializable key, T value) {
		check();
		String json = JsonUtil.bean2Str(value);
		Element element = new Element(key, json);
		cache.put(element);

		return true;
	}

	/**
	 * 依据key移除元素
	 * 
	 * @param key
	 * @return
	 */
	public boolean remove(Serializable key) {
		check();
		return cache.remove(key);
	}

	/**
	 * 依据key获取元素
	 * 
	 * @param key
	 * @param clazz
	 * @return
	 */
	public T getValue(Serializable key) {
		check();
		Element element = cache.get(key);
		if (element == null) {
			return null;
		}

		String json = (String) element.getValue();
		T t = (T) JsonUtil.str2bean(json, entityClass);

		return t;
	}
	
	/**
	 * 是否包含目标key
	 * 
	 * @param key
	 * @return
	 */
	public boolean contains(Serializable key) {
		check();
		Element element = cache.get(key);
		if (element != null) {
			return true;
		}

		return false;
	}

}
