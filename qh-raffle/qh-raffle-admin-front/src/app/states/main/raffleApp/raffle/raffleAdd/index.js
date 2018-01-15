import conf from "../../../../../conf";
import angular from "angular";
import uiRouter from "angular-ui-router";
import confState from "./confState";
import "./css.scss";
import "angular-material/angular-material.css";


// import 'bootstrap/dist/css/bootstrap.css';
// import 'bootstrap/dist/css/bootstrap-theme.css';

import 'angular-ui-bootstrap'
// import $_ from "expose-loader?$!jquery";
import 'angular-ui-bootstrap/dist/ui-bootstrap-csp.css'
import ngFileUpload from 'ng-file-upload';
//
// global.$ = $_;
// global.jQuery = $_;
// global.jquery = $_;
// global.jq = $_;


export default angular.module(`${conf.app}.states.raffleApp.raffle.raffleAdd`, [
    uiRouter,ngFileUpload,'ui.bootstrap'
])
    .config(confState);



