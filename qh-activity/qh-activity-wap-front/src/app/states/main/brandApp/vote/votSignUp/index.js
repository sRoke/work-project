import conf from "../../../../../conf";
import angular from "angular";
import uiRouter from "angular-ui-router";
import confState from "./confState";
import "./css.scss";
import "angular-material/angular-material.css";


import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap-theme.css';

import 'angular-ui-bootstrap'
import 'angular-ui-bootstrap/dist/ui-bootstrap-csp.css'
import ngFileUpload from 'ng-file-upload';

export default angular.module(`${conf.app}.states.brandApp.vote.votSignUp`, [
    uiRouter,ngFileUpload,'ui.bootstrap'
])
    .config(confState);



