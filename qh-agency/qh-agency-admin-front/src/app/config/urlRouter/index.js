import angular from "angular";
import conf from "../../conf";
import confUrlRouter from "./confUrlRouter";
import uiRouter from "angular-ui-router";
/**
 * 配置主题
 */
export default angular.module(`${conf.app}.config.urlRouter`, [
        uiRouter
])
    .config(confUrlRouter);
