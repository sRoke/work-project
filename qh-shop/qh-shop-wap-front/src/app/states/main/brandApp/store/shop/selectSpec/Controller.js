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
        loginService.loginCtl(true, $location.absUrl());
        $scope.go = function (state) {
            $state.go(state);
        };

        $scope.choose = $stateParams.choose;
        $scope.from = $stateParams.from;

        $scope.id = $stateParams.id;
        if ($stateParams.skuData) {
            $scope.skuData = JSON.parse($stateParams.skuData);
            // console.log('  $scope.121', $scope.skuData);
        }
        else {
            $scope.skuData = {};
        }
        // /console.log('  $scope.skuData', $scope.skuData);

        $scope.getInfo = function () {
            $http({
                method: "GET",  ///brandApp/{brandAppId}/shop/{shopId}/itemProp/itemPropList
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/itemProp/itemPropList",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                if (resp.data.data == null) {
                    $scope.noGood = true;
                } else {
                    $scope.noGood = false;
                    $scope.items = resp.data.data;
                    // console.log(1111111111111, $scope.items);

                    for (let i = 0; i < $scope.skuData.specs.length; i++) {
                        for (let j = 0; j < $scope.skuData.specs[i].itemProp.length; j++) {
                            for (let z = 0; z < $scope.items.length; z++) {
                                for (let k = 0; k < $scope.items[z].propValues.length; k++) {
                                    if ($scope.skuData.specs[i].itemProp[j].id == $scope.items[z].propValues[k].id) {
                                        $scope.items[z].propValues[k].checked = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }, function (resp) {
                //error
            });
        };
        $scope.getInfo();

        $scope.getLead = function () {
            $http({
                method: "GET",  ///brandApp/{brandAppId}/shop/{shopId}/itemProp/itemPropList
                url: conf.apiPath + "/common/guidePage",
                params: {
                    type: 'ITEMPROP',

                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                // console.log('common/guidePage', resp);

                $scope.showLead = resp.data.data;

                if ($scope.showLead) {
                    $scope.lead = 1;
                }
            }, function (resp) {
                //error
            });
        };
        $scope.getLead();

        //控制引导页
        $scope.leading = function (num) {
            $scope.lead = num;
        };


        // // //左右滑动
        // $scope.onSwipeLeft = function (item) {
        //     for (var i = 0; i < $scope.items.length; i++) {
        //         $scope.items[i].moveLeft = false;                    //父级
        //         for (var j = 0; j < $scope.items[i].propValues.length; j++) {
        //             $scope.items[i].propValues[j].moveLeft = false;     //子级
        //         }
        //     }
        //     item.moveLeft = true;
        // };
        // $scope.onSwipeRight = function (item) {
        //     for (var i = 0; i < $scope.items.length; i++) {
        //         $scope.items[i].moveLeft = false;
        //         for (var j = 0; j < $scope.items[i].propValues.length; j++) {
        //             $scope.items[i].propValues[j].moveLeft = false;
        //         }
        //     }
        // };
        //


        if (!$scope.skuData.specs) {
            $scope.skuData.specs = [];
        }

        //

        $scope.selectCheck = function (info, item) {
            info.checked = !info.checked;
            // console.log('info', info);
            // console.log('item', item);
            var specs = $scope.skuData.specs;


            if (info.checked) {

                var flog = false;

                for (var i = 0; i < specs.length; i++) {

                    if (specs[i].id == item.id) {
                        specs[i].itemProp.push(info);
                        flog = true;
                        break;
                    } else {
                        // break;
                    }

                }

                if (!flog) {
                    specs.push({
                        id: item.id,                  //该属性id
                        realName: item.name,          //该属性名
                        itemProp: [info],                 //该规格选中的属性
                        // itemPropName: [],             //该规格选中的属性
                    });
                }
            }
            else {
                for (let i = 0; i < specs.length; i++) {

                    if (specs[i].id == item.id) {

                        if (!specs[i]) {
                            return
                        }
                        else {
                            if (!specs[i].itemProp) {
                                return
                            }
                            for (let j = 0; j < specs[i].itemProp.length; j++) {
                                if (info.id == specs[i].itemProp[j].id) {
                                    specs[i].itemProp.splice(j, 1);
                                }

                                if (specs[i].itemProp.length == 0) {
                                    specs.splice(i, 1);
                                }
                            }
                        }

                    }

                }
            }
            $scope.getSkus($scope.skuData.specs)
            // console.log('select--id', $scope.skuData.specs);
        };


//--------------------------------------------------------------------------------------生成获取SKU
        $scope.getSku = function () {
            // console.log('用于生成sku的specs===', $scope.skuData.specs);
            $scope.getSkus($scope.skuData.specs);
            // console.log('生成的sku==============', $scope.skuData.skuList);
        };
        //sku 条目处理函数
        // $scope.skuData.skus = [];   //用于生成sku表
        $scope.getSkus = function (specs) {
            // var specs = $scope.specs;
            var rows = 1;
            var cols = specs.length;
            specs.forEach(function (spec) {
                // console.log('spec ', spec);

                rows *= spec.itemProp.length;   //--------------------一共需要生成rows条sku

                // console.log('rows ', rows);
            });
            var skus = [];
            for (var i = 0; i < rows; i++) {     //第一个sku开始生成
                var sku = {
                    specList: [],
                    labelPrice: '',
                    salePrice: '',
                    buyPrice: '',
                    storage: '',
                };
                for (var j = 0; j < cols; j++) {              // -----地一个规格开始循环
                    var m = 1;
                    specs.forEach(function (spec, index) {
                        if (index <= j) {
                            return;
                        }
                        m *= spec.itemProp.length;
                    });
                    //if (i % m == 0) {
                    sku.specList.push({
                        name: specs[j].realName,
                        objId: specs[j].objId,
                        propId: specs[j].id,
                        //value: specs[j].values[Math.ceil(1.0 * i / m) % specs[j].values.length]
                        value: specs[j].itemProp[Math.floor(1.0 * i / m) % specs[j].itemProp.length]
                    });
                    //}
                }
                skus.push(sku);
            }
            // updataSku($scope.startSkus,skus);
            // $scope.skuData.skus = skus;

            $scope.skuData.skuList = skus;
            // console.log('123456$scope.skuData.skus', $scope.skuData.skuList);
        };


        //新增
        $scope.add = function (id) {
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
                    if (id) {   //子级
                        vmd.titleName = '新增规格值';
                        vmd.placeHolder = '请输入规格值,如:红色,L'
                    } else {   //父级
                        vmd.titleName = '新增规格';
                        vmd.placeHolder = '请输入规格,如:颜色,尺寸'
                    }
                    // vmd.title = '新增规格';
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
                        if (id) {   //子级
                            $scope.url = conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/itemProp/" + id + "/propValue";
                            vmd.placeHolder = '请输入属性名,如:红色,L'
                        } else {   //父级
                            $scope.url = conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/itemProp";
                            vmd.placeHolder = '请输入规格,如:颜色,尺寸'
                        }
                        $http({
                            method: "POST",
                            url: $scope.url,
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            },
                            params: {
                                name: vmd.name,
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
        $scope.edit = function (id, name, equal) {
            $mdDialog.show({
                templateUrl: 'addGood.html',
                //parent: angular.element(document.body).find('#qh-shop-wap-front'),
                clickOutsideToClose: false,   //true 点击界外的时候弹窗消失
                fullscreen: false,
                // locals: {key: $scope.info},
                controller: ['$mdDialog', function ($mdDialog) {
                    var vmd = this;

                    // vmd.title = '编辑规格';
                    if (equal == 'parent') {   //父级
                        vmd.titleName = '编辑规格';
                        vmd.placeHolder = '请输入规格,如:颜色,尺寸'

                    } else {   //子级
                        vmd.titleName = '编辑规格值';
                        vmd.placeHolder = '请输入规格值,如:红色,L'
                    }
                    vmd.codeShow = false;
                    vmd.name = name;
                    vmd.noName = false;
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
                        if (equal == 'parent') {
                            $scope.url = conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/itemProp/" + id;
                        } else {
                            $scope.url = conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/itemProp/" + id + "/propValue";
                        }
                        $http({
                            method: "PUT",
                            url: $scope.url,
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            },
                            params: {
                                name: vmd.name,
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
        $scope.delte = function (id, name) {
            if (name == 'parent') {
                $scope.url = conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/itemProp/" + id;
            } else {
                $scope.url = conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/itemProp/" + id + "/propValue";
            }
            alertService.confirm(null, "", "删除规格？", "取消", "确定").then(function (data) {
                if (data) {
                    $http({
                        method: "DELETE",   ///brandApp/{brandAppId}/shop/{shopId}/itemProp/{id}
                        url: $scope.url,
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        },
                    }).then(function (resp) {
                        if (resp.data.status == '200') {
                            $scope.getInfo();
                        }
                    }, function (resp) {

                    });
                }
            });

        };


        $scope.finish = function () {
            // console.log('$scope.skuData', $scope.skuData);
            var json = angular.toJson($scope.skuData);

            if ($scope.from == 'itemAdd') {
                $state.go("main.brandApp.store.itemManage.itemAdd", {
                    id: $scope.id,
                    skuData: json,
                    status: 'add',
                    selectMoreSpec: $stateParams.selectMoreSpec
                })
            } else {
                $state.go("main.brandApp.store.itemManage.itemEdit", {
                    id: $scope.id,
                    skuData: json,
                    status: 'add',
                    selectMoreSpec: $stateParams.selectMoreSpec
                })
            }
        };

        /*返回上级*/
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.store.shop.shopManage", {brandAppId: $stateParams.brandAppId}, {reload: true});
            } else {
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
