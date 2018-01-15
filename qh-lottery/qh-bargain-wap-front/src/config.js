(function () {
    angular.module('qh-bargain-wap-front')

        .config(['$urlMatcherFactoryProvider', function ($urlMatcherFactoryProvider) {
            $urlMatcherFactoryProvider.strictMode(false);
        }])

        .config(['$urlRouterProvider', function ($urlRouterProvider) {
            $urlRouterProvider.otherwise('/');
        }])

        .config(['$provide', '$httpProvider', function ($provide, $httpProvider) {
            $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

            $provide.factory('myHttpInterceptor', ['$log', '$q', 'errorService', function ($log, $q, errorService) {
                return {
                    // optional method
                    'response': function (response) {
                        // 配置禁止全局异常处理
                        if (response.config.skipGlobalErrorHandler) {
                            return response;
                        }

                        // 非JSON数据
                        var contentType = response.headers('Content-Type');
                        if (!contentType || contentType.indexOf('application/json') !== 0) {
                            return response;
                        }

                        // JSON 响应结果是成功的结果
                        var respData = response.data;
                        if (respData && respData.code && respData.code === 'SUCCESS'
                            || respData && respData.code && respData.raw === true) {
                            return response;
                        }

                        // 默认错误结果
                        var resultJson = {
                            msg: "服务器异常，请稍后重试",
                            raw: false,  // 始终为false
                            code: 'ERROR',
                            rawMsg: null
                        };

                        if (typeof respData.code === 'string' && respData.code) {
                            resultJson.code = respData.code;

                            if (respData.code !== 'SUCCESS') {
                                if (respData.raw) {
                                    if (respData.msg) {
                                        resultJson.rawMsg = respData.msg;
                                    }
                                } else {
                                    if (respData.msg) {
                                        resultJson.msg = respData.msg;
                                    }
                                }
                            }
                        }

                        //if (!response.config.notShowError) {
                        //    errorService.errors(resultJson.msg);
                        //}
                        //console.log(response)
                        //console.log(resultJson)
                        if (resultJson.code === 'NOT_LOGINED') {
                            if (!response.config.notShowError && response.config.showLoginError) {
                                // errorService.errors(resultJson.msg);
                                console.log(1);
                            }
                        } else {
                            if (!response.config.notShowError) {
                                // errorService.errors(resultJson.msg);
                                console.log(2);
                            } else if (resultJson.code === "ERROR") {
                                // errorService.errors(resultJson.msg);
                                console.log(3);
                            }
                        }

                        response.oldData = response.data;
                        response.data = resultJson;
                        return $q.reject(response);
                    },

                    // 401 404 500 等错误
                    'responseError': function (response) {
                        // 配置禁止全局异常处理
                        if (response.config.skipGlobalErrorHandler) {
                            console.log("!111")
                            return $q.reject(response);
                        }


                        // 默认错误结果
                        var resultJson = {
                            msg: "系统错误，请稍后重试",
                            raw: false,  // 始终为false
                            code: 'ERROR',
                            rawMsg: null
                        };

                        var contentType = response.headers('Content-Type');
                        if (contentType == null) {
                            resultJson.msg = "网络连接异常";
                            resultJson.code = "UNKNOWN";
                        } else if (contentType && (contentType.indexOf('application/json') === 0)) {
                            var respData = response.data;
                            if (typeof respData.code === 'string' && respData.code) {
                                resultJson.code = respData.code;
                            }
                            if (respData.raw) {
                                if (respData.msg) {
                                    resultJson.rawMsg = respData.msg;
                                }
                            } else {
                                if (respData.msg) {
                                    resultJson.msg = respData.msg;
                                }
                            }
                        }
                        if (resultJson.code === 'NOT_LOGINED') {
                            if (!response.config.notShowError && response.config.showLoginError) {
                                // errorService.errors(resultJson.msg);
                                console.log(4);
                            }
                        } else {
                            if (resultJson.code !== "UNKNOWN" && !response.config.notShowError) {
                                // errorService.errors(resultJson.msg);
                                console.log(5);
                            } else if (resultJson.code == "UNKNOWN") {
                                // errorService.errors(resultJson.msg, true);
                                console.log(6);
                            }
                        }
                        response.oldData = response.data;
                        response.data = resultJson;

                        return $q.reject(response);
                    }
                };
            }]);

            $httpProvider.interceptors.push('myHttpInterceptor');
        }])

        .run(['$rootScope', '$interval', '$state', '$stateParams', '$log', '$cacheFactory', 'errorService','wxService',
            function ($rootScope, $interval, $state, $stateParams, $log, $cacheFactory, errorService,wxService) {

                $rootScope.$state = $state;
                $rootScope.$stateParams = $stateParams;

                // 缓存商品的详情
                $rootScope.itemCache = $cacheFactory('itemCache');
                // 缓存网店详情
                $rootScope.agentCache = $cacheFactory('agentCache');
                // 缓存公司详情
                $rootScope.pageCache = $cacheFactory('pageCache');
                // 缓存活动详情
                $rootScope.activityCache = $cacheFactory('activityCache');
                // 缓存蚕丝被翻新添补各类的介绍详情
                $rootScope.serviceOrderCache = $cacheFactory('serviceOrderCache');
                // 服务承诺详情
                $rootScope.productCache = $cacheFactory('productCache');
                // 前端商品的活动后的商品进行的缓存
                $rootScope.activitySkuCache = $cacheFactory('activitySkuCache');


                $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {
                    // $log.debug("$stateChangeStart : fromState = " + JSON.stringify(fromState.name) + ", toState = " + JSON.stringify(toState.name));
                    var url = window.location.href;
                    var ua = window.navigator.userAgent.toLowerCase();
                    if (!ua.match(/android/i) && !ua.match(/MicroMessenger/i) && !ua.match(/iphone/i)) {
                        // 检查url是否输入错误
                        // 查找url中的#
                        var o = url.indexOf("#");
                        var y = url.indexOf("?");
                        if (o > 0 && (y > o || y === -1)) {
                            // 替换新的url  重新刷新url
                            url = url.substr(0, o) + "?showwxpaytitle=1" + url.substr(o);
                            location.href = url;
                        }
                    }
                    $interval.cancel($rootScope.intervalStop);
                    $rootScope.intervalStop = null;
                    // if (window.cordova) {
                    //     if (cordova.platformId === 'ios') {
                    //         var toStates = ["main.center", "main.wallet", "main.wallet.balanceDetail", "main.user.codee"]; // #333333
                    //         for (var i = 0; i < toStates.length; i++) {
                    //             if (toStates[i] === toState.name) {
                    //                 window.StatusBar.backgroundColorByHexString("#333");
                    //                 window.StatusBar.styleLightContent();
                    //                 return;
                    //             }
                    //         }
                    //         window.StatusBar.backgroundColorByHexString("#FFF");
                    //         window.StatusBar.styleDefault();
                    //     }
                    // }
                });

                $rootScope.$on('$stateNotFound', function (event, unfoundState, fromState, fromParams) {
                    //$log.debug("$stateNotFound : fromState = " + JSON.stringify(fromState.name) + ", toState = " + JSON.stringify(unfoundState.name));
                });

                $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
                    $rootScope.errorsMsg = true;
                    //$log.debug("$stateChangeSuccess : fromState = " + JSON.stringify(fromState.name) + ", toState = " + JSON.stringify(toState.name));

                    wxService.initShareOnStart();
                });
                $rootScope.$on('$stateChangeError', function (event, toState, toParams, fromState, fromParams, error) {
                    //$log.debug("$stateChangeError : fromState = " + JSON.stringify(fromState.name) + ", toState = " + JSON.stringify(toState.name) + ", error = ", error);
                    // 未登录
                    // https://github.com/angular-ui/ui-router/issues/42
                    if (error && error.data && error.data.code === 'NOT_LOGINED') {
                        //errorService.errors(error.data.msg ? error.data.msg : "请先登录", function () {
                        $rootScope._savedState = {
                            fromStateName: fromState.name,
                            fromStateParams: fromParams
                        };
                        $state.go("main.login", {backUrl: window.location.href});
                        //$state.go("main.login", {s:fromState.name,src: window.location.href});
                        //});
                    }
                });

                $rootScope.$on('$viewContentLoading', function (event, viewConfig) {
                });
                $rootScope.$on('$viewContentLoaded', function (event) {
                });
            }])

        .run(['$rootScope', '$state', '$stateParams', function ($rootScope, $state, $stateParams) {
            $rootScope.$state = $state;
            $rootScope.$stateParams = $stateParams;
        }])

        .run(['updateService', function (updateService) {
            updateService.init();
        }])
        .run(['$cookies', '$location', function ($cookies, $location) {
            var params = $location.absUrl();
            var indexOfOrgId = $location.absUrl().indexOf("orgId=");
            var indexOfEnd = $location.absUrl().indexOf("#/");
            var orgIdString = params.substring(indexOfOrgId + 6, indexOfEnd);
            $cookies.orgId = orgIdString;
            document.cookie = 'orgId=' + orgIdString;
        }])
})();
