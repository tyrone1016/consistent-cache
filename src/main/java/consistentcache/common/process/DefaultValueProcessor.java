package consistentcache.common.process;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 类DefaultValueProcessor.java的实现描述：ValueProcessor抽象类，提供默认的getKeyFromObject方法
 * 
 * @author tyrone Aug 21, 2013 1:38:11 PM
 */
public abstract class DefaultValueProcessor<T> implements ValueProcessor<T> {

	@Override
	public String getKeyFromObject(T keyObject, LinkedHashMap<String, Object> keyValues) {
		if (keyObject == null || MapUtils.isEmpty(keyValues)) {
			throw new RuntimeException("The key information is empty");
		}
		
		List<String> keyStringList = new ArrayList<String>(keyValues.size());
		for (Map.Entry<String, Object> entry : keyValues.entrySet()) {
			Object value = entry.getValue();
			
			if (value == null) {
				keyStringList.add("null");
				continue;
			}
			
			String keyString = null;
			// TODO 将对象转换成String，转化方法有待改进
			if (suitableForToString(value)) {
				keyString = value.toString();
			} else {
				keyString = ToStringBuilder.reflectionToString(value, ToStringStyle.SHORT_PREFIX_STYLE);
			}
			
			keyStringList.add(keyString);
		}
		
		return StringUtils.join(keyStringList, getKeySeparator());
	}
	
	/**
	 * 判断是否适宜直接调用toString()方法
	 * 
	 * @param value
	 * @return
	 */
	private boolean suitableForToString(Object value) {
		if (value instanceof String || value instanceof Number || value instanceof Date ||
				value instanceof Collection || value instanceof Map) {
			return true;
		}
		
		return false;
	}
}
