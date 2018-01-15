import conf from "../conf";
import angular from "angular";


import ksFooterMenu from "./ksFooterMenu";
import ngMobileClick from "./ngMobileClick";
import stopTouchEvent from "./stopTouchEvent";
import contenteditable from "./contenteditable"

export default angular.module(`${conf.app}.directives`, [
    ksFooterMenu.name,
    ngMobileClick.name,
    stopTouchEvent.name,
    contenteditable.name
]);
