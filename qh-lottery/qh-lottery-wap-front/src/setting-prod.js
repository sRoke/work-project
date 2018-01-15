(function () {
    let domain = "//kingsilk.net";
    let rootPath = domain + "/qh/lottery/?showwxpaytitle=1";
    let apiPath = domain + "/qh/mall/api";
    let oauthPath = `//kingsilk.net/oauth/rs`;         //换成线上
    let wxloginPath = `${oauthPath}/oauth/authorize?`; //换成线上
    let token = `qh-lottery-wap-front.prod.token`;

    var appConfig = {
        imgUrl: "//img.kingsilk.net/",   // 图片地址
        rootPath: rootPath,
        apiPath: apiPath,
        wxloginPath: wxloginPath,
        token: token,
        oauthPath: oauthPath
    };

    window.appConfig = appConfig;
})();

//
// angular.module('qh-lottery-wap-front').factory('appConfig', function () {
//
//
//     return window._appConfig;
// });
