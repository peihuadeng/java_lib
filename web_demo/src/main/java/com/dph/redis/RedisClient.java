package com.dph.redis;

import java.util.List;
import java.util.Map;

import com.dph.common.utils.JsonUtils;

import redis.clients.jedis.ShardedJedis;

/**
 * redis客户端
 * @author root
 *
 */
public class RedisClient {

	/**
	 * redis客户端管理器
	 */
	private static RedisClientManager manager = RedisClientManager.getInstance();

	/**
	 * 设置key：value键值对
	 * @param key
	 * @param value
	 */
	public static void set(String key, String value) {
		ShardedJedis client = manager.getResource();
		if (client == null) {
			return;
		}

		try {
			client.set(key, value);
		} finally {
			client.close();
		}
	}

	/**
	 * 依据key获取value
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		ShardedJedis client = manager.getResource();
		if (client == null) {
			return null;
		}

		try {
			String value = client.get(key);
			return value;
		} finally {
			client.close();
		}
	}

	/**
	 * 移除key:value
	 * @param key
	 * @return
	 */
	public static Long del(String key) {
		ShardedJedis client = manager.getResource();
		if (client == null) {
			return null;
		}

		try {
			Long result = client.del(key);
			return result;
		} finally {
			client.close();
		}
	}
	
	/**
	 * 判断是否存在key
	 * @param key
	 * @return
	 */
	public static Boolean exists(String key) {
		ShardedJedis client = manager.getResource();
		if (client == null) {
			return null;
		}

		try {
			Boolean result = client.exists(key);
			return result;
		} finally {
			client.close();
		}
	}

	/**
	 * 把对象转换成json，然后设置key：json键值对
	 * @param key
	 * @param obj
	 */
	public static void setObjectToJson(String key, Object obj) {
		ShardedJedis client = manager.getResource();
		if (client == null) {
			return;
		}

		try {
			String json = JsonUtils.bean2Str(obj);
			client.set(key, json);
		} finally {
			client.close();
		}
	}

	/**
	 * 依据key，从redis中获取json并转换成对应对象
	 * @param key
	 * @param clazz
	 * @return
	 */
	public static <T> T getObjectFromJson(String key, Class<T> clazz) {
		ShardedJedis client = manager.getResource();
		if (client == null) {
			return null;
		}

		try {
			String json = client.get(key);
			if (json == null) {
				return null;
			}
			
			T t = JsonUtils.str2bean(json, clazz);
			return t;
		} finally {
			client.close();
		}
	}

	/**
	 * 依据key，从redis中获取json并转换成对应list
	 * @param key
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> getListFromJson(String key, Class<T> clazz) {
		ShardedJedis client = manager.getResource();
		if (client == null) {
			return null;
		}

		try {
			String json = client.get(key);
			if (json == null) {
				return null;
			}
			
			List<T> list = JsonUtils.str2list(json, clazz);
			return list;
		} finally {
			client.close();
		}
	}

	/**
	 * 依据key，从redis中获取json并转换成对应map
	 * @param key
	 * @param keyClass
	 * @param valueClass
	 * @return
	 */
	public static <KT, VT> Map<KT, VT> getMapFromJson(String key, Class<KT> keyClass, Class<VT> valueClass) {
		ShardedJedis client = manager.getResource();
		if (client == null) {
			return null;
		}

		try {
			String json = client.get(key);
			if (json == null) {
				return null;
			}
			
			Map<KT, VT> map = JsonUtils.str2map(json, keyClass, valueClass);
			return map;
		} finally {
			client.close();
		}
	}

}
