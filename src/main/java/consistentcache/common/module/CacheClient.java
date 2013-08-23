package consistentcache.common.module;

/**
 * 类CacheClient.java的实现描述：访问缓存层Client 
 * @author tyrone Aug 22, 2013 10:07:06 AM
 */
public interface CacheClient {
	/**
	 * 判断缓存是否命中
	 * 
	 * @param key
	 * @return
	 */
	public boolean contains(String key);
	
	/**
	 * 从缓存层获取对象
	 * 
	 * @param key
	 * @return
	 */
	public Object get(String key);
	
	/**
	 * 往缓存层放值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Object set(String key, Object value);
}
