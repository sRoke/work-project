import conf from "../conf";
import angular from "angular";

import alertService from "./alertService";
import userService from "./userService";
import loginService from "./loginService";
import addressService from  './addressService';
import wxService from  './wxService';

import authService from  './authService';

export default angular.module(`${conf.app}.services`, [
    alertService.name,
    userService.name,
    loginService.name,
    addressService.name,
    wxService.name,
    authService.name,
]);
