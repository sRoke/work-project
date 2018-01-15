package net.kingsilk.qh.shop.service.util.errorHandler;

import net.kingsilk.qh.shop.api.ErrStatusException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;

import java.util.Map;

/**
 *
 */
@Service
public class ErrorAttributesEx extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(
            RequestAttributes requestAttributes,
            boolean includeStackTrace
    ) {

        Map<String, Object> errorAttributes = super.getErrorAttributes(requestAttributes, includeStackTrace);

        addCustomErr(requestAttributes, errorAttributes);

        return errorAttributes;
    }


    public void addCustomErr(
            RequestAttributes requestAttributes,
            Map<String, Object> errorAttributes
    ) {

        Throwable error = getError(requestAttributes);
        if (error == null) {
            return;
        }

        int index = ExceptionUtils.indexOfThrowable(error, ErrStatusException.class);
        if (index < 0) {
            return;
        }
        ErrStatusException e = (ErrStatusException) ExceptionUtils.getThrowableList(error).get(index);


        Integer customErrStatus = e.getErrStatus();
        if (customErrStatus != null) {
            errorAttributes.put("status", customErrStatus);
        }

        String errMsg = e.getMessage();
        if (StringUtils.hasText(errMsg)) {
            errorAttributes.put("error", errMsg);
        }
    }

}
