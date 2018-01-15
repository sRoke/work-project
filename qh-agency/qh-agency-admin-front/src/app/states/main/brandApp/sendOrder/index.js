/**
 * Created by renmy on 17-7-13.
 */
import conf from "../../../../conf";
import angular from "angular";
import uiRouter from "angular-ui-router";
import confState from "./confState";
import "./css.scss";
import "angular-material/angular-material.css";

export default angular.module(`${conf.app}.states.brandApp.sendOrder`, [
    uiRouter
])
    .config(confState);
/**
 * Created by renmy on 17-7-11.
 */
/**
 * Created by renmy on 17-7-13.
 */
