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
        $mdDialog=_$mdDialog;
        loginService = _loginService;
        $state = _$state;
        alertService=_alertService;
        $stateParams = _$stateParams;
        $location =_$location;
        $http = _$http;
        $scope.brandAppId = $stateParams.brandAppId;
        $scope.storeId= $stateParams.storeId;
        loginService.loginCtl(true,$location.absUrl());
        $scope.go = function (state) {
            $state.go(state);
        };
        $scope.getInfo=function(){
            $http({
                method: "GET",  ///brandApp/{brandAppId}/shop/{shopId}/itemProp/itemPropList
                url: conf.apiPath + "/brandApp/"+$scope.brandAppId+"/shop/"+$scope.storeId+"/itemProp/itemPropList",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                // console.log(1111111111111,resp);
                if(resp.data.data==null){
                    $scope.noGood=true;
                }else{
                    $scope.noGood=false;
                    $scope.items=resp.data.data;
                }
            }, function (resp) {
                //error
            });
        };
        $scope.getInfo();
        // // //左右滑动
        // $scope.onSwipeLeft = function(item) {
        //     for(var i=0;i<$scope.items.length;i++){
        //         $scope.items[i].moveLeft=false;                    //父级
        //         for(var j=0;j<$scope.items[i].propValues.length;j++){
        //             $scope.items[i].propValues[j].moveLeft=false;     //子级
        //         }
        //     }
        //     item.moveLeft=true;
        // };
        // $scope.onSwipeRight = function(item) {
        //     for(var i=0;i<$scope.items.length;i++){
        //         $scope.items[i].moveLeft=false;
        //         for(var j=0;j<$scope.items[i].propValues.length;j++){
        //             $scope.items[i].propValues[j].moveLeft=false;
        //         }
        //     }
        // };
        //新增
        $scope.add=function (id) {
            $mdDialog.show({
                templateUrl: 'addGood.html',
                //parent: angular.element(document.body).find('#qh-shop-wap-front'),
                clickOutsideToClose: false,   //true 点击界外的时候弹窗消失
                fullscreen:false,
               // locals: {key: $scope.info},
                controller: ['$mdDialog', function ($mdDialog) {
                    var vmd = this;
                    vmd.codeShow=false;
                    vmd.noName=false;
                    //验证名字
                    vmd.check=function (name) {
                        if(name){
                            vmd.noName=false;
                        }else{
                            vmd.noName=true;
                        }
                    };
                    //取消
                    vmd.cance=function(){
                        $mdDialog.cancel();
                    };
                    vmd.confirm=function () {
                        if(!vmd.name){
                            vmd.noName=true;
                            return;
                        }
                        if(id){   //子级
                            $scope.url= conf.apiPath + "/brandApp/"+$scope.brandAppId+"/shop/"+$scope.storeId+"/itemProp/"+id+"/propValue"
                        }else{   //父级
                            $scope.url= conf.apiPath + "/brandApp/"+$scope.brandAppId+"/shop/"+$scope.storeId+"/itemProp";
                        };
                        $http({
                            method: "POST",
                            url: $scope.url,
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            },
                            params:{
                                name:vmd.name,
                            },
                        }).then(function (resp) {
                            if(resp.data.status=='200'){
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
        $scope.edit=function(id,name,equal){
            $mdDialog.show({
                templateUrl: 'addGood.html',
                //parent: angular.element(document.body).find('#qh-shop-wap-front'),
                clickOutsideToClose: false,   //true 点击界外的时候弹窗消失
                fullscreen:false,
                // locals: {key: $scope.info},
                controller: ['$mdDialog', function ($mdDialog) {
                    var vmd = this;
                    vmd.codeShow=false;
                    vmd.name=name;
                    vmd.noName=false;
                    //验证名字
                    vmd.check=function (name) {
                        if(name){
                            vmd.noName=false;
                        }else{
                            vmd.noName=true;
                        }
                    };
                    //取消
                    vmd.cance=function(){
                        $mdDialog.cancel();
                    };
                    vmd.confirm=function () {
                        if(!vmd.name){
                            vmd.noName=true;
                            return;
                        }
                        if(equal=='parent'){
                            $scope.url=conf.apiPath + "/brandApp/"+$scope.brandAppId+"/shop/"+$scope.storeId+"/itemProp/"+id;
                        }else{
                            $scope.url=conf.apiPath + "/brandApp/"+$scope.brandAppId+"/shop/"+$scope.storeId+"/itemProp/"+id+"/propValue";
                        }
                        $http({
                            method: "PUT",
                            url: $scope.url,
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            },
                            params:{
                                name:vmd.name,
                            },
                        }).then(function (resp) {
                            // console.log(4422221111112222,resp.data.data );
                            if(resp.data.status=='200'){
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
        $scope.delte= function (id,name) {
            if(name=='parent'){
                $scope.url=conf.apiPath + "/brandApp/"+$scope.brandAppId+"/shop/"+$scope.storeId+"/itemProp/"+id;
            }else{
                $scope.url=conf.apiPath + "/brandApp/"+$scope.brandAppId+"/shop/"+$scope.storeId+"/itemProp/"+id+"/propValue";
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
                        if(resp.data.status=='200'){
                            $scope.getInfo();
                        }
                    }, function (resp) {

                    });
                }
            });

        };
        /*返回上级*/
        $scope.fallbackPage = function () {
            // if (history.length === 1) {
                $state.go("main.brandApp.store.shop.shopManage", {brandAppId:$stateParams.brandAppId}, {reload: true});
            // } else {
            //     history.back();
            // }
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
