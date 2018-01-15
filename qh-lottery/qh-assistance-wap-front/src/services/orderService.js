angular.module('qh-assistance-wap-front').factory('orderService', ['$http', '$q', '$state', '$log', 'appConfig', function ($http, $q, $state, $log, appConfig) {
    //现在是用来作为服务洗衣篮状态保持用 
    return {
        basket: [],
        //------------------------END
        resetOrderBaseOn: function (initObj) {
            this.basket = angular.extend([], initObj);
        }
    };
}]);