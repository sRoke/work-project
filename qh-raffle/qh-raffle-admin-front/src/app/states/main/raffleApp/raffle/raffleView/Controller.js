import conf from "../../../../../conf";
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
    Upload,
    wxService,
    $interval,
    alertService,
    $templateCache;
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
                _Upload,
                _wxService,
                _$interval,
                _alertService,
                _$templateCache) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        loginService = _loginService;
        $log = _$log;
        $q = _$q;
        alertService = _alertService;
        $interval = _$interval;
        $templateCache = _$templateCache;
        Upload = _Upload;
        $location = _$location;
        wxService = _wxService;
        $stateParams = _$stateParams;
        $scope.raffleAppId = $stateParams.raffleAppId;
        $scope.id =$stateParams.id;
        /////////////////////////////////
        loginService.loginCtl(true, $location.absUrl());

        $scope.editRaffle=function(){
            $state.go("main.raffleApp.raffle.raffleEdit", {
                id:$stateParams.id,
            })
        };

        $scope.goEdit = function (type) {
            $state.go("main.raffleApp.raffle.textImg", {
                from: 'view',
                type: type,
                id:$stateParams.id,
            })
        };
        //设置奖项
        $scope.setLottery=function () {
            $state.go("main.raffleApp.raffle.setLottery", {
                from: 'view',
                id:$stateParams.id,
            })
        };


        $scope.getData = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/raffleApp/" + $stateParams.raffleAppId + '/raffle/' + $stateParams.id ,
                params: {},
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken()
                }
            }).then(function (resp) {
                console.log('resprespresprespresp', resp);
                $scope.raffleDataview = resp.data.data;
                    // $rootScope.rootraffleDataView = resp.data.data;

                //分享
                if (wxService.isInWx()) {
                    var confWx = {
                        title: $scope.raffleDataview.shareTitle,
                        desc: $scope.raffleDataview.shareDesp,
                        link: conf.raffleWapRootUrl + 'raffleApp/' + $stateParams.raffleAppId + '/raffle/' +  $stateParams.id +'/home',
                        imgUrl: $scope.raffleDataview.dialImg,
                        success: function () {
                            // 用户确认分享后执行的回调函数
                            // alert('success');
                            $scope.maskHide();
                            $scope.$digest();
                        },
                        cancel: function () {
                            // 用户取消分享后执行的回调函数
                            // alert('cancel');
                            $scope.maskHide();
                            $scope.$digest()
                        }
                    };
                    if ($scope.wxInit) {
                        wxService.shareRing(confWx);
                        wxService.shareFriend(confWx);
                    } else {
                        wxService.init().then(function (data) {
                            if (data) {
                                $scope.wxInit = true;
                                wxService.shareRing(confWx);
                                wxService.shareFriend(confWx);
                            }
                        });
                    }
                }
            }, function () {
                //error
                $scope.cliclSave = false;
            });
        };
        $scope.getData();
        $scope.changeStatus = function (raffle) {
            console.log('raffle==============',raffle);
            if(raffle.raffleStatus == 'ENABLE'){
                alertService.confirm(null,'确认禁用?','温馨提示').then(function (data) {
                    if(data){
                        $http({
                            method: "PUT",
                            url: conf.apiPath + "/raffleApp/" + $stateParams.raffleAppId + '/raffle/' + $stateParams.id +'/enable',
                            params: {
                                enable: false,
                            },
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken()
                            }
                        }).then(function (resp) {
                            $scope.getData();
                        }, function () {
                        });
                    }


                })
            }else if(raffle.raffleStatus == 'CLOSED'){
                alertService.confirm(null,'确认启用?','温馨提示').then(function (data) {
                    if(data){
                        $http({
                            method: "PUT",
                            url: conf.apiPath + "/raffleApp/" + $stateParams.raffleAppId + '/raffle/' + $stateParams.id +'/enable',
                            params: {
                                enable: true,
                            },
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken()
                            }
                        }).then(function (resp) {
                            $scope.getData();
                        }, function () {
                        });
                    }
                })
            }
        };




      //预览
        $scope.goView = function (id) {
            location.href = conf.raffleWapRootUrl + 'raffleApp/' + $stateParams.raffleAppId + '/raffle/' + $stateParams.id +'/home';
        }









        $scope.share = function (id) {
            $scope.maskShow();
        };

        //遮罩打开与关闭
        $scope.mask = false;
        $scope.maskShow = function () {
            $scope.mask = true;
        };
        $scope.maskHide = function () {
            $scope.mask = false;
        };






        $scope.fallbackPage = function () {
            $state.go("main.raffleApp.raffle.raffleHome", {}, {reload: true});
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
    'Upload',
    'wxService',
    '$interval',
    'alertService',
    '$templateCache'
];

export default Controller ;
