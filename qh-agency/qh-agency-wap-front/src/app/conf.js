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
    const apiPort = "14400";              //api端口，支付默认api端口

    /////////////////////////////////这是分割线
    let domain = "//kingsilk.net";
    let apiPath = domain + `/agency/rs/local/${apiPort}/api`;
    let pfApiPath = domain + `/platform/rs/local/11300/api`;

    // let apiPath = domain + `/rs/api`;
    let shopUrl = domain + `/shop/local/?_ddnsPort=${devPort}#/`;
    let shopPath = domain + `/shop/rs/local/${apiPort}/api`;

    let oauthPath = `//kingsilk.net/oauth2/rs/local/11300`;
    // let wxloginPath = `//kingsilk.net/oauth/local/17100/#/oauth/authorize?`;

    let wxloginPath = `//kingsilk.net/oauth2/wap/local/11300/#/oauth/authorize?`;

    let payUrl = domain + `/pay/local/?_ddnsPort=16300#/?id=`;

    let payBackUrl = domain + `/agency/local/?_ddnsPort=${devPort}#/`;


    let brandAppId = `qh-agency-wap-front.${devPort}.brandAppId`;
    let token = `qh.${devPort}.token`;

    // let loginBackUrl = `https://agency.kingsilk.net/local/${devPort}/#/wxLogin`;

    return Object.assign(baseObj, {
        pfApiPath: pfApiPath,
        shopPath: shopPath,
        shopUrl: shopUrl,
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
    let apiPath = domain + `/agency/rs/local/${apiPort}/api`;
    let shopUrl = domain + `/shop/local/?_ddnsPort=${devPort}#/`;
    let shopPath = domain + `/shop/rs/local/${apiPort}/api`;
    let pfApiPath = domain + `/platform/rs/local/${apiPort}/api`;

    // let oauthPath = `//kingsilk.net/oauth/local/${apiPort}/rs`;
    // let wxloginPath = `${oauthPath}/oauth/authorize?`;
    let oauthPath = `//kingsilk.net/oauth2/rs/local/${apiPort}`;
    // let wxloginPath = `//kingsilk.net/oauth/local/17100/#/oauth/authorize?`;
    let wxloginPath = `//kingsilk.net/oauth2/wap/local/${devPort}/#/oauth/authorize?`;


    let payUrl = domain + `/pay/local/?_ddnsPort=${devPort}#/?id=`;
    let payBackUrl = domain + `/agency/local/?_ddnsPort=${devPort}#/`;

    let brandAppId = `qh-agency-wap-front.${devPort}.brandAppId`;
    let token = `qh.${devPort}.token`;
    // let loginBackUrl = `https://agency.kingsilk.net/local/${devPort}/#/loginTime`;

    return Object.assign(baseObj, {
        pfApiPath: pfApiPath,
        shopPath: shopPath,
        shopUrl: shopUrl,
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
    let apiPath = domain + `/agency/rs/local/${apiPort}/api`;
    let shopUrl = domain + `/shop/local/?_ddnsPort=${devPort}#/`;
    let shopPath = domain + `/shop/rs/local/${apiPort}/api`;
    let pfApiPath = domain + `/platform/rs/local/${apiPort}/api`;

    // let oauthPath = `//kingsilk.net/oauth/local/11300/rs`;
    // let wxloginPath = `${oauthPath}/oauth/authorize?`;
    let oauthPath = `//kingsilk.net/oauth2/rs/local/${apiPort}`;
    // let wxloginPath = `//kingsilk.net/oauth/local/17100/#/oauth/authorize?`;
    let wxloginPath = `//kingsilk.net/oauth2/wap/local/${devPort}/#/oauth/authorize?`;


    let payUrl = domain + `/pay/local/?_ddnsPort=${devPort}#/?id=`;
    let payBackUrl = domain + `/agency/local/?_ddnsPort=${devPort}#/`;

    let brandAppId = `qh-agency-wap-front.${devPort}.brandAppId`;
    let token = `qh.${devPort}.token`;

    // let loginBackUrl = `https://agency.kingsilk.net/local/${devPort}/#/loginTime`;

    return Object.assign(baseObj, {
        pfApiPath: pfApiPath,
        shopPath: shopPath,
        shopUrl: shopUrl,
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
    let apiPath = domain + `/agency/rs/local/${apiPort}/api`;
    let shopUrl = domain + `/shop/local/?_ddnsPort=${devPort}#/`;
    let shopPath = domain + `/shop/rs/local/${apiPort}/api`;
    let pfApiPath = domain + `/platform/rs/local/${apiPort}/api`;

    // let oauthPath = `//kingsilk.net/oauth/local/${apiPort}/rs`;
    // let wxloginPath = `${oauthPath}/oauth/authorize?`;
    let oauthPath = `//kingsilk.net/oauth2/rs/local/${apiPort}`;
    // let wxloginPath = `//kingsilk.net/oauth/local/17100/#/oauth/authorize?`;
    let wxloginPath = `//kingsilk.net/oauth2/wap/local/${devPort}/#/oauth/authorize?`;
    let payUrl = domain + `/pay/local/?_ddnsPort=${devPort}#/?id=`;
    let payBackUrl = domain + `/agency/local/?_ddnsPort=${devPort}#/`;

    let brandAppId = `qh-agency-wap-front.${devPort}.brandAppId`;
    let token = `qh.${devPort}.token`;

    // let loginBackUrl = `https://agency.kingsilk.net/local/${devPort}/#/loginTime`;

    return Object.assign(baseObj, {
        pfApiPath: pfApiPath,
        shopPath: shopPath,
        shopUrl: shopUrl,
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
        // loginBackUrl: loginBackUrl
    });
}


/**
 * 开发用的测试环境。
 */
function prod() {

    /////////////////////////////////这是分割线
    let domain = "//kingsilk.net";
    let apiPath = domain + `/agency/rs/api`;
    let shopUrl = domain + `/shop/?showwxpaytitle=1#/`;
    let shopPath = domain + `/shop/rs/api`;
    let pfApiPath = domain + `/platform/rs/api`;

    // let oauthPath = `//kingsilk.net/oauth/rs`;
    // let wxloginPath = `${oauthPath}/oauth/authorize?`;
    let oauthPath = `//kingsilk.net/oauth2/rs`;
    // let wxloginPath = `//kingsilk.net/oauth/local/17100/#/oauth/authorize?`;
    let wxloginPath = `//kingsilk.net/oauth2/wap/?showwxpaytitle=1#/oauth/authorize?`;


    let payUrl = `https://kingsilk.net/pay/?showwxpaytitle=1#/?id=`;
    let payBackUrl = `https://kingsilk.net/agency/?showwxpaytitle=1#/`;

    let brandAppId = `qh-agency-wap-front.brandAppId`;
    let token = `qh.token`;

    // let loginBackUrl = `https://agency.kingsilk.net/#/loginTime`;

    return Object.assign(baseObj, {
        pfApiPath: pfApiPath,
        shopPath: shopPath,
        shopUrl: shopUrl,
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
