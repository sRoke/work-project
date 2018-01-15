import conf from "../../../../conf";

var $scope,
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

        $scope.staff.id = $stateParams.id;
        $scope.staff.source = $stateParams.source;
        $scope.source = $stateParams.source;
        //初始化编辑
        $http({
            method:"get",
            url:conf.apiPath + "/brandApp/"+$scope.brandAppId+"/partnerStaff/"+$scope.staff.id,
            headers: {
                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                "brandApp-Id": $scope.brandAppId
            },
        }).success(function (data) {
            console.log(data);
            $scope.staff = data.data;



            $http({
                method: 'GET',
                url:conf.oauthPath + '/api/user/'+$scope.staff.userId,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $scope.staff.realName = resp.data.data.realName;
                $scope.staff.phone = resp.data.data.phone;
                console.log('$scope.staff',$scope.staff);
            })













        });



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
            // if (!$scope.staff.tags) {
            //     return errorService.error("类型不能为空！", null)
            // }
            // if (!$scope.staff.contacts) {
            //     return errorService.error("联系人不能为空！", null)
            // }
            // if ($scope.staff.contacts.length < 2) {
            //     return errorService.error("联系人长度不能少于2位！", null)
            // }
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

                    console.log($scope.staff);



                    $http({
                        method: 'PUT',
                        url: conf.oauthPath + "/api/user",
                        data: {
                            phone: $scope.staff.phone,
                            realName: $scope.staff.realName,
                            userId :$scope.staff.userId
                        },
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        }
                    }).then(function (resp) {

                        console.log('resp',resp);
                        $http({
                            method:"PUT",
                            url:conf.apiPath + "/brandApp/"+$scope.brandAppId+"/partnerStaff/"+$scope.staff.id,
                            data:{
                                disabled:$scope.staff.disabled,
                                userId:$scope.staff.userId,
                            },
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            },
                        }).success(function (data) {
                            $scope.fallbackPage();
                        })

                    },function (error) {
                        errorService.error(error.data.message, null)
                    });
















                    //
                    // $http.get(conf.apiPath + "/brandApp/{brandAppId}/partnerStaff/"+, {
                    //     params: {
                    //         phone: $scope.staff.phone,
                    //         id: $scope.staff.id
                    //     },
                    //     headers: {
                    //         'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    //         "brandApp-Id": $scope.brandAppId
                    //     },
                    // }).success(function (data) {
                    //
                    //     $scope.isRepeat = data.data;
                    //     if ($scope.isRepeat) {
                    //         return errorService.error("手机号重复！", null)
                    //     } else {
                    //         $http({
                    //             method: "POST",
                    //             url: conf.apiPath + "/member/save",
                    //             headers: {
                    //                 'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    //                 "brandApp-Id": $scope.brandAppId
                    //             },
                    //             data: {
                    //                 id: $scope.staff.id,
                    //                 realName: $scope.staff.realName,
                    //                 phone: $scope.staff.phone,
                    //                 tags: $scope.staff.tags,
                    //                 contacts: $scope.staff.contacts,
                    //                 disabled: $scope.staff.disabled
                    //             },
                    //         }).then(function (data) {
                    //             $state.go("main.brandApp.member", {reload: true});
                    //         })
                    //     }
                    // });
                }
            }


        };
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
