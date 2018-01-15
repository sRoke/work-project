import conf from "../../../../conf";
// import 'angular-bootstrap';
import store from "store";

var $scope,
    $rootScope,
    authService,
    loginService,
    $stateParams,
    $mdDialog,
    errorService,
    $location,
    $http;

class Controller {
    constructor(_$scope,_$rootScope, _$http, _loginService, _authService, _$mdDialog,_errorService, _$location, _$stateParams) {
        $scope = _$scope;
        $rootScope=_$rootScope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        errorService=_errorService;
        $location = _$location;
        $stateParams = _$stateParams;
        loginService = _loginService;
        authService = _authService;
        var vm = this;


        loginService.loginCtl(true);
        //权限相关
        $scope.ORDER_U = authService.hasAuthor("ORDER_U");    //发货
        $scope.ORDER_R = authService.hasAuthor("ORDER_R");    //查看
        $scope.ORDER_E = authService.hasAuthor("ORDER_E");    //导出
        $scope.brandAppId = $stateParams.brandAppId;
        $scope.shopId = $stateParams.shopId;
        console.log('shopId', $scope.shopId);
        //初始化
        // /brandApp/{id}
        // $rootScope.data={};
        $scope.activeNum = '1';
        $scope.changeTab = function (num) {
            $scope.activeNum = num;
        };
        //选择框下拉
        // $scope.isDown=true;
        // $scope.changeDown=function(){
        //     $scope.isDown=!$scope.isDown;
        // };
        $scope.curPage = 1;
        $scope.search={};
        $scope.pageChanged = function (curPage) {
            console.log('qwwqqqqq11111',$scope.search.goodsName)
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId+"/shop/"+$scope.shopId+"/item",
                params: {
                    keyWords:$scope.search.goodsName,
                    page: curPage ? curPage-1 : $scope.curPage - 1,
                    size:10,
                    status:'ALL'
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).success(function (response) {
                console.log(12121212, response);
                $scope.page = response.data;
                //$scope.curPage = response.data.number + 1;
                $scope.items=response.data.content;
            });
            //获取门店信息
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/"+$scope.brandAppId+"/shop/"+ $scope.shopId,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $scope.data=resp.data.data;
            }, function (resp) {
                //error
            });
        };
        $scope.pageChanged();
        $scope.btn = function() {
            var certBytes = new FormData();
            var file = document.querySelector('input[type=file]').files[0];
            console.log('file',file.name);
            certBytes.append('certBytes', file);
            //fd.certBytes=file;
            console.log('fd1111111111111111111111111111111111111111',certBytes);
            $http({
                method:'POST',
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId+"/shop/"+$scope.shopId+"/item/import",
                data: certBytes,
                headers: {
                    'Authorization': 'Bearer' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId,
                    'Content-Type':undefined
                },
                transformRequest: angular.identity
            }).success( function ( response ) {
                //上传成功的操作
                console.log('response',response);
                if(response.data.id){
                    return errorService.error("上传成功", null);
                }
            });

        };
        //导入tk
        $scope.importText = function (ev) {
            $mdDialog.show({
                templateUrl: 'remark.html',
                parent: angular.element(document.body).find('#qh-agency-admin-front'),
                targetEvent: ev,
                clickOutsideToClose: true,   //true 点击界外的时候弹窗消失
                //locals: {key: $scope.memo},    //用来传数据
                fullscreen: false,
                controller: ['$mdDialog', function ($mdDialog) {
                    var vmd = this;
                    // console.log(locals);
                    // vmd.memo = locals.key;
                    vmd.cancel = function () {
                        return $mdDialog.cancel();
                    };
                    vmd.btn=function () {
                        var certBytes = new FormData();
                        var file = document.querySelector('input[type=file]').files[0];
                        //console.log('file',file.name);
                        certBytes.append('certBytes', file);
                        //fd.certBytes=file;
                        console.log('fd1111111111111111111111111111111111111111',certBytes);
                        $http({
                            method:'POST',
                            url: conf.apiPath + "/brandApp/" + $scope.brandAppId+"/shop/"+$scope.shopId+"/item/import",
                            data: certBytes,
                            headers: {
                                'Authorization': 'Bearer' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId,
                                'Content-Type':undefined
                            },
                            transformRequest: angular.identity
                        }).success( function ( response ) {
                            //上传成功的操作
                            if(response.code=='SUCCESS'){
                                vmd.cancel();
                                return errorService.error(response.data, null);
                                $scope.pageChanged();
                            }
                            console.log('response',response);
                        }).error(function (response) {
                            vmd.cancel();
                            $scope.pageChanged();
                            return errorService.error("导入失败", null);

                        });
                    }
                    vmd.url=conf.apiPath + "/brandApp/" + $scope.brandAppId+"/shop/"+$scope.shopId+"/item/downloadModel";
                    // vmd.download=function () {
                    //     $http({
                    //         method:'GET',
                    //         url: conf.apiPath + "/brandApp/" + $scope.brandAppId+"/shop/"+$scope.shopId+"/item/downloadModel",
                    //         headers: {
                    //             'Authorization': 'Bearer' + loginService.getAccessToken(),
                    //             "brandApp-Id": $scope.brandAppId,
                    //             'Content-Type':undefined
                    //         },
                    //     }).success( function ( response ) {
                    //         console.log('response11111111111',response);
                    //         vmd.fullListUrl = 'data:text/csv;charset=utf-8,' + unescape(response);
                    //         console.log('$scope.fullListUrl',vmd.fullListUrl);
                    //     }).error(function (response) {
                    //
                    //     });
                    // }
                }],
                controllerAs: "vmd"
            }).then(function (answer) {
            }, function () {
            });
        };
        console.log('$scope.curPage',$scope.curPage);
    }
}

Controller.$inject = [
    '$scope', '$rootScope','$http', 'loginService', 'authService', '$mdDialog', 'errorService','$location', '$stateParams'
];

export default Controller ;
