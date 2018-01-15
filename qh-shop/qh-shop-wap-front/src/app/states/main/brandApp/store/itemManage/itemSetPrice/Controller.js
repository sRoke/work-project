import conf from "../../../../../../conf";


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
        alertService = _alertService;
        $state = _$state;
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
        $scope.choose = $stateParams.choose;
        $scope.from = $stateParams.from;
        $scope.editStatus = $stateParams.editStatus;

        if ($stateParams.skuData) {
            $scope.skuData = JSON.parse($stateParams.skuData);
            // $scope.skuData.categorys = [];
            //
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
        }


        // console.log('$scope.skuData', $scope.skuData);
        // for (var i = 0; i < $scope.skuData.skuList.length; i++) {
        //     $scope.skuData.skuList[i].labelPrice = $scope.skuData.skuList[i].labelPrice;
        //     $scope.skuData.skuList[i].salePrice = $scope.skuData.skuList[i].salePrice;
        //     $scope.skuData.skuList[i].buyPrice = $scope.skuData.skuList[i].buyPrice;
        //     $scope.skuData.skuList[i].storage = $scope.skuData.skuList[i].storage;
        // }

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
        //
        //
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

        $scope.openSku = function (sku) {
            sku.check = !sku.check;
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


        //统一设置
        $scope.openDialog = function (skuList) {

            var skuList = angular.copy(skuList);
            $mdDialog.show({
                templateUrl: 'setSku.html',
                //parent: angular.element(document.body).find('#qh-shop-wap-front'),
                clickOutsideToClose: false,   //true 点击界外的时候弹窗消失
                fullscreen: false,
                // locals: {key: skuList},
                controller: ['$mdDialog', 'alertService', function ($mdDialog, alertService) {
                    var vmd = this;
                    vmd.data = {};

                    //取消
                    vmd.cance = function () {
                        $mdDialog.cancel();
                    };

                    //检验输入框
                    vmd.AddEventInput = function (num) {
                        var reg = /(^[1-9]{1}[0-9]*$)|(^[0-9]+\.[0-9]{0,2}$)|(^[0]$)/;

                        if (num == 1) {
                            if (!reg.test(vmd.data.salePrice)) {
                                alertService.msgAlert("exclamation-circle", '请输入大于0的数');
                                vmd.data.salePrice = '';
                                // console.log("请输入大于0的整数或者保留")
                            } else {
                                // console.log("输入正确");
                            }
                        }

                        if (num == 2) {
                            if (!reg.test(vmd.data.labelPrice)) {
                                vmd.data.labelPrice = '';
                                alertService.msgAlert("exclamation-circle", '请输入大于0的数');
                                // console.log("请输入大于0的整数或者保留")
                            } else {
                                // console.log("输入正确");
                            }
                        }

                        if (num == 3) {
                            if (!reg.test(vmd.data.buyPrice)) {
                                vmd.data.buyPrice = '';
                                alertService.msgAlert("exclamation-circle", '请输入大于0的数');
                                // console.log("请输入大于0的整数或者保留")
                            } else {
                                // console.log("输入正确");
                            }
                        }

                    };

                    vmd.confirm = function () {
                        // !vmd.data.labelPrice || || !vmd.data.buyPrice
                        if (!vmd.data.salePrice || !vmd.data.storage) {
                            return;
                        }
                        vmd.data.labelPrice = vmd.data.labelPrice * 100;
                        vmd.data.salePrice = vmd.data.salePrice * 100;
                        vmd.data.buyPrice = vmd.data.buyPrice * 100;
                        $mdDialog.hide({data: vmd.data});
                    }
                }],
                controllerAs: "vmd"
            }).then(function (answer) {

                // console.log('answer', answer);
                // for (var i = 0; i < $scope.skuData.skus.length; i++) {
                //     $scope.skuData.skus[i].labelPrice = answer.data.labelPrice;
                //     $scope.skuData.skus[i].salePrice = answer.data.salePrice;
                //     $scope.skuData.skus[i].buyPrice = answer.data.buyPrice;
                //     $scope.skuData.skus[i].storage = answer.data.storage;
                // }
                for (var i = 0; i < $scope.skuData.skuList.length; i++) {
                    $scope.skuData.skuList[i].labelPrice = answer.data.labelPrice / 100;
                    $scope.skuData.skuList[i].salePrice = answer.data.salePrice / 100;
                    $scope.skuData.skuList[i].buyPrice = answer.data.buyPrice / 100;
                    $scope.skuData.skuList[i].storage = answer.data.storage;
                }
            }, function () {
                //error
            });

        };

        //检验输入框
        $scope.AddEventInput = function (num,sku, price) {

            // console.log('sku',sku);
            // console.log('price',price);
            var reg = /(^[1-9]{1}[0-9]*$)|(^[0-9]+\.[0-9]{0,2}$)|(^[0]$)/;

            if (num == 1) {
                if (!reg.test(price)) {
                    alertService.msgAlert("exclamation-circle", '请输入大于0的数');
                    sku.salePrice = '';
                    // console.log("请输入大于0的整数或者保留")
                } else {
                    // console.log("输入正确");
                }
            }

            if (num == 2) {
                if (!reg.test(price)) {
                    sku.labelPrice = '';
                    alertService.msgAlert("exclamation-circle", '请输入大于0的数');
                    // console.log("请输入大于0的整数或者保留")
                } else {
                    // console.log("输入正确");
                }
            }

            if (num == 3) {
                if (!reg.test(price)) {
                    sku.buyPrice = '';
                    alertService.msgAlert("exclamation-circle", '请输入大于0的数');
                    // console.log("请输入大于0的整数或者保留")
                } else {
                    // console.log("输入正确");
                }
            }

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
                    editStatus: $scope.editStatus,
                    selectMoreSpec: $stateParams.selectMoreSpec
                })
            }
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
