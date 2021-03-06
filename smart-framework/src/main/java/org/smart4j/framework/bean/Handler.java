package org.smart4j.framework.bean;

import java.lang.reflect.Method;

/**
 * Created by CrazyAndy
 * Date: 2017/5/7
 * Time: 12:09
 */
public class Handler {
    private Class<?> controllerClass;
    private Method actionMethod;

    public Handler(Class<?> controllerClass, Method actionMethod) {
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }
}
