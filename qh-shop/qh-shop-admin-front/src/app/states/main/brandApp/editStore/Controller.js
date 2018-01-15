import conf from "../../../../conf";
// import 'angular-bootstrap';
import store from "store";

import laydate from "layui-laydate";

var $scope,
    $rootScope,
    authService,
    loginService,
    $stateParams,
    $filter,
    $location,
    Upload,
    alertService,
    errorService,
    $state,
    $http;

class Controller {
    constructor(_$scope,_$rootScope, _$http, _loginService, _authService, _$filter, _$location, _Upload ,_alertService,_errorService,_$state,_$stateParams) {
        $scope = _$scope;
        $rootScope=_$rootScope;
        $http = _$http;
        $filter = _$filter;
        $location = _$location;
        $stateParams = _$stateParams;
        loginService = _loginService;
        Upload = _Upload;
        $state = _$state;
        alertService = _alertService;
        errorService = _errorService;
        authService = _authService;
        var vm = this;


        loginService.loginCtl(true);
        //权限相关
        $scope.ORDER_U = authService.hasAuthor("ORDER_U");    //发货
        $scope.ORDER_R = authService.hasAuthor("ORDER_R");    //查看
        $scope.ORDER_E = authService.hasAuthor("ORDER_E");    //导出
        $scope.brandAppId = $stateParams.brandAppId;
        //初始化


        $scope.store = {};
        $scope.clickSave = false;






        $scope.getInfo = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + '/shop/' + $stateParams.shopId,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
                params: {},
            }).then(function (response) {
                console.log('111111111',response);
                $scope.store = response.data.data;
                $scope.store.platformAccount = response.data.data.phone;
                $scope.store.type = response.data.data.shopTypeCode;
                $scope.imgs[0] = response.data.data.img;
                $scope.store.address = response.data.data.detailAddr;
                $scope.store.platformAccount = response.data.data.phone;
                $scope.store.phone = response.data.data.tel;

                if(response.data.data.provinceNo){
                    vm.provinceNo = response.data.data.provinceNo;
                    vm.cityNo = response.data.data.cityNo;
                    vm.countyNo = response.data.data.adcNo;
                    $scope.getAddress(vm.provinceNo,1);
                    $scope.getAddress(vm.cityNo,2)
                }else if(response.data.data.cityNo){
                    vm.provinceNo = response.data.data.cityNo;
                    vm.cityNo = response.data.data.adcNo;
                    $scope.getAddress(vm.provinceNo,1);
                    $scope.getAddress(vm.cityNo,2)
                }else {
                    vm.provinceNo = response.data.data.adcNo;
                }


            }, function (response) {});
        };

        $scope.getInfo();

        $scope.getAddress = function (type, level) {
            $http({
                method: "GET",
                url: conf.apiPath + "/common/queryAdc",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
                params: {
                    adc: type,
                },
            }).then(function successCallback(response) {
                console.log('111111111', level,response);
                if (response.status === 200) {
                    if (level === undefined) {
                        vm.provinces = response.data.data;
                    } else if (level === 1) {
                        vm.citys = response.data.data;
                    } else if (level === 2) {
                        vm.countys = response.data.data;
                    }
                }
            }, function errorCallback(response) {
            });
        };











        $scope.save = function () {
            // console.log('store===========',$scope.store);
            // console.log('img===========',$scope.imgs);
            // console.log('addressData===========',$scope.addressData);
            // console.log('provinceNo===========',vm.provinceNo);
            // console.log('cityNo===========',vm.cityNo);
            // console.log('countyNo===========', vm.countyNo);
            // console.log('provinceNo-List===========',vm.provinces);
            // console.log('cityNo-List===========',vm.citys);
            // console.log('countyNo-List===========', vm.countys);



            if($scope.clickSave){
                return;
            }else {
                $scope.clickSave = true;
            }



            if(!$scope.store.name){
                errorService.error('请填写门店名称');
                $scope.clickSave = false;
                return;
            }
            if(!$scope.store.type){
                errorService.error('请选择门店类型');
                $scope.clickSave = false;
                return;
            }
            if (!(/^1[34578]\d{9}$/.test($scope.store.phone))) {
                $scope.clickSave = false;
                errorService.error('联系方式有误，请重填');
                return false;
            }
            if(!(/^1[34578]\d{9}$/.test($scope.store.platformAccount))){
                errorService.error('负责人平台帐号有误，请重填');
                $scope.clickSave = false;
                return;
            }

            if(!vm.provinceNo){
                errorService.error('请选择完整的所属地区');
                $scope.clickSave = false;
                return;
            }
            if(vm.citys && vm.citys.list.length>0 && !vm.cityNo){
                errorService.error('请选择完整的所属地区');
                $scope.clickSave = false;
                return;
            }
            if(vm.countys && vm.countys.list.length>0 && !vm.countyNo){
                errorService.error('请选择完整的所属地区');
                $scope.clickSave = false;
                return;
            }

            if(!$scope.store.address){
                errorService.error('请填写详细地址');
                $scope.clickSave = false;
                return;
            }

            // if($scope.imgs.length<1){
            //     errorService.error('请上传门店照片');
            //     $scope.clickSave = false;
            //     return;
            // }

            if(vm.countyNo){
                $scope.store.adcNo = vm.countyNo;
            }else if(vm.cityNo){
                $scope.store.adcNo = vm.cityNo;
            }else {
                $scope.store.adcNo = vm.provinceNo;
            }

            $http({
                method: "PUT",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + '/shop/' + $stateParams.shopId,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
                data: {
                    phone: $scope.store.platformAccount,
                    name: $scope.store.name,
                    adcNo: $scope.store.adcNo,
                    address: $scope.store.address,
                    img: $scope.imgs[0],
                    tel: $scope.store.phone,
                    shopType: $scope.store.type,
                },
            }).then(function (response) {
                console.log('111111111',response);
                if(response.data.status == 200){
                    $scope.fallBack();
                }
            }, function (response) {
                $scope.clickSave = false;
            });
        };


        // $scope.addressData = {};
        //初始化地址
        vm.getAddress = function (type, level) {
            //限制二级地区时，三级地区码为上一次的情况，进行清空;
            vm.countyNo=undefined;
            $http({
                method: "GET",
                url: conf.apiPath + "/common/queryAdc",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
                params: {
                    adc: type,
                },
            }).then(function successCallback(response) {
                console.log('111111111', level,response);
                if (response.status === 200) {
                    if (level === undefined) {
                        vm.provinces = response.data.data;
                        // vm.citys=null;
                        // vm.countys=null;
                        // vm.provinceNo = null;
                        // vm.cityNo = null;
                        // vm.countyNo = null;
                    } else if (level === 1) {
                        vm.citys = response.data.data;
                        vm.countys=null;
                        vm.cityNo = null;
                        vm.countyNo = null;
                    } else if (level === 2) {
                        vm.countys = response.data.data;
                        vm.countyNo = null;
                    }
                }
            }, function errorCallback(response) {
            });
        };
        vm.getAddress();




        //图片上传
        $scope.imgs = [];
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
                    // console.log('Success ' + resp.config.data.file.name + 'uploaded. Response: ', resp.data);
                    $http({
                        method: 'GET',
                        url: conf.yunApiPath + '/app/5988791a6b869f4e18d5c8d5/org/598878fc6b869f4e0f19fb47/yunFile/' + resp.data.data
                    }).then(function (resp) {
                        //console.log('Success ' + resp.data.data.cdnUrls[0].url);
                        // 上传代码返回结果之后，将图片插入到编辑器中
                        $scope.imgs.push(resp.data.data.cdnUrls[0].url);
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
            $scope.imgs.splice(index, 1);
        };


        $scope.fallBack = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.store", null, {reload: true});
            } else {
                history.back();
            }
        }


    }
}

Controller.$inject = [
    '$scope', '$rootScope','$http', 'loginService', 'authService', '$filter', '$location','Upload','alertService','errorService','$state', '$stateParams'
];

export default Controller ;
