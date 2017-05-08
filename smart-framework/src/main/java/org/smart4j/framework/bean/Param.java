package org.smart4j.framework.bean;

import org.smart4j.framework.util.CastUtil;

import java.util.Map;

/**
 * Created by CrazyAndy
 * Date: 2017/5/8
 * Time: 12:00
 */
public class Param {
    private Map<String,Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public long getLong(String name){
        return CastUtil.castLong(name);
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }
}
