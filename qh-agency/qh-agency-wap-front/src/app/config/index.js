import angular from "angular";
import conf from "../conf";
import confNgMaterial from "./ngMaterial";
import confUrlMatcher from "./urlMatcher";
import confUrlRouter from "./urlRouter";
import confHttp from "./http";

console.log('http', confHttp);
export default angular.module(`${conf.app}.config`, [
    confNgMaterial.name,
    confUrlMatcher.name,
    confUrlRouter.name,
    confHttp.name
]);

