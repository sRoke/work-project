import conf from "../../../../conf";

var $scope,
    $http,
    $state,
    $log,
    $filter,
    $mdBottomSheet,
    loginService,
    $mdDialog,
    $timeout,
    $stateParams,
    $location;
class Controller {
    constructor(_$scope,
                _$http,
                _$state,
                _$log,
                _$filter,
                _$mdBottomSheet,
                _loginService,
                _$mdDialog,
                _$timeout,
                _$stateParams,
                _$location) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        $timeout = _$timeout;
        loginService = _loginService;
        $filter = _$filter;
        $log = _$log;
        $stateParams = _$stateParams;
        $mdBottomSheet = _$mdBottomSheet;
        $location = _$location;
        /////////////////////////////////
        loginService.loginCtl(true, $location.absUrl());
        $scope.brandAppId = $stateParams.brandAppId;
        $scope.currentPage = 0;

        $scope.tab = 1;
        $scope.labelListShow = '';
        $scope.categoryName = '筛选类别';

        $scope.changeTab = function (tabNum) {
            $scope.currentPage = 0;
            $scope.keyWord = null;
            $scope.itemList = [];
            $scope.tab = tabNum;
            if (tabNum == 4) {
                if ($scope.labelListShow == false) {
                    $scope.labelListShow = true;
                } else {
                    $scope.labelListShow = false;
                }
            }else if(tabNum==2 || tabNum==3){
                $scope.categoryId = null;
                $scope.categoryName = '筛选类别';
                if ($scope.labelListShow == '') {
                    $scope.getItemList('out');        //对紧缺的 售磬的做单独处理为空
                    return;
                }
                if ($scope.labelListShow !== false) {
                    $scope.labelListShow = false;
                    $scope.getItemList('out');     //对紧缺的 售磬的做单独处理为空
                }
            }else {
                $scope.categoryId = null;
                $scope.categoryName = '筛选类别';
                if ($scope.labelListShow == '') {
                    $scope.getItemList();
                    return;
                }
                if ($scope.labelListShow !== false) {
                    $scope.labelListShow = false;
                    $scope.getItemList();
                }

            }
        };

        //-------------------------------------------------------------获取列表
        $scope.itemList = [];
        $scope.getItemList = function (keyword) {
            if (keyword == 'search') {
                $scope.itemList = [];
                $scope.currentPage = 0;
                // $scope.categoryId = null;
            }

            $http({
                method: 'GET',
                url: conf.apiPath + '/brandApp/' + $scope.brandAppId + '/partner/123456789/skuStore',
                params: {
                    page: $scope.currentPage,
                    size: conf.pageSize,
                    keyWord: $scope.keyWord,
                    categoryId: $scope.categoryId,
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $scope.pageData = resp.data.data;
                if (keyword != 'more') {
                    $scope.itemList = [];
                }
                for (var i = 0; i < resp.data.data.content.length; i++) {
                    $scope.itemList.push(resp.data.data.content[i])
                }
                if(keyword=='out'){
                    console.log(11111111111111111111);
                    $scope.itemList = [];
                }
                $scope.currentPage++;

            })
        };
        $scope.getItemList();

        //----------------------------------------获取帅选分类
        $scope.categorys = [];
        $scope.getCategory = function () {
            $http({
                method: 'GET',
                // url: conf.apiPath + "/item/search",
                url: conf.apiPath + '/brandApp/' + $scope.brandAppId + '/partner/123/category',
                params: {
                    page: $scope.currentPage,
                    size: conf.pageSize
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                // console.log('111111111111111', resp.data);
                $scope.categorys = resp.data.data;
            })
        };
        $scope.getCategory();

        $scope.changeCategoryName = function (data) {
            $scope.currentPage = 0;
            $scope.itemList = [];
            $scope.categoryName = data.name;
            $scope.categoryId = data.id;
            $scope.labelListShow = !$scope.labelListShow;
            $scope.getItemList();
        };


        /*返回上级*/
        $scope.fallbackPage = function () {
            $state.go("main.brandApp.home", null, {reload: true});
        };
    }
}

Controller.$inject = [
    '$scope',
    '$http',
    '$state',
    '$log',
    '$filter',
    '$mdBottomSheet',
    'loginService',
    '$mdDialog',
    '$timeout',
    '$stateParams',
    '$location'
];

export default Controller ;
