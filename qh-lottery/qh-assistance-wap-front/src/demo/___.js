/**
 * Module : xxx
 */
(function () {
    angular.module('qh-assistance-wap-front')
        .config(['$stateProvider', function ($stateProvider) {

            $stateProvider.state("main.xxx", {
                url: "/xxx",
                views: {
                    "@": {
                        templateUrl: 'views/main//___.html',
                        controller: xxxController
                    }
                }
            });
        }]);


    // ----------------------------------------------------------------------------
    xxxController.$inject = ['$scope'];
    function xxxController($scope) {
    }
})();