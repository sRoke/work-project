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
        $scope.editStatus = $stateParams.editStatus;
        $scope.id = $stateParams.id;
        if ($stateParams.skuData) {
            $scope.skuData = JSON.parse($stateParams.skuData);
            // console.log('  $scope.121', $scope.skuData);
        }
        else {
            $scope.skuData = {};
        }
        // console.log('  $scope.skuData', $scope.skuData);
        $scope.specShow = false;

        $scope.skuData.addSpecs = [];

        $scope.getInfo = function () {
            $http({
                method: "GET",  ///brandApp/{brandAppId}/shop/{shopId}/itemProp/itemPropValueListList
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

                    var upldateFlog = false;

                    for (let i = 0; i < $scope.skuData.specs.length; i++) {
                        for (let j = 0; j < $scope.skuData.specs[i].itemPropValueList.length; j++) {
                            for (let z = 0; z < $scope.items.length; z++) {
                                if ($scope.skuData.specs[i].itemProp.id == $scope.items[z].id) {
                                    $scope.items[z].specShow = true;
                                }
                                for (let k = 0; k < $scope.items[z].propValues.length; k++) {
                                    if ($scope.skuData.specs[i].itemPropValueList[j].id == $scope.items[z].propValues[k].id) {
                                        $scope.items[z].propValues[k].checked = true;
                                        if ($scope.skuData.specs[i].itemPropValueList[j].name != $scope.items[z].propValues[k].name) {
                                            $scope.skuData.specs[i].itemPropValueList[j] = $scope.items[z].propValues[k];
                                            upldateFlog = true;

                                        }
                                        // $scope.items[z].propValues[k].flog = true;  //选中的是否可编辑
                                    }
                                    // else {
                                    //     if (!$scope.skuData.specs[i]) {
                                    //         return
                                    //     }
                                    //     else {
                                    //         if (!$scope.skuData.specs[i].itemPropValueList) {
                                    //             return
                                    //         }
                                    //         for (let j = 0; j < $scope.skuData.specs[i].itemPropValueList.length; j++) {
                                    //             if ($scope.items[z].propValues[k].id == $scope.skuData.specs[i].itemPropValueList[j].id) {
                                    //                 $scope.skuData.specs[i].itemPropValueList.splice(j, 1);
                                    //             }
                                    //
                                    //             if ($scope.skuData.specs[i].itemPropValueList.length == 0) {
                                    //                 $scope.skuData.specs.splice(i, 1);
                                    //             }
                                    //         }
                                    //     }
                                    // }


                                }
                            }
                        }
                    }


                    if (upldateFlog) {
                        // console.log(1);

                        for (let q = 0; q < $scope.skuData.skuList.length; q++) {
                            for (let w = 0; w < $scope.skuData.skuList[q].specList.length; w++) {

                                // if($scope.skuData.skuList[q].specList[w].itemPropValueName != ){}

                                for (let z = 0; z < $scope.items.length; z++) {
                                    for (let k = 0; k < $scope.items[z].propValues.length; k++) {
                                        if ($scope.skuData.skuList[q].specList[w].itemPropValueId == $scope.items[z].propValues[k].id) {
                                            if ($scope.skuData.skuList[q].specList[w].itemPropValueName != $scope.items[z].propValues[k].name) {
                                                $scope.skuData.skuList[q].specList[w].itemPropValueName = $scope.items[z].propValues[k].name

                                            }
                                            // $scope.items[z].propValues[k].flog = true;  //选中的是否可编辑
                                        }
                                    }
                                }


                            }
                        }


                        // $scope.getSkus($scope.skuData.specs)
                    }


                }
            }, function (resp) {
                //error
            });
        };
        $scope.getInfo();


        $scope.getData = function () {
            ///brandApp/{brandAppId}/shop/{shopId}/itemProp/{id}
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/item/" + $scope.id,
                data: $scope.skuData,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                // console.log('resp', resp);
                $scope.oldSpecs = resp.data.data.specs;


                for (let i = 0; i < $scope.oldSpecs.length; i++) {
                    for (let j = 0; j < $scope.oldSpecs[i].itemPropValueList.length; j++) {
                        for (let z = 0; z < $scope.items.length; z++) {
                            if ($scope.oldSpecs[i].itemProp.id == $scope.items[z].id) {
                                $scope.items[z].specShow = true;
                            }
                            for (let k = 0; k < $scope.items[z].propValues.length; k++) {
                                if ($scope.oldSpecs[i].itemPropValueList[j].id == $scope.items[z].propValues[k].id) {
                                    // $scope.items[z].propValues[k].checked = true;
                                    $scope.items[z].propValues[k].flog = true;  //选中的是否可编辑
                                }
                            }
                        }
                    }
                }
            }, function (resp) {
                //TODO 错误处理
            });
        };

        $scope.getData();


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
        //编辑新增

        $scope.selectCheck = function (info, item) {
            info.checked = !info.checked;
            // console.log('info', info);
            // console.log('item', item);
            var specs = $scope.skuData.specs;

            if (info.checked) {
                var flog = false;

                for (var i = 0; i < specs.length; i++) {

                    if (specs[i].itemProp.id == item.id) {
                        specs[i].itemPropValueList.push(info);
                        // console.log('specs[i].itemPropValueList', specs[i].itemPropValueList);
                        flog = true;
                        break;
                    } else {
                        // break;
                    }

                }

                // if (!flog) {
                //     addSpecs.push({
                //         id: item.id,                  //该属性id
                //         realName: item.name,          //该属性名
                //         itemProp: [info],                 //该规格选中的属性
                //         // itemPropName: [],             //该规格选中的属性
                //     });
                // }
            }
            else {
                for (let i = 0; i < specs.length; i++) {

                    if (specs[i].itemProp.id == item.id) {

                        if (!specs[i]) {
                            return
                        }
                        else {
                            if (!specs[i].itemPropValueList) {
                                return
                            }
                            for (let j = 0; j < specs[i].itemPropValueList.length; j++) {
                                if (info.id == specs[i].itemPropValueList[j].id) {
                                    specs[i].itemPropValueList.splice(j, 1);
                                }

                                if (specs[i].itemPropValueList.length == 0) {
                                    specs.splice(i, 1);
                                }
                            }
                        }

                    }

                }
            }

            // console.log($scope.skuData.specs);
            $scope.getSkus($scope.skuData.specs);
            // console.log('select--id', $scope.skuData.addSpecs);
        };


        //更新sku
        function updataSku(startSkus, skus) {
            // console.log('startSkus=======', startSkus);
            // console.log('skus=======', skus);
            var flog = true;
            for (var i = 0; i < skus.length; i++) {
                for (var j = 0; j < startSkus.length; j++) {
                    for (var m = 0; m < startSkus[j].specList.length; m++) {

                        // if (startSkus[j].specList[m].itemPropValueName !== skus[i].specList[m].itemPropValueName) {
                        //     startSkus[j].specList[m] = skus[i].specList[m]
                        // }else {
                        if (startSkus[j].specList[m].itemPropValueId !== skus[i].specList[m].itemPropValueId) {

                            // if (startSkus[j].specList[m].itemPropValueName !== skus[i].specList[m].itemPropValueName) {
                            //     startSkus[j].specList[m] = skus[i].specList[m]
                            // }

                            flog = false;
                            break;
                        } else {
                            flog = true;
                        }
                        // }


                    }
                    if (flog == true) {
                        skus[i] = startSkus[j];
                    }
                }
            }
        }

//--------------------------------------------------------------------------------------生成获取SKU
        $scope.getSku = function () {
            // console.log('用于生成sku的addSpecs===', $scope.skuData.addSpecs);
            $scope.getSkus($scope.skuData.addSpecs);
            // console.log('生成的sku==============', $scope.skuData.skuList);
        };
        //sku 条目处理函数
        // $scope.skuData.skus = [];   //用于生成sku表
        $scope.getSkus = function (addSpecs) {
            // var addSpecs = $scope.addSpecs;
            var rows = 1;
            var cols = addSpecs.length;
            addSpecs.forEach(function (spec) {
                // console.log('spec ', spec);

                rows *= spec.itemPropValueList.length;   //--------------------一共需要生成rows条sku

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
                    addSpecs.forEach(function (spec, index) {
                        if (index <= j) {
                            return;
                        }
                        m *= spec.itemPropValueList.length;
                    });
                    //if (i % m == 0) {
                    sku.specList.push({
                        itemPropId: addSpecs[j].itemProp.id,
                        itemPropName: addSpecs[j].itemProp.name,
                        itemPropValueId: addSpecs[j].itemPropValueList[Math.floor(1.0 * i / m) % addSpecs[j].itemPropValueList.length].id,
                        itemPropValueName: addSpecs[j].itemPropValueList[Math.floor(1.0 * i / m) % addSpecs[j].itemPropValueList.length].name,
                    });

                    // sku.specList.push({
                    //     itemPropId: addSpecs[j].itemProp.id,
                    //     itemPropName: addSpecs[j].itemProp.name,
                    //     itemPropValueId: addSpecs[j].itemPropValueList.id,
                    //     itemPropValueName: spec.itemPropValueList.name
                    // });
                    //}
                }
                skus.push(sku);

            }
            updataSku($scope.skuData.skuList, skus);  //更新sku,如果存在,覆盖
            $scope.skuData.skuList = skus;

            // console.log('123456$scope.skuData.skuList', $scope.skuData.skuList);

        };


        $scope.test = function () {
            // console.log('1');
            // console.log('生成的==============', $scope.skuData);

            // console.log('生成的specs==============', $scope.skuData.specs);
            // $scope.getSkus($scope.skuData.specs);
            for (let z = 0; z < $scope.items.length; z++) {
                for (let k = 0; k < $scope.items[z].propValues.length; k++) {
                    // console.log('$scope.items[z].propValues[k].checked ', $scope.items[z].propValues[k].checked)
                }
            }
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
                            $scope.url = conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/itemProp/" + id + "/propValue"
                        } else {   //父级
                            $scope.url = conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/itemProp";
                        }
                        ;
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
                                $scope.getData();

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
        $scope.edit = function (info, item, equal) {
            $mdDialog.show({
                templateUrl: 'addGood.html',
                //parent: angular.element(document.body).find('#qh-shop-wap-front'),
                clickOutsideToClose: false,   //true 点击界外的时候弹窗消失
                fullscreen: false,
                // locals: {key: $scope.info},
                controller: ['$mdDialog', function ($mdDialog) {
                    var vmd = this;
                    vmd.codeShow = false;
                    vmd.name = info.name;

                    vmd.id = info.id
                    if (equal == 'parent') {   //父级
                        vmd.titleName = '编辑规格';
                        vmd.placeHolder = '请输入规格,如:颜色,尺寸'

                    } else {   //子级
                        vmd.titleName = '编辑规格值';
                        vmd.placeHolder = '请输入规格值,如:红色,L'
                    }
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
                            $scope.url = conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/itemProp/" + vmd.id;
                        } else {
                            $scope.url = conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/itemProp/" + vmd.id + "/propValue";
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
                                // info.checked = false;
                                $mdDialog.cancel();

                                $scope.getInfo();
                                $scope.getData();

                                // $scope.selectCheck(info, item);

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
        $scope.delte = function (info, item, name) {
            if (name == 'parent') {
                $scope.url = conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/itemProp/" + info.id;
            } else {
                $scope.url = conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/itemProp/" + info.id + "/propValue";
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


                            for (let i = 0; i < $scope.skuData.specs.length; i++) {

                                if ($scope.skuData.specs[i].itemProp.id == item.id) {

                                    if (!$scope.skuData.specs[i]) {
                                        return
                                    }
                                    else {
                                        if (!$scope.skuData.specs[i].itemPropValueList) {
                                            return
                                        }
                                        for (let j = 0; j < $scope.skuData.specs[i].itemPropValueList.length; j++) {
                                            if (info.id == $scope.skuData.specs[i].itemPropValueList[j].id) {
                                                $scope.skuData.specs[i].itemPropValueList.splice(j, 1);
                                            }

                                            if ($scope.skuData.specs[i].itemPropValueList.length == 0) {
                                                $scope.skuData.specs.splice(i, 1);
                                            }
                                        }
                                    }

                                }

                            }

                            // console.log($scope.skuData.specs);
                            $scope.getSkus($scope.skuData.specs);

                            $scope.getInfo();

                            $scope.getData();

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
                $state.go("main.brandApp.store.itemManage.itemAdd", {id: $scope.id, skuData: json, status: 'add'})
            } else {
                $state.go("main.brandApp.store.itemManage.itemEdit", {
                    id: $scope.id, skuData: json, status: 'add',
                    editStatus: $scope.editStatus
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
