import conf from "../../../../conf";

var $scope,
    $stateParams,
    $http,
    loginService,
    alertService,
    $state,
    $location;
class Controller {
    constructor(_$scope,
                _$stateParams,
                _$http,
                _loginService,
                _alertService,
                _$state,
                _$location) {
        $scope = _$scope;
        $state = _$state;
        $http = _$http;
        loginService = _loginService;
        $stateParams = _$stateParams;
        alertService = _alertService;
        $location = _$location;
        var vm = this;
        $scope.brandAppId = $stateParams.brandAppId;

        const TAG = "main/manageInvoice";
        console.log(`=> ${TAG}`);
        loginService.loginCtl(true, $location.absUrl());

        $scope.data = {
            ALL: {},
            UNSHIPPED: {},
            UNRECEIVED: {},
            RECEIVED: {},
        };


        $scope.status = $stateParams.status ? $stateParams.status : "ALL";
        $scope.tableIndex = $stateParams.tableIndex ? $stateParams.tableIndex : "0";

        $scope.tabs = function (status, tableIndex) {
            $scope.status = status;
            $scope.tableIndex = tableIndex;

            if (!$scope.data[$scope.status].data) {
                $scope.data[$scope.status].data = [];
                $scope.getList()
            }
        };

        vm.size = conf.pageSize;
        // vm.size = 2;
        $scope.getList = function (page) {
            if (!$scope.data[$scope.status].number) {
                $scope.data[$scope.status].number = 0;
            }
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/11111111111111/deliverInvoice",
                params: {
                    pageSize: vm.size,
                    number:page?page: $scope.data[$scope.status].number,
                    status: $scope.status === "ALL" ? null : $scope.status,
                    keyWords:$scope.keyWord
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                // $scope.data = resp.data.data;
                if(resp.data.data.number=='0'){
                    $scope.data[$scope.status].data = []; //搜索时清空
                }
                if (!$scope.data[$scope.status].data) {
                    $scope.data[$scope.status].data = [];
                }
                //存入数据
                $scope.data[$scope.status].data = $scope.data[$scope.status].data.concat(resp.data.data.content);
                $scope.data[$scope.status].totalNum = resp.data.data.totalElements + 1;
                $scope.data[$scope.status].number = resp.data.data.number;
                if ((resp.data.data.number + 1) * resp.data.data.size >= resp.data.data.totalElements) {
                    $scope.data[$scope.status].pageEnd = true;
                }
                $scope.data[$scope.status].number++;
            }, function (resp) {
            });
        };


        /*
         * 确认收货
         * */
        $scope.deliverInvoice = (id) => {
            $state.go('main.brandApp.manageInvoiceDetail', {id: id}, {reload: true});
            // alertService.confirm(null, "", "确认发货？", "取消", "确定").then(function (data) {
            //     if (data) {
            //         $http({
            //             method: "PUT",
            //             url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/deliverInvoice/" + id,
            //             data: {},
            //             headers: {
            //                 'Authorization': 'Bearer ' + loginService.getAccessToken(),
            //                 "brandApp-Id": $scope.brandAppId
            //             }
            //         }).then(function () {
            //             // alertService.msgAlert('success', '收货成功');
            //             $scope.data[$scope.status] = {};
            //             $scope.getList()
            //         }, function (resp) {
            //         });
            //     }
            // });
        };


        $scope.tabs($scope.status, $scope.tableIndex);

    }


    /*返回上级*/
    fallbackPage() {
        // $state.go("main.brandApp.stock", null, {reload: true});
        // if ($stateParams.from == 'purchase' || ($stateParams.status || $stateParams.tableIndex)) {
        //     $state.go("main.brandApp.purchase", null, {reload: true});
        // } else if ($stateParams.from == 'stock') {
        //     $state.go("main.brandApp.stock", null, {reload: true});
        //
        // } else {
        //     history.back();
        // }
        history.back();
    };
}

Controller.$inject = [
    '$scope',
    '$stateParams',
    '$http',
    'loginService',
    'alertService',
    '$state',
    '$location'
];

export default Controller ;
