import conf from "../../../../conf";
// import Upload from ""
var $scope,
    $http,
    loginService,
    authService,
    $stateParams,
    alertService,
    errorService,
    $state;


Controller.$inject = [
    '$scope',
    '$http',
    'loginService',
    'authService',
    'alertService',
    '$state',
    'errorService',
    '$stateParams'];
function Controller(_$scope, _$http, _loginService, _authService, _alertService,_errorService, _$state, _$stateParams) {
    $scope = _$scope;
    $http = _$http;
    $stateParams = _$stateParams;
    loginService = _loginService;
    authService = _authService;
    $state = _$state;
    errorService=_errorService;
    alertService = _alertService;
    loginService.loginCtl(true);
    //权限相关
    $scope.ITEM_C = authService.hasAuthor("ITEM_C");    //增
    $scope.ITEM_U = authService.hasAuthor("ITEM_U");    //改
    $scope.ITEM_R = authService.hasAuthor("ITEM_R");    //读
    $scope.ITEM_D = authService.hasAuthor("ITEM_D");    //删
   // console.log('NORMAL' && $scope.ITEM_D);

    $scope.brandAppId = $stateParams.brandAppId;
    $scope.searchTitle = '';
    $scope.searchStatus = '';
    $scope.status = [
        {status: "EDITING", value: '编辑中'},
        {status: "NORMAL", value: '正常'},
        {status: "SALE_OFF", value: '已下架'}
    ];
    $scope.data = {};
    $scope.id = '';
    $scope.curPage = 1;
    $scope.pageChanged = function (curPage) {
        // if ($scope.data.number >$scope.data.totalPages){
        //     return;
        // };
      //  console.log('222222222',curPage);
        $http({
            method:"GET",
            url:conf.apiPath + "/brandApp/"+$scope.brandAppId+"/item",
            headers: {
                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                "brandApp-Id": $scope.brandAppId
            },
            params: {
                page: curPage ?curPage:$scope.curPage-1,
                size: conf.pageSize,
                title: $scope.searchTitle,
                status: $scope.searchStatus,
            }
        }).success(function (response) {
            $scope.data = response.data;
            //console.log('111111',response.data);
            $scope.curPage =response.data.number+1;
        });
    };
    // 初始化的时候就获取第一页的数据
    $scope.pageChanged();
    //删除
    $scope.delete = function (id, itemProp) {

        alertService.confirm(null, "确定删除该商品？", "温馨提示", "取消", "确认")
            .then(function (data) {
                if (data) {
                    $http({
                        method:'DELETE',
                        url:conf.apiPath + "/brandApp/"+$scope.brandAppId+"/item/"+id,
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        },
                    }).success(function (data) {
                        $scope.pageChanged();
                    });
                }
            });
    };
    //下架

    $scope.saleDialog = function (id, status) {
        var changeToStatus = '';
        if (status == '下架') {
            changeToStatus = "SALE_OFF";
        } else {
            if (status == '上架') {
                changeToStatus = "NORMAL";
            } else {
                //alert('出错了');
                return errorService.error("出错！", null)
            }
        }
        alertService.confirm(null, "确定" + status + "该商品？", "温馨提示", "取消", "确认")
            .then(function (data) {
                if (data) {
                    $http({
                        method: 'PUT',
                        url:conf.apiPath + "/brandApp/"+$scope.brandAppId+"/item/"+id+"/changeStatus",
                        params: {
                            status: changeToStatus
                        },
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        },
                    }).success(function (data) {
                        if (data.status == 200) {
                            $scope.pageChanged();
                        }
                    });
                }
            });
    };
}

export default Controller;
