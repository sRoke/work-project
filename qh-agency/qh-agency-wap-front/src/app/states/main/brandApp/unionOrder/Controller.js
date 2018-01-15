import conf from "../../../../conf";

var
    $scope,
    $stateParams,
    $http,
    loginService,
    alertService,
    $mdDialog,
    $interval,
    $state,
    $filter,
    $location;
class Controller {
    constructor(_$scope,
                _$stateParams,
                _$http,
                _loginService,
                _alertService,
                _$mdDialog,
                _$interval,
                _$state,
                _$filter,
                _$location) {
        $scope = _$scope;
        $state = _$state;
        $http = _$http;
        loginService = _loginService;
        $stateParams = _$stateParams;
        alertService = _alertService;
        $mdDialog=_$mdDialog;
        $interval=_$interval;
        $location = _$location;
        $filter = _$filter;
        var vm = this;
        $scope.brandAppId = $stateParams.brandAppId;

        const TAG = "main/unionOrder";
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
                    $scope.choosesStatus = "待发货";
                    break;
                case 'UNRECEIVED':
                    $scope.choosesStatus = "更多状态";
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
            console.log(1)
        };

        vm.size = conf.pageSize;
        // vm.size = 2;
        $scope.getList = function () {
            if (!$scope.data[$scope.status].number) {
                $scope.data[$scope.status].number = 0;
            }
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/order/list",
                params: {
                    pageSize: vm.size,
                    number: $scope.data[$scope.status].number,
                    status: $scope.status === "ALL" ? null : $scope.status,
                    // status: $scope.status,
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
         * 确认收货
         * */
        $scope.receive = (id) => {
            alertService.confirm(null, "", "确认收货？", "取消", "确定").then(function (data) {
                if (data) {
                    $http({
                        method: "PUT",
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/11111111111111/order/" + id + "/confirmReceive",
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
                        alertService.msgAlert("exclamation-circle", "收货成功");
                        $scope.getList();
                    }, function (resp) {
                    });
                }
            });
        };

        /**
         * 去支付
         * @param id; 订单id
         */
        $scope.pay = function (id, count) {
            if (count == '0') {
                //$state.go("main.brandApp.wallet.payPassword", {id: id}, {reload: true});
                //针对改版
                $mdDialog.show({
                    templateUrl: 'messageCode.html',
                    parent: angular.element(document.body).find('#qh-agency-admin-front'),
                    clickOutsideToClose: true,   //true 点击界外的时候弹窗消失
                    fullscreen:false,
                    locals: {key: $scope.info},
                    controller: ['$mdDialog', 'locals', function ($mdDialog, locals) {
                        var vmd = this;
                        // vmd.info = locals.key;
                        // vmd.id = id;
                        // vmd.orderId = locals.key.id;
                        vmd.codeShow=false;
                        //取消
                        vmd.cance=function(){
                            $mdDialog.cancel();
                        };
                        vmd.gitCodeTime = '获取验证码';
                        vmd.getCodeCon='余额支付需要手机短信验证';
                        vmd.lastTime = 0;
                        //获取短信验证码
                        vmd.getVCcode = function () {
                            if (vmd.lastTime <= 0) {
                                // //手机号验证
                                // if (!(/^1[34578]\d{9}$/.test($scope.checkPhone))) {
                                //     alertService.msgAlert("exclamation-circle", "手机号码有误，请重填");
                                //     return false;
                                // }
                                //倒计时
                                vmd.lastTime = 60;
                                $http({
                                    method: "GET",      ///brandApp/{brandAppId}/partner/{partnerId}/partnerAccount/sendSms  GET    发送验证码
                                    url: conf.apiPath + "/brandApp/"+$scope.brandAppId+"/partner/123/partnerAccount/"+id+"/sendSms",
                                    params: {

                                    },
                                    headers: {
                                        'Authorization': 'Bearer ' + loginService.getAccessToken()
                                    }
                                }).then(function (resp) {
                                        //设置下一步点击
                                        console.log(resp);
                                        if(resp.data.status=='10086'){
                                           // alertService.msgAlert("exclamation-circle", "，请稍后重试!");
                                            vmd.getCodeCon="短信发送过于频繁";
                                            return false;
                                        }else{
                                            var str=resp.data.data;
                                            //$scope.first=false;
                                            // alertService.msgAlert("success", "已发送");
                                            vmd.getCodeCon='已将短信发送至尾号'+str.substr(str.length-4);
                                            vmd.gitCodeTime = vmd.lastTime + 's';
                                            var gitCode = $interval(function () {
                                                vmd.lastTime--;
                                                vmd.gitCodeTime = vmd.lastTime + 's';
                                                if (vmd.lastTime <= 0) {
                                                    vmd.gitCodeTime = '重新获取';
                                                    $interval.cancel(gitCode);
                                                }
                                            }, 1000)
                                        }

                                    }, function () {
                                        //error
                                        //alertService.msgAlert("exclamation-circle", "发送失败请重试!");
                                        vmd.lastTime = 0;
                                    }
                                );
                            }
                        };
                        //确认
                        vmd.confirm = function () {
                            //点击确认后调用短信验证码    核对接口
                            // $mdDialog.hide();
                            //点击确认后调用短信验证码    核对接口
                            if(!vmd.code){
                                vmd.codeShow=true;
                                return false;
                            }
                            if(vmd.code.length!=6){
                                vmd.codeShow=true;
                                return false;
                            }
                            $http({
                                method: "GET",
                                url: conf.apiPath + "/brandApp/"+$scope.brandAppId+"/partner/123/partnerAccount/"+id+"/checkSms",
                                params: {
                                    code:vmd.code
                                },
                                headers: {
                                    'Authorization': 'Bearer ' + loginService.getAccessToken()
                                }
                            }).then(function (resp) {
                                    //设置下一步点击
                                    console.log(resp);
                                    if(resp.data.status=='10080'){
                                       //alertService.msgAlert("exclamation-circle", "验证码错误!");
                                        vmd.codeShow=true;
                                        return false;
                                    }else if(resp.data.status=='200'){
                                        // alertService.msgAlert("exclamation-circle", "验证码成功!");
                                        $mdDialog.cancel();
                                        $state.go("main.brandApp.unionOrder",null,{reload:true})
                                        return false;
                                    }
                                }, function () {
                                    //error
                                    //alertService.msgAlert("exclamation-circle", "发送失败请重试!");

                                }
                            );

                        };
                    }],
                    controllerAs: "vmd"
                }).then(function (answer) {


                }, function () {
                    //error
                });
            } else {
                $http({
                    method: "PUT",
                    url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/pay/" + id,
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    }
                }).then(function (resp) {
                    // 跳转到支付页
                    // location.href = `${conf.payUrl}${resp.data.data}&backUrl=${encodeURIComponent(location.href)}`;


                    location.href = `${conf.payUrl}${resp.data.data}&brandAppId=${$scope.brandAppId}&backUrl=${encodeURIComponent(location.href)}`;

                }, function (resp) {
                    //TODO 失败页
                });
            }
            ;

        };


        $scope.tabs($scope.status, $scope.tableIndex);

        $scope.searchItem = function () {
            $http({
                method: 'GET',
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/order/list",
                params: {
                    keyWord: $scope.keyWord,
                    pageSize: vm.size,
                    number: $scope.data[$scope.status].number,
                    status: $scope.status === "ALL" ? null : $scope.status,
                    type: "purchase"
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
    '$mdDialog',
    '$interval',
    '$state',
    '$filter',
    '$location'
];

export default Controller ;
