import angular from "angular";
import conf from "../../conf";
import confMdGestureProvider from "./confMdGestureProvider";
import ngMaterial from "angular-material";
/**
 * 配置主题
 */
export default angular.module(`${conf.app}.config.ngMaterial`, [
    ngMaterial
])
    .config(confMdGestureProvider);
