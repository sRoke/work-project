import conf from "../../../../../conf.js";

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
        $location =_$location;

        alertService = _alertService;
        loginService = _loginService;
        $state = _$state;
        $stateParams = _$stateParams;

        $scope.brandAppId = $stateParams.brandAppId;

        const TAG = "main/order/refund ";
        console.log(`=> ${TAG}`);
        loginService.loginCtl(true,$location.absUrl());


        $scope.refund = {
            reasonList: [],
            typeList: [],
            applyPrice: $stateParams.amount / 100,
            maxPrice: $stateParams.amount / 100,          //当前可申请的最大金额
            skuId: $stateParams.skuId,
            orderId: $stateParams.id,
            reason: "",
            type: "MONEY_ONLY",
            memo: ""
        };

        ////退货类型列表
        if ($stateParams.status == 'UNSHIPPED') {
            $scope.refund.typeList = [
                {
                    value: "MONEY_ONLY",
                    name: "仅退款"
                }
            ];
        } else {
            $scope.refund.typeList = [
                {
                    value: "MONEY_ONLY",
                    name: "仅退款"
                },
                {
                    value: "ITEM",
                    name: "退货退款"
                }
            ];
        }


        ////退货原因列表
        $scope.refund.reasonList = [
            {
                value: '七天无理由退换货',
                name: '七天无理由退换货'
            },
            {
                value: '退运费',
                name: '退运费'
            },
            {
                value: ' 商品错发/漏发',
                name: ' 商品错发/漏发'
            },
            {
                value: ' 收到商品破损',
                name: ' 收到商品破损'
            },
            {
                value: '收到商品与描述不符',
                name: '收到商品与描述不符'
            },
            {
                value: '商品质量问题',
                name: '商品质量问题'
            },
            {
                value: '未按约定时间发货',
                name: '未按约定时间发货'
            },
            {
                value: '不想要了',
                name: '不想要了'
            },
            {
                value: '其它',
                name: '其它'
            },
        ];

        //确认退款
        $scope.submit = function () {
            //TODO 除以100
            if ($scope.refund.applyPrice > $scope.refund.maxPrice) {
                alertService.msgAlert('exclamation-circle', '退款金额大于支付金额');
                return;
            }

            if (!$scope.refund.reason) {
                alertService.msgAlert('exclamation-circle', '填写退货原因');
                return;
            }
            alertService.confirm(null, "", "确认申请退款？", "取消", "确定").then(function (data) {
                if (data) {
                    $http({
                        method: "POST",
                        url: conf.apiPath + "/order/refund",
                        data: {
                            orderId: $scope.refund.orderId,
                            skuId: $scope.refund.skuId,
                            applyPrice: parseInt(parseFloat($scope.refund.applyPrice * 100)),
                            type: $scope.refund.type,
                            reason: $scope.refund.reason,
                            memo: $scope.refund.memo
                        },
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            'brandApp-Id': $scope.brandAppId
                        }
                    }).then(function (data) {
                            // 获取购物车数量有多少个SKU
                            if (data.data.status == 200) {
                                //讲图标变色
                                alertService.msgAlert('success', '申请成功');
                                $scope.fallbackPage();
                            } else {
                                alertService.msgAlert('exclamation-circle', '提交失败');
                            }

                        }, function () {

                        }
                    );
                }
            });


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
