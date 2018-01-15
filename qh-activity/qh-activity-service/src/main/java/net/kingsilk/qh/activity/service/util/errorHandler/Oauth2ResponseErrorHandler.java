package net.kingsilk.qh.activity.service.util.errorHandler;

import net.kingsilk.qh.activity.api.ErrStatusException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 *
 */
public class Oauth2ResponseErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return hasError(getHttpStatusCode(response));
    }

    protected boolean hasError(HttpStatus statusCode) {
        return (statusCode.series() == HttpStatus.Series.CLIENT_ERROR ||
                statusCode.series() == HttpStatus.Series.SERVER_ERROR);
    }
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode = getHttpStatusCode(response);
//        switch (statusCode.series()) {
//
//            case CLIENT_ERROR:
//                throw new ErrStatusException(statusCode.value(),"");
//            case SERVER_ERROR:
//                throw new ErrStatusException(statusCode.value(),"1111111111111111111");
//            default:
//                throw new RestClientException("Unknown status code [" + statusCode + "]");
//        }
        if (401==statusCode.value()){
            throw new ErrStatusException(statusCode.value(),"AccessToken 过期或无效，请重新登录！");
        }else if (403==statusCode.value()){
            throw new ErrStatusException(statusCode.value(),"无权访问该资源！");
        }else {
            throw new RestClientException("Unknown status code [" + statusCode + "]");
        }
    }

    private HttpStatus getHttpStatusCode(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode;
        try {
            statusCode = response.getStatusCode();
        }
        catch (IllegalArgumentException ex) {
            throw new UnknownHttpStatusCodeException(response.getRawStatusCode(),
                    response.getStatusText(), response.getHeaders(), getResponseBody(response), getCharset(response));
        }
        return statusCode;
    }

    private byte[] getResponseBody(ClientHttpResponse response) {
        try {
            InputStream responseBody = response.getBody();
            if (responseBody != null) {
                return FileCopyUtils.copyToByteArray(responseBody);
            }
        }
        catch (IOException ex) {
            // ignore
        }
        return new byte[0];
    }

    private Charset getCharset(ClientHttpResponse response) {
        HttpHeaders headers = response.getHeaders();
        MediaType contentType = headers.getContentType();
        return contentType != null ? contentType.getCharset() : null;
    }
}
