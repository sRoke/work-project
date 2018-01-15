import conf from "../../conf";
import angular from "angular";
import loginServiceFactory from "./loginServiceFactory";


export default angular.module(`${conf.app}.services.loginService`, [])
    .factory('loginService', loginServiceFactory);
