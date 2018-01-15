
//
// // 开发环境的配置
// (function () {
//     var devPort = "11400";
//     var oauthPath = '//kingsilk.net/oauth/local/11400/rs';
//     var wxloginPath = oauthPath + '/oauth/authorize?';
//     // var token = 'qh-passistance-wap-front.17100.userInfo';
//     var token = 'qh-assistance-wap-front.' + devPort + '.userInfo';
//     var appConfig = {
//
//         wxloginPath: wxloginPath,
//         token: token,
//         imgUrl: "//img.kingsilk.net/",   // 图片地址
//         oauthPath: oauthPath
//     };
//
//     window.appConfig = appConfig;
//
//
//     // 测试环境的配置
//     angular.module('qh-assistance-wap-front').factory('appConfig', function () {
//         var devPort = "11400";              //开发端口
//         var apiPort = "11400";              //api端口，支付默认api端口
//         // // -------------------------- 测试环境 api
//         var domain = "//kingsilk.net";
//         var rootPath = domain + "/qh/assistance/local/?_ddnsPort="+devPort;   // 如果后台开发人员使用本地启动的API，仅仅修改此行的端口即可，不要提交修改。
//         var apiPath = domain + "/qh/mall/local/"+apiPort+"/api";
//         var appVersion = "1.0.0";
//         var token = 'qh-assistance-wap-front.'+devPort+'.token';
//         return {
//             rootPath:rootPath,
//             apiPath: apiPath,
//             imgUrl: "//img.kingsilk.net/",   // 图片地址
//             token: token,
//         };
//     });
// })();
// 开发环境的配置
(function () {
    var devPort = "11400";
    var oauthPath = '//kingsilk.net/oauth/rs';
    var wxloginPath = oauthPath + '/oauth/authorize?';
    // var token = 'qh-passistance-wap-front.17100.userInfo';
    var token = 'qh-assistance-wap-front.' + devPort + '.userInfo';
    var appConfig = {

        wxloginPath: wxloginPath,
        token: token,
        imgUrl: "//img.kingsilk.net/",   // 图片地址
        oauthPath: oauthPath
    };

    window.appConfig = appConfig;



    angular.module('qh-assistance-wap-front').factory('appConfig', function () {
        var devPort = "11400";              //开发端口
        var apiPort = "11400";              //api端口，支付默认api端口
        // // -------------------------- 测试环境 api
        var domain = "//kingsilk.net";
        var rootPath = domain + "/qh/passistance/local/?_ddnsPort=" + devPort;   // 如果后台开发人员使用本地启动的API，仅仅修改此行的端口即可，不要提交修改。
        var apiPath = domain + "/qh/mall/local/" + apiPort + "/api";
        var appVersion = "1.0.0";
        var token = 'qh-assistance-wap-front.' + devPort + '.userInfo';
        return {
            rootPath: rootPath,
            apiPath: apiPath,
            imgUrl: "//img.kingsilk.net/",   // 图片地址
            token: token,
        };
    });
})();

