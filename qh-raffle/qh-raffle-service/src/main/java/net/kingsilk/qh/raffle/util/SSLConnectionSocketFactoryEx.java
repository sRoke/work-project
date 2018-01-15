package net.kingsilk.qh.raffle.util;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.net.Proxy;
import java.net.Socket;

/**
 * 额外提供代理服务器的设置
 * <p>
 * 参考 http://stackoverflow.com/a/22960881/533317
 */
public class SSLConnectionSocketFactoryEx extends SSLConnectionSocketFactory {

    private Proxy proxy;

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

}
