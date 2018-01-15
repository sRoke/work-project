import conf from "../../conf";
import angular from "angular";
import userServiceFactory from "./userServiceFactory";

export default angular.module(`${conf.app}.services.userService`, [])
    .factory('userService', userServiceFactory);
