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

    const devPort = "14300";            //开发端口
    const apiPort = "14300";            //api端口

    /////////////////////////////////////////////分割线
    let domain = "//kingsilk.net";
    let rootUrl = `/local/${devPort}/admin`;
    let rootPath = domain + rootUrl;
    let apiPath = domain + `/platform/rs/local/${apiPort}/api`;


    let agencyPath =`//kingsilk.net/agency/rs/local/${apiPort}/api`;



    let yunApiPath = `//kingsilk.net/yun/local/11300/rs/api`;


    let shopUrl = domain + `/shop/local/?_ddnsPort=${devPort}#/`;
    let shopPath = domain + `/shop/local/${apiPort}/rs/api`;

    let oauthPath = `//kingsilk.net/oauth2/rs/local/14300`;
    let wxloginPath = `//kingsilk.net/oauth2/wap/local/14300/#/oauth/authorize?`;
    let wx4jPath =`//kingsilk.net/wx/rs/local/${apiPort}/api`;
    let thirdWXId=`wxcee33ae0776a9a96`;

    let brandAppId = `qh-platform-admin-front.${devPort}.brandAppId`;
    let token = `qh.${devPort}.token`;
    let authorSet = `qh-platform-admin-front.${devPort}.authorSet`;
    let loginBackUrl = `https://kingsilk.net/platform/local/${devPort}/#/loginTime`;
    //let loginBackUrl = `https://kingsilk.net/admin/local/${devPort}/#/loginTime`;


    return Object.assign(baseObj, {
        thirdWXId:thirdWXId,
        wx4jPath:wx4jPath,
        agencyPath:agencyPath,
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
        loginBackUrl:loginBackUrl
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
    let apiPath = domain + `/platform/rs/local/${apiPort}/api`;


    let agencyPath =`//kingsilk.net/agency/rs/local/${apiPort}/api`;



    let yunApiPath = `//kingsilk.net/yun/local/${apiPort}/rs/api`;


    let shopUrl = domain + `/shop/local/?_ddnsPort=${devPort}#/`;
    let shopPath = domain + `/shop/local/${apiPort}/rs/api`;


    let oauthPath = `//kingsilk.net/oauth2/rs/local/${apiPort}`;
    let wxloginPath = `//kingsilk.net/oauth2/wap/local/?_ddnsPort=${devPort}#/oauth/authorize?`;
    let wx4jPath =`//kingsilk.net/wx/rs/local/${apiPort}/api`;
    let thirdWXId=`wxcee33ae0776a9a96`;

    let brandAppId = `qh-platform-admin-front.${devPort}.brandAppId`;
    let token = `qh.${devPort}.token`;
    let authorSet = `qh-platform-admin-front.${devPort}.authorSet`;
    let loginBackUrl = `https://kingsilk.net/platform/local/?_ddnsPort=${devPort}#/loginTime`;

    return Object.assign(baseObj, {
        thirdWXId:thirdWXId,
        wx4jPath:wx4jPath,
        agencyPath:agencyPath,
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
    let apiPath = domain + `/platform/rs/local/${apiPort}/api`;


    let agencyPath =`//agency.kingsilk.net/local/${apiPort}/rs/api`;



    let yunApiPath = `//kingsilk.net/yun/local/${apiPort}/rs/api`;


    let shopUrl = domain + `/shop/local/?_ddnsPort=${devPort}#/`;
    let shopPath = domain + `/shop/local/${apiPort}/rs/api`;

    let oauthPath = `//kingsilk.net/oauth2/rs/local/${apiPort}`;
    let wxloginPath = `//kingsilk.net/oauth2/wap/local/${devPort}/#/oauth/authorize?`;
    let wx4jPath =`//kingsilk.net/wx/rs/local/${apiPort}/api`;
    let thirdWXId=`wxcee33ae0776a9a96`;

    let brandAppId = `qh-platform-admin-front.${devPort}.brandAppId`;
    let token = `qh.${devPort}.token`;
    let authorSet = `qh-platform-admin-front.${devPort}.authorSet`;
    let loginBackUrl = `https://kingsilk.net/platform/local/${devPort}/#/loginTime`;

    return Object.assign(baseObj, {
        thirdWXId:thirdWXId,
        wx4jPath:wx4jPath,
        agencyPath:agencyPath,
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
    let apiPath = domain + `/platform/rs/local/${apiPort}/api`;


    let agencyPath =`//kingsilk.net/agency/rs/local/${apiPort}/api`;



    let yunApiPath = `//kingsilk.net/yun/local/${apiPort}/rs/api`;


    let shopUrl = domain + `/shop/local/?_ddnsPort=${devPort}#/`;
    let shopPath = domain + `/shop/local/${apiPort}/rs/api`;


    let oauthPath = `//kingsilk.net/oauth2/rs/local/${apiPort}`;
    let wxloginPath = `//kingsilk.net/oauth2/wap/local/?_ddnsPort=${devPort}#/oauth/authorize?`;
    let wx4jPath =`//kingsilk.net/wx/rs/local/${apiPort}/api`;
    let thirdWXId=`wxcee33ae0776a9a96`;

    let brandAppId = `qh-platform-admin-front.${devPort}.brandAppId`;
    let token = `qh.${devPort}.token`;
    let authorSet = `qh-platform-admin-front.${devPort}.authorSet`;
    let loginBackUrl = `https://kingsilk.net/platform/local/?_ddnsPort=${devPort}#/loginTime`;

    return Object.assign(baseObj, {
        thirdWXId:thirdWXId,
        wx4jPath:wx4jPath,
        agencyPath:agencyPath,
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
    let apiPath = domain + `/platform/rs/api`;

    let shopUrl = domain + `/shop`;
    let shopPath = domain + `/shop/rs/api`;

    let oauthPath = `//kingsilk.net/oauth2/rs`;
    let wxloginPath = `//kingsilk.net/oauth2/wap/#/oauth/authorize?`;
    let wx4jPath =`//kingsilk.net/wx/rs/api`;
    let thirdWXId=`wxcee33ae0776a9a96`;

    let yunApiPath = `//kingsilk.net/yun/rs/api`;

    let brandAppId = `qh-platform-admin-front.brandAppId`;
    let token = `qh.token`;
    let authorSet = `qh-platform-admin-front.authorSet`;

    let loginBackUrl = `https://kingsilk.net/platform/#/loginTime`;

    let agencyPath =`//kingsilk.net/agency/rs/api`;

    return Object.assign(baseObj, {
        thirdWXId:thirdWXId,
        wx4jPath:wx4jPath,
        agencyPath:agencyPath,
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
