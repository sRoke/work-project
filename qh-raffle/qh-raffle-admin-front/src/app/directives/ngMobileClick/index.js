import conf from "../../conf";
import angular from "angular";
import directiveFactory  from "./directiveFactory";

export default angular.module(`${conf.app}.directives.ngMobileClick`, [])
    .directive('ngMobileClick', directiveFactory);
