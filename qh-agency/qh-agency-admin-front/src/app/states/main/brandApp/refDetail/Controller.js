import conf from "../../../../conf";
import "bootstrap/dist/css/bootstrap.css";
var $scope,
    $http,
    $state,
    $mdDialog,
    authService,
    loginService,
    $stateParams,
    errorService,
    $timeout,
    Upload,
    alertService;
class Controller {
    constructor(_$scope, _$http, _loginService, _$state,_$mdDialog, _authService, _$stateParams,_errorService, _$timeout,_Upload, _alertService) {
        $scope = _$scope;
        $http = _$http;
        $state = _$state;
        $mdDialog = _$mdDialog;
        loginService = _loginService;
        authService = _authService;
        $stateParams = _$stateParams;
        errorService=_errorService;
        $timeout=_$timeout;
        Upload = _Upload;
        alertService = _alertService;
        loginService.loginCtl(true);

        //权限相关
        $scope.REFUND_C = authService.hasAuthor("REFUND_C");    //退款操作

        $scope.brandAppId = $stateParams.brandAppId;
        $scope.brandAppId = $stateParams.brandAppId;
        $scope.refund = {};
        $scope.id = $stateParams.id;
        $scope.getInfo=function () {
            $http({
                method:"GET",
                url:conf.apiPath+"/brandApp/"+$scope.brandAppId+"/refund/"+$scope.id,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
            }).success(function (resp) {
                console.log(resp);
                $scope.data=resp.data;
                $scope.items=resp.data.orderItemInfos;
            });
        };
        $scope.getInfo();







        //拒绝确认tk
        $scope.defaultRefund = function (ev) {
            $mdDialog.show({
                templateUrl: 'defaultRefund.html',
                parent: angular.element(document.body).find('#qh-agency-admin-front'),
                targetEvent: ev,
                clickOutsideToClose: true,   //true 点击界外的时候弹窗消失
                fullscreen: false,
                controller: ['$mdDialog', function ($mdDialog) {
                    var vmd = this;
                    vmd.cancel = function () {
                        return $mdDialog.cancel();
                    };

                    // vmd.uploadImg = function (file) {
                    //     $scope.f = file;
                    //     // $scope.errFile = errFiles && errFiles[0];
                    //     if (file) {
                    //         file.upload = Upload.upload({
                    //             url: conf.apiPath + "/uploadImg/uploadImg?imgType=UPLOAD&type=UPLOAD&X-Requested-With=XMLHttpRequest",
                    //             data: {excelFile: file}
                    //         });
                    //
                    //         file.upload.then(function (data) {
                    //             console.log(data);
                    //             if (data.data.data.code == 'SUCCESS') {
                    //                 vmd.imgs = data.data.data.file_path;
                    //             } else {
                    //                 var msg = object.msg;
                    //                 $window.alert(msg);
                    //             }
                    //         });
                    //     }
                    // }
                    //拒绝接单js
                    vmd.reject = function () {
                        if(!vmd.reason){
                            return errorService.error("拒绝原因不能为空", null)
                        }else{
                            $http({
                                method:"PUT",
                                url:conf.apiPath +"/brandApp/"+$scope.brandAppId+"/refund/"+$scope.id+"/reject",
                                params: {
                                    rejectReason: vmd.reason
                                },
                                headers: {
                                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                    "brandApp-Id": $scope.brandAppId
                                },
                            }).success(function (data) {
                                console.log(data);
                                if (data.status == '200') {
                                    vmd.cancel();
                                    $scope.getInfo();
                                    //$scope.fallbackPage();
                                }
                            });
                        }
                    };
                }],
                controllerAs: "vmd"
            }).then(function (answer) {
            }, function () {
            });
        };
        $scope.trueRefundDialog = function (id, refundAmount) {
            // var msg = "退款金额：" + refundAmount/100+ "元"
            alertService.confirm(null,'是否确认退货', "警告", "取消", "确认")
                .then(function (data) {
                    if (data) {
                        $scope.handle(id, refundAmount);
                    }
                });
        };

        //同意退货申请
        $scope.showRefundDialog = function () {
            //var msg = "退款金额：" + refundAmount/100+ "元"
            alertService.confirm(null, "确定同意申请？", "温馨提示", "取消", "确认")
                .then(function (data) {
                    if (data) {
                        $http({
                            method: "PUT",
                            url:conf.apiPath +"/brandApp/"+$scope.brandAppId+"/refund/"+$scope.id+"/agreeReturnGoods",
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            }
                        }).then(function successCallback(response) {
                            console.log(response);
                            $scope.getInfo();
                        }, function errorCallback(response) {
                            // 请求失败执行代码     return errorService.error("！", null)
                        });
                    }
                });
        };

        //货已收到
        $scope.showReceivedDialog = function () {
            alertService.confirm(null, "确定货已收到？", "温馨提示", "取消", "确认")
                .then(function (data) {
                    if (data) {
                        $http({
                            method: "PUT",
                            url:conf.apiPath +"/brandApp/"+$scope.brandAppId+"/refund/"+$scope.id+"/agreeRefund",
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            }
                        }).then(function successCallback(response) {
                            console.log(response);
                            $scope.getInfo();
                        }, function errorCallback(response) {
                            // 请求失败执行代码     return errorService.error("！", null)
                        });
                    }
                });
        };
        $scope.hideRefundDialog = function (id, isAgree) {
            var msg = '是否拒绝退款';
            alertService.confirm(null, msg, "提示:", "取消", "确认")
                .then(function (data) {
                    if (data) {
                        $scope.handle(id, isAgree);
                    }
                });
        };
        $scope.handle = function (id, isAgree) {
            $http({
                method: "POST",
                url: conf.apiPath + "/refund/refundHandle",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
                data: {
                    id: id,
                    isAgree: isAgree,
                    brandApp: $scope.refund.brandApp,
                    expressNo: $scope.refund.expressNo
                }
            }).then(function successCallback(response) {
                $scope.getInfo();
            }, function errorCallback(response) {
                // 请求失败执行代码
            });
        };

        // 返回按钮
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.index", null, {reload: true});
            } else {
                history.back();
            }
        };


        // $('#checkModal').modal({
        //     backdrop: false,
        //     show: false
        // });
        $scope.classMap = {
            class: [
                {cur: false, date: null}, {cur: false, date: null}, {cur: false, date: null},
                {
                    cur: false,
                    date: null
                }],
            status: []
        };
        // 遍历状态值
        $scope.classDateTime = function () {
            for (var i = 0; i < $scope.classMap.status.length; i++) {
                $scope.classMap.class[i].cur = true;
                for (var o = 0; o < $scope.refundLogs.length; o++) {
                    if ($scope.refundLogs[o].name === $scope.classMap.status[i]) {
                        $scope.classMap.class[i].date = $scope.refundLogs[o].dateCreated;
                        break;
                    }
                }
            }
        };

        /**
         * 查询日志信息
         */

        $scope.logs = function () {
            // $http.get(appConfig.apiPath + "/refund/logs/" + refundId).success(function (data) {
            //     $scope.refundLogs = data.logs;
            if ($scope.refund.status === 'FINISHED') {
                $scope.classMap.status = ["UNCHECKED", "WAIT_BUYER_SENDING", "REFUNDING", "FINISHED"];
                $scope.classDateTime();
            } else if ($scope.refund.status === 'REFUNDING') {
                $scope.classMap.status = ["UNCHECKED", "WAIT_BUYER_SENDING"];
                $scope.classDateTime();
            } else if ($scope.refund.status === 'UNCHECKED' ||
                $scope.refund.status === 'REJECTED' ||
                $scope.refund.status === 'CANCLED') {
                $scope.classMap.status = ["UNCHECKED"];
                $scope.classDateTime();
            } else if ($scope.refund.status === 'WAIT_BUYER_SENDING' ||
                $scope.refund.status === 'WAIT_SELLER_RECEIVED' ||
                $scope.refund.status === 'WAIT_BUYER_ADJUST_SIGN') {
                if ($scope.refund.status === 'WAIT_SELLER_RECEIVED') {
                    $scope.classMap.status = ["UNCHECKED", "WAIT_BUYER_SENDING", "WAIT_BUYER_SENDING"];
                } else {
                    $scope.classMap.status = ["UNCHECKED", "WAIT_BUYER_SENDING"];
                }

                $scope.classDateTime();
            }
        }


        //
        //         $scope.order = {};
        //         $scope.logistics = {};
        //         $scope.discardGoods = false;
        //         $scope.refundLogs = {};
        //         $scope.refundId = $stateParams.id;
        //         var refundId = $stateParams.id;
        //         $scope.refund = {};
        //         $scope.queryInfo = function () {
        //             $http.get(appConfig.apiPath + "/refund/info/" + refundId).success(function (data) {
        //                 $scope.refund = data.refund;
        //                 if ($scope.refund.type.indexOf("MONEY_ONLY") >= 0) {
        //                     $scope.refund.type = "MONEY_ONLY"
        //                 }
        //                 $scope.logs();
        //             });
        //         };
        //         $scope.queryInfo();
        //
        //
        //
        // $scope.getInfo = function () {
        //     $http({
        //         method:"GET",
        //         url:conf.apiPath +"/refund/info",
        //         headers: {
        //             'brandApp-Id': "58d9ff74ab19c918e1c12716"
        //         },
        //         params:{
        //             id:$scope.id,
        //         }
        //     }).then(function successCallback(response) {
        //         $scope.refund = response.data.data;
        //
        //         $scope.refund = data.refund;
        //         if ($scope.refund.type.indexOf("MONEY_ONLY") >= 0) {
        //             $scope.refund.type = "MONEY_ONLY"
        //         }
        //         $scope.logs();
        //
        //     }, function errorCallback(response) {
        //         // 请求失败执行代码
        //     });
        // }
        // $scope.getInfo();
        //
        //
        //
        //
        //
        //
        //         $('#checkModal').modal({
        //             backdrop: false,
        //             show: false
        //         });
        //         $scope.classMap = {
        //             class: [
        //                 {cur: false, date: null}, {cur: false, date: null}, {cur: false, date: null},
        //                 {
        //                     cur: false,
        //                     date: null
        //                 }],
        //             status: []
        //         };
        //         // 遍历状态值
        //         $scope.classDateTime = function () {
        //             for (var i = 0; i < $scope.classMap.status.length; i++) {
        //                 $scope.classMap.class[i].cur = true;
        //                 for (var o = 0; o < $scope.refundLogs.length; o++) {
        //                     if ($scope.refundLogs[o].name === $scope.classMap.status[i]) {
        //                         $scope.classMap.class[i].date = $scope.refundLogs[o].dateCreated;
        //                         break;
        //                     }
        //                 }
        //             }
        //         };
        //         $scope.refundLogs = [];
        //         /**
        //          * 查询日志信息
        //          */
        //         $scope.logs = function () {
        //
        //             $scope.getInfo = function () {
        //                 $http({
        //                     method:"GET",
        //                     url:conf.apiPath +"/refund/info",
        //                     headers: {
        //                         'brandApp-Id': "58d9ff74ab19c918e1c12716"
        //                     },
        //                     params:{
        //                         id:$scope.id,
        //                     }
        //                 }).then(function successCallback(response) {
        //                     $scope.refund = response.data.data;
        //                 }, function errorCallback(response) {
        //                     // 请求失败执行代码
        //                 });
        //             }
        //
        //
        //
        //             $http.get(appConfig.apiPath + "/refund/logs/" + refundId).success(function (data) {
        //                 $scope.refundLogs = data.logs;
        //                 if ($scope.refund.status === 'FINISHED') {
        //                     $scope.classMap.status = ["UNCHECKED", "WAIT_BUYER_SENDING", "REFUNDING", "FINISHED"];
        //                     $scope.classDateTime();
        //                 } else if ($scope.refund.status === 'REFUNDING') {
        //                     $scope.classMap.status = ["UNCHECKED", "WAIT_BUYER_SENDING"];
        //                     $scope.classDateTime();
        //                 } else if ($scope.refund.status === 'UNCHECKED' ||
        //                     $scope.refund.status === 'REJECTED' ||
        //                     $scope.refund.status === 'CANCLED') {
        //                     $scope.classMap.status = ["UNCHECKED"];
        //                     $scope.classDateTime();
        //                 } else if ($scope.refund.status === 'WAIT_BUYER_SENDING' ||
        //                     $scope.refund.status === 'WAIT_SELLER_RECEIVED' ||
        //                     $scope.refund.status === 'WAIT_BUYER_ADJUST_SIGN') {
        //                     if ($scope.refund.status === 'WAIT_SELLER_RECEIVED') {
        //                         $scope.classMap.status = ["UNCHECKED", "WAIT_BUYER_SENDING", "WAIT_BUYER_SENDING"];
        //                     } else {
        //                         $scope.classMap.status = ["UNCHECKED", "WAIT_BUYER_SENDING"];
        //                     }
        //
        //                     $scope.classDateTime();
        //                 }
        //             });
        //         };


        // /**
        //  * 同意退款
        //  */
        // $scope.payment = function () {
        //     $("#paymentModal").modal("hide");
        //     if (!$scope.refundSuccess) {
        //         $scope.refundSuccess = true;
        //         $http({
        //             method: 'post',
        //             url: appConfig.apiPath + "/refund/payment",
        //             data: {id: refundId},
        //             headers: {'X-Requested-With': 'XMLHttpRequest'}
        //         }).then(function (data) {
        //             $scope.refund.status = data.status;
        //             $scope.refund.statusDesp = data.statusDesp;
        //             angular.element("#paymentModal").modal("hide");
        //             $scope.queryInfo();
        //             $scope.refundSuccess = false;
        //         }).then(function () {
        //             $scope.refundSuccess = false;
        //         });
        //     }
        //
        //
        // };

        // /**
        //  * 提交退货审核
        //  */
        // $scope.verify = function () {
        //     $("#alertModal").modal("hide");
        //     $http({
        //         method: 'post',
        //         url: appConfig.apiPath + "/refund/verify",
        //         data: {id: refundId},
        //         headers: {'X-Requested-With': 'XMLHttpRequest'}
        //     }).success(function (data) {
        //         $scope.refund.status = data.status;
        //         $scope.refund.statusDesp = data.statusDesp;
        //         angular.element("#alertModal").modal("hide");
        //         $scope.queryInfo();
        //     });
        // };
        //
        // /**
        //  * 提交修改后的金额
        //  */
        // $scope.adjustMoney = function () {
        //     $http({
        //         method: 'post',
        //         url: appConfig.apiPath + "/refund/adjustMoney",
        //         data: {id: refundId, adjustMoney: $scope.refundMoney},
        //         headers: {'X-Requested-With': 'XMLHttpRequest'}
        //     }).success(function (data) {
        //         $scope.refund.refundMoney = data.refundMoney;
        //         angular.element("#moneyModal").modal("hide");
        //         $scope.queryInfo();
        //     });
        // };

        // /**
        //  * 提交柜绝申请
        //  */
        // $scope.rejectSubmit = function () {
        //     $http({
        //         method: 'post',
        //         url: appConfig.apiPath + "/refund/reject",
        //         data: {id: refundId, content: $scope.reject.content},
        //         headers: {'X-Requested-With': 'XMLHttpRequest'}
        //     }).success(function (data) {
        //         $scope.refund.status = data.status;
        //         $scope.refund.statusDesp = data.statusDesp;
        //         $scope.refund.rejectReason = $scope.reject.content;
        //         angular.element("#rejectModal").modal("hide");
        //         $scope.queryInfo();
        //     });
        // };

        // $scope.hide = function () {
        //     //移除
        //     angular.element("#tooltip").remove();
        // };
        // $scope.toolTip = function (e, imgKey) {
        //     //创建 div 元素
        //     var tooltip = "<div id='tooltip'>" +
        //         "<img src='http://img.kingsilk.net/" + imgKey + "' alt='产品预览图' height='300px'/>" +
        //         "<\/div>";
        //     //把它追加到文档中
        //     angular.element("body").append(tooltip);
        //     angular.element("#tooltip")
        //         .css({
        //             "top": 50 + "px",
        //             "right": 500 + "px"
        //         }).show("fast");
        // };
    }


}

Controller.$inject = [
    '$scope', '$http', 'loginService', '$state','$mdDialog', 'authService', '$stateParams','errorService','$timeout', 'Upload','alertService'
];

export default Controller ;
