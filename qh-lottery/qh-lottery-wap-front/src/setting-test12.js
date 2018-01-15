(function () {
    const devPort = "11200";              //开发端口
    const apiPort = "11200";              //api端口，支付默认api端口
    let domain = "//kingsilk.net";
    let rootPath = domain + `/qh/lottery/local/?_ddnsPort=${devPort}`;
    let apiPath = domain + `/qh/mall/local/${apiPort}/api`;
    let oauthPath = `//kingsilk.net/oauth/local/${apiPort}/rs`;
    let wxloginPath = `${oauthPath}/oauth/authorize?`;
    let token = `qh-lottery-wap-front.${devPort}.token`;

    var appConfig = {
        imgUrl: "//img.kingsilk.net/",   // 图片地址
        rootPath: rootPath,
        apiPath: apiPath,
        wxloginPath: wxloginPath,
        token: token,
        oauthPath: oauthPath
    };

    window.appConfig = appConfig;
})();
