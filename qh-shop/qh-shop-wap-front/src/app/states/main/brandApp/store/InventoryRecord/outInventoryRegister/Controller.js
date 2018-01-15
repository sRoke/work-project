import conf from "../../../../../../conf";


import Sortable from 'sortablejs';

import PhotoClip from 'photoclip';


var $scope,
    $stateParams,
    $httpParamSerializer,
    alertService,
    $http,
    loginService,
    Upload,
    $state;
class Controller {
    constructor(_$scope,
                _$stateParams,
                _$httpParamSerializer,
                _alertService,
                _$http,
                _loginService,
                _Upload,
                _$state) {
        $scope = _$scope;
        $state = _$state;
        $http = _$http;
        Upload = _Upload;
        loginService = _loginService;
        $stateParams = _$stateParams;
        $httpParamSerializer = _$httpParamSerializer;
        alertService = _alertService;

        $scope.brandAppId = $stateParams.brandAppId;

        ///////////////////////////////////////
        const TAG = "main/address ";
        console.log(`=> ${TAG}`);
        loginService.loginCtl(true);


        $scope.orderId = $stateParams.orderId;


        // 拖拽排序
        // Sortable.create(document.getElementById('simpleList'), {
        //     draggable: ".imgBox",
        //     onEnd: function (/**Event*/evt) {
        //         var phote = $scope.imgs[evt.oldIndex];
        //         $scope.imgs.splice(evt.oldIndex, 1);
        //         $scope.imgs.splice(evt.newIndex, 0, phote);
        //         $scope.$digest();
        //     },
        // });

        $scope.checkTabsShow = false;


        //切换入库方式
        $scope.chooseMode = function (tab) {
            if (tab == 'scanCode') {
                $scope.scanCode = true;
                $scope.Manual = false;
            } else if (tab == 'Manual') {
                $scope.scanCode = false;
                $scope.Manual = true;
            }
        };


        $scope.deliveryCopy = '';
        $scope.checkDelivery = function (index) {
            $scope.deliveryCopy = index;
        };


        $scope.checkTabs = function (index) {
            if (index == 'cancel') {
                $scope.checkTabsShow = false;
            } else if (index == 'show') {
                $scope.checkTabsShow = true;
            } else if (typeof index == 'number') {
                $scope.tab = index;
            }
        };


        $scope.save = function () {
            // console.log($scope.imgs);
        };


        $scope.imgs = [];

        // $scope.choosePhote = false;
        //图片裁剪
        // var pc = new PhotoClip('#clipArea', {
        //     size: [260,260],
        //     outputSize: 640,
        //     // adaptive: ['70','40'],
        //     file: '#file,#file2',
        //     view: '#view',
        //     ok: '#clipBtn',
        //     style:{
        //         maskColor:'rgba(0,0,0,0.7)',
        //         // jpgFillColor:''
        //     },
        //     loadStart: function() {
        //         console.log('开始读取照片');
        //     },
        //     loadComplete: function() {
        //         console.log('照片读取完成',$scope);
        //         $scope.choosePhote = true;
        //         $scope.$apply();
        //     },
        //     done: function(dataURL) {
        //         console.log('base64裁剪完成,正在上传');
        //         $scope.saveImg(dataURL);
        //     },
        //     fail: function(msg) {
        //         alert(msg);
        //     }
        // });
        // $scope.saveImg = function (dataUrl) {
        //     $http({
        //         method: "POST",
        //         url: conf.yunApiPath + "/app/5988791a6b869f4e18d5c8d5/org/598878fc6b869f4e0f19fb47/yunFile/b64",
        //         data: {
        //             base64DataUrl: dataUrl
        //         },
        //         headers: {
        //             'Authorization': 'Bearer ' + loginService.getAccessToken()
        //         }
        //     }).then(function (resp) {
        //             $scope.dataUrl = resp.data.data;
        //             $scope.getImg($scope.dataUrl);
        //             console.log(resp.data.data)
        //
        //         }, function () {
        //             //error
        //         }
        //     );
        // };
        // $scope.getImg = function (id) {
        //     $http({
        //         method: "GET",
        //         url: conf.yunApiPath + "/app/5988791a6b869f4e18d5c8d5/org/598878fc6b869f4e0f19fb47/yunFile/" + id,
        //         headers: {
        //             'Authorization': 'Bearer ' + loginService.getAccessToken()
        //         }
        //     }).then(function (resp) {
        //             console.log(resp.data.data);
        //             $scope.imgs.push(resp.data.data.cdnUrls[0].url) ;
        //             $scope.choosePhote = false;
        //         }, function () {
        //             //error
        //         }
        //     );
        // };
        // $scope.cancelChoosePhote = function () {
        //     $scope.choosePhote = false;
        // };


        $scope.uploading = function (file) {
            $scope.f = file;
            // $scope.errFile =errFiles && errFiles[0];
            if (file) {
                Upload.upload({
                    url: conf.yunApiPath + '/app/5988791a6b869f4e18d5c8d5/org/598878fc6b869f4e0f19fb47/yunFile',
                    data: {
                        file: file,
                    }
                }).then(function (resp) {
                    // console.log('Success ' + resp.config.data.file.name + 'uploaded. Response: ', resp.data);
                    $http({
                        method: 'GET',
                        url: conf.yunApiPath + '/app/5988791a6b869f4e18d5c8d5/org/598878fc6b869f4e0f19fb47/yunFile/' + resp.data.data
                    }).then(function (resp) {
                        //console.log('Success ' + resp.data.data.cdnUrls[0].url);
                        // 上传代码返回结果之后，将图片push到数组中
                        $scope.imgs.push(resp.data.data.cdnUrls[0].url);
                    }, function (resp) {
                        // console.log('Error status: ' + resp.status);
                    });
                }, function (resp) {
                    // console.log('Error status: ' + resp.status);
                }, function (evt) {
                    var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                    // console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
                });
            }

        };
        /**
         * 删除指定的图片
         * @param index
         */
        $scope.cancelDel = function (index) {
            $scope.imgs.splice(index, 1);
        };


        /*返回上级*/
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.home", null, {reload: true});
            } else {
                history.back();
            }
        };
    }


}

Controller.$inject = [
    '$scope',
    '$stateParams',
    '$httpParamSerializer',
    'alertService',
    '$http',
    'loginService',
    'Upload',
    '$state'
];

export default Controller ;
