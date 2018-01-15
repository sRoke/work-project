
# 说明

1. 要求已经登录。


# 参数

* backUrl    : 绑定成功后的回退URL。
* wxMpAppId  : 微信公众号APP ID
* wxComAppId : 微信第三方平台的APP ID。
    可选，如果存在，则调用托管微信公众号的相关API，
    否则，调用自营 微信公众号的相关API。


# 流程

1. 调用 API 绑定当前 SESSION 中已经授权的 微信号。
1. 如果 SESSION 中没有微信授权，则调用API 获取微信授权URL

1. 检查是否是从微信返回（URL Hash 参数 fromWx=true）

    1. 解析 URL HASH 参数 code、state 等
    1. 调用 API，完成微信验证，并绑定该微信号。
    1. JS 跳回 backUrl。

1. JS 跳转到微信授权URL，

    1. 调用API，获得微信授权 URL，其中参数 回调地址为当前页面（且多额外参数 fromWx=true)
    1. JS 跳转到微信授权页面。



# 相关API

```txt

# 前提要求：已经微信授权过，session 里有相应信息，
/oauth/local/rs/11300/s/user/bind/sessionWxMp

```

