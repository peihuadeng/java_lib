package com.dph.common.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dph.system.context.SpringContextHolder;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * redis客户端管理器
 * @author root
 *
 */
public class RedisClientManager {

	private final static Logger logger = LoggerFactory.getLogger(RedisClientManager.class);

	private static RedisClientManager instance;
	private ShardedJedisPool pool = null;

	/**
	 * 获取单例
	 * @return
	 */
	public synchronized static RedisClientManager getInstance() {
		if (instance == null) {
			instance = new RedisClientManager();
		}

		return instance;
	}

	/**
	 * 初始化：读取配置，初始化redis分片连接池
	 */
	private RedisClientManager() {
		pool = SpringContextHolder.getBean(ShardedJedisPool.class);
	}

	/**
	 * 获取redis资源
	 * @return
	 */
	public ShardedJedis getResource() {
		if (pool == null) {
			return null;
		}
		
		ShardedJedis jedis = null;
		try {
			jedis = pool.getResource();
		} catch (Exception e) {
			logger.error("fail to get jedis resource", e);
			return null;
		}

		return jedis;
	}

}
