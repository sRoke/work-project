import conf from "../../../../../conf";

var $scope,
    $stateParams,
    $http,
    loginService,
    alertService,
    $state,
    $location;
class Controller {
    constructor(_$scope,
                _$stateParams,
                _$http,
                _loginService,
                _alertService,
                _$state,
                _$location) {
        $scope = _$scope;
        $state = _$state;
        $http = _$http;
        loginService = _loginService;
        $stateParams = _$stateParams;
        alertService = _alertService;
        $location = _$location;
        var vm = this;
        $scope.brandAppId = $stateParams.brandAppId;

        const TAG = "main/myRefundManage";
        console.log(`=> ${TAG}`);
        loginService.loginCtl(true, $location.absUrl());

        $scope.data = {
            ALL: {},
            UNCHECKED: {},
            BUYER_UNCHECKED: {},
            REJECTED: {},
            WAIT_SENDING: {},
            WAIT_RECEIVED: {},
            UNPAYED: {},
            UNRECEIVED: {},
            FINISHED: {},
            CLOSED:{},
        };

        $scope.openDropDown = false;

        $scope.status = $stateParams.status ? $stateParams.status : "ALL";
        $scope.tableIndex = $stateParams.tableIndex ? $stateParams.tableIndex : "0";

        $scope.tabs = function (status, tableIndex, ev) {
            $scope.status = status;
            $scope.tableIndex = tableIndex;
            if (ev) {
                ev.stopPropagation();
            }
            switch ($scope.status) {
                case 'ALL':
                    $scope.choosesStatus = "更多状态";
                    break;
                case 'UNCHECKED':
                    $scope.choosesStatus = "待确认";
                    break;
                case 'BUYER_UNCHECKED':
                    $scope.choosesStatus = "待确认";
                    break;
                // case 'REJECTED':
                //     $scope.choosesStatus = "卖家拒绝售后";
                //     break;
                case 'WAIT_SENDING':
                    $scope.choosesStatus = "待退货";
                    break;
                case 'WAIT_RECEIVED':
                    $scope.choosesStatus = "更多状态";
                    break;
                case 'UNPAYED':
                    $scope.choosesStatus = "待退款";
                    break;
                case 'CLOSED':
                    $scope.choosesStatus = "已关闭";
                    break;
                case 'FINISHED':
                    $scope.choosesStatus = "已完成";
                    break;
                default:
                    $scope.choosesStatus = "更多状态";
            }
            $scope.openDropDown = false;

            if (!$scope.data[$scope.status].data) {
                $scope.data[$scope.status].data = [];
                $scope.getList()
            }
        };

        $scope.openDropDownBtn = function (status, tableIndex) {
            if (status == 'openIt') {
                $scope.openDropDown = true;
            }
            $scope.tableIndex = tableIndex;
        };

        vm.size = conf.pageSize;
        // vm.size = 2;
        $scope.getList = function () {
            if (!$scope.data[$scope.status].number) {
                $scope.data[$scope.status].number = 0;
            }
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/refund/",
                params: {
                    pageSize: vm.size,
                    number: $scope.data[$scope.status].number,
                    status: $scope.status === "ALL" ? null : $scope.status,
                    // status: $scope.status,
                    source: 'BUYER'
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                // $scope.data = resp.data.data;
                if (!$scope.data[$scope.status].data) {
                    $scope.data[$scope.status].data = [];
                }
                //存入数据
                $scope.data[$scope.status].data = $scope.data[$scope.status].data.concat(resp.data.data.content);
                $scope.data[$scope.status].totalNum = resp.data.data.totalElements + 1;
                $scope.data[$scope.status].number = resp.data.data.number;
                if ((resp.data.data.number + 1) * resp.data.data.size >= resp.data.data.totalElements) {
                    $scope.data[$scope.status].pageEnd = true;
                }
                $scope.data[$scope.status].number++;
            }, function (resp) {
            });
        };


        /*
         * 取消订单
         * */
        $scope.cancleOrder = function (id) {
            alertService.confirm(null, "", "取消订单？", "取消", "确定").then(function (data) {
                if (data) {
                    $http({
                        method: "PUT",
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/111111111/refund/" + id + "/cancelRefund",
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        }
                    }).then(function (resp) {
                        // alertService.msgAlert('success', '取消成功');
                        $scope.data[$scope.status] = {};
                        $scope.getList()
                    }, function (resp) {
                    });
                }
            });
        };

        /*
         * 确认收货
         * */
        $scope.receive = (id) => {
            alertService.confirm(null, "", "确认收货？", "取消", "确定").then(function (data) {
                if (data) {
                    $http({
                        method: "GET",
                        url: conf.apiPath + "/order/confirmReceive",
                        params: {
                            id: id
                        },
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        }
                    }).then(function () {
                        // alertService.msgAlert('success', '收货成功');
                        $scope.data[$scope.status] = {};
                        $scope.getList()
                    }, function (resp) {
                    });
                }
            });
        };

        $scope.tabs($scope.status, $scope.tableIndex);


        $scope.searchItem = function () {
            $http({
                method: 'GET',
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/111111111/skuStore/search",
                params: {
                    keyWord: $scope.keyWord,
                    type: "refund"
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                if (!$scope.data['ALL'].data) {
                    $scope.data['ALL'].data = [];
                }
                //存入数据
                $scope.data['ALL'].data = $scope.data['ALL'].data.concat(resp.data.data.content);
                $scope.data['ALL'].totalNum = resp.data.data.totalElements + 1;
                $scope.data['ALL'].number = resp.data.data.number;
                if ((resp.data.data.number + 1) * resp.data.data.size >= resp.data.data.totalElements) {
                    $scope.data['ALL'].pageEnd = true;
                }
                $scope.data['ALL'].number++;
            })
        };
    }


    /*返回上级*/
    fallbackPage() {
        $state.go("main.brandApp.home", null, {reload: true});
        // if ($stateParams.from == 'purchase' || ($stateParams.status || $stateParams.tableIndex)) {
        //     $state.go("main.brandApp.purchase", null, {reload: true});
        // } else if ($stateParams.from == 'stock') {
        //     $state.go("main.brandApp.stock", null, {reload: true});
        //
        // } else {
        //     history.back();
        // }
    };
}

Controller.$inject = [
    '$scope',
    '$stateParams',
    '$http',
    'loginService',
    'alertService',
    '$state',
    '$location'
];

export default Controller ;
