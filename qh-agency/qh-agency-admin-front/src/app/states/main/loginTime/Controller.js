import conf from "../../../conf";


var $scope,
    $state,
    $http,
    alertService,
    loginService;
class Controller {
    constructor(_$scope,
                _$state,
                _$http,
                _alertService,
                _loginService) {
        $scope = _$scope;
        $http = _$http;
        loginService = _loginService;
        $state = _$state;
        alertService = _alertService;
        ////登录类型，参数
        const loginType = {
            WX: "loginType=WX&autoCreateUser",
            WX_SCAN: "loginType=WX_SCAN&autoCreateUser",
            PASSWORD: "loginType=PASSWORD"
        };

        const TAG = "main/loginTime";
        console.log(`==> ${TAG}`);

        //获取用户手机号
        $scope.getUserPhone = () => {
            console.log(`${TAG} => getUserPhone`);

            $http({
                method: "GET",
                url: conf.oauthPath + "/api/user/info",
                //url: `https://login.kingsilk.net/local/16600/rs/api/user/info`,
                params: {},
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                }
            }).then(function (resp) {
                    console.log(`login  => /api/user/info => ${resp.data.data}`);
                    if (!resp.data.data.phone) {
                        //如果手机号不存在,跳转到绑定手机号页面
                        $state.go("main.bindPhone");``
                    } else {
                        //获取用户brandApp列表

                        // TODO  1. 检查 localStorage 中是否有 brandAppId, 且该 brandAppId真是有效，且当前用户是该 brandApp 的员工，
                        //          则直接跳转，return
                        //          清空 localStorage 中的 brandAppId

                        if (loginService.getbrandAppId()) {
                            $state.go("main.brandApp.home", {brandAppId:'59782691f8fdbc1f9b2c4323'}, {reload: true});
                        }
                        else {
                            $scope.getbrandAppList();
                        }
                    }
                }, function () {

                }
            );
        };

        ///获取用户brandApp列表
        $scope.getbrandAppList = () => {
            console.log(`${TAG} => getbrandAppList`);
            // $http({
            //     method: "GET",
            //     url: conf.apiPath + "/brandApp/bindPhoneAndList",
            //     params: {},
            //     headers: {
            //         'Authorization': 'Bearer ' + loginService.getAccessToken(),
            //     }
            // }).then(function (resp) {
            //         if (resp.data.data.recList.length == 0) {
            //             alert("暂无绑定的组织");
            //             return;
            //         }
            //         //TODO 选择brandApp页面
            //         //loginService.setbrandAppId(resp.data.data.recList[0].id);
            //
            //         console.log('brandAppId ============', resp.data.data.recList[0].id);
            //
                    $state.go("main.brandApp.home", {brandAppId:'59782691f8fdbc1f9b2c4323'}, {reload: true});
            //         //$scope.fallbackPage();
            //     }, function (resp) {
            //         alertService.msgAlert("cancle", resp.data.data.message);
            //     }
            // );
        };
        $scope.getUserPhone();
        ////登出
        $scope.logout = () => {
            jso.wipeTokens();
            loginService.setAccessToken(null);
            location.href = location.protocol + conf.oauthPath + "/logout";
        };
    }


}

Controller.$inject = [
    '$scope',
    '$state',
    '$http',
    'alertService',
    'loginService',

];

export default Controller ;
