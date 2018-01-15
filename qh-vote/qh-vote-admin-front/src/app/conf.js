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

    const devPort = "17100";              //开发端口
    const apiPort = "16000";              //api端口，支付默认api端口

    /////////////////////////////////这是分割线
    let domain = "//kingsilk.net";
    let rootPath = "//kingsilk.net";
    let apiPath = domain + `/vote/rs/local/${apiPort}/api`;
    let pfApiPath = domain + `/platform/rs/local/11300/api`;


    let yunApiPath = `//kingsilk.net/yun/local/11400/rs/api`;


    let oauthPath = `//kingsilk.net/oauth2/rs/local/${apiPort}`;
    let wxloginPath = `//kingsilk.net/oauth2/wap/local/11300/#/oauth/authorize?`;

    let payUrl = domain + `/pay/local/?_ddnsPort=${devPort}#/?id=`;
    let payBackUrl = domain + `/vote/local/?_ddnsPort=${devPort}#/`;

    let wxPath=`https://kingsilk.net/wx/rs/local/11300/api`; //------------获取jssdk
    let wxComAppId=`wxcee33ae0776a9a96`;

    let voteAppId = `qh-vote-admin-front.${devPort}.voteAppId`;
    let token = `qh.${devPort}.token`;

    // let loginBackUrl = `https://vote.kingsilk.net/local/${devPort}/#/wxLogin`;


    //回退链接
    let backShopUrl = domain + `/shop/local/?_ddnsPort=${devPort}#/`;


    //活动用户端链接
    let voteWapRootUrl = domain + `/vote/local/?_ddnsPort=17100#/`;




    return Object.assign(baseObj, {
        wxComAppId:wxComAppId,
        wxPath:wxPath,
        yunApiPath:yunApiPath,
        pfApiPath: pfApiPath,
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
        voteAppId: voteAppId,
        token: token,
        // loginBackUrl: loginBackUrl

        backShopUrl:backShopUrl,
        voteWapRootUrl:voteWapRootUrl,
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
    let apiPath = domain + `/vote/rs/local/${apiPort}/api`;

    let pfApiPath = domain + `/platform/rs/local/${apiPort}/api`;

    let yunApiPath = `//kingsilk.net/yun/local/${apiPort}/rs/api`;
    let oauthPath = `//kingsilk.net/oauth2/rs/local/${apiPort}`;
    let wxloginPath = `//kingsilk.net/oauth2/wap/local/${devPort}/#/oauth/authorize?`;

    let wxPath=`https://kingsilk.net/wx/rs/local/${apiPort}/api`;
    let wxComAppId=`wxcee33ae0776a9a96`;
    let payUrl = domain + `/pay/local/?_ddnsPort=${devPort}#/?id=`;
    let payBackUrl = domain + `/vote/local/?_ddnsPort=${devPort}#/`;

    let voteAppId = `qh-vote-admin-front.${devPort}.voteAppId`;
    let token = `qh.${devPort}.token`;
    // let loginBackUrl = `https://vote.kingsilk.net/local/${devPort}/#/loginTime`;

    //回退链接
    let backShopUrl = domain + `/shop/local/?_ddnsPort=${devPort}/#/`;


    //活动用户端链接
    let voteWapRootUrl = domain + `/vote/local/?_ddnsPort=${devPort}#/`;


    return Object.assign(baseObj, {
        yunApiPath:yunApiPath,
        wxComAppId:wxComAppId,
        wxPath:wxPath,
        pfApiPath: pfApiPath,
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
        voteAppId: voteAppId,
        token: token,

        backShopUrl:backShopUrl,
        voteWapRootUrl:voteWapRootUrl,
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
    let apiPath = domain + `/vote/rs/local/${apiPort}/api`;

    let pfApiPath = domain + `/platform/rs/local/${apiPort}/api`;
    let yunApiPath = `//kingsilk.net/yun/local/11400/rs/api`;

    let oauthPath = `//kingsilk.net/oauth2/rs/local/${apiPort}`;
    let wxloginPath = `//kingsilk.net/oauth2/wap/local/${devPort}/#/oauth/authorize?`;


    let payUrl = domain + `/pay/local/?_ddnsPort=${devPort}#/?id=`;
    let payBackUrl = domain + `/vote/local/?_ddnsPort=${devPort}#/`;
    let wxPath=`https://kingsilk.net/wx/rs/local/${apiPort}/api`;
    let wxComAppId=`wxcee33ae0776a9a96`;
    let voteAppId = `qh-vote-admin-front.${devPort}.voteAppId`;
    let token = `qh.${devPort}.token`;


    //回退链接
    let backShopUrl = domain + `/shop/local/?_ddnsPort=${devPort}#/`;

    //活动用户端链接
    let voteWapRootUrl = domain + `/vote/local/?_ddnsPort=${devPort}#/`;


    return Object.assign(baseObj, {
        yunApiPath:yunApiPath,
        wxComAppId:wxComAppId,
        wxPath:wxPath,
        pfApiPath: pfApiPath,
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
        voteAppId: voteAppId,
        token: token,

        backShopUrl:backShopUrl,
        voteWapRootUrl:voteWapRootUrl,
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
    let apiPath = domain + `/vote/rs/local/${apiPort}/api`;

    let pfApiPath = domain + `/platform/rs/local/${apiPort}/api`;

    let yunApiPath = `//kingsilk.net/yun/local/${apiPort}/rs/api`;
    let oauthPath = `//kingsilk.net/oauth2/rs/local/${apiPort}`;

    let wxloginPath = `//kingsilk.net/oauth2/wap/local/${devPort}/#/oauth/authorize?`;
    let payUrl = domain + `/pay/local/?_ddnsPort=${devPort}#/?id=`;
    let payBackUrl = domain + `/vote/local/?_ddnsPort=${devPort}#/`;
    let wxPath=`https://kingsilk.net/wx/rs/local/${apiPort}/api`;
    let wxComAppId=`wxcee33ae0776a9a96`;
    let voteAppId = `qh-vote-admin-front.${devPort}.voteAppId`;
    let token = `qh.${devPort}.token`;

    //回退链接
    let backShopUrl = domain + `/shop/local/?_ddnsPort=${devPort}#/`;

    //活动用户端链接
    let voteWapRootUrl = domain + `/vote/local/?_ddnsPort=${devPort}#/`;


    return Object.assign(baseObj, {
        yunApiPath:yunApiPath,
        wxComAppId:wxComAppId,
        wxPath:wxPath,
        pfApiPath: pfApiPath,
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
        voteAppId: voteAppId,
        token: token,

        backShopUrl:backShopUrl,
        voteWapRootUrl:voteWapRootUrl,
    });
}


/**
 * 开发用的测试环境。
 */
function prod() {

    /////////////////////////////////这是分割线
    let domain = "//kingsilk.net";
    let apiPath = domain + `/vote/rs/api`;
    let pfApiPath = domain + `/platform/rs/api`;


    let oauthPath = `//kingsilk.net/oauth2/rs`;
    let wxloginPath = `//kingsilk.net/oauth2/wap/#/oauth/authorize?`;
    let yunApiPath = `//kingsilk.net/yun/rs/api`;

    let payUrl = `https://kingsilk.net/pay/?#/?id=`;
    let payBackUrl = `https://kingsilk.net/vote/`;
    let wxPath=`https://kingsilk.net/wx/rs/api`;
    let wxComAppId=`wxcee33ae0776a9a96`;
    let voteAppId = `qh-vote-admin-front.voteAppId`;
    let token = `qh.token`;

    //回退链接
    let backShopUrl = domain + `/shop/?showwxpaytitle=1#/`;

    //活动用户端链接
    let voteWapRootUrl = domain + `/vote/?showwxpaytitle=1#/`;


    return Object.assign(baseObj, {
        yunApiPath:yunApiPath,
        wxComAppId:wxComAppId,
        wxPath:wxPath,
        pfApiPath: pfApiPath,
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
        voteAppId: voteAppId,
        token: token,

        backShopUrl:backShopUrl,
        voteWapRootUrl:voteWapRootUrl,
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
