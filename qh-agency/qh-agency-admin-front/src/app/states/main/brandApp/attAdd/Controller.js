/**
 * Created by susf on 17-3-29.
 */
import conf from "../../../../conf";

var $scope,
    $http,
    loginService,
    $state,
    errorService,
    $stateParams;


class Controller {
    constructor(_$scope, _$http,_loginService,_$state,_errorService,_$stateParams) {
        $scope = _$scope;
        $http = _$http;
        loginService=_loginService;
        $state = _$state;
        errorService = _errorService;
        $stateParams = _$stateParams;

        var vm = this;

        $scope.attrData = {};
        $scope.flog = false;
        $scope.attrData.valueGroups=[];
        vm.valueGroup = '';
        $scope.valGroup = {};
        $scope.id = $stateParams.id?$stateParams.id:null;
        $scope.source = $stateParams.about?$stateParams.about:null;
        loginService.loginCtl(true);
        $scope.brandAppId = $stateParams.brandAppId;
        // 返回按钮
        $scope.fallbackPage =function () {
            if (history.length === 1) {
                $state.go("main.brandApp.index", null, {reload: true});
            } else {
                history.back();
            }
        };


        // $scope.ngChange =function () {
        //     $scope.attrData.showName=$scope.attrData.showName?$scope.attrData.showName:$scope.attrData.name;
        // };




        // 属性值添加----属性值添加
        $scope.addValueGroup = function () {
            console.log($scope.attrData.valueGroups);
            $scope.valGroup = angular.copy($scope.valGroup);
            $scope.valGroup.name= vm.valueGroup ;
            $scope.attrData.valueGroups.push($scope.valGroup);
            vm.valueGroup = '';
        };
        //属性值添加--属性值删除
        $scope.delValueGroup = function(index){
            $scope.attrData.valueGroups.splice(index, 1);
        };




        //添加,  stateParams.id不存在为新添加界面
        if (!$stateParams.id) {
            $scope.title = "添加商品属性";
            //添加
            $scope.save = function () {
                if (!$scope.attrData.name) {
                    return errorService.error("属性名称不能为空！", null)
                }
                if (!$scope.attrData.showName) {
                    return errorService.error("显示名称不能为空！", null)
                }
                // if ($scope.attrData.valueGroups.length<1) {
                //     return errorService.error("属性值至少添加一个！", null)
                // }

                $http({
                    method: "POST",
                    url:conf.apiPath + "/brandApp/"+$scope.brandAppId+"/itemProp",
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    },
                    data: {
                        name: $scope.attrData.name,
                        memName: $scope.attrData.showName,
                        itemPropValues:$scope.attrData.valueGroups,
                        memo: $scope.attrData.desp,
                    },
                }).then(function successCallback(response) {
                    if (response.status == 200) {
                        $scope.fallbackPage()
                    }
                }, function errorCallback(response) {
                    // 请求失败执行代码
                });
            }
        }


            // 编辑stateParams.id存在为编辑或者产看界面
        if ($stateParams.id) {
            $scope.title = "编辑商品属性";

            //判断是否为查看页面
            if($stateParams.about == 'view'){
                $scope.title = "查看商品属性";
                $scope.flog =true;                               //禁用输入框
                //$scope.save = $scope.fallbackPage;
                $http({
                    method: "get",
                    url:conf.apiPath + "/brandApp/"+$scope.brandAppId+"/itemProp/"+$stateParams.id,
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    },
                }).then(function successCallback(response) {
                    if (response.status == 200) {
                        $scope.attrData.id = response.data.data.id;
                        $scope.attrData.name = response.data.data.name;
                        $scope.attrData.valueGroups =response.data.data.propValues;
                        $scope.attrData.showName =response.data.data.memName;
                        $scope.attrData.desp=response.data.data.memo;
                    }
                }, function errorCallback(response) {
                    // 请求失败执行代码
                });
            }else{
                //编辑查看界面属性展示
                $http({
                    method: "get",
                    url:conf.apiPath + "/brandApp/"+$scope.brandAppId+"/itemProp/"+$stateParams.id,
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    },
                }).then(function successCallback(response) {
                    if (response.status == 200) {
                        console.log(response.data);
                        $scope.attrData.id = response.data.data.id;
                        $scope.attrData.name = response.data.data.name;
                        $scope.attrData.valueGroups =response.data.data.propValues;
                        $scope.attrData.showName =response.data.data.memName;
                        $scope.attrData.desp=response.data.data.memo;
                    }
                }, function errorCallback(response) {
                    // 请求失败执行代码
                });
            }

            //保存按钮
            $scope.save = function () {

                if (!$scope.attrData.name) {
                    return errorService.error("属性名称不能为空！", null)
                }
                if (!$scope.attrData.showName) {
                    return errorService.error("显示名称不能为空！", null)
                }
                // if ($scope.attrData.valueGroups.length<1) {
                //     return errorService.error("属性值至少添加一个！", null)
                // }

                //添加
                $http({
                    method: "PUT",
                    url: conf.apiPath +"/brandApp/"+$scope.brandAppId+"/itemProp/"+$scope.attrData.id,
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    },
                    data: {
                        // id:$scope.attrData.id,
                        name: $scope.attrData.name,
                        memName: $scope.attrData.showName,
                        itemPropValues:$scope.attrData.valueGroups,
                        memo: $scope.attrData.desp,
                    }
                }).then(function successCallback(response) {
                    if (response.status == 200) {
                        $scope.fallbackPage()
                    }
                }, function errorCallback(response) {
                    // 请求失败执行代码
                });

                $scope.attrData={};

            }
        }


    }
}

Controller.$inject = [
    '$scope', '$http','loginService','$state', 'errorService','$stateParams',
];

export default Controller ;
