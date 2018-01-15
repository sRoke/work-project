import conf from "../../../../../../conf";
import PhotoClip from 'photoclip';
import weui from 'weui.js';
var $scope,
    loginService,
    $state,
    $stateParams,
    $location,
    $rootScope,
    $http
class Controller {
    constructor(_$scope,
                _loginService,
                _$state,
                _$stateParams,
                _$location,
                _$rootScope,
                _$http) {
        $scope = _$scope;
        loginService = _loginService;
        $state = _$state;
        $stateParams = _$stateParams;
        $location =_$location;
        $rootScope=_$rootScope;
        $http = _$http;
        $scope.brandAppId = $stateParams.brandAppId;
        $scope.storeId = $stateParams.storeId;
        loginService.loginCtl(true,$location.absUrl());
        $scope.go = function (state) {
            $state.go(state);
        };
        //编辑保存切换
        $scope.isSave=false;
        $scope.changeStatus=function () {
            $scope.isSave=!$scope.isSave;
        };
        $scope.getInfo=function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/"+$scope.brandAppId+"/shop/"+ $scope.storeId,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $scope.data=resp.data.data;
                vm.adcNo = $scope.data.adcNo;
                vm.province=$scope.data.province;
                vm.city=$scope.data.city;
                vm.area=$scope.data.area;
            }, function (resp) {
                //error
            });
        };
        $scope.getInfo();
        //

        var vm = this;
        //打开地址弹窗
        $scope.openAdcDialog = function () {


            // console.log($rootScope.adc.data);

            weui.picker($rootScope.adc.data, {
                depth: 3,
                defaultValue: [0, 0, 0],
                onChange: function (result) {
                    for (let i = 0; i < result.length; i++) {
                        $scope.adcNo = result[result.length - 1].value;
                    }
                },
                onConfirm: function (result) {            //点击确定
                    for (let i = 0; i < result.length; i++) {
                        if (result.length == 3) {
                            vm.province = result[result.length - 3].label;
                            vm.city = result[result.length - 2].label;
                            vm.area = result[result.length - 1].label;
                        } else if (result.length == 2) {
                            vm.province = result[result.length - 2].label;
                            vm.city = result[result.length - 1].label;
                        } else {
                            vm.province = result[result.length - 1].label;
                        }
                        vm.adcNo = result[result.length - 1].value;
                    }
                    // console.log('result1',result,vm.adcNo);
                    $scope.$apply();
                },
                id: 'cascadePicker'
            });
        };
        //test
        var pc = new PhotoClip('#clipArea', {
            size: [260,260],
            outputSize: 640,
            // adaptive: ['70','40'],
            file: '#file',
            view: '#view',
            ok: '#clipBtn',
            style:{
                maskColor:'rgba(0,0,0,0.7)',
                // jpgFillColor:''
            },
            //img: 'img/mm.jpg',
            loadStart: function() {
                // console.log('开始读取照片');
            },
            loadComplete: function() {
                // console.log('照片读取完成',$scope);
                $scope.choosePhote = true;
                $scope.$apply();
            },
            done: function(dataURL) {
                // console.log('base64裁剪完成,正在上传');
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
                    // console.log(resp.data.data)

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
                    $scope.data.img = resp.data.data.cdnUrls[0].url;
                    // console.log('imgurl',$scope.data.img );
                    // $scope.realSave(resp.data.data.cdnUrls[0].url)
                    $scope.choosePhote = false;
                }, function () {
                    //error
                }
            );
        };
        //取消图片保存按钮
        $scope.cancelChoosePhote = function () {
            $scope.choosePhote = false;
        };
        //保存
        $scope.save=function () {
            // /console.log('saveimgurl',$scope.data.img );
            $http({
                method: "PUT",
                url: conf.apiPath + "/brandApp/"+$scope.brandAppId+"/shop/"+ $scope.storeId,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
                data:{
                    name:$scope.data.name,
                    img:$scope.data.img,
                    adcNo:vm.adcNo,
                    address:$scope.data.detailAddr,
                    tel:$scope.data.tel
                },
            }).then(function (resp) {
                // console.log(resp.data.data);
                if(resp.data.data=='success'){
                    $scope.getInfo();
                    $scope.changeStatus();
                }
            }, function (resp) {
                //error
            });
        };
        /*返回上级*/
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.store.shop.shopMange", {brandAppId:$stateParams.brandAppId}, {reload: true});
            } else {
                history.back();
            }
        };
    }
}

Controller.$inject = [
    '$scope',
    'loginService',
    '$state',
    '$stateParams',
    '$location',
    '$rootScope',
    '$http',
];

export default Controller ;
