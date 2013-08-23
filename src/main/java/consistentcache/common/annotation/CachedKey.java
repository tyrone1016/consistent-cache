package consistentcache.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类CachedKey.java的实现描述：标记某个字段为缓存中的Key 
 * @author tyrone Aug 21, 2013 11:18:31 AM
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CachedKey {
	
	String keyName() default "";
	
	/**
	 * 顺序
	 * @return
	 */
	int order() default 0;
}
