import controller from "./Controller";
import html from "!html-loader?minimize=true!./view.html";

confState.$inject = ['$stateProvider'];
function confState($stateProvider) {
    /**
     * 组织信息
     */
    $stateProvider.state("main.brandApp.org.list", {
        url: "/",

        resolve: {
            // // 当前的用户信息
            // curUser: ['userService', function (userService) {
            //     var q = userService.getCurUser(true, true);
            //     return q;
            // }]
        },
        views: {
            "org@main.brandApp": {
                template: html,
                controller: controller,
                controllerAs: "vm"
            }
        }
    });
}


export default confState ;






