import Controller from "./Controller";
import html from "!html-loader?minimize=true!./view.html";

function confState($stateProvider) {
    /**
     * 提供主要的布局结构：左侧纵向菜单栏，右侧则主要内容区域。
     */
    $stateProvider.state("main", {
        abstract: true,
        url: "",
        views: {
            "main@": {
                template: html,
                controller: Controller,
                controllerAs: "vm"
            }
        }
    });
}
confState.$inject = ['$stateProvider'];

export default confState ;
