import userAddController from "./Controller";
import html from "!html-loader?minimize=true!./view.html";

confState.$inject = ['$stateProvider'];
function confState($stateProvider) {

    $stateProvider.state("main.brandApp.mbAdd", {
        url: "/mbAdd/{id}",

        resolve: {
            // // 当前的用户信息
            // curUser: ['userService', function (userService) {
            //     var q = userService.getCurUser(true, true);
            //     return q;
            // }]
        },
        views: {
            "mbAdd@main.brandApp": {
                template: html,
                controller: userAddController,
                controllerAs: "vm"
            }
        }
    });
}



export default confState ;




