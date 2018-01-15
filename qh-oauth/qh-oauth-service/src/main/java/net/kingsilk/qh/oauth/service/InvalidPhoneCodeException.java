package net.kingsilk.qh.oauth.service;

import org.springframework.security.core.*;

/**
 *
 */
public class InvalidPhoneCodeException extends AuthenticationException {

    public InvalidPhoneCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public InvalidPhoneCodeException(String msg) {
        super(msg);
    }
}
