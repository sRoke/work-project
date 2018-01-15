import conf from "../../../../../conf";

import cropper from 'cropperjs';

import PhotoClip from 'photoclip';



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
    alertService,
    $rootScope,
    $timeout,
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
                _alertService,
                _$rootScope,
                _$timeout,
                _$templateCache) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        loginService = _loginService;
        $log = _$log;
        $q = _$q;
        $rootScope = _$rootScope;
        $templateCache = _$templateCache;
        Upload = _Upload;
        $location = _$location;
        $timeout = _$timeout;
        alertService = _alertService;
        $stateParams = _$stateParams;
        $scope.brandAppId = $stateParams.brandAppId;
        /////////////////////////////////

        loginService.loginCtl(true, $location.absUrl());
        $scope.brandAppId = $stateParams.brandAppId;

        $scope.user = {};
        $scope.user.desc = '什么都别说,投我!';
        $scope.tabs = 1;
        $scope.checkTabs = function (tabIndex) {
            console.log(tabIndex);
            $scope.tabs = tabIndex;
        };


        $scope.currentPage = 1;
        $scope.changePage = function () {
            console.log($scope.currentPage);
        }

        $scope.choosePhote = false;

        var pc = new PhotoClip('#clipArea', {
            size: [260,260],
            outputSize: 640,
            // adaptive: ['70','40'],
            file: '#file,#file2',
            view: '#view',
            ok: '#clipBtn',
            style:{
                maskColor:'rgba(0,0,0,0.7)',
                // jpgFillColor:''
            },
            //img: 'img/mm.jpg',
            loadStart: function() {
                console.log('开始读取照片');
            },
            loadComplete: function() {
                console.log('照片读取完成',$scope);
                $scope.choosePhote = true;
                $scope.$apply();
            },
            done: function(dataURL) {
                console.log('base64裁剪完成,正在上传');
                $scope.saveImg(dataURL);
            },
            fail: function(msg) {
                alert(msg);
            }
        });

        $scope.saveImg = function (dataUrl) {
            $http({
                method: "POST",
                url: conf.yunApiPath + "/app/5988791a6b869f4e18d5c8d5/org/598878fc6b869f4e0f19fb47/yunFile/b64",
                data: {
                    base64DataUrl: dataUrl
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken()
                }
            }).then(function (resp) {
                    $scope.dataUrl = resp.data.data;
                    $scope.getImg($scope.dataUrl);
                    console.log(resp.data.data)

                }, function () {
                    //error
                }
            );
        };
        $scope.getImg = function (id) {
            $http({
                method: "GET",
                url: conf.yunApiPath + "/app/5988791a6b869f4e18d5c8d5/org/598878fc6b869f4e0f19fb47/yunFile/" + id,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken()
                }
            }).then(function (resp) {

                    console.log(resp.data.data);
                    $scope.imgs = resp.data.data.cdnUrls[0].url;
                    // $scope.realSave(resp.data.data.cdnUrls[0].url)
                    $scope.choosePhote = false;
                }, function () {
                    //error
                }
            );
        };


        $scope.cancelChoosePhote = function () {
            $scope.choosePhote = false;
        }

        // $scope.clipBtnShow = function () {
        //     $scope.choosePhote = false;
        // }



        $scope.fallBack = function (route,params) {
            $state.go(route,params)
        };

        //图片上传
        $scope.uploading = function (file) {
            $scope.f = file;
            // $scope.errFile =errFiles && errFiles[0];
            if (file) {
                Upload.upload({
                    url: conf.yunApiPath + '/app/5988791a6b869f4e18d5c8d5/org/598878fc6b869f4e0f19fb47/yunFile',
                    data: {
                        file: file,
                    }
                }).then(function (resp) {
                    console.log('Success ' + resp.config.data.file.name + 'uploaded. Response: ', resp.data);
                    $http({
                        method: 'GET',
                        url: conf.yunApiPath + '/app/5988791a6b869f4e18d5c8d5/org/598878fc6b869f4e0f19fb47/yunFile/' + resp.data.data
                    }).then(function (resp) {
                        console.log('Success ' + resp.data.data.cdnUrls[0].url);
                        $scope.imgs = resp.data.data.cdnUrls[0].url;
                        // 上传代码返回结果之后，将图片插入到编辑器中
                    }, function (resp) {
                        console.log('Error status: ' + resp.status);
                    });
                }, function (resp) {
                    console.log('Error status: ' + resp.status);
                }, function (evt) {
                    var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                    console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
                });
            }

        };
        /**
         * 删除指定的图片
         * @param index
         */
        $scope.cancelDel = function (index) {
            $scope.imgs = '';
        };





        //                 获取用户信息

        $scope.getUserInfo = function () {
            $http({
                method: 'GET',
                url: conf.oauthPath + "/api/user/info",
                data: {},
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $scope.user.phone = resp.data.data.phone;
            })
        };

        $scope.getUserInfo();

        $scope.clickSave = true;
        $http({
            method: 'GET',
            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + '/voteActivity/' + $stateParams.activityId + '/isFollow',
            params: {
                shareUrl: $location.absUrl(),
            },
            headers: {
                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                "brandApp-Id": $scope.brandAppId
            }
        }).then(function (resp) {
            if(resp.data.status == 10028){
                // ------------------------------------------二维码弹窗不需要了
                $mdDialog.show({
                    templateUrl: 'qrCode.html',
                    parent: angular.element(document.body),
                    clickOutsideToClose: false,   //true 点击界外的时候弹窗消失
                    fullscreen: false,
                    controller: ['$mdDialog', function ($mdDialog) {
                        var vmd = this;
                        vmd.qrCode = resp.data.data;
                        vmd.cancel = function () {
                            console.log(11111111111111)
                            $state.go('main.brandApp.vote.votHome');
                            return $mdDialog.cancel();
                        };
                        vmd.goAddress = function () {
                        }
                    }],
                    controllerAs: "vmd"
                }).then(function (answer) {
                }, function () {
                });
            }else {
                $scope.clickSave = false;
            }
        });














        $scope.save = function () {
            if($scope.clickSave){
                return;
            }else {
                $scope.clickSave = true;
            }

            if(!$scope.user.name){
                $scope.clickSave = false;
                return alertService.msgAlert("exclamation-circle", "姓名不能为空");
            }
            if (!(/^1[34578]\d{9}$/.test($scope.user.phone))) {
                $scope.clickSave = false;
                alertService.msgAlert("exclamation-circle", "手机号码有误，请重填");
                return false;
            }
            if(!$scope.imgs){
                $scope.clickSave = false;
                return  alertService.msgAlert("exclamation-circle", "参赛照片不能为空");
            }
            if(!$scope.user.desc){
                $scope.clickSave = false;
                return  alertService.msgAlert("exclamation-circle", "参赛宣言不能为空");
            }



            $http({
                method: 'GET',
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + '/activity/' + $stateParams.activityId + '/voteWorks/s/isSignup',
                params: {},
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                console.log('aaaaaaaaaaaaaaaa',resp);
                $rootScope.wxComAppId = resp.data.data.wxComAppId;
                $rootScope.wxMpAppId = resp.data.data.wxMpAppId;
                $http({
                    method: 'POST',
                    url: conf.apiPath + "/brandApp/" + $scope.brandAppId +'/activity/'+$stateParams.activityId+ "/voteWorks",
                    data: {
                        name:$scope.user.name,
                        phone:$scope.user.phone,
                        worksImgUrl:$scope.imgs,
                        slogan:$scope.user.desc,
                        wxComAppId:$rootScope.wxComAppId,
                        wxMpAppId:$rootScope.wxMpAppId,
                        wxheadImg:resp.data.data.wxheadImg,
                        nickName:resp.data.data.nickName,
                        openId:resp.data.data.openId
                    },
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    }
                }).then(function (resp) {
                    // $scope.data = resp.data.data;
                    if(resp.data.status == 10024) {
                        // alertService.error(resp.data.data);
                        alertService.msgAlert("exclamation-circle", resp.data.data);
                        $scope.fallBack('main.brandApp.vote.votHome');
                        $scope.clickSave = false;
                        return;
                    }else if(resp.data.status == 200) {
                        alertService.msgAlert("exclamation-circle", '报名成功!马上为您审核!').then(function () {
                            $scope.fallBack('main.brandApp.vote.myVote',{id:resp.data.data});
                            $scope.clickSave = false;
                        });
                    }
                },function (error) {
                    $scope.clickSave = false;
                })
            },function (error) {
                $scope.clickSave = false;
            })











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
    'alertService',
    '$rootScope',
    '$timeout',
    '$templateCache'
];

export default Controller ;
