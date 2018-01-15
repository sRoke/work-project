import conf from './conf';

runState.$inject = ['$rootScope', '$log', '$state', '$stateParams','loginService','$location','$timeout'];
function runState($rootScope, $log, $state, $stateParams,loginService,$location,$timeout) {
    $rootScope.$state = $state;
    $rootScope.$stateParams = $stateParams;
    $rootScope.$on('$stateChangeError', function (event, toState, toParams, fromState, fromParams, error) {
        $log.info("$stateChangeError111 : fromState = " + JSON.stringify(fromState.name) +
            ", toState = " + JSON.stringify(toState.name) + ", error = ", error);
    });
    $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {
        $log.info("$stateChangeStart111 : fromState = " + JSON.stringify(fromState.name) + ", toState = " + JSON.stringify(toState.name));
    });
    $rootScope.$on('$stateNotFound', function (event, unfoundState, fromState, fromParams) {
        $log.info("$stateNotFound111 : fromState = " + JSON.stringify(fromState.name) + ", toState = " + JSON.stringify(unfoundState.name));
    });
    $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
        $rootScope.errorsMsg = true;

        //---------------百度统计
        // $timeout(function () {
            console.log('_hmt.push_trackPageview===============',$location.absUrl().replace('https://kingsilk.net',''));
            _hmt.push(['_setAutoPageview', false]);
            _hmt.push(['_trackPageview',$location.absUrl().replace('https://kingsilk.net','')]);
        // },0);
        // var a = toState.name.replace(/\./ig,'/');
        // var b = conf.rootPath.replace('//kingsilk.net','')+'#'+a.replace('main','');
        // console.log(b);



        $log.info("$stateChangeSuccess111 : fromState = " + JSON.stringify(fromState.name) + ", toState = " + JSON.stringify(toState.name));
        // loginService.loginCtl(true,toState.name+'{brandAppId:'+toParams.brandAppId+'}');
        // loginService.loginCtl(true,toState.name,toParams);

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
}


export default runState;
