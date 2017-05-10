package org.smart4j.framework;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.smart4j.framework.bean.Data;
import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.bean.View;
import org.smart4j.framework.helper.*;
import org.smart4j.framework.util.CodecUtil;
import org.smart4j.framework.util.JsonUtil;
import org.smart4j.framework.util.ReflectionUtil;
import org.smart4j.framework.util.StreamUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CrazyAndy
 * Date: 2017/5/8
 * Time: 12:11
 */
@WebServlet(urlPatterns = "/*",loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet{

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        HelperLoader.init();
        ServletContext servletContext = servletConfig.getServletContext();
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath()+"*");
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath()+"*");
        UploadHelper.init(servletContext);
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String requestMethod = request.getMethod().toLowerCase();
        String requestPath = request.getPathInfo();

        if(requestPath.equals("/favicon.ico")){
            return;
        }
        ServletHelper.init(request,response);

        try {
            Handler handler = ControllerHelper.getHandler(requestMethod,requestPath);
            if(handler != null){
                //获取controller类以及bean实例
                Class<?> controllerClass = handler.getControllerClass();
                Object controllerBean = BeanHelper.getBean(controllerClass);

                Param param;
                if(UploadHelper.isMultipart(request)){
                    param = UploadHelper.createParam(request);
                }else {
                    param = RequestHelper.createParam(request);
                }

                Object result ;
                //调用action方法
                Method actionMethod = handler.getActionMethod();
                if (param.isEmpty()){
                    result = ReflectionUtil.invokeMethod(controllerBean,actionMethod);
                }else {
                    result = ReflectionUtil.invokeMethod(controllerBean,actionMethod,param);
                }
                //处理action
                if(result instanceof View){
                    handleViewResult((View)result,request,response);
                }else if(result instanceof Data){
                    handleDataResult((Data)result,response);
                }
            }
        } catch (Exception e) {
           e.printStackTrace();
        } finally {
            ServletHelper.destroy();
        }
    }
    private void handleViewResult(View view,HttpServletRequest request,
           HttpServletResponse response) throws IOException,ServletException{
        String path = view.getPath();
        if(StringUtils.isNotEmpty(path)){
            if(path.startsWith("/")){
                response.sendRedirect(request.getContextPath()+path);
            }else {
                Map<String,Object> model = view.getModel();
                for (Map.Entry<String,Object> entry:model.entrySet()){
                    request.setAttribute(entry.getKey(),entry.getValue());
                }
                request.getRequestDispatcher(ConfigHelper.getAppJspPath()+path).
                        forward(request,response);
            }
        }
    }
    private void handleDataResult(Data data,HttpServletResponse response) throws IOException{
        Object model = data.getModel();
        if(model != null){
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            String json = JsonUtil.toJson(model);
            writer.write(json);
            writer.flush();
            writer.close();
        }
    }
}
