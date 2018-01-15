
var loginService;
class Controller {
    constructor(_loginService) {
        loginService = _loginService;
        loginService.loginCtl(true);
    }
}

Controller.$inject = [
    'loginService'
];

export default Controller ;
