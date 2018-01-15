import conf from "./conf";

import "./css.scss";
import "normalize-css/normalize.css";
import "animate.css/animate.css";
// angular
import angular from "angular";
// angular-material
import ngMaterial from "angular-material";
import "angular-material/angular-material.css";
import config from "./config";
import filters from "./filters";
import services from "./services";
import d3 from "d3";

import "./global"
import "ui-router-extras";
import runState from "./runState";

import uiBootstrap from 'angular-ui-bootstrap';
import ngFileUpload from  "ng-file-upload";

import uiRouter from "angular-ui-router";


import "./libs";

import states from "./states"

export default angular.module(`${conf.app}`, [
    uiRouter,
    ngMaterial,
    uiBootstrap,
    ngFileUpload,
    config.name,
    filters.name,
    services.name,
    states.name

    //"ct.ui.router.extras"
])
    .run(runState)
    .run();
