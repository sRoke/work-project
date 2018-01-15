package net.kingsilk.qh.activity.service.util;

import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.net.Proxy;
import java.net.Socket;

/**
 * 追加 SOCKS 代理服务器的设置
 */
public class PlainConnectionSocketFactoryEx extends PlainConnectionSocketFactory {
    private Proxy proxy;

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
}