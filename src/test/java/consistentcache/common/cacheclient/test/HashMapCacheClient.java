package consistentcache.common.cacheclient.test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import consistentcache.common.module.CacheClient;

/**
 * 类HashMapCacheClient.java的实现描述：CacheClient的HashMap实现 
 * @author tyrone Aug 22, 2013 1:57:16 PM
 */
public class HashMapCacheClient implements CacheClient {
	
	private Map<String, Object> cache = new ConcurrentHashMap<String, Object>();

	@Override
	public boolean contains(String key) {
		return cache.containsKey(key);
	}

	@Override
	public Object get(String key) {
		return cache.get(key);
	}

	@Override
	public Object set(String key, Object value) {
		return cache.put(key, value);
	}

}
