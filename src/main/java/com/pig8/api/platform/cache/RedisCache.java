/**
 * 
 */
package com.pig8.api.platform.cache;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author navy
 *
 */
public interface RedisCache {
	
	public boolean putHashMap(final String key, final Map<String, Object> dataMap);
	
	public boolean putHashMap(final String key, final Map<String, Object> dataMap, final long timeout, final TimeUnit timeUnit);
	
	public Map<String, Object> getHashMap(final String key);
	
	public boolean putObject(final String key, final Serializable obj);
	
	public boolean putObject(final String key, final Serializable obj, final long timeout, final TimeUnit timeUnit);
	
	public Object getObject(final String key);
	
	/**
	 * 设置缓存失效时间
	 * 
	 * @param key
	 * @param timeout
	 * @param unit
	 * @return
	 */
	public boolean expire(String key, long timeout, TimeUnit unit);
	
	public boolean delete(String key);

}
