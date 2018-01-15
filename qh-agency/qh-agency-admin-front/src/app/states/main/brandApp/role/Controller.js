import conf from "../../../../conf";
var $scope,
    $http,
    loginService,
    $stateParams,
    alertService,
    authService,
    $state;


Controller.$inject = [
    '$scope',
    '$http',
    '$stateParams',
    'loginService',
    'alertService',
    'authService',
    '$state'];
function Controller(_$scope,
                    _$http,
                    _$stateParams,
                    _loginService,
                    _alertService,
                    _authService,
                    _$state) {
    $scope = _$scope;
    $http = _$http;
    loginService = _loginService;
    alertService = _alertService;
    authService = _authService;
    $state = _$state;
    $stateParams = _$stateParams;

    loginService.loginCtl(true);

    //权限相关
    $scope.STAFF_GROUP_C = authService.hasAuthor("STAFF_GROUP_C");    //增
    $scope.STAFF_GROUP_U = authService.hasAuthor("STAFF_GROUP_U");    //改
    $scope.STAFF_GROUP_R = authService.hasAuthor("STAFF_GROUP_R");    //读
    $scope.STAFF_GROUP_D = authService.hasAuthor("STAFF_GROUP_D");    //删

    $scope.brandAppId = $stateParams.brandAppId;
    $scope.data = {};
    $scope.data.number = $scope.data.number?$scope.data.number:1;
    $scope.staffGroup = {};
    $scope.pageChanged = function (curPage) {
        if ($scope.data.number >$scope.data.totalPages){
            return;
        };
        if (curPage == null) {
            curPage = $scope.data.number -1;
        }
        $http.get(conf.apiPath + "/staffGroup/page", {
            params: {
                curPage: curPage,
                pageSize: conf.pageSize,
                keyWord: $scope.staffGroup.keyWord,
                //startDate: $scope.staffGroup.startDate.toLocaleString(),
                //endDate: $scope.staffGroup.endDate.toLocaleString()
            },
            headers: {
                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                "brandApp-Id": $scope.brandAppId
            },
        }).success(function (data) {
            $scope.data = data.data.recPage;
            $scope.data.number = data.data.recPage.number + 1;
        });
    };

    $scope.initDateTime = function () {
        $scope.staffGroup.startDate = new Date().toLocaleString();
        $scope.staffGroup.endDate = new Date().toLocaleString();

        console.log($scope.staffGroup.endDate);
        this.isOpen = false;
    };


    $scope.initDateTime();

    // 初始化的时候就获取第一页的数据
    $scope.pageChanged(0);

    $scope.delete = function (id) {

        alertService.confirm(null, "确定删除该角色？", "温馨提示", "取消", "确认")
            .then(function (data) {
                if (data) {
                    $http.get(conf.apiPath + "/staffGroup/delete", {
                        params: {
                            id: id
                        },
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        },
                    }).success(function (data) {
                        $scope.staffGroup = data.data;
                        $scope.pageChanged(0);
                    });
                }
            });

    };

    $scope.alert = function (id, status) {
        $scope.id = id;
        var tmp;
        if (!status) {
            tmp = "启用"
        } else {
            tmp = "禁用"
        }
        alertService.confirm(null, "确定" + tmp + "该会员？", "温馨提示", "取消", "确认").then(function (data) {
            if (data == true) {
                $http.get(conf.apiPath + "/staffGroup/enable", {
                    params: {
                        id: $scope.id,
                        status: !status
                    },
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    },
                }).success(function (data) {

                    if (data.code === "SUCCESS") {
                        $scope.pageChanged();
                    }
                });
            }
        });
    };

}

export default Controller;
