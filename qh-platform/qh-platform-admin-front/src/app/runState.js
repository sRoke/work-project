runState.$inject = ['$rootScope', '$log', '$state', '$stateParams'];
function runState($rootScope, $log, $state, $stateParams) {
    $rootScope.$state = $state;
    $rootScope.$stateParams = $stateParams;
    $rootScope.$on('$stateChangeError', function (event, toState, toParams, fromState, fromParams, error) {
    });
    $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {
    });
    $rootScope.$on('$stateNotFound', function (event, unfoundState, fromState, fromParams) {
    });
    $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
        $rootScope.errorsMsg = true;
    });
}


export default runState;
