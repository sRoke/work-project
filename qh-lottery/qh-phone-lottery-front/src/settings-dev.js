// 开发环境的配置
angular.module('qh-phone-lottery-front').factory('appConfig', function () {
    var devPort = "16300";              //开发端口
    var apiPort = "14300";              //api端口，支付默认api端口
    // // -------------------------- 测试环境 api
    var domain = "//kingsilk.net";
    var rootPath = domain + "/qh/phone/lottery/local/?_ddnsPort="+devPort;   // 如果后台开发人员使用本地启动的API，仅仅修改此行的端口即可，不要提交修改。
    var apiPath = domain + "/qh/mall/local/"+apiPort+"/api";
    var appVersion = "1.0.0";
    var token = 'qh-phone-lottery-front.'+devPort+'.userInfo';
    return {
        rootPath:rootPath,
        apiPath: apiPath,
        imgUrl: "//img.kingsilk.net/",   // 图片地址
        token: token,
    };
});


// (function () {
//     const devPort = "17100";              //开发端口
//     const apiPort = "14300";              //api端口，支付默认api端口
//     let domain = "//kingsilk.net";
//     let rootPath = domain + `/qh/lottery/local/?_ddnsPort=${devPort}`;
//     let apiPath = domain + `/qh/mall/local/${apiPort}/api`;
//     let oauthPath = `//kingsilk.net/oauth/local/${apiPort}/rs`;
//     let wxloginPath = `${oauthPath}/oauth/authorize?`;
//     let token = `qh-lottery-wap-front.${devPort}.token`;
//
//     var appConfig = {
//         imgUrl: "//img.kingsilk.net/",   // 图片地址
//         rootPath: rootPath,
//         apiPath: apiPath,
//         wxloginPath: wxloginPath,
//         token: token,
//         oauthPath: oauthPath
//     };
//
//     window.appConfig = appConfig;
// })();