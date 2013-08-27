package consistentcache.processor.test.cases.simple;

import java.lang.reflect.Field;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import consistentcache.common.analyzer.ConsistentCacheHelper;
import consistentcache.common.keyobject.test.CustomerInfoKey;
import consistentcache.common.process.ValueProcessor;
import consistentcache.processor.test.cases.simple.model.MockedValue;
import consistentcache.processor.test.cases.simple.processor.TestValueProcessor;

/**
 * 类UserDefinedValueProcessorTest.java的实现描述：自定义的ValueProcessor测试
 * 通过该ValueProcessor去测试{@link consistentcache.common.analyzer.ConsistentCacheHelper}的功能
 * @author tyrone Aug 22, 2013 2:22:49 PM
 */
public class UserDefinedValueProcessorTest {
	
	@Test
	public void test_userDefinedProcessor_extendsDefaultProcessor() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		ConsistentCacheHelper cacheHelper = new ConsistentCacheHelper();
		Long[] productIds = new Long[] {1l, 2l, 3l};
		CustomerInfoKey keyObject = constructCustomerInfoKey("test01", "esite", "index", productIds, 5);
		ValueProcessor<CustomerInfoKey> processor = new TestValueProcessor();
		
		// 检查放入值前
		boolean isContains = cacheHelper.contains(keyObject, processor);
		Assert.assertFalse(isContains);
		
		// 放入数值
		MockedValue result = (MockedValue) cacheHelper.setCachedValue(keyObject, processor, true);
		isContains = cacheHelper.contains(keyObject, processor);
		Assert.assertTrue(isContains);
		String firstCacheTime = result.getCachedTime();
		
		// 取值
		MockedValue cachedValue = (MockedValue) cacheHelper.getCachedValue(keyObject, processor);
		Assert.assertTrue(result == cachedValue);
		
		// 再次放入相同Key的值，但是选择不覆盖
		CustomerInfoKey sameKeyFieldObject = constructCustomerInfoKey("test01", "esite", "index", null, 10);
		MockedValue setValueAgainResult = (MockedValue) cacheHelper.setCachedValue(sameKeyFieldObject, processor, false);
		Assert.assertTrue(result == setValueAgainResult);
		
		// 放入相同Key, 但是选择覆盖
		MockedValue setValueAgainWithCoverResult = (MockedValue) cacheHelper.setCachedValue(sameKeyFieldObject, processor, true);
		Assert.assertFalse(result == setValueAgainWithCoverResult);
		String setValueAgainWithCoverCacheTime = setValueAgainWithCoverResult.getCachedTime();
		Assert.assertFalse(setValueAgainWithCoverCacheTime == firstCacheTime);
		
		// 因为TestValueProcessor未在filter方法中做处理，所以CachedKey相同的KeyObject取回的值是相同的
		MockedValue sameKeyFieldGetCacheResult = (MockedValue) cacheHelper.getCachedValue(keyObject, processor);
		Assert.assertTrue(sameKeyFieldGetCacheResult == setValueAgainWithCoverResult);
		
		// 直接验证HashMap中的个数
		Field cacheClientField = processor.getClass().getDeclaredField("cacheClient");
		cacheClientField.setAccessible(true);
		Object cacheClient = cacheClientField.get(processor);
		Class<?> fieldClass = cacheClientField.getType();
		Field cacheField = fieldClass.getDeclaredField("cache");
		cacheField.setAccessible(true);
		Map<String, Object> cacheMap = (Map<String,Object>) cacheField.get(cacheClient);
		Assert.assertEquals(1, cacheMap.size());
		Assert.assertTrue(cacheMap.containsKey("test01-esite-index"));
		
		CustomerInfoKey anotherObjectKey = constructCustomerInfoKey("customer01", "esite", "index", null, 15);
		cacheHelper.setCachedValue(anotherObjectKey, processor, true);
		Assert.assertEquals(2, cacheMap.size());
		Assert.assertTrue(cacheMap.containsKey("customer01-esite-index"));
	}
	
	private CustomerInfoKey constructCustomerInfoKey(String name,
			String siteId, String pageName, Long[] productIds, int countNum) {
		CustomerInfoKey customerInfoKey = new CustomerInfoKey();
		customerInfoKey.setName(name);
		customerInfoKey.setSiteId(siteId);
		customerInfoKey.setPageName(pageName);
		customerInfoKey.setProductIds(productIds);
		customerInfoKey.setCountNum(countNum);

		return customerInfoKey;
	}
}
