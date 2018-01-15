import conf from "../../../../../conf"

var $scope,
    $http,
    authService,
    $state,
    $log,
    $filter,
    loginService,
    alertService,
    $mdDialog,
    $stateParams,
    Upload,
    errorService;
class Controller {
    constructor(_$scope,
                _$http,
                _$state,
                _$log,
                _$filter,
                _authService,
                _loginService,
                _alertService,
                _$mdDialog,
                _$stateParams,
                _Upload,
                _errorService) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        $filter = _$filter;
        authService = _authService;
        loginService = _loginService;
        $log = _$log;
        Upload = _Upload;
        $stateParams = _$stateParams;
        alertService = _alertService;
        errorService = _errorService;
        /////////////////////////////////
        loginService.loginCtl(true);
        $scope.brandAppId = $stateParams.brandAppId;
        //获取权限，勿删
        // authService.setAuthorities($scope.brandAppId);
        //console.log("====================", $stateParams);
        $scope.data = {};
        if($stateParams.type == "QH_PLATFORM"){
            console.log('平台支撑系统');
            $scope.urlPath = conf.apiPath + '/staffGroup';
        }else if($stateParams.type == "QH_MALL"){
            console.log('微商城')
        }else if($stateParams.type == "QH_AGENCY"){
            console.log('经销系统');
            $scope.urlPath = conf.agencyPath +'/brandApp/'+$scope.brandAppId+'/staffGroup';
        }
        $scope.pageChanged = function () {
            console.log('url========',$scope.urlPath + "/staffGroup/load");

                $http({
                    method: "get",
                    url: $scope.urlPath + "/load",
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId.constructor,
                    },
                    params: {}
                }).success(function (resp) {
                    $scope.PermissionsList = resp.data.authorMap;
                    console.log( $scope.PermissionsList);

                });
        };
        $scope.pageChanged();


        if($stateParams.appUserId){
            $http({
                method: "get",
                url: $scope.urlPath + "/" + $stateParams.appUserId,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId.constructor,
                },
                params: {}
            }).success(function (resp) {
                $scope.data = resp.data;
                $scope.checkedList = resp.data.author;
            });
        }




        $scope.checkedList = [];
        $scope.toggle = function (item, list) {
            var idx = list.indexOf(item);
            if (idx > -1) {
                list.splice(idx, 1);
            }
            else {
                list.push(item);
            }
            console.log('$scope.checkedList',$scope.checkedList);
        };

        $scope.exists = function (item, list) {
            return list.indexOf(item) > -1;
        };

        $scope.toggleTotal = function (item, list) {
            if($scope.existsTotal(item, list)){
                for (var key in item){
                    var idx = list.indexOf(key);
                    if (idx > -1) {
                        list.splice(idx, 1);
                    }
                }
            }else {
                for (var key in item){
                    var idx = list.indexOf(key);
                    if (idx == -1) {
                        list.push(key);
                    }
                }
            }
        };

        $scope.existsTotal = function (item, list) {
            for (var key in item){
                if(list.indexOf(key) == -1){
                    return false
                }
            }
            return true;
        };
        $scope.save = function () {
            console.log('$scope.checkedList',$scope.checkedList);
            if(!$scope.data.name){
              return  errorService.error('角色名称不能为空!')
            }
            if(!$scope.data.desp){
                return  errorService.error('角色描述不能为空!')
            }
            if($scope.checkedList.length<=0){
                return  errorService.error('权限列表不能为空!');
                // return  alertService.msgAlert('ks-close','应用名称不能为空!')
            }
            $http({
                method: $stateParams.appUserId ? 'PUT' :'POST',
                url: $stateParams.appUserId ? $scope.urlPath +'/'+ $stateParams.appUserId : $scope.urlPath,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId.constructor,
                },
                data: {
                    name:$scope.data.name,
                    desp:$scope.data.desp,
                    authorMap:$scope.checkedList,
                    reserved:true,
                }
            }).then(function (resp) {
                if(resp.status == 200){
                    $scope.fallbackPage();
                }
            },function (err) {
                errorService.error(err.data.message)
            });
        };

        //返回按钮
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.index", null, {reload: true});
            } else {
                history.back();
            }
        };
        //yun图片上传
        // $scope.uploading = function (file) {
        //     $scope.f = file;
        //     // $scope.errFile =errFiles && errFiles[0];
        //     if (file) {
        //         Upload.upload({
        //             url: conf.yunApiPath + '/app/5988791a6b869f4e18d5c8d5/org/598878fc6b869f4e0f19fb47/yunFile',
        //             data: {
        //                 file: file,
        //             }
        //         }).then(function (resp) {
        //             // console.log('Success ' + resp.config.data.file.name + 'uploaded. Response: ', resp.data);
        //             $http({
        //                 method: 'GET',
        //                 url: conf.yunApiPath + '/app/5988791a6b869f4e18d5c8d5/org/598878fc6b869f4e0f19fb47/yunFile/' + resp.data.data
        //             }).then(function (resp) {
        //                 //console.log('Success ' + resp.data.data.cdnUrls[0].url);
        //                 // 上传代码返回结果之后，将图片插入到编辑器中
        //                 $scope.data.logo = resp.data.data.cdnUrls[0].url;
        //             }, function (resp) {
        //                 console.log('Error status: ' + resp.status);
        //             });
        //         }, function (resp) {
        //             //console.log('Error status: ' + resp.status);
        //         }, function (evt) {
        //             // var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
        //             // console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
        //         });
        //     }
        // };
        //
        // $scope.cancelDelImg = function () {
        //     $scope.data.logo = '';
        // }

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
    'alertService',
    '$mdDialog',
    '$stateParams',
    'Upload',
    'errorService'
];

export default Controller ;