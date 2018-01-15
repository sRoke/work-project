import conf from "../../conf";
import angular from "angular";
import commonServiceFactory from "./commonServiceFactory";

export default angular.module(`${conf.app}.services.commonService`, [])
    .factory('commonService', commonServiceFactory)
;
