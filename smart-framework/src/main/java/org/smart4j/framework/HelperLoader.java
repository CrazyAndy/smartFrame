package org.smart4j.framework;

import org.smart4j.framework.helper.*;
import org.smart4j.framework.util.ClassUtil;

/**
 * Created by CrazyAndy
 * Date: 2017/5/8
 * Time: 11:49
 */
public class HelperLoader {
    public static void init(){
        Class<?>[] classList = {ClassHelper.class,
                BeanHelper.class, AopHelper.class,IocHelper.class, ControllerHelper.class};
        for (Class<?> cls:classList) {
            ClassUtil.loadClass(cls.getName());
        }
    }
}
