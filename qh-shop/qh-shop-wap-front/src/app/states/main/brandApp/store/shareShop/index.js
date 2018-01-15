import conf from "../../../../../conf";
import angular from "angular";
import uiRouter from "angular-ui-router";
import confState from "./confState";
import "./css.scss";




import qrcode from 'qrcode-generator';
import  'angular-qrcode';
window.qrcode = qrcode;
require('qrcode-generator/qrcode_UTF8');

export default angular.module(`${conf.app}.states.brandApp.store.shareShop`, [
        uiRouter,'monospaced.qrcode'
    ])
    .config(confState);
