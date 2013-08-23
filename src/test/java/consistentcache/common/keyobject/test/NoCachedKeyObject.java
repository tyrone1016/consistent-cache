package consistentcache.common.keyobject.test;

/**
 * 类NoCachedKeyKeyObject.java的实现描述：用于测试没有
 * {@link consistentcache.common.annotation.CachedKey}的KeyObject情况
 * 
 * @author tyrone Aug 22, 2013 10:00:13 PM
 */
public class NoCachedKeyObject {
	
	private String name;
	
	private String province;
	
	private String city;

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
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
}
