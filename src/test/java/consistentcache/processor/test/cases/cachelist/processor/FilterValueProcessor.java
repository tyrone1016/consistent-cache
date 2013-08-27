package consistentcache.processor.test.cases.cachelist.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import consistentcache.common.cacheclient.test.HashMapCacheClient;
import consistentcache.common.keyobject.test.OfferKey;
import consistentcache.common.module.CacheClient;
import consistentcache.common.process.DefaultValueProcessor;
import consistentcache.processor.test.cases.cachelist.model.Offer;

/**
 * 类FilterValueProcessor.java的实现描述：测试缓存List的Processor情况 
 * @author tyrone Aug 26, 2013 1:48:02 PM
 */
public class FilterValueProcessor extends DefaultValueProcessor<OfferKey> {
	
	private final HashMapCacheClient cacheClient = new HashMapCacheClient();

	@Override
	public String getKeySeparator() {
		return "-";
	}

	@Override
	public CacheClient getCacheClient() {
		return cacheClient;
	}

	@Override
	public Object processNewValue(OfferKey keyObject,
			LinkedHashMap<String, Object> keyValues) {
		Integer custId = (Integer) keyValues.get("custId");
		Integer groupId = (Integer) keyValues.get("groupId");
		
		List<Offer> resultList = new ArrayList<Offer>();
		
		for (int i = 1; i <= 100; i++) {
			Offer offer = new Offer();
			offer.setCustId(custId);
			offer.setGroupId(groupId);
			offer.setOfferId(i);
			
			int remainder = i % 3;
			offer.setOnline(remainder == 0);
			
			resultList.add(offer);
		}
		
		return resultList;
	}

	@Override
	public Object filterValue(OfferKey keyObject, Object value) {
		if (value == null) {
			return null;
		}
		
		List<Offer> cacheResult = filterByOfferIds(keyObject, value);
		
		List<Offer> filteredResult = filterByIsOnline(keyObject, cacheResult);
		
		return filteredResult;
	}

	/**
	 * @param keyObject
	 * @param cacheResult
	 * @return
	 */
	private List<Offer> filterByIsOnline(OfferKey keyObject,
			List<Offer> cacheResult) {
		if (keyObject.isOnline() == null) {
			return cacheResult;
		}
		
		List<Offer> filteredResult = new ArrayList<Offer>();

		for (Offer offer : cacheResult) {
			if (offer.isOnline() == keyObject.isOnline().booleanValue()) {
				filteredResult.add(offer);
			}
		}
		return filteredResult;
	}

	/**
	 * @param keyObject
	 * @param value
	 * @return
	 */
	private List<Offer> filterByOfferIds(OfferKey keyObject, Object value) {
		if (keyObject.getOfferIds() == null
				|| keyObject.getOfferIds().length == 0) {
			return (List<Offer>) value;
		}

		List<Offer> cacheResult = new ArrayList<Offer>();

		Map<Integer, Offer> offerIdMap = new HashMap<Integer, Offer>();
		for (Offer offer : (List<Offer>) value) {
			offerIdMap.put(offer.getOfferId(), offer);
		}

		Integer[] requestOfferIds = keyObject.getOfferIds();
		for (Integer requestId : requestOfferIds) {
			Offer offer = offerIdMap.get(requestId);
			if (offer != null) {
				cacheResult.add(offer);
			}
		}
		return cacheResult;
	}

}
