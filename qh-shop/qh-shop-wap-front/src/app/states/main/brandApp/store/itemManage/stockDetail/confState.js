import Controller from "./Controller";
import html from "!html-loader?minimize=true!./view.html";

confState.$inject = ['$stateProvider'];
function confState($stateProvider) {
    $stateProvider.state("main.brandApp.store.itemManage.stockDetail", {
        url: "/stockDetail",
        sticky: true,
        deepStateRedirect: true,
        views: {
            "stockDetail@main.brandApp": {
                template: html,
                controller: Controller,
                controllerAs: "vm"
            }
        }
    });
}

export default confState ;


