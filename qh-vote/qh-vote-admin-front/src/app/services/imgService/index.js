import conf from "../../conf";
import angular from "angular";
import imgServiceFactory from "./imgServiceFactory";
import "./css.scss";

export default angular.module(`${conf.app}.services.imgService`, [])
    .factory('imgService', imgServiceFactory);
