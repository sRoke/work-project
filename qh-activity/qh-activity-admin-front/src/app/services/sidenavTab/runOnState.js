import asTabStates from "./asTabStates";

runOnState.$inject = ['$rootScope', '$log', '$state', '$stateParams', 'sidenavTab'];
function runOnState($rootScope, $log, $state, $stateParams, sidenavTab) {
    $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {
        // $log.info("event ===", event);
        // $log.info("toState ===", toState);
        // $log.info("toParams ===", toParams);
        //
        // $log.info("fromState ===", fromState);
        // $log.info("fromParams ===", fromParams);
        sidenavTab.openOrFocusTab(toState, fromParams, toParams);

        $log.info("$stateChangeStart ===  sidenavTab : fromState = " +
            JSON.stringify(fromState.name) + ", toState = " + JSON.stringify(toState.name));




    });

    // 默认就包含主页
    sidenavTab.openOrFocusTab(asTabStates[0].curState);
}

export default  runOnState;
