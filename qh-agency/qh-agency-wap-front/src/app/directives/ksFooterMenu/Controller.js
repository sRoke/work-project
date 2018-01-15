import conf from "../../conf";

var $scope, $stateParams, $http, loginService;
class Controller {

    constructor(_$scope, _$stateParams, _$http, _loginService) {
        $scope = _$scope;
        $stateParams = _$stateParams;
        $http = _$http;
        loginService = _loginService;
        $scope.brandAppId = $stateParams.brandAppId;
        var vm = this;
        vm.menus = [
            {
                name: 'home',
                iconLiga: "home",
                iconClass: "ag-home",
                text: "主页",
                state: "main.brandApp.home"
            },
            {
                name: 'shop',
                iconLiga: "shop",
                iconClass: "ag-truck-o",
                text: "门店",
                state: "shop",
                shopPath: '12'
            },
            // {
            //     name: 'sales',
            //     iconLiga: "fileboard",
            //     iconClass: "ag-fileboard",
            //     text: "销",
            //     state: "main.brandApp.sales.salesMain"
            // },
            // {
            //     name: 'stock',
            //     iconLiga: "grid-system",
            //     iconClass: "ag-grid-system",
            //     text: "存",
            //     state: "main.brandApp.stock"
            // },
            // {
            //     name: 'report',
            //     iconLiga: "showcase",
            //     iconClass: "ag-showcase",
            //     text: "报表",
            //     state: "main.brandApp.report.reportMain"
            // },
            // {
            //     name: 'shelves',
            //     iconLiga: "shelf",
            //     iconClass: "ag-shelf",
            //     text: "货架",
            //     state: "main.brandApp.shelves"
            // },
            {
                name: 'record',
                iconLiga: "work-o",
                iconClass: "ag-work-o",
                text: "参谋",
                state: "main.brandApp.record"
            },
            {
                name: 'center',
                iconLiga: "profile",
                iconClass: "ag-profile",
                text: "我的",
                state: "main.brandApp.center.main"
            }
        ];
        $scope.getPatter = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/check",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    'brandApp-Id': $stateParams.brandAppId ? $stateParams.brandAppId : '567b614be4b0f72f9f6cf02e',
                }
            }).then(function (data) {
                    $scope.patnerId = data.data.data;

                    $http({
                        method: "GET",
                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/" + $scope.patnerId,
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            'brandApp-Id': $stateParams.brandAppId,
                        }
                    }).then(function (resp) {
                            console.log('resp', resp);
                            $scope.shopData = resp.data.data;

                        }, function () {

                        }
                    );
                }
            );
        };

        $scope.getPatter()

        $scope.goto = function (data) {
            var path = 'https:' + conf.shopUrl + 'brandApp/' + $scope.shopData.shopBrandAppId + '/selectStore';
            console.log(path);
            window.location = path;
        };

    }

}

Controller.$inject = ['$scope', '$stateParams', '$http', 'loginService'];

export default Controller;
