import conf from "../../conf";
import angular from "angular";
import errorServiceFactory from "./errorServiceFactory";

export default angular.module(`${conf.app}.services.errorService`, [])
    .factory('errorService', errorServiceFactory)
;
