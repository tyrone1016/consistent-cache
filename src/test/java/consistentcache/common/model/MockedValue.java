package consistentcache.common.model;
/**
 * 类MockedValue.java的实现描述：测试用的结果类
 * @author tyrone Aug 22, 2013 2:17:00 PM
 */
public class MockedValue {
	private String name;
	
	private String siteId;
	
	private String pageName;
	
	private String cachedTime;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the siteId
	 */
	public String getSiteId() {
		return siteId;
	}

	/**
	 * @param siteId the siteId to set
	 */
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	/**
	 * @return the pageName
	 */
	public String getPageName() {
		return pageName;
	}

	/**
	 * @param pageName the pageName to set
	 */
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	/**
	 * @return the cachedTime
	 */
	public String getCachedTime() {
		return cachedTime;
	}

	/**
	 * @param cachedTime the cachedTime to set
	 */
	public void setCachedTime(String cachedTime) {
		this.cachedTime = cachedTime;
	}
}
