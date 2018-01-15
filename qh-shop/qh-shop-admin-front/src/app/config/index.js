import angular from "angular";
import conf from "../conf";
import confHttp from "./http";
import confNgMaterial from "./ngMaterial";
import confSidenav from "./sidenav";
import confUrlMatcher from "./urlMatcher";
import confUrlRouter from "./urlRouter";


export default angular.module(`${conf.app}.config`, [
    confHttp.name,
    confNgMaterial.name,
    confSidenav.name,
    confUrlMatcher.name,
    confUrlRouter.name
]);

