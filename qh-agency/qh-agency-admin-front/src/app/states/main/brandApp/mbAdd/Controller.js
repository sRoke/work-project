import conf from "../../../../conf";

let $scope,
    $stateParams,
    loginService,
    $state,
    errorService,
    $http;


class Controller {
    constructor(_$scope, _$stateParams, _loginService, _$state, _errorService, _$http) {
        $scope = _$scope;
        $http = _$http;
        loginService=_loginService;
        $state = _$state;
        errorService = _errorService;
        $stateParams = _$stateParams;
        loginService.loginCtl(true);
        $scope.brandAppId = $stateParams.brandAppId;
        $scope.staff = {};
        // $scope.staff.disable = true;
        // $scope.staff.disabled = $scope.staff.disabled ? $scope.staff.disabled : true;
        $scope.save = function () {

            if (!$scope.staff.realName) {
                return errorService.error("名称不能为空！", null)
            }
            if ($scope.staff.realName.length < 2) {
                return errorService.error("名称不能少于2位！", null)
            }
            if (!$scope.staff.phone) {
                return errorService.error("手机号不能为空！", null)
            }
            if (!$scope.staff.tags) {
                return errorService.error("类型不能为空！", null)
            }
            if (!$scope.staff.contacts) {
                return errorService.error("联系人不能为空！", null)
            }
            if ($scope.staff.contacts.length < 2) {
                return errorService.error("联系人长度不能少于2位！", null)
            }
            if (!$scope.staff.disabled) {
                return errorService.error("状态必须选择一项", null)
            }
            if ($scope.staff.phone) {
                if ($scope.staff.phone.length != 11) {
                    return errorService.error("请输入有效的手机号码！", null)
                }
                var phoneReg = /^1[34578]\d{9}$/;
                if (!phoneReg.test($scope.staff.phone)) {
                    return errorService.error("请输入有效的手机号码！", null)
                } else {
                    $http.get(conf.apiPath + "/member/queryPhone", {
                        params: {
                            phone: $scope.staff.phone,
                            id: $scope.staff.id
                        },
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        },
                    }).success(function (data) {

                        $scope.isRepeat = data.data;
                        if ($scope.isRepeat) {
                            return errorService.error("手机号重复！", null)
                        } else {
                            $http({
                                method: "POST",
                                url: conf.apiPath + "/member/save",
                                headers: {
                                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                    "brandApp-Id": $scope.brandAppId
                                },
                                data: {
                                    id: $scope.staff.id,
                                    realName: $scope.staff.realName,
                                    phone: $scope.staff.phone,
                                    tags: $scope.staff.tags,
                                    contacts: $scope.staff.contacts,
                                    disabled: $scope.staff.disabled
                                },
                            }).then(function (data) {
                                $state.go("main.brandApp.member", {reload: true});
                            })
                        }
                    });
                }
            }


        };
        $scope.staff.id = $stateParams.id;
        //$scope.staff.source = $stateParams.source;
        // $scope.source = $stateParams.source;
        // $scope.load = function () {
        //     if ($scope.staff.source == 'new') {
        //         $scope.name = "新增";
        //         $http.get(conf.apiPath + "/member/type", {
        //             params: {},
        //             headers: {
        //                 'Authorization': 'Bearer ' + loginService.getAccessToken(),
        //                 "brandApp-Id": $scope.brandAppId
        //             },
        //         }).success(function (data) {
        //             $scope.staff.tags = data.data;
        //         });
        //     } else if ($scope.staff.source == 'edit') {
        //         $scope.name = "编辑";
        //
        //         $http.get(conf.apiPath + "/member/info", {
        //             params: {
        //                 id: $scope.staff.id,
        //             },
        //             headers: {
        //                 'Authorization': 'Bearer ' + loginService.getAccessToken(),
        //                 "brandApp-Id": $scope.brandAppId
        //             },
        //         }).success(function (data) {
        //             $scope.staff = data.data;
        //         });
        //         // $http.get(conf.apiPath + "/member/type", {
        //         //     params: {}
        //         // }).success(function (data) {
        //         //     $scope.staff.tags = data.data;
        //         // });
        //     } else {
        //         $http.get(conf.apiPath + "/partnerStaff/info", {
        //             params: {
        //                 id: $scope.staff.id,
        //             },
        //             headers: {
        //                 'Authorization': 'Bearer ' + loginService.getAccessToken(),
        //                 "brandApp-Id": $scope.brandAppId
        //             },
        //         }).success(function (data) {
        //             $scope.staff = data.data;
        //         });
        //         $scope.name = "查看";
        //     }
        //
        // };
        // $scope.load();


        //会员管理的查看接口
        $http({
            method:"GET",
            url:conf.apiPath + "/brandApp/"+$scope.brandAppId+"/partnerStaff/"+$scope.staff.id,
            headers: {
                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                "brandApp-Id": $scope.brandAppId
            },
        }).success(function (data) {
            $scope.staff = data.data;

            $http({
                method:"GET",
                url:conf.oauthPath + "/api/user/"+$scope.staff.userId,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                },
            }).then(function (data) {
                $scope.staff.realName = data.data.data.realName;
                $scope.staff.phone = data.data.data.phone;
            });

        });




        /*返回上级*/
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.shelves", null, {reload: true});
            } else {
                history.back();
            }
        };

    }
}


Controller.$inject = [
    '$scope',
    '$stateParams',
    'loginService',
    '$state',
    'errorService',
    '$http'
];
export default Controller ;
