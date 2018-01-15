import html from "!html-loader?minimize=true!./view.html";
import Controller from "./Controller";

function directiveFactory() {

    return {
        restrict: 'EA',
        priority: 500,
        template: html,
        scope: {
            cur: '='
        },
        controller: Controller,
        /*api: function(){
            console.log("---------ksFooterMenu : Controller.js ")
        },*/
        controllerAs: "footerCtrl",
        link: function (scope, element, attrs, ctrls) {
            element.addClass("ksFooterMenu");
        }
    };

}

directiveFactory.$inject = [];

export default directiveFactory;
