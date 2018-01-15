enumServiceFactory.$inject = ['$log', 'appConfig', '$injector', '$http', '$q'];
function enumServiceFactory($log, appConfig, $injector, $http, $q) {

    var codes = null;

    /**
     * 所有枚举值。因为单例，可减少网络请求次数。
     * 使用示例：
     *
     * enumService
     * enumService.getCodes().then(function (codes) {
         *    console.log( codes.ServiceResultEnum);                // 访问特定的枚举（数组类型）
         *    console.log( codes.ServiceResultEnum[0]);             // 通过下标访问特定的枚举的一个枚举值
         *    console.log( codes.ServiceResultEnum.UNPROCESSED);    // 通过特定枚举的名称访问。
         * });
     */
    return {
        getCodes: getCodes,
        reload: reload
    };

    // -------------------------------

    /**
     * 获取所有代码值。如果尚未获取，则重新获取（只重新获取一次）
     *
     * @return promise
     */
    function getCodes() {
        if (codes) {
            return $q.resolve(codes);
        }
        return reload();
    }

    /**
     * 重新加载所有的枚举值。
     * @return promise
     */
    function reload() {
        return $http({
            method: 'get',
            url: appConfig.apiPath + "/enum/codes"
        }).then(function (resp) {
            codes = resp.data.data;
            addKeyToArr(codes);
            return $q.resolve(codes);
        }, function (reason) {
            return $q.reject(reason);
        });
    }


    /***
     * 改造数组，使之能够直接通过 枚举的名字进行访问元素。
     */
    /* 示例：
     *
     * codes = {
     *  XxxEnum:[{
     *     name:"YYY",
     *     description:""
     *  },{},{}]
     * }
     *
     * 使之可以
     * codes.XxxEnum.YYY === codes.XxxEnum[0]
     */
    function addKeyToArr(codes) {

        for (var enumType in codes) {
            if (!codes.hasOwnProperty(enumType)) {
                continue;
            }

            codes[enumType].forEach(function (item) {
                codes[enumType][item.name] = item;
            });
        }
    }
}


export default enumServiceFactory;
