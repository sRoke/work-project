





# 参数

* `backUrl`     : 登录成功后的跳回的URL。
* `wxMpAppId`   : 微信公众号的 APP ID 


# 流程

1. 先检查当前用户是否已经登录，如果已经登录：

    1. 检查用户是否已经绑定手机号，没绑定的 跳转至 `#/user/bind/phone`
       传递参数 backUrl 为 当前页面的参数的 backUrl
     
    1. 已绑定，则返回到 backUrl

1. 判断是否从微信返回 （通过 hash 中参数 fromWx 是否为 true 判断）

    1. 如果是从微信返回，则从url hash 中提取 code，state 等参数
       调用 `/oauth/rs/api/s/login/wxMp` API，进行登录。
    1. 如果登录成功，后续处理同第一步
    
    1. 如果未登录（未找到绑定该微信号的用户）
       则跳转到 `#/reg/phone` 通过手机号、短信验证码注册新用户(同时包含登录)
       并传递参数 backUrl 为 (`#/user/bind/wx` + 当前页面的 backUrl参数)    
       
       
       ```txt
       
       var backUrl=urlEncode(
               #/user/bind/wx?backUrl=urlEnocde(
                   #/oauth/authorize?redirect_url=urlEncode(
                       httpts://.../agency/#/xxx
                   )
               )
       )
       
       goto  #/reg/phone?backUrl=${backUrl}
       goto  #/login/phone?backUrl=${backUrl}
       ``` 
    
1. 跳转到微信

    1. 将当前URL作为微信授权返回的URL，但追加额外 hash 参数 ： fromWx=true
    1. 调用 API `/wx/rs/api/wxMp/{wxMpAppId}/user/auth/url` 获取微信授权的URL
    1. JS 跳转到 微信授权URL




