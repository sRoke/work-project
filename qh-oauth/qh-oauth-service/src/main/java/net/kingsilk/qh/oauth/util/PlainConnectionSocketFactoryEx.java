package net.kingsilk.qh.oauth.util;

import org.apache.http.conn.socket.*;
import org.apache.http.protocol.*;

import java.io.*;
import java.net.*;

/**
 * 追加 SOCKS 代理服务器的设置
 */
public class PlainConnectionSocketFactoryEx extends PlainConnectionSocketFactory {

    public PlainConnectionSocketFactoryEx() {
        super();
    }

    @Override
    public Socket createSocket(final HttpContext context) throws IOException {
        if (proxy != null) {
            return new Socket(proxy);
        }

        return super.createSocket(context);
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    private Proxy proxy;
}
