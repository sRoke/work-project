package net.kingsilk.qh.oauth.core.wap.errorHandler;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;

/**
 * Created by lit on 17/10/23.
 */
public class Oauth2ResponseExceptionTranslator extends DefaultWebResponseExceptionTranslator {

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        InvalidTokenException e400 = new InvalidTokenException(e.getMessage()) {
            @Override
            public int getHttpErrorCode() {
                return 401;
            }
        };
        return super.translate(e400);
    }
}
