package consistentcache.processor.test.cases.cachelist;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import consistentcache.common.analyzer.ConsistentCacheHelper;
import consistentcache.common.keyobject.test.OfferKey;
import consistentcache.common.module.CacheClient;
import consistentcache.processor.test.cases.cachelist.model.Offer;
import consistentcache.processor.test.cases.cachelist.processor.FilterValueProcessor;

/**
 * 类FilterValueProcessorTest.java的实现描述：
 * @author tyrone Aug 26, 2013 3:51:13 PM
 */
public class FilterValueProcessorTest {
	
	@Test
	public void test_filterValueProcessor() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		// 首先, 测试值不存在的情况
		OfferKey allOffers = new OfferKey();
		allOffers.setCustId(10000);
		allOffers.setGroupId(60000);
		
		ConsistentCacheHelper cacheHelper = new ConsistentCacheHelper();
		FilterValueProcessor processor = new FilterValueProcessor();
		
		boolean isContain = cacheHelper.contains(allOffers, processor);
		Object result = cacheHelper.getCachedValue(allOffers, processor);
		Assert.assertFalse(isContain);
		Assert.assertNull(result);
		
		// 放入数据
		List<Offer> cachedOffers = (List<Offer>) cacheHelper.setCachedValue(allOffers, processor, true);
		Assert.assertEquals(100, cachedOffers.size());
		Offer firstOffer = cachedOffers.get(0);
		Offer no99Offer = cachedOffers.get(98);
		Assert.assertEquals(new Integer(1), firstOffer.getOfferId());
		Assert.assertFalse(firstOffer.isOnline());
		Assert.assertEquals(new Integer(99), no99Offer.getOfferId());
		Assert.assertTrue(no99Offer.isOnline());
		
		// 重新构造一个KeyObject，判断是否存在缓存
		OfferKey sameKeyWithDifferentCondition = new OfferKey();
		sameKeyWithDifferentCondition.setCustId(10000);
		sameKeyWithDifferentCondition.setGroupId(60000);
		sameKeyWithDifferentCondition.setOnline(true);
		Integer[] offerIds = new Integer[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14};
		sameKeyWithDifferentCondition.setOfferIds(offerIds);
		
		boolean isContainWithSameCachedKey = cacheHelper.contains(sameKeyWithDifferentCondition, processor);
		Assert.assertTrue(isContainWithSameCachedKey);
		// TODO 通过反射进行验证
		CacheClient cacheClient = processor.getCacheClient();
		Field hashMapField = cacheClient.getClass().getDeclaredField("cache");
		hashMapField.setAccessible(true);
		Map<String, Object> mapObject = (Map<String, Object>) hashMapField.get(cacheClient);
		Assert.assertEquals(1, mapObject.size());
		
		List<Offer> conditionResult = (List<Offer>) cacheHelper.getCachedValue(sameKeyWithDifferentCondition, processor);
		Assert.assertEquals(4, conditionResult.size());
		Assert.assertEquals(3, conditionResult.get(0).getOfferId().intValue());
		Assert.assertEquals(12, conditionResult.get(3).getOfferId().intValue());
	}
	
	// TODO 测试带条件放入的情况，缓存的数据集会大于等于getter方法返回的数据集
}
