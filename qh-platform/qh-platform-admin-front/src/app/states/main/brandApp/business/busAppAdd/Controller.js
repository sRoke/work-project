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
    Upload;
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
                _Upload) {
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
        /////////////////////////////////
        loginService.loginCtl(true);
        $scope.brandAppId = $stateParams.brandAppId;
        //获取权限，勿删
        // authService.setAuthorities($scope.brandAppId);
        $scope.app = {};

        $scope.checkedApp = [];
        $scope.toggle = function (item, list) {
            var idx = list.indexOf(item);
            if (idx > -1) {
                list.splice(idx, 1);
            }
            else {
                list[0] = item
            }
        };
        $scope.exists = function (item, list) {
            return list.indexOf(item) > -1;
        };





        $scope.today = new Date();








        $scope.getApp = function () {
            $http({
                method: "get",
                url: conf.apiPath + "/app",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
                params: {
                    page: 0,
                    size: 999,
                }
            }).success(function (resp) {
                $scope.data = resp.data;
                console.log('data',  $scope.data );
            });
        };
        $scope.getApp();


        $scope.save = function () {
            if($stateParams.id){
                $http({
                    method: "POST",
                    url: conf.apiPath+ "/brandApp/add",
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    },
                    data: {
                        appId:$scope.checkedApp[0],
                        date:$filter('date')($scope.app.date,'yyyy-MM-dd'),
                        shopName:$scope.app.shopName,
                        brandComId:$stateParams.id,
                    }
                }).success(function (resp) {
                    if(resp.status == 200){
                        $scope.fallbackPage();
                    }
                });
            }

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
    'Upload'
];

export default Controller ;