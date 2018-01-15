import Controller from "./Controller";
import html from "!html-loader?minimize=true!./view.html";

confState.$inject = ['$stateProvider'];
function confState($stateProvider) {
    /**
     * 测试
     */
    $stateProvider.state("main.voteApp.vote.joinNum", {
        url: "/joinNum?id",   //不写依赖的话，，则会判定主要显示页面
        sticky: true,
        deepStateRedirect: true,
        views: {
            "joinNum@main.voteApp": {
                template: html,
                controller: Controller,
                controllerAs: "vm"
            }
        }
    });
}

export default confState ;
