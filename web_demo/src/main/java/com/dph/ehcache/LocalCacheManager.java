package com.dph.ehcache;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;
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

	/**
	 * 创建缓存
	 * 
	 * @param name：缓存名称
	 * @return：缓存
	 */
	private <T> LocalCache<T> createCache(String name, Class<T> clazz) {
		Cache cache = new Cache(new CacheConfiguration(name, 1024).eternal(false).timeToIdleSeconds(1800).timeToLiveSeconds(1800).overflowToDisk(false).memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LRU));

		cacheManager.addCache(cache);
		LocalCache<T> localCache = new LocalCache<T>(cache, clazz);
		cacheMap.put(name, localCache);

		return localCache;
	}

	/**
	 * 依据名称获得缓存
	 * 
	 * @param name：缓存名称
	 * @param createIfNull：true时，没有缓存则创建，否则没有缓存时不创建缓存并返回空
	 * @return ： 缓存
	 */
	@SuppressWarnings("unchecked")
	public <T> LocalCache<T> getCache(String name, Class<T> clazz, boolean createIfNull) {
		LocalCache<T> localCache = (LocalCache<T>) cacheMap.get(name);
		if (localCache == null) {
			Cache cache = cacheManager.getCache(name);
			if (cache != null) {
				localCache = new LocalCache<T>(cache, clazz);
				cacheMap.put(name, localCache);
			}
		}

		if (localCache == null && createIfNull == true) {
			localCache = createCache(name, clazz);
		}

		return localCache;
	}

	/**
	 * 依据名称获得缓存
	 * 
	 * @param name：缓存名称
	 * @return：缓存，有可能返回空
	 */
	public <T> LocalCache<T> getCache(String name, Class<T> clazz) {
		return getCache(name, clazz, false);
	}
}
