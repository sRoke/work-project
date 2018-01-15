package net.kingsilk.qh.oauth.service;

import net.kingsilk.qh.oauth.domain.*;
import org.slf4j.*;
import org.springframework.context.*;
import org.springframework.context.support.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;

/**
 * Created by zll on 14/08/2017.
 */
@Service
public class UserChecker implements MessageSourceAware {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    public void check(User user) {
        preCheck(user);
        postCheck(user);
    }

    public void preCheck(User user) {
        if (user.isAccountLocked()) {
            logger.debug("User account is locked");

            throw new LockedException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.locked",
                    "User account is locked"));
        }

        if (!user.isEnabled()) {
            logger.debug("User account is disabled");

            throw new DisabledException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.disabled",
                    "User is disabled"));
        }

        if (user.isAccountExpired()) {
            logger.debug("User account is expired");

            throw new AccountExpiredException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.expired",
                    "User account has expired"));
        }
    }

    public void postCheck(User user) {
//
//        if (!user.isCredentialsNonExpired()) {
//            logger.debug("User account credentials have expired");
//
//            throw new CredentialsExpiredException(messages.getMessage(
//                    "AbstractUserDetailsAuthenticationProvider.credentialsExpired",
//                    "User credentials have expired"));
//        }
    }
}
