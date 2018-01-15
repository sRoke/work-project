package net.kingsilk.qh.oauth.security.login.pwd;

import net.kingsilk.qh.oauth.domain.*;
import net.kingsilk.qh.oauth.domain.User;
import net.kingsilk.qh.oauth.repo.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.userdetails.User.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

import java.util.*;

import static com.querydsl.core.types.dsl.Expressions.*;

/**
 * 通过 密码登录。
 *
 * 用户名可以是：userId, userName，绑定的电话号码，绑定的手机号码。
 */
@Service
public class UsernamePasswordDetailsService implements UserDetailsService {

    //public static final String EMPTY_PASSWORD = "N/A"

    @Autowired
    private UserRepo userRepo;

    /**
     *
     * @param username 用户名
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findOne(allOf(

                anyOf(
                        // 登录用户名就是用户名
                        QUser.user.username.eq(username),

                        // 登录用户名是手机号，且该手机号已经绑定
                        allOf(
                                QUser.user.phone.eq(username),
                                QUser.user.phoneVerifiedAt.isNotNull()
                        ),

                        // 登录用户名是电子邮箱，且电子邮箱已经绑定
                        allOf(
                                QUser.user.email.eq(username),
                                QUser.user.emailVerifiedAt.isNotNull()
                        )
                ),

                // 确保未被逻辑删除
                QUser.user.deleted.eq(false)
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
