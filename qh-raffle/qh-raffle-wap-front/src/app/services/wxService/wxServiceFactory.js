import angular from "angular";
import conf from "../../conf";
var wx = window.wx = require('weixin-js-sdk');
wxServiceFactory.$inject = ['$q', '$http', '$stateParams','$rootScope'];
function wxServiceFactory($q, $http, $stateParams,$rootScope,$scope) {
    var WXreturn = {
        init: init,
        shareFriend: shareFriend,
        shareRing: shareRing,
        wxUploadImg: wxUploadImg,
        initShareOnStart: initShareOnStart,
        chooseImage: chooseImage,
        // wxLogin: wxLogin,
        // getUserInfo: getUserInfo,
        isInWx: isInWx,
        initShareOnStartResult: false,
    };


    /**
     * 初始化微信
     */
    function init() {
        var deferred = $q.defer();
        var ua = window.navigator.userAgent.toLowerCase();
        if (ua.match(/MicroMessenger/i) && !ua.match(/windows/i)) {
            if($rootScope.wxComAppId && $rootScope.wxMpAppId){
                $http.get(conf.wxPath + "/wxCom/" + $rootScope.wxComAppId + "/mp/" + $rootScope.wxMpAppId + "/jsAt/conf", {
                    params: {
                        url: location.href.split('#')[0]
                    }
                }).then(function (resp) {
                    resp.data.data.debug = false;
                    console.info("已经获取了微信JS SDK 的配置对象", resp.data.data);
                    //$scope.wxReady = true;
                    resp.data.data.jsApiList = [
                        "startRecord",
                        "stopRecord",
                        "onVoiceRecordEnd",
                        "chooseImage",
                        "imagePreview",
                        "uploadImage",
                        "downloadImage",
                        "translateVoice",
                        "getNetworkType",
                        "openLocation",
                        "geoLocation",
                        "hideOptionMenu",
                        "showOptionMenu",
                        "hideMenuItems",
                        "showMenuItems",
                        "hideAllNonBaseMenuItem",
                        "showAllNonBaseMenuItem",
                        "closeWindow",
                        "scanQRCode",
                        "getBrandWCPayRequest",
                        "openProductViewWithPid",
                        "batchAddCard",
                        "chooseCard",
                        "batchViewCard",
                        "onMenuShareTimeline",
                        "onMenuShareAppMessage"
                    ];
                    wx.config(resp.data.data);
                    wx.isConfig = true;
                    wx.error(function (res) {
                        console.info("微信调用出错了 ", res);
                    });
                    wx.ready(function () {
                        deferred.resolve(true);
                    });

                });
            }else {
              // /raffleApp/{raffleAppId}   Get
                $http({
                    method: "GET",
                    url: conf.apiPath + "/raffleApp/" + $stateParams.raffleAppId,
                    headers: {
                        //'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "raffleApp-Id": $stateParams.raffleAppId
                    },
                }).then(function (resp) {
                    console.log('resp-raffleApp',resp);
                    $rootScope.brandAppId=resp.data.data;
                    $http({
                        method: "GET",
                        url: conf.pfApiPath + "/brandApp/" + $rootScope.brandAppId,
                        headers: {
                            "brandApp-Id": $rootScope.brandAppId
                        },
                        data: {}
                    }).success(function (resp) {
                        $rootScope.wxComAppId = resp.data.wxComAppId;
                        $rootScope.wxMpAppId = resp.data.wxMpId;
                        $http.get(conf.wxPath + "/wxCom/" + resp.data.wxComAppId + "/mp/" + resp.data.wxMpId + "/jsAt/conf", {
                            params: {
                                url: location.href.split('#')[0]
                            }
                        }).then(function (resp) {
                            resp.data.data.debug = false;
                            console.info("已经获取了微信JS SDK 的配置对象", resp.data.data);
                            //$scope.wxReady = true;
                            resp.data.data.jsApiList = [
                                "startRecord",
                                "stopRecord",
                                "onVoiceRecordEnd",
                                "chooseImage",
                                "imagePreview",
                                "uploadImage",
                                "downloadImage",
                                "translateVoice",
                                "getNetworkType",
                                "openLocation",
                                "getLocation",
                                "hideOptionMenu",
                                "showOptionMenu",
                                "hideMenuItems",
                                "showMenuItems",
                                "hideAllNonBaseMenuItem",
                                "showAllNonBaseMenuItem",
                                "closeWindow",
                                "scanQRCode",
                                "getBrandWCPayRequest",
                                "openProductViewWithPid",
                                "batchAddCard",
                                "chooseCard",
                                "batchViewCard",
                                "onMenuShareTimeline",
                                "onMenuShareAppMessage"
                            ];
                            wx.config(resp.data.data);
                            wx.isConfig = true;
                            wx.error(function (res) {
                                console.info("微信调用出错了 ", res);
                            });
                            wx.ready(function () {
                                deferred.resolve(true);
                            });
                        });
                    });
                }, function (resp) {
                    //error
                });


            }
        } else {
            deferred.resolve(false);
        }
        return deferred.promise;
    }

    /*
     * 判断是否在微信
     * */
    function isInWx() {
        var ua = window.navigator.userAgent.toLowerCase();
        return ua.match(/MicroMessenger/i) && !ua.match(/windows/i)
    }

    /*
     * 设置默认分享模板
     * */
    var defaultShareRingConf = {
        title: "小皇叔",
        desc: "您的朋友分享了一个链接给你",
        link: location.href,
        imgUrl: "https://img.kingsilk.net/FipIBAo39SLMX04AAwxV-RrjV2G1",
    };
    // 分享到朋友圈
    function shareRing(listener) {
        // if (!isInWx()) {
        //     return;
        // }
        // alert(111);
        wx.onMenuShareTimeline(listener ? listener : defaultShareRingConf);
    }

    // 发送给朋友
    function shareFriend(listener) {
        // if (!isInWx()) {
        //     return;
        // }
        wx.onMenuShareAppMessage(listener ? listener : defaultShareRingConf);
    }

    // 微信上传图片
    function wxUploadImg() {
        var deferred = $q.defer();
        wx.chooseImage({
            count: 1, // 默认9
            sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
            sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
            success: function (res) {
                var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
                if (res.localIds.length === 1) {
                    wx.uploadImage({
                        localId: localIds[0], // 需要上传的图片的本地ID，由chooseImage接口获得
                        isShowProgressTips: 1, // 默认为1，显示进度提示
                        success: function (res) {
                            var serverId = res.serverId; // 返回图片的服务器端ID
                            $http.get(conf.apiPath + '/common/uploadImage', {
                                params: {
                                    media_id: serverId
                                }
                            }).then(function (resp) {
                                deferred.resolve(resp.data);
                                //console.log("上传图片成功");
                            });
                        }
                    });
                }
            }
        });
        return deferred.promise;
    }

    // 微信上传图片
    function chooseImage(count) {
        if (!count) {
            count = 1;
        }
        var deferred = $q.defer();
        wx.chooseImage({
            count: count, // 默认9
            sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
            sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
            success: function (res) {
                var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
                deferred.resolve(localIds);
            }
        });
        return deferred.promise;
    }

    /*
     * 初始化微信分享
     * */
    function initShareOnStart(jsonWx) {
        var deferred = $q.defer();
        // $timeout(function () {
        init().then(function (data) {
            if (data) {
                shareRing(jsonWx);
                shareFriend(jsonWx);
            }
            deferred.resolve(true);
        });

        WXreturn.initShareOnStartResult = deferred.promise;
        return deferred.promise;
        // }, 1000);
    }

    // function wxLogin(urls, loginUrl) {
    //     var url = "";
    //     if (urls) {
    //         // 在这里要重新给url解码后在重新编码
    //         // url = decodeURIComponent(urls);
    //         url = encodeURIComponent(urls);
    //     }
    //     if (loginUrl) {
    //         // 在这里要重新给url解码后在重新编码
    //         // url = decodeURIComponent(urls);
    //         loginUrl = encodeURIComponent(loginUrl);
    //     }
    //     var ua = window.navigator.userAgent.toLowerCase();
    //     if (ua.match(/MicroMessenger/i) && !ua.match(/windows/i)) {
    //         $http
    //             .get(conf.apiPath + '/weiXin/wxOauthLogin?backUrl=' + url + "&url=" + loginUrl)
    //             .success(function (data) {
    //                 window.location.href = data.uri;
    //             });
    //     } else if (window.cordova) {
    //         var scope = "snsapi_userinfo";
    //         window.Wechat.isInstalled(function (installed) {
    //             if (!installed) {
    //                 alertService.msgAlert("cancle", "您尚未安装微信!");
    //                 return;
    //             }
    //             // 获取登录用的 state
    //             $http({
    //                 method: "POST",
    //                 url: conf.apiPath + '/weiXin/genLoginState',
    //                 headers: {
    //                     'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
    //                 }
    //             }).success(function (genState) {
    //                 // 调用微信 APP 进行登录
    //                 window.Wechat.auth(scope, genState.state, function (response) {
    //                     //$log.log("====== Wechat.auth = " + JSON.stringify(response));
    //                     // you may use response.code to get the access token.
    //                     // 进行微信登陆
    //                     $http.get(conf.apiPath + "/weiXin/wxLoginVerify?code=" + response.code + "&state=" + response.state + "&wxType=qhApp&url=" + loginUrl)
    //                         .then(function (resp) {
    //                             if (resp.data.code === "NOT_WEIXIN") {
    //                                 var loginUrls = "main.newLogin.bindPhone";
    //                                 if (loginUrl) {
    //                                     loginUrl = loginUrl.replace(new RegExp(/\//g), '.');
    //                                     loginUrls = "mian" + loginUrl;
    //                                 }
    //                                 $state.go(loginUrls, {backUrl: urls}, null);
    //                                 return;
    //                             }
    //                             urls = decodeURIComponent(urls);
    //                             location.href = urls;
    //                         }, function (resp) {
    //                             if (resp.data.code === "NOT_WEIXIN") {
    //                                 $state.go("main.newLogin.bindPhone", {backUrl: urls}, null);
    //                             }
    //                         });
    //                 }, function (reason) {
    //                     // console.log("=== WeiXin login Failed : " + reason);
    //                 });
    //
    //             }).error(function (data, status, headers, config) {
    //                 //redirect(uri: defaultBackUrl + "#/register/wx?backUrl=" + backUrlEncode);
    //             });
    //
    //         }, function (reason) {
    //             alertService.msgAlert("cancle", "您尚未安装微信!");
    //         });
    //
    //     } else {
    //         $http
    //             .get(conf.apiPath + '/weiXin/wxWebLogin?backUrl=' + url + "&url=" + loginUrl)
    //             .success(function (data) {
    //                 window.location.href = data.uri;
    //             });
    //     }
    // }
    // function getUserInfo(userId) {
    //     if (!userId) {
    //         userId = $cookies.get("staffactivity_fromId");
    //     }
    //     var deferred = $q.defer(); //创建一个等待的意思 先后顺序
    //     $http({
    //         method: "GET",
    //         url: conf.apiPath + '/user/userInfo',
    //         params: {
    //             userId: userId,
    //         }
    //     }).success(function (resp) {
    //         if (resp.code === 'SUCCESS') {
    //             deferred.resolve(resp);
    //         }
    //     }).error(function (resp) {
    //         deferred.reject(resp);
    //     });
    //     return deferred.promise;
    // }

    return WXreturn;
}


export default  wxServiceFactory;