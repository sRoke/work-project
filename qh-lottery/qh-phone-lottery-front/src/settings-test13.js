// 测试环境的配置
angular.module('qh-phone-lottery-front').factory('appConfig', function () {
    var devPort = "11300";              //开发端口
    var apiPort = "11300";              //api端口，支付默认api端口
    // // -------------------------- 测试环境 api
    var domain = "//kingsilk.net";
    var rootPath = domain + "/qh/phone/lottery/local/?_ddnsPort="+devPort;   // 如果后台开发人员使用本地启动的API，仅仅修改此行的端口即可，不要提交修改。
    var apiPath = domain + "/qh/mall/local/"+apiPort+"/api";
    var appVersion = "1.0.0";
    var token = 'qh-phone-lottery-front.'+devPort+'.token';
    return {
        rootPath:rootPath,
        apiPath: apiPath,
        imgUrl: "//img.kingsilk.net/",   // 图片地址
        token: token,
    };
});
