import Controller from "./Controller";
import html from "!html-loader?minimize=true!./view.html";

confState.$inject = ['$stateProvider'];
function confState($stateProvider) {

    $stateProvider.state("main.test.zll.reg", {
        url: "/reg",
        sticky: true,
        deepStateRedirect: true,
        views: {
            "reg@main.test.zll": {
                template: html,
                controller: Controller,
                controllerAs: "vm"
            }
        }
    });
}

export default confState ;


