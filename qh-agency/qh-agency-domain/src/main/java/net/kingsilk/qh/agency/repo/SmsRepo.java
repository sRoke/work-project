package net.kingsilk.qh.agency.repo;

import net.kingsilk.qh.agency.domain.Sms;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;

/**
 * 保留该接口，方法统一追加自定义方法
 */
public interface SmsRepo extends BaseRepo<Sms, String> {
    @Query(value = "{ phone : ?0 }")
    public abstract Page<Sms> findAllByPhone(String phone, Pageable pageable);

    long countByPhone(String phone);

    Sms findOneByPhoneAndContentAndValid(String phone, String code,boolean valid);
}

