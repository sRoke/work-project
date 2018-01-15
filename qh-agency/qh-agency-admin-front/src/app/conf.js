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

    const apiPort = "14400";            //api端口

    let wxPath =`//kingsilk.net/wx/rs/local/11300/api`;
    let wxComAppId = `qh.${devPort}.wxComAppId`;
    /////////////////////////////////////////////分割线
    let domain = "//kingsilk.net";
    let apiPath = domain + `/agency/rs/local/${apiPort}/api`;
    let pfApiPath = domain + `/platform/rs/local/14400/api`;
    let payApipath=domain+`/pay/rs/local/16700/api`;
    //kingsilk.net/yun/local/16700/rs/api
    let yunApiPath = `//kingsilk.net/yun/local/11300/rs/api`;


    let shopUrl = domain + `/shop/local/?_ddnsPort=${devPort}#/`;
    let shopPath = domain + `/shop/rs/local/11300/api`;


    let oauthPath = `//kingsilk.net/oauth2/rs/local/11300`;

    let wxloginPath = `//kingsilk.net/oauth2/wap/local/11300/#/oauth/authorize?`;

    let linkPath=`https://kingsilk.net/agency/local/${devPort}/#/brandApp/`;



    let brandAppId = `qh-agency-admin-front.${devPort}.brandAppId`;
    let token = `qh.${devPort}.token`;
    let authorSet = `qh-agency-admin-front.${devPort}.authorSet`;
    // let loginBackUrl = `https://agency.kingsilk.net/admin/local/${devPort}/#/loginTime`;



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
        shopUrl: shopUrl,
        yunApiPath: yunApiPath,
        shopPath: shopPath,
        authorSet: authorSet,
        wxPath:wxPath,
        wxComAppId:wxComAppId,
        linkPath:linkPath,
        payApipath:payApipath,
        // loginBackUrl:loginBackUrl,
    });
}

/**
 * 测试环境13
 */
function test13() {

    const devPort = "11300";            //开发端口
    const apiPort = "11300";            //api端口
    let wxPath =`//kingsilk.net/wx/rs/local/${apiPort}/api`;
    let wxComAppId = `qh.${devPort}.wxComAppId`;
    /////////////////////////////////////////////分割线
    let domain = "//kingsilk.net";
    let apiPath = domain + `/agency/rs/local/${apiPort}/api`;
    let pfApiPath = domain + `/platform/rs/local/${apiPort}/api`;
    let payApipath=domain+`/pay/rs/local/${apiPort}/api`;
    let shopUrl = domain + `/shop/local/${devPort}`;
    let shopPath = domain + `/shop/rs/local/${apiPort}/api`;
    let yunApiPath = `//kingsilk.net/yun/local/${apiPort}/rs/api`;

    let linkPath=`https://kingsilk.net/agency/local/${devPort}/#/brandApp/`;

    let oauthPath = `//kingsilk.net/oauth2/rs/local/${apiPort}`;
    let wxloginPath = `//kingsilk.net/oauth2/wap/local/${devPort}/#/oauth/authorize?`;


    let brandAppId = `qh-agency-admin-front.${devPort}.brandAppId`;
    let token = `qh.${devPort}.token`;
    let authorSet = `qh-agency-admin-front.${devPort}.authorSet`;

    // let loginBackUrl = `https://agency.kingsilk.net/admin/local/${devPort}/#/loginTime`;



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
        shopUrl: shopUrl,
        shopPath: shopPath,
        authorSet: authorSet,
        yunApiPath: yunApiPath,
        wxPath:wxPath,
        wxComAppId:wxComAppId,
        linkPath:linkPath,
        payApipath:payApipath,
        // loginBackUrl: loginBackUrl,
    });

}


/**
 * 测试环境12
 */
function test12() {

    const devPort = "11200";            //开发端口
    const apiPort = "11200";            //api端口
    let wxPath =`//kingsilk.net/wx/rs/local/${apiPort}/api`;
    let wxComAppId = `qh.${devPort}.wxComAppId`;
    /////////////////////////////////////////////分割线
    let domain = "//kingsilk.net";
    let apiPath = domain + `/agency/rs/local/${apiPort}/api`;
    let pfApiPath = domain + `/platform/rs/local/${apiPort}/api`;
    let payApipath=domain+`/pay/rs/local/${apiPort}/api`;
    let shopUrl = domain + `/shop/local/?_ddnsPort=${devPort}#/`;
    let shopPath = domain + `/shop/rs/local/${apiPort}/api`;

    let yunApiPath = `//kingsilk.net/yun/local/${apiPort}/rs/api`;
    let linkPath=`https://kingsilk.net/agency/local/${devPort}/#/brandApp/`;

    let oauthPath = `//kingsilk.net/oauth2/rs/local/${apiPort}`;
    let wxloginPath = `//kingsilk.net/oauth2/wap/local/${devPort}/#/oauth/authorize?`;


    let brandAppId = `qh-agency-admin-front.${devPort}.brandAppId`;
    let token = `qh.${devPort}.token`;
    let authorSet = `qh-agency-admin-front.${devPort}.authorSet`;

    // let loginBackUrl = `https://agency.kingsilk.net/admin/local/${devPort}/#/loginTime`;

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
        shopUrl: shopUrl,
        shopPath: shopPath,
        yunApiPath: yunApiPath,
        authorSet: authorSet,
        wxPath:wxPath,
        wxComAppId:wxComAppId,
        linkPath:linkPath,
        payApipath:payApipath,
        // loginBackUrl: loginBackUrl,
    });

}


/**
 * 模拟测试线上生产环境
 */
function test14() {
    const devPort = "11400";            //开发端口
    const apiPort = "11400";            //api端口
    let wxPath =`//kingsilk.net/wx/rs/local/${apiPort}/api`;
    let wxComAppId = `qh.${devPort}.wxComAppId`;
    /////////////////////////////////////////////分割线
    let domain = "//kingsilk.net";
    let apiPath = domain + `/agency/rs/local/${apiPort}/api`;
    let pfApiPath = domain + `/platform/rs/local/${apiPort}/api`;
    let payApipath=domain+`/pay/rs/local/${apiPort}/api`;
    let shopUrl = domain + `/shop/local/?_ddnsPort=${devPort}#/`;
    let shopPath = domain + `/shop/rs/local/${apiPort}/api`;
    let yunApiPath = `//kingsilk.net/yun/local/${apiPort}/rs/api`;

    let linkPath=`https://kingsilk.net/agency/local/${devPort}/#/brandApp/`;

    let oauthPath = `//kingsilk.net/oauth2/rs/local/${apiPort}`;
    let wxloginPath = `//kingsilk.net/oauth2/wap/local/${devPort}/#/oauth/authorize?`;


    let brandAppId = `qh-agency-admin-front.${devPort}.brandAppId`;
    let token = `qh.${devPort}.token`;
    let authorSet = `qh-agency-admin-front.${devPort}.authorSet`;

    // let loginBackUrl = `https://agency.kingsilk.net/admin/local/${devPort}/#/loginTime`;


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
        shopUrl: shopUrl,
        shopPath: shopPath,
        authorSet: authorSet,
        yunApiPath: yunApiPath,
        wxPath:wxPath,
        wxComAppId:wxComAppId,
        linkPath:linkPath,
        payApipath:payApipath,
        // loginBackUrl: loginBackUrl,
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
    let apiPath = domain + `/agency/rs/api`;
    let shopUrl = domain + `/shop/?showwxpaytitle=1#/`;
    let payApipath=domain+`/pay/rs/api`;
    let shopPath = domain + `/shop/rs/api`;
    let wxPath =`//kingsilk.net/wx/rs/api`;
    let wxComAppId = `qh.wxComAppId`;
    let pfApiPath = domain + `/platform/rs/api`;

    // let oauthPath = `//kingsilk.net/oauth/rs`;
    // let wxloginPath = `${oauthPath}/oauth/authorize?`;
    let oauthPath = `//kingsilk.net/oauth2/rs`;
    let wxloginPath = `//kingsilk.net/oauth2/wap/?showwxpaytitle=1#/oauth/authorize?`;

    let linkPath=`https://kingsilk.net/agency/?showwxpaytitle=1#/brandApp/`;

    let yunApiPath = `//kingsilk.net/yun/rs/api`;

    let brandAppId = `qh-agency-admin-front.brandAppId`;
    let token = `qh.token`;
    let authorSet = `qh-agency-admin-front.authorSet`;

    // let loginBackUrl = `https://agency.kingsilk.net/admin/#/loginTime`;


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
        shopUrl: shopUrl,
        shopPath: shopPath,
        yunApiPath: yunApiPath,
        authorSet: authorSet,
        wxPath:wxPath,
        wxComAppId:wxComAppId,
        linkPath:linkPath,
        payApipath:payApipath,
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
