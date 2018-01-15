import Controller from "./Controller";
import html from "!html-loader?minimize=true!./view.html";

confState.$inject = ['$stateProvider'];
function confState($stateProvider) {
    $stateProvider.state("main.brandApp.store.itemManage.itemSetPrice", {
        url: "/itemSetPrice/?id&skuData&choose&from&editStatus&selectMoreSpec",
        sticky: true,
        deepStateRedirect: true,
        views: {
            "itemSetPrice@main.brandApp": {
                template: html,
                controller: Controller,
                controllerAs: "vm"
            }
        }
    });
}

export default confState ;


