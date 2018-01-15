// 生产环境的配置
(function () {
    var domain = "//kingsilk.net";
    var rootPath = domain + "/qh/assistance/?showwxpaytitle=1";
    var apiPath = domain + "/qh/mall/api";
    var oauthPath = '//kingsilk.net/oauth/rs';
    var wxloginPath = oauthPath + '/oauth/authorize?';
    // var token = 'qh-passistance-wap-front.17100.userInfo';
    var token = 'qh-assistance-wap-front.prod.userInfo';
    var appConfig = {
        rootPath: rootPath,
        apiPath: apiPath,
        oauthPath: oauthPath,
        wxloginPath: wxloginPath,
        token: token,
        imgUrl: "//img.kingsilk.net/",   // 图片地址
    };

    window.appConfig = appConfig;

    angular.module('qh-assistance-wap-front').factory('appConfig', function () {
        return appConfig;
    });

})();

