import conf from "../../../../../conf.js";
import weui from 'weui.js';

var $scope,
    $stateParams,
    $http,
    alertService,
    loginService,
    $state,
    $location;
class Controller {
    constructor(_$scope,
                _$stateParams,
                _$http,
                _alertService,
                _loginService,
                _$state,
                _$location) {
        $scope = _$scope;
        $http = _$http;
        $location = _$location;

        alertService = _alertService;
        loginService = _loginService;
        $state = _$state;
        $stateParams = _$stateParams;

        $scope.brandAppId = $stateParams.brandAppId;

        const TAG = "main/order/refund ";
        console.log(`=> ${TAG}`);
        loginService.loginCtl(true, $location.absUrl());

        $scope.deliver = {};
        $scope.logistics = {};    //
        $scope.deliverList = {};       //获取的数据


        $scope.getDeliver = () => {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/111111111111/order/getLogisticsCompanyEnum",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    'brandApp-Id': $scope.brandAppId
                }
            }).then(function (resp) {
                    $scope.expressData = [];
                    $scope.express = resp.data.data;
                    for (let key in $scope.express) {
                        $scope.expressData.push({
                            label: $scope.express[key],
                            value: key
                        });
                    }
                }, function () {

                }
            );
        };
        $scope.getDeliver();


        $scope.save = () => {
            if (!$scope.logistics.expressNo) {
                alertService.msgAlert('cancle', "请填写快递单号");
                return;
            }
            // if (!$scope.logistics.brandApp) {
            //     alertService.msgAlert('cancle', "请选择快递公司");
            //     return;
            // }


            $http({
                method: "PUT",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/111111111/order/" + $stateParams.id + "/updateAddress",
                data: {
                    memo: $scope.deliver.memo,
                    company: $scope.expressKey,
                    expressNo: $scope.logistics.expressNo,
                    // orderId: $stateParams.id,
                    // skuId: $stateParams.skuId
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    'brandApp-Id': $scope.brandAppId
                }
            }).then(function (resp) {
                    // alertService.msgAlert('success', '申请成功');
                    $scope.fallbackPage();
                }, function () {

                }
            );
        };


        $scope.open = function () {
            weui.picker($scope.expressData, {
                className: 'custom-classname',
                defaultValue: [0],
                onChange: function (result) {
                    $scope.expressName = result[0].label;
                    $scope.expressKey = result[0].value;

                },
                onConfirm: function (result) {
                    $scope.expressName = result[0].label;
                    $scope.expressKey = result[0].value;
                    $scope.$apply();
                },
                id: 'singleLinePicker'
            });
            'cascadePicker'
        };


        /*返回上级*/
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.home", null, {reload: true});
            } else {
                history.back();
            }
        };

    }
}

Controller.$inject = [
    '$scope',
    '$stateParams',
    '$http',
    'alertService',
    'loginService',
    '$state',
    '$location'
];

export default Controller ;
