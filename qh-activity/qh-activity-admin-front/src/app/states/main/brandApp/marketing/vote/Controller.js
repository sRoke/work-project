import conf from "../../../../../conf";

var $scope,
    $http,
    authService,
    $state,
    errorService,
    $filter,
    loginService,
    alertService,
    $mdDialog,
    $stateParams;
class Controller {
    constructor(_$scope,
                _$http,
                _$state,
                _errorService,
                _$filter,
                _authService,
                _loginService,
                _alertService,
                _$mdDialog,
                _$stateParams) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        $filter = _$filter;
        authService = _authService;
        loginService = _loginService;
        errorService = _errorService;
        $stateParams = _$stateParams;
        alertService = _alertService;
        /////////////////////////////////
        loginService.loginCtl(true);
        $scope.brandAppId = $stateParams.brandAppId;
        //获取权限，勿删
        // authService.setAuthorities($scope.brandAppId);
        //console.log("====================", $stateParams);


        $scope.curPage = 1;
        $scope.pageChanged = function (curPage) {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/voteActivity",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
                params: {
                    page: curPage ? curPage - 1 : $scope.curPage - 1,
                    size: conf.pageSize,
                    sort: ['dateCreated,desc'],
                    keyWord: $scope.keyWords,
                }
            }).success(function (resp) {
                $scope.data = resp.data;
            });
        };
        $scope.pageChanged();



        // https://kingsilk.net/activity/local/?_ddnsPort=17100#/brandApp/59782691f8fdbc1f9b2c4323/vote/59eabadf5908010008934746/votHome


        $scope.isShow = function (id) {
            for (var i = 0; i < $scope.data.content.length; i++) {
                if (id == $scope.data.content[i].id) {
                    $scope.linkAddress = conf.linkPath + $scope.brandAppId + "/vote/" + id + "/votHome";
                    $scope.data.content[i].clickThis = !$scope.data.content[i].clickThis;
                }
            }
        };
        $scope.isHide = function () {
            for (var i = 0; i < $scope.data.content.length; i++) {
                $scope.data.content[i].clickThis = false;
            }
        };
        $scope.copyUrl2 = function () {
            var Url2 = document.getElementById("addr");
            Url2.select(); // 选择对象
            document.execCommand("Copy"); // 执行浏览器复制命令
            return errorService.error("链接地址已复制好，可贴粘。", null);
        };
        $scope.copy = function () {
            var Url2 = document.getElementById("addr");
            Url2.select(); // 选择对象
        };

        $scope.del = function (id) {
            alertService.confirm(null, "确定删除该活动？", "温馨提示", "取消", "确认").then(function (data) {
                if (data) {
                    $http({
                        method: "DELETE",
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/voteActivity/" + id,
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        },
                        params: {}
                    }).then(function (resp) {

                        if (resp.status == 200) {
                            $scope.pageChanged();
                        }
                    }, function (resp) {

                    });
                }
            });
        };




        $scope.cancel = function (id) {
            alertService.confirm(null, "确定取消该活动？", "温馨提示", "取消", "确认").then(function (data) {
                if (data) {
                    // $http({
                    //     method: "DELETE",
                    //     url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/voteActivity/" + id,
                    //     headers: {
                    //         'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    //         "brandApp-Id": $scope.brandAppId
                    //     },
                    //     params: {}
                    // }).then(function (resp) {
                    //
                    //     if (resp.status == 200) {
                    //         $scope.pageChanged();
                    //     }
                    // }, function (resp) {
                    //
                    // });
                }
            });
        };

    }
}

Controller.$inject = [
    '$scope',
    '$http',
    '$state',
    'errorService',
    '$filter',
    'authService',
    'loginService',
    'alertService',
    '$mdDialog',
    '$stateParams'
];

export default Controller ;