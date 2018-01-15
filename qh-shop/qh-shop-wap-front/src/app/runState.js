runState.$inject = ['$rootScope', '$log', '$state', '$stateParams', 'loginService', '$location'];
function runState($rootScope, $log, $state, $stateParams, loginService, $location) {



    $rootScope.$state = $state;
    $rootScope.$stateParams = $stateParams;
    $rootScope.$on('$stateChangeError', function (event, toState, toParams, fromState, fromParams, error) {
        $log.info("$stateChangeError111 : fromState = " + JSON.stringify(fromState.name) +
            ", toState = " + JSON.stringify(toState.name) + ", error = ", error);
    });
    $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {
        $log.info("$stateChangeStart111 : fromState = " + JSON.stringify(fromState.name) + ", toState = " + JSON.stringify(toState.name));

        if (fromState.name === '' && toState.name === 'main') {
            // cancel initial transition
            event.preventDefault();
            // Go to the default background state. (Don't update the URL)
            $state.go("main.test1", undefined, {location: false}).then(function () {
                // OK, background is loaded, now go to the original modalstate
                $state.go(toState, toParams);
            });
        }

    });
    $rootScope.$on('$stateNotFound', function (event, unfoundState, fromState, fromParams) {
        $log.info("$stateNotFound111 : fromState = " + JSON.stringify(fromState.name) + ", toState = " + JSON.stringify(unfoundState.name));
    });
    $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
        $rootScope.errorsMsg = true;

        $rootScope.previousState_name = fromState.name;
        $rootScope.previousState_params = fromParams;

        $log.info("$stateChangeSuccess111 : fromState = " + JSON.stringify(fromState.name) + ", toState = " + JSON.stringify(toState.name));
        // loginService.loginCtl(true,toState.name+'{brandAppId:'+toParams.brandAppId+'}');
        // loginService.loginCtl(true,toState.name,toParams);
        // console.log(toParams);
        // console.log(toState)
        // TODO check login at here
        /*
         var publicStates = ["a1.*","b1","login"]
         var needLogin = true;
         publicStates.each{
         if(needLogin &&  $state.includes( pubStat ){
         needLogin=false;
         }
         }
         if(needLogin){
         $state.go("login", {backUrl : getUrl(toState);} )
         }

         */
    });
    $rootScope.back = function () {//实现返回的函数
        $state.go($rootScope.previousState_name, $rootScope.previousState_params);
    };
    // This code applies a default background state for the modal
    // $rootScope.$on('$stateChangeStart', function (evt, toState, toParams, fromState) {
    //     // initial load and is trying to load the modalstate
    //     if (fromState.name === '' && toState.name === 'main') {
    //         // cancel initial transition
    //         evt.preventDefault();
    //         // Go to the default background state. (Don't update the URL)
    //         $state.go("main.test1", undefined, {location: false}).then(function () {
    //             // OK, background is loaded, now go to the original modalstate
    //             $state.go(toState, toParams);
    //         });
    //     }
    // });

}
export default runState;
