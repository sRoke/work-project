import conf from "../../../../../../conf";


var $scope,
    loginService,
    $state,
    Upload,
    $stateParams,
    $location,
    $http, $mdDialog;
class Controller {
    constructor(_$scope,
                _loginService,
                _$state,
                _Upload,
                _$stateParams,
                _$location,
                _$http, _$mdDialog) {
        $scope = _$scope;
        loginService = _loginService;
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
        $scope.editStatus = $stateParams.editStatus;

        // console.log(' $scope.editStatus = $stateParams.editStatus;', $scope.editStatus);
        if ($stateParams.skuData) {
            $scope.skuData = JSON.parse($stateParams.skuData);
            $scope.skuData.categorys = [];
            for (var i = 0; i < $scope.skuData.category.id.length; i++) {
                $scope.skuData.categorys.push($scope.skuData.category.id[i])
            }
        } else {
            $state.go("main.brandApp.store.home", {}, {reload: true});
        }

        // console.log('$scope.skuData', $scope.skuData);

        $scope.openSku = function (sku) {
            sku.check = !sku.check;
        };

        $scope.finish = function () {
            // console.log('$scope.skuData', $scope.skuData);
            var json = angular.toJson($scope.skuData);

            if ($scope.from == 'itemAdd') {
                $state.go("main.brandApp.store.itemManage.itemAdd", {id: $scope.id, skuData: json, status: 'add'})
            } else {
                $state.go("main.brandApp.store.itemManage.itemEdit", {
                    id: $scope.id,
                    skuData: json,
                    status: 'add',
                    editStatus: $scope.editStatus
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
    '$state',
    'Upload',
    '$stateParams',
    '$location',
    '$http',
    '$mdDialog'
];

export default Controller ;
