import conf from "../../conf";
import angular from "angular";
import sidenavTab from "./sidenavTab";
import runOnState from "./runOnState";

export default angular.module(`${conf.app}.services.sidenavTab`, [])
    .service('sidenavTab', sidenavTab)
    .run(runOnState)
;
