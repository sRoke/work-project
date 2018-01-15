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
import "layui-laydate/src/theme/default/laydate.css"
import uiRouter from "angular-ui-router";


import "./libs";

import states from "./states";
import uiBootstrap from "angular-ui-bootstrap";

export default angular.module(`${conf.app}`, [
    uiRouter,
    ngMaterial,
    config.name,
    filters.name,
    services.name,
    states.name,
    uiBootstrap


    //"ct.ui.router.extras"
])
    .run(runState)
    .run();
