import conf from "../../../../../../conf";


var $scope,
    $stateParams,
    $httpParamSerializer,
    alertService,
    $http,
    loginService,
    $state;
class Controller {
    constructor(_$scope,
                _$stateParams,
                _$httpParamSerializer,
                _alertService,
                _$http,
                _loginService,
                _$state) {
        $scope = _$scope;
        $state = _$state;
        $http = _$http;
        loginService = _loginService;
        $stateParams = _$stateParams;
        $httpParamSerializer = _$httpParamSerializer;
        alertService = _alertService;
        $scope.brandAppId = $stateParams.brandAppId;
        $scope.id = $stateParams.id;
        loginService.loginCtl(true);
        ///////////////////////////////////////
        $scope.dataInfo = {};
        $scope.selectOne = {};
        $scope.selectTwo = {};

        $scope.clickThis = function (key,value) {
            $scope.selectOne.key = key;
            $scope.selectOne.value = value;
        };

        $scope.showTab2 = []; //控制收起展开的数组
        $scope.checkedList = []; //控制选中的数组
        $scope.toggle = function (item, list) {
            var idx = list.indexOf(item);
            if (idx > -1) {
                list.splice(idx, 1);
            }
            else {
                list.push(item);
            }
        };
        $scope.exists = function (item, list) {
            return list.indexOf(item) > -1;
        };
        $scope.toggleTotal = function (item, list) {
            if($scope.existsTotal(item, list)){
                for (var key in item){
                    var idx = list.indexOf(key);
                    if (idx > -1) {
                        list.splice(idx, 1);
                    }
                }
            }else {
                for (var key in item){
                    var idx = list.indexOf(key);
                    if (idx == -1) {
                        list.push(key);
                    }
                }
            }
        };
        $scope.existsTotal = function (item, list) {
            for (var key in item){
                if(list.indexOf(key) == -1){
                    return false
                }
            }
            return true;
        };
        // -------------------------------------------------------获取权限列表
        $http({
            method: "GET",
            url: conf.apiPath + "/brandApp/" + $stateParams.brandAppId + '/shop/' + $stateParams.storeId + '/shopStaffGroup/load',
            params: {},
            headers: {
                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                "brandApp-Id": $scope.brandAppId
            }
        }).then(function (resp) {

            $scope.AuthorList = resp.data.data.authorMap;
            for(var key in $scope.AuthorList){
                $scope.selectOne.key = key;
                $scope.selectOne.value = $scope.AuthorList[key];
                break;
             }
            // console.log('resp-----------------------', resp);
        }, function (error) {

        });


        if($scope.id){ //id存在获取角色详情
            $scope.pageTitle = '编辑角色';
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $stateParams.brandAppId + '/shop/' + $stateParams.storeId + '/shopStaffGroup/'+$stateParams.id,
                params: {},
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $scope.dataInfo = resp.data.data;
                $scope.checkedList = resp.data.data.author;
            }, function (error) {

            });
        }else {
            $scope.pageTitle = '新增角色';
        }







        $scope.clickedSave = false;
        $scope.save = function () {
            if($scope.clickedSave){
                return
            }else {
                $scope.clickedSave = true;
            }
            if(!$scope.dataInfo.name){
                alertService.msgAlert('ag-exclamation-circle','角色名称不能为空!');
                $scope.clickedSave = false;
                return;
            }

            if(!$scope.dataInfo.desp){
                alertService.msgAlert('ag-exclamation-circle','备注不能为空!');
                $scope.clickedSave = false;
                return;
            }


            if($scope.checkedList.length<1){
                alertService.msgAlert('ag-exclamation-circle','权限至少选择一项!');
                $scope.clickedSave = false;
                return;
            }

            if($scope.id){
                $http({
                    method: "PUT",
                    url: conf.apiPath + "/brandApp/" + $stateParams.brandAppId + '/shop/' + $stateParams.storeId + '/shopStaffGroup/'+$stateParams.id,
                    data: {
                        name:$scope.dataInfo.name,
                        desp:$scope.dataInfo.desp,
                        authorMap:$scope.checkedList,
                    },
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    }
                }).then(function (resp) {
                    // console.log('resp-----------------------', resp);
                    $scope.fallbackPage()
                    $scope.clickedSave = false;
                }, function (error) {
                    $scope.clickedSave = false;
                    alertService.msgAlert('ag-exclamation-circle',error.data.message);
                });
            }else {
                $http({
                    method: "POST",
                    url: conf.apiPath + "/brandApp/" + $stateParams.brandAppId + '/shop/' + $stateParams.storeId + '/shopStaffGroup',
                    data: {
                        name:$scope.dataInfo.name,
                        desp:$scope.dataInfo.desp,
                        authorMap:$scope.checkedList,
                    },
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    }
                }).then(function (resp) {
                    $scope.clickedSave = false;
                    $scope.fallbackPage();
                    // console.log('resp-----------------------', resp);
                }, function (error) {
                    $scope.clickedSave = false;
                });
            }
        };





        $scope.del = function () {



            alertService.del('确定删除此角色?').then(function (data) {
             if(data){
                 $http({
                     method: "DELETE",
                     url: conf.apiPath + "/brandApp/" + $stateParams.brandAppId + '/shop/' + $stateParams.storeId + '/shopStaffGroup/'+$stateParams.id,
                     data: {},
                     headers: {
                         'Authorization': 'Bearer ' + loginService.getAccessToken(),
                         "brandApp-Id": $scope.brandAppId
                     }
                 }).then(function (resp) {
                     // console.log('resp-----------------------', resp);
                     $scope.clickedSave = false;
                     // $scope.fallbackPage();
                 }, function (error) {
                     $scope.clickedSave = false;
                     alertService.msgAlert('ag-exclamation-circle',error.data.message);
                 });
             }else {
                 $scope.clickedSave = false;
             }
            })
        }












        /*返回上级*/
        $scope.fallbackPage = function () {
            $state.go("main.brandApp.store.shop.shopRoles", null, {reload: true});
        };
    }


}

Controller.$inject = [
    '$scope',
    '$stateParams',
    '$httpParamSerializer',
    'alertService',
    '$http',
    'loginService',
    '$state'
];

export default Controller ;
