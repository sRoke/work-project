package net.kingsilk.qh.oauth.core.wap.resource.s.user;

import net.kingsilk.qh.oauth.api.s.user.dto.InfoResp;
import net.kingsilk.qh.oauth.domain.User;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class InfoRespConverter implements Converter<User, InfoResp> {

    @Autowired
    private ObjectProvider<ConversionService> csProvider;

    @Override
    public InfoResp convert(User source) {

        if (source == null) {
            return null;
        }
        InfoResp target = new InfoResp();


        // --------------------------------------- common fields
        target.setId(source.getId());
        target.setDateCreated(source.getDateCreated());
        target.setCreatedBy(source.getCreatedBy());
        target.setLastModifiedDate(source.getLastModifiedDate());
        target.setLastModifiedBy(source.getLastModifiedBy());

        // --------------------------------------- biz fields
        target.setEnabled(source.isEnabled());
        target.setAccountExpired(source.isAccountLocked());
        target.setAccountExpired(source.isAccountExpired());
        target.setInviterUserId(source.getInviterUserId());

        // ---------------------------------- 绑定用户信息:  用户名、密码、支付密码
        target.setUsername(source.getUsername());

        // ---------------------------------- 绑定用户信息:  手机号
        target.setPhone(source.getPhone());
        target.setPhoneVerifiedAt(source.getPhoneVerifiedAt());

        // ---------------------------------- 绑定用户信息:  电子邮箱
        target.setEmail(source.getEmail());
        target.setEmailVerifiedAt(source.getEmailVerifiedAt());


        // ---------------------------------- 绑定用户信息:  微信用户
        // 这里不处理

        return target;
    }
}
