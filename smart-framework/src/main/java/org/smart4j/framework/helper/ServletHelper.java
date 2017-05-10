package org.smart4j.framework.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by CrazyAndy
 * Date: 2017/5/10
 * Time: 22:03
 */
public final class ServletHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServletHelper.class);

    private static final ThreadLocal<ServletHelper> SERVLET_HELPER_HOLDER =
            new ThreadLocal<ServletHelper>();
    private HttpServletRequest request;
    private HttpServletResponse response;

    public ServletHelper(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public static void init(HttpServletRequest request,HttpServletResponse response){
        SERVLET_HELPER_HOLDER.set(new ServletHelper(request,response));
    }

    public static void destroy(){
        SERVLET_HELPER_HOLDER.remove();
    }

    private static HttpServletRequest getRequest() {
        return SERVLET_HELPER_HOLDER.get().request;
    }

    private static HttpServletResponse getResponse() {
        return SERVLET_HELPER_HOLDER.get().response;
    }
    private static HttpSession getSession(){
        return getRequest().getSession();
    }
    private static ServletContext getServletContext(){
        return getRequest().getServletContext();
    }
    public static void setRequestAttribute(String key,Object value){
        getRequest().setAttribute(key,value);
    }
    public static <T> T getRequestAttribute(String key){
        return (T)getRequest().getAttribute(key);
    }
    public static void remoteRequestAttribute(String key){
        getRequest().removeAttribute(key);
    }
    public static void setRedirect(String location){
        try {
            getResponse().sendRedirect(getRequest().getContextPath()+location);
        } catch (Exception e) {
            LOGGER.error("redirect failure",e);
        }
    }
    public static void setSessionAttribute(String key,Object value){
        getSession().setAttribute(key,value);
    }
    public static <T> T getSessionAttribut(String key){
        return (T)getSession().getAttribute(key);
    }
    private static void removeSessionAttribut(String key){
        getSession().removeAttribute(key);
    }

    /**
     * 使 Session 失效
     */

    public static void invalidateSession(){
        getSession().invalidate();
    }
}
