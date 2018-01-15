package net.kingsilk.qh.oauth.repo;

import net.kingsilk.qh.oauth.domain.User;

/**
 *
 */
public interface UserRepo extends BaseRepo<User, String> {

    User findOneByPhone(String phone);
}
