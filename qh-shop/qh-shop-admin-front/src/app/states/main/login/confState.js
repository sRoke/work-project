import Controller from "./Controller";
import html from "!html-loader?minimize=true!./view.html";

confState.$inject = ['$stateProvider'];
function confState($stateProvider) {
    /**
     * 提供主要的布局结构：左侧纵向菜单栏，右侧则主要内容区域。
     */
    $stateProvider.state("main.login", {
        url: "/?backUrl&brandAppId",
        views: {
            "login@main": {
                template: html,
                controller: Controller,
                controllerAs: "vm"
            }
        }
    });
}

export default confState ;


