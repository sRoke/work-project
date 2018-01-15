import angular from "angular";
import conf from "../../conf";
import confUrlRouter from "./confUrlRouter";

/**
 * 配置主题
 */
export default angular.module(`${conf.app}.config.urlRouter`, [])
    .config(confUrlRouter);
