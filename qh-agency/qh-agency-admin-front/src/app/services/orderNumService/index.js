import conf from "../../conf";
import angular from "angular";
import orderNumServiceFactory from "./orderNumServiceFactory";

export default angular.module(`${conf.app}.services.orderNumService`, [])
    .factory('orderNumService', orderNumServiceFactory)
;
