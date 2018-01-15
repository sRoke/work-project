errorServiceFactory.$inject = ['$injector'];
function errorServiceFactory($injector) {
    /**
     * 弹出错误消息提示
     */
    function error(msg, onCloseFn) {
        //var $ = jQuery;
        //$.notifyClose(); // 先关闭之前所有的消息

        var $mdToast = $injector.get('$mdToast');
        $mdToast.show($mdToast.simple()
            .textContent(msg)
            .position("top right")
            .hideDelay(3000)
        );
    }

    return {
        error: error
    };
}
export default errorServiceFactory;

