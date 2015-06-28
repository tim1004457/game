/**
 * 
 */
package com.pig8.api.platform.cache.impl;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.pig8.api.platform.cache.RedisCache;
import com.pig8.api.platform.global.Constants;

/**
 * @author navy
 *
 */
@Repository
public class RedisCacheImpl implements RedisCache {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private RedisTemplate<String, Serializable> redisTemplate;
	
	public boolean putHashMap(final String key, final Map<String, Object> dataMap) {
		return putHashMap(key, dataMap, Constants.DEFAULT_CACHE_TIMEOUT, Constants.DEFAULT_CACHE_TIME_UNIT);
	}
	
	public boolean putHashMap(final String key, final Map<String, Object> dataMap, final long timeout, final TimeUnit timeUnit) {
		try {
			return (Boolean) redisTemplate
					.execute(new RedisCallback<Boolean>() {
						public Boolean doInRedis(RedisConnection connection)
								throws DataAccessException {
							BoundHashOperations<String, String, Object> boundHashOps = redisTemplate.boundHashOps(key);
							boundHashOps.putAll(dataMap);
							boundHashOps.expire(timeout, timeUnit);
							return true;
						}
					});
		} catch (Exception e) {
			logger.error("加入缓存失败,key=" + key, e);
			return false;
		}
	}
	
	public Map<String, Object> getHashMap(final String key) {
		try {
			return (Map<String, Object>) redisTemplate
					.execute(new RedisCallback<Map<String, Object>>() {
						public Map<String, Object> doInRedis(RedisConnection connection)
								throws DataAccessException {
							BoundHashOperations<String, String, Object> boundHashOps = redisTemplate.boundHashOps(key);
							return boundHashOps.entries();
						}
					});
		} catch (Exception e) {
			logger.error("从缓存获取数据失败,key=" + key, e);
			return null;
		}
	}
	
	public boolean putObject(final String key, final Serializable obj) {
		return putObject(key, obj, Constants.DEFAULT_CACHE_TIMEOUT, Constants.DEFAULT_CACHE_TIME_UNIT);
	}
	
	public boolean putObject(final String key, final Serializable obj, final long timeout, final TimeUnit timeUnit) {
		try {
			return (Boolean) redisTemplate
					.execute(new RedisCallback<Boolean>() {
						public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
							BoundValueOperations<String, Serializable> boundValueOps = redisTemplate.boundValueOps(key);
							boundValueOps.set(obj, timeout, timeUnit);
							return true;
						}
					});
		} catch (Exception e) {
			logger.error("加入缓存失败,key=" + key, e);
			return false;
		}
	}
	
	public Object getObject(final String key) {
		try {
			return (Object) redisTemplate
					.execute(new RedisCallback<Object>() {
						public Object doInRedis(RedisConnection connection) throws DataAccessException {
							BoundValueOperations<String, Serializable> boundValueOps = redisTemplate.boundValueOps(key);
							return boundValueOps.get();
						}
					});
		} catch (Exception e) {
			logger.error("从缓存获取对象失败,key=" + key, e);
			return null;
		}
	}

	public boolean expire(String key, long timeout, TimeUnit unit) {
		return redisTemplate.expire(key, timeout, unit);
	}

	public boolean delete(String key) {
		redisTemplate.delete(key);
		return true;
	}

}
