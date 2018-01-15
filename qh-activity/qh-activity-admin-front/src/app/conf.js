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

    const devPort = "16300";            //开发端口
    const apiPort = "16500";            //api端口

    /////////////////////////////////////////////分割线
    let domain = "//kingsilk.net";
    let rootUrl = `/local/${devPort}/admin`;
    let rootPath = domain + rootUrl;
    let apiPath = domain + `/activity/rs/local/${apiPort}/api`;
    let linkPath = `https://kingsilk.net/activity/local/${devPort}/#/brandApp/`;
    let pfApiPath = domain + `/platform/rs/local/11300/api`;

    let agencyPath = `//kingsilk.net/agency/rs/local/${apiPort}/api`;
    let wxPath = `//kingsilk.net/wx/rs/local/11300/api`;

    let yunApiPath = `//kingsilk.net/yun/local/11300/rs/api`;

    let wxComAppId = `qh.${devPort}.wxComAppId`;

    let shopUrl = domain + `/shop/local/${devPort}`;
    let shopPath = domain + `/shop/local/${apiPort}/rs/api`;

    let oauthPath = `//kingsilk.net/oauth2/rs/local/11300`;
    let wxloginPath = `//kingsilk.net/oauth2/wap/local/11300/#/oauth/authorize?`;
    let wx4jPath = `//kingsilk.net/wx/rs/local/${apiPort}/api`;
    let thirdWXId = `wxcee33ae0776a9a96`;

    let brandAppId = `qh-activity-admin-front.${devPort}.brandAppId`;
    let token = `qh.${devPort}.token`;
    let authorSet = `qh-activity-admin-front.${devPort}.authorSet`;
    let loginBackUrl = `https://kingsilk.net/activity/local/${devPort}/#/loginTime`;
    //let loginBackUrl = `https://kingsilk.net/admin/local/${devPort}/#/loginTime`;


    return Object.assign(baseObj, {
        thirdWXId: thirdWXId,
        wx4jPath: wx4jPath,
        agencyPath: agencyPath,
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
        shopUrl: shopUrl,
        yunApiPath: yunApiPath,
        shopPath: shopPath,
        authorSet: authorSet,
        loginBackUrl: loginBackUrl,
        linkPath: linkPath,
        pfApiPath: pfApiPath,
        wxPath: wxPath,
        wxComAppId: wxComAppId
    });
}

/**
 * 测试环境13
 */
function test13() {

    const devPort = "11300";            //开发端口
    const apiPort = "11300";            //api端口

    /////////////////////////////////////////////分割线
    let domain = "//kingsilk.net";
    let rootUrl = `/local/${devPort}/admin`;
    let rootPath = domain + rootUrl;
    let apiPath = domain + `/activity/rs/local/${apiPort}/api`;

    let pfApiPath = domain + `/platform/rs/local/${apiPort}/api`;


    let agencyPath = `//kingsilk.net/agency/rs/local/${apiPort}/api`;
    let wxPath = `//kingsilk.net/wx/rs/local/${apiPort}/api`;

    let yunApiPath = `//kingsilk.net/yun/local/${apiPort}/rs/api`;
    let linkPath = `https://kingsilk.net/activity/local/${devPort}/#/brandApp/`;

    let shopUrl = domain + `/shop/local/${devPort}`;
    let shopPath = domain + `/shop/local/${apiPort}/rs/api`;

    let wxComAppId = `qh.${devPort}.wxComAppId`;

    let oauthPath = `//kingsilk.net/oauth2/rs/local/${apiPort}`;
    let wxloginPath = `//kingsilk.net/oauth2/wap/local/${devPort}/#/oauth/authorize?`;
    let wx4jPath = `//kingsilk.net/wx/rs/local/${apiPort}/api`;
    let thirdWXId = `wxcee33ae0776a9a96`;

    let brandAppId = `qh-activity-admin-front.${devPort}.brandAppId`;
    let token = `qh.${devPort}.token`;
    let authorSet = `qh-activity-admin-front.${devPort}.authorSet`;
    let loginBackUrl = `https://kingsilk.net/activity/local/${devPort}/#/loginTime`;

    return Object.assign(baseObj, {
        pfApiPath: pfApiPath,
        thirdWXId: thirdWXId,
        wx4jPath: wx4jPath,
        agencyPath: agencyPath,
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
        shopUrl: shopUrl,
        shopPath: shopPath,
        authorSet: authorSet,
        yunApiPath: yunApiPath,
        loginBackUrl: loginBackUrl,
        linkPath: linkPath,
        wxPath: wxPath,
        wxComAppId: wxComAppId
    });

}


/**
 * 测试环境12
 */
function test12() {

    const devPort = "11200";            //开发端口
    const apiPort = "11200";            //api端口

    /////////////////////////////////////////////分割线
    let domain = "//kingsilk.net";
    let rootUrl = `/local/${devPort}/admin`;
    let rootPath = domain + rootUrl;
    let apiPath = domain + `/activity/rs/local/${apiPort}/api`;

    let pfApiPath = domain + `/platform/rs/local/${apiPort}/api`;
    let agencyPath = `//agency.kingsilk.net/local/${apiPort}/rs/api`;
    let linkPath = `https://kingsilk.net/activity/local/${devPort}/#/brandApp/`;

    let yunApiPath = `//kingsilk.net/yun/local/${apiPort}/rs/api`;
    let wxComAppId = `qh.${devPort}.wxComAppId`;


    let shopUrl = domain + `/shop/local/${devPort}`;
    let shopPath = domain + `/shop/local/${apiPort}/rs/api`;
    let wxPath = `//kingsilk.net/wx/rs/local/${apiPort}/api`;

    let oauthPath = `//kingsilk.net/oauth2/rs/local/${apiPort}`;
    let wxloginPath = `//kingsilk.net/oauth2/wap/local/${devPort}/#/oauth/authorize?`;
    let wx4jPath = `//kingsilk.net/wx/rs/local/${apiPort}/api`;
    let thirdWXId = `wxcee33ae0776a9a96`;

    let brandAppId = `qh-activity-admin-front.${devPort}.brandAppId`;
    let token = `qh.${devPort}.token`;
    let authorSet = `qh-activity-admin-front.${devPort}.authorSet`;
    let loginBackUrl = `https://kingsilk.net/activity/local/${devPort}/#/loginTime`;

    return Object.assign(baseObj, {
        pfApiPath: pfApiPath,
        thirdWXId: thirdWXId,
        wx4jPath: wx4jPath,
        agencyPath: agencyPath,
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
        shopUrl: shopUrl,
        shopPath: shopPath,
        yunApiPath: yunApiPath,
        authorSet: authorSet,
        loginBackUrl: loginBackUrl,
        linkPath: linkPath,
        wxPath: wxPath,
        wxComAppId: wxComAppId

    });

}


/**
 * 模拟测试线上生产环境
 */
function test14() {
    const devPort = "11400";            //开发端口
    const apiPort = "11400";            //api端口

    /////////////////////////////////////////////分割线
    let domain = "//kingsilk.net";
    let rootUrl = `/local/${devPort}/admin`;
    let rootPath = domain + rootUrl;
    let apiPath = domain + `/activity/rs/local/${apiPort}/api`;
    let pfApiPath = domain + `/platform/rs/local/${apiPort}/api`;

    let agencyPath = `//agency.kingsilk.net/local/${apiPort}/rs/api`;

    let wxComAppId = `qh.${devPort}.wxComAppId`;

    let yunApiPath = `//kingsilk.net/yun/local/${apiPort}/rs/api`;

    let linkPath = `https://kingsilk.net/activity/local/${devPort}/#/brandApp/`;
    let shopUrl = domain + `/shop/local/${devPort}`;
    let shopPath = domain + `/shop/local/${apiPort}/rs/api`;

    let oauthPath = `//kingsilk.net/oauth2/rs/local/${apiPort}`;
    let wxloginPath = `//kingsilk.net/oauth2/wap/local/${devPort}/#/oauth/authorize?`;
    let wx4jPath = `//kingsilk.net/wx/rs/local/${apiPort}/api`;
    let thirdWXId = `wxcee33ae0776a9a96`;

    let brandAppId = `qh-activity-admin-front.${devPort}.brandAppId`;
    let token = `qh.${devPort}.token`;
    let authorSet = `qh-activity-admin-front.${devPort}.authorSet`;
    let loginBackUrl = `https://kingsilk.net/activity/local/${devPort}/#/loginTime`;
    let wxPath = `//kingsilk.net/wx/rs/local/${apiPort}/api`;


    return Object.assign(baseObj, {
        pfApiPath: pfApiPath,
        thirdWXId: thirdWXId,
        wx4jPath: wx4jPath,
        agencyPath: agencyPath,
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
        shopUrl: shopUrl,
        shopPath: shopPath,
        yunApiPath: yunApiPath,
        authorSet: authorSet,
        loginBackUrl: loginBackUrl,
        linkPath: linkPath,
        wxPath: wxPath,
        wxComAppId: wxComAppId
    });
}


/**
 * 线上生产环境
 */
function prod() {

    /////////////////////////////////////////////分割线
    let domain = "//kingsilk.net";
    let rootUrl = `/admin/`;
    let rootPath = domain + rootUrl;
    let apiPath = domain + `/activity/rs/api`;

    let shopUrl = domain + `/shop`;
    let shopPath = domain + `/shop/rs/api`;
    let pfApiPath = domain + `/platform/rs/api`;
    let oauthPath = `//kingsilk.net/oauth2/rs`;
    let wxPath = `//kingsilk.net/wx/rs/api`;

    let wxloginPath = `//kingsilk.net/oauth2/wap/#/oauth/authorize?`;
    let wx4jPath = `//kingsilk.net/wx/rs/api`;
    let thirdWXId = `wxcee33ae0776a9a96`;

    let yunApiPath = `//kingsilk.net/yun/rs/api`;
    let wxComAppId = `qh.wxComAppId`;
    let brandAppId = `qh-activity-admin-front.brandAppId`;
    let token = `qh.token`;
    let authorSet = `qh-activity-admin-front.authorSet`;

    let loginBackUrl = `https://kingsilk.net/activity/#/loginTime`;
    let linkPath = `https://kingsilk.net/activity/#/brandApp/`;
    let agencyPath = `//agency.kingsilk.net/rs/api`;

    return Object.assign(baseObj, {
        pfApiPath: pfApiPath,
        thirdWXId: thirdWXId,
        wx4jPath: wx4jPath,
        agencyPath: agencyPath,
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
        shopUrl: shopUrl,
        shopPath: shopPath,
        yunApiPath: yunApiPath,
        authorSet: authorSet,
        loginBackUrl: loginBackUrl,
        linkPath: linkPath,
        wxPath: wxPath,
        wxComAppId: wxComAppId

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
