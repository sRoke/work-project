import Controller from "./Controller";
import html from "!html-loader?minimize=true!./view.html";

confState.$inject = ['$stateProvider'];
function confState($stateProvider) {

    /**
     * 测试
     */
    $stateProvider.state("main.test.zll.login", {
        url: "/login",
        sticky: true,
        deepStateRedirect: true,
        views: {
            "login@main.test.zll": {
                template: html,
                controller: Controller,
                controllerAs: "vm"
            }
        }
    });
}

export default confState ;


