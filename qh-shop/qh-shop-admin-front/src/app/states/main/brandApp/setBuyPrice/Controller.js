import conf from "../../../../conf";
// import dialog from "!html-loader?minimize=true!./updateAddress.html";

var $scope,
    $http,
    authService,
    $state,
    $log,
    loginService,
    errorService,
    Upload,
    $filter,
    $mdDialog,
    $stateParams;
class Controller {
    constructor(_$scope,
                _$http,
                _$state,
                _$log,
                _authService,
                _loginService,
                _errorService,
                _Upload,
                _$filter,
                _$mdDialog,
                _$stateParams) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        authService = _authService;
        loginService = _loginService;
        errorService = _errorService;
        Upload = _Upload;
        $filter = _$filter;
        $log = _$log;
        $stateParams = _$stateParams;
        /////////////////////////////////
        loginService.loginCtl(true);
        $scope.brandAppId = $stateParams.brandAppId;
        //获取权限，勿删
        // authService.setAuthorities($scope.brandAppId);

        $scope.price = {};  //页面绑定
        ///brandApp/{brandAppId}/sysConf/shopPrice
        $scope.getInfo=function(){
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/sysConf/shopPrice",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                console.log(123456789,resp.data.data)
                $scope.items=resp.data.data;
                $scope.price.free=$scope.items.free.free;
                $scope.price.mOldPrice=$scope.items.monthly.mOldPrice/100;
                $scope.price.mSallPrice=$scope.items.monthly.mSallPrice/100;
                $scope.price.mDay=$scope.items.monthly.mDay;
                // $scope.price.tOldPrice=$scope.items.quarter.tOldPrice;
                // $scope.price.tSallPrice=$scope.items.quarter.tSallPrice;
                // $scope.price.tDay==$scope.items.quarter.tDay;
                // $scope.price.yOldPrice=$scope.items.year.yOldPrice;
                // $scope.price.ySallPrice=$scope.items.year.ySallPrice;
                // $scope.price.yDay=$scope.items.year.yDay;

            }, function (resp) {
                //error
            });
        };
        $scope.getInfo();
        $scope.save = function () {


            // $scope.saveData = [];
            //
            // var m = [], t = [], y = [], f = [];  //定义包装数组
            //
            // m.push(  //月费
            //     {
            //         mOldPrice: $scope.price.mOldPrice,
            //         mSallPrice: $scope.price.mSallPrice,
            //         mDay: $scope.price.mDay
            //     }
            // );
            // t.push( //季度
            //     {
            //         tOldPrice: $scope.price.tOldPrice,
            //         tSallPrice: $scope.price.tSallPrice,
            //         tDay: $scope.price.tDay
            //     }
            // );
            // y.push(  //年费
            //     {
            //         yOldPrice: $scope.price.yOldPrice,
            //         ySallPrice: $scope.price.ySallPrice,
            //         yDay: $scope.price.yDay
            //     }
            // );
            // f.push(  //试用
            //     {
            //         free: $scope.price.free,
            //     }
            // );
            // $scope.saveData.push(  //存到数组中传给后台
            //     {
            //         monthly: m,
            //         quarter: t,
            //         year: y,
            //         free: f
            //     }
            // );
            //
            // console.log($scope.saveData);
            if(!$scope.price.mDay){
                $scope.price.mDay=30;
            };
            $http({
                method: "POST",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/sysConf/shopPrice",
                data: {
                    mOldPrice:  $scope.price.mOldPrice*100,
                    mSallPrice:  $scope.price.mSallPrice*100,
                    mDay:  $scope.price.mDay,
                    // tOldPrice:  $scope.price.tOldPrice,
                    // tSallPrice:  $scope.price.tSallPrice,
                    // tDay:  $scope.price.tDay,
                    // yOldPrice:  $scope.price.yOldPrice,
                    // ySallPrice:  $scope.price.ySallPrice,
                    // yDay:  $scope.price.yDay,
                    free:  $scope.price.free,
                }
            }).success(function (response) {
                console.log(6989595985895, response);
                return errorService.error(response.data, null);
            });

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
    'errorService',
    'Upload',
    '$filter',
    '$mdDialog',
    '$stateParams'
];

export default Controller ;
