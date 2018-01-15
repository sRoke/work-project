import conf from "../conf";
// import JSO from  "jso";
import JSO from "../thirdJs/jso.js";
import store from "store";

import $_ from "expose-loader?$!jquery";

global.$ = $_;
global.jQuery = $_;
global.jquery = $_;
global.jq = $_;



// 构建配置的数据
var jso = global.jso = new JSO({
    providerID: 'oauth2-authorization-server',
    client_id:'CLIENT_ID_qh-front',
    redirect_uri: location.href,
    authorization: conf.wxloginPath + "loginType=WX_SCAN&autoCreateUser",
    scopes: {request: ['LOGIN']},
    debug: true
});

// 检查是否是从 认证服务器回来的。
jso.callback(location.href, function (at) {
    global.at = at;
    store.set(conf.token, at.access_token);
    // location.href = conf.loginBackUrl;
    console.log("------------ return from OAuth server", at);
}, "oauth2-authorization-server");


// 当要跳转到 OAuth 认证服务器时，交给我们来处理。
jso.on('redirect', function (url) {
    console.log("-------redirect : " + url);
    location.href = url;
});