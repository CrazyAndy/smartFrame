package org.smart4j.framework.bean;

/**
 * Created by CrazyAndy
 * Date: 2017/5/10
 * Time: 9:51
 */
public class FormParam {
    private String fieldName;
    private String fieldValue;

    public FormParam(String fieldName, String fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }
}
