import conf from "../../conf";
import angular from "angular";
import userServiceFactory from "./wxServiceFactory";

export default angular.module(`${conf.app}.services.wxService`, [])
    .factory('wxService', userServiceFactory);
