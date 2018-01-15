import controller from "./Controller";
import html from "!html-loader?minimize=true!./view.html";

confState.$inject = ['$stateProvider'];
function confState($stateProvider) {

    $stateProvider.state("main.brandApp.roAdd", {
        url: "/roAdd?id$source",

        resolve: {
            // // 当前的用户信息
            // curUser: ['userService', function (userService) {
            //     var q = userService.getCurUser(true, true);
            //     return q;
            // }]
        },
        views: {
            "roAdd@main.brandApp": {
                template: html,
                controller: controller,
                controllerAs: "vm"
            }
        }
    });
}



export default confState ;




