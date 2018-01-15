import conf from "../../../../conf";

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

        const TAG = "main/orderManage";
        console.log(`=> ${TAG}`);
        loginService.loginCtl(true, $location.absUrl());

        //定义数据存储空间
        $scope.data = {
            ALL: {},　　　　　　　　　　　　　　　　　   //全部
            UNPAYED: {},                             //待付款
            UNCOMMITED: {},                          //未提交
            UNCONFIRMED: {},                         //待确认接单
            REJECTED: {},                            //卖家拒绝接单
            CANCELING: {},                           //申请取消中
            CANCELED: {},                            //已取消
            UNSHIPPED: {},                           //待卖家发货
            UNRECEIVED: {},                          //待收货
            CLOSED: {},                              //已关闭
            FINISHED: {},                            //已完成
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
                case 'UNPAYED':
                    $scope.choosesStatus = "更多状态";
                    break;
                case 'UNCOMMITED':
                    $scope.choosesStatus = "未提交";
                    break;
                case 'UNCONFIRMED':
                    $scope.choosesStatus = "待确认";
                    break;
                case 'REJECTED':
                    $scope.choosesStatus = "卖家拒绝接单";
                    break;
                case 'CANCELING':
                    $scope.choosesStatus = "申请取消中";
                    break;
                case 'CANCELED':
                    $scope.choosesStatus = "已取消";
                    break;
                case 'UNSHIPPED':
                    $scope.choosesStatus = "更多状态";
                    break;
                case 'UNRECEIVED':
                    $scope.choosesStatus = "待收货";
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
                $scope.openDropDown = !$scope.openDropDown;
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
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/11111111111111/order",
                params: {
                    size: vm.size,
                    page: page?page:$scope.data[$scope.status].number,
                    status: $scope.status === "ALL" ? null : $scope.status,
                    keyWord:$scope.keyWord
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                // $scope.data = resp.data.data;
                //如果搜索   清空
                if(resp.data.data.number=='0'){
                    $scope.data[$scope.status].data = [];
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
         * 取消订单
         * */
        $scope.canclOrder = function (id) {
            alertService.confirm(null, "", "取消订单？", "取消", "确定").then(function (data) {
                if (data) {
                    $http({
                        method: "PUT",
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/11111111111111/order/" + id + "/cancel",
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
         * 拒绝接单
         * */
        $scope.rejectOrder = function (id) {
            alertService.confirm(null, "", "拒绝接单？", "取消", "确定").then(function (data) {
                if (data) {
                    $http({
                        method: "PUT",
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/11111111111111/order/" + id + "/rejectOrder",
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        }
                    }).then(function (resp) {
                        // alertService.msgAlert('success', '取消成功');
                        $scope.data[$scope.status] = {};
                        $scope.getList();
                    }, function (resp) {
                    });
                }
            });

        };


        /*
         * 确认接单
         * */
        $scope.confirmOrder = (id) => {
            alertService.confirm(null, "", "确认接单？", "取消", "确定").then(function (data) {
                if (data) {
                    $http({
                        method: "PUT",
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/11111111111111/order/" + id + "/confirmOrder",
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

        /*
         *发货
         */

        $scope.sendOrder = function (id) {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/11111111111111/deliverInvoice/" + id + "/deliverInvoice",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $state.go('main.brandApp.manageInvoiceDetail', {id: resp.data.data}, {reload: true});
            }, function (resp) {
            });
        };

        /**
         * 去支付
         * @param id; 订单id
         */
        $scope.pay = function (id) {
            $http({
                method: "PUT",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/pay/" + id,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                // 跳转到支付页
                location.href = `${conf.payUrl}${resp.data.data}&backUrl=${encodeURIComponent(location.href)}`;
            }, function (resp) {
                //TODO 失败页
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
