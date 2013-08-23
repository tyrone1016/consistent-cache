package consistentcache.common.process;

import java.util.LinkedHashMap;

import consistentcache.common.module.CacheClient;

/**
 * 类ValueProcessor.java的实现描述：缓存计算器接口
 * @author tyrone Aug 21, 2013 1:17:09 PM
 */
public interface ValueProcessor<T> {
	/**
	 * 获取缓存对象的Key字符串
	 * 
	 * @param keyObject
	 * @param keyFields 已根据order字段从大到小排序
	 * @return
	 */
	public String getKeyFromObject(T keyObject, LinkedHashMap<String, Object> keyValues); 
	
	/**
	 * 提供给系统自动生成Key时，多个字段之间的分隔符
	 * 
	 * @return
	 */
	public String getKeySeparator();
	
	/**
	 * 获取缓存层访问客户端
	 * 
	 * @return
	 */
	public CacheClient getCacheClient();
	
	/**
	 * 缓存未命中的情况下，从其他数据源获取数据
	 * 
	 * @param keyObject
	 * @param keyValues keyObject对象中 {@link consistentcache.common.annotation.CachedKey} 数值
	 * @return
	 */
	public Object processNewValue(T keyObject, LinkedHashMap<String, Object> keyValues);
	
	/**
	 * 根据keyObject过滤object中过多的信息, value可能为null
	 * 
	 * @param keyObject
	 * @param value
	 * @return
	 */
	public Object filterValue(T keyObject, Object value);
}
