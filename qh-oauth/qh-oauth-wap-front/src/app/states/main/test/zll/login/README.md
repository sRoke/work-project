


# 参数

* `loginType` : 登录类型
   
   候选值：
   
    - `auto`    : 默认，根据一定规则判断出最优登录方式。
    - `backUrl` : 登录成功后返回的URL
    - `wxMp`    : 微信登录。会自动判断是否是在微信内，如果不再微信内，则是微信扫码登录。
    - `wxComMp` : 微信第三方平台托管的微信公众号登录，不支持微信扫码登录。
    - `pwd`     : 用户名、密码登录
    - `phone`   : 手机短信验证码登录

* `backUrl`     : 登录成功后的跳回的URL。

* `wxMpAppId`   : 微信公众号的 APP ID 

    当 `loginType` = `wxMp`、 `wxComMp` 时需要传递
        
* `wxComAppId`   : 微信第三方平台的 APP ID

    当 `loginType` = `wxComMp` 时需要传递
    

# 流程

1. 根据 loginType 选择合适的登录方式
1. 检查所需的参数
1. 跳转到与登录方式匹配的 state


    