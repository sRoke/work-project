// import conf from "../conf";
//
// // import JSO from  "jso";
// import JSO from "../thirdJs/jso.js"
//
//
// // 构建配置的数据
// var jso = global.jso = new JSO({
//     providerID: 'oauth2-authorization-server',
//     client_id: 'CLIENT_ID_qh-net.kingsilk.qh.vote-wap-front',
//     redirect_uri: encodeURIComponent(location.href),
//     authorization: conf.wxloginPath + "&backUrl=" + encodeURIComponent(location.href),
//     scopes: {request: 'LOGIN'},
//     debug: true
// });
//
// // 检查是否是从 认证服务器回来的。
// jso.callback(location.href, function (at) {
//
//     global.at = at;
//     console.log("------------ return from OAuth server", at);
// }, "oauth2-authorization-server");
//
//
// // 当要跳转到 OAuth 认证服务器时，交给我们来处理。
// jso.on('redirect', function (url) {
//     console.log("-------redirect : " + url);
//     location.href = url;
// });
import conf from "../conf";
import JSO from "../thirdJs/jso.js";
import store from "store";

// 构建配置的数据
var jso = global.jso = new JSO({
    providerID: 'oauth2-authorization-server-vote-wap',
    client_id:'CLIENT_ID_qh-front',
    redirect_uri: location.href,
    authorization: conf.wxloginPath + "loginType=WX_SCAN&autoCreateUser",
    scopes: {request: ['LOGIN']},
    debug: true
});

console.log(' location.href', location.href);
// 检查是否是从 认证服务器回来的。
jso.callback(location.href, function (at) {
    global.at = at;
    console.log(`OAuth server  >>>>>>  at`,at);
    // 1. 先检查当前 参数中 restoreUrl, 有的话,直接掉转到该URL
    // 2. TODO 从 localStorage 中获取登录前最后一次访问的URL, 有的话,先清除,再上次如果有就跳转过去,
    // 3. 什么都不做
    store.set(conf.token, at.access_token);
    // location.href = store.get('login_backUrl');
    store.remove('login_backUrl');
}, "oauth2-authorization-server-vote-wap");


// 当要跳转到 OAuth 认证服务器时，交给我们来处理。
jso.on('redirect', function (url) {
    /*

     http://kingsilk.net/qh/oauth/api/oauth?loginType=WxLogin&backUrl=urlencode(

     https://vote.kingsilk.net/local/17100/#/voteApp/11111/center/main  & restoreUrl=urlEncode(
                                https://vote.kingsilk.net/local/17100/#/a/b
     )

     ) -> 追加 restoreUrl

     */
    // 在修改一下URL : http://kingsilk.net/qh/oauth/api/oauth?loginType=WxLogin&backUrl=urlencode("") -> 更新 redirect_uri
    console.log("jso.on >>>  redirect  >>> " + url);
    location.href = url;
});