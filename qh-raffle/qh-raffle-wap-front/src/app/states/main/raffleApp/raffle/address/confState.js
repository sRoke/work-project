import Controller from "./Controller";
import html from "!html-loader?minimize=true!./view.html";

confState.$inject = ['$stateProvider'];
function confState($stateProvider) {
    /**
     * 测试
     */
    $stateProvider.state("main.raffleApp.raffle.address", {
        url: "/address/{orderId}",
        sticky: true,
        deepStateRedirect: true,
        views: {
            "address@main.raffleApp": {
                template: html,
                controller: Controller,
                /*controller: function(){
                 console.log("----------------------------------------zll")
                 },*/
                controllerAs: "vm"
            }
        }
    });
}

export default confState ;


