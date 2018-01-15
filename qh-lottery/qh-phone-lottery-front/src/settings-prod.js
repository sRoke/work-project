// 生产环境的配置
angular.module('qh-phone-lottery-front').factory('appConfig', function () {
    var domain = "//kingsilk.net";
    var rootPath = domain + "/qh/phone/phoneLottery/?showwxpaytitle=1";
    var apiPath = domain + "/qh/mall/api";
    var appVersion = "2.0.0";
    return {
        share: "https:" + rootPath + "#/share",
        apiPath: apiPath,
        appVersion: appVersion,
        maxSize: 5,  // 页数多少多少翻页数
        pageSize: 10, // 每页多少条数据
        imgUrl: "//o96iiewkd.qnssl.com/",   // 图片地址
        //cdnUrl: "//o96iczjtp.qnssl.com/prod/qh-phone-lottery-front/" + appVersion + "/",// cdn地址访问本地图片
        imgView1: "?imageView2/2/w/500/h/500",// 对图片进行缩放(首页)
        imgView2: "?imageView2/2/w/100/h/100",// 对图片进行缩放(用户中心)
        imgUpload: "/common/uploadImgS",
        tokenImg: apiPath + "/common/generatorToken"
    };
});