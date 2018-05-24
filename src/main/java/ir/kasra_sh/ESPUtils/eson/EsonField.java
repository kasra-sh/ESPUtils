package ir.kasra_sh.ESPUtils.eson;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by blkr on 4/5/18.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EsonField {
    String name();
    Class arrayType() default Object.class;
}
