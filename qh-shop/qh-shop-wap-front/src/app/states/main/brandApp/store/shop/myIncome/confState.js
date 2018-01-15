import Controller from "./Controller";
import html from "!html-loader?minimize=true!./view.html";

confState.$inject = ['$stateProvider'];
function confState($stateProvider) {
    /**
     * 测试
     */
    $stateProvider.state("main.brandApp.store.shop.myIncome", {
        url: "/myIncome",
        sticky: true,
        deepStateRedirect: true,
        views: {
            "myIncome@main.brandApp": {
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


