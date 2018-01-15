import conf from "../../../../conf";
var $scope,
    $http,
    loginService,
    errorService,
    $state,
    $window,
    $stateParams;
class Controller {
    constructor(_$scope, _$http, _loginService,_errorService, _$state, _$window, _$stateParams) {
        $scope = _$scope;
        $http = _$http;
        loginService = _loginService;
        errorService=_errorService;
        $state = _$state;
        $window = _$window;
        $stateParams = _$stateParams;

        loginService.loginCtl(true);

        $scope.brandAppId = $stateParams.brandAppId;
        $scope.id = $stateParams.id;
        $scope.disabled = $stateParams.disabled;
        $scope.data = [];
        $scope.staffGroups = [];      //初始化时   该员工具备的角色
        $scope.staffGroupIds = [];    //id 组合
        $scope.roles=[];              //所有角色的集合
        //初始化
        $scope.start = function () {
            $http({
                method: 'get',
                url:conf.apiPath + '/brandApp/'+$scope.brandAppId+'/staff/'+$stateParams.id,
                params: {

                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                console.log('11111111111',resp);
                $scope.staff = resp.data.data;
                $scope.roleGroups=resp.data.data.staffGroupList;
                $scope.getRole();
            });

        };
        $scope.start();
        //查询角色
        $scope.getRole = function () {
            $http({
                method: 'GET',
                url: conf.apiPath + "/brandApp/"+$stateParams.brandAppId+"/staffGroup",
                data: {
                    size: 99,
                    page: 0,
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $scope.roles = resp.data.data.content;
                for (var i in $scope.roles) {
                    $scope.roles[i].selected = false;
                    for(var j in $scope.roleGroups){
                        if($scope.roles[i].id==$scope.roleGroups[j].id){
                            $scope.roles[i].selected = true;
                        }
                    }
                }
                console.log('111111', $scope.roles);

            }, function (error) {
                //errorService.error(error.data.message, null)
            });
        };



        $scope.staffGroupIds = [];
        $scope.selectRole = function (role) {
            role.selected = !role.selected;
            console.log('id', $scope.roles);

        };

        //验证手机号
        $scope.checkPhone = function (num) {
            // console.log(num);
            if (!num) {
                return errorService.error("手机号不能为空！", null)
            } else if (!/^1[34578]\d{9}$/.test(num)) {
                return errorService.error("请输入有效的手机号码！", null)
            }
            ;
        };


        $scope.saveRole=function(){
            $scope.staffGroupIds=[];
            //检测手机号

            if (!$scope.staff.phone) {
                return errorService.error("手机号不能为空！", null)
            } else if (!/^1[34578]\d{9}$/.test($scope.staff.phone)) {
                return errorService.error("请输入有效的手机号码！", null)
            }
            for (var i in $scope.roles) {
                if($scope.roles[i].selected){
                    $scope.staffGroupIds.push($scope.roles[i].id);
                }
            }
            //console.log('1222222id', $scope.staffGroupIds);
            if(!$scope.staffGroupIds[0]){
                return errorService.error("请给员工分配至少一个角色！", null);
            }
            $http({
                method: 'PUT',
                url: conf.apiPath + "/brandApp/"+$scope.brandAppId+"/staff/"+$scope.id,
                data: {
                    disabled:$scope.disabled,
                    userId:$scope.staff.userId,
                    realName: $scope.staff.realName,
                    phone:$scope.staff.phone,
                    staffGroupIds:$scope.staffGroupIds
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                console.log(1111111, resp);
                if(resp.data.status=='200'){
                    $state.go("main.brandApp.user", null, {reload: true});
                }
            }, function (error) {
                return errorService.error(error.data.message, null)
            });
        };





        // 返回按钮
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.index", null, {reload: true});
            } else {
                history.back();
            }
        };
    }
}

Controller.$inject = [
    '$scope', '$http', 'loginService','errorService', '$state', '$window', '$stateParams'
];

export default Controller ;
