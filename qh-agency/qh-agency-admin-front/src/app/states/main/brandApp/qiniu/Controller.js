import conf from "../../../../conf";

var $scope,
    $http,
    loginService,
    Upload,
    $state;

Controller.$inject = [
    '$scope',
    '$http',
    'loginService',
    'Upload',
    '$state'];
function Controller(_$scope,
                    _$http,
                    _loginService,
                    _Upload,
                    _$state) {
    $scope = _$scope;
    $http = _$http;
    Upload = _Upload;
    $state = _$state;
    loginService = _loginService;
    loginService.loginCtl(true);
    $scope.uploadImg = function (file) {
        $scope.f = file;
        // $scope.errFile = errFiles && errFiles[0];
        if (file) {
            file.upload = Upload.upload({
                url: conf.apiPath + "/uploadImg/uploadImg?imgType=UPLOAD&type=UPLOAD&X-Requested-With=XMLHttpRequest",
                data: {excelFile: file}
            });

            file.upload.then(function (data) {

                if (data.data.data.code == 'SUCCESS') {
                    $scope.imgs = data.data.data.file_path;

                } else {
                    var msg = object.msg;
                    $window.alert(msg);
                }
            });
        }
    }
}

export default Controller;
