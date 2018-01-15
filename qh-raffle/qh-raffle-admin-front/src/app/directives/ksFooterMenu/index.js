import conf from "../../conf";
import angular from "angular";
import directiveFactory  from "./directiveFactory";
import "./css.scss";

export default angular.module(`${conf.app}.directives.ksFooterMenu`, [])
    .directive('ksFooterMenu', directiveFactory);
