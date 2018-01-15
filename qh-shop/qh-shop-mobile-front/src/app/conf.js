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

    const devPort = "16300";              //开发端口
    const apiPort = "11400";              //api端口，支付默认api端口
    /////////////////////////////////这是分割线
    let domain = "//kingsilk.net";
    let rootUrl = `/shop/local/${devPort}/`;
    let agencyPath = domain + `/agency/rs/local/${apiPort}/api`;
    let apiPath = domain + `/shop/rs/local/${apiPort}/api`;
    let agencyRootUrl = domain + `/agency/local/?_ddnsPort=${devPort}#/`;
    let pfApiPath = domain + `/platform/rs/local/11400/api`;
    let wxPath = `//kingsilk.net/wx/rs/local/11400/api`;
    let shopUrl = domain + `/shop/local/?_ddnsPort=${devPort}#/`;
    // let oauthPath = `//kingsilk.net/oauth/local/11400/rs`;
    // let wxloginPath = `${oauthPath}/oauth/authorize?`;
    let yunApiPath = `//kingsilk.net/yun/local/11400/rs/api`;
    let oauthPath = `//kingsilk.net/oauth2/rs/local/11400`;
    let wxloginPath = `//kingsilk.net/oauth2/wap/local/11400/#/oauth/authorize?`;

    let payUrl = domain + `/pay/local/?_ddnsPort=16300#/?id=`;

    let payBackUrl = `https://kingsilk.net/shop/mobile/local/?_ddnsPort=${devPort}#/`;
    let payJsdkPath = "//kingsilk.net/pay/rs/local/11400/api";

    let brandAppId = `qh-shop-mobile-front.${devPort}.brandAppId`;
    let token = `qh.${devPort}.token`;
    let authorSet = `qh-shop-mobile-front.${devPort}.authorSet`;
    // let loginBackUrl = `https://agency.kingsilk.net/shop/local/${devPort}/#/wxLogin`;

    return Object.assign(baseObj, {
        pfApiPath: pfApiPath,
        agencyRootUrl: agencyRootUrl,
        shopUrl:shopUrl,
        payJsdkPath: payJsdkPath,
        agencyPath: agencyPath,
        rootUrl: rootUrl,                   // FIXME 有用到？
        apiPath: apiPath,
        maxSize: 8,                         // 页数多少多少翻页数
        pageSize: 10,                       // 每页多少条数据
        oauthPath: oauthPath,               // oauth
        wxloginPath: wxloginPath,           // weixin登录的url
        imgUrl: "//o96iiewkd.qnssl.com/",   // 图片地址
        // cdnUrl: "//o96iczjtp.qnssl.com/qh-admin-front/prod/",   // cdn地址访问本地图片
        imgView1: "?imageView2/2/w/100/h/100",// 对图片进行缩放
        tokenImg: apiPath + "/common/generatorToken",
        payUrl: payUrl,             //支付页面地址
        payBackUrl: payBackUrl,      //支付成功回调地址
        brandAppId: brandAppId,
        token: token,
        wxPath:wxPath,
        yunApiPath:yunApiPath,
        authorSet:authorSet,
        // wxComAppId: wxComAppId,
        // wxMpAppId: wxMpAppId
        // loginBackUrl: loginBackUrl
    });
}

/**
 * 开发用的测试环境。
 */
function test12() {

    const devPort = "11200";              //开发端口
    const apiPort = "11200";              //api端口，支付默认api端口
    /////////////////////////////////这是分割线
    let domain = "//kingsilk.net";
    let rootUrl = `/shop/local/${devPort}/`;
    let apiPath = domain + `/shop/rs/local/${apiPort}/api`;
    let agencyRootUrl = domain + `/agency/local/?_ddnsPort=${devPort}#/`;
    let agencyPath = domain + `/agency/rs/local/${apiPort}/api`;
    let pfApiPath = domain + `/platform/rs/local/${apiPort}/api`;
    let wxPath = `//kingsilk.net/wx/rs/local/11300/api`;
    let shopUrl = domain + `/shop/local/?_ddnsPort=${devPort}#/`;
    // let oauthPath = `//kingsilk.net/oauth/local/${apiPort}/rs`;
    // let wxloginPath = `${oauthPath}/oauth/authorize?`;
    let oauthPath = `//kingsilk.net/oauth2/rs/local/${apiPort}`;
    let wxloginPath = `//kingsilk.net/oauth2/wap/local/${devPort}/#/oauth/authorize?`;
    let wxComAppId = 'wxcee33ae0776a9a96';
    let wxMpAppId = 'WX7CC0B96ADD4254B1';

    let payUrl = domain + `/pay/local/?_ddnsPort=${devPort}#/?id=`;
    let payBackUrl = `https://kingsilk.net/shop/mobile/local/?_ddnsPort=${devPort}#/`;
    let payJsdkPath = "//kingsilk.net/pay/rs/local/11200/api";

    let brandAppId = `qh-shop-mobile-front.${devPort}.brandAppId`;
    let token = `qh.${devPort}.token`;
    // let loginBackUrl = `https://agency.kingsilk.net/shop/local/${devPort}/#/loginTime`;

    return Object.assign(baseObj, {
        pfApiPath: pfApiPath,
        agencyRootUrl: agencyRootUrl,
        payJsdkPath: payJsdkPath,
        agencyPath: agencyPath,
        shopUrl:shopUrl,
        rootUrl: rootUrl,                   // FIXME 有用到？
        apiPath: apiPath,
        maxSize: 8,                         // 页数多少多少翻页数
        pageSize: 10,                       // 每页多少条数据
        oauthPath: oauthPath,               // oauth
        wxloginPath: wxloginPath,           // weixin登录的url
        imgUrl: "//o96iiewkd.qnssl.com/",   // 图片地址
        cdnUrl: "//o96iczjtp.qnssl.com/qh-admin-front/prod/",   // cdn地址访问本地图片
        imgView1: "?imageView2/2/w/100/h/100",// 对图片进行缩放
        tokenImg: apiPath + "/common/generatorToken",
        payUrl: payUrl,             //支付页面地址
        payBackUrl: payBackUrl,      //支付成功回调地址
        brandAppId: brandAppId,
        token: token,
        wxComAppId: wxComAppId,
        wxPath:wxPath,
        wxMpAppId: wxMpAppId
        // loginBackUrl: loginBackUrl
    });
}


/**
 * 开发用的测试环境。
 */
function test13() {

    const devPort = "11300";              //开发端口
    const apiPort = "11300";              //api端口，支付默认api端口
    /////////////////////////////////这是分割线
    let domain = "//kingsilk.net";
    let rootUrl = `/shop/local/${devPort}/`;
    let apiPath = domain + `/shop/rs/local/${apiPort}/api`;
    let agencyRootUrl = domain + `/agency/local/?_ddnsPort=${devPort}#/`;
    let agencyPath = domain + `/agency/rs/local/${apiPort}/api`;
    let pfApiPath = domain + `/platform/rs/local/${apiPort}/api`;
    let shopUrl = domain + `/shop/local/?_ddnsPort=${devPort}#/`;
    let wxPath = `//kingsilk.net/wx/rs/local/11300/api`;
    // let oauthPath = `//kingsilk.net/oauth/local/11300/rs`;
    // let wxloginPath = `${oauthPath}/oauth/authorize?`;
    let oauthPath = `//kingsilk.net/oauth2/rs/local/${apiPort}`;
    let wxloginPath = `//kingsilk.net/oauth2/wap/local/${devPort}/#/oauth/authorize?`;
    let wxComAppId = 'wxcee33ae0776a9a96';
    let wxMpAppId = 'WX7CC0B96ADD4254B1';

    let payUrl = domain + `/pay/local/?_ddnsPort=${devPort}#/?id=`;
    let payBackUrl = `https://kingsilk.net/shop/mobile/local/?_ddnsPort=${devPort}#/`;
    let payJsdkPath = "//kingsilk.net/pay/rs/local/11300/api";

    let brandAppId = `qh-shop-mobile-front.${devPort}.brandAppId`;
    let token = `qh.${devPort}.token`;

    // let loginBackUrl = `https://agency.kingsilk.net/shop/local/${devPort}/#/loginTime`;

    return Object.assign(baseObj, {
        pfApiPath: pfApiPath,
        agencyRootUrl: agencyRootUrl,
        payJsdkPath: payJsdkPath,
        agencyPath: agencyPath,
        shopUrl:shopUrl,
        rootUrl: rootUrl,                   // FIXME 有用到？
        apiPath: apiPath,
        maxSize: 8,                         // 页数多少多少翻页数
        pageSize: 10,                       // 每页多少条数据
        oauthPath: oauthPath,               // oauth
        wxloginPath: wxloginPath,           // weixin登录的url
        imgUrl: "//o96iiewkd.qnssl.com/",   // 图片地址
        cdnUrl: "//o96iczjtp.qnssl.com/qh-admin-front/prod/",   // cdn地址访问本地图片
        tokenImg: apiPath + "/common/generatorToken",
        imgView1: "?imageView2/2/w/100/h/100",// 对图片进行缩放
        payUrl: payUrl,             //支付页面地址
        payBackUrl: payBackUrl,      //支付成功回调地址
        brandAppId: brandAppId,
        token: token,
        wxComAppId: wxComAppId,
        wxPath:wxPath,
        wxMpAppId: wxMpAppId
        // loginBackUrl: loginBackUrl
    });
}

/**
 * 开发用的测试环境。
 */
function test14() {

    const devPort = "11400";              //开发端口
    const apiPort = "11400";              //api端口，支付默认api端口
    /////////////////////////////////这是分割线
    let domain = "//kingsilk.net";
    let rootUrl = `/shop/local/${devPort}/`;
    let apiPath = domain + `/shop/rs/local/${apiPort}/api`;
    let agencyRootUrl = domain + `/agency/local/?_ddnsPort=${devPort}#/`;
    let agencyPath = domain + `/agency/rs/local/${apiPort}/api`;
    let pfApiPath = domain + `/platform/rs/local/${apiPort}/api`;
    let shopUrl = domain + `/shop/local/?_ddnsPort=${devPort}#/`;
    let wxPath = `//kingsilk.net/wx/rs/local/11300/api`;
    let oauthPath = `//kingsilk.net/oauth2/rs/local/${apiPort}`;
    let wxloginPath = `//kingsilk.net/oauth2/wap/local/${devPort}/#/oauth/authorize?`;
    let wxComAppId = 'wxcee33ae0776a9a96';
    let wxMpAppId = 'WX7CC0B96ADD4254B1';

    let payUrl = domain + `/pay/local/?_ddnsPort=${devPort}#/?id=`;
    let payBackUrl = `https://kingsilk.net/shop/mobile/local/?_ddnsPort=${devPort}#/`;
    let payJsdkPath = "//kingsilk.net/pay/local/11400/rs/api";

    let brandAppId = `qh-shop-mobile-front.${devPort}.brandAppId`;
    let token = `qh.${devPort}.token`;

    // let loginBackUrl = `https://agency.kingsilk.net/shop/local/${devPort}/#/loginTime`;

    return Object.assign(baseObj, {
        pfApiPath: pfApiPath,
        agencyRootUrl: agencyRootUrl,
        payJsdkPath: payJsdkPath,
        agencyPath: agencyPath,
        shopUrl:shopUrl,
        rootUrl: rootUrl,                   // FIXME 有用到？
        apiPath: apiPath,
        maxSize: 8,                         // 页数多少多少翻页数
        pageSize: 10,                       // 每页多少条数据
        oauthPath: oauthPath,               // oauth
        wxloginPath: wxloginPath,           // weixin登录的url
        imgUrl: "//o96iiewkd.qnssl.com/",   // 图片地址
        cdnUrl: "//o96iczjtp.qnssl.com/qh-admin-front/prod/",   // cdn地址访问本地图片
        tokenImg: apiPath + "/common/generatorToken",
        imgView1: "?imageView2/2/w/100/h/100",// 对图片进行缩放
        payUrl: payUrl,             //支付页面地址
        payBackUrl: payBackUrl,      //支付成功回调地址
        brandAppId: brandAppId,
        token: token,
        wxComAppId: wxComAppId,
        wxMpAppId: wxMpAppId,
        wxPath:wxPath
        // loginBackUrl: loginBackUrl
    });
}


/**
 * 开发用的测试环境。
 */
function prod() {

    /////////////////////////////////这是分割线
    let domain = "//kingsilk.net";
    let rootUrl = `/shop/mobile/?showwxpaytitle=1#/`;
    let apiPath = domain + `/shop/rs/api`;
    let agencyRootUrl = domain + `/agency/?showwxpaytitle=1#/`;
    let agencyPath = domain + `/agency/rs/api`;
    let pfApiPath = domain + `/platform/rs/api`;
    let wxPath = `//kingsilk.net/wx/rs/api`;
    let oauthPath = `//kingsilk.net/oauth2/rs`;
    let wxloginPath = `//kingsilk.net/oauth2/wap/?showwxpaytitle=1#/oauth/authorize?`;
    let wxComAppId = 'wxcee33ae0776a9a96';
    let wxMpAppId = 'wxbba6def439fc0bd0';
    let shopUrl = domain + `/shop/?showwxpaytitle=1#/`;
    let payUrl = domain + `/pay/?showwxpaytitle=1#/?id=`;

    // let payUrl = `https://kingsilk.net/pay/?showwxpaytitle=1#/scanReceive?id=`;
    // let payUrl = `https://pay.kingsilk.net/qh/pay/?id=`;
    // let payBackUrl = `https://kingsilk.net/shop/?showwxpaytitle=1#/`;
    let payBackUrl = `https://kingsilk.net/shop/mobile/?showwxpaytitle=1#/`;
    let payJsdkPath = "//kingsilk.net/pay/rs/api";

    let brandAppId = `qh-shop-mobile-front.brandAppId`;
    let token = `qh.token`;

    // let loginBackUrl = `https://agency.kingsilk.net/#/loginTime`;

    return Object.assign(baseObj, {
        pfApiPath: pfApiPath,
        agencyRootUrl: agencyRootUrl,
        payJsdkPath: payJsdkPath,
        agencyPath: agencyPath,
        shopUrl:shopUrl,
        rootUrl: rootUrl,                   // FIXME 有用到？
        apiPath: apiPath,
        maxSize: 8,                         // 页数多少多少翻页数
        pageSize: 10,                       // 每页多少条数据
        oauthPath: oauthPath,               // oauth
        wxloginPath: wxloginPath,           // weixin登录的url
        imgUrl: "//o96iiewkd.qnssl.com/",   // 图片地址
        cdnUrl: "//o96iczjtp.qnssl.com/qh-admin-front/prod/",   // cdn地址访问本地图片
        tokenImg: apiPath + "/common/generatorToken",
        imgView1: "?imageView2/2/w/100/h/100",// 对图片进行缩放
        payUrl: payUrl,             //支付页面地址
        payBackUrl: payBackUrl,      //支付成功回调地址
        brandAppId: brandAppId,
        token: token,
        wxPath: wxPath,
        wxComAppId: wxComAppId,
        wxMpAppId: wxMpAppId
        // loginBackUrl: loginBackUrl,
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
