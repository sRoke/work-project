/**
 * Created by susf on 17-7-25.
 */
import conf from "../../conf";
import angular from "angular";
import addressServiceFactory from "./addressServiceFactory";

export default angular.module(`${conf.app}.services.addressService`, [])
    .factory('addressService', addressServiceFactory)
;
