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
        $scope.getInfo=function(){
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/"+$scope.brandAppId+"/shop/"+$scope.storeId+"/category",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                // console.log(1111111111111,resp.data.data);
                if(resp.data.data.length>0){
                    $scope.noGood=false;
                    $scope.lists=resp.data.data;
                    $scope.subData=[];//定义数组接收删除的子级
                    for(var i=0;i<$scope.lists.length;i++){
                        //$scope.lists[i].moveLeft=false;
                       if($scope.lists[i].parentId){
                           $scope.subData.push($scope.lists[i]);  //将子级保存在数组
                           $scope.lists.splice(i,1);     //删除子级
                           i--;   //i值减1是避免下一位数据没有遍历到;
                       }
                    }
                    // console.log('$scope.lists',$scope.lists)
                    for(var i=0;i<$scope.lists.length;i++){
                        $scope.lists[i].subarr=[];      //定义接收子级的数组
                        for(var j=0;j<$scope.subData.length;j++){
                            if($scope.lists[i].id==$scope.subData[j].parentId){
                                $scope.lists[i].subarr.push($scope.subData[j]);
                            }
                        }
                    }
                    $scope.items=$scope.lists;
                    // console.log(' $scope.items', $scope.items);
                }else{
                    $scope.noGood=true;
                }
            }, function (resp) {
                //error
            });
        };
        $scope.getInfo();



        //外侧检测到$scope.items
        // //左右滑动
        // $scope.onSwipeLeft = function(item) {
        //     for(var i=0;i<$scope.items.length;i++){
        //         $scope.items[i].moveLeft=false;                    //父级
        //         for(var j=0;j<$scope.items[i].subarr.length;j++){
        //             $scope.items[i].subarr[j].moveLeft=false;     //子级
        //         }
        //     }
        //     item.moveLeft=true;
        //     console.log(111);
        //     console.log(' $scope.itemswwwwwwwwwwwww', $scope.items)
        // };
        // $scope.onSwipeRight = function(item) {
        //     for(var i=0;i<$scope.items.length;i++){
        //         $scope.items[i].moveLeft=false;
        //         for(var j=0;j<$scope.items[i].subarr.length;j++){
        //             $scope.items[i].subarr[j].moveLeft=false;
        //         }
        //     }
        //     console.log(222);
        // };




        //新增
        $scope.add=function (id) {
            // console.log('id',id)//   id即为父级id
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
                        $http({
                            method: "POST",
                            url: conf.apiPath + "/brandApp/"+$scope.brandAppId+"/shop/"+$scope.storeId+"/category",
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            },
                            data:{
                                enable:true,
                                name:vmd.name,
                                order:vmd.number,
                                parentId:id,
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
        $scope.edit=function(id,parentId){
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
                    //先获取当前信息
                    $http({
                        method: "GET",
                        url: conf.apiPath + "/brandApp/"+$scope.brandAppId+"/shop/"+$scope.storeId+"/category/"+id,
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        }
                    }).then(function (resp) {
                        vmd.name=resp.data.data.name;
                        vmd.number=resp.data.data.order;
                        //获取名字   序号

                    }, function (resp) {
                        //error
                    });
                    //取消
                    vmd.cance=function(){
                        $mdDialog.cancel();
                    };
                    vmd.confirm=function () {
                        if(!vmd.name){
                            vmd.noName=true;
                            return;
                        }
                        $http({
                            method: "PUT",
                            url: conf.apiPath + "/brandApp/"+$scope.brandAppId+"/shop/"+$scope.storeId+"/category/"+id,
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            },
                            data:{
                                enable:true,
                                name:vmd.name,
                                order:vmd.number,
                                parentId:parentId,
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


        $scope.delte = function (id) {
            alertService.confirm(null, "", "删除分类？", "取消", "确定").then(function (data) {
                if (data) {
                    $http({
                        method: "DELETE",
                        url: conf.apiPath + "/brandApp/"+$scope.brandAppId+"/shop/"+$scope.storeId+"/category/"+id,
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        },
                    }).then(function (resp) {
                        // console.log('delte',resp);
                        if(resp.data.status=='200'){
                            $scope.getInfo();
                        }else{
                            alertService.msgAlert("exclamation-circle", resp.data.data);
                            $scope.getInfo();
                        };
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
