import conf from "../../conf";
import angular from "angular";
import directiveFactory  from "./directiveFactory";

export default angular.module(`${conf.app}.directives.stopTouchEvent`, [])
    .directive('stopTouchEvent', directiveFactory);
