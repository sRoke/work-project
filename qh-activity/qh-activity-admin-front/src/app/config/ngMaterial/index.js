import angular from "angular";
import conf from "../../conf";
import confMdTheming from "./confMdTheming";
import confMdGestureProvider from "./confMdGestureProvider"
import confMdDateLocaleProvider from "./confMdDateLocaleProvider"

import ngMaterial from "angular-material";

export default angular.module(`${conf.app}.config.ngMaterial`, [
    ngMaterial
])
    .config(confMdTheming)

    .config(confMdGestureProvider)
    // .config(confMdDateLocaleProvider)
;

