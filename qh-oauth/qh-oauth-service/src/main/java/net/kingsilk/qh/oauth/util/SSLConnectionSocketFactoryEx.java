package net.kingsilk.qh.oauth.util;

import org.apache.http.conn.ssl.*;
import org.apache.http.protocol.*;

import javax.net.ssl.*;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.*;

/**
 * 额外提供代理服务器的设置
 * <p>
 * 参考 http://stackoverflow.com/a/22960881/533317
 */
public class SSLConnectionSocketFactoryEx extends SSLConnectionSocketFactory {
    public SSLConnectionSocketFactoryEx(SSLContext sslContext) {
        super(sslContext);
    }

    public SSLConnectionSocketFactoryEx(SSLContext sslContext, X509HostnameVerifier hostnameVerifier) {
        super(sslContext, hostnameVerifier);
    }

    public SSLConnectionSocketFactoryEx(SSLContext sslContext, String[] supportedProtocols, String[] supportedCipherSuites, X509HostnameVerifier hostnameVerifier) {
        super(sslContext, supportedProtocols, supportedCipherSuites, hostnameVerifier);
    }

    public SSLConnectionSocketFactoryEx(SSLSocketFactory socketfactory, X509HostnameVerifier hostnameVerifier) {
        super(socketfactory, hostnameVerifier);
    }

    public SSLConnectionSocketFactoryEx(SSLSocketFactory socketfactory, String[] supportedProtocols, String[] supportedCipherSuites, X509HostnameVerifier hostnameVerifier) {
        super(socketfactory, supportedProtocols, supportedCipherSuites, hostnameVerifier);
    }

    public SSLConnectionSocketFactoryEx(SSLContext sslContext, HostnameVerifier hostnameVerifier) {
        super(sslContext, hostnameVerifier);
    }

    public SSLConnectionSocketFactoryEx(SSLContext sslContext, String[] supportedProtocols, String[] supportedCipherSuites, HostnameVerifier hostnameVerifier) {
        super(sslContext, supportedProtocols, supportedCipherSuites, hostnameVerifier);
    }

    public SSLConnectionSocketFactoryEx(SSLSocketFactory socketfactory, HostnameVerifier hostnameVerifier) {
        super(socketfactory, hostnameVerifier);
    }

    public SSLConnectionSocketFactoryEx(SSLSocketFactory socketfactory, String[] supportedProtocols, String[] supportedCipherSuites, HostnameVerifier hostnameVerifier) {
        super(socketfactory, supportedProtocols, supportedCipherSuites, hostnameVerifier);
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
