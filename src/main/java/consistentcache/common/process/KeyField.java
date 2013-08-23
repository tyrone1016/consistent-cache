package consistentcache.common.process;

import java.lang.reflect.Field;

/**
 * 类KeyFieldInfo.java的实现描述：作为Key的Field信息 
 * @author tyrone Aug 21, 2013 3:17:57 PM
 */
public class KeyField implements Comparable<KeyField>{
	
	/**
	 * 顺序
	 */
	private int order;
	
	/**
	 * Key的名字
	 */
	private String name;
	
	/**
	 * Field本身的信息
	 */
	private Field field;
	
	/**
	 * @return the order
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(int order) {
		this.order = order;
	}

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
	 * @return the field
	 */
	public Field getField() {
		return field;
	}

	/**
	 * @param field the field to set
	 */
	public void setField(Field field) {
		this.field = field;
	}

	/* 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(KeyField other) {
		if (other == null) {
			throw new RuntimeException("Comparing with a null KeyField");
		}
		
		int otherOrder = other.getOrder();
		
		// order值大的对象排在前
		return otherOrder - this.order ;
	}

}
