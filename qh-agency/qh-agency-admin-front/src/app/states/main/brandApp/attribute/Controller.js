/**
 * Created by susf on 17-3-29.
 */
import conf from "../../../../conf";
var $scope,
    alertService,
    $stateParams,
    loginService,
    authService,
    $http;


class Controller {

    constructor(_$scope, _loginService, _$stateParams, _alertService, _authService, _$http) {

        $scope = _$scope;
        loginService = _loginService;
        $http = _$http;
        $stateParams = _$stateParams;
        authService = _authService;
        alertService = _alertService;
        loginService.loginCtl(true);
        //权限相关
        $scope.ITEM_PROP_C = authService.hasAuthor("ITEM_PROP_C");    //增
        $scope.ITEM_PROP_U = authService.hasAuthor("ITEM_PROP_U");    //改
        $scope.ITEM_PROP_R = authService.hasAuthor("ITEM_PROP_R");    //读
        $scope.ITEM_PROP_D = authService.hasAuthor("ITEM_PROP_D");    //删


        $scope.search = {};
        $scope.data = {};
        $scope.id = '';
        //$scope.data.number = $scope.data.number?$scope.data.number:1;
        $scope.brandAppId = $stateParams.brandAppId;
        //$scope.data.pageSize = $scope.data.pageSize ? $scope.data.pageSize : 15;

        // 主界面展示
        $scope.curPage = 1;
        $scope.pageChanged = function (curPage) {
            $http({
                method:"GET",
                url:conf.apiPath + "/brandApp/"+$scope.brandAppId+"/itemProp",
                params: {
                    page: curPage ?curPage:$scope.curPage-1,
                    size: conf.pageSize,
                    name: $scope.search.name
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
            }).success(function (data) {
                $scope.data = data.data;
                $scope.curPage =data.data.number+1;
            });
        };
        // 初始化的时候就获取第一页的数据
        $scope.pageChanged();

        //删除
        $scope.alert = function (id, itemProp) {
            // alertService.confirm("是否确定删除该条数据", $scope, "!!!!!!!!!!");
            // $scope.remove = function (id,itemProp) {2
            $http({
                method: "DELETE",
                url: conf.apiPath + "/brandApp/"+$scope.brandAppId+"/itemProp/"+id,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
            }).then(function successCallback(response) {
                if (response.status == 200) {
                    $scope.data.content.splice($scope.data.content.indexOf(itemProp), 1);
                }
            }, function errorCallback(response) {
                // 请求失败执行代码
            });
        };

        $scope.delate = function (id, itemProp) {
            alertService.confirm(null, "确定删除该属性？", "温馨提示", "取消", "确认")
                .then(function (data) {
                    if (data) {
                        $scope.alert(id, itemProp);
                    }
                });
        };
    }
}


Controller.$inject = [
    '$scope',
    'loginService',
    '$stateParams',
    'alertService',
    'authService',
    '$http',
];

export default Controller ;
