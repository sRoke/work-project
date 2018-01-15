import conf from "../../conf";
import store from "store";

loginServiceFactory.$inject = ['$q', '$http', '$state','$location','$stateParams'];
function loginServiceFactory($q, $http, $state,$location,$stateParams) {

    const TAG = "loginService";

    let setbrandAppId = (brandAppId) => {
        store.set(conf.brandAppId, brandAppId);
    };

    let setAccessToken = (token) => {
        store.set(conf.token, token);
    };

    let getbrandAppId = () => {
        store.get(conf.brandAppId);
        return store.get(conf.brandAppId);
    };

    let getAccessToken = () => {
        var at = store.get(conf.token);
        console.log("-------------getAccessToken() = ", at);
        return at;
    };

    /**
     * 判断是否登录，未登录跳转到登录页
     * @param required
     */
    let loginCtl = (required) => {
        console.log(`${TAG} => loginCtl`);
        console.log(getAccessToken());
        if (!required) {
            return;
        }
        if (!getAccessToken()) {
           console.log("未登录，跳转到登录页");
            $state.go("main.login",({backUrl:$location.absUrl(),brandAppId:$stateParams.brandAppId}))
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

    return {
        loginCtl: loginCtl,
        setbrandAppId: setbrandAppId,
        setAccessToken: setAccessToken,
        getbrandAppId: getbrandAppId,
        getAccessToken: getAccessToken,
        getLocalStorage: getLocalStorage,
        setLocalStorage: setLocalStorage,
        isUpdateApp: true,
        pages: {item: 0}
    };
}

export default  loginServiceFactory;