package org.smart4j.framework.annotation;

import java.lang.annotation.*;

/**
 * Created by CrazyAndy
 * Date: 2017/5/8
 * Time: 20:05
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    Class<? extends Annotation> value();
}
