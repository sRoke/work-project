import conf from "../../../conf";


var $scope,
    $state,
    $http,
    alertService,
    loginService,
    $stateParams;
class Controller {
    constructor(_$scope,
                _$state,
                _$http,
                _alertService,
                _loginService,
                _$stateParams) {
        $scope = _$scope;
        $http = _$http;
        loginService = _loginService;
        $state = _$state;
        alertService = _alertService;
        $stateParams = _$stateParams;
        ////登录类型，参数
        const loginType = {
            WX: "loginType=WX&autoCreateUser",
            WX_SCAN: "loginType=WX_SCAN&autoCreateUser",
            WX_QYH: "loginType=WX_QYH&autoCreateUser",
            WX_QYH_SCAN: "loginType=WX_QYH_SCAN&autoCreateUser",
            PASSWORD: "loginType=PASSWORD",
            PHONE:'loginType=PHONE',
            WAPPHONE:'loginType=WAPPHONE',
        };

        const TAG = "main/Login";
        console.log(`==> ${TAG}`);


        ////登录
        $scope.login = function () {
            let type = "PHONE";
            console.log('branComId',$stateParams.brandAppId, $stateParams.backUrl);

            $scope.wxMpAppId = "wx7cc0b96add4254b1";

            $http({
                method: "GET",
                url: conf.pfApiPath + "/brandApp/"+ $stateParams.brandAppId,
                headers: {
                    // 'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandAppp-Id": $scope.brandAppId
                },
                data: {}
            }).success(function (resp) {
                console.log(resp);
                global.jso.config.config.redirect_uri = $stateParams.backUrl;
                global.jso.config.config.authorization = `${conf.wxloginPath}${loginType[type]}&wxMpAppId=${resp.data.wxMpId}`;
                global.jso.getToken((token) => {
                    loginService.setAccessToken(token.access_token);
                    console.log("I got the token: ", token);
                    if($stateParams.backUrl){
                        location.href = $stateParams.backUrl;
                    }

                }, {});

            });



        };




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
                    console.log(`login  => /api/user/info => ${resp.data.data}`)
                    if (!resp.data.data.phone) {
                        //如果手机号不存在,跳转到绑定手机号页面
                        $state.go("main.bindPhone");
                    } else {
                        //获取用户brandApp列表

                        // TODO  1. 检查 localStorage 中是否有 brandAppId, 且该 brandAppId真是有效，且当前用户是该 brandApp 的员工，
                        //          则直接跳转，return
                        //          清空 localStorage 中的 brandAppId

                        if (loginService.getbrandAppId()) {
                            // $state.go("main.brandApp.home");
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
            //         //$scsope.fallbackPage();
            //     }, function (resp) {
            //         alertService.msgAlert("cancle", resp.data.data.message);
            //     }
            // );
        };
        if (global.at) {
            console.log('ewqwewdfsdfsd =================')
            $scope.login();
        }

        ////登出
        $scope.logout = () => {
            jso.wipeTokens();
            loginService.setAccessToken(null);
            loginService.setbrandAppId(null);
            $http({
                method: "POST",
                url: "https:" + conf.oauthPath +"/logout",
                headers: {},
                params: {},
                withCredentials:true,
            }).success(function (resp) {
                // console.log('data', resp);
                location.reload();
            },function(resp){
                console.log('ERR', resp);
            });
        };


        // $scope.login('PASSWORD');
        $scope.login()
    }


}

Controller.$inject = [
    '$scope',
    '$state',
    '$http',
    'alertService',
    'loginService',
    '$stateParams'
];

export default Controller ;
