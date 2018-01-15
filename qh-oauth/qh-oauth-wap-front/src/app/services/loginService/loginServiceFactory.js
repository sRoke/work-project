import conf from "../../conf";
import store from "store";

loginServiceFactory.$inject = ['$q', '$http', '$state', '$stateParams', '$location'];
function loginServiceFactory($q, $http, $state, $stateParams, $location) {

    const TAG = "loginService";

    let setBrandAppId = (brandAppId) => {
        store.set(conf.brandAppId, brandAppId);
    };

    let setAccessToken = (token) => {
        store.set(conf.token, token);
    };

    let getBrandAppId = () => {
        store.get(conf.brandAppId);
        return store.get(conf.brandAppId);
    };

    let getAccessToken = () => {
        if (!store.get(conf.token)) {
            store.set(conf.token, null);
            return;
        }
        store.get(conf.token);
        return store.get(conf.token);
    };
    let getCheck = () => {
        console.log(111);
        $http({
            method: "GET",
            url: conf.apiPath + "/brandApp/" + $stateParams.brandAppId + "/partner/check",
            data: {},
            headers: {
                'Authorization': 'Bearer ' + store.get(conf.token),
                'brandApp-Id': $stateParams.brandAppId,
            }
        }).then(function (resp) {
                console.log('respTop,check', resp);
            }, function (error) {
                console.log(error);
            }
        );
    };
    // getCheck();
    /**
     * 判断是否登录，未登录跳转到登录页
     * @param required
     * @param backUrl
     */
    let loginCtl = (required, backUrl) => {
        console.log(`${TAG} => loginCtl`);

        if (!required) {
            return;
        }
        if (!getAccessToken()) {
            // alert('未登录，跳转到登录页');
            console.log("未登录，跳转到登录页");
            // TODO 登录跳转前访问的最后一个URl(backUrl) 保存到 localStorage 中
            store.set('login_backUrl', backUrl);
            $state.go("main.wxLogin", {backUrl: backUrl})
        } else {
            // alert('loginCtl');
            // return getUserCtl(true,backUrl);
        }

    };


    /**
     * 判断是否加入，未加入跳转到加入页
     * @param getUser
     * @param backUrl
     */
    let getUserCtl = (getUser, backUrl) => {
        console.log(`${TAG} => getUser`);
        // alert(`${TAG} => getUser`);
        if (getUser) {
            $http({
                method: "get",
                url: conf.apiPath + "/member/info",
                headers: {
                    'Authorization': 'Bearer ' + getAccessToken(),
                    'brandApp-Id': $stateParams.brandAppId,
                }
            }).then(function (data) {
                    // alert('/member/info');
                    // alert(data.data.status);
                    if (data.data.status == 301) {
                        $state.go("main.brandApp.joinUs");
                    }
                }, function (error) {
                    console.log('error', error);
                    if (error.status == 401) {
                        // alert('token过期，跳转到登录页');
                        store.set(conf.token, null);
                        store.remove(conf.token);
                        //jso.wipeTokens();
                        store.set('login_backUrl', backUrl);
                        $state.go("main.wxLogin", {backUrl: backUrl})
                    }
                }
            );

        }
    };


    /**
     * 获取localStorage内容
     * @param key
     */
    let getLocalStorage = (key) => {
        return store.get(key);
    };

    /**
     * 设置localStorage
     * @param key
     * @param value
     */
    let setLocalStorage = (key, value) => {
        store.set(key, value);
    };


    /**
     * 获取登录方式getLoginType
     * @param type
     */
    let getLoginType = (type) => {
        if (type == 'PHONE') {                 //--------------------------------手机号验证码登录
            // $state.go("main.login.phone", {
            //     backUrl:$location.absUrl(),
            // });
            //判断手机端还是pc端
            if ((navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i))) {
                $state.go("main.login.wapPhone", {
                    backUrl: $location.absUrl(),
                });
                // alert('手机端')
            } else {
                $state.go("main.login.phone", {
                    backUrl: $location.absUrl(),
                });
                // alert('pc端')
            }
        }
        // else if(type == 'WAPPHONE'){
        //         $state.go("main.login.wapPhone", {
        //             backUrl:$location.absUrl(),
        //         });
        // }
        else if (type == 'WX') {

            console.log('type == WX')

        } else if (type == 'WX_SCAN') {                              //------------------------------自己的微信登录   可能仅用于platform
            $state.go("main.login.wxMp", {
                backUrl: $location.absUrl(),
            });
        } else if (type == 'WX_SCAN_third') {                       //-------------------------------第三方公众好平台登录
            $state.go("main.login.wxComMp", {
                backUrl: $location.absUrl(),
                wxComAppId: $stateParams.wxComAppId,
                wxMpAppId: $stateParams.wxMpAppId,
            });
        }
    };

    return {
        getLoginType: getLoginType,
        getUserCtl: getUserCtl,
        loginCtl: loginCtl,
        setBrandAppId: setBrandAppId,
        setAccessToken: setAccessToken,
        getBrandAppId: getBrandAppId,
        getAccessToken: getAccessToken,
        getLocalStorage: getLocalStorage,
        setLocalStorage: setLocalStorage,
        getCheck: getCheck,
        isUpdateApp: true,
        pages: {item: 0}
    };
}

export default  loginServiceFactory;