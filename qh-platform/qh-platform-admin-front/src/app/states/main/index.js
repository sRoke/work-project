import conf from "../../conf";
import angular from "angular";
import uiRouter from "angular-ui-router";
import confState from "./confState";
import "./css.scss";

//console.log("-000000 ---- main", uiRouter);
export default angular.module(`${conf.app}.states.main`, [
    uiRouter
])
    .config(confState)
    .run(function () {
        //console.log("---------main running");
    });

