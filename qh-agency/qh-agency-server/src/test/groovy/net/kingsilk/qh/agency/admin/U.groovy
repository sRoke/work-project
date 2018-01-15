package net.kingsilk.qh.agency.admin

/**
 * 测试用工具类
 */
class U {

    /**
     * 生成 basic 认证的 http 请求头 "Authorization" 的值。
     *
     * @param username 用户名
     * @param password 密码
     * @return base64 转码后的用户名、密码
     */
    static String basicAuth(String username, String password) {
        return new String(Base64.encoder.encode("${username}:${password}".getBytes("UTF-8")), "UTF-8");
    }
}
