import angular from "angular";
import conf from "../../conf";
import uiRouter from "angular-ui-router";
import ocLazyload from "oclazyload";
import confFutureState from "./confFutureState.js";
import "ui-router-extras";

/**
 * 该模块用来注册配置 futureState 加载规则
 */
export default angular.module(`${conf.app}.config.futureStates`, [
    uiRouter,
    ocLazyload,
    "ct.ui.router.extras"
])
    .config(confFutureState);



