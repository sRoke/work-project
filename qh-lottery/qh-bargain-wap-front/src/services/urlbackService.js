(function () {
    "use strict";

    /**
     * 关于自动更新的相关代码
     * 参考： https://github.com/Microsoft/cordova-plugin-code-push
     */
    angular.module('qh-bargain-wap-front')
        .factory('urlbackService', urlbackService);

    urlbackService.$inject = ['$q', '$window', '$state'];
    function urlbackService($q, $window, $state) {

        return {
            urlBack: urlBack
        };

        /** 检查是否有cordova, 并绑定相应的事件函数 */
        function urlBack(url) {
            /**
             * 首页用做url跳转的链接
             * @param url
             */
            if (url.indexOf('http') > -1) {
                // 全路劲
                if ($window.cordova) {
                    // 手机app当中。进行的跳转
                    $state.go("main.iframe", {url: url}, null);
                } else {
                    // 浏览器当中做的跳转
                    location.href = url;
                }
            } else {
                $window.location.href = $window.location.protocol + "//" + $window.location.host + $window.location.pathname + $window.location.search + url;
            }
        }
    }
})();
