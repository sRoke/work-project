import angular from "angular";
import conf from "../../conf";
import confUrlMatcher from "./confUrlMatcher";
import uiRouter from "angular-ui-router";
/**
 * 配置主题
 */
export default angular.module(`${conf.app}.config.urlMatcher`, [
        uiRouter
])
    .config(confUrlMatcher);
