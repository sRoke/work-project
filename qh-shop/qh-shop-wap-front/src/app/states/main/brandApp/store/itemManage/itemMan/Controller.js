import conf from "../../../../../../conf";


var $scope,
    loginService,
    $state,
    $stateParams,
    $location,
    $http;
class Controller {
    constructor(_$scope,
                _loginService,
                _$state,
                _$stateParams,
                _$location,
                _$http) {
        $scope = _$scope;
        loginService = _loginService;
        $state = _$state;
        $stateParams = _$stateParams;
        $location = _$location;
        $http = _$http;
        loginService.loginCtl(true, $location.absUrl());
        $scope.brandAppId = $stateParams.brandAppId;
        $scope.storeId = $stateParams.storeId;
        // $scope.isClick = 1;
        $scope.clickThis = function (num) {
            $scope.isClick = num;
        };

        $scope.clearKeyWord = function () {
            $scope.keyWord = '';
        };

        //搜索

        $scope.focus = function (status) {
            if (status) {
                $scope.searchShow = true;
            } else {
                $scope.searchShow = false;
            }
        };

        //
        // $scope.com = new Array(3);
        //
        //
        // $scope.getData = function () {
        //     $http({
        //         method: "GET",
        //         url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/1111/item",
        //         // headers: {
        //         //     'Authorization': 'Bearer ' + loginService.getAccessToken(),
        //         //     "brandApp-Id": $scope.brandAppId
        //         // }
        //     }).then(function (resp) {
        //
        //         $scope.listData = resp.data.data;
        //
        //         console.log('$scope.listData', $scope.listData);
        //
        //     }, function (resp) {
        //         //TODO 错误处理
        //     });
        // };
        //
        // $scope.getData()

        $scope.rank = 1;
        $scope.rankChoose = function (rankNum) {
            $scope.rank = rankNum;
        };
        $scope.rankShow = false;
        $scope.openRank = function () {
            $scope.rankShow = !$scope.rankShow;
        };
        $scope.data = {
            ALL: {},
            EDITING: {},   //上架中
            NORMAL: {},     //已上架
            SALE_OFF: {},   //已下架
        };


        $scope.status = $stateParams.status ? $stateParams.status : "ALL";
        $scope.isClick = $stateParams.isClick ? $stateParams.isClick : "1";

        $scope.tabs = function (status, tableIndex, search) {
            $scope.status = status;
            // $scope.tableIndex = tableIndex;
            $scope.isClick = tableIndex;

            if (search) {
                $scope.data[$scope.status].data = [];
                $scope.getList(0)

                // console.log(1);

            }

            if (!$scope.data[$scope.status].data) {
                $scope.data[$scope.status].data = [];
                $scope.getList()
                // console.log(2);
            }
        };

        // vm.size = 2;
        $scope.getList = function (page) {
            // console.log('page', page);
            if(page == 0){
                $scope.data[$scope.status].number = 0;
            }
            if (!$scope.data[$scope.status].number) {
                $scope.data[$scope.status].number = 0;
            }
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/item",
                params: {
                    size: conf.pageSize,
                    page: page ? page : $scope.data[$scope.status].number,
                    status: $scope.status,
                    keyWords: $scope.keyWord
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                // $scope.data = resp.data.data;
                if (resp.data.data.number == '0') {
                    $scope.data[$scope.status].data = []; //搜索时清空
                }
                if (!$scope.data[$scope.status].data) {
                    $scope.data[$scope.status].data = [];
                }
                //存入数据
                $scope.data[$scope.status].data = $scope.data[$scope.status].data.concat(resp.data.data.content);
                $scope.data[$scope.status].totalNum = resp.data.data.totalElements + 1;
                $scope.data[$scope.status].number = resp.data.data.number;
                if ((resp.data.data.number + 1) * resp.data.data.size >= resp.data.data.totalElements) {
                    $scope.data[$scope.status].pageEnd = true;
                }
                $scope.data[$scope.status].number++;

            }, function (resp) {
            });
        };


        $scope.tabs($scope.status, $scope.isClick);


        /*返回上级*/
        $scope.fallbackPage = function () {
            $state.go("main.brandApp.store.home", {}, {reload: true});
        };
    }
}

Controller.$inject = [
    '$scope',
    'loginService',
    '$state',
    '$stateParams',
    '$location',
    '$http',
];

export default Controller ;
