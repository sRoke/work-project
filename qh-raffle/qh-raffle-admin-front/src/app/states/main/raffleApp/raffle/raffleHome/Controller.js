import conf from "../../../../../conf";
// import "jquery";
var $scope,
    $rootScope,
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
                _$rootScope,
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
        $rootScope=_$rootScope;
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
        /////////////////////////////////
        loginService.loginCtl(true, $location.absUrl());

        $scope.clearKeyWord = function () {
            $scope.keyWord = '';
        };

        //搜索
        $scope.search = function () {
            $scope.pageTitle = '全部';
            $scope.status = 'all';
            $scope.pageChange(0);
        };

        $scope.focus = function (status) {
            if(status){
                $scope.searchShow = true;
            }else {
                $scope.searchShow = false;
            }
        };


        $scope.status = $stateParams.status ? $stateParams.status : "all";
        $scope.isClick = $stateParams.isClick ? $stateParams.isClick : "1";
        $scope.tabs = function (status, tableIndex, search) {
            $scope.status = status;
            // $scope.tableIndex = tableIndex;
            $scope.isClick = tableIndex;
            $scope.pageChange(0);
        };


        $scope.listDialogShow = function (raffle) {
            for(var i = 0 ; i< $scope.raffleList.content.length;i++){
                if($scope.raffleList.content[i].id == raffle.id){
                    raffle.listDialog = !raffle.listDialog;
                }else {
                    $scope.raffleList.content[i].listDialog = false;
                }
            }
        };



        $scope.currpage = 0;
        $scope.pageChange = function (currpage) {

            $http({
                method: "GET",      ///raffleApp/{raffleAppId}/raffle/page GET
                url: conf.apiPath + "/raffleApp/" + $stateParams.raffleAppId + '/raffle/page',
                params: {
                    page:currpage,
                    size:conf.pageSize,
                    status:$scope.status,
                    keyWord:$scope.keyWord,
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken()
                }
            }).then(function (resp) {
                $scope.currpage = currpage;
                    console.log('resprespresprespresp', resp);
                    if(currpage){
                        $scope.raffleList.number = resp.data.data.number;
                        for (var i=0;i< resp.data.data.content.length;i++){
                            $scope.raffleList.content.push(resp.data.data.content[i])
                        }
                    }else {
                        $scope.raffleList = resp.data.data;
                    }
                }, function () {
                    //error
                    $scope.cliclSave = false;
                }
            );
        }


        $scope.pageChange(0);



        $scope.changeStatus = function (raffle) {
            if(raffle.status == 'ENABLE'){      ///raffleApp/{raffleAppId}/raffle/{id}/enable  PUT
                alertService.confirm(null,'确认禁用?','温馨提示').then(function (data) {
                    if(data){
                        $http({
                            method: "PUT",
                            url: conf.apiPath + "/raffleApp/" + $stateParams.raffleAppId + '/raffle/' + raffle.id +'/enable',
                            params: {
                                enable: false,
                            },
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken()
                            }
                        }).then(function (resp) {
                            $scope.pageChange(0);
                        }, function () {
                        });
                    }
                })
            }else if(raffle.status == 'CLOSED'){
                alertService.confirm(null,'确认启用?','温馨提示').then(function (data) {
                    if(data){
                        $http({
                            method: "PUT",
                            url: conf.apiPath + "/raffleApp/" + $stateParams.raffleAppId + '/raffle/' + raffle.id +'/enable',
                            params: {
                                enable: true,
                            },
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken()
                            }
                        }).then(function (resp) {
                            $scope.pageChange(0);
                        }, function () {
                        });
                    }
                })
            }
        };






        $scope.getBackUrlId = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/raffleApp/" + $stateParams.raffleAppId + '/backToShop',
                params: {},
                headers: {
                    // 'Authorization': 'Bearer ' + loginService.getAccessToken()
                }
            }).then(function (resp) {
                $scope.brandAppId = resp.data.data.brandAppId;
                $scope.shopId = resp.data.data.shopId;
            }, function () {

            });
        };

        //$scope.getBackUrlId();


        //分享
        $scope.share = function (raffle) {
            $scope.shareUrl=conf.raffleWapRootUrl + 'raffleApp/' + $stateParams.raffleAppId + '/raffle/' + raffle.id +'/home';
            $scope.shareDesc=raffle.shareDesc;
            $scope.shareTitle=raffle.shareTitle;
            $scope.shareImg=raffle.dialImg;
            $scope.maskShow();
            $scope.wxCsh();
        };
        //分享
        $scope.wxCsh=function () {
            if (wxService.isInWx()) {
                var confWx = {
                    title: $scope.shareTitle,
                    desc: $scope.shareDesc,
                    link: $scope.shareUrl,
                    imgUrl: $scope.shareImg,
                    success: function () {
                        // 用户确认分享后执行的回调函数
                        // alert('success');
                        // $scope.maskHide();
                        $scope.$digest();
                    },
                    cancel: function () {
                        // 用户取消分享后执行的回调函数
                        // alert('cancel');
                        //$scope.maskHide();
                        $scope.$digest();
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
        };

        //遮罩打开与关闭
        $scope.mask = false;
        $scope.maskShow = function () {
            $scope.mask = true;
        };
        $scope.maskHide = function () {
            $scope.mask = false;
        };


        // https://kingsilk.net/raffle/local/?_ddnsPort=16600#/raffleApp/5a30f4e146e0fb00083bd03f/raffle/5a323e32a9233d2eaa273274/home
        //预览
        $scope.goView = function (id) {
            location.href = conf.raffleWapRootUrl + 'raffleApp/' + $stateParams.raffleAppId + '/raffle/' + id +'/home';
        };


        $scope.addLottery=function () {
            // ui-sref="main.raffleApp.raffle.raffleAdd({form:'home'})"
            $rootScope.lotteryList=[];
            $state.go("main.raffleApp.raffle.raffleAdd", {form:'home'}, {reload: true});
        };





        $scope.fallbackPage = function () {
           if($scope.brandAppId && $scope.shopId){
                location.href = conf.backShopUrl + 'brandApp/' + $scope.brandAppId + '/store/'+ $scope.shopId +'/marketing';
           }else {
               $http({
                   method: "GET",
                   url: conf.apiPath + "/raffleApp/" + $stateParams.raffleAppId + '/backToShop',
                   params: {},
                   headers: {
                       // 'Authorization': 'Bearer ' + loginService.getAccessToken()
                   }
               }).then(function (resp) {
                   $scope.brandAppId = resp.data.data.brandAppId;
                   $scope.shopId = resp.data.data.shopId;
                   location.href = conf.backShopUrl + 'brandApp/' + $scope.brandAppId + '/store/'+ $scope.shopId +'/marketing';
               }, function () {

               });
           }
        };





    }
}

Controller.$inject = [
    '$scope',
    '$rootScope',
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
