import conf from "../../../../../../conf";


var $scope,
    loginService,
    $state,
    $stateParams,
    $interval,
    $location,
    $mdDialog,
    $http
class Controller {
    constructor(_$scope,
                _loginService,
                _$state,
                _$stateParams,
                _$interval,
                _$location,
                _$mdDialog,
                _$http) {
        $scope = _$scope;
        loginService = _loginService;
        $state = _$state;
        $stateParams = _$stateParams;
        $interval =_$interval;
        $location=_$location;
        $mdDialog=_$mdDialog;
        $http = _$http;
        $scope.brandAppId = $stateParams.brandAppId;
        $scope.storeId= $stateParams.storeId;
        loginService.loginCtl(true,$location.absUrl());
        $scope.salesStatus = '1';
        $scope.changeTab = function (num) {
            $scope.salesStatus = num;
        };
        $scope.btn=function () {
            $mdDialog.show({
                templateUrl: 'messageCode.html',
                parent: angular.element(document.body).find('#qh-agency-admin-front'),
                clickOutsideToClose: true,   //true 点击界外的时候弹窗消失
                fullscreen: false,
                locals: {key: $scope.info},
                controller: ['$mdDialog',  function ($mdDialog) {
                    var vmd = this;
                    vmd.codeShow = false;
                    //取消
                    vmd.cance = function () {
                        $mdDialog.cancel();
                    };
                    vmd.gitCodeTime = '获取验证码';
                    vmd.getCodeCon = '绑定支付宝需要手机短信验证';
                    vmd.lastTime = 0;
                    //获取短信验证码
                    vmd.getVCcode = function () {
                        if (vmd.lastTime <= 0) {
                            //倒计时
                            vmd.lastTime = 60;
                            $http({
                                method: "POST",
                                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/"+$scope.storeId+"/withdraw/ali",
                                data: {
                                    realName:$scope.name,
                                    account:$scope.phone,
                                },
                                headers: {
                                    'Authorization': 'Bearer ' + loginService.getAccessToken()
                                }
                            }).then(function (resp) {
                                    //设置下一步点击
                                    // console.log(1, resp);
                                    if (resp.status == '10086') {
                                        // alert();
                                        vmd.getCodeCon = "短信发送过于频繁，请稍后重试!";
                                        return false;
                                    }else{
                                        // console.log(111111,resp);
                                        var str = resp.data.data;
                                        vmd.getCodeCon = '短信已发送至'+str;
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
                    vmd.getCodeShow = function () {
                        vmd.codeShow = false;
                    };


                    //确认
                    vmd.confirm = function () {
                        //点击确认后调用短信验证码    核对接口
                        if (!vmd.code) {
                            vmd.codeShow = true;
                            return false;
                        }
                        if (vmd.code.length != 6) {
                            vmd.codeShow = true;
                            return false;
                        }
                        // $mdDialog.hide();
                        $http({
                            method: "PUT",  ///brandApp/{brandAppId}/shop/{shopId}/withdraw/checkAli   PUT  code
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/"+$scope.storeId+"/withdraw/checkAli",
                            params: {
                                code: vmd.code
                            },
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken()
                            }
                        }).then(function (resp) {
                                //设置下一步点击
                                // console.log(resp);
                                if (resp.data.status == '10080') {
                                    //alertService.msgAlert("exclamation-circle", "验证码错误!");
                                    vmd.codeShow = true;
                                    return false;
                                } else if (resp.data.status == '200') {
                                    // alertService.msgAlert("exclamation-circle", "验证码成功!");
                                    $mdDialog.cancel();

                                    $scope.fallbackPage();
                                    // $state.go("main.brandApp.store.shop.drawType", null, {reload: true});
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
        };









        /*返回上级*/
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.store.home", {brandAppId:$stateParams.brandAppId}, {reload: true});
            } else{
                history.back();
            }


            //     if($stateParams.form){
            //     $state.go($stateParams.form, {id:$stateParams.formPageId}, {reload: true});
            // }else {
            //     $state.go("main.brandApp.store.shop.drawType", {brandAppId:$stateParams.brandAppId}, {reload: true});
            // }
        };
    }
}

Controller.$inject = [
    '$scope',
    'loginService',
    '$state',
    '$stateParams',
    '$interval',
    '$location',
    '$mdDialog',
    '$http',
];

export default Controller ;
