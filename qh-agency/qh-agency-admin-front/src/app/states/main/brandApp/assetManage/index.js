import conf from "../../../../conf";
import angular from "angular";
import uiRouter from "angular-ui-router";
import confState from "./confState";
import "./css.scss";
import "angular-material/angular-material.css";

export default angular.module(`${conf.app}.states.brandApp.assetManage`, [
    uiRouter
])
    .config(confState);
