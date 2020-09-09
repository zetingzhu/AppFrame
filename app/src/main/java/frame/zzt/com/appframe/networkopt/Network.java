package frame.zzt.com.appframe.networkopt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zeting
 * Date 19/7/23.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Network {

    NetworkType netType() default NetworkType.AUTO;

}
