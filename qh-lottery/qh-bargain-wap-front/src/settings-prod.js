// 生产环境的配置
(function () {
    var domain = "//kingsilk.net";
    var rootPath = domain + "/qh/bargain/?showwxpaytitle=1";
    var apiPath = domain + "/qh/mall/api";
    var oauthPath = '//kingsilk.net/oauth/rs';
    var wxloginPath = oauthPath + '/oauth/authorize?';
    // var token = 'qh-pbargain-wap-front.17100.userInfo';
    var token = 'qh-bargain-wap-front.prod.userInfo';
    var buyUrl = domain + "/qh/mall/?showwxpaytitle=1";

    var appConfig = {
        rootPath: rootPath,
        apiPath: apiPath,
        oauthPath: oauthPath,
        wxloginPath: wxloginPath,
        token: token,
        imgUrl: "//img.kingsilk.net/",   // 图片地址
        buyUrl: buyUrl
    };

    window.appConfig = appConfig;

    angular.module('qh-bargain-wap-front').factory('appConfig', function () {
        return appConfig;
    });

})();

