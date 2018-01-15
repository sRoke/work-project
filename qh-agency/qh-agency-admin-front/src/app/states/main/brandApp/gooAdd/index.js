/**
 * Created by susf on 17-3-29.
 */
import conf from "../../../../conf";
import angular from "angular";
import uiRouter from "angular-ui-router";
import confState from "./confState";
import "./css.scss";
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap-theme.css';
import 'angular-ui-bootstrap';
import ngFileUpload from  "ng-file-upload";

import "textangular";
import ngSanitize from "angular-sanitize";
import "rangy";
import 'angular-messages';

export default angular.module(`${conf.app}.states.brandApp.gooAdd`, [
    uiRouter,'ui.bootstrap', "textAngular", ngSanitize,ngFileUpload,'ngMessages'
])
    .config(confState)
    .config(['$provide', function ($provide) {
        $provide.decorator('taOptions', ['$delegate', function (taOptions) {
            taOptions.forceTextAngularSanitize = false;
            return taOptions;
        }]);
    }])
;