import conf from "../../../../../../conf";

import PhotoClip from 'photoclip';
var $scope,
    loginService,
    alertService,
    $state,
    Upload,
    $stateParams,
    $location,
    $http, $mdDialog;
class Controller {
    constructor(_$scope,
                _loginService,
                _alertService,
                _$state,
                _Upload,
                _$stateParams,
                _$location,
                _$http,
                _$mdDialog) {
        $scope = _$scope;
        loginService = _loginService;
        $state = _$state;
        alertService = _alertService;
        $mdDialog = _$mdDialog;
        $stateParams = _$stateParams;
        $location = _$location;
        $http = _$http;
        Upload = _Upload;
        loginService.loginCtl(true, $location.absUrl());

        $scope.brandAppId = $stateParams.brandAppId;
        $scope.id = $stateParams.id;
        $scope.status = $stateParams.status;
        $scope.storeId = $stateParams.storeId;

        if ($stateParams.skuData) {
            $scope.skuData = JSON.parse($stateParams.skuData);

            // if ($scope.skuData.category) {
            //     for (var i = 0; i < $scope.skuData.category.id.length; i++) {
            //         $scope.skuData.categorys.push($scope.skuData.category.id[i])
            //     }
            // }

        } else {
            $scope.skuData = {};
            //--------------图片上传
            $scope.skuData.imgs = [];
            $scope.skuData.skuList = [];
            $scope.skuData.specs = [];
            $scope.skuData.category = [];
            $scope.skuData.singleSku = {};
        }

        // console.log('$scope.skuData', $scope.skuData);


        $scope.choosePhote = false;
        // 图片裁剪
        var pc = new PhotoClip('#clipArea', {
            size: [260, 260],
            outputSize: 640,
            // adaptive: ['70','40'],
            file: '#file,#file2',
            view: '#view',
            ok: '#clipBtn',
            style: {
                maskColor: 'rgba(0,0,0,0.7)',
                // jpgFillColor:''
            },
            loadStart: function () {
                // console.log('开始读取照片');
            },
            loadComplete: function () {
                // console.log('照片读取完成', $scope);
                $scope.choosePhote = true;
                $scope.$apply();
            },
            done: function (dataURL) {
                // console.log('base64裁剪完成,正在上传');
                $scope.saveImg(dataURL);
            },
            fail: function (msg) {
                alert(msg);
            }
        });
        $scope.saveImg = function (dataUrl) {
            $http({
                method: "POST",
                url: conf.yunApiPath + "/app/5988791a6b869f4e18d5c8d5/org/598878fc6b869f4e0f19fb47/yunFile/b64",
                data: {
                    base64DataUrl: dataUrl
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken()
                }
            }).then(function (resp) {
                    $scope.dataUrl = resp.data.data;
                    $scope.getImg($scope.dataUrl);
                    // console.log(resp.data.data)

                }, function () {
                    //error
                }
            );
        };
        $scope.getImg = function (id) {
            $http({
                method: "GET",
                url: conf.yunApiPath + "/app/5988791a6b869f4e18d5c8d5/org/598878fc6b869f4e0f19fb47/yunFile/" + id,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken()
                }
            }).then(function (resp) {
                    // console.log(resp.data.data);
                    $scope.skuData.imgs.push(resp.data.data.cdnUrls[0].url);
                    $scope.choosePhote = false;
                    // console.log('$scope.skuData.imgs', $scope.skuData.imgs);

                }, function () {
                    //error
                }
            );
        };
        $scope.cancelChoosePhote = function () {
            $scope.choosePhote = false;
        };


        //检验输入框
        $scope.AddEventInput = function (num) {
            var reg = /(^[1-9]{1}[0-9]*$)|(^[0-9]+\.[0-9]{0,2}$)|(^[0]$)/;

            if (num == 1) {
                if (!reg.test($scope.skuData.singleSku.salePrice)) {
                    alertService.msgAlert("exclamation-circle", '请输入大于0的数');
                    $scope.skuData.singleSku.salePrice='';
                    // console.log("请输入大于0的整数或者保留")
                } else {
                    // console.log("输入正确");
                }
            }

            if (num == 2) {
                if (!reg.test($scope.skuData.singleSku.labelPrice)) {
                    $scope.skuData.singleSku.labelPrice = '';
                    alertService.msgAlert("exclamation-circle", '请输入大于0的数');
                    // console.log("请输入大于0的整数或者保留")
                } else {
                    // console.log("输入正确");
                }
            }

            if (num == 3) {
                if (!reg.test($scope.skuData.singleSku.buyPrice)) {
                    $scope.skuData.singleSku.buyPrice = '';
                    alertService.msgAlert("exclamation-circle", '请输入大于0的数');
                    // console.log("请输入大于0的整数或者保留")
                } else {
                    // console.log("输入正确");
                }
            }
            if (num == 4) {
                if (!reg.test($scope.skuData.freight)) {
                    $scope.skuData.freight = '';
                    alertService.msgAlert("exclamation-circle", '请输入不小于0的数');
                    // console.log("请输入大于0的整数或者保留")
                } else {
                    // console.log("输入正确");
                }
            }
        };
        //
        // //图片上传
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
        //                 // 上传代码返回结果之后，将图片push到数组中
        //                 $scope.skuData.imgs.push(resp.data.data.cdnUrls[0].url);
        //                 console.log('$scope.skuData.imgs', $scope.skuData.imgs)
        //
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
        //
        // };

        /**
         * 删除指定的图片
         * @param index
         */
        $scope.cancelDel = function (index) {
            $scope.skuData.imgs.splice(index, 1);
        };


        $scope.imgDialog = false;
        $scope.viewImg = function (img) {
            $scope.imgDialog = true;
            $scope.imgView = img;
        };

        $scope.closeView = function () {
            $scope.imgDialog = false;
        };
        // console.log(' $stateParams.selectMoreSpec  ', $stateParams.selectMoreSpec);

        //多规格选择
        $scope.selectMoreSpec = $stateParams.selectMoreSpec;  // ($stateParams.selectMoreSpec == true) ? true : false;
        // console.log('    $scope.selectMoreSpec ', $scope.selectMoreSpec);
        $scope.moreSpec = function () {
            $scope.selectMoreSpec = !$scope.selectMoreSpec;
            // console.log('    $scope.selectMoreSpec ', $scope.selectMoreSpec);

        };


        // $scope.singleSku = {};

        //
        // var defaultSku = {
        //     specTitle: '',
        //     specValue: '',
        //     // specName: '',
        //     salePrice: '',
        //     buyPrice: '',
        //     labelPrice: '',
        //     storage: '',
        //     code: '',
        //     check: ''
        // };


        // $scope.addSku = function () {
        //
        //     if ($scope.skuData.skuList.length == 0) {
        //         defaultSku.specTitle = '规格1';
        //         defaultSku.check = true;
        //     } else {
        //         for (var i = 0; i < $scope.skuData.skuList.length; i++) {
        //             console.log('$scope.skuData.skuList.length', $scope.skuData.skuList.length);
        //             console.log('i', i);
        //             $scope.skuData.skuList[i].check = false;
        //             if (i == $scope.skuData.skuList.length - 1) {
        //                 defaultSku.specTitle = '规格' + (i + 2);
        //                 defaultSku.check = true;
        //                 console.log('defaultSku.specTitle', defaultSku.specTitle);
        //             }
        //
        //         }
        //     }
        //     console.log('defaultSku', defaultSku);
        //     $scope.skuData.skuList.push(angular.copy(defaultSku));
        //     console.log('$scope.skuData.skuList', $scope.skuData.skuList);
        // };
        //

        //单规格sku展开收缩
        $scope.check = false;
        $scope.openSku = function () {
            $scope.check = !$scope.check;
        };
        //
        // //定义数组函数,查询元素在数组中的位置,如果有返回i,没有就返回-1
        // Array.prototype.indexOf = function (val) {
        //     for (var i = 0; i < this.length; i++) {
        //         if (this[i] == val) return i;
        //     }
        //     return -1;
        // };
        // //定义数组删除函数,根据查询道德index,删除数组中的指定元素
        // Array.prototype.remove = function (val) {
        //     var index = this.indexOf(val);
        //     if (index > -1) {
        //         this.splice(index, 1);
        //     }
        // };
        // $scope.deleteSku = function (sku) {
        //
        //     //通过此方法就可以调用删除
        //     $scope.skuData.skuList.remove(sku);
        //
        //     console.log('$scope.skuData.skuList.', $scope.skuData.skuList)
        // };


        // $scope.getData = function () {
        //
        //     ///brandApp/{brandAppId}/shop/{shopId}/itemProp/{id}         $scope.storeId = $stateParams.storeId;
        //
        //
        //     $http({
        //         method: "GET",
        //         url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/item/" + $scope.id,
        //         data: $scope.skuData,
        //         headers: {
        //             'Authorization': 'Bearer ' + loginService.getAccessToken(),
        //             "brandApp-Id": $scope.brandAppId
        //         }
        //     }).then(function (resp) {
        //         console.log('resp', resp);
        //         $scope.skuData = resp.data.data;
        //
        //         for (var i = 0; i < $scope.skuData.skuList.length; i++) {
        //             $scope.skuData.skuList[i].specValue = $scope.skuData.skuList[i].specList[0].itemPropValue
        //         }
        //
        //     }, function (resp) {
        //         //TODO 错误处理
        //     });
        //
        // };
        //
        // if ($scope.id) {
        //     $scope.getData()
        // }

        $scope.updateItem = function () {
            // /brandApp/{brandAppId}/shop/{shopId}/item/{id}/changeStatus

            $http({
                method: "PUT",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/item/" + $scope.id + "/changeStatus",
                params: {
                    status: 'NORMAL'
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                // console.log('resp', resp);
                // $scope.skuData = resp.data.data
            }, function (resp) {
                //TODO 错误处理
            });
        };


        $scope.save = function () {
            $scope.saveData = {
                categorys: [],
                imgs: [],
                skuList: [],
                specs: []
            };

            // console.log('$scope.skuData', $scope.skuData);
            // console.log('$scope.skuData.skuData.singleSku', $scope.skuData.singleSku);
            // console.log(' selectMoreSpec', $scope.selectMoreSpec);

            if ($scope.skuData.imgs.length == 0) {
                alertService.msgAlert("exclamation-circle", '请上传图片');
                return;
            }
            if (!$scope.skuData.title) {
                alertService.msgAlert("exclamation-circle", '请输入商品名称');
                return;
            }
            if (!$scope.selectMoreSpec) {
                if (!$scope.skuData.singleSku.salePrice) {
                    alertService.msgAlert("exclamation-circle", '请输入销售价格');
                    return;
                }
                else if (!$scope.skuData.singleSku.storage) {
                    alertService.msgAlert("exclamation-circle", '请输入商品库存');
                    return;
                } else {
                    if ($scope.skuData.skuList.length <= 0) {
                        $scope.skuData.skuList.push($scope.skuData.singleSku);
                    } else {

                    }
                }
            }

            if ($scope.selectMoreSpec) {
                if ($scope.skuData.specs.length == 0) {
                    alertService.msgAlert("exclamation-circle", '请选择规格');
                    return;
                }
            }

            if ($scope.skuData.skuList.length == 0) {
                alertService.msgAlert("exclamation-circle", '请选择sku');
                return;
            }


            for (let i = 0; i < $scope.skuData.specs.length; i++) {

                $scope.saveData.specs.push({
                    itemPropId: $scope.skuData.specs[i].id,
                    itemPropValueIds: []
                });

                for (let j = 0; j < $scope.skuData.specs[i].itemProp.length; j++) {
                    $scope.saveData.specs[i].itemPropValueIds.push($scope.skuData.specs[i].itemProp[j].id)

                }
            }


            for (let i = 0; i < $scope.skuData.skuList.length; i++) {
                if (!$scope.skuData.skuList[i].salePrice) {
                    alertService.msgAlert("exclamation-circle", '请编辑价格');
                    return;
                }
                if (!$scope.skuData.skuList[i].storage) {
                    alertService.msgAlert("exclamation-circle", '请编辑库存');
                    return;
                }
                $scope.saveData.skuList.push({
                    buyPrice: $scope.skuData.skuList[i].buyPrice * 100,
                    labelPrice: $scope.skuData.skuList[i].labelPrice * 100,
                    salePrice: $scope.skuData.skuList[i].salePrice * 100,
                    storage: $scope.skuData.skuList[i].storage,
                    code: $scope.skuData.skuList[i].code,
                    specList: [],
                });

                if ($scope.selectMoreSpec) {
                    for (let j = 0; j < $scope.skuData.skuList[i].specList.length; j++) {
                        $scope.saveData.skuList[i].specList.push({
                            itemPropId: $scope.skuData.skuList[i].specList[j].propId,
                            itemPropValueId: $scope.skuData.skuList[i].specList[j].value.id
                        });
                    }
                }


            }


            if ($scope.skuData.category.id) {
                $scope.saveData.categorys = $scope.skuData.category.id;
            }

            $scope.saveData.imgs = $scope.skuData.imgs;
            $scope.saveData.title = $scope.skuData.title;
            $scope.saveData.detail = $scope.skuData.detail;
            $scope.saveData.freight = $scope.skuData.freight * 100;
            $scope.saveData.desp = $scope.skuData.desp;
            // console.log('$scope.saveData', $scope.saveData);
            alertService.confirm(null, "", "确定保存？", "取消", "确定").then(function (data) {
                if (data) {
                    $http({
                        method: "POST",
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/item",
                        data: $scope.saveData,
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        }
                    }).then(function (resp) {
                        // console.log('resp', resp);

                        if (resp.data.status == '200') {
                            $state.go("main.brandApp.store.itemManage.itemMan", {}, {reload: true});
                        }

                    }, function (resp) {
                        //TODO 错误处理
                    });
                }
            });
        };


        //统一设置
        // $scope.openDialog = function (skuList) {
        //
        //     var skuList = angular.copy(skuList);
        //     $mdDialog.show({
        //         templateUrl: 'setSku.html',
        //         //parent: angular.element(document.body).find('#qh-shop-wap-front'),
        //         clickOutsideToClose: false,   //true 点击界外的时候弹窗消失
        //         fullscreen: false,
        //         // locals: {key: skuList},
        //         controller: ['$mdDialog', function ($mdDialog) {
        //             var vmd = this;
        //             vmd.data = {};
        //
        //             //取消
        //             vmd.cance = function () {
        //                 $mdDialog.cancel();
        //             };
        //             vmd.confirm = function () {
        //                 if (!vmd.data.labelPrice || !vmd.data.salePrice || !vmd.data.buyPrice || !vmd.data.storage) {
        //                     return;
        //                 }
        //                 $mdDialog.hide({data: vmd.data});
        //             }
        //         }],
        //         controllerAs: "vmd"
        //     }).then(function (answer) {
        //
        //         for (var i = 0; i < $scope.skuData.skuList.length; i++) {
        //             $scope.skuData.skuList[i].labelPrice = answer.data.labelPrice;
        //             $scope.skuData.skuList[i].salePrice = answer.data.salePrice;
        //             $scope.skuData.skuList[i].buyPrice = answer.data.buyPrice;
        //             $scope.skuData.skuList[i].storage = answer.data.storage;
        //         }
        //
        //     }, function () {
        //         //error
        //     });
        //
        // };


        $scope.saveAndUpdate = function () {
            $scope.saveData = {
                categorys: [],
                imgs: [],
                skuList: [],
                specs: []
            };

            // console.log('$scope.skuData', $scope.skuData);
            // console.log('$scope.skuData.singleSku', $scope.skuData.singleSku);
            // console.log(' $scope.selectMoreSpec', $scope.selectMoreSpec);


            if ($scope.skuData.imgs.length == 0) {
                alertService.msgAlert("exclamation-circle", '请上传图片');
                return;
            }
            if (!$scope.skuData.title) {
                alertService.msgAlert("exclamation-circle", '请输入商品名称');
                return;
            }
            if (!$scope.selectMoreSpec) {
                if (!$scope.skuData.singleSku.salePrice) {
                    alertService.msgAlert("exclamation-circle", '请输入销售价格');
                    return;
                }
                else if (!$scope.skuData.singleSku.storage) {
                    alertService.msgAlert("exclamation-circle", '请输入商品库存');
                    return;
                } else {
                    if ($scope.skuData.skuList.length <= 0) {
                        $scope.skuData.skuList.push($scope.skuData.singleSku);
                    } else {

                    }
                }
            }

            if ($scope.selectMoreSpec) {
                if ($scope.skuData.specs.length == 0) {
                    alertService.msgAlert("exclamation-circle", '请选择规格');
                    return;
                }
            }

            if ($scope.skuData.skuList.length == 0) {
                alertService.msgAlert("exclamation-circle", '请选择sku');
                return;
            }


            for (let i = 0; i < $scope.skuData.specs.length; i++) {

                $scope.saveData.specs.push({
                    itemPropId: $scope.skuData.specs[i].id,
                    itemPropValueIds: []
                });

                for (let j = 0; j < $scope.skuData.specs[i].itemProp.length; j++) {
                    $scope.saveData.specs[i].itemPropValueIds.push($scope.skuData.specs[i].itemProp[j].id)

                }
            }


            for (let i = 0; i < $scope.skuData.skuList.length; i++) {
                if (!$scope.skuData.skuList[i].salePrice) {
                    alertService.msgAlert("exclamation-circle", '请编辑价格');
                    return;
                }
                if (!$scope.skuData.skuList[i].storage) {
                    alertService.msgAlert("exclamation-circle", '请编辑库存');
                    return;
                }
                $scope.saveData.skuList.push({
                    buyPrice: $scope.skuData.skuList[i].buyPrice * 100,
                    labelPrice: $scope.skuData.skuList[i].labelPrice * 100,
                    salePrice: $scope.skuData.skuList[i].salePrice * 100,
                    storage: $scope.skuData.skuList[i].storage,
                    code: $scope.skuData.skuList[i].code,
                    specList: [],
                });

                if ($scope.selectMoreSpec) {
                    for (let j = 0; j < $scope.skuData.skuList[i].specList.length; j++) {
                        $scope.saveData.skuList[i].specList.push({
                            itemPropId: $scope.skuData.skuList[i].specList[j].propId,
                            itemPropValueId: $scope.skuData.skuList[i].specList[j].value.id
                        });
                    }
                }


            }


            if ($scope.skuData.category.id) {
                $scope.saveData.categorys = $scope.skuData.category.id;
            }

            $scope.saveData.imgs = $scope.skuData.imgs;
            $scope.saveData.title = $scope.skuData.title;
            $scope.saveData.detail = $scope.skuData.detail;
            $scope.saveData.freight = $scope.skuData.freight * 100;
            $scope.saveData.desp = $scope.skuData.desp;

            // console.log('$scope.saveData', $scope.saveData);


            alertService.confirm(null, "", "确定保存并上架？", "取消", "确定").then(function (data) {
                if (data) {
                    $http({
                        method: "POST",
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/item",
                        data: $scope.saveData,
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        }
                    }).then(function (resp) {
                        // console.log('resp', resp);

                        if (resp.data.status == '200') {
                            // $state.go("main.brandApp.store.itemManage.itemMan", {}, {reload: true});

                            $http({
                                method: "PUT",
                                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/item/" + resp.data.data + "/changeStatus",
                                params: {
                                    status: 'NORMAL'
                                },
                                headers: {
                                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                    "brandApp-Id": $scope.brandAppId
                                }
                            }).then(function (resp) {
                                // console.log('resp', resp);
                                $state.go("main.brandApp.store.itemManage.itemMan", {}, {reload: true});
                            }, function (resp) {
                                //TODO 错误处理
                                alertService.msgAlert("exclamation-circle", resp.data.message)
                            });
                        }

                    }, function (resp) {
                        //TODO 错误处理
                    });
                }
            });


        };


        $scope.goChoose = function () {
            var json = angular.toJson($scope.skuData);
            $state.go("main.brandApp.store.shop.selectClassify", {
                id: $scope.id,
                skuData: json,
                choose: 'choose',
                from: 'itemAdd',
                selectMoreSpec: $scope.selectMoreSpec,
            })
        };

        $scope.goSetPrice = function () {
            var json = angular.toJson($scope.skuData);
            $state.go("main.brandApp.store.itemManage.itemSetPrice", {
                id: $scope.id,
                skuData: json,
                choose: 'choose',
                from: 'itemAdd',
                selectMoreSpec: $scope.selectMoreSpec,
            })
        };

        $scope.goChooseSpec = function () {
            var json = angular.toJson($scope.skuData);
            $state.go("main.brandApp.store.shop.selectSpec", {
                id: $scope.id,
                skuData: json,
                choose: 'choose',
                from: 'itemAdd',
                selectMoreSpec: $scope.selectMoreSpec,
            })
        };


        $scope.goEdit = function () {
            var json = angular.toJson($scope.skuData);
            $state.go("main.brandApp.store.textImg", {
                skuData: json,
                choose: 'choose',
                from: 'itemAdd',
                selectMoreSpec: $scope.selectMoreSpec,
            })
        };
        /*返回上级*/
        $scope.fallbackPage = function () {
            $state.go("main.brandApp.store.itemManage.itemMan", {}, {reload: true});
        };
    }
}

Controller.$inject = [
    '$scope',
    'loginService',
    'alertService',
    '$state',
    'Upload',
    '$stateParams',
    '$location',
    '$http',
    '$mdDialog',
];

export default Controller ;
