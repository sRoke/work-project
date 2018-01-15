import conf from "../../../../conf";
import moment from "moment";
import dialog from "!html-loader?minimize=true!./linkShop.html";
import laydate from "layui-laydate";
//import Mock from 'mockjs'
var $scope,
    $http,
    authService,
    $state,
    $log,
    $filter,
    loginService,
    $mdDialog,
    alertService,
    errorService,
    $stateParams;
class Controller {
    constructor(_$scope,
                _$http,
                _$state,
                _$log,
                _$filter,
                _authService,
                _loginService,
                _$mdDialog,
                _alertService,
                _errorService,
                _$stateParams) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        authService = _authService;
        loginService = _loginService;
        $log = _$log;
        $filter = _$filter;
        alertService = _alertService;
        errorService = _errorService;
        $stateParams = _$stateParams;
        /////////////////////////////////
        loginService.loginCtl(true);
        $scope.brandAppId = $stateParams.brandAppId;
        //获取权限，勿删
        // authService.setAuthorities($scope.brandAppId);
        //console.log("====================", $stateParams);

        $scope.PARTNERLICENSE_U = authService.hasAuthor("PARTNERLICENSE_U");    //授权
        $scope.PARTNERLICENSE_R = authService.hasAuthor("PARTNERLICENSE_R");    //查看
        $scope.PARTNERLICENSE_D = authService.hasAuthor("PARTNERLICENSE_D");    //禁用
        // $scope.MEMBER_E = authService.hasAuthor("MEMBER_E");    //导出


        //时间选择器
        laydate.render({
            elem: '#test1', //指定元素
            done: function (value, date) {      //事件完成回调 将value赋值给自己定义的字符串
                $scope.startDate = value;
            }

        });
        laydate.render({
            elem: '#test2', //指定元素
            done: function (value, date) {      //事件完成回调 将value赋值给自己定义的字符串
                $scope.endDate = value;
            }
        });
        // js控制写在此处
        $scope.curPage = 1;
        //前端进行请求
        $scope.pageChanged = function (curPage) {
            $scope.startTime = $filter('date')($scope.startDate, 'yyyy-MM-dd');
            $scope.endTime = $filter('date')($scope.endDate, 'yyyy-MM-dd');
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner",
                params: {
                    page: curPage ? curPage : $scope.curPage - 1,
                    size: conf.pageSize,
                    status: $scope.applyStatus,
                    source: 'PASS',
                    applyType: $scope.appType,
                    startDate: $scope.startTime,
                    endDate: $scope.endTime,
                    keyWord: $scope.keyWords
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
            }).success(function (response) {
                // console.log(response);
                // console.log(response.data.content);
                $scope.items = response.data.content;
                $scope.page = response.data;
                $scope.curPage = response.data.number + 1;
            });
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
        }
        $scope.pageChanged();

        //状态禁用启用
        $scope.changeStatus = function (id, status, name) {
            alertService.confirm(null, "确定" + name + "该用户？", "温馨提示", "取消", "确认")
                .then(function (data) {
                    if (data) {
                        $http({
                            method: "PUT",
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/" + id + "/changeStatus",
                            params: {
                                status: status
                            },
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            },
                        }).success(function (data) {
                            console.log(data);
                            if (data.status == 200) {
                                $scope.pageChanged();
                            }
                        });
                    }
                });
        }

        $scope.linkShop = function (userId, ev, item) {
            $mdDialog.show({
                template: dialog,
                targetEvent: ev,
                clickOutsideToClose: true,
                // locals: locals,
                fullscreen: false,
                controller: [function () {
                    var vm = this;

                    vm.data = item;
                    vm.getUserInfo = function () {
                        $http({
                            method: "get",
                            url: conf.oauthPath + "/api/user/" + vm.data.userId,
                            // url: 'http://user/info',
                            data: {},
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                'brandApp-Id': $stateParams.brandAppId ? $stateParams.brandAppId : '567b614be4b0f72f9f6cf02e',
                            }
                        }).then(function (data) {
                                vm.userPhone = data.data.data;

                                console.log('vm.userPhone', vm.userPhone);
                            }, function () {

                            }
                        );
                    };
                    vm.getUserInfo();
                    console.log('vm.data', vm.data);
                    vm.getShopList = function () {
                        // /brandApp/{brandAppId}/shop/getShopList
                        $http({
                            method: "GET",
                            url: conf.shopPath + "/brandApp/" + $scope.brandAppId + "/shop/getShopList",
                            params: {
                                userId: userId
                            },
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            },
                        }).success(function (response) {

                            console.log('response', response);
                            if (response.status == "200") {
                                //判断成功后执行

                                vm.shopList = response.data;

                            } else {
                                //保存失败执行
                                return errorService.error(response.data, null)
                            }
                        });
                    };


                    vm.shop = {};


                    vm.getShop = function () {

                        ///brandCom/getBrandAppList  brandAppId

                        $http({
                            method: "GET",
                            url: conf.pfApiPath + "/brandCom/getBrandAppList",
                            params: {
                                brandAppId: $scope.brandAppId
                            },
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            },
                        }).success(function (response) {

                            console.log('response', response);
                            if (response.status == "200") {
                                //判断成功后执行

                                vm.shopData = response.data;

                            } else {
                                //保存失败执行
                                return errorService.error(response.data, null)
                            }
                        });

                    };

                    vm.getShop();


                    vm.update = function () {

                        if (!vm.userPhone) {
                            return
                        }
                        else {
                            $http({
                                method: "POST",
                                url: conf.shopPath + "/brandApp/" + vm.shop.selectBrandAppId + "/shop",
                                data: {
                                    phone: vm.userPhone.phone,
                                    name: vm.data.realName,
                                    adcNo: vm.data.adc,
                                    address: vm.data.shopAddr,
                                    img: '',
                                    tel: vm.userPhone.phone,
                                    shopType: 'COOPERATE'
                                },
                                headers: {
                                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                    "brandApp-Id": $scope.brandAppId
                                },
                            }).success(function (data) {
                                console.log(data.data);
                                if (data.status == 200) {
                                    vm.shop.selectShopId = data.data;
                                    alert('创建成功');
                                    vm.cancel();
                                }
                            });
                        }
                    };


                    vm.cancel = function () {
                        return $mdDialog.cancel();
                    };
                }],
                controllerAs: "vm"
            }).then(function (answer) {
                $scope.pageChanged();
            }, function () {
            });
        }
        $scope.getShop = function () {

            $http({
                method: "GET",
                url: conf.pfApiPath + "/brandCom/getBrandAppList",
                params: {
                    brandAppId: $scope.brandAppId
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
            }).success(function (response) {

                console.log('response', response);
                if (response.status == "200") {
                    //判断成功后执行

                    $scope.shopData = response.data;

                } else {
                    //保存失败执行
                    return errorService.error(response.data, null)
                }
            });

        };

        $scope.getShop();


        $scope.linkAddress = conf.linkPath + $scope.brandAppId + "/home";

        // $scope.copyUrl2=function(){
        //     var Url2=document.getElementById("addr");
        //     Url2.select(); // 选择对象
        //     document.execCommand("Copy"); // 执行浏览器复制命令
        //     return errorService.error("链接地址已复制好，可贴粘。", null);
        // };
        // $scope.copy=function(){
        //     var Url2=document.getElementById("addr");
        //     Url2.select(); // 选择对象
        // };
        // $scope.addressShow=false;
        // $scope.isShow=function(){
        //     $scope.addressShow=true;
        // };
        // $scope.isHide=function(){
        //     $scope.addressShow=false;
        // };

    }
}

Controller.$inject = [
    '$scope',
    '$http',
    '$state',
    '$log',
    '$filter',
    'authService',
    'loginService',
    '$mdDialog',
    'alertService',
    'errorService',
    '$stateParams'
];

export default Controller ;

