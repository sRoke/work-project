import conf from "../../../../conf";
import moment from "moment";
var $scope,
    $http,
    authService,
    $state,
    $log,
    loginService,
    $mdDialog,
    alertService,
    errorService,
    $stateParams;
class Controller {
    constructor(_$scope,
                _$http,
                _$state,
                _$log,
                _authService,
                _loginService,
                _$mdDialog,
                _alertService,
                _errorService,
                _$stateParams) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        authService = _authService;
        loginService = _loginService;
        $log = _$log;
        alertService = _alertService;
        errorService = _errorService;
        $stateParams = _$stateParams;
        /////////////////////////////////
        loginService.loginCtl(true);
        $scope.brandAppId = $stateParams.brandAppId;
        //获取权限，勿删
        // authService.setAuthorities($scope.brandAppId);
        // console.log("====================", $stateParams);
        $scope.id = $state.params.id;
        $scope.isSave = false;   //定义初始化编辑保存的显示
        $scope.save = true;
        // if($scope.isSave=false){
        //     $scope.save=true;
        // };
        $scope.changeStatus = function () {
            $scope.isSave = !$scope.isSave;
        };
        // 不选定上级渠道，禁止审核
        $scope.parentSelect = true;
        //rmy   初始化 info
        $scope.pageChanged = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/" + $scope.id,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
            }).success(function (response) {
                //console.log('112311', response);
                $scope.data = response.data;
                $scope.adc = $scope.data.adc;
                $scope.parId = $scope.data.parentId;     //上级服务商ID
                $scope.parentInfo = response.data;
                $scope.brand = $scope.data.parentId;    //上级服务商ID

                $scope.userId = $scope.data.userId;    //

                if ($scope.userId) {
                    $scope.getShopList()
                }
                console.log(' $scope.userId', $scope.userId)

                //初始化地址
                if (response.data.provinceNo == undefined) {
                    //台湾
                    if (response.data.cityNo == undefined) {
                        $scope.address.provinceNo = response.data.adc;
                    } else {
                        //直辖市
                        $scope.address.provinceNo = response.data.cityNo;
                        $scope.getAddress($scope.address.provinceNo, 1);
                        $scope.address.cityNo = response.data.adc;
                    }
                } else {
                    //三级省市区
                    $scope.address.provinceNo = response.data.provinceNo;
                    $scope.getAddress($scope.address.provinceNo, 1);
                    $scope.address.cityNo = response.data.cityNo;
                    $scope.getAddress($scope.address.cityNo, 2);
                    $scope.address.countyNo = response.data.adc;
                }


                //判断是否存在上级Id
                if ($scope.parId) {
                    $scope.parentSelect = false;
                }
                //初始化时，如果存在上级渠道商id,根据id查询上级渠道商信息
                // if ($scope.parId) {
                //     $scope.parentSelect=false;
                //     $http({
                //         method: "GET",
                //         url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/" + $scope.data.parentId,
                //         headers: {
                //             'Authorization': 'Bearer ' + loginService.getAccessToken(),
                //             "brandApp-Id": $scope.brandAppId
                //         },
                //     }).success(function (response) {
                //         console.log('parentID', response.data);
                //        // $scope.parentInfo = response.data;
                //
                //     });
                // }
            });
        };
        $scope.pageChanged();
        $scope.getUserInfo = function () {
            $http({
                method: "get",
                url: conf.oauthPath + "/api/user/info",
                // url: 'http://user/info',
                data: {},
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    'brandApp-Id': $stateParams.brandAppId ? $stateParams.brandAppId : '567b614be4b0f72f9f6cf02e',
                }
            }).then(function (data) {
                    $scope.userPhone = data.data.data;

                    console.log('$scope.userPhone', $scope.userPhone);
                }, function () {

                }
            );
        };
        $scope.getUserInfo();
        $scope.getShopList = function () {
            // /brandApp/{brandAppId}/shop/getShopList
            $http({
                method: "GET",
                url: conf.shopPath + "/brandApp/" + $scope.brandAppId + "/shop/getShopList",
                params: {
                    userId: $scope.userId
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
            }).success(function (response) {

                console.log('response', response);
                if (response.status == "200") {
                    //判断成功后执行

                    $scope.shopList = response.data;

                } else {
                    //保存失败执行
                    return errorService.error(response.data, null)
                }
            });
        };


        $scope.shop = {};
        // $scope.test = function () {
        //     console.log('$scope.shop.selectData', $scope.shop.selectData);
        // };
        //
        // $scope.selectShop = function (result) {
        //     console.log(result);
        //     $scope.search.shopwords = result.name;
        //
        //     console.log('$scope.search.shopwords', $scope.shopwords);
        //
        // };


        $scope.getShop = function () {

            ///brandCom/getBrandAppList  brandAppId

            $http({
                method: "GET",
                url: conf.pfApiPath + "/brandCom/getBrandAppList",
                params: {
                    brandAppId: $scope.brandAppId
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
            }).success(function (response) {

                console.log('response', response);
                if (response.status == "200") {
                    //判断成功后执行

                    $scope.shopData = response.data;

                } else {
                    //保存失败执行
                    return errorService.error(response.data, null)
                }
            });

        };

        $scope.getShop();

        $scope.getShopList = function () {
            // /brandApp/{brandAppId}/shop/getShopList
            $http({
                method: "GET",
                url: conf.shopPath + "/brandApp/" + $scope.brandAppId + "/shop/getShopList",
                params: {
                    userId: $scope.userId
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
            }).success(function (response) {

                console.log('response', response);
                if (response.status == "200") {
                    //判断成功后执行

                    $scope.shopList = response.data;

                } else {
                    //保存失败执行
                    return errorService.error(response.data, null)
                }
            });
        };


        $scope.shop = {};
        // $scope.test = function () {
        //     console.log('$scope.shop.selectData', $scope.shop.selectData);
        // };
        //
        // $scope.selectShop = function (result) {
        //     console.log(result);
        //     $scope.search.shopwords = result.name;
        //
        //     console.log('$scope.search.shopwords', $scope.shopwords);
        //
        // };


        $scope.getShop = function () {

            ///brandCom/getBrandAppList  brandAppId

            $http({
                method: "GET",
                url: conf.pfApiPath + "/brandCom/getBrandAppList",
                params: {
                    brandAppId: $scope.brandAppId
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
            }).success(function (response) {

                console.log('response', response);
                if (response.status == "200") {
                    //判断成功后执行

                    $scope.shopData = response.data;

                } else {
                    //保存失败执行
                    return errorService.error(response.data, null)
                }
            });

        };

        $scope.getShop();


        $scope.search = {};
        // 查询上级渠道商       07-10
        $scope.selectedPartner = function () {
            console.log(111111111111, $scope.search.keywords);
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner",
                params: {
                    keyWord: $scope.search.keywords,
                    page: 0,
                    size: conf.pageSize,
                    source: 'PASS',
                    //根据输入框模糊搜索
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
            }).success(function (data) {
                //console.log('test', data);
                console.log(111111111111, $scope.search.keywords);
                $scope.items = data.data.content;
            });
        };
        //修改地址
        $scope.address = [];
        var addr = null;
        $scope.getAddress = function (type, level) {
            //限制二级地区时，三级地区码为上一次的情况，进行清空;
            $scope.address.countyNo = undefined;
            $http({
                method: "GET",
                url: conf.apiPath + "/common/queryAdc",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
                params: {
                    adc: type,
                },
            }).then(function successCallback(response) {
                // console.log("540000",response);
                if (response.status === 200) {
                    if (level === undefined) {
                        $scope.provinces = response.data.data;
                        $scope.citys = null;
                        $scope.countys = null;
                    } else if (level === 1) {
                        $scope.citys = response.data.data;
                        $scope.countys = null;
                    } else if (level === 2) {
                        $scope.countys = response.data.data;
                    }
                }
            }, function errorCallback(response) {
            });
        };
        $scope.getAddress();


        $scope.selectActivityBrand = function (result) {
            console.log(result);
            $scope.brand = result.id;
            //console.log(3333,$scope.brand);
            if ($scope.brand) {
                $scope.parentSelect = false;      //选中上级
            }
            ;
            if (result.shopAddr) {
                console.log('1----shopAddr', result.shopAddr);
                $scope.search.keywords = result.realName + result.phone + result.partnerType + result.shopAddr;
            } else {
                console.log('2---shopAddr', result.shopAddr);
                $scope.search.keywords = result.realName + result.phone + result.partnerType;
            }

            // $http({
            //     method: 'post',
            //     url: appConfig.apiPath + "/activity/checkActivityBrand",
            //     data: $scope.temp,
            //     headers: {'X-Requested-With': 'XMLHttpRequest'}
            // }).success(function (data) {
            //     if (data.code === "SUCCESS") {
            //         $scope.activity.brandId = result.id;
            //         vm.search.keywords.activityBrand = result.nameCN;
            //     }
            // });
        };

        $scope.removeActivityBrand = function () {
            console.log('2222222222')

            $scope.search.keywords = "";
            $scope.brand = null;
            $scope.parentSelect = true;       //删除     状态为未选择上级服务商
        };
        $scope.save = function () {
            console.log(22222, $scope.brand);
            if ($scope.brand == undefined) {
                if ($scope.parId == undefined) {
                    return errorService.error('请选择上级渠道商', null);
                } else {
                    $scope.brand = $scope.parId;
                }
            }
            ;
            console.log(11111111111111, $scope.brand);
            alertService.confirm(null, "确定保存修改的信息？", "温馨提示", "取消", "确认")
                .then(function (data) {
                    if (data) {
                        //判断最后的地址;
                        //console.log('aaaaaaaaaaaaaaaaaa', $scope.address.provinceNo,$scope.address.cityNo,$scope.address.countyNo);
                        if ($scope.address.provinceNo === undefined) {
                            addr = $scope.adc;
                            //  console.log('啥情况')
                        } else if ($scope.address.provinceNo == "710000") {
                            //台湾
                            addr = $scope.address.provinceNo;
                            //console.log('直接保存taiwan')
                        } else if ($scope.citys.list.length == 0) {
                            addr = $scope.address.provinceNo;
                        } else if ($scope.address.countyNo == undefined) {
                            addr = $scope.address.cityNo;
                        }
                        else {
                            //三级省市县
                            if ($scope.countys.list.length > 0) {
                                addr = $scope.address.countyNo;
                                console.log(1, addr);
                            } else if ($scope.citys.list.length > 0) {
                                addr = $scope.address.cityNo;
                                console.log(2, addr);
                            } else {
                                addr = $scope.address.provinceNo;
                                console.log(3, addr);
                            }
                        }
                        ;
                        //console.log(55555555555555,addr);
                        //console.log("brand",$scope.brand);
                        $http({
                            method: "PUT",
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/" + $scope.id,
                            data: {
                                realName: $scope.data.realName,
                                phone: $scope.data.phone,
                                idNo: $scope.data.idNo,
                                parentId: $scope.brand,
                                partnerType: $scope.data.partnerTypeEnum,
                                shopAddr: addr,
                                invitationCode: $scope.data.invitationCode
                            },
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            },
                        }).success(function (response) {
                            if (response.status == "200") {
                                //判断成功后执行
                                $scope.pageChanged();
                                $scope.changeStatus();
                            } else {
                                //保存失败执行
                                return errorService.error(response.data, null)
                            }
                        });
                    }
                });

        };


        //审核
        $scope.checkStatus = function (st, name) {
            //console.log($scope.id);


            console.log('$scope.shop', $scope.shop);


            $scope.checkApply = function () {

                alertService.confirm(null, "确定" + name + "该用户？", "温馨提示", "取消", "确认")
                    .then(function (data) {
                        if (data) {
                            $http({
                                method: "PUT",
                                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/" + $scope.id + "/review",
                                params: {
                                    status: st,
                                    parentId: $scope.brand,
                                    shopBrandAppId: $scope.shop.selectBrandAppId,
                                    shopId: $scope.shop.selectShopId
                                },
                                headers: {
                                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                    "brandApp-Id": $scope.brandAppId
                                },
                            }).success(function (data) {
                                //console.log(data);
                                if (data.status == 200) {
                                    $state.go("main.brandApp.partnerApply", null, {reload: true});
                                }
                            });
                        }
                    });
            };
            //   思路       审核时：如果为拒绝申请，即st=false，直接$scope.checkApply();
            //如果同意申请  在判断    $scope.parentSelect是否为true，$scope.parentSelect初始化为true，即未选择服务商

            if ($scope.shop.selectBrandAppId && $scope.shop.selectShopId) {  //有两个id择去审核通过

                console.log(1);
                console.log('$scope.shop1', $scope.shop);

                if (st == false) {
                    $scope.checkApply();
                } else {
                    if ($scope.parentSelect) {
                        return errorService.error("请选择上级服务商", null)
                    } else {
                        $scope.checkApply();
                    }
                    ;
                }
            } else if ($scope.shop.selectBrandAppId && !$scope.shop.selectShopId) {  //有brand,没有门店则注册门店
                console.log(2);
                console.log('$scope.shop2', $scope.shop);

                $http({
                    method: "POST",
                    url: conf.shopPath + "/brandApp/" + $scope.shop.selectBrandAppId + "/shop",
                    data: {
                        phone: $scope.data.phone,
                        name: $scope.data.realName,
                        adcNo: $scope.data.adc,
                        address: $scope.data.shopAddr,
                        img: '',
                        tel: $scope.data.phone,
                        shopType: 'COOPERATE'
                    },
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    },
                }).success(function (data) {
                    console.log(data.data);
                    if (data.status == 200) {
                        $scope.shop.selectShopId = data.data;
                        if (st == false) {
                            $scope.checkApply();
                        } else {
                            if ($scope.parentSelect) {
                                return errorService.error("请选择上级服务商", null)
                            } else {
                                $scope.checkApply();
                            }
                            ;
                        }
                    }

                });

            }
            else {
                console.log('$scope.shop3', $scope.shop);

                if (st == false) {
                    $scope.checkApply();
                    if ($scope.shop.selectBrandAppId && $scope.shop.selectShopId) {  //有两个id择去审核通过

                        console.log(1);
                        console.log('$scope.shop1', $scope.shop);

                        if (st == false) {
                            $scope.checkApply();
                        } else {
                            if ($scope.parentSelect) {
                                return errorService.error("请选择上级服务商", null)
                            } else {
                                $scope.checkApply();
                            }
                            ;
                        }
                    } else if ($scope.shop.selectBrandAppId && !$scope.shop.selectShopId) {  //有brand,没有门店则注册门店
                        console.log(2);
                        console.log('$scope.shop2', $scope.shop);

                        $http({
                            method: "POST",
                            url: conf.shopPath + "/brandApp/" + $scope.shop.selectBrandAppId + "/shop",
                            data: {
                                phone: $scope.data.phone,
                                name: $scope.data.realName,
                                adcNo: $scope.data.adc,
                                address: $scope.data.shopAddr,
                                img: '',
                                tel: $scope.data.phone,
                                shopType: 'COOPERATE'
                            },
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            },
                        }).success(function (data) {
                            console.log(data.data);
                            if (data.status == 200) {
                                $scope.shop.selectShopId = data.data;
                                if (st == false) {
                                    $scope.checkApply();
                                } else {
                                    if ($scope.parentSelect) {
                                        return errorService.error("请选择上级服务商", null)
                                    } else {
                                        $scope.checkApply();
                                    }
                                    ;
                                }
                            }

                        });

                    }
                    else {
                        console.log('$scope.shop3', $scope.shop);

                        if (st == false) {
                            $scope.checkApply();
                        } else {
                            if ($scope.parentSelect) {
                                return errorService.error("请选择上级服务商", null)
                            } else {
                                $scope.checkApply();
                            }
                            ;
                        }
                    }


                };
                // $scope.specsListResult =[] 存储模糊搜索结果作为下拉框展示数据
                // $scope.selectedSpecsAttr = function () {
                //     $http.get(conf.apiPath + "/itemProp/itemPropList", {
                //         params: {
                //             itemPropKeyword: $scope.specsKeyword.key,
                //         },
                //         headers: {
                //             'Authorization': 'Bearer ' + loginService.getAccessToken(),
                //             "brandApp-Id": $scope.brandAppId
                //         },
                //     }).success(function (data) {
                //         $scope.specsListResult = data.data;
                //     });
                // };
                //
                // //选择上级渠道
                // $scope.selectSpecsLIst = function (staff) {
                //     $scope.specsKeyword.key = staff.name;
                //     $scope.specsKeyID = staff.id;
                // };


            }
        }


    }
}

Controller.$inject = [
    '$scope',
    '$http',
    '$state',
    '$log',
    'authService',
    'loginService',
    '$mdDialog',
    'alertService',
    'errorService',
    '$stateParams'
];

export default Controller;

