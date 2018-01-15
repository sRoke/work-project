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


        $scope.isClick = 1;

        $scope.clearKeyWord = function () {        //清空搜索框
            $scope.keyWord = '';
        };

        $scope.search = function () {
            $scope.isClick = 1;
            $scope.status = [];
            $scope.pageChange(0);
        }

        $scope.focus = function (status) {
            if (status) {
                $scope.searchShow = true;
            } else {
                $scope.searchShow = false;
            }
        }


        $scope.tabs = function (tableIndex) {
            $scope.isClick = tableIndex;
            switch (tableIndex) {
                case 1:
                    $scope.status = [];
                    break;
                case 2:
                    $scope.status = ['UNCHECKED'];
                    break;
                case 3:
                    $scope.status = ['WAIT_BUYER_SENDING', 'WAIT_SELLER_RECEIVED'];
                    break;
                case 4:
                    $scope.status = ['REJECTED', 'FINISHED'];
                    break;
                default:
                    // console.log('错误!')
            }
            $scope.pageChange(0);
        };


        $scope.currpage = 0;
        $scope.pageChange = function (currpage) {
            $scope.currpage = currpage;
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $stateParams.storeId + '/refund',
                params: {
                    page: currpage,
                    size: conf.pageSize,
                    status: $scope.status,
                    keyWord: $scope.keyWord,
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                if(currpage){
                    $scope.refundList.number = resp.data.data.number;
                    for (var i=0;i< resp.data.data.content.length;i++){
                        $scope.refundList.content.push(resp.data.data.content[i])
                    }
                }else {
                    $scope.refundList = resp.data.data;
                }
                // console.log('11111111111111111111111111',$scope.refundList);
            }, function (resp) {
                //TODO 错误处理
            });

        };


        $scope.pageChange($scope.currpage);


        $scope.refundConfirm = function (status, id) {
            if (status) {
                alertService.confirm(null, '同意申请?', '温馨提示').then(function (data) {
                    if (data) {
                        $http({
                            method: "PUT",
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $stateParams.storeId + '/refund/' + id,
                            params: {},
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            }
                        }).then(function (resp) {
                            // console.log(resp.data.data);
                            $scope.pageChange($scope.currpage);
                        }, function (resp) { alertService.msgAlert("exclamation-circle", resp.data.message);
                            //TODO 错误处理
                        });
                    }
                });
            } else {
                alertService.confirm(null, '拒绝申请?', '温馨提示').then(function (data) {
                    if (data) {
                        $http({
                            method: "PUT",
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $stateParams.storeId + '/refund/' + id + '/reject',
                            params: {},
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            }
                        }).then(function (resp) {
                            // console.log(resp.data.data);
                            $scope.pageChange($scope.currpage);
                        }, function (resp) { alertService.msgAlert("exclamation-circle", resp.data.message);
                            //TODO 错误处理
                        });
                    }
                });
            }
        };


        //确认收货
        $scope.received = function (id) {
            alertService.confirm(null, '确认收货?', '温馨提示').then(function (data) {
                if (data) {
                    $http({
                        method: "PUT",
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $stateParams.storeId + '/refund/' + id + '/agreeRefund',
                        params: {},
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        }
                    }).then(function (resp) {
                        // console.log(resp.data.data);
                        $scope.pageChange($scope.currpage);
                    }, function (resp) { alertService.msgAlert("exclamation-circle", resp.data.message);
                        //TODO 错误处理
                    });
                }
            });
        };


        // $scope.pageTitle = '订单详情';
        /*返回上级*/
        $scope.fallbackPage = function () {
            $state.go("main.brandApp.store.home", null, {reload: true});
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
