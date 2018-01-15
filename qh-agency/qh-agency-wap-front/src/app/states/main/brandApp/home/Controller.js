import conf from "../../../../conf";
// import "jquery";
var $scope,
    $http,
    $state,
    $log,
    loginService,
    $mdDialog,
    $stateParams,
    $location,
    $q,
    Upload;
class Controller {
    constructor(_$scope,
                _$http,
                _$state,
                _$log,
                _loginService,
                _$mdDialog,
                _$stateParams,
                _$location,
                _$q,
                _Upload) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        loginService = _loginService;
        $log = _$log;
        $q = _$q;
        Upload = _Upload;
        $location = _$location;
        $stateParams = _$stateParams;
        $scope.brandAppId = $stateParams.brandAppId;
        /////////////////////////////////

        loginService.loginCtl(true, $location.absUrl());
        $scope.brandAppId = $stateParams.brandAppId;

        $scope.goto = function (data) {
            var path = 'https:'+conf.shopUrl+'brandApp/'+$scope.brandAppId+data;
            console.log(path);
            window.location=path;
        };



        $scope.getData = function () {
            $http({
                method: 'GET',
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/123/report",
                params: '',
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                console.log(resp);
                $scope.data = resp.data.data;
            })
        };
        $scope.getData();


        ////登出   --------------------------------测试威信扫码的代码
        $scope.logout = () => {
            // https://kingsilk.net/qh/mall/local/11200/api
            function init() {
                var deferred = $q.defer();
                var ua = window.navigator.userAgent.toLowerCase();
                if (ua.match(/MicroMessenger/i) && !ua.match(/windows/i)) {
                    $http.get('https://kingsilk.net/qh/mall/local/11300/api' + '/weiXin/jsSdkConf', {
                        params: {
                            url: location.href.split('#')[0]
                        }
                    }).then(function (resp) {
                        resp.data.jsApiConf.debug = false;
                        console.info("已经获取了微信JS SDK 的配置对象", resp.data.jsApiConf);
                        //$scope.wxReady = true;
                        wx.config(resp.data.jsApiConf);
                        wx.isConfig = true;
                        wx.error(function (res) {
                            console.info("微信调用出错了 ", res);
                        });
                        wx.ready(function () {
                            deferred.resolve(true);
                        });

                    });
                } else {
                    deferred.resolve(false);
                }
                return deferred.promise;
            }

            init().then(function () {
                wx.scanQRCode({
                    desc: 'scanQRCode desc',
                    needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
                    scanType: ["qrCode", "barCode"], // 可以指定扫二维码还是一维码，默认二者都有
                    success: function (res) {
                        // 回调
                        var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
                    },
                    error: function (res) {
                        if (res.errMsg.indexOf('function_not_exist') > 0) {
                            alert('版本过低请升级')
                        }
                    }
                });
            });
        };

    }
}

Controller.$inject = [
    '$scope',
    '$http',
    '$state',
    '$log',
    'loginService',
    '$mdDialog',
    '$stateParams',
    '$location',
    '$q',
    'Upload'
];

export default Controller ;
