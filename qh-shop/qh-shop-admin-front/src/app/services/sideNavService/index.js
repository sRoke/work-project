import conf from "../../conf";
import angular from "angular";
import sideNavServiceFactory from "./sideNavServiceFactory";


export default angular.module(`${conf.app}.services.sideNavService`, [])
    .factory('sideNavService', sideNavServiceFactory)
;
