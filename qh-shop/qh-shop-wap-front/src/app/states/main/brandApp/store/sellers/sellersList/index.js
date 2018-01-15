import conf from "../../../../../../conf";
import angular from "angular";
import uiRouter from "angular-ui-router";
import confState from "./confState";
import "./css.scss";

// import  '../../../../../../thirdJs/qrcode-generator/js/qrcode';
//
// import qrcode from 'qrcode-generator';

// import ngQrcode from 'angular-qrcode';

// import qrcode from 'qrcode-generator';
// global.qrcode = qrcode;
// import qrcode from 'qrcode-generator';
// import ngQrcode from 'angular-qrcode';
// import   '../../../../../../thirdJs/qrcode-generator/js/qrcode_UTF8';

// console.log('11111111111111111',ngQrcode);


import qrcode from 'qrcode-generator';
import  'angular-qrcode';

// hacks for the browser
// if using webpack there is a better solution below
window.qrcode = qrcode;
require('qrcode-generator/qrcode_UTF8');


export default angular.module(`${conf.app}.states.brandApp.store.sellers.sellersList`, [
    uiRouter, 'monospaced.qrcode'
])
    .config(confState);
