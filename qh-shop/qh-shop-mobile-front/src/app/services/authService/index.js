import conf from "../../conf";
import angular from "angular";
import authServiceFactory from "./authServiceFactory";

export default angular.module(`${conf.app}.services.authService`, [])
    .factory('authService', authServiceFactory)
;
