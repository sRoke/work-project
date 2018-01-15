import Controller from "./Controller";
import html from "!html-loader?minimize=true!./view.html";

confState.$inject = ['$stateProvider'];
function confState($stateProvider) {

    /**
     * 测试
     */
    $stateProvider.state("main.test.zll.login.wxMp", {
        url: "/wxMp?loginType&providerID&client_id&redirect_uri&scope&state",  //不写则会默认显示  ?providerID&client_id&redirect_uri&scope&state
        params: {

        },

        sticky: true,
        deepStateRedirect: true,
        views: {
            "wxMp@main.test.zll.login": {
                template: html,
                controller: Controller,
                controllerAs: "vm"
            }
        }
    });
}

export default confState ;


