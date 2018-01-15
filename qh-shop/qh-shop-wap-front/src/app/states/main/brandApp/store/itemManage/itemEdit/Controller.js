import conf from "../../../../../../conf";

import PhotoClip from 'photoclip';

var $scope,
    loginService,
    $state,
    Upload,
    $stateParams,
    alertService,
    $location,
    $http, $mdDialog;
class Controller {
    constructor(_$scope,
                _loginService,
                _$state,
                _Upload,
                _$stateParams,
                _alertService,
                _$location,
                _$http, _$mdDialog) {
        $scope = _$scope;
        loginService = _loginService;
        $state = _$state;
        $mdDialog = _$mdDialog;
        $stateParams = _$stateParams;
        $location = _$location;
        alertService = _alertService;
        $http = _$http;
        Upload = _Upload;
        loginService.loginCtl(true, $location.absUrl());

        $scope.brandAppId = $stateParams.brandAppId;
        $scope.id = $stateParams.id;
        $scope.status = $stateParams.status;
        $scope.storeId = $stateParams.storeId;
        if ($stateParams.skuData) {
            $scope.skuData = JSON.parse($stateParams.skuData);
            $scope.skuData.categorys = [];
            for (var i = 0; i < $scope.skuData.category.id.length; i++) {
                $scope.skuData.categorys.push($scope.skuData.category.id[i])
            }
        } else {
            $scope.skuData = {};
            //--------------图片上传
            $scope.skuData.imgs = [];
            $scope.skuData.skuList = [];
        }

        // console.log('$scope.skuData', $scope.skuData);
        //图片上传
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
                }, function () {
                    //error
                }
            );
        };
        $scope.cancelChoosePhote = function () {
            $scope.choosePhote = false;
        };
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
        $scope.skuData.skuList = [];
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
                $scope.skuData = resp.data.data;


                $scope.status = $scope.skuData.status;
                $scope.skuData.freight = $scope.skuData.freight / 100;
                // console.log('$scope.skuData.specs.length',$scope.skuData.specs.length <= 0);

                if ($scope.skuData.specs.length <= 0) {
                    $scope.selectMoreSpec = false;
                    $scope.skuData.singleSku = $scope.skuData.skuList[0];
                }else{
                    $scope.selectMoreSpec = true
                }

                for (var i = 0; i < $scope.skuData.skuList.length; i++) {
                    $scope.skuData.skuList[i].labelPrice = $scope.skuData.skuList[i].labelPrice / 100;
                    $scope.skuData.skuList[i].salePrice = $scope.skuData.skuList[i].salePrice / 100;
                    $scope.skuData.skuList[i].buyPrice = $scope.skuData.skuList[i].buyPrice / 100;
                    $scope.skuData.skuList[i].storage = $scope.skuData.skuList[i].storage;
                    $scope.skuData.skuList[i].code = $scope.skuData.skuList[i].code;

                    // console.log('$scope.skuData.skuList[i].labelPrice ', $scope.skuData.skuList[i].labelPrice);
                }
                if ($stateParams.skuData) {
                    $scope.skuData = JSON.parse($stateParams.skuData);
                    $scope.skuData.categorys = [];

                    for (var i = 0; i < $scope.skuData.category.id.length; i++) {
                        $scope.skuData.categorys.push($scope.skuData.category.id[i])
                    }
                }


                // for (var i = 0; i < $scope.skuData.skuList.length; i++) {
                //     $scope.skuData.skuList[i].specValue = $scope.skuData.skuList[i].specList[0].itemPropValue
                // }

            }, function (resp) {
                //TODO 错误处理
            });
        };

        $scope.getData();

        $scope.check = false;
        $scope.openSku = function () {
            $scope.check = !$scope.check;
        };
        //上架
        $scope.updateItem = function (status, edit) {
            // $scope.editSave();
            if (status == 'NORMAL') {
                $scope.sta = '上架';
            } else if (status == 'SALE_OFF') {
                $scope.sta = '下架';
            }


            alertService.confirm(null, "", "确定" + $scope.sta + "？", "取消", "确定").then(function (data) {
                if (data) {
                    $http({
                        method: "PUT",
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/item/" + $scope.id + "/changeStatus",
                        params: {
                            status: status
                        },
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        }
                    }).then(function (resp) {
                        // console.log('resp', resp);
                        if (edit) {
                            $scope.editStatus = !$scope.editStatus;
                        }
                        alertService.msgAlert("exclamation-circle", $scope.sta + "成功");

                        $scope.getData();
                    }, function (resp) {
                        //TODO 错误处理
                        alertService.msgAlert("exclamation-circle", resp.data.message)
                    });
                }
            });


        };

        $scope.editStatus = $stateParams.editStatus ? $stateParams.editStatus : 'edit';
        $scope.goEdit = function () {
            $scope.editStatus = !$scope.editStatus;

            // console.log('$scope.editStatus', $scope.editStatus)
        };
        $scope.goSetPrice = function () {
            var json = angular.toJson($scope.skuData);
            $state.go("main.brandApp.store.itemManage.itemSetPrice", {
                id: $scope.id,
                skuData: json,
                choose: 'choose',
                from: 'itemEdit',
                editStatus: $scope.editStatus
            })
        };
        $scope.editSave = function () {
            // /brandApp/{brandAppId}/shop/{shopId}/item/{id}

            $scope.saveData = {
                categorys: [],
                imgs: [],
                skuList: [],
                specs: []
            };
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

            // if (!$scope.skuData.specs) {
            //     alertService.msgAlert("exclamation-circle", '请选择规格');
            //     return;
            // }

            if ($scope.selectMoreSpec) {
                if ($scope.skuData.specs.length == 0) {
                    alertService.msgAlert("exclamation-circle", '请选择规格');
                    return;
                }
            }

            if (!$scope.skuData.skuList) {
                alertService.msgAlert("exclamation-circle", '请选择sku');
                return;
            }
            // if (!$scope.skuData.detail) {
            //     alertService.msgAlert("exclamation-circle", '请编辑图文');
            //     return;
            // }

            for (let i = 0; i < $scope.skuData.specs.length; i++) {
                $scope.saveData.specs.push({
                    itemPropId: $scope.skuData.specs[i].itemProp.id,
                    itemPropValueIds: []
                });

                if ($scope.skuData.specs[i].itemPropValueList) {
                    for (let j = 0; j < $scope.skuData.specs[i].itemPropValueList.length; j++) {
                        $scope.saveData.specs[i].itemPropValueIds.push($scope.skuData.specs[i].itemPropValueList[j].id)
                    }
                }
                else {
                    for (let j = 0; j < $scope.skuData.specs[i].itemProp.length; j++) {
                        $scope.saveData.specs[i].itemPropValueIds.push($scope.skuData.specs[i].itemProp[j].id)

                    }
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
                    buyPrice: $scope.skuData.skuList[i].buyPrice * 100,//
                    labelPrice: $scope.skuData.skuList[i].labelPrice * 100,
                    salePrice: $scope.skuData.skuList[i].salePrice * 100,
                    storage: $scope.skuData.skuList[i].storage,
                    code: $scope.skuData.skuList[i].code,//
                    specList: [],
                });

                if ($scope.selectMoreSpec) {
                    for (let j = 0; j < $scope.skuData.skuList[i].specList.length; j++) {
                        $scope.saveData.skuList[i].specList.push({
                            itemPropId: $scope.skuData.skuList[i].specList[j].itemPropId,
                            itemPropValueId: $scope.skuData.skuList[i].specList[j].itemPropValueId
                        });
                    }
                }


            }


            $scope.saveData.categorys = $scope.skuData.category.id;
            $scope.saveData.imgs = $scope.skuData.imgs;
            $scope.saveData.title = $scope.skuData.title;
            $scope.saveData.detail = $scope.skuData.detail;
            $scope.saveData.desp = $scope.skuData.desp;

            $scope.saveData.freight = $scope.skuData.freight * 100;
            // $scope.saveData.skuList = $scope.skuData.skuList;
            // console.log('$scope.skuData', $scope.skuData);
            // console.log('$scope.saveData', $scope.saveData);


            $http({
                method: "PUT",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/item/" + $scope.id,
                data: $scope.saveData,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                // console.log('resp', resp);
                if (resp.data.status == '200') {
                    // console.log('$scope.editStatus', $scope.editStatus)
                    $scope.editStatus = 'edit';
                    alertService.msgAlert("exclamation-circle", "保存成功").then(function () {
                        $scope.fallbackPage()
                    });
                }

            }, function (resp) {
                //TODO 错误处理
            });

        };


        $scope.saveAndUpdate = function () {
            // /brandApp/{brandAppId}/shop/{shopId}/item/{id}

            $scope.saveData = {
                categorys: [],
                imgs: [],
                skuList: [],
                specs: []
            };
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

            // if (!$scope.skuData.specs) {
            //     alertService.msgAlert("exclamation-circle", '请选择规格');
            //     return;
            // }

            if ($scope.selectMoreSpec) {
                if ($scope.skuData.specs.length == 0) {
                    alertService.msgAlert("exclamation-circle", '请选择规格');
                    return;
                }
            }

            if (!$scope.skuData.skuList) {
                alertService.msgAlert("exclamation-circle", '请选择sku');
                return;
            }
            // if (!$scope.skuData.detail) {
            //     alertService.msgAlert("exclamation-circle", '请编辑图文');
            //     return;
            // }

            for (let i = 0; i < $scope.skuData.specs.length; i++) {
                $scope.saveData.specs.push({
                    itemPropId: $scope.skuData.specs[i].itemProp.id,
                    itemPropValueIds: []
                });

                if ($scope.skuData.specs[i].itemPropValueList) {
                    for (let j = 0; j < $scope.skuData.specs[i].itemPropValueList.length; j++) {
                        $scope.saveData.specs[i].itemPropValueIds.push($scope.skuData.specs[i].itemPropValueList[j].id)
                    }
                }
                else {
                    for (let j = 0; j < $scope.skuData.specs[i].itemProp.length; j++) {
                        $scope.saveData.specs[i].itemPropValueIds.push($scope.skuData.specs[i].itemProp[j].id)

                    }
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
                    buyPrice: $scope.skuData.skuList[i].buyPrice * 100,//
                    labelPrice: $scope.skuData.skuList[i].labelPrice * 100,
                    salePrice: $scope.skuData.skuList[i].salePrice * 100,
                    storage: $scope.skuData.skuList[i].storage,
                    code: $scope.skuData.skuList[i].code,//
                    specList: [],
                });

                if ($scope.selectMoreSpec) {
                    for (let j = 0; j < $scope.skuData.skuList[i].specList.length; j++) {
                        $scope.saveData.skuList[i].specList.push({
                            itemPropId: $scope.skuData.skuList[i].specList[j].itemPropId,
                            itemPropValueId: $scope.skuData.skuList[i].specList[j].itemPropValueId
                        });
                    }
                }


            }


            $scope.saveData.categorys = $scope.skuData.category.id;
            $scope.saveData.imgs = $scope.skuData.imgs;
            $scope.saveData.title = $scope.skuData.title;
            $scope.saveData.detail = $scope.skuData.detail;
            $scope.saveData.desp = $scope.skuData.desp;

            $scope.saveData.freight = $scope.skuData.freight * 100;
            // $scope.saveData.skuList = $scope.skuData.skuList;
            // console.log('$scope.skuData', $scope.skuData);
            // console.log('$scope.saveData', $scope.saveData);

            alertService.confirm(null, "", "确定保存并上架？", "取消", "确定").then(function (data) {
                if (data) {
                    $http({
                        method: "PUT",
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/item/" + $scope.id,
                        data: $scope.saveData,
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        }
                    }).then(function (resp) {
                        // console.log('resp', resp);
                        if (resp.data.status == '200') {
                            // console.log('$scope.editStatus', $scope.editStatus)
                            $scope.editStatus = 'edit';


                            $http({
                                method: "PUT",
                                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/item/" + $scope.id + "/changeStatus",
                                params: {
                                    status: 'NORMAL',
                                },
                                headers: {
                                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                    "brandApp-Id": $scope.brandAppId
                                }
                            }).then(function (resp) {
                                // console.log('resp', resp);
                                // $scope.getData();

                                alertService.msgAlert("exclamation-circle", "保存成功").then(function () {
                                    $scope.fallbackPage()
                                });
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
                from: 'itemEdit',
                editStatus: $scope.editStatus
            })
        };

        $scope.goChooseSpec = function () {
            var json = angular.toJson($scope.skuData);
            $state.go("main.brandApp.store.shop.itemEditSelectSpec", {
                id: $scope.id,
                skuData: json,
                choose: 'choose',
                from: 'itemEdit',
                editStatus: $scope.editStatus
            })
        };
        $scope.goCheckSku = function () {
            var json = angular.toJson($scope.skuData);

            $state.go("main.brandApp.store.itemManage.specEdit", {
                id: $scope.id,
                skuData: json,
                choose: 'choose',
                from: 'itemEdit',
                editStatus: $scope.editStatus
            })
        };
        $scope.goEditImg = function () {
            var json = angular.toJson($scope.skuData);
            $state.go("main.brandApp.store.textImg", {
                id: $scope.id,
                skuData: json,
                choose: 'choose',
                from: 'itemEdit',
                editStatus: $scope.editStatus
            })
        };

        $scope.goViewImg = function () {
            var json = angular.toJson($scope.skuData);
            $state.go("main.brandApp.store.viewImg", {
                id: $scope.id,
                skuData: json,
                choose: 'choose',
                from: 'itemEdit',
                editStatus: $scope.editStatus
            })
        };

        //删除
        $scope.toDelete = function () {

            alertService.confirm(null, "", "确定删除？", "取消", "确定").then(function (data) {
                if (data) {
                    $http({
                        method: "DELETE",
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/item/" + $scope.id,
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        }
                    }).then(function (resp) {
                        // console.log('resp', resp);
                        alertService.msgAlert("exclamation-circle", "删除成功").then(function () {
                            $scope.fallbackPage()
                        });
                    }, function (resp) {
                        //TODO 错误处理
                    });
                }
            });


        };


        //统一设置
        $scope.openDialog = function (skuList) {

            var skuList = angular.copy(skuList);
            $mdDialog.show({
                templateUrl: 'setSku.html',
                //parent: angular.element(document.body).find('#qh-shop-wap-front'),
                clickOutsideToClose: false,   //true 点击界外的时候弹窗消失
                fullscreen: false,
                // locals: {key: skuList},
                controller: ['$mdDialog', function ($mdDialog) {
                    var vmd = this;
                    vmd.data = {};

                    //取消
                    vmd.cance = function () {
                        $mdDialog.cancel();
                    };
                    vmd.confirm = function () {
                        if (!vmd.data.labelPrice || !vmd.data.salePrice || !vmd.data.buyPrice || !vmd.data.storage) {
                            return;
                        }
                        $mdDialog.hide({data: vmd.data});
                    }
                }],
                controllerAs: "vmd"
            }).then(function (answer) {

                for (var i = 0; i < $scope.skuData.skuList.length; i++) {
                    $scope.skuData.skuList[i].labelPrice = answer.data.labelPrice;
                    $scope.skuData.skuList[i].salePrice = answer.data.salePrice;
                    $scope.skuData.skuList[i].buyPrice = answer.data.buyPrice;
                    $scope.skuData.skuList[i].storage = answer.data.storage;
                }

            }, function () {
                //error
            });

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
    '$state',
    'Upload',
    '$stateParams',
    'alertService',
    '$location',
    '$http',
    '$mdDialog'
];

export default Controller ;
