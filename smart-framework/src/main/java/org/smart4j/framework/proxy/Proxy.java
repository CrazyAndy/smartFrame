package org.smart4j.framework.proxy;

/**
 * Created by CrazyAndy
 * Date: 2017/5/8
 * Time: 20:08
 */
public interface Proxy {
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
