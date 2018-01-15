import conf from "../../../../../conf";

var $scope,
    alertService,
    $mdDialog,
    $http,
    loginService,
    $stateParams,
    $state;
class Controller {
    constructor(_$scope, _alertService, _$mdDialog, _$http, _loginService, _$stateParams, _$state) {
        $scope = _$scope;
        alertService= _alertService;
        $mdDialog = _$mdDialog;
        $http = _$http;
        loginService = _loginService;
        $stateParams = _$stateParams;
        $state =_$state;

        $scope.addressList = [{
            updateDefault:true,
        },{
            updateDefault:false,
        }];




        // 设为默认地址
        $scope.setDefault = function (addressList, index) {
            //这里为什么要传addressList参数 这个参数你可以理解成我们当前点击的这个addressList!
            addressList.updateDefault = !addressList.updateDefault;

            // $http({
            //     method: "GET",
            //     url: conf.apiPath + '/addr/setDefault',
            //     params: {
            //         id: addressList.id
            //     },
            //     headers: {
            //         'Authorization': 'Bearer ' + loginService.getAccessToken(),
            //         "brandApp-Id": $scope.brandAppId
            //     }
            // }).then(function (resp) {
            //         $scope.list.recList[index].isDefault = true;
            //         $scope.getList();
            //     }, function () {
            //
            //     }
            // );
        };

        /*
         * 删除地址
         * */
        $scope.deleteAddress = function (addressList, index) {


            //这里为什么要传addressList参数 这个参数你可以理解成我们当前点击的这个addressList!
            alertService.confirm(null, "", "是否确认删除该地址", "取消", "确定").then(function (data) {
                if (data) {
                    $scope.addressList.splice(index,1);
                    // $http({
                    //     method: "GET",
                    //     url: conf.apiPath + '/addr/delete',
                    //     params: {
                    //         id: addressList.id
                    //     },
                    //     headers: {
                    //         'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    //         "brandApp-Id": $scope.brandAppId
                    //     }
                    // }).then(function (resp) {
                    //         $scope.getList();
                    //     }, function () {
                    //
                    //     }
                    // );


                }
            });

        };



        /*返回上级*/
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.center.main", null, {reload: true});
            } else {
                history.back();
            }
        };



    }
}

Controller.$inject = [
    '$scope',
    'alertService',
    '$mdDialog',
    '$http',
    'loginService',
    '$stateParams',
    '$state'
];

export default Controller ;
