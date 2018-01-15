package net.kingsilk.qh.oauth.mongo;

import net.kingsilk.qh.oauth.security.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

/**
 *
 */
@Service
public class MongoAuditorService implements AuditorAware<String> {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private SecService secService;

    @Override
    public String getCurrentAuditor() {

        return secService.curUserId();


    }
}
