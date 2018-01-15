import conf from "../../../../conf";
var $scope,
    $http,
    loginService,
    errorService,
    $state,
    $stateParams,
    $window;
class Controller {
    constructor(_$scope, _$http, _loginService, _errorService, _$state, _$window, _$stateParams) {
        $scope = _$scope;
        $http = _$http;
        loginService = _loginService;
        errorService = _errorService;
        $state = _$state;
        $window = _$window;
        $stateParams = _$stateParams;

        loginService.loginCtl(true);

        $scope.brandAppId = $stateParams.brandAppId;
        $scope.staff = {};
        $scope.staffGroups = [];
        $scope.staffGroupIds = [];

        $scope.checkPhone = function (num) {
           // console.log(num);
            if (!num) {
                return errorService.error("手机号不能为空！", null)
            } else if (!/^1[34578]\d{9}$/.test(num)) {
                return errorService.error("请输入有效的手机号码！", null)
            }
            ;
        };
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
                // console.log(1111111, resp);
                $scope.roles = resp.data.data.content;

                for (var i in $scope.roles) {
                    $scope.roles[i].selected = false;
                }

            }, function (error) {
                //errorService.error(error.data.message, null)
            });
        };
        $scope.getRole();
        $scope.staffGroupIds = [];
        $scope.slRole = '';
        $scope.selectRole = function (role) {
            role.selected = !role.selected;
            console.log('id', $scope.roles);

        };
       //保存
       $scope.saveRole=function(){
           $scope.staffGroupIds=[];
           //检测手机号
           console.log($scope.staff.phoneNum);
           if (!$scope.staff.phoneNum) {
               return errorService.error("手机号不能为空！", null)
           } else if (!/^1[34578]\d{9}$/.test($scope.staff.phoneNum)) {
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
               method: 'POST',
               url: conf.apiPath + "/brandApp/"+$scope.brandAppId+"/staff",
               data: {
                   disabled:false,
                   realName: $scope.staff.linkName,
                   phone:$scope.staff.phoneNum,
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
    '$scope', '$http', 'loginService', 'errorService', '$state', '$window', '$stateParams'
];

export default Controller ;
