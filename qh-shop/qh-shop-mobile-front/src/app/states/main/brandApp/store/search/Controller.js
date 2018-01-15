import conf from "../../../../../conf";
import Clipboard from "clipboard"
var $scope,
    $http,
    $state,
    $log,
    $mdBottomSheet,
    loginService,
    $mdDialog,
    $timeout,
    $stateParams,
    $location,
    alertService,
    wxService,
    authService,
    $rootScope;
class Controller {
    constructor(_$scope,
                _$http,
                _$state,
                _$log,
                _$mdBottomSheet,
                _loginService,
                _$mdDialog,
                _$timeout,
                _$stateParams,
                _$location,
                _alertService,
                _wxService,
                _authService,
                _$rootScope) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        $rootScope = _$rootScope;
        $timeout = _$timeout;
        loginService = _loginService;
        $log = _$log;
        authService = _authService;
        $stateParams = _$stateParams;
        $mdBottomSheet = _$mdBottomSheet;
        $location = _$location;
        alertService = _alertService;
        wxService = _wxService;
        /////////////////////////////////
        // loginService.loginCtl(true, $location.absUrl());
        // authService.setAuthorities($stateParams.brandAppId, $stateParams.storeId).then(function (data) {
        //     console.log('55555555555555', data)
        //     $scope.viewShow = data;
        // });
        $rootScope.brandAppId = $scope.brandAppId = $stateParams.brandAppId;
        $scope.storeId = $stateParams.storeId;

        var storage = window.localStorage;
        $scope.data={};

        $scope.data.keyWords = storage.getItem("locates");
        if (!$scope.data.keyWords) {
            $scope.data.keyWords = [];
        } else {
            $scope.data.keyWords = $scope.data.keyWords.split(",");
        }


        
        $scope.locateSearch = function (locate) {
            if (locate) {
                // 是否有相同的cookie
                var boo = false;
                for (var i = 0; i < $scope.data.keyWords.length; i++) {
                    if ($scope.data.keyWords[i] === locate) {
                        boo = true;
                        break;
                    }
                }
                // 没有相同的cookie，进行添加
                if (!boo) {
                    $scope.data.keyWords.unshift(locate);
                    if ($scope.data.keyWords.length > 5) {
                        $scope.data.keyWords.splice(5, $scope.data.keyWords.length - 5);
                    }
                    storage.setItem("locates", $scope.data.keyWords.join());
                    // Pass a key name and its value to add or update that key.
                }
            }
        };
        $scope.locateSearch(null);
        // 清空历史框
        $scope.removeCookie = function (locate) {
            for (var i = 0; i < $scope.data.keyWords.length; i++) {
                if ($scope.data.keyWords[i] === locate) {
                    $scope.data.keyWords.splice(i, 1);
                    break;
                }
            }
            storage.setItem("locates", $scope.data.keyWords.join());
            // Pass a key name and its value to add or update that key.
        };
        //清空全部搜索历史
        $scope.removeCookieAll = function () {
            ///brandApp/{brandAppId}/shop/{shopId}/mall/recentSearch
            alertService.confirm(null, "", "确定删除？", "取消", "确定").then(function (data) {
                if (data) {
                    if (loginService.getAccessToken()){
                        $http({
                            method: "DELETE",
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/mall/recentSearch",
                            params: {

                            },
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            }
                        }).then(function (resp) {
                            $scope.data = resp.data.data;
                            // console.log('$scope.data ', $scope.data);
                        }, function (resp) {
                        });
                    }else{
                        $http({
                            method: "DELETE",
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/mall/recentSearch",
                            params: {

                            },
                            headers: {
                                // 'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            }
                        }).then(function (resp) {
                            $scope.data = resp.data.data;
                            // console.log('$scope.data ', $scope.data);
                        }, function (resp) {
                        });
                    }

                    $scope.data.keyWords = [];
                    storage.setItem("locates", $scope.data.keyWords.join());
                }
            });

            // Pass a key name and its value to add or update that key.
        };
        // 进行搜索，搜索时候将搜索的值放入cookie
        $scope.search = function (text, boo) {
            //console.log($scope.searchText)
            if (boo) {
                $scope.locateSearch(text);
            }
            $state.go("main.brandApp.store.searchItem", {keyWord: text});
        };
        // 清除搜索的值
        $scope.searchTextCancel = function () {
            $scope.searchText = "";
        };


        $scope.getList = function () {
            //brandApp/{brandAppId}/shop/{shopId}/mall/recentSearch

            if (loginService.getAccessToken()){
                $http({
                    method: "GET",
                    url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/mall/recentSearch",
                    params: {

                    },
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    }
                }).then(function (resp) {
                    $scope.data = resp.data.data;
                    // console.log('$scope.data ', $scope.data);
                }, function (resp) {
                });
            }else{
                $http({
                    method: "GET",
                    url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/mall/recentSearch",
                    params: {

                    },
                    headers: {
                        // 'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    }
                }).then(function (resp) {
                    $scope.data = resp.data.data;
                    // console.log('$scope.data ', $scope.data);
                }, function (resp) {
                });
            }

        };


        if(loginService.getAccessToken()){
            $scope.getList();
        }

        $scope.fallbackPage =function () {
            $state.go('main.brandApp.store.home')
        }
    }
}

Controller.$inject = [
    '$scope',
    '$http',
    '$state',
    '$log',
    '$mdBottomSheet',
    'loginService',
    '$mdDialog',
    '$timeout',
    '$stateParams',
    '$location',
    'alertService',
    'wxService',
    'authService',
    '$rootScope'
];

export default Controller ;
