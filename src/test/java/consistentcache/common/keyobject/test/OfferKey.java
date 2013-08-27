package consistentcache.common.keyobject.test;

import consistentcache.common.annotation.CachedKey;

/**
 * 类OfferKey.java的实现描述：测试二维Cache-Key的情况 
 * @author tyrone Aug 26, 2013 1:57:38 PM
 */
public class OfferKey {
	@CachedKey
	private Integer custId;
	
	@CachedKey
	private Integer groupId;
	
	private Integer[] offerIds;
	
	private Boolean isOnline;

	/**
	 * @return the custId
	 */
	public Integer getCustId() {
		return custId;
	}

	/**
	 * @param custId the custId to set
	 */
	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	/**
	 * @return the groupId
	 */
	public Integer getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the offerIds
	 */
	public Integer[] getOfferIds() {
		return offerIds;
	}

	/**
	 * @param offerIds the offerIds to set
	 */
	public void setOfferIds(Integer[] offerIds) {
		this.offerIds = offerIds;
	}

	/**
	 * @return the isOnline
	 */
	public Boolean isOnline() {
		return isOnline;
	}

	/**
	 * @param isOnline the isOnline to set
	 */
	public void setOnline(Boolean isOnline) {
		this.isOnline = isOnline;
	}

}
