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
        $scope.keyWord = $stateParams.keyWord;

        $timeout(function () {                 //商品属性选择图
            $scope.swiper = new Swiper('#swiper-container2', {
                wrapperClass: 'my-wrapper2',
                slideClass: 'my-slide2',
                pagination: '.my-swiper-pagination',
                slidesPerView: 'auto',
                paginationClickable: true,
                roundLengths: true,
                freeModeSticky: true,
                freeMode: true,
                watchSlidesProgress: true,
            });
        }, 500);


        //切换分类属性
        $scope.isClick = 0;
        $scope.choosedCategory = {};
        $scope.clickThis = function (num,category) {      // 选择那个属性
            $scope.isClick = num;
            $scope.choosedCategory = category;
            // console.log('$scope.choosedCategory===',$scope.choosedCategory);
            $scope.swiper.slideTo(num, 1000, false);
            $scope.getList(0)
        };

        //获取商品分类属性
        $scope.getCategory = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/"+ $stateParams.storeId +'/mall/category',
                headers: {
                    // 'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $scope.categoryList = [];
                for(var i=0;i<resp.data.data.length;i++ ){
                    if(!resp.data.data[i].parentId){
                        $scope.categoryList.push(resp.data.data[i]);
                    }
                }

                // $scope.swiper = new Swiper('#swiper-container2', {
                //     wrapperClass: 'my-wrapper2',
                //     slideClass: 'my-slide2',
                //     pagination: '.my-swiper-pagination',
                //     slidesPerView: 'auto',
                //     paginationClickable: true,
                //     roundLengths: true,
                //     freeModeSticky: true,
                //     freeMode: true,
                //     watchSlidesProgress: true,
                // });


            }, function (resp) {
                //TODO 错误处理
            });
        };
        $scope.getCategory();

        //切换展示最新发布等
        $scope.rank = 1;
        $scope.rankChoose = function (rankNum) {
            $scope.rank = rankNum;
        };
        $scope.rankShow = false;
        $scope.openRank = function () {
            $scope.rankShow = !$scope.rankShow;
        };



        // $scope.data = {
        //     ALL: {},
        //     APPLYING: {},   //上架中
        //     NORMAL: {},     //已上架
        //     SALE_OFF: {},   //已下架
        // };
        // $scope.status = "ALL";

        //获取商品列表
        $scope.currpage = 0;
        $scope.getList = function (page) {
            // if (!$scope.data[$scope.status].number) {
            //     $scope.data[$scope.status].number = 0;
            // }
            $scope.currpage = page;
            if(loginService.getAccessToken()){
                $http({
                    method: "GET",
                    ///brandApp/{brandAppId}/shop/{shopId}/mall/item/search
                    url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/mall/item/search",
                    params: {
                        size: conf.pageSize,
                        page: page,
                        status: 'NORMAL',
                        keyWords: $scope.keyWord,
                        category: $scope.choosedCategory.id,
                        sort:[]
                    },
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    }
                }).then(function (resp) {


                    if(page){
                        $scope.data.number = resp.data.data.number;
                        for (var i=0;i< resp.data.data.content.length;i++){
                            $scope.data.content.push(resp.data.data.content[i])
                        }
                    }else {
                        $scope.data = resp.data.data;
                    }
                    // console.log('11111111111111111111111111',$scope.data);

                    // if (resp.data.data.number == '0') {
                    //     $scope.data[$scope.status].data = []; //搜索时清空
                    // }
                    // if (!$scope.data[$scope.status].data) {
                    //     $scope.data[$scope.status].data = [];
                    // }
                    // //存入数据
                    // $scope.data[$scope.status].data = $scope.data[$scope.status].data.concat(resp.data.data.content);
                    // $scope.data[$scope.status].totalNum = resp.data.data.totalElements + 1;
                    // $scope.data[$scope.status].number = resp.data.data.number;
                    // if ((resp.data.data.number + 1) * resp.data.data.size >= resp.data.data.totalElements) {
                    //     $scope.data[$scope.status].pageEnd = true;
                    // }
                    // $scope.data[$scope.status].number++;
                }, function (resp) {
                });
            }else{
                $http({
                    method: "GET",
                    ///brandApp/{brandAppId}/shop/{shopId}/mall/item/search
                    url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/mall/item/search",
                    params: {
                        size: conf.pageSize,
                        page: page,
                        status:'NORMAL',
                        keyWords: $scope.keyWord,
                        category: $scope.choosedCategory.id,
                    },
                    headers: {
                        // 'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    }
                }).then(function (resp) {
                    if(page){
                        $scope.data.number = resp.data.data.number;
                        for (var i=0;i< resp.data.data.content.length;i++){
                            $scope.data.content.push(resp.data.data.content[i])
                        }
                    }else {
                        $scope.data = resp.data.data;
                    }
                    // console.log('11111111111111111111111111',$scope.data);
                    // if (resp.data.data.number == '0') {
                    //     $scope.data[$scope.status].data = []; //搜索时清空
                    // }
                    // if (!$scope.data[$scope.status].data) {
                    //     $scope.data[$scope.status].data = [];
                    // }
                    // //存入数据
                    // $scope.data[$scope.status].data = $scope.data[$scope.status].data.concat(resp.data.data.content);
                    // $scope.data[$scope.status].totalNum = resp.data.data.totalElements + 1;
                    // $scope.data[$scope.status].number = resp.data.data.number;
                    // if ((resp.data.data.number + 1) * resp.data.data.size >= resp.data.data.totalElements) {
                    //     $scope.data[$scope.status].pageEnd = true;
                    // }
                    // $scope.data[$scope.status].number++;
                }, function (resp) {
                });
            }

        };

        $scope.getList(0);
        // $scope.tabs($scope.status, $scope.isClick);
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
