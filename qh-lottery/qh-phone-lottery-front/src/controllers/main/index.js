(function () {
    angular.module('qh-phone-lottery-front').config(['$stateProvider', function ($stateProvider) {
        /**
         * 主页
         */
        $stateProvider.state("main.index", {
            url: "/?&id",
            views: {
                "@": {
                    templateUrl: 'views/main/index.html',
                    controller: IndexController,
                    controllerAs: 'vm'
                }
            }
        });
    }]);
    IndexController.$inject = ['$scope', '$rootScope', '$http', '$interval', '$state', "alertService", 'wxService', 'appConfig', '$httpParamSerializer', '$timeout','$location'];
    function IndexController($scope, $rootScope, $http, $interval, $state, alertService, wxService, appConfig, $httpParamSerializer, $timeout,$location) {



        $http({
            method: 'GET',
            url: appConfig.apiPath + '/phoneLottery/getImg',
            params: {
                id: $location.search().id,
            },
            headers: {}
        }).then(function (resp) {
            $scope.isEarlier = resp.data.isEarlier;
            $scope.overdue = resp.data.overdue;
            $scope.loginImg = appConfig.imgUrl + resp.data.loginImg;
            $scope.qRCodeImg = appConfig.imgUrl + resp.data.qRCodeImg;
            if (wxService.isInWx()) {
                var curConf = {
                    title: resp.data.shareInfo.title,
                    desc: resp.data.shareInfo.desc,
                    link: resp.data.shareInfo.url,
                    imgUrl: 'https:' + appConfig.imgUrl + resp.data.shareInfo.imgUrl,
                    success: function () {
                        // 用户确认分享后执行的回调函数
                        // console.log(1111111111111111111);
                    },
                    cancel: function () {
                        // 用户取消分享后执行的回调函数
                        // alertService.msgAlert('exclamation-circle', "取消分享");
                    }
                };
                wxService.initShareOnStart(curConf);
            }
        });


        $http({
            method: 'GET',
            url: appConfig.apiPath + '/phoneLottery/initSession',
            params: {},
            headers: {}
        }).then(function (resp) {
        });

        $scope.goLottery = function () {
            if (!$scope.user.phone || $scope.user.phone.length <= 10) {
                return;
            }
            $state.go('main.lottery', {id: $state.params.id, phone: $scope.user.phone}, {reload: true})
        };
        var vm = this;
        $scope.title = '绑定手机号';
        $scope.confirm = '确认';
        // console.log(appConfig.token);
        // if ($state.params.noWx === 'true') {
        //     $scope.title = '手机号登录';
        //     $scope.confirm = '登录';
        // }
        $scope.user = {};
        $scope.fsyzm = "发送验证码";
        $scope.state = 1;
        $scope.srcState = $state.params.backUrl;
        $scope.isLogin = false;

        vm.isPhoneNumTrue = false;
        //限制手机号输入，只能输入以1开头的数字，不做正则验证
        $scope.checkPhone = function () {
            var phone = $scope.user.phone + '';
            //如果未输入
            // console.log(phone)
            if (!phone || phone === '' || phone === 'null') {
                vm.isPhoneNumTrue = false;
                return -1;
            }
            //如果不是数字、不是以小数点结尾或者不是以1开头，抹去最后一位
            if (isNaN(phone) || phone.substr(0, 1) !== '1') {
                $scope.user.phone = phone.substr(0, phone.length - 1);
                vm.isPhoneNumTrue = false;
                return -1;
            }
            if (isNaN(phone) || phone.substr(1, 1) == '0' || phone.substr(1, 1) == '1' || phone.substr(1, 1) == '2') {
                $scope.user.phone = phone.substr(0, phone.length - 1);
                vm.isPhoneNumTrue = false;
                return -1;
            }
            //如果长度超过11，抹去最后一位
            if (phone.length > 11) {
                $scope.user.phone = phone.substr(0, phone.length - 1);
                vm.isPhoneNumTrue = true;
                return -1;
            }
            if (phone.length !== 11) {
                vm.isPhoneNumTrue = false;
                return -1;
            }
            vm.isPhoneNumTrue = true;
            return 0;
        };
        vm.isCodeNumTrue = false;
        //限制验证码输入
        $scope.checkCode = function () {
            var code = $scope.user.code + '';
            //如果未输入
            if (!code || code === '' || code === 'null') {
                vm.isCodeNumTrue = false;
                return -1;
            }
            //如果不是数字，抹去最后一位
            if (isNaN(code)) {
                $scope.user.code = code.substr(0, code.length - 1);
                vm.isCodeNumTrue = false;
                return -1;
            }
            //如果长度超过11，抹去最后一位
            if (code.length > 6) {
                $scope.user.code = code.substr(0, code.length - 1);
                vm.isCodeNumTrue = true;
                return -1;
            }
            if (code.length !== 6) {
                vm.isCodeNumTrue = false;
                return -1;
            }
            vm.isCodeNumTrue = true;
            return 0;
        };
        $scope.loginSuccess = function () {
            $scope.isLogin = true;
            if ($scope.isLogin && $state.params.backUrl) {
                // 登陆成功才能返回刚才的url
                $state.go('main.lottery', {req: $state.params.req, userId: store.get(appConfig.token)}, {reload: true})
                // var urldecode = decodeURIComponent($state.params.backUrl);
                // location.href = urldecode;
            }
            else {
                history.go(-1);
            }
        };
        $scope.submit = function () {
            //提交数据
            $http({
                method: 'POST',
                url: appConfig.apiPath + '/phoneLottery/loginPhone',
                data: $httpParamSerializer({
                    phone: $scope.user.phone,
                    code: $scope.user.code
                }),
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
                }
            }).then(function (resp) {



                // if (resp.data.couponSize > 0) {
                //     alertService.msgAlert(null, "注册红包领取成功，请到个人中心查看").then(function () {
                //         $scope.loginSuccess();
                //     });
                // } else {
                //     $scope.loginSuccess();
                // }
            });
        };



        // 回退页面
        $scope.fallbackPage = function () {
            if ($scope.isLogin && $state.params.backUrl) {
                // 登陆成功才能返回刚才的url
                var urldecode = decodeURIComponent($state.params.backUrl);
                location.href = urldecode;
            } else if ($state.params.s === "index" || history.length <= 2 || $scope.isLogin || $state.params.s === '') {
                $state.go("main.index", null, {reload: true});
                return;
            } else {
                history.back();
            }
        };
    }
})();



