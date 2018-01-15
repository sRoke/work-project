import conf from "../../conf";
import angular from "angular";
import qiniuUploadServiceFactory from "./qiniuUploadServiceFactory";

export default angular.module(`${conf.app}.services.qiniuUploadService`, [])
    .factory('qiniuUploadService', qiniuUploadServiceFactory)
;
