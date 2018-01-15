import Controller from "./Controller";
import html from "!html-loader?minimize=true!./view.html";

confState.$inject = ['$stateProvider'];
function confState($stateProvider) {
    /**
     * 测试
     */
    $stateProvider.state("main.test.zll.oauth.authorize", {
        url: "/authorize",  //不写则会默认显示  ?providerID&client_id&redirect_uri&scope&state

        sticky: true,
        deepStateRedirect: true,
        views: {
            "authorize@main.test.zll.oauth": {
                template: html,
                controller: Controller,
                controllerAs: "vm"
            }
        }
    });
}

export default confState ;


