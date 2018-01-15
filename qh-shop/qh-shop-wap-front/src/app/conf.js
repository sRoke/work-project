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
    let pfApiPath = domain + `/platform/rs/local/11300/api`;
    let wxPath = `//kingsilk.net/wx/rs/local/11300/api`;


    let yunApiPath = `//kingsilk.net/yun/local/11300/rs/api`;
    let oauthPath = `//kingsilk.net/oauth2/rs/local/11300`;
    let wxloginPath = `//kingsilk.net/oauth2/wap/local/11300/#/oauth/authorize?`;


    // let payUrl = `https://kingsilk.net/pay/local/?_ddnsPort=${devPort}#/scanReceive?id=`;
    let payUrl = domain + `/pay/local/?_ddnsPort=16300#/?id=`;
    let payBackUrl = `https://kingsilk.net/shop/local/?_ddnsPort=${devPort}#/`;
    let payJsdkPath = "//kingsilk.net/pay/rs/local/11400/api";

    let brandAppId = `qh-shop-wap-front.${devPort}.brandAppId`;
    let token = `qh.${devPort}.token`;
    let authorSet = `qh-shop-wap-front.${devPort}.authorSet`;

    let dwzApiPath = 'https://dwz.kingsilk.net/admin/local/11400/rs/api/dwz/addDwz';     //添加短网址，返回data
    let dwzUrlPath = 'https://dwz.kingsilk.net/admin/local/11400/rs/api/s/';            //data 直接加在后边






    //活动相关链接
    let bargainApiPath = domain + `/bargain/rs/local/${apiPort}/api`;
    let bargainRootUrl = domain + `/bargain/admin/local/?_ddnsPort=${devPort}#/`;

    let shareItemLink = `https://kingsilk.net/shop/mobile/local/?_ddnsPort=11300` ;

    // //活动相关链接
    // let bargainApiPath = domain + `/bargain/rs/local/${apiPort}/api`;
    // let bargainRootUrl = domain + `/bargain/admin/local/?_ddnsPort=${devPort}#/`;

    let voteApiPath = domain + `/vote/rs/local/${apiPort}/api`;
    let voteRootUrl = domain + `/vote/admin/local/?_ddnsPort=${devPort}#/`;

    let raffleApiPath = domain + `/raffle/rs/local/16700/api`;
    let raffleRootUrl=domain + `/raffle/admin/local/?_ddnsPort=${devPort}#/`;


    return Object.assign(baseObj, {
        shareItemLink:shareItemLink,
        dwzApiPath:dwzApiPath,
        dwzUrlPath:dwzUrlPath,
        pfApiPath: pfApiPath,
        agencyRootUrl: agencyRootUrl,
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

        bargainApiPath:bargainApiPath,
        bargainRootUrl:bargainRootUrl,
        voteApiPath:voteApiPath,
        voteRootUrl:voteRootUrl,
        raffleApiPath:raffleApiPath,
        raffleRootUrl:raffleRootUrl,
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

    let oauthPath = `//kingsilk.net/oauth2/rs/local/${apiPort}`;
    let wxloginPath = `//kingsilk.net/oauth2/wap/local/${devPort}/#/oauth/authorize?`;
    let yunApiPath = `//kingsilk.net/yun/local/${devPort}/rs/api`;

    // let payUrl =  `https://kingsilk.net/pay/local/?_ddnsPort=${devPort}#/scanReceive?id=`;
    // let payBackUrl = `https://kingsilk.net/shop/local/?_ddnsPort=${devPort}#/`;
    let payUrl = domain + `/pay/local/?_ddnsPort=11200#/?id=`;
    let payBackUrl = `https://kingsilk.net/shop/local/?_ddnsPort=${devPort}#/`;
    let payJsdkPath = "//kingsilk.net/pay/rs/local/11200/api";

    let brandAppId = `qh-shop-wap-front.${devPort}.brandAppId`;
    let token = `qh.${devPort}.token`;


    let authorSet = `qh-shop-wap-front.${devPort}.authorSet`;


    let dwzApiPath = `https://dwz.kingsilk.net/admin/local/${apiPort}/rs/api/dwz/addDwz`;     //添加短网址，返回data
    let dwzUrlPath = `https://dwz.kingsilk.net/admin/local/${apiPort}/rs/api/s/`;            //data 直接加在后边

    let shareItemLink = `https://kingsilk.net/shop/mobile/local/?_ddnsPort=${apiPort}` ;


    //活动相关链接
    let bargainApiPath = domain + `/bargain/rs/local/${apiPort}/api`;
    let bargainRootUrl = domain + `/bargain/admin/local/?_ddnsPort=${devPort}#/`;

    let voteApiPath = domain + `/vote/rs/local/${apiPort}/api`;
    let voteRootUrl = domain + `/vote/admin/local/?_ddnsPort=${devPort}#/`;

    let raffleApiPath = domain + `/raffle/rs/local/${apiPort}/api`;
    let raffleRootUrl=domain + `/raffle/admin/local/?_ddnsPort=${devPort}#/`;
    return Object.assign(baseObj, {
        shareItemLink:shareItemLink,
        dwzApiPath:dwzApiPath,
        dwzUrlPath:dwzUrlPath,
        yunApiPath:yunApiPath,
        pfApiPath: pfApiPath,
        agencyRootUrl: agencyRootUrl,
        payJsdkPath: payJsdkPath,
        agencyPath: agencyPath,
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
        wxPath:wxPath,
        authorSet:authorSet,

        bargainApiPath:bargainApiPath,
        bargainRootUrl:bargainRootUrl,
        voteApiPath:voteApiPath,
        voteRootUrl:voteRootUrl,
        raffleApiPath:raffleApiPath,
        raffleRootUrl:raffleRootUrl,
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
    let wxPath = `//kingsilk.net/wx/rs/local/11300/api`;

    let oauthPath = `//kingsilk.net/oauth2/rs/local/${apiPort}`;
    let wxloginPath = `//kingsilk.net/oauth2/wap/local/${devPort}/#/oauth/authorize?`;
    let yunApiPath = `//kingsilk.net/yun/local/11400/rs/api`;

    // let payUrl = `https://kingsilk.net/pay/local/?_ddnsPort=${devPort}#/scanReceive?id=`;
    // let payBackUrl = `https://kingsilk.net/shop/local/?_ddnsPort=${devPort}#/`;
    let payUrl = domain + `/pay/local/?_ddnsPort=${devPort}#/?id=`;
    let payBackUrl = `https://kingsilk.net/shop/local/?_ddnsPort=${devPort}#/`;
    let payJsdkPath = "//kingsilk.net/pay/rs/local/11300/api";

    let brandAppId = `qh-shop-wap-front.${devPort}.brandAppId`;
    let token = `qh.${devPort}.token`;

    let authorSet = `qh-shop-wap-front.${devPort}.authorSet`;


    let dwzApiPath = `https://dwz.kingsilk.net/admin/local/${apiPort}/rs/api/dwz/addDwz`;     //添加短网址，返回data
    let dwzUrlPath = `https://dwz.kingsilk.net/admin/local/${apiPort}/rs/api/s/`;            //data 直接加在后边

    let shareItemLink = `https://kingsilk.net/shop/mobile/local/?_ddnsPort=${apiPort}` ;

    //活动相关链接
    let bargainApiPath = domain + `/bargain/rs/local/${apiPort}/api`;
    let bargainRootUrl = domain + `/bargain/admin/local/?_ddnsPort=${devPort}#/`;


    let voteApiPath = domain + `/vote/rs/local/${apiPort}/api`;
    let voteRootUrl = domain + `/vote/admin/local/?_ddnsPort=${devPort}#/`;

    let raffleApiPath = domain + `/raffle/rs/local/${apiPort}/api`;
    let raffleRootUrl=domain + `/raffle/admin/local/?_ddnsPort=${devPort}#/`;
    return Object.assign(baseObj, {
        shareItemLink:shareItemLink,
        dwzApiPath:dwzApiPath,
        dwzUrlPath:dwzUrlPath,
        yunApiPath:yunApiPath,
        pfApiPath: pfApiPath,
        agencyRootUrl: agencyRootUrl,
        payJsdkPath: payJsdkPath,
        agencyPath: agencyPath,
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
        wxPath:wxPath,
        authorSet:authorSet,

        bargainApiPath:bargainApiPath,
        bargainRootUrl:bargainRootUrl,
        voteApiPath:voteApiPath,
        voteRootUrl:voteRootUrl,
        raffleApiPath:raffleApiPath,
        raffleRootUrl:raffleRootUrl,
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
    let wxPath = `//kingsilk.net/wx/rs/local/11300/api`;
    let oauthPath = `//kingsilk.net/oauth2/rs/local/${apiPort}`;
    let wxloginPath = `//kingsilk.net/oauth2/wap/local/${devPort}/#/oauth/authorize?`;
    let yunApiPath = `//kingsilk.net/yun/local/${devPort}/rs/api`;
    let payUrl = domain + `/pay/local/?_ddnsPort=${devPort}#/?id=`;
    let payBackUrl = `https://kingsilk.net/shop/local/?_ddnsPort=${devPort}#/`;
    // let payUrl =  `https://kingsilk.net/pay/local/?_ddnsPort=${devPort}#/scanReceive?id=`;
    // let payBackUrl = `https://kingsilk.net/shop/local/?_ddnsPort=${devPort}#/`;
    let payJsdkPath = "//kingsilk.net/pay/local/11400/rs/api";

    let brandAppId = `qh-shop-wap-front.${devPort}.brandAppId`;
    let token = `qh.${devPort}.token`;

    let authorSet = `qh-shop-wap-front.${devPort}.authorSet`;

    let dwzApiPath = `https://dwz.kingsilk.net/admin/local/${apiPort}/rs/api/dwz/addDwz`;     //添加短网址，返回data
    let dwzUrlPath = `https://dwz.kingsilk.net/admin/local/${apiPort}/rs/api/s/`;            //data 直接加在后边

    let shareItemLink = `https://kingsilk.net/shop/mobile/local/?_ddnsPort=${apiPort}` ;

    //活动相关链接
    let bargainApiPath = domain + `/bargain/rs/local/${apiPort}/api`;
    let bargainRootUrl = domain + `/bargain/admin/local/?_ddnsPort=${devPort}#/`;


    let voteApiPath = domain + `/vote/rs/local/${apiPort}/api`;
    let voteRootUrl = domain + `/vote/admin/local/?_ddnsPort=${devPort}#/`;

    let raffleApiPath = domain + `/raffle/rs/local/${apiPort}/api`;
    let raffleRootUrl=domain + `/raffle/admin/local/?_ddnsPort=${devPort}#/`;
    return Object.assign(baseObj, {
        shareItemLink:shareItemLink,
        dwzApiPath:dwzApiPath,
        dwzUrlPath:dwzUrlPath,
        yunApiPath:yunApiPath,
        pfApiPath: pfApiPath,
        agencyRootUrl: agencyRootUrl,
        payJsdkPath: payJsdkPath,
        agencyPath: agencyPath,
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
        wxPath:wxPath,
        authorSet:authorSet,

        bargainApiPath:bargainApiPath,
        bargainRootUrl:bargainRootUrl,
        voteApiPath:voteApiPath,
        voteRootUrl:voteRootUrl,
        raffleApiPath:raffleApiPath,
        raffleRootUrl:raffleRootUrl,
    });
}


/**
 * 开发用的测试环境。
 */
function prod() {

    /////////////////////////////////这是分割线
    let domain = "//kingsilk.net";
    let rootUrl = `/shop/?showwxpaytitle=1#/`;
    let apiPath = domain + `/shop/rs/api`;

    let agencyRootUrl = domain + `/agency/?showwxpaytitle=1#/`;
    let agencyPath = domain + `/agency/rs/api`;
    let pfApiPath = domain + `/platform/rs/api`;
    let wxPath = `//kingsilk.net/wx/rs/api`;

    let oauthPath = `//kingsilk.net/oauth2/rs`;
    let wxloginPath = `//kingsilk.net/oauth2/wap/?showwxpaytitle=1#/oauth/authorize?`;
    let yunApiPath = `//kingsilk.net/yun/rs/api`;

    let payUrl = `https://kingsilk.net/pay/?showwxpaytitle=1#/?id=`;
    let payBackUrl = `https://kingsilk.net/shop/?showwxpaytitle=1#/`;
    let payJsdkPath = "//kingsilk.net/pay/rs/api";


    let brandAppId = `qh-shop-wap-front.brandAppId`;
    let token = `qh.token`;
    let authorSet = `qh-shop-wap-front.authorSet`;

    let dwzApiPath = `https://dwz.kingsilk.net/admin/rs/api/dwz/addDwz`;     //添加短网址，返回data
    let dwzUrlPath = `https://dwz.kingsilk.net/admin/rs/api/s/`;            //data 直接加在后边

    let shareItemLink = `https://kingsilk.net/shop/mobile/?showwxpaytitle=1` ;

    //活动相关链接
    let bargainApiPath = domain + `/bargain/rs/api`;
    let bargainRootUrl = domain + `/bargain/admin/?showwxpaytitle=1#/`;


    let voteApiPath = domain + `/vote/rs/api`;
    let voteRootUrl = domain + `/vote/admin/?showwxpaytitle=1#/`;


    let raffleApiPath = domain + `/raffle/rs/api`;
    let raffleRootUrl=domain + `/raffle/admin/?showwxpaytitle=1#/`;
    return Object.assign(baseObj, {
        shareItemLink:shareItemLink,
        dwzApiPath:dwzApiPath,
        dwzUrlPath:dwzUrlPath,
        yunApiPath:yunApiPath,
        pfApiPath: pfApiPath,
        agencyRootUrl: agencyRootUrl,
        payJsdkPath: payJsdkPath,
        agencyPath: agencyPath,
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
        authorSet:authorSet,



        bargainApiPath:bargainApiPath,
        bargainRootUrl:bargainRootUrl,
        voteApiPath:voteApiPath,
        voteRootUrl:voteRootUrl,
        raffleApiPath:raffleApiPath,
        raffleRootUrl:raffleRootUrl,
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
