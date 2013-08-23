package consistentcache.common.keyobject.test;

import consistentcache.common.annotation.CachedKey;

/**
 * 类TestNameKey.java的实现描述：用于测试提供name属性的KeyObject
 * @author tyrone Aug 22, 2013 6:35:10 PM
 */
public class TestNameKey {
	
	@CachedKey(keyName="remarkName", order = 2)
	private String name;
	
	@CachedKey
	private String description;
	
	private int age;

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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}
}
