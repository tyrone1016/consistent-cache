package consistentcache.common.analyer.test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import consistentcache.common.analyzer.ConsistentCacheHelper;
import consistentcache.common.annotation.CachedKey;
import consistentcache.common.keyobject.test.CustomerInfoKey;
import consistentcache.common.keyobject.test.NoCachedKeyObject;
import consistentcache.common.keyobject.test.TestNameKey;
import consistentcache.common.keyobject.test.ToBeIgnoredKeyObject;
import consistentcache.common.process.KeyField;

/**
 * 类ConsistentCacheHelperTest.java的实现描述：ConsistentCacheHelper单元测试类
 * @author tyrone Aug 22, 2013 4:30:30 PM
 */
public class ConsistentCacheHelperTest {
	
	private ConsistentCacheHelper consistentCacheHelper = new ConsistentCacheHelper();
	
	private CustomerInfoKey unifyKeyObject ;
	
	@Before
	public void initUnifyKeyObject() {
		unifyKeyObject = new CustomerInfoKey();
		unifyKeyObject.setName("rdtest");
		unifyKeyObject.setSiteId("esite");
		unifyKeyObject.setPageName("index");
		unifyKeyObject.setProductIds(new Long[]{1l, 2l, 3l});
		unifyKeyObject.setCountNum(5);
	}
	
	private CustomerInfoKey getUnifyKeyObject() {
		return unifyKeyObject;
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void test_getKeyFieldsOfKeyObject() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		CustomerInfoKey keyObject = new CustomerInfoKey();
		List<KeyField> result = (List<KeyField>) invoke(consistentCacheHelper, "getKeyFieldsOfKeyObject", keyObject);
		Assert.assertEquals(3, result.size());
		KeyField nameField = result.get(0);
		KeyField siteField = result.get(1);
		KeyField pageNameField = result.get(2);
		Assert.assertEquals("name", nameField.getName());
		Assert.assertEquals("siteId", siteField.getName());
		Assert.assertEquals("pageName", pageNameField.getName());
		
		CustomerInfoKey anotherKeyObject = new CustomerInfoKey();
		List<KeyField> repeatResult = (List<KeyField>) invoke(consistentCacheHelper, "getKeyFieldsOfKeyObject", anotherKeyObject);
		Assert.assertTrue(result == repeatResult);
		
		// 测试没有任何@CachedKey的情况
		NoCachedKeyObject noCachedKeyObject = new NoCachedKeyObject();
		List<KeyField> noCachedKeyResult = (List<KeyField>) invoke(consistentCacheHelper, "getKeyFieldsOfKeyObject", noCachedKeyObject);
		Assert.assertNull(noCachedKeyResult);
		Field ignoredClassesField = consistentCacheHelper.getClass().getDeclaredField("ignoredFindKeyFieldClasses");
		ignoredClassesField.setAccessible(true);
		Map<Class<?>, Object> ignoredClasses = (Map<Class<?>, Object>) ignoredClassesField.get(consistentCacheHelper);
		Assert.assertTrue(ignoredClasses.containsKey(NoCachedKeyObject.class));
	}
	
	@Test
	public void test_constructKeyField() throws SecurityException, NoSuchFieldException {
		Field nameField = TestNameKey.class.getDeclaredField("name");
		CachedKey annotationOfNameField = nameField.getAnnotation(CachedKey.class);
		KeyField nameKeyField = (KeyField) invoke(consistentCacheHelper, "constructKeyField", annotationOfNameField, nameField);
		Assert.assertEquals("remarkName", nameKeyField.getName());
		Assert.assertEquals(2, nameKeyField.getOrder());
		Assert.assertTrue(nameField == nameKeyField.getField());
		
		Field descriptionField = TestNameKey.class.getDeclaredField("description");
		CachedKey annotationOfDescriptionField = descriptionField.getAnnotation(CachedKey.class);
		KeyField descriptionKeyField = (KeyField) invoke(consistentCacheHelper, "constructKeyField", annotationOfDescriptionField, descriptionField); 
		Assert.assertEquals("description", descriptionKeyField.getName());
		Assert.assertEquals(0, descriptionKeyField.getOrder());
		Assert.assertTrue(descriptionField == descriptionKeyField.getField());
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void test_getKeyValuesFromKeyFields() {
		CustomerInfoKey keyObject = getUnifyKeyObject();
		
		List<KeyField> keyFields = (List<KeyField>) invoke(consistentCacheHelper, "getKeyFieldsOfKeyObject", keyObject);
		Map<String, Object> keyValues = (Map<String, Object>) invoke(consistentCacheHelper, "getKeyValuesFromKeyFields", keyObject, keyFields);
		
		List<String> nameValueStringList = new ArrayList<String>();
		for (Map.Entry<String, Object> entry : keyValues.entrySet()) {
			String keyName = entry.getKey();
			Object keyValue = entry.getValue();
			
			nameValueStringList.add(keyName + "-" + keyValue);
		}
		
		String name = nameValueStringList.get(0);
		String siteId = nameValueStringList.get(1);
		String pageName = nameValueStringList.get(2);
		Assert.assertEquals("name-rdtest", name);
		Assert.assertEquals("siteId-esite", siteId);
		Assert.assertEquals("pageName-index", pageName);
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void test_extractValueOfKeyFields() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		ConsistentCacheHelper newCacheHelper = new ConsistentCacheHelper();
		Field ignoredKeyObjectClassesField = ConsistentCacheHelper.class.getDeclaredField("ignoredFindKeyFieldClasses");
		ignoredKeyObjectClassesField.setAccessible(true);
		Map<Class<?>, Object> ignoredClasses = (Map<Class<?>, Object>) ignoredKeyObjectClassesField.get(newCacheHelper);
		ignoredClasses.put(ToBeIgnoredKeyObject.class, new Object());
		
		ToBeIgnoredKeyObject beIgnoredKeyObject = new ToBeIgnoredKeyObject();
		beIgnoredKeyObject.setKeyName("ignoredName");
		beIgnoredKeyObject.setKeyContent("ignoredContent");
		Map<String, Object> keyValues = (Map<String, Object>) invoke(newCacheHelper, "extractValueOfKeyFields", beIgnoredKeyObject);
		Assert.assertNull(keyValues);
		
		CustomerInfoKey customerKeyObject = getUnifyKeyObject();
		Map<String, Object> customerResult = (Map<String, Object>) invoke(newCacheHelper, "extractValueOfKeyFields", customerKeyObject);
		List<String> keyValueStringList = new ArrayList<String>();
		for (Map.Entry<String, Object> entry : customerResult.entrySet()) {
			String keyName = entry.getKey();
			Object keyValue = entry.getValue();
			
			keyValueStringList.add(keyName + "-" + keyValue);
		}
		String name = keyValueStringList.get(0);
		String siteId = keyValueStringList.get(1);
		String pageName = keyValueStringList.get(2);
		Assert.assertEquals("name-rdtest", name);
		Assert.assertEquals("siteId-esite", siteId);
		Assert.assertEquals("pageName-index", pageName);
	}
	
	private Object invoke(Object target, String methodName, Object... params) {
		Class<?> clazz = target.getClass();
		Method[] methods = clazz.getDeclaredMethods();
		
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				try {
					method.setAccessible(true);
					return method.invoke(target, params);
				} catch (Exception e) {
					e.printStackTrace(System.out);
				}
			}
		}
		
		return null;
	}
}
