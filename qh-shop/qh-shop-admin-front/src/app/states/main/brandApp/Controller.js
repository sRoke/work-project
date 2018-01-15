import conf from "../../../conf"
var $rootScope,
    $scope,
    ssSideNav,
    loginService,
    sidenavTab,
    sideNavService,
    authService,
    $http,
    $state,
    $mdSidenav,
    $mdMedia,
    $stateParams;
class Controller {

    constructor(_$rootScope,
                _$scope,
                _ssSideNav,
                _loginService,
                _sidenavTab,
                _sideNavService,
                _authService,
                _$http,
                _$state,
                _$mdSidenav,
                _$mdMedia,
                _$stateParams) {
        loginService = _loginService;
        $rootScope = _$rootScope;
        $scope = _$scope;
        $http = _$http;
        ssSideNav = _ssSideNav;
        sideNavService = _sideNavService;
        authService = _authService;
        $stateParams = _$stateParams;
        $state = _$state;
        $mdMedia = _$mdMedia;
        $mdSidenav = _$mdSidenav;

        this.sidenavTab = sidenavTab = _sidenavTab;
        loginService.loginCtl(true);
        $scope.brandAppId = $stateParams.brandAppId;
        authService.setAuthorities($scope.brandAppId);
        $scope.changeBack  = function () {
            $("#newImg").remove();
            $("#mdIcon").hide();
            $rootScope.backShow = false;
        };
        //console.log(sidenavTab);
        $scope.menu = ssSideNav;
        // sideNavService.update(authService.getAuthorities());


        $scope.viewTab = function (tab) {
            $state.go(tab.curState, tab.tParams, {reload: false})
        };


        //获取当前用户的手机号接口
        $scope.getUser=function(){
            $http({
                method: "GET",
                url:conf.oauthPath+"/api/user/info",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
                params: {},
                withCredentials:true,
            }).success(function (resp) {
                 console.log('data', resp.data.phone);
                 $scope.tel= resp.data.phone;
                 $scope.telShow= $scope.tel.substr(0,3)+'****'+ $scope.tel.substr(7);
            },function(resp){
                console.log('ERROR', resp);
            });

        };
        $scope.getUser();
        //$rootScope.ssSideNav 在config.js中有用到，勿删
        $rootScope.ssSideNav = ssSideNav;

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

        ////登出
        $scope.logout = () => {
            jso.wipeTokens();
            loginService.setAccessToken(null);
            loginService.setbrandAppId(null);
            $http({
                method: "POST",
                url: "https:" + conf.oauthPath +"/logout",
                headers: {},
                params: {},
                withCredentials:true,
            }).success(function (resp) {
                // console.log('data', resp);
                location.reload();
            },function(resp){
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
    'ssSideNav',
    'loginService',
    'sidenavTab',
    'sideNavService',
    'authService',
    '$http',
    '$state',
    '$mdSidenav',
    '$mdMedia',
    '$stateParams'
];

export default Controller ;
