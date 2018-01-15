import conf from "../../../../conf";
import angular from "angular";
import uiRouter from "angular-ui-router";
import confState from "./confState";
import "./css.scss";
import "angular-material/angular-material.css";

import $_ from "expose-loader?$!jquery";

import ngFileUpload from 'ng-file-upload';

global.$ = $_;
global.jQuery = $_;
global.jquery = $_;
global.jq = $_;


export default angular.module(`${conf.app}.states.voteApp.vote`, [
    uiRouter,ngFileUpload
])
    .config(confState);



