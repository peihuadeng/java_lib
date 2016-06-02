package com.dph.common.cache.local;

import java.io.Serializable;

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

	public LocalCache(Cache cache) {
		if (cache == null) {
			throw new RuntimeException("cache object must not be null.");
		}
		this.cache = cache;
	}

	private void check() {
		if (cache == null) {
			throw new RuntimeException("cache object must not be null.");
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
		Element element = new Element(key, value);
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
	@SuppressWarnings("unchecked")
	public T getValue(Serializable key) {
		check();
		Element element = cache.get(key);
		if (element == null) {
			return null;
		}

		T value = (T) element.getValue();

		return value;
	}

}
