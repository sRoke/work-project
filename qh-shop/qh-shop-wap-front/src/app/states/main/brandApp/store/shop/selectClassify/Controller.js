import conf from "../../../../../../conf";

var $scope,
    $mdDialog,
    loginService,
    $state,
    alertService,
    $stateParams,
    $location,
    $http
class Controller {
    constructor(_$scope,
                _$mdDialog,
                _loginService,
                _$state,
                _alertService,
                _$stateParams,
                _$location,
                _$http) {
        $scope = _$scope;
        $mdDialog = _$mdDialog;
        loginService = _loginService;
        $state = _$state;
        alertService = _alertService;
        $stateParams = _$stateParams;
        $location = _$location;
        $http = _$http;
        $scope.brandAppId = $stateParams.brandAppId;
        $scope.storeId = $stateParams.storeId;

        $scope.choose = $stateParams.choose;
        $scope.from = $stateParams.from;
        $scope.editStatus = $stateParams.editStatus;
        loginService.loginCtl(true, $location.absUrl());

        $scope.id = $stateParams.id;
        if ($stateParams.skuData) {
            $scope.skuData = JSON.parse($stateParams.skuData);
        }
        else {
            $scope.skuData = {};
            $scope.skuData.category = {
                id: [],
                name: []
            };
        }
        // console.log('  $scope.jsons', $scope.skuData);

        loginService.loginCtl(true, $location.absUrl());
        $scope.go = function (state) {
            $state.go(state);
        };
        $scope.getInfo = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/category",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                // console.log(1111111111111, resp.data.data);
                if (resp.data.data.length > 0) {
                    $scope.noGood = false;
                    $scope.lists = resp.data.data;
                    $scope.subData = [];//定义数组接收删除的子级
                    for (var i = 0; i < $scope.lists.length; i++) {
                        //$scope.lists[i].moveLeft=false;
                        $scope.lists[i].checked = false;
                        if ($scope.lists[i].parentId) {
                            $scope.subData.push($scope.lists[i]);  //将子级保存在数组
                            $scope.lists.splice(i, 1);     //删除子级
                            i--;   //i值减1是避免下一位数据没有遍历到;
                        }
                    }
                    // console.log('$scope.lists', $scope.lists)
                    for (var i = 0; i < $scope.lists.length; i++) {
                        $scope.lists[i].subarr = [];      //定义接收子级的数组
                        for (var j = 0; j < $scope.subData.length; j++) {
                            if ($scope.lists[i].id == $scope.subData[j].parentId) {
                                $scope.lists[i].subarr.push($scope.subData[j]);
                            }
                        }
                    }
                    $scope.items = $scope.lists;
                    // console.log(' $scope.items', $scope.items);


                    if ($scope.skuData.category) {

                        if (!$scope.skuData.category.id) {
                            $scope.skuData.category = {
                                id: [],
                                name: []
                            };
                            return;
                        }
                        else {
                            for (let i = 0; i < $scope.skuData.category.id.length; i++) {
                                for (let j = 0; j < $scope.items.length; j++) {
                                    if ($scope.skuData.category.id[i] == $scope.items[j].id) {
                                        $scope.items[j].checked = true;

                                        // for (let k = 0; k < $scope.items[j].subarr.length; k++) {
                                        //     console.log('$scope.items[j].subarr', $scope.items[j].subarr[k].id);
                                        //     console.log('$scope.skuData.category.id[i]', $scope.skuData.category.id[i]);
                                        //     if ($scope.skuData.category.id[i] == $scope.items[j].subarr[k].id) {
                                        //         $scope.items[j].subarr[k].checked = true;
                                        //     }
                                        // }

                                    }

                                    if ($scope.items[j].subarr.length != 0) {
                                        for (let k = 0; k < $scope.items[j].subarr.length; k++) {
                                            // console.log('$scope.items[j].subarr', $scope.items[j].subarr[k].id);
                                            // console.log('$scope.skuData.category.id[i]', $scope.skuData.category.id[i]);
                                            if ($scope.skuData.category.id[i] == $scope.items[j].subarr[k].id) {
                                                $scope.items[j].subarr[k].checked = true;
                                            }
                                        }
                                    }

                                }
                            }
                        }

                    } else {
                        $scope.skuData.category = {
                            id: [],
                            name: []
                        };
                    }


                } else {
                    $scope.noGood = true;
                }
            }, function (resp) {
                //error
            });
        };
        $scope.getInfo();
        //选择
        //定义数组接收选中分类id    classfiyId

        $scope.classfiyId = [];
        $scope.selectCheck = function (info) {
            info.checked = !info.checked;
            if (info.checked) {
                $scope.skuData.category.id.push(info.id);
                $scope.skuData.category.name.push(info.name);
            } else {

                if ($scope.skuData.categorys) {
                    for (let i = 0; i < $scope.skuData.categorys.length; i++) {
                        if (info.id == $scope.skuData.categorys[i]) {
                            $scope.skuData.categorys.splice(i, 1);
                            break;
                        }
                    }
                }


                for (let i = 0; i < $scope.skuData.category.id.length; i++) {
                    if (info.id == $scope.skuData.category.id[i]) {
                        $scope.skuData.category.id.splice(i, 1);
                        break;
                    }
                }
                for (let i = 0; i < $scope.skuData.category.name.length; i++) {

                    // console.log('info.name', info.name);
                    // console.log(' == $scope.skuData.category.name[i]', $scope.skuData.category.name[i]);

                    if (info.name == $scope.skuData.category.name[i]) {
                        $scope.skuData.category.name.splice(i, 1);
                        break;
                    }
                }
            }
            // console.log(' $scope.skuData.categorys', $scope.skuData.categorys);
            // console.log(' $scope.skuData', $scope.skuData);

        };

        $scope.finish = function () {
            // main.brandApp.store.itemManage.itemAdd({status:'add'})

            var json = angular.toJson($scope.skuData);

            if ($scope.from == 'itemAdd') {
                $state.go("main.brandApp.store.itemManage.itemAdd", {
                    id: $scope.id, skuData: json, status: 'add',
                    selectMoreSpec: $stateParams.selectMoreSpec
                })
            } else {
                $state.go("main.brandApp.store.itemManage.itemEdit", {
                    id: $scope.id,
                    skuData: json,
                    status: 'add',
                    editStatus: $scope.editStatus,
                    selectMoreSpec: $stateParams.selectMoreSpec
                })
            }
        };
        //外侧检测到$scope.items
        // // //左右滑动
        // $scope.onSwipeLeft = function (item) {
        //     for (var i = 0; i < $scope.items.length; i++) {
        //         $scope.items[i].moveLeft = false;                    //父级
        //         for (var j = 0; j < $scope.items[i].subarr.length; j++) {
        //             $scope.items[i].subarr[j].moveLeft = false;     //子级
        //         }
        //     }
        //     item.moveLeft = true;
        //     console.log(111);
        //     console.log(' $scope.itemswwwwwwwwwwwww', $scope.items)
        // };
        // $scope.onSwipeRight = function (item) {
        //     for (var i = 0; i < $scope.items.length; i++) {
        //         $scope.items[i].moveLeft = false;
        //         for (var j = 0; j < $scope.items[i].subarr.length; j++) {
        //             $scope.items[i].subarr[j].moveLeft = false;
        //         }
        //     }
        //     console.log(222);
        // };


        //新增
        $scope.add = function (id) {
            // console.log('id', id)//   id即为父级id
            $mdDialog.show({
                templateUrl: 'addGood.html',
                //parent: angular.element(document.body).find('#qh-shop-wap-front'),
                clickOutsideToClose: false,   //true 点击界外的时候弹窗消失
                fullscreen: false,
                // locals: {key: $scope.info},
                controller: ['$mdDialog', function ($mdDialog) {
                    var vmd = this;
                    vmd.codeShow = false;
                    vmd.noName = false;
                    vmd.title = '新增分类';
                    //验证名字
                    vmd.check = function (name) {
                        if (name) {
                            vmd.noName = false;
                        } else {
                            vmd.noName = true;
                        }
                    };
                    //取消
                    vmd.cance = function () {
                        $mdDialog.cancel();
                    };
                    vmd.confirm = function () {
                        if (!vmd.name) {
                            vmd.noName = true;
                            return;
                        }
                        $http({
                            method: "POST",
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/category",
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            },
                            data: {
                                enable: true,
                                name: vmd.name,
                                order: vmd.number,
                                parentId: id,
                            },
                        }).then(function (resp) {
                            if (resp.data.status == '200') {
                                $mdDialog.cancel();
                                $scope.getInfo();
                            }
                        }, function (resp) {
                            //error
                        });
                    }
                }],
                controllerAs: "vmd"
            }).then(function (answer) {
            }, function () {
                //error
            });

        };
        //编辑
        $scope.edit = function (id, parentId) {
            $mdDialog.show({
                templateUrl: 'addGood.html',
                //parent: angular.element(document.body).find('#qh-shop-wap-front'),
                clickOutsideToClose: false,   //true 点击界外的时候弹窗消失
                fullscreen: false,
                // locals: {key: $scope.info},
                controller: ['$mdDialog', function ($mdDialog) {
                    var vmd = this;
                    vmd.codeShow = false;
                    vmd.codeShow = false;
                    vmd.noName = false;

                    vmd.title = '编辑分类';
                    //验证名字
                    vmd.check = function (name) {
                        if (name) {
                            vmd.noName = false;
                        } else {
                            vmd.noName = true;
                        }
                    };
                    //先获取当前信息
                    $http({
                        method: "GET",
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/category/" + id,
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        }
                    }).then(function (resp) {
                        vmd.name = resp.data.data.name;
                        vmd.number = resp.data.data.order;
                        //获取名字   序号

                    }, function (resp) {
                        //error
                    });
                    //取消
                    vmd.cance = function () {
                        $mdDialog.cancel();
                    };
                    vmd.confirm = function () {
                        if (!vmd.name) {
                            vmd.noName = true;
                            return;
                        }
                        $http({
                            method: "PUT",
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/category/" + id,
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            },
                            data: {
                                enable: true,
                                name: vmd.name,
                                order: vmd.number,
                                parentId: parentId,
                            },
                        }).then(function (resp) {
                            // console.log(4422221111112222, resp.data.data);
                            if (resp.data.status == '200') {
                                $mdDialog.cancel();
                                $scope.getInfo();
                            }
                        }, function (resp) {
                            //error
                        });
                    }
                }],
                controllerAs: "vmd"
            }).then(function (answer) {

            }, function () {
                //error
            });
        };
        //删除
        $scope.delte = function (id) {
            alertService.confirm(null, "", "删除分类？", "取消", "确定").then(function (data) {
                if (data) {
                    $http({
                        method: "DELETE",
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/category/" + id,
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        },
                    }).then(function (resp) {
                        if (resp.data.status == '200') {
                            $scope.getInfo();
                        } else {
                            alertService.msgAlert("exclamation-circle", resp.data.data);
                            $scope.getInfo();
                        }
                        ;
                    }, function (resp) {

                    });
                }
            });

        };

        /*返回上级*/
        $scope.fallbackPage = function () {

            var json = angular.toJson($scope.skuData);

            if ($scope.from == 'itemAdd') {
                $state.go("main.brandApp.store.itemManage.itemAdd", {
                    id: $scope.id,
                    skuData: json,
                    status: 'add',
                    editStatus: $scope.editStatus,
                    selectMoreSpec: $stateParams.selectMoreSpec
                })
            } else if ($scope.from == 'itemEdit') {
                $state.go("main.brandApp.store.itemManage.itemEdit", {
                    id: $scope.id,
                    skuData: json,
                    status: 'add',
                    editStatus: $scope.editStatus,
                    selectMoreSpec: $stateParams.selectMoreSpec

                })
            }
            else {
                history.back();
            }
        };
    }
}

Controller.$inject = [
    '$scope',
    '$mdDialog',
    'loginService',
    '$state',
    'alertService',
    '$stateParams',
    '$location',
    '$http',
];

export default Controller ;
