import angular from "angular";
import conf from "../../conf";
import confUrlMatcher from "./confUrlMatcher";

/**
 * 配置主题
 */
export default angular.module(`${conf.app}.config.urlMatcher`, [])
    .config(confUrlMatcher);
