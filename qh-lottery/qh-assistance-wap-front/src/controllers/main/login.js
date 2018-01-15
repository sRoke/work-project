/**
 * Created by susf on 17-5-17.
 */
/**
 * Module : xxx
 */
(function () {
    angular.module('qh-assistance-wap-front')
        .config(['$stateProvider', function ($stateProvider) {

            $stateProvider.state("main.login", {
                url: "/login",
                views: {
                    "@": {
                        templateUrl: 'views/main/login/index.root.html',
                        controller: loginController
                    }
                }
            });
        }]);


    // ----------------------------------------------------------------------------
    loginController.$inject = ['$scope', '$mdDialog', '$http', '$location', '$templateCache','appConfig'];
    function loginController($scope, $mdDialog, $http, $location, $templateCache,appConfig) {
              alert('wwwwwwwwww');

    }



})();