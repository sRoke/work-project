/**
 * Created by susf on 17-3-29.
 */
import controller from "./Controller";
import html from "!html-loader?minimize=true!./view.html";

confState.$inject = ['$stateProvider'];
function confState($stateProvider) {

    $stateProvider.state("main.brandApp.goodAdd", {
        url: "/goodAdd",

        resolve: {
            // // 当前的用户信息
            // curUser: ['userService', function (userService) {
            //     var q = userService.getCurUser(true, true);
            //     return q;
            // }]
        },
        views: {
            "goodAdd@main.brandApp": {
                template: html,
                controller: controller,
                controllerAs: "vm"
            }
        }
    });
}



export default confState ;



