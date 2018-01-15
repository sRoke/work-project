import weui from "weui.js";
import conf from "../../../../conf";
import store from "store";

var $scope,
    $state,
    $interval,
    alertService,
    $http,
    loginService,
    $location,
    $stateParams,
    $rootScope;
class Controller {
    constructor(_$scope, _$state, _$interval, _alertService, _$http, _loginService, _$location, _$stateParams, _$rootScope) {
        $scope = _$scope;
        $state = _$state;
        $interval = _$interval;
        alertService = _alertService;
        $http = _$http;
        loginService = _loginService;
        $location = _$location;
        $stateParams = _$stateParams;
        $rootScope = _$rootScope;
        loginService.loginCtl(true, $location.absUrl());



        // alert($location.absUrl());
        $scope.backURL = $location.absUrl();
        //--------------------------------------------------------------------------判断状态
        $scope.page = function () {
            $http({
                method: "GET",
                // url: conf.apiPath + "/partner/check",
                url: conf.apiPath + "/brandApp/" + $stateParams.brandAppId + "/partner/check",
                data: {},
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    'brandApp-Id': $stateParams.brandAppId,
                }
            }).then(function (data) {
                    // console.log(data.data.data);
                    if (data.data.status == 10301) {
                        $scope.changeTab(1);
                    } else if (data.data.status == 10302) {
                        $scope.applications = '提交申请';
                        $scope.changeTab(2);
                    } else if (data.data.status == 10303) {
                        $scope.changeTab(2);
                        $scope.applications = '审核中';
                    } else if (data.data.status == 200) {

                        // $scope.changeTab(3);
                        $scope.changeTab(4)
                    }else if (data.data.status == 10304) {
                        $scope.applications = '重新申请';
                        $scope.ChannelId = data.data.data;
                        $scope.changeTab(2);
                    }
                }, function (error) {
                    console.log(error);
                    // if(error.status == 401){
                    //     store.set(conf.token, null);
                    //     store.remove(conf.token);
                    //     jso.wipeTokens();
                    //     store.set('login_backUrl', $scope.backURL);
                    //     $state.go("main.wxLogin", {backUrl: $scope.backURL})
                    // }
                }
            );
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $stateParams.brandAppId + "/partner/partnerTypes  ",
                data: {},
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    'brandApp-Id': $stateParams.brandAppId,
                }
            }).then(function (response) {
                    console.log(response.data.data);
                    $scope.partnerTypes=response.data.data;
                }, function (error) {
                    console.log(error);
                }
            );
        };
        $scope.page();


        //---------------------------------------------------------------地址选择控件
        $scope.address = {};
        $scope.openAddress = function () {
            weui.picker($rootScope.adc.data, {
                depth: 3,
                // defaultValue: [0, 0, 0],
                onChange: function (result) {
                    // console.log('1', result);
                },
                onConfirm: function (result) {
                    // console.log(result);
                    for (let i = 0; i < result.length; i++) {
                        // console.log(result[result.length - 1]);
                        if (result.length == 3) {
                            $scope.address.province = result[result.length - 3].label;
                            $scope.address.city = result[result.length - 2].label;
                            $scope.address.area = result[result.length - 1].label;
                        } else if (result.length == 2) {
                            $scope.address.province = result[result.length - 2].label;
                            $scope.address.city = result[result.length - 1].label;
                        } else {
                            $scope.address.province = result[result.length - 1].label;
                        }

                        $scope.address.adcNo = result[result.length - 1].value;
                    }
                    // console.log(' $scope.province', $scope.address.province);
                    // console.log(' $scope.city', $scope.address.city);
                    // console.log(' $scope.area', $scope.address.area);
                    // console.log(' $scope.adcNo', $scope.address.adcNo);
                    $scope.address.total = $scope.address.province +' '+$scope.address.city + ' '+$scope.address.area;
                    // console.log(' $scope.adcNo', $scope.address.total);
                    $scope.$apply();
                },
                id: 'multiPickerBtn'
            });
        };


        //------------------------------------------------------------------------------------------------提交申请信息
        $scope.apply = {};
        // $scope.apply.Channel = '加盟商';
        // $scope.apply.partnerType = 'LEAGUE';
        //-----------------------------------选择加盟商
        $scope.chooseChannel = function (type) {
             console.log('type=',type);
            // console.log(type == '总代理');
            if (type == '加盟商') {
                $scope.apply.partnerType = 'LEAGUE';
                $scope.apply.Channel = type;
                $scope.chooseChannelFlog = false;
            } else if (type == '总代理') {
                $scope.apply.partnerType = 'GENERAL_AGENCY';
                $scope.apply.Channel = type;
                $scope.chooseChannelFlog = false;
            } else if (type == '代理') {
                $scope.apply.partnerType = 'REGIONAL_AGENCY';
                $scope.apply.Channel = type;
                $scope.chooseChannelFlog = false;
            } else {
                $scope.chooseChannelFlog = !$scope.chooseChannelFlog;
            }
            // console.log($scope.apply.Channel);
        };
        //---------------------------------------------------------------------------------------------active控制tab改变
        $scope.changeTab = function (num) {
            if (num == 4) {
                $state.go('main.brandApp.home');
            } else {
                $scope.active = num;
            }
        };

        $scope.reapply = function () {
            $scope.applications = '提交申请';
            $scope.status = '重新申请';
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/"+$stateParams.brandAppId+"/partner/"+$scope.ChannelId,
                data: {
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    'brandApp-Id': $stateParams.brandAppId,
                }
            }).then(function (data) {
                $scope.apply={
                    phone: data.data.data.phone,
                    name: data.data.data.realName,
                    ID: data.data.data.idNo,
                    invite: data.data.data.invitationCode,
                };
                $scope.address.adcNo = data.data.data.adc;
                $scope.address.total = data.data.data.shopAddr;
                $scope.userId = data.data.data.userId;
                $scope.chooseChannel(data.data.data.partnerType);
                }, function () {
                }
            );

            $scope.changeTab(2);
        };

        $scope.applications = '提交申请';
        $scope.submitApp = function () {
            if (!$scope.apply.partnerType) {
                alertService.msgAlert("exclamation-circle", "渠道类型不能为空!");
                return false;
            }
            if (!$scope.apply.name) {
                alertService.msgAlert("exclamation-circle", "申请人姓名不能为空!");
                return false;
            }
            if (!(/^1[34578]\d{9}$/.test($scope.apply.invite))) {
                alertService.msgAlert("exclamation-circle", "邀请人手机号码有误，请重填");
                return false;
            }
            if (!(/^1[34578]\d{9}$/.test($scope.apply.phone))) {
                alertService.msgAlert("exclamation-circle", "手机号码有误，请重填");
                return false;
            }
            if (!$scope.address.adcNo) {
                alertService.msgAlert("exclamation-circle", "申请地址不能为空!");
                return false;
            }
            if ($scope.apply.ID && $scope.apply.ID.length !== 18) {
                alertService.msgAlert("exclamation-circle", "请输入正确的身份证号码!");
                return false;
            }
            if ($scope.apply.invite && !(/^1[34578]\d{9}$/.test($scope.apply.invite))) {
                alertService.msgAlert("exclamation-circle", "请输入正确的邀请手机号!");
                return false;
            }


            if($scope.status == '重新申请'){
                $http({
                    method: "PUT",
                    url: conf.apiPath + "/brandApp/"+$stateParams.brandAppId+"/partner/"+$scope.ChannelId,
                    data: {
                        partnerType: $scope.apply.partnerType,
                        phone: $scope.apply.phone,
                        realName: $scope.apply.name,
                        shopAddr: $scope.address.adcNo,
                        idNo: $scope.apply.ID,
                        invitationCode: $scope.apply.invite,
                        applyStatus:'APPLYING'
                    },
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        'brandApp-Id': $stateParams.brandAppId,
                    }
                }).then(function (data) {
                        $scope.page();
                    }, function () {

                    }
                );
            }else {
                $http({
                    method: "POST",
                    url: conf.apiPath + "/brandApp/"+$stateParams.brandAppId+"/partner",
                    data: {
                        partnerType: $scope.apply.partnerType,
                        phone: $scope.apply.phone,
                        realName: $scope.apply.name,
                        shopAddr: $scope.address.adcNo,
                        idNo: $scope.apply.ID,
                        invitationCode: $scope.apply.invite,
                    },
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        'brandApp-Id': $stateParams.brandAppId,
                    }
                }).then(function (data) {
                        // console.log('submitApp===>>',data);
                        $scope.page();
                    }, function () {

                    }
                );
            }



        };

        //---------------------------------------------------------------------------------------一些验证方法
        //验证手机号格式
        $scope.formatPhone = function (number) {
            if (!(/^1[34578]\d{9}$/.test(number))) {
                alertService.msgAlert("exclamation-circle", "请输入正确的手机号");
                return false;
            } else {
                return true;
            }
        };
        $scope.linkmanPhone = function (number) {
            if (!(/^1[34578]\d{9}$/.test(number))) {
                alertService.msgAlert("exclamation-circle", "请输入邀请人正确的手机号");
                return false;
            } else {
                return true;
            }
        };
        /**
         * 真正的发送短信
         */
        //------------------------------------------------------------------------------------------发送验证码
        $scope.gitCodeTime = '获取验证码';
        $scope.lastTime = 0;
        $scope.getVCcode = function () {
            if ($scope.lastTime <= 0) {
                //手机号验证
                if (!(/^1[34578]\d{9}$/.test($scope.firstStep.phone))) {
                    alertService.msgAlert("exclamation-circle", "手机号码有误，请重填");
                    return false;
                }
                //倒计时
                $scope.lastTime = 60;
                $http({
                    method: "GET",
                    url: conf.oauthPath + "/api/user/sendVerifyCode",
                    params: {
                        phone: $scope.firstStep.phone
                    },
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken()
                    }
                }).then(function (resp) {
                        alertService.msgAlert("success", "已发送");
                        $scope.gitCodeTime = $scope.lastTime + 's';
                        var gitCode = $interval(function () {
                            $scope.lastTime--;
                            $scope.gitCodeTime = $scope.lastTime + 's';
                            if ($scope.lastTime <= 0) {
                                $scope.gitCodeTime = '重新获取';
                                $interval.cancel(gitCode);
                            }
                        }, 1000)
                    }, function () {
                        //error
                        alertService.msgAlert("exclamation-circle", "发送失败请重试!");
                        $scope.lastTime = 0;
                    }
                );
            }
        };
        //----------------------------------------------------------------------------第一个tab提交信息
        $scope.firstStep = {};
        $scope.next = function () {
            //手机号验证
            if (!(/^1[34578]\d{9}$/.test($scope.firstStep.phone))) {
                alertService.msgAlert("exclamation-circle", "手机号码有误，请重填");
                return false;
            }
            //验证码验证
            if (!$scope.firstStep.vcCode) {
                alertService.msgAlert("exclamation-circle", "请输入验证码!");
                return false;
            }
            //--------------------------------------------------------------------------验证验证码
            $http({
                method: "GET",
                url: conf.oauthPath + "/api/user/bindPhone",
                params: {
                    code: $scope.firstStep.vcCode,
                    phone: $scope.firstStep.phone
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    'brandApp-Id': $stateParams.brandAppId,
                }
            }).then(function (resp) {
                    //-------------------------------------------------------------------注册
                    // $http({
                    //     method: "GET",
                    //     url: conf.apiPath + "/member/register",
                    //     params: {},
                    //     headers: {
                    //         'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    //         'brandApp-Id': $stateParams.brandAppId,
                    //     }
                    // }).then(function (resp) {
                    //         $scope.page();
                    //     }, function (resp) {
                    //         //error
                    //         console.log(resp.data);
                    //     }
                    // );

                    $scope.apply.phone = $scope.firstStep.phone;
                    $scope.page();
                }, function (resp) {
                    //error
                    // console.log(resp.data);
                    alertService.msgAlert("exclamation-circle", resp.data.message);
                    $scope.firstStep.vcCode = '';

                }
            );
        };

        //    --------------------------------------------------------------------------------goPurchase 去采购
        $scope.goPurchase = function () {
            $state.go('main.brandApp.home')
        }
        /*返回上级*/
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.center.home", null, {reload: true});
            } else {
                history.back();
            }
        };
    }
}

Controller
    .$inject = [
    '$scope',
    '$state',
    '$interval',
    'alertService',
    '$http',
    'loginService',
    '$location',
    '$stateParams',
    '$rootScope',
];

export
default
Controller;
