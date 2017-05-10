package org.smart4j.framework.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by CrazyAndy
 * Date: 2017/5/10
 * Time: 10:06
 */
public class StringUtil {
    public static final String SEPARATOR = String.valueOf((char)29);
    public static boolean isNotEmpty(String name){
        return StringUtils.isNotEmpty(name);
    }
    public static String[] splitString(String str,String separator){
        return StringUtils.split(str,separator);
    }
}
