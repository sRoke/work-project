var env = __ENV__.toUpperCase();

var baseObj = {
    app: __APP__,
    version: __VERSION__,
    imgView1: "?imageView2/1/w/500/h/200",  // 对图片进行缩放(首页)
    imgView2: "?imageView2/2/w/100/h/100",  // 对图片进行缩放(用户中心)
    imgUpload: "/common/uploadImgS",
    title: "钱皇平台"
};

/**
 * 开发用的测试环境。
 */
function dev() {

    const devPort = "14300";              //开发端口
    const apiPort = "14300";              // api端口，支付默认api端口

    /////////////////////////////////这是分割线
    let oauthPath = `//kingsilk.net/oauth2/rs/local/${apiPort}/api`;
    let wx4jPath =`//kingsilk.net/wx/rs/local/14300/api`;

    let brandAppId = `qh-oauth-wap-front.${devPort}.brandAppId`;
    let token = `qh.${devPort}.token`;

    return Object.assign(baseObj, {
        oauthPath: oauthPath,
        wx4jPath: wx4jPath,
        imgUrl: "//o96iiewkd.qnssl.com/",   // 图片地址
        // cdnUrl: "//o96iczjtp.qnssl.com/qh-admin-front/prod/",   // cdn地址访问本地图片
        imgView1: "?imageView2/2/w/100/h/100",// 对图片进行缩放
        brandAppId: brandAppId,
        token: token
    });
}

/**
 * 开发用的测试环境。
 */
function test12() {

    const devPort = "11200";              //开发端口
    const apiPort = "11200";              //api端口，支付默认api端口
    /////////////////////////////////这是分割线


    let oauthPath = `//kingsilk.net/oauth2/rs/local/${apiPort}/api`;
    let wx4jPath =`//kingsilk.net/wx/rs/local/${apiPort}/api`;

    let brandAppId = `qh-oauth-wap-front.${devPort}.brandAppId`;
    let token = `qh.${devPort}.token`;

    return Object.assign(baseObj, {
        wx4jPath: wx4jPath,
        oauthPath: oauthPath,               // oauth
        imgUrl: "//o96iiewkd.qnssl.com/",   // 图片地址
        // cdnUrl: "//o96iczjtp.qnssl.com/qh-admin-front/prod/",   // cdn地址访问本地图片
        imgView1: "?imageView2/2/w/100/h/100",// 对图片进行缩放
        brandAppId: brandAppId,
        token: token
    });
}


/**
 * 开发用的测试环境。
 */
function test13() {

    const devPort = "11300";              //开发端口
    const apiPort = "11300";              //api端口，支付默认api端口
    /////////////////////////////////这是分割线
    let oauthPath = `//kingsilk.net/oauth2/rs/local/${apiPort}/api`;
    let wx4jPath =`//kingsilk.net/wx/rs/local/${apiPort}/api`;

    let brandAppId = `qh-oauth-wap-front.${devPort}.brandAppId`;
    let token = `qh.${devPort}.token`;

    return Object.assign(baseObj, {
        wx4jPath: wx4jPath,
        oauthPath: oauthPath,               // oauth
        imgUrl: "//o96iiewkd.qnssl.com/",   // 图片地址
        // cdnUrl: "//o96iczjtp.qnssl.com/qh-admin-front/prod/",   // cdn地址访问本地图片
        imgView1: "?imageView2/2/w/100/h/100",// 对图片进行缩放
        brandAppId: brandAppId,
        token: token
    });
}

/**
 * 开发用的测试环境。
 */
function test14() {

    const devPort = "11400";              //开发端口
    const apiPort = "11400";              //api端口，支付默认api端口
    /////////////////////////////////这是分割线
    let oauthPath = `//kingsilk.net/oauth2/rs/local/${apiPort}/api`;
    let wx4jPath =`//kingsilk.net/wx/rs/local/${apiPort}/api`;

    let brandAppId = `qh-oauth-wap-front.${devPort}.brandAppId`;
    let token = `qh.${devPort}.token`;

    return Object.assign(baseObj, {
        wx4jPath: wx4jPath,
        oauthPath: oauthPath,               // oauth
        imgUrl: "//o96iiewkd.qnssl.com/",   // 图片地址
        // cdnUrl: "//o96iczjtp.qnssl.com/qh-admin-front/prod/",   // cdn地址访问本地图片
        imgView1: "?imageView2/2/w/100/h/100",// 对图片进行缩放
        brandAppId: brandAppId,
        token: token
    });
}


/**
 * 开发用的测试环境。
 */
function prod() {

    /////////////////////////////////这是分割线


    let oauthPath = `//kingsilk.net/oauth2/rs/api`;
    let wx4jPath =`//kingsilk.net/wx/rs/api`;

    let brandAppId = `qh-oauth-wap-front.brandAppId`;
    let token = `qh.token`;
    let domain = "//kingsilk.net";
    let rootPath = domain + `/oauth/rs`;
    let apiPath = rootPath + `/api`;
    let rootUrl = `/`;
    return Object.assign(baseObj, {
        wx4jPath:wx4jPath,
        rootPath: rootPath,
        apiPath: apiPath,
        oauthPath: oauthPath,               // oauth
        brandAppId: brandAppId,
        token: token,
        rootUrl: rootUrl,
        maxSize: 8,                         // 页数多少多少翻页数
        pageSize: 10,                       // 每页多少条数据
        imgUrl: "//o96iiewkd.qnssl.com/",   // 图片地址
        cdnUrl: "//o96iczjtp.qnssl.com/qh-admin-front/prod/",   // cdn地址访问本地图片
        tokenImg: apiPath + "/common/generatorToken",
        imgView1: "?imageView2/2/w/100/h/100",// 对图片进行缩放
    });
}


function appConfigFactory() {

    let appConfig = null;

    if (env === "TEST13") {
        appConfig = test13();
    } else if (env === "TEST14") {
        appConfig = test14();
    } else if (env === "TEST12") {
        appConfig = test12();
    } else if (env === "PROD") {
        appConfig = prod();
    } else {
        appConfig = dev();
    }

    return appConfig;
}

export default appConfigFactory();
