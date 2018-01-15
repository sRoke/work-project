import conf from "../../conf"
var $rootScope,
    $scope,
    ssSideNav,
    loginService,
    sidenavTab,
    $http;
class Controller {

    constructor(_$rootScope,
                _$scope,
                _ssSideNav,
                _loginService,
                _sidenavTab,
                _$http) {
        loginService = _loginService;
        $rootScope = _$rootScope;
        $scope = _$scope;
        $http = _$http;
        ssSideNav = _ssSideNav;
        this.sidenavTab = sidenavTab = _sidenavTab;


        $scope.menu = ssSideNav;

        //$rootScope.ssSideNav 在config.js中有用到，勿删
        $rootScope.ssSideNav = ssSideNav;
        ////登出
        // $scope.logout = () => {
        //     jso.wipeTokens();
        //     loginService.setAccessToken();
        //     loginService.setbrandAppId();
        //     location.href = location.protocol + conf.oauthPath + "/logout";
        // };

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
        //console.log("------000 close", arguments);
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
    '$http'
];

export default Controller ;
