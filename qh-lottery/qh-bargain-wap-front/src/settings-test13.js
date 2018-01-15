// 生产环境的配置
(function () {
    var devPort = "11300";              //开发端口
    var apiPort = "11300";              //api端口，支付默认api端口
    var domain = "//kingsilk.net";
    var rootPath = domain + "/qh/bargain/local/?_ddnsPort=" + devPort;   // 如果后台开发人员使用本地启动的API，仅仅修改此行的端口即可，不要提交修改。
    var apiPath = domain + "/qh/mall/local/" + apiPort + "/api";
    var token = 'qh-bargain-wap-front.' + devPort + '.userInfo';
    var oauthPath = '//kingsilk.net/oauth/rs';
    var wxloginPath = oauthPath + '/oauth/authorize?';
    var buyUrl = domain + "/qh/mall/local/?_ddnsPort=" + devPort;

    var appConfig = {
        rootPath:rootPath,
        apiPath:apiPath,
        oauthPath: oauthPath,
        wxloginPath: wxloginPath,
        token: token,
        imgUrl: "//img.kingsilk.net/",   // 图片地址
        buyUrl:buyUrl,
    };

    window.appConfig = appConfig;

    angular.module('qh-bargain-wap-front').factory('appConfig', function () {
        return appConfig;
    });

})();



