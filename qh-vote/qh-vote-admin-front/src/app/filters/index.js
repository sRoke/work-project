import angular from "angular";
import conf from "../conf";
import num from "./num";

export default angular.module(`${conf.app}.filters`, [
    num.name
]);
