import conf from "../../../conf";
import angular from "angular";
import uiRouter from "angular-ui-router";
import confState from "./confState";
import "./css.scss";
// import "ui-router-extras";
export default angular.module(`${conf.app}.states.test3`, [
    uiRouter,
    // "ct.ui.router.extras",
    // 'ct.ui.router.extras.core',
    // 'ct.ui.router.extras.dsr',
    // 'ct.ui.router.extras.future',
    // 'ct.ui.router.extras.previous',
    // 'ct.ui.router.extras.statevis',
    // 'ct.ui.router.extras.sticky',
    // 'ct.ui.router.extras.transition'
])
    .config(confState)
;
