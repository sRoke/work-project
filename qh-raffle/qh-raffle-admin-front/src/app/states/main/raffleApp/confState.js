import Controller from "./Controller";
import html from "!html-loader?minimize=true!./view.html";

confState.$inject = ['$stateProvider'];
function confState($stateProvider) {
    /**
     * 测试
     */
    $stateProvider.state("main.raffleApp", {
        url: "/raffleApp/{raffleAppId}",   //不写依赖的话，，则会判定主要显示页面
        abstract:true,
        sticky: true,
        deepStateRedirect: true,
        views: {
            "raffleApp@main": {
                template: html,
                controller: Controller,
                controllerAs: "vm"
            }
        }
    });
}


export default confState ;



