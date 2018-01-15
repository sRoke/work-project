import conf from "../../../../../../conf";
import weui from "weui.js";
var $scope,
    loginService,
    wxService,
    $state,
    $stateParams,
    $location,
    alertService,
    $rootScope,
    $http
class Controller {
    constructor(_$scope,
                _loginService,
                _wxService,
                _$state,
                _$stateParams,
                _$location,
                _alertService,
                _$rootScope,
                _$http) {
        $scope = _$scope;
        loginService = _loginService;
        wxService=_wxService;
        $state = _$state;
        $stateParams = _$stateParams;
        $location =_$location;
        alertService=_alertService;
        $rootScope=_$rootScope;
        $http = _$http;
        $scope.brandAppId = $stateParams.brandAppId;
        $scope.storeId = $stateParams.storeId;
        $scope.refundId = $stateParams.refundId;
        loginService.loginCtl(true,$location.absUrl());
        //获取快递公司接口
        $scope.getInfo=function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/"+$scope.brandAppId+"/shop/"+ $scope.storeId+"/order/getLogisticsCompanyEnum",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                // console.log('resp-express',resp.data.data)
                $scope.expressData = [];
                $scope.express = resp.data.data;
                for (let key in $scope.express) {
                    $scope.expressData.push({
                        label: $scope.express[key],
                        value: key
                    });
                }
            }, function (resp) {
                //error
            });
        };
        $scope.getInfo();
        $scope.expressList = [];
        $scope.selectCompanys=false;
        $scope.openExpress = function () {
            weui.picker($scope.expressData, {
                depth: 1,
                // defaultValue: [0, 0, 0],
                onChange: function (result) {
                    // console.log('1', result);
                },
                onConfirm: function (result) {
                    // console.log(result);
                    $scope.express = result[0];
                    $scope.selectCompanys=true;
                    $scope.$apply();
                },
                id: 'multiPickerBtn'
            });
        };


        //扫描
        wxService.init().then(function (data) {
            if(data){
                $scope.initWX = true;
            }
        });


        $scope.clickWxSys = false;

        $scope.wxSys = function () {
            if($scope.clickWxSys){
                return;
            }else {
                $scope.clickWxSys = true;
            }
            if (wxService.isInWx()) {
                if ($scope.initWX) {
                    wx.scanQRCode({
                        desc: 'scanQRCode desc',
                        needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
                        scanType: ["qrCode", "barCode"], // 可以指定扫二维码还是一维码，默认二者都有
                        success: function (res) {
                            var result = res.resultStr.split(','); // 当needResult 为 1 时，扫码返回的结果
                            $scope.sysQrcode = result[result.length - 1];
                            $scope.logistics.expressNo = $scope.sysQrcode;
                            // $scope.showScanCodeAddDialog($scope.sysQrcode);
                            $scope.clickWxSys = false;
                            $scope.$digest();
                        },
                        error: function (res) {
                            $scope.clickWxSys = false;
                            if (res.errMsg.indexOf('function_not_exist') > 0) {
                                alertService.msgAlert("exclamation-circle", "版本过低请升级");
                            }
                        }
                    });
                } else {
                    wxService.init().then(function (data) {
                        if (data) {
                            wx.scanQRCode({
                                desc: 'scanQRCode desc',
                                needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
                                scanType: ["qrCode", "barCode"], // 可以指定扫二维码还是一维码，默认二者都有
                                success: function (res) {
                                    var result = res.resultStr.split(','); // 当needResult 为 1 时，扫码返回的结果
                                    $scope.sysQrcode = result[result.length - 1];
                                    $scope.logistics.expressNo = $scope.sysQrcode
                                    // $scope.showScanCodeAddDialog($scope.sysQrcode);
                                    $scope.clickWxSys = false;
                                    $scope.$digest();
                                },
                                error: function (res) {
                                    $scope.clickWxSys = false;
                                    if (res.errMsg.indexOf('function_not_exist') > 0) {
                                        alertService.msgAlert("exclamation-circle", "版本过低请升级");
                                    }
                                }
                            });
                        }
                    })
                }
            }else {
                $scope.clickWxSys = false;
            }
        };


        $scope.logistics={};
        $scope.save = () => {
            if (!$scope.express.value) {
                alertService.msgAlert('cancle', "请选择快递公司");
                return;
            };
            if (!$scope.logistics.expressNo) {
                alertService.msgAlert('cancle', "请填写快递单号");
                return;
            };

            $http({
                method: "PUT",   ///brandApp/{brandAppId}/shop/{shopId}/mall/refund/{id}/refundLogistics
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/"+$scope.storeId+"/mall/refund/" + $scope.refundId + "/refundLogistics",
                data: {
                    company: $scope.express.value,
                    expressNo: $scope.logistics.expressNo
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    'brandApp-Id': $scope.brandAppId
                }
            }).then(function (resp) {
                    // alertService.msgAlert('success', '申请成功');
                   // console.log('success-resp',resp)
                    if(resp.data.status=='200'){
                        $state.go("main.brandApp.store.personalCenter.refundOrder", {brandAppId:$stateParams.brandAppId}, {reload: true});
                    }
                }, function () {

                }
            );
        };
        //
        /*返回上级*/
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.store.home", {brandAppId:$stateParams.brandAppId}, {reload: true});
            } else {
                history.back();
            }
        };
    }
}

Controller.$inject = [
    '$scope',
    'loginService',
    'wxService',
    '$state',
    '$stateParams',
    '$location',
    'alertService',
    '$rootScope',
    '$http',
];

export default Controller ;
