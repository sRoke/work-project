import Controller from "./Controller";
import html from "!html-loader?minimize=true!./view.html";

confState.$inject = ['$stateProvider'];
function confState($stateProvider) {

    /**
     * 测试
     */
    $stateProvider.state("main.test3", {
        url: "/test3",  //不写则会默认显示  ?providerID&client_id&redirect_uri&scope&state

        sticky: true,
        deepStateRedirect: true,
        views: {
            "test3@main": {
                template: html,
                controller: Controller,
                controllerAs: "vm"
            }
        }
    });
}

export default confState ;


