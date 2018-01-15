import conf from "../../conf";
import angular from "angular";
import enumServiceFactory from "./enumServiceFactory";

export default angular.module(`${conf.app}.services.enumService`, [])
    .factory('enumService', enumServiceFactory)
;
