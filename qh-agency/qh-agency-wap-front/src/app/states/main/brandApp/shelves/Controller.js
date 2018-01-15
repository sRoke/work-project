import conf from "../../../../conf";
import dialog from "!html-loader?minimize=true!./dialog.html";
//import JSO from  "jso";

var $scope,
    $timeout,
    $http,
    $state,
    $log,
    loginService,
    $stateParams,
    $mdDialog,
    $location;
class Controller {
    constructor(_$scope,
                _$timeout,
                _$http,
                _$state,
                _$log,
                _loginService,
                _$stateParams,
                _$mdDialog,
                _$location) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        loginService = _loginService;
        $log = _$log;
        $stateParams = _$stateParams;
        $timeout = _$timeout;
        $location = _$location;
        let vm = this;
        /*
         * 获取brandAppId
         * */
        $scope.brandAppId = $stateParams.brandAppId;
        loginService.loginCtl(true,$location.absUrl());
        vm.choose = 'null';


        vm.account = {};
        // 积分明细

        vm.maxSize = conf.maxSize;
        //页面展示页数   5
        vm.pageSize = 10;
        //页面展示条数   10
        vm.pageEnd = false;    //是否是最后一页
        vm.number = 0;

        vm.page = function (number, pageSize) {
            if (number || pageSize) {
                vm.number = number;
                vm.pageSize = pageSize;
            }
            $http({
                method: "GET",
                url: conf.apiPath + "/item/search",
                params: {
                    number: vm.number,
                    pageSize: vm.pageSize,
                    categoryId: vm.tab
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                    var data = resp.data.data.page;
                    /**
                     * 判断是否是第1页，不是就追加数据
                     */
                    if (vm.number > 0) {
                        for (var i = 0; i < data.content.length; i++) {
                            vm.account.content.push(data.content[i]);
                        }
                    } else {
                        vm.account = data;
                    }
                    vm.number = data.number + 1;
                    vm.totalElements = data.totalElements;
                    /**
                     * 判断是否是最后一页
                     */
                    vm.pageEnd = false;
                    if (data.totalElements % vm.pageSize !== 0) {
                        if (Math.floor(data.totalElements / vm.pageSize) === data.number) {
                            vm.pageEnd = true;
                        }
                    } else {
                        if (Math.floor(data.totalElements / vm.pageSize) - 1 === data.number) {
                            vm.pageEnd = true;
                        }
                    }
                    //如果只有一页
                    if (data.totalElements <= vm.pageSize) {
                        vm.pageEnd = true;
                        vm.number = 1;
                    }
                }, function () {
                }
            );
        };
        // 面板
        vm.tabs = function (num) {
            if (num == undefined || num == 'other') {
                for (var i = 0; i < vm.comments.length; i++) {
                    vm.comments[i].checked = false;
                }
                vm.choose = num;
                vm.tab = num;
            }
            else {
                for (var i = 0; i < vm.comments.length; i++) {
                    vm.comments[i].checked = false;
                    vm.choose = false;
                    if (vm.comments[i].id === num.id) {
                        vm.tab = num ? num.id : null;
                        num.checked = true;
                        vm.choose = num;
                    }
                }
            }
            vm.number = 0;
            vm.curPage = 1;
            vm.page();
        };

        $http({
            method: "GET",
            url: conf.apiPath + "/item/getCategory",
            params: {},
            headers: {
                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                "brandApp-Id": $scope.brandAppId
            }
        }).then(function (resp) {
                vm.comments = resp.data.data.content;
                vm.tabs(null)
            }, function () {
            }
        );
        $scope.openDialog = function (ev) {
            $mdDialog.show({
                template: dialog,
                parent: angular.element(document.body).find('#qh-agency-wap-front'),
                targetEvent: ev,
                clickOutsideToClose: true,
                fullscreen: false,
                controller: [function () {
                }],
                controllerAs: "vm"
            }).then(function (answer) {
            }, function () {
            });
        };

        // $timeout(function () {
        //
        // }, 150)

    }
}

Controller.$inject = [
    '$scope',
    '$timeout',
    '$http',
    '$state',
    '$log',
    'loginService',
    '$stateParams',
    '$mdDialog',
    '$location'
];

export default Controller ;
