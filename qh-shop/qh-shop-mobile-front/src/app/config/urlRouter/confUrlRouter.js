confUrlRouter.$inject = ['$urlRouterProvider'];
function confUrlRouter($urlRouterProvider) {
    $urlRouterProvider.otherwise('/');
}


export default confUrlRouter;
