import conf from "../../../../conf";
import weui from 'weui.js';

var $scope,
    $http,
    $stateParams,
    loginService,
    alertService,
    $state,
    $filter,
    $location;
class Controller {
    constructor(_$scope,
                _$http,
                _$stateParams,
                _loginService,
                _alertService,
                _$state,
                _$filter,
                _$location) {
        $scope = _$scope;
        $state = _$state;
        $http = _$http;
        loginService = _loginService;
        $stateParams = _$stateParams;
        alertService = _alertService;
        $location = _$location;
        $filter = _$filter;
        $scope.brandAppId = $stateParams.brandAppId;


        const TAG = "main/order/orderDetail ";
        console.log(`=> ${TAG}`);
        loginService.loginCtl(true, $location.absUrl());

        $scope.id = $stateParams.id;

        $scope.getInfo = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/11111111111111/deliverInvoice/" + $scope.id,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $scope.showBtn = resp.data.data.deliverStatusEnum;
                $scope.expressData = [];
                $scope.express = resp.data.data.logisticsCompanys;
                if (resp.data.data.logisticses.length > 0) {
                    $scope.expressName = resp.data.data.logisticses[0].company;
                    $scope.expressNo = resp.data.data.logisticses[0].expressNo;
                }
                console.log('test  物流',$scope.expressName);
                for (let key in $scope.express) {
                    $scope.expressData.push({
                        label: $scope.express[key],
                        value: key
                    });
                }
                $scope.orderId = resp.data.data.orderId;
                $scope.checkOrder($scope.orderId);
            }, function (resp) {
            });
        };
        $scope.getInfo();
        $scope.checkOrder = function (orderId) {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/order/" + orderId + "/detail",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $scope.info = resp.data.data;
            }, function (resp) {
            });
        };

        /*
         * 确认发货
         * */
        $scope.sendOrder = () => {
            if (!$scope.expressKey) {
                alertService.msgAlert('cancle', '请选择物流公司');
                return;
            }
            if (!$scope.expressNo) {
                alertService.msgAlert('cancle', '请输入快递单号');
                return;
            }
            alertService.confirm(null, "", "确认发货？", "取消", "确定").then(function (data) {
                if (data) {
                    $http({
                        method: "PUT",
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/11111111111111/deliverInvoice/" + $scope.id,
                        data: {
                            company: $scope.expressKey,
                            expressNo: $scope.expressNo
                        },
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            'brandApp-Id': $scope.brandAppId
                        }
                    }).then(function () {
                        alertService.msgAlert('success', '确认发货');
                        history.back();
                    }, function (resp) {
                    });
                }
            });
        };


        $scope.open = function () {
            if($scope.info.status!='UNSHIPPED'){
                return false;
            }
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


    }

    /*返回上级*/
    fallbackPage() {
        // if ($stateParams.status || $stateParams.tableIndex) {
        //     $state.go("main.brandApp.unionOrder", {
        //         status: $stateParams.status,
        //         tableIndex: $stateParams.tableIndex
        //     }, {reload: true});
        // } else {
        //     history.back();
        // }
        history.back();
    };
}

Controller.$inject = [
    '$scope',
    '$http',
    '$stateParams',
    'loginService',
    'alertService',
    '$state',
    '$filter',
    '$location',
];

export default Controller ;
