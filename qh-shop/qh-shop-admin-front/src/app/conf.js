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


    const devPort = "17100";            //开发端口
    const apiPort = "16000";            //api端口


    let wxPath =`//kingsilk.net/wx/rs/local/11300/api`;
    /////////////////////////////////////////////分割线
    let domain = "//kingsilk.net";
    let apiPath = domain + `/shop/rs/local/${apiPort}/api`;
    let pfApiPath = domain + `/platform/rs/local/11300/api`;
    let payApipath=domain+`/pay/rs/local/${apiPort}/api`;
    //kingsilk.net/yun/local/16700/rs/api
    let yunApiPath = `//kingsilk.net/yun/local/11400/rs/api`;

    let oauthPath = `//kingsilk.net/oauth2/rs/local/11300`;
    let wxloginPath = `//kingsilk.net/oauth2/wap/local/11300/#/oauth/authorize?`;

    let linkPath=`https://kingsilk.net/shop/local/${devPort}/#/brandApp/`;

    let brandAppId = `qh-shop-admin-front.${devPort}.brandAppId`;
    let token = `qh.${devPort}.token`;
    let authorSet = `qh-shop-admin-front.${devPort}.authorSet`;
    return Object.assign(baseObj, {
        pfApiPath:pfApiPath,
        apiPath: apiPath,
        oauthPath: oauthPath,               // oauth
        wxloginPath: wxloginPath,           // weixin登录的url
        maxSize: 8,                         // 页数多少多少翻页数
        pageSize: 15,                       // 每页多少条数据
        imgUrl: "//img.kingsilk.net/",      // 图片地址
        brandAppId: brandAppId,
        token: token,
        yunApiPath: yunApiPath,
        payApipath:payApipath,
        authorSet: authorSet,
        wxPath:wxPath,
        linkPath:linkPath,
    });
}

/**
 * 测试环境13
 */
function test13() {

    const devPort = "11300";            //开发端口
    const apiPort = "11300";            //api端口
    let wxPath =`//kingsilk.net/wx/rs/local/${apiPort}/api`;
    /////////////////////////////////////////////分割线
    let domain = "//kingsilk.net";
    let apiPath = domain + `/shop/rs/local/${apiPort}/api`;
    let pfApiPath = domain + `/platform/rs/local/${apiPort}/api`;
    let payApipath=domain+`/pay/rs/local/${apiPort}/api`;
    let yunApiPath = `//kingsilk.net/yun/local/11400/rs/api`;

    let linkPath=`https://kingsilk.net/shop/local/${devPort}/#/brandApp/`;

    let oauthPath = `//kingsilk.net/oauth2/rs/local/${apiPort}`;
    let wxloginPath = `//kingsilk.net/oauth2/wap/local/${devPort}/#/oauth/authorize?`;

    let brandAppId = `qh-shop-admin-front.${devPort}.brandAppId`;
    let token = `qh.${devPort}.token`;
    let authorSet = `qh-shop-admin-front.${devPort}.authorSet`;

    return Object.assign(baseObj, {
        pfApiPath:pfApiPath,
        apiPath: apiPath,
        oauthPath: oauthPath,               // oauth
        wxloginPath: wxloginPath,           // weixin登录的url
        maxSize: 8,                         // 页数多少多少翻页数
        pageSize: 15,                       // 每页多少条数据
        imgUrl: "//img.kingsilk.net/",      // 图片地址
        brandAppId: brandAppId,
        token: token,
        authorSet: authorSet,
        yunApiPath: yunApiPath,
        payApipath:payApipath,
        wxPath:wxPath,
        linkPath:linkPath,
    });

}


/**
 * 测试环境12
 */
function test12() {

    const devPort = "11200";            //开发端口
    const apiPort = "11200";            //api端口
    let wxPath =`//kingsilk.net/wx/rs/local/${apiPort}/api`;
    /////////////////////////////////////////////分割线
    let domain = "//kingsilk.net";
    let apiPath = domain + `/shop/rs/local/${apiPort}/api`;
    let pfApiPath = domain + `/platform/rs/local/${apiPort}/api`;
    let payApipath=domain+`/pay/rs/local/${apiPort}/api`;
    let yunApiPath = `//kingsilk.net/yun/local/${apiPort}/rs/api`;
    let linkPath=`https://kingsilk.net/shop/local/${devPort}/#/brandApp/`;

    let oauthPath = `//kingsilk.net/oauth2/rs/local/${apiPort}`;
    let wxloginPath = `//kingsilk.net/oauth2/wap/local/${devPort}/#/oauth/authorize?`;

    let brandAppId = `qh-shop-admin-front.${devPort}.brandAppId`;
    let token = `qh.${devPort}.token`;
    let authorSet = `qh-shop-admin-front.${devPort}.authorSet`;

    return Object.assign(baseObj, {
        pfApiPath:pfApiPath,
        apiPath: apiPath,
        oauthPath: oauthPath,               // oauth
        wxloginPath: wxloginPath,           // weixin登录的url
        maxSize: 8,                         // 页数多少多少翻页数
        pageSize: 15,                       // 每页多少条数据
        imgUrl: "//img.kingsilk.net/",      // 图片地址
        brandAppId: brandAppId,
        token: token,
        yunApiPath: yunApiPath,
        authorSet: authorSet,
        payApipath:payApipath,
        wxPath:wxPath,
        linkPath:linkPath,
    });

}


/**
 * 模拟测试线上生产环境
 */
function test14() {
    const devPort = "11400";            //开发端口
    const apiPort = "11400";            //api端口
    let wxPath =`//kingsilk.net/wx/rs/local/${apiPort}/api`;
    /////////////////////////////////////////////分割线
    let domain = "//kingsilk.net";
    let apiPath = domain + `/shop/rs/local/${apiPort}/api`;
    let pfApiPath = domain + `/platform/rs/local/${apiPort}/api`;
    let payApipath=domain+`/pay/rs/local/${apiPort}/api`;
    let yunApiPath = `//kingsilk.net/yun/local/${apiPort}/rs/api`;

    let linkPath=`https://kingsilk.net/shop/local/${devPort}/#/brandApp/`;

    let oauthPath = `//kingsilk.net/oauth2/rs/local/${apiPort}`;
    let wxloginPath = `//kingsilk.net/oauth2/wap/local/${devPort}/#/oauth/authorize?`;

    let brandAppId = `qh-shop-admin-front.${devPort}.brandAppId`;
    let token = `qh.${devPort}.token`;
    let authorSet = `qh-shop-admin-front.${devPort}.authorSet`;

    return Object.assign(baseObj, {
        pfApiPath:pfApiPath,
        apiPath: apiPath,
        oauthPath: oauthPath,               // oauth
        wxloginPath: wxloginPath,           // weixin登录的url
        maxSize: 8,                         // 页数多少多少翻页数
        pageSize: 15,                       // 每页多少条数据
        imgUrl: "//img.kingsilk.net/",      // 图片地址
        brandAppId: brandAppId,
        token: token,
        payApipath:payApipath,
        authorSet: authorSet,
        yunApiPath: yunApiPath,
        wxPath:wxPath,
        linkPath:linkPath,
    });
}


/**
 * 线上生产环境
 */
function prod() {

    /////////////////////////////////////////////分割线
    let domain = "//kingsilk.net";
    let rootUrl = `/shop/`;
    let rootPath = domain + rootUrl;
    let apiPath = domain + `/shop/rs/api`;

    let wxPath =`//kingsilk.net/wx/rs/api`;
    let pfApiPath = domain + `/platform/rs/api`;

    let oauthPath = `//kingsilk.net/oauth2/rs`;
    let wxloginPath = `//kingsilk.net/oauth2/wap/?showwxpaytitle=1#/oauth/authorize?`;
    let payApipath=domain+`/pay/rs/api`;
    let linkPath=`https://kingsilk.net/shop/?showwxpaytitle=1#/brandApp/`;

    let yunApiPath = `//kingsilk.net/yun/rs/api`;

    let brandAppId = `qh-shop-admin-front.brandAppId`;
    let token = `qh.token`;
    let authorSet = `qh-shop-admin-front.authorSet`;

    return Object.assign(baseObj, {
        pfApiPath:pfApiPath,
        rootUrl: rootUrl,
        share: rootPath + "#/share",
        apiPath: apiPath,
        oauthPath: oauthPath,               // oauth
        wxloginPath: wxloginPath,           // weixin登录的url
        maxSize: 8,                         // 页数多少多少翻页数
        pageSize: 15,                       // 每页多少条数据
        imgUrl: "//img.kingsilk.net/",      // 图片地址
        brandAppId: brandAppId,
        token: token,
        payApipath:payApipath,
        yunApiPath: yunApiPath,
        authorSet: authorSet,
        wxPath:wxPath,
        linkPath:linkPath,
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
