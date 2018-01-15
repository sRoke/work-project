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

        const TAG = "main/refundManage";
        console.log(`=> ${TAG}`);
        loginService.loginCtl(true, $location.absUrl());

        $scope.data = {
            ALL: {},
            UNCHECKED:{},
            BUYER_UNCHECKED:{},
            REJECTED:{},
            WAIT_SENDING:{},
            WAIT_RECEIVED:{},
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
                    $scope.choosesStatus = "更多状态";
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
        $scope.getList = function (page) {
            if (!$scope.data[$scope.status].number) {
                $scope.data[$scope.status].number = 0;
            }
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/refund/",
                params: {
                    pageSize: vm.size,
                    number:page?page: $scope.data[$scope.status].number,
                    status: $scope.status === "ALL" ? null : $scope.status,
                    keyWord:$scope.keyWord,
                    source: 'SELLER'
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                // $scope.data = resp.data.data;
                if(resp.data.data.number=='0'){
                    $scope.data[$scope.status].data = [];    //对搜索进行清空
                }
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
         * 拒绝
         * */
        $scope.rejectOrder = function (id) {
            alertService.confirm(null, "", "拒绝申请？", "取消", "确定").then(function (data) {
                if (data) {
                    $http({
                        method: "PUT",
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/111111111/refund/" + id + "/reject",
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
         * 同意
         * */
        $scope.agreeOrder = (id) => {
            alertService.confirm(null, "", "同意？", "取消", "确定").then(function (data) {
                if (data) {
                    $http({
                        method: "PUT",
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/111111111/refund/" + id + "/agreeReturnGoods",
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

        /*
         * 确认收货
         * */
        $scope.confirmReceipt = (id) => {
            alertService.confirm(null, "", "确认收货？", "取消", "确定").then(function (data) {
                if (data) {
                    $http({
                        method: "PUT",
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/111111111/refund/" + id + "/agreeRefund",
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


        /*
         * 确认退款
         * */
        $scope.confirmRefund = (id) => {
            alertService.confirm(null, "", "确认退款？", "取消", "确定").then(function (data) {
                if (data) {
                    $http({
                        method: "PUT",
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/111111111/refund/" + id + "/refundHandle",
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
