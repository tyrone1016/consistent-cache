package consistentcache.processor.test.cases.simple.processor;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;

import consistentcache.common.cacheclient.test.HashMapCacheClient;
import consistentcache.common.keyobject.test.CustomerInfoKey;
import consistentcache.common.module.CacheClient;
import consistentcache.common.process.DefaultValueProcessor;
import consistentcache.processor.test.cases.simple.model.MockedValue;

/**
 * 类TestValueProcessor.java的实现描述：测试ValueProcessor类 
 * @author tyrone Aug 22, 2013 1:41:27 PM
 */
public class TestValueProcessor extends DefaultValueProcessor<CustomerInfoKey> {
	
	private HashMapCacheClient cacheClient = new HashMapCacheClient();

	@Override
	public String getKeySeparator() {
		return "-";
	}

	@Override
	public CacheClient getCacheClient() {
		return cacheClient;
	}

	@Override
	public Object processNewValue(CustomerInfoKey keyObject, LinkedHashMap<String, Object> keyValues) {
		// 根据key从数据源获取对应的数据信息
		String name = (String) keyValues.get("name");
		String siteId = (String) keyValues.get("siteId");
		String pageName = (String) keyValues.get("pageName");
		
		// 测试结果类
		MockedValue value = new MockedValue();
		value.setName(name);
		value.setSiteId(siteId);
		value.setPageName(pageName);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String cachedTime = dateFormat.format(new Date());
		value.setCachedTime(cachedTime);
		
		return value;
	}

	@Override
	public Object filterValue(CustomerInfoKey keyObject, Object value) {
		return value;
	}

}
