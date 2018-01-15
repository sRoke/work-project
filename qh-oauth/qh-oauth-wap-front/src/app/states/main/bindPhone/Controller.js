import conf from "../../../conf";
let $scope,
    alertService,
    $rootScope,
    $interval,
    $state,
    $stateParams,
    loginService,
    $http;
class Controller {
    constructor(_$scope,
                _$interval,
                _$rootScope,
                _alertService,
                _$state,
                _$stateParams,
                _loginService,
                _$http) {
        /////////////////////////////////////通用注入
        $scope = _$scope;
        $state = _$state;
        $interval = _$interval;
        alertService = _alertService;
        $rootScope = _$rootScope;
        $http = _$http;
        loginService = _loginService;
        $stateParams = _$stateParams;

        ////////////////////////////////////变量定义
        const TAG = "main/bindPhone ";
        console.log(`==> ${TAG}`);
        // loginService.loginCtl(true);

        // let vm = this;
        // vm.show = {
        //     state: 1,
        //     yzm: "发送验证码"
        // };
        // //返回上级
        // vm.fallbackPage = () => {
        //     if (history.length === 1) {
        //         $state.go("main.wxLogin", null, {reload: true});
        //     } else {
        //         history.back();
        //     }
        // };
        //
        // /**
        //  * 真正的发送短信
        //  */
        // vm.realSendCode = function () {
        //     $http({
        //         method: "GET",
        //         url: conf.oauthPath + "/api/user/sendVerifyCode",
        //         params: {
        //             phone: vm.show.phone
        //         },
        //         headers: {
        //             'Authorization': 'Bearer ' + loginService.getAccessToken()
        //         }
        //     }).then(function (resp) {
        //             alertService.msgAlert("success", "已发送");
        //             if ($rootScope.intervalStop) {
        //                 $interval.cancel($rootScope.intervalStop);//解除计时器
        //             }
        //             var i = 60;
        //             $rootScope.intervalStop = $interval(function () {
        //                 i--;
        //                 vm.show.yzm = i + 'S';
        //                 vm.show.state = 2;
        //                 if (i <= 0) {
        //                     vm.show.yzm = "重新发送";
        //                     vm.show.state = 1;
        //                     $interval.cancel($rootScope.intervalStop);//解除计时器
        //                     $rootScope.intervalStop = null;
        //                 }
        //             }, 1000);
        //         }, function () {
        //             //error
        //         }
        //     );
        // };
        //
        //
        // vm.isPhoneNumTrue = false;
        // //限制手机号输入，只能输入以1开头的数字，不做正则验证
        // vm.checkPhone = function () {
        //     var phone = vm.show.phone + '';
        //     //如果未输入
        //     if (!phone || phone === '' || phone === 'null') {
        //         vm.isPhoneNumTrue = false;
        //         return -1;
        //     }
        //     //如果不是数字、不是以小数点结尾或者不是以1开头，抹去最后一位
        //     if (isNaN(phone) || phone.substr(0, 1) !== '1') {
        //         vm.show.phone = phone.substr(0, phone.length - 1);
        //         vm.isPhoneNumTrue = false;
        //         return -1;
        //     }
        //     //如果长度超过11，抹去最后一位
        //     if (phone.length > 11) {
        //         vm.show.phone = phone.substr(0, phone.length - 1);
        //         vm.isPhoneNumTrue = true;
        //         return -1;
        //     }
        //     if (phone.length !== 11) {
        //         vm.isPhoneNumTrue = false;
        //         return -1;
        //     }
        //     vm.isPhoneNumTrue = true;
        //     return 0;
        // };
        //
        // vm.isCodeNumTrue = false;
        // //限制验证码输入
        // vm.checkCode = function () {
        //     var code = vm.show.code + '';
        //     //如果未输入
        //     if (!code || code === '' || code === 'null') {
        //         vm.isCodeNumTrue = false;
        //         return -1;
        //     }
        //     //如果不是数字，抹去最后一位
        //     if (isNaN(code)) {
        //         vm.show.code = code.substr(0, code.length - 1);
        //         vm.isCodeNumTrue = false;
        //         return -1;
        //     }
        //     //如果长度超过6，抹去最后一位
        //     if (code.length > 6) {
        //         vm.show.code = code.substr(0, code.length - 1);
        //         vm.isCodeNumTrue = true;
        //         return -1;
        //     }
        //     if (code.length !== 6) {
        //         vm.isCodeNumTrue = false;
        //         return -1;
        //     }
        //     vm.isCodeNumTrue = true;
        //     return 0;
        // };
        //
        //
        // vm.submit = function () {
        //     if (!vm.isPhoneNumTrue) {
        //         alertService.msgAlert("cancle", "正确输入手机号");
        //         return;
        //     }
        //     if (!vm.isCodeNumTrue) {
        //         alertService.msgAlert("cancle", "正确输入验证码");
        //         return;
        //     }
        //     $http({
        //         method: "GET",
        //         url: conf.oauthPath + "/api/user/bindPhone",
        //         params: {
        //             code: vm.show.code,
        //             phone: vm.show.phone
        //         },
        //         headers: {
        //             'Authorization': 'Bearer ' + loginService.getAccessToken()
        //         }
        //     }).then(function (resp) {
        //             alertService.msgAlert("success", "绑定成功");
        //             $state.go('main.loginTime');
        //         }, function (resp) {
        //             //error
        //             console.log(resp.data);
        //             alertService.msgAlert("cancle", resp.data.message);
        //
        //         }
        //     );
        // };
    }
}

Controller
    .$inject = [
    '$scope',
    '$interval',
    '$rootScope',
    'alertService',
    '$state',
    '$stateParams',
    'loginService',
    '$http'
];

export
default
Controller;
