import conf from "../../../../conf";
import moment from "moment";
import laydate from "layui-laydate";
var $scope,
    $http,
    authService,
    $state,
    $log,
    $filter,
    loginService,
    $mdDialog,
    $stateParams;
class Controller {
    constructor(_$scope,
                _$http,
                _$state,
                _$log,
                _$filter,
                _authService,
                _loginService,
                _$mdDialog,
                _$stateParams) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        $filter=_$filter;
        authService = _authService;
        loginService = _loginService;
        $log = _$log;
        $stateParams = _$stateParams;
        /////////////////////////////////
        loginService.loginCtl(true);
        $scope.brandAppId = $stateParams.brandAppId;
        //获取权限，勿删
        // authService.setAuthorities($scope.brandAppId);
        //console.log("====================", $stateParams);

        //权限相关
        $scope.PARTNERCHECK_U = authService.hasAuthor("PARTNERCHECK_U");    //审核

        // $scope.MEMBER_U = authService.hasAuthor("MEMBER_U");    //改
        // $scope.MEMBER_R = authService.hasAuthor("MEMBER_R");    //读
        // $scope.MEMBER_D = authService.hasAuthor("MEMBER_D");    //删
        // $scope.MEMBER_E = authService.hasAuthor("MEMBER_E");    //导出




        // js控制写在此处

//rmy 07-20 获取列表
        $scope.curPage=1;
        //时间选择器
        laydate.render({
            elem: '#test1', //指定元素
            done: function (value, date) {      //事件完成回调 将value赋值给自己定义的字符串
                $scope.startDate = value;
            }

        });
        laydate.render({
            elem: '#test2', //指定元素
            done: function (value, date) {      //事件完成回调 将value赋值给自己定义的字符串
                $scope.endDate = value;
            }
        });
        //前端进行请求
        $scope.pageChanged=function(curPage){
            $scope.startTime=$filter('date')($scope.startDate,'yyyy-MM-dd');
            $scope.endTime=$filter('date')($scope.endDate,'yyyy-MM-dd');
            $http({
                method:"GET",
                url:conf.apiPath + "/brandApp/"+$scope.brandAppId+"/partner",
                params: {
                    page: curPage ?curPage:$scope.curPage-1,
                    size:conf.pageSize,
                    status:$scope.applyStatus,
                    source:'APPLY',
                    applyType:$scope.appType,
                    startDate: $scope.startTime,
                    endDate: $scope.endTime,
                    keyWord:$scope.keyWords
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
            }).success(function (response) {
                // console.log(response);
                // console.log(response.data.content);
                $scope.items=response.data.content;
                $scope.page=response.data;
                $scope.curPage =response.data.number+1;
            });
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $stateParams.brandAppId + "/partner/partnerTypes  ",
                data: {},
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    'brandApp-Id': $stateParams.brandAppId,
                }
            }).then(function (response) {
                    console.log(response.data.data);
                    $scope.partnerTypes=response.data.data;
                }, function (error) {
                    console.log(error);
                }
            );
        }
        $scope.pageChanged();
    }
}

Controller.$inject = [
    '$scope',
    '$http',
    '$state',
    '$log',
    '$filter',
    'authService',
    'loginService',
    '$mdDialog',
    '$stateParams'
];

export default Controller ;
/**
 * Created by renmy on 17-7-11.
 */

