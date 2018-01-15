(function () {
    angular.module('qh-phone-lottery-front')
        .config(['$stateProvider', function ($stateProvider) {

            /**
             * 该状态是所有状态的父状态。用来设置全局性的数据。该状态是 abstract 的，并不给分配 URL。
             */
            $stateProvider.state("main", {
                abstract: true,
                // 通过 resolve 获取的数据，只要state不重新加载，就不会重新发送http请求，
                // 因此可以在子状态之间临时保存一些数据，然后一起提交到服务器上
                resolve: {
                    // // 当前的用户信息
                    // curUser: ['userService', 'wxService', function (userService, wxService) {
                    //     var q = userService.getCurUser(false, false);
                    //     /**
                    //      * 修改微信的分享链接
                    //      */
                    //     wxService.initShare();
                    //     return q;
                    // }]
                }
            });
        }]);
})();
