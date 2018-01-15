import conf from "../../../../../conf";
import angular from "angular";
import uiRouter from "angular-ui-router";
import confState from "./confState";
import "./css.scss";
import "./dialog.scss"

export default angular.module(`${conf.app}.states.brandApp.store.item`, [
        uiRouter
    ])
    .config(confState);
