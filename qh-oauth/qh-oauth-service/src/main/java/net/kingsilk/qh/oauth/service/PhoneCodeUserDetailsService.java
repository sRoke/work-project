package net.kingsilk.qh.oauth.service;

import net.kingsilk.qh.oauth.domain.*;
import net.kingsilk.qh.oauth.domain.User;
import net.kingsilk.qh.oauth.repo.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.userdetails.User.*;
import org.springframework.security.core.userdetails.*;

import java.util.*;

import static com.querydsl.core.types.dsl.Expressions.*;

/**
 * 读取通过手机号获取用户。
 */
@Deprecated
public class PhoneCodeUserDetailsService implements UserDetailsService {


    @Autowired
    private UserRepo userRepo;

    /**
     *
     * @param username 可能是 userId, 手机号，用户名，
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findOne(anyOf(
                QUser.user.phone.eq(username),
                anyOf(
                        QUser.user.deleted.isNull(),
                        QUser.user.deleted.eq(false)
                )

        ));
        if (user == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        UserBuilder ub = org.springframework.security.core.userdetails.User.withUsername(user.getId());
        ub.password(user.getPassword());
        ub.authorities(Collections.emptyList());
        return ub.build();
    }
}
