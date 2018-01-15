import Controller from "./Controller";
import html from "!html-loader?minimize=true!./view.html";

confState.$inject = ['$stateProvider'];
function confState($stateProvider) {
    $stateProvider.state("main.brandApp.store.itemManage.specEdit", {
        url: "/specEdit/?id&status&skuData&editStatus",
        sticky: true,
        deepStateRedirect: true,
        views: {
            "specEdit@main.brandApp": {
                template: html,
                controller: Controller,
                controllerAs: "vm"
            }
        }
    });
}

export default confState ;


