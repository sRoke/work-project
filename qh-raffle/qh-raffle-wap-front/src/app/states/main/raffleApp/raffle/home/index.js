import conf from "../../../../../conf";
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



// console.log("================== $" , $);
//
// import $_ from "jquery";

// console.log("================== $_" , $_);


// console.log("----------------------- global = ",global);
// console.log("----------------------- window = ",window);
// console.log("----------------------- global === window ", (global === window));

export default angular.module(`${conf.app}.states.raffleApp.raffle.home`, [
    uiRouter,ngFileUpload
])
    .config(confState);



