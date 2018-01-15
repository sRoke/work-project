orderNumServiceFactory.$inject = ['$log', 'appConfig', '$injector', '$state', '$http', '$interval', '$rootScope', '$timeout'];
function orderNumServiceFactory($log, appConfig, $injector, $state, $http, $interval, $rootScope, $timeout) {
    var _this = {
        update: update,
        rentOrderId: [],
        orderId: [],
        serviceId: [],
        refundId: [],
        rentUserId: []
    };
    return _this;

    // return {
    //     update: update,
    //     rentOrderId: rentOrderId,
    //     orderId: orderId,
    //     refundId: refundId,
    //     rentUserId: rentUserId
    // };

    // -------------------------------

    /**
     * 获取通知的数量更新menu
     */
    function update() {
        getDate();
    }

    /**
     *
     * @param musicSrc;播放语音目录
     * @param isPlay;是否播放
     */
    function playMusic(playMp3) {
        var flag = false;
        for (var i = 0; i < playMp3.length; i++) {
            if (playMp3[i].isPlay) {
                flag = true;
                break;
            }
        }

        if (flag) {
            for (var j = 0; j < playMp3.length; j++) {
                if (playMp3[j].isPlay) {
                    var audio = document.createElement("audio");
                    audio.src = playMp3[j].src;
                    audio.play();
                    audio.addEventListener('ended', function () {
                        audio.remove();
                        playMp3[j].isPlay = false;
                        playMusic(playMp3);
                    }, false);
                    break;
                }
            }
        }
    }

    /**
     * 获取数据
     */
    function getDate() {
        /* console.log("向后台请求数据");*/
        //发送请求
        $http({
            method: 'get',
            url: appConfig.apiPath + '/notify/getData',
            params: {
                time: $rootScope.pollTimeGetTime
            }
        }).success(function (data) {
            var playMp3 = [
                {id: "rentUser", isPlay: false, src: "assets/rentUser.mp3"},
                {id: "order", isPlay: false, src: "assets/order.mp3"},
                {id: "rentOrder", isPlay: false, src: "assets/rentOrder.mp3"},
                {id: "refund", isPlay: false, src: "assets/refund.mp3"}
            ];

            var numbers = data.data[0];
            $rootScope.pollTimeGetTime = numbers.time;
            // console.log("$rootScope.pollTimeGetTime:"+$rootScope.pollTimeGetTime)
            //播放语音
            var ssSideNavSections = $injector.get('ssSideNavSections');
            var sections = ssSideNavSections.sections;
            //更新menu
            for (var i = 0; i < sections.length; i++) {
                if (sections[i].name === '人员管理') {
                    for (var j = 0; j < sections[i].pages.length; j++) {
                        if (sections[i].pages[j].name === '租赁会员') {
                            if (sections[i].pages[j].number === undefined) {

                                sections[i].pages[j].number = numbers.rentUser;
                                sections[i].number = sections[i].pages[j].number;
                            } else if (numbers.rentUser > 0) {
                                sections[i].pages[j].number += numbers.rentUser;
                                playMp3[0].isPlay = true;
                                sections[i].number = sections[i].pages[j].number;
                            }
                            if (numbers.rentUserId) {
                                _this.rentUserId = _this.rentUserId.concat(numbers.rentUserId)
                            }
                        }
                    }
                } else if (sections[i].name === '商品订单') {
                    //sections[i].number = numbers.order
                    for (var j = 0; j < sections[i].pages.length; j++) {
                        if (sections[i].pages[j].name === '订单管理') {
                            if (sections[i].pages[j].number === undefined) {
                                sections[i].pages[j].number = numbers.order;
                                sections[i].number = sections[i].pages[j].number;
                            } else if (numbers.order > 0) {
                                sections[i].pages[j].number += numbers.order;
                                playMp3[1].isPlay = true;
                                sections[i].number = sections[i].pages[j].number;
                            }
                            if (numbers.orderId) {
                                _this.orderId = _this.orderId.concat(numbers.orderId)
                            }
                        }
                    }
                } else if (sections[i].name === '服务订单') {
                    //sections[i].number = numbers.serviceOrder
                    for (var j = 0; j < sections[i].pages.length; j++) {
                        if (sections[i].pages[j].name === '订单管理') {
                            if (sections[i].pages[j].number === undefined) {
                                sections[i].pages[j].number = numbers.serviceOrder;
                                sections[i].number = sections[i].pages[j].number;
                            } else if (numbers.serviceOrder > 0) {
                                sections[i].pages[j].number += numbers.serviceOrder;
                                playMp3[1].isPlay = true;
                                sections[i].number = sections[i].pages[j].number;
                            }
                            if (numbers.serviceId) {
                                _this.serviceId = _this.serviceId.concat(numbers.serviceId)
                            }
                        }
                    }
                } else if (sections[i].name === '租赁订单') {
                    for (var r = 0; r < sections[i].pages.length; r++) {
                        if (sections[i].pages[r].name === '订单管理') {
                            if (sections[i].pages[r].number === undefined) {
                                sections[i].pages[r].number = numbers.rentOrder;
                                sections[i].number = sections[i].pages[r].number;
                            } else if (numbers.rentOrder > 0) {
                                sections[i].pages[r].number += numbers.rentOrder;
                                playMp3[2].isPlay = true;
                                sections[i].number = sections[i].pages[r].number;
                            }
                            if (numbers.rentOrderId) {
                                _this.rentOrderId = _this.rentOrderId.concat(numbers.rentOrderId)
                            }
                        }
                    }
                } else if (sections[i].name === '售后管理') {
                    for (var t = 0; t < sections[i].pages.length; t++) {
                        if (sections[i].pages[t].name === '退货管理') {
                            if (sections[i].pages[t].number === undefined) {
                                sections[i].pages[t].number = numbers.refund;
                                sections[i].number = sections[i].pages[t].number;
                            } else if (numbers.refund > 0) {
                                sections[i].pages[t].number += numbers.refund;
                                sections[i].number = sections[i].pages[t].number;
                                playMp3[3].isPlay = true;
                            }
                            if (numbers.refundId) {
                                _this.refundId = _this.refundId.concat(numbers.refundId)
                            }
                        }
                    }
                }

            }
            //如果有新数据的话就播放语音
            playMusic(playMp3);
            //发送请求
            $timeout(function () {
                getDate();
            }, numbers.pollTime * 1000);
        }).error(function () {
            //发送请求
            $timeout(function () {
                getDate();
            }, 10 * 1000);
        });

    }
}


export default orderNumServiceFactory;





