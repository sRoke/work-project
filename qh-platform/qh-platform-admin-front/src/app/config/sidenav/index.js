import angular from "angular";
import conf from "../../conf";
import md from "angular-material";
import "angular-material-sidenav";
import uiRouter from "angular-ui-router";
import runUpdateTpl from "./runUpdateTpl";
import confSsSideNavSections from "./confSsSideNavSections";

export default angular.module(`${conf.app}.config.sidenav`, [
    uiRouter,
    md,
    'sasrio.angular-material-sidenav'
])
    .config(confSsSideNavSections)
    .run(runUpdateTpl);

