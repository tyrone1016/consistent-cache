package consistentcache.processor.test.cases.cachelist.model;
/**
 * 类Offer.java的实现描述：返回的数据对象 
 * @author tyrone Aug 26, 2013 2:25:06 PM
 */
public class Offer {
	private Integer custId;
	
	private Integer groupId;
	
	private Integer offerId;
	
	private boolean isOnline;

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
	 * @return the offerId
	 */
	public Integer getOfferId() {
		return offerId;
	}

	/**
	 * @param offerId the offerId to set
	 */
	public void setOfferId(Integer offerId) {
		this.offerId = offerId;
	}

	/**
	 * @return the isOnline
	 */
	public boolean isOnline() {
		return isOnline;
	}

	/**
	 * @param isOnline the isOnline to set
	 */
	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}
	
}
