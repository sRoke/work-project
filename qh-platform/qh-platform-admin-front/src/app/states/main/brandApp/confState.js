/**
 * Created by susf on 17-4-14.
 */
import Controller from "./Controller";
import html from "!html-loader?minimize=true!./view.html";

confState.$inject = ['$stateProvider'];
function confState($stateProvider) {

    $stateProvider.state("main.brandApp", {
        url: "/brandApp/{brandAppId}",   //不写依赖的话，，则会判定主要显示页面
        abstract:true,
        sticky: true,
        deepStateRedirect: true,
        views: {
            "brandApp@main": {
                template: html,
                controller: Controller,
                controllerAs: "vm"
            }
        }
    });
}


export default confState ;



