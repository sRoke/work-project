import conf from "../../../../../../conf";
import weui from "weui.js";

var $scope,
    $stateParams,
    $httpParamSerializer,
    alertService,
    $http,
    loginService,
    wxService,
    $rootScope,
    $state;
class Controller {
    constructor(_$scope,
                _$stateParams,
                _$httpParamSerializer,
                _alertService,
                _$http,
                _loginService,
                _wxService,
                _$rootScope,
                _$state) {
        $scope = _$scope;
        $state = _$state;
        $http = _$http;
        loginService = _loginService;
        $stateParams = _$stateParams;
        wxService = _wxService;
        $httpParamSerializer = _$httpParamSerializer;
        alertService = _alertService;
        $rootScope = _$rootScope;
        loginService.loginCtl(true);
        $scope.brandAppId = $stateParams.brandAppId;
        $scope.storeId = $stateParams.storeId;
        ///////////////////////////////////////
        const TAG = "main/address ";
        console.log(`=> ${TAG}`);
        // loginService.loginCtl(true);



        $scope.pageTitle = '全部订单';
        $scope.checkTabsShow = false;
        $scope.checkTabs = function (index) {
            // console.log($scope.pageTitle);
            $scope.pageTitle = index;
            $scope.checkTabsShow = !$scope.checkTabsShow;
        };
        $scope.clearKeyWord = function () {
            $scope.keyWord = '';
        };

        $scope.express = '';
        $scope.expressList = [];

        $scope.openExpress = function () {
            weui.picker($scope.expressList, {
                depth: 1,
                // defaultValue: [0, 0, 0],
                onChange: function (result) {
                    // console.log('1', result);
                },
                onConfirm: function (result) {
                    // console.log(result);
                    $scope.express = result[0];
                    $scope.$apply();
                },
                id: 'multiPickerBtn'
            });
        };


        $http({
            method: "GET",
            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/order/getLogisticsCompanyEnum",
            data: $scope.skuData,
            headers: {
                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                "brandApp-Id": $scope.brandAppId
            },
        }).then(function (resp) {
            for(var key in resp.data.data){
                $scope.expressList.push({'label':resp.data.data[key],'value':key})
            }
        }, function (resp) {
            //TODO 错误处理
        });











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
                            $scope.scanCode = $scope.sysQrcode;
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
                                    $scope.scanCode = $scope.sysQrcode
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



        $scope.save = function () {
            if(!$scope.express){
                return    alertService.msgAlert("exclamation-circle", "请选择快递公司");
            }
            if(!$scope.scanCode){
                return    alertService.msgAlert("exclamation-circle", "请填写物流单号");
            }

            $http({
                method: "PUT",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $stateParams.storeId +  '/deliverInvoice/'+$stateParams.id,
                data: {
                    company:$scope.express.value,
                    expressNo:$scope.scanCode,
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                // console.log('resp-------------',resp);
                $scope.fallbackPage();
            }, function (resp) {alertService.msgAlert("exclamation-circle", resp.data.message);
                //TODO 错误处理
            });


        }



        /*返回上级*/
        $scope.fallbackPage = function () {
            history.back();
            // $state.go("main.brandApp.store.home", null, {reload: true});
        };
    }


}

Controller.$inject = [
    '$scope',
    '$stateParams',
    '$httpParamSerializer',
    'alertService',
    '$http',
    'loginService',
    'wxService',
    '$rootScope',
    '$state'
];

export default Controller ;
