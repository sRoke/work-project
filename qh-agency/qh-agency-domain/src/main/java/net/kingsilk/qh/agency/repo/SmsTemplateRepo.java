package net.kingsilk.qh.agency.repo;

import net.kingsilk.qh.agency.domain.SmsTemplate;
import org.springframework.data.mongodb.repository.Query;

/**
 * 保留该接口，方法统一追加自定义方法
 */
public interface SmsTemplateRepo extends BaseRepo<SmsTemplate, String> {
    @Query(value = "{ key : ?0 }")
    public abstract SmsTemplate findOneByKey(String key);
}
