import conf from "../../../../../conf";
import angular from "angular";
import uiRouter from "angular-ui-router";
import confState from "./confState";
import "./css.scss";
import ngFileUpload from  "ng-file-upload";



export default angular.module(`${conf.app}.states.brandApp.store.marketing`, [
    uiRouter, ngFileUpload
])
    .config(confState);
