package consistentcache.common.keyobject.test;

import consistentcache.common.annotation.CachedKey;

/**
 * 类CustomerDTO.java的实现描述：测试用对象 
 * @author tyrone Aug 22, 2013 1:45:25 PM
 */
public class CustomerInfoKey {
	
	@CachedKey(order = 3)
	private String name;
	
	@CachedKey(order = 2)
	private String siteId;
	
	@CachedKey(order = 1)
	private String pageName;
	
	private Long[] productIds;
	
	private int countNum;

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
	 * @return the productIds
	 */
	public Long[] getProductIds() {
		return productIds;
	}

	/**
	 * @param productIds the productIds to set
	 */
	public void setProductIds(Long[] productIds) {
		this.productIds = productIds;
	}

	/**
	 * @return the countNum
	 */
	public int getCountNum() {
		return countNum;
	}

	/**
	 * @param countNum the countNum to set
	 */
	public void setCountNum(int countNum) {
		this.countNum = countNum;
	}
	
}
