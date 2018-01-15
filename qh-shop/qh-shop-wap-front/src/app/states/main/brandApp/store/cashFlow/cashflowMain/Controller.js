import conf from "../../../../../../conf";

var $scope,
    loginService,
    $state,
    $location,
    $stateParams,
    $http,
    $filter;
class Controller {
    constructor(_$scope,
                _loginService,
                _$state,
                _$location,
                _$stateParams,
                _$http,
                _$filter) {
        $scope = _$scope;
        loginService = _loginService;
        $state = _$state;
        $location = _$location;
        $stateParams = _$stateParams;
        $http = _$http;
        $filter = _$filter;
        $scope.brandAppId = $stateParams.brandAppId;
        loginService.loginCtl(true, $location.absUrl());
        $scope.curpage = 0;

        $scope.go = function (state) {
            $state.go(state);
        };

        $scope.getTime = function () {
            $http({
                method: "GET",
                url:  conf.apiPath + "/common/queryDate",
                params: {},
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $scope.today = $filter('date')(resp.data.data, 'yyyy-MM-dd');
                $scope.yesterday = $filter('date')(resp.data.data - 1000 * 60 * 60 * 24, 'yyyy-MM-dd');
                // console.log('today',$scope.today);
                // console.log('yesterday',$scope.yesterday);
                }, function () {

                }
            );
        };
        $scope.getTime();

        $scope.keyWord = '';

        $scope.pageList = function (pageNum) {
            if(pageNum){
                $scope.curpage = pageNum;
            }else {
                $scope.curpage = 0;
            }
            $http({
                method: "GET",
                url:  conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/order/list",
                params: {
                    page:$scope.curpage,
                    pageSize:conf.pageSize,
                    keyWord:$scope.keyWord,
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $scope.data = resp.data.data;
                if($scope.curpage == 0){
                    $scope.lists = resp.data.data.content;
                }else {
                    for (let i = 0;i<resp.data.data.content.length;i++){
                        $scope.lists.push(resp.data.data.content[i]);
                    }
                }
                // console.log(resp);
                }, function () {

                }
            );
        };
        $scope.pageList();

        /*返回上级*/
        $scope.fallbackPage = function () {
            var path = 'brandApp/' + $scope.brandAppId + '/home';
            window.location = conf.agencyRootUrl + path;
        };

    }
}

Controller.$inject = [
    '$scope',
    'loginService',
    '$state',
    '$location',
    '$stateParams',
    '$http',
    '$filter',
];

export default Controller ;
