package consistentcache.common.keyobject.test;

import consistentcache.common.annotation.CachedKey;

/**
 * 类ToBeIgnoredKeyObject.java的实现描述：用来测试被忽略的KeyObject类 
 * @author tyrone Aug 23, 2013 9:53:52 AM
 */
public class ToBeIgnoredKeyObject {
	@CachedKey
	private String keyName;
	
	@CachedKey
	private String keyContent;

	/**
	 * @return the keyName
	 */
	public String getKeyName() {
		return keyName;
	}

	/**
	 * @param keyName the keyName to set
	 */
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	/**
	 * @return the keyContent
	 */
	public String getKeyContent() {
		return keyContent;
	}

	/**
	 * @param keyContent the keyContent to set
	 */
	public void setKeyContent(String keyContent) {
		this.keyContent = keyContent;
	}
}
