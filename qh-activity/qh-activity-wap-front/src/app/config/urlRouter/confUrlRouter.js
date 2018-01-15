confUrlRouter.$inject = ['$urlRouterProvider'];
function confUrlRouter($urlRouterProvider) {
    $urlRouterProvider.otherwise('/otherMain');
}


export default confUrlRouter;
