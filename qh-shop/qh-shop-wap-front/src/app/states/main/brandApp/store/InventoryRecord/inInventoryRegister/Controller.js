import conf from "../../../../../../conf";

import Sortable from 'sortablejs';

var $scope,
    $stateParams,
    $httpParamSerializer,
    alertService,
    $http,
    loginService,
    $mdDialog,
    Upload,
    wxService,
    $state;
class Controller {
    constructor(_$scope,
                _$stateParams,
                _$httpParamSerializer,
                _alertService,
                _$http,
                _loginService,
                _$mdDialog,
                _Upload,
                _wxService,
                _$state) {
        $scope = _$scope;
        $state = _$state;
        $http = _$http;
        wxService = _wxService;
        Upload = _Upload;
        $mdDialog = _$mdDialog;
        loginService = _loginService;
        $stateParams = _$stateParams;
        $httpParamSerializer = _$httpParamSerializer;
        alertService = _alertService;
        $scope.brandAppId = $stateParams.brandAppId;
        loginService.loginCtl(true);

        ///////////////////////////////////////
        const TAG = "main/address ";
        console.log(`=> ${TAG}`);
        // loginService.loginCtl(true);

        $scope.imgs = [];







        $scope.tab = 0;
        $scope.checkTabsShow = false;
        $scope.checkTabs = function (index) {
            $scope.tab = index;
            $scope.checkTabsShow = !$scope.checkTabsShow;
        };


        //切换入库方式
        $scope.chooseMode = function (tab) {
            if(tab == 'scanCode'){
                $scope.scanCode = true;
                $scope.Manual = false;
                $scope.wxSys();
            }else if(tab == 'Manual'){
                $scope.scanCode = false;
                $scope.Manual = true;
            }
        };


        //------------------------------扫描成功弹窗

        $scope.showScanCodeAddDialog = function (scanCode) {
            $mdDialog.show({
                templateUrl: 'scanCodeAdd.html',
                parent: angular.element(document.body),
                clickOutsideToClose: true,   //true 点击界外的时候弹窗消失
                fullscreen: false,
                hasBackdrop: false,
                controller: ['$http', '$stateParams', '$mdDialog', function ($http, $stateParams, $mdDialog) {
                    var vmd = this;
                    vmd.scanCode = scanCode;
                    vmd.cancel = function () {
                        return $mdDialog.cancel();
                    };
                }],
                controllerAs: "vmd"
            }).then(function (answer) {
            }, function () {
            });
        };

        $scope.showScanCodeAddDialog()


        //-------------------------------------------------删除
        // alertService.del('').then(function (data) {
        //    console.log('123123123',data)
        // })

        /**
         * --------------------------------------------------------------------------微信扫码选择界面js
         */
        //-------------------------------------调用微信扫一扫功能
        if (wxService.isInWx()) {
            // console.log('wxService', wxService);
            wxService.init().then(function (data) {
                if (data) {
                    $scope.initWX = true;
                }
            })
        }
        $scope.wxSys = function () {
            if (wxService.isInWx()) {
                if ($scope.initWX) {
                    wx.scanQRCode({
                        desc: 'scanQRCode desc',
                        needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
                        scanType: ["qrCode", "barCode"], // 可以指定扫二维码还是一维码，默认二者都有
                        success: function (res) {
                            var result = res.resultStr.split(','); // 当needResult 为 1 时，扫码返回的结果
                            $scope.sysQrcode = result[result.length - 1];
                            $scope.showScanCodeAddDialog($scope.sysQrcode);
                            $scope.$digest();
                        },
                        error: function (res) {
                            if (res.errMsg.indexOf('function_not_exist') > 0) {
                                alertService.msgAlert("exclamation-circle", "版本过低请升级");
                            }
                        }
                    });
                } else {
                    wxService.init().then(function (data) {
                        if (data) {
                            wx.scanQRCode({
                                desc: 'scanQRCode desc',
                                needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
                                scanType: ["qrCode", "barCode"], // 可以指定扫二维码还是一维码，默认二者都有
                                success: function (res) {
                                    var result = res.resultStr.split(','); // 当needResult 为 1 时，扫码返回的结果
                                    $scope.sysQrcode = result[result.length - 1];
                                    $scope.showScanCodeAddDialog($scope.sysQrcode);
                                    $scope.$digest();
                                },
                                error: function (res) {
                                    if (res.errMsg.indexOf('function_not_exist') > 0) {
                                        alertService.msgAlert("exclamation-circle", "版本过低请升级");
                                    }
                                }
                            });
                        }
                    })
                }
            }
        };






        // 拖拽排序
        // Sortable.create(document.getElementById('simpleList'), {
        //     draggable: ".imgBox",
        //     onEnd: function (/**Event*/evt) {
        //         var phote = $scope.imgs[evt.oldIndex];
        //         $scope.imgs.splice(evt.oldIndex, 1);
        //         $scope.imgs.splice(evt.newIndex,0,phote);
        //     },
        // });
        //图片上传
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
            // console.log('AAAAAA==',index, $scope.imgs)
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
    '$mdDialog',
    'Upload',
    'wxService',
    '$state'
];

export default Controller ;
