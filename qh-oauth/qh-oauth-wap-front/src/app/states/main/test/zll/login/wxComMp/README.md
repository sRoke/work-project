

## API 描述
 
1. 根据参数 调用 wx4j-broker-server 的 API ，获取微信授权URL。
1. 调用 wx4j-broker-server 的 API ，验证从微信服务器跳转回来的参数（ 内部会用 coder 换取 access token）。
2. 调用 wx4j-oauth-server 的 API ，获取微信授权URL。




