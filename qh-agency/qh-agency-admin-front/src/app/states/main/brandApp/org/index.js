import conf from "../../../../conf";
import angular from "angular";
import uiRouter from "angular-ui-router";
import confState from "./confState";


export default angular.module(`${conf.app}.states.brandApp.org`, [
    uiRouter
])
    .config(confState)
;







