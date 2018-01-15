import conf from "../../../../../../conf";
import angular from "angular";
import uiRouter from "angular-ui-router";
import confState from "./confState";
import "./css.scss";


// 微信内点击授权按钮登录
export default angular.module(`${conf.app}.states.test.zll.login.wxMp`, [
    uiRouter
])
    .config(confState)
;

