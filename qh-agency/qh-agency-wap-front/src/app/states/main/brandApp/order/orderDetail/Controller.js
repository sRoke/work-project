import conf from "../../../../../conf";

var $scope,
    $http,
    $stateParams,
    loginService,
    alertService,
    $mdDialog,
    $interval,
    $state,
    $filter,
    $location;
class Controller {
    constructor(_$scope,
                _$http,
                _$stateParams,
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
        $scope.brandAppId = $stateParams.brandAppId;






        const TAG = "main/order/orderDetail ";
        console.log(`=> ${TAG}`);
        loginService.loginCtl(true, $location.absUrl());

        $scope.orderId = $stateParams.id;
        $scope.checkOrder = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/order/" + $scope.orderId + "/detail",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
                /*headers: {
                 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
                 }*/
            }).then(function (resp) {
                console.log(111111111111,resp);
                $scope.info = resp.data.data;
            }, function (resp) {
            });
        };
        $scope.checkOrder();
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
                        // $scope.data[$scope.status] = {};
                        //$scope.getList()
                        $scope.checkOrder();
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
                            'brandApp-Id': $scope.brandAppId
                        }
                    }).then(function () {
                        // alertService.msgAlert('success', '收货成功');
                        // history.back();
                        $scope.checkOrder();
                    }, function (resp) {
                    });
                }
            });
        };

        /**
         * 去支付
         * @param id; 订单id
         */

        $scope.pay = (id,count) => {
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
                                       // alertService.msgAlert("exclamation-circle", "验证码错误!");
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
            }else{
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
        }
    }

    /*返回上级*/
    fallbackPage() {
        if ($stateParams.status || $stateParams.tableIndex) {
            $state.go("main.brandApp.unionOrder", {
                status: $stateParams.status,
                tableIndex: $stateParams.tableIndex
            }, {reload: true});
        } else {
            history.back();
        }
    };
}

Controller.$inject = [
    '$scope',
    '$http',
    '$stateParams',
    'loginService',
    'alertService',
    '$mdDialog',
    '$interval',
    '$state',
    '$filter',
    '$location',
];

export default Controller ;
