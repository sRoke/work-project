import Controller from "./Controller";
import html from "!html-loader?minimize=true!./view.html";

confState.$inject = ['$stateProvider'];
function confState($stateProvider) {
    $stateProvider.state("main.brandApp.store.inventoryRecord.itemChoice", {
        url: "/itemChoice",
        sticky: true,
        deepStateRedirect: true,
        views: {
            "itemChoice@main.brandApp": {
                template: html,
                controller: Controller,
                controllerAs: "vm"
            }
        }
    });
}

export default confState ;


