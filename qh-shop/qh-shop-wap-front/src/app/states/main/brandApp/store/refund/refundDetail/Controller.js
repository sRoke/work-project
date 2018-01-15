import conf from "../../../../../../conf";


var $scope,
    $stateParams,
    $httpParamSerializer,
    alertService,
    $http,
    loginService,
    $mdDialog,
    $state;
class Controller {
    constructor(_$scope,
                _$stateParams,
                _$httpParamSerializer,
                _alertService,
                _$http,
                _loginService,
                _$mdDialog,
                _$state) {
        $scope = _$scope;
        $state = _$state;
        $http = _$http;
        loginService = _loginService;
        $stateParams = _$stateParams;
        $mdDialog = _$mdDialog;
        $httpParamSerializer = _$httpParamSerializer;
        alertService = _alertService;
        loginService.loginCtl(true);
        $scope.brandAppId = $stateParams.brandAppId;

        ///////////////////////////////////////
        // const TAG = "main/address ";
        // console.log(`=> ${TAG}`);
        loginService.loginCtl(true);


        $scope.getRefundDetail = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $stateParams.storeId + '/refund/' + $stateParams.id,
                params: {},
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $scope.refundDetail = resp.data.data;
                // console.log($scope.refundDetail)
            }, function (resp) {
                //TODO 错误处理
            });

        }

        $scope.getRefundDetail();


        $scope.refundConfirm = function (status) {
            if (status) {
                alertService.confirm(null, '同意申请?', '温馨提示').then(function (data) {
                    if (data) {
                        $http({
                            method: "PUT",
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $stateParams.storeId + '/refund/' + $scope.refundDetail.id,
                            params: {},
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            }
                        }).then(function (resp) {
                            // console.log(resp.data.data);
                            $scope.getRefundDetail();
                        }, function (resp) {
                            alertService.msgAlert("exclamation-circle", resp.data.message);
                            //TODO 错误处理
                        });
                    }
                });
            } else {
                alertService.confirm(null, '拒绝申请?', '温馨提示').then(function (data) {
                    if (data) {
                        $http({
                            method: "PUT",
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $stateParams.storeId + '/refund/' + $scope.refundDetail.id + '/reject',
                            params: {},
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            }
                        }).then(function (resp) {
                            // console.log(resp.data.data);
                            $scope.getRefundDetail();
                        }, function (resp) {
                            alertService.msgAlert("exclamation-circle", resp.data.message);
                            //TODO 错误处理
                        });
                    }
                });
            }
        };


        $scope.received = function () {
            alertService.confirm(null, '确认收货?', '温馨提示').then(function (data) {
                if (data) {
                    $http({
                        method: "PUT",
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $stateParams.storeId + '/refund/' + $scope.refundDetail.id + '/agreeRefund',
                        params: {},
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        }
                    }).then(function (resp) {
                        // console.log(resp.data.data);
                        $scope.getRefundDetail();
                    }, function (resp) {
                        //TODO 错误处理
                    });
                }
            });
        };


        // $scope.pageTitle = '订单详情';
        /*返回上级*/
        $scope.fallbackPage = function () {
            $state.go("main.brandApp.store.refund.refundList", null, {reload: true});
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
    '$mdDialog',
    '$state'
];

export default Controller ;
