import conf from "../../../../conf";
// import "jquery";


import PhotoClip from 'photoclip';


var $scope,
    $http,
    $state,
    $log,
    loginService,
    $mdDialog,
    $stateParams,
    $location,
    $q,
    wxService,
    Upload;
class Controller {
    constructor(_$scope,
                _$http,
                _$state,
                _$log,
                _loginService,
                _$mdDialog,
                _$stateParams,
                _$location,
                _$q,
                _wxService,
                _Upload) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        loginService = _loginService;
        $log = _$log;
        $q = _$q;
        Upload = _Upload;
        wxService = _wxService;
        $location = _$location;
        $stateParams = _$stateParams;
        $scope.brandAppId = $stateParams.brandAppId;
        /////////////////////////////////


        $scope.map = new BMap.Map("baiduMap");    // 创建Map实例
        // $scope.map.centerAndZoom(new BMap.Point(116.404, 39.915), 20);


        function getAddr() {
            var geolocation = new BMap.Geolocation();
            geolocation.getCurrentPosition(function (r) {
                if (this.getStatus() == BMAP_STATUS_SUCCESS) {
                    console.log('111111111111==============', r);//地址信息


                    var mk = new BMap.Marker(r.point);
                    $scope.map.addOverlay(mk);//标出所在地
                    $scope.map.centerAndZoom(r.point, 20);
                    // $scope.map.panTo(r.point);//地图中心移动


                    //alert('您的位置：'+r.point.lng+','+r.point.lat);
                    var point = new BMap.Point(r.point.lng, r.point.lat);//用所定位的经纬度查找所在地省市街道等信息
                    var gc = new BMap.Geocoder();
                    gc.getLocation(point, function (rs) {
                        var addComp = rs.addressComponents;
                        console.log('2=============地址信息===', rs);//地址信息
                        $scope.baiduMapAddress = rs;
                        $scope.$digest();
                    });


                    //
                    // var navigationControl = new BMap.NavigationControl({
                    //     // 靠左上角位置
                    //     anchor: BMAP_ANCHOR_TOP_LEFT,
                    //     // LARGE类型
                    //     type: BMAP_NAVIGATION_CONTROL_LARGE,
                    //     // 启用显示定位
                    //     enableGeolocation: true
                    // });
                    // $scope.map.addControl(navigationControl);


                    // 添加定位控件
                    // var geolocationControl = new BMap.GeolocationControl();
                    // geolocationControl.addEventListener("locationSuccess", function(e){
                    //     // 定位成功事件
                    //     var address = '';
                    //     address += e.addressComponent.province;
                    //     address += e.addressComponent.city;
                    //     address += e.addressComponent.district;
                    //     address += e.addressComponent.street;
                    //     address += e.addressComponent.streetNumber;
                    //     $scope.addresslocation = address;
                    //     $scope.$digest();
                    //     console.log(e);
                    //     alert("当前定位地址为：" + address);
                    // });
                    // geolocationControl.addEventListener("locationError",function(e){
                    //     // 定位失败事件
                    //     alert(e.message);
                    // });
                    // $scope.map.addControl(geolocationControl);

                }
            });
        }

        getAddr();


        $scope.MapLocal = new BMap.LocalSearch($scope.map, {
            renderOptions: {map: $scope.map}
        });
        // $scope.MapLocal.enableFirstResultSelection();
        $scope.search = function () {
            console.log($scope.keyWord);


            $scope.MapLocal.search($scope.keyWord);


        }


        $scope.MapLocal.setMarkersSetCallback(function (pois) {

            console.log('pois===============', pois)

            for (var i = 0; i < pois.length; i++) {

                pois[i].marker.addEventListener('click', function () {
                    console.log('2222222222222222', this.getPosition());
                    $scope.map.panTo(this.getPosition());//地图中心移动
                })


            }

        })


        ////登出   --------------------------------测试威信扫码的代码
        $scope.logout = () => {
            // https://kingsilk.net/qh/mall/local/11200/api
            function init() {
                var deferred = $q.defer();
                var ua = window.navigator.userAgent.toLowerCase();
                if (ua.match(/MicroMessenger/i) && !ua.match(/windows/i)) {
                    $http.get('https://kingsilk.net/qh/mall/local/11300/api' + '/weiXin/jsSdkConf', {
                        params: {
                            url: location.href.split('#')[0]
                        }
                    }).then(function (resp) {
                        resp.data.jsApiConf.debug = false;
                        console.info("已经获取了微信JS SDK 的配置对象", resp.data.jsApiConf);
                        //$scope.wxReady = true;
                        wx.config(resp.data.jsApiConf);
                        wx.isConfig = true;
                        wx.error(function (res) {
                            console.info("微信调用出错了 ", res);
                        });
                        wx.ready(function () {
                            deferred.resolve(true);
                        });

                    });
                } else {
                    deferred.resolve(false);
                }
                return deferred.promise;
            }

            init().then(function () {
                wx.scanQRCode({
                    desc: 'scanQRCode desc',
                    needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
                    scanType: ["qrCode", "barCode"], // 可以指定扫二维码还是一维码，默认二者都有
                    success: function (res) {
                        // 回调
                        var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
                    },
                    error: function (res) {
                        if (res.errMsg.indexOf('function_not_exist') > 0) {
                            alert('版本过低请升级')
                        }
                    }
                });
            });
        };

    }
}

Controller.$inject = [
    '$scope',
    '$http',
    '$state',
    '$log',
    'loginService',
    '$mdDialog',
    '$stateParams',
    '$location',
    '$q',
    'wxService',
    'Upload'
];

export default Controller ;
