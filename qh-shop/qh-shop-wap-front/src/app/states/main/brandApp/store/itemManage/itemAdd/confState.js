import Controller from "./Controller";
import html from "!html-loader?minimize=true!./view.html";

confState.$inject = ['$stateProvider'];
function confState($stateProvider) {
    $stateProvider.state("main.brandApp.store.itemManage.itemAdd", {
        url: "/itemAdd/?id&status&skuData&selectMoreSpec",
        sticky: true,
        deepStateRedirect: true,
        views: {
            "itemAdd@main.brandApp": {
                template: html,
                controller: Controller,
                controllerAs: "vm"
            }
        }
    });
}

export default confState ;


