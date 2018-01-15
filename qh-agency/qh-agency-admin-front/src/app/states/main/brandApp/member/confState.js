import UserListController from "./Controller";
import html from "!html-loader?minimize=true!./view.html";

confState.$inject = ['$stateProvider'];
function confState($stateProvider) {

    $stateProvider.state("main.brandApp.member", {
        url: "/member",

        resolve: {
            // // 当前的用户信息
            // curUser: ['userService', function (userService) {
            //     var q = userService.getCurUser(true, true);
            //     return q;
            // }]
        },
        views: {
            "member@main.brandApp": {
                template: html,
                controller: UserListController,
                controllerAs: "vm"
            }
        }
    });

}


export default confState ;




