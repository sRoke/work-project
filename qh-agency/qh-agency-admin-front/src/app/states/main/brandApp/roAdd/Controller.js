import conf from "../../../../conf";


var $scope,
    $stateParams,
    $state,
    errorService,
    loginService,
    $http;
class Controller {
    constructor(_$scope, _$http, _loginService, _$stateParams, _errorService, _$state) {
        $scope = _$scope;
        $http = _$http;
        loginService = _loginService;
        $state = _$state;
        errorService = _errorService;
        $stateParams = _$stateParams;


        loginService.loginCtl(true);
        $scope.brandAppId = $stateParams.brandAppId;
        $scope.authMap = {};
        $scope.staffGroup = {};
        $scope.staff = {};
        $scope.currentUserAuthor = [];
        $scope.currentStaffGroup = [];
        $scope.currentStaffGroup.staffs = [];
        // $scope.currentStaffGroup.author = [];
        // $scope.staff.disable = true;
        $scope.staffGroup.reserved = $scope.staffGroup.reserved ? $scope.staffGroup.reserved : true;
        $scope.toggle = function (name) {
            name.collapsed = !name.collapsed;
        };
        $scope.save = function () {
            // console.log($scope.authMap);
            if (!$scope.currentStaffGroup.name) {
                return errorService.error("名称不能为空！", null)
            }
            if ($scope.currentStaffGroup.name.length < 2) {
                return errorService.error("名称长度不能小于2！", null)
            }
            if (!$scope.currentStaffGroup.status) {
                return errorService.error("状态不能为空！", null)
            }

            $http({
                method: "POST",
                url: conf.apiPath + "/staffGroup/save",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
                data: {
                    id: $scope.id,
                    name: $scope.currentStaffGroup.name,
                    status: $scope.currentStaffGroup.status,
                    staffs: $scope.currentStaffGroup.staffs,
                    // reserved: $scope.currentStaffGroup.reserved,
                    authorMap: $scope.authMap,
                    desp: $scope.currentStaffGroup.desp
                },
                // data: {
                //
                // }


            }).then(function (data) {
                $state.go("main.brandApp.role", {reload: true});
            })
        };
        /**
         * load 方法：
         *      新建时只需要传入当前用户id，根据当前用户id获取系、当前用户的权限列表
         *      编辑时需要传入当前用户id，所编辑用户的id，以及来源，根据来源判断是新建还是编辑
         *      编辑时调用load方法，返回当前操作用户的权限列表，调用info方法返回当前编辑角色的基本信息，以及权限列表信息
         */
        $scope.source = $stateParams.source;
        $scope.id = $stateParams.id;
        // $scope.nowView = '';
        console.log($scope.source);
        $scope.load = function () {

            if ($scope.source == 'new') {
                console.log(1);
                $http.get(conf.apiPath + "/staffGroup/load", {
                    params: {
                        source: $scope.source
                    },
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    },
                }).success(function (data) {
                    $scope.staffGroup = data.data;
                });

                $http.get(conf.apiPath + "/staffGroup/currentUserInfo", {
                    params: {
                        id: $scope.id,
                        source: $scope.source

                    },
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    },
                }).success(function (data) {
                    $scope.currentUserAuthor = data.data.currentAuthor;
                });

            } else if ($scope.source == 'edit') {
                console.log(2);
                $http.get(conf.apiPath + "/staffGroup/load", {
                    params: {
                        source: $scope.source
                    },
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    },
                }).success(function (data) {
                    $scope.staffGroup = data.data;
                });

                $http.get(conf.apiPath + "/staffGroup/currentUserInfo", {
                    params: {
                        id: $scope.id,
                        source: $scope.source

                    },
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    },
                }).success(function (data) {
                    $scope.currentUserAuthor = data.data.currentAuthor;
                });

                $http.get(conf.apiPath + "/staffGroup/info", {
                    params: {
                        id: $scope.id,
                        source: $scope.source

                    },
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    },
                }).then(function (data) {

                    $scope.currentStaffGroup = data.data.data;
                    console.log($scope.currentStaffGroup.status)
                    $scope.currentStaffGroup.author.forEach((element) => {
                        $scope.authMap[element] = true;
                    })
                }, function (data) {
                    console.log(data)
                });

            } else {
                console.log(3);

                $http.get(conf.apiPath + "/staffGroup/load", {
                    params: {
                        source: $scope.source
                    },
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    },
                }).success(function (data) {
                    $scope.staffGroup = data.data;
                });

                $http.get(conf.apiPath + "/staffGroup/info", {
                    params: {
                        id: $scope.id,
                        source: $scope.source

                    },
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    },
                }).success(function (data) {
                    $scope.currentStaffGroup = data.data;
                    console.log($scope.currentStaffGroup);
                    $scope.currentStaffGroup.author.forEach((element) => {
                        $scope.authMap[element] = true;
                    })
                });
            }


        };

        $scope.load();

        $scope.searchStaff = function () {
            $http.get(conf.apiPath + "/search/staffList", {
                params: {
                    staffKeyword: $scope.staff.staffKeyword,
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
            }).success(function (data) {
                $scope.staffResult = data.data.recList;
                // console.log(  $scope.staffResult );
            });

        };

        /**
         * 选择员工
         * @param staff
         */
        $scope.selectStaff = function (staff) {
            $scope.staff.staffKeyword = staff.realName;
            $scope.staff.staffId = staff.id;
        };

        /**
         * 移除搜索框中的文本
         */
        $scope.removeStaff = function () {
            $scope.staff.staffKeyword = "";
            $scope.staff.staffId = null;
        };

        /**
         * 移除添加的员工
         * @param index
         */
        $scope.delStaff = function (index) {
            $scope.currentStaffGroup.staffs.splice(index, 1);
        };

        /**
         * 添加员工到员工组
         */
        $scope.addStaff = function () {
            //没有选择员工
            if (!$scope.staff.staffId) {
                return;
            }
            var isSame = false;
            //循环已经添加的员工
            // console.log($scope.currentStaffGroup.staffs)
            $scope.currentStaffGroup.staffs = $scope.currentStaffGroup.staffs ? $scope.currentStaffGroup.staffs : []
            for (var i = 0; i < $scope.currentStaffGroup.staffs.length; i++) {
                var user = $scope.currentStaffGroup.staffs[i];
                if (user.id === $scope.staff.staffId) {
                    isSame = true;
                    break;
                }
            }
            //没有相同的
            if (!isSame) {
                $scope.currentStaffGroup.staffs.push({id: $scope.staff.staffId, realName: $scope.staff.staffKeyword});
            } else {
                window.alert("添加的员工已经存在");
            }
            $scope.removeStaff()
        };
        $scope.changeStatus = function (key) {
            var currentUserAuthor = $scope.currentUserAuthor;
            // console.log(currentUserAuthor)
            return !currentUserAuthor.includes(key)
        };
        // $scope.changeCheck = function (key) {
        //     var currentAuthor = $scope.currentStaffGroup.author;
        //     // console.log(currentUserAuthor)
        //     return currentAuthor.includes(key)
        // }

        $scope.delete = function () {
            $http.get(conf.apiPath + "/staffGroup/delete", {
                params: {
                    id: $scope.id
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
            }).success(function (data) {
                $scope.staffGroup = data.data;
            });
        }


        //返回按钮
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
    '$scope', '$http', 'loginService', '$stateParams', 'errorService', '$state'
];

export default Controller ;
