import Controller from "./Controller";
import html from "!html-loader?minimize=true!./view.html";

function confState($stateProvider) {
    /**
     * 提供主要的布局结构：左侧纵向菜单栏，右侧则主要内容区域。
     */
    $stateProvider.state("main", {
        abstract: true,
        url: "",

        // 通过 resolve 获取的数据，只要state不重新加载，就不会重新发送http请求，
        // 因此可以在子状态之间临时保存一些数据，然后一起提交到服务器上
        resolve: {
            // // 当前的用户信息
            // curUser: ['userService', function (userService) {
            //     var q = userService.getCurUser(true, false);
            //     return q;
            // }],
            // myAuthority: ['userService', function (userService) {
            //     var q = userService.getMyAuthority();
            //     return q;
            // }]
        },
        views: {
            "main@": {
                template: html,
                controller: Controller,
                controllerAs: "vm"
            }
        }
    });
}
confState.$inject = ['$stateProvider'];

export default confState ;
