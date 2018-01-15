import conf from "../../../../../conf";
import angular from "angular";
import uiRouter from "angular-ui-router";
import confState from "./confState";
import "./css.scss";
import ngFileUpload from  "ng-file-upload";

//引入lay.css
import "layui-laydate/src/theme/default/laydate.css"

export default angular.module(`${conf.app}.states.brandApp.marketing.creatVote`, [
    uiRouter, ngFileUpload
]).config(confState);







