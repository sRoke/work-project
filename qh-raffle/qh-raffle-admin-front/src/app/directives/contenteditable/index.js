import conf from "../../conf";
import angular from "angular";
import directiveFactory  from "./directiveFactory";

export default angular.module(`${conf.app}.directives.contenteditable`, [])
    .directive('contenteditable', directiveFactory);
