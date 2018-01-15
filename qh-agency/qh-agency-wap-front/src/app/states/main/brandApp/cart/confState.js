import Controller from "./Controller";
import html from "!html-loader?minimize=true!./view.html";

confState.$inject = ['$stateProvider'];
function confState($stateProvider) {
    /**
     * 购物车
     */
    $stateProvider.state("main.brandApp.cart", {
        url: "/cart?s",
        sticky: true,
        deepStateRedirect: true,
        views: {
            "cart@main.brandApp": {
                template: html,
                controller: Controller,
                controllerAs: "vm"
            }
        }
    });
}


export default confState ;



