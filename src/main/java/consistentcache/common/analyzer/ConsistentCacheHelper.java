package consistentcache.common.analyzer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import consistentcache.common.annotation.CachedKey;
import consistentcache.common.module.CacheClient;
import consistentcache.common.process.KeyField;
import consistentcache.common.process.ValueProcessor;

/**
 * 类ConsistentCacheHelper.java的实现描述：一致性缓存调用类
 * 
 * @author tyrone Aug 21, 2013 1:04:36 PM
 */
public class ConsistentCacheHelper {

	private ConcurrentHashMap<Class<?>, List<KeyField>> cachedKeyFields = new ConcurrentHashMap<Class<?>, List<KeyField>>();
	private ConcurrentHashMap<Class<?>, Object> ignoredFindKeyFieldClasses = new ConcurrentHashMap<Class<?>, Object>();
	private static final Object NULL_OBJECT = new Object();

	public <T> Object getCachedValue(T keyObject, ValueProcessor<T> processor) {
		if (keyObject == null || processor == null) {
			throw new RuntimeException("KeyObject and ValueProcessor mustn't be null");
		}

		LinkedHashMap<String, Object> keyValues = extractValueOfKeyFields(keyObject);
		String key = processor.getKeyFromObject(keyObject, keyValues);
		CacheClient cacheClient = processor.getCacheClient();
		
		if (!cacheClient.contains(key)) {
			return null;
		}
		
		Object value = cacheClient.get(key);
		// filter缓存信息
		value = processor.filterValue(keyObject, value);
		return value;
	}
	
	public <T> boolean contains(T keyObject, ValueProcessor<T> processor) {
		if (keyObject == null || processor == null) {
			throw new RuntimeException("KeyObject and ValueProcessor mustn't be null");
		}
		
		LinkedHashMap<String, Object> keyValues = extractValueOfKeyFields(keyObject);
		String key = processor.getKeyFromObject(keyObject, keyValues);
		CacheClient cacheClient = processor.getCacheClient();
		
		return cacheClient.contains(key);
	}
	
	public <T> Object setCachedValue(T keyObject, ValueProcessor<T> processor, boolean isCover) {
		if (keyObject == null || processor == null) {
			throw new RuntimeException("KeyObject and ValueProcessor mustn't be null");
		}
		
		LinkedHashMap<String, Object> keyValues = extractValueOfKeyFields(keyObject);
		String key = processor.getKeyFromObject(keyObject, keyValues);
		CacheClient cacheClient = processor.getCacheClient();
		
		// 不进行覆盖，并且该Key的值已经存在，得到对应的值返回
		if (!isCover && cacheClient.contains(key)) {
			Object value = cacheClient.get(key);
			return processor.filterValue(keyObject, value);
		}
		
		Object value = processor.processNewValue(keyObject, keyValues);
		cacheClient.set(key, value);
		
		return processor.filterValue(keyObject, value);
	}
	
	private LinkedHashMap<String, Object> extractValueOfKeyFields(Object keyObject) {
		LinkedHashMap<String, Object> keyValues = null;
		if (!ignoredFindKeyFieldClasses.containsKey(keyObject.getClass())) {
			// keyObject拥有CachedKey的字段
			List<KeyField> keyFields = getKeyFieldsOfKeyObject(keyObject);
			keyValues = getKeyValuesFromKeyFields(keyObject, keyFields);
		}
		
		return keyValues;
	}
	
	private List<KeyField> getKeyFieldsOfKeyObject(Object keyObject) {
		Class<?> clazz = keyObject.getClass();
		List<KeyField> fields = cachedKeyFields.get(clazz);
		if (fields == null) {
			// 进行对keyObject的解析
			fields = new ArrayList<KeyField>();
			Field[] declaredFields = clazz.getDeclaredFields();

			for (Field field : declaredFields) {
				CachedKey cachedKeyAnnotation = field
						.getAnnotation(CachedKey.class);

				if (cachedKeyAnnotation != null) {
					// 构造一个KeyField对象
					KeyField keyField = constructKeyField(cachedKeyAnnotation,
							field);
					fields.add(keyField);
				}
			}

			if (CollectionUtils.isEmpty(fields)) {
				// 加入没有忽略KeyField中
				ignoredFindKeyFieldClasses.put(clazz, NULL_OBJECT);
				return null;
			}

			// 对KeyFields进行排序
			Collections.sort(fields);
			cachedKeyFields.put(clazz, fields);
		}

		return fields;
	}

	/**
	 * 根据Field和CachedKey的信息构造KeyField
	 * 
	 * @param cachedKeyAnnotation
	 * @param field
	 * @return
	 */
	private KeyField constructKeyField(CachedKey cachedKeyAnnotation,
			Field field) {
		int order = cachedKeyAnnotation.order();
		String keyName = cachedKeyAnnotation.keyName();

		if (StringUtils.isBlank(keyName)) {
			keyName = field.getName();
		}

		KeyField keyField = new KeyField();
		keyField.setName(keyName);
		keyField.setOrder(order);
		keyField.setField(field);
		return keyField;
	}

	/**
	 * 从keyObject对象中获取Key属性的值，并以fieldName->value返回
	 * 
	 * @param keyObject
	 * @param keyFields
	 * @return
	 */
	private <T> LinkedHashMap<String, Object> getKeyValuesFromKeyFields(
			T keyObject, List<KeyField> keyFields) {
		if (keyFields == null) {
			return null;
		}

		LinkedHashMap<String, Object> cachedKeys = new LinkedHashMap<String, Object>();
		for (KeyField keyField : keyFields) {
			Object fieldValue = null;
			try {
				Field field = keyField.getField();
				field.setAccessible(true);
				fieldValue = field.get(keyObject);
			} catch (Exception e) {
				throw new RuntimeException("Get Filed's value error", e);
			}

			String fieldName = keyField.getName();
			cachedKeys.put(fieldName, fieldValue);
		}

		return cachedKeys;
	}
}
