import Controller from "./Controller";
import html from "!html-loader?minimize=true!./view.html";

confState.$inject = ['$stateProvider'];
function confState($stateProvider) {

    /**
     * 测试
     */
    $stateProvider.state("main.test.zll.login.phone", {
        url: "/phone",
        params: {
            backUrl:null
        },

        sticky: true,
        deepStateRedirect: true,
        views: {
            "phone@main.test.zll.login": {
                template: html,
                controller: Controller,
                controllerAs: "vm"
            }
        }
    });
}

export default confState ;


