package net.kingsilk.qh.shop.repo;

import net.kingsilk.qh.shop.domain.Sms;

public interface SmsRepo extends BaseRepo<Sms, String> {

    long countByPhone(String phone);

}
