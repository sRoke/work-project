import conf from "../../../conf"
var $rootScope,
    $scope,
    $state,
    ssSideNav,
    loginService,
    sidenavTab,
    sideNavService,
    authService,
    $http,
    $mdMedia,
    $mdSidenav,
    $stateParams;
class Controller {

    constructor(_$rootScope,
                _$scope,
                _$state,
                _ssSideNav,
                _loginService,
                _sidenavTab,
                _sideNavService,
                _authService,
                _$http,
                _$mdMedia,
                _$mdSidenav,
                _$stateParams) {
        $state=_$state;
        loginService = _loginService;
        $rootScope = _$rootScope;
        $scope = _$scope;
        $http = _$http;
        ssSideNav = _ssSideNav;
        sideNavService = _sideNavService;
        authService = _authService;
        $stateParams = _$stateParams;
        $mdMedia = _$mdMedia;
        $mdSidenav = _$mdSidenav;
        this.sidenavTab = sidenavTab = _sidenavTab;
        loginService.loginCtl(true);
        $scope.brandAppId = $stateParams.brandAppId;
        authService.setAuthorities($scope.brandAppId);
        $scope.changeBack = function () {
            $("#newImg").remove();
            $("#mdIcon").hide();
            $rootScope.backShow = false;
        };
        //console.log(sidenavTab);
        $scope.menu = ssSideNav;
        // sideNavService.update(authService.getAuthorities());

        $scope.openLeft = function () {
            if($scope.leftShow){
                $scope.ts = '打开侧边栏';
                $scope.leftShow = false;
            }else {
                $scope.ts = '关闭侧边栏';
                $scope.leftShow = true;
            }
        };
        $scope.leftShow = true;
        $scope.$watch(function() { return $mdMedia('gt-sm'); }, function(data) {
            $scope.leftShow = data;
            if(data){
                $scope.ts = '关闭侧边栏';
            }else {
                $scope.ts = '打开侧边栏';
            }

        });


        $scope.openLeft = function () {
            if($scope.leftShow){
                $scope.ts = '打开侧边栏';
                $scope.leftShow = false;
            }else {
                $scope.ts = '关闭侧边栏';
                $scope.leftShow = true;
            }
        };
        $scope.leftShow = true;
        $scope.$watch(function() { return $mdMedia('gt-sm'); }, function(data) {
            $scope.leftShow = data;
            if(data){
                $scope.ts = '关闭侧边栏';
            }else {
                $scope.ts = '打开侧边栏';
            }

        });








        //获取当前用户的手机号接口以及当前商家信息
        $scope.getUser = function () {
            $http({
                method: "GET",
                url: conf.oauthPath + "/api/user/info",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
                params: {},
                withCredentials: true,
            }).success(function (resp) {
                console.log('data', resp.data.phone);
                $scope.tel = resp.data.phone;
                $scope.telShow = $scope.tel.substr(0, 3) + '****' + $scope.tel.substr(7);
            }, function (resp) {
                console.log('ERROR', resp);
            });
            //
            $http({
                method: "GET",
                url: conf.pfApiPath + "/brandApp/" + $scope.brandAppId,
                params: {}
            }).success(function (response) {
                console.log('测试', response.data);
                $scope.logo = response.data;
            });

        };
        $scope.getUser();
        //$rootScope.ssSideNav 在config.js中有用到，勿删
        $rootScope.ssSideNav = ssSideNav;


        $scope.goView = function (tab) {
            //ui-sref="{{tab.curState}}({{tab.tParams}})"

            $state.go(tab.curState, tab.tParams, {reload: false})
        }

        ////登出
        $scope.logout = () => {
            jso.wipeTokens();
            loginService.setAccessToken(null);
            loginService.setbrandAppId(null);
            $http({
                method: "POST",
                url: "https:" + conf.oauthPath + "/logout",
                headers: {},
                params: {},
                withCredentials: true,
            }).success(function (resp) {
                // console.log('data', resp);
                location.reload();
            }, function (resp) {
                console.log('ERR', resp);
            });
        };

    }

    close(tab, $event) {
        console.log("------000 close", arguments);
        if ($event) {
            $event.stopPropagation();
        }
        sidenavTab.closeTab(tab);
    }
}

Controller.$inject = [
    '$rootScope',
    '$scope',
    '$state',
    'ssSideNav',
    'loginService',
    'sidenavTab',
    'sideNavService',
    'authService',
    '$http',
    '$mdMedia',
    '$mdSidenav',
    '$stateParams'
];

export default Controller ;
