import conf from "../../../../conf";
// import dialog from "!html-loader?minimize=true!./updateAddress.html";

var $scope,
    $http,
    authService,
    $state,
    $log,
    loginService,
    errorService,
    Upload,
    $filter,
    $mdDialog,
    $stateParams;
class Controller {
    constructor(_$scope,
                _$http,
                _$state,
                _$log,
                _authService,
                _loginService,
                _errorService,
                _Upload,
                _$filter,
                _$mdDialog,
                _$stateParams) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        authService = _authService;
        loginService = _loginService;
        errorService = _errorService;
        Upload=_Upload;
        $filter = _$filter;
        $log = _$log;
        $stateParams = _$stateParams;
        /////////////////////////////////
        loginService.loginCtl(true);
        $scope.brandAppId = $stateParams.brandAppId;
        //获取权限，勿删
        // authService.setAuthorities($scope.brandAppId);
        var vm = this;
        $scope.activeNum = '1';
        $scope.changeTab = function (num) {
            $scope.activeNum = num;
        };
        $scope.data = {};
        $scope.item={};
        $scope.tradeChannels = [];
        $scope.transferChannels = [];
        //初始化接口
        $scope.getList = function () {
            $http({
                method: "GET",
                url: conf.payApipath + "/app/"+$scope.brandAppId,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                console.log(4422222222222222, resp.data.data);
                $scope.info=resp.data.data;
                $scope.data=resp.data.data.wxPayInfo;
                $scope.item=resp.data.data.aliPayInfo;
                $scope.transferChannels=resp.data.data.transferChannels;
                $scope.tradeChannels=resp.data.data.tradeChannels;

            }, function (resp) {
                //error
            });
        };
        $scope.getList();
        var method=null;
        var url=null;
        // 保存接口
        $scope.save= function (type) {
            $scope.aliPayInfo = {
                appId: $scope.item.appId,
                mchPubKey: $scope.item.mchPubKey,
                mchPriKey: $scope.item.mchPriKey
            };
            $scope.wxPayInfo = {
                appId: $scope.data.appId,
                paySecret: $scope.data.paySecret,
                mchId: $scope.data.mchId
            };
            if(type=='wx'){
                if ($scope.wxPayInfo.appId && $scope.wxPayInfo.paySecret && $scope.wxPayInfo.mchId) {
                    console.log(1);
                    if($scope.tradeChannels.indexOf('WX_JSSDK')==-1){
                        $scope.tradeChannels.push('WX_JSSDK');
                    }
                    if($scope.transferChannels.indexOf('WX')==-1){
                        $scope.transferChannels.push("WX");
                    }
                }else{
                    return errorService.error("请将微信支付相关信息填写完整。", null);
                };
            }
            if(type=='pay'){
                if ($scope.aliPayInfo.appId && $scope.aliPayInfo.mchPubKey && $scope.aliPayInfo.mchPriKey) {
                    console.log(2);
                    if($scope.tradeChannels.indexOf('ALIPAY_WAP')==-1){
                        $scope.tradeChannels.push('ALIPAY_WAP');
                    }
                    if($scope.transferChannels.indexOf('ALIPAY')==-1){
                        $scope.transferChannels.push("ALIPAY");
                    }
                }else{
                    return errorService.error("请将支付宝支付相关信息填写完整。", null);
                };
            };
            //设置类型
            if(!$scope.info){
                method='POST';   //新增
                url=conf.payApipath + "/app";
            }else{
                method='PATCH';   //编辑
                url=conf.payApipath + "/app/"+$scope.brandAppId;
            };
            console.log('$scope.tradeChannels',$scope.tradeChannels);
            console.log(' $scope.transferChannels', $scope.transferChannels);
            console.log('type',method,$scope.data.appId);
            $http({
                method: method,
                url: url,
                data: {
                    wxPayInfo:$scope.wxPayInfo,
                    aliPayInfo:$scope.aliPayInfo,
                    tradeChannels:  $scope.tradeChannels,
                    transferChannels: $scope.transferChannels,
                    qhPayAppId: $scope.brandAppId
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                //success
                console.log(4122222222222222, resp.data.data);
                $scope.getList();
            }, function (resp) {
                //error
            });

        };

        $scope.btn = function() {
            var certBytes = new FormData();
            var file = document.querySelector('input[type=file]').files[0];
            console.log('file',file.name);
            certBytes.append('certBytes', file);
            //fd.certBytes=file;
            console.log('fd1111111111111111111111111111111111111111',certBytes);
            $http({
                method:'PATCH',
                url: conf.payApipath+ '/app/'+$scope.brandAppId+'/wx/cert',
                data: certBytes,
                headers: {
                    'Authorization': 'Bearer' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId,
                    'Content-Type':undefined
                },
                transformRequest: angular.identity
            }).success( function ( response ) {
                //上传成功的操作
                //console.log('response',response);
                if(response.data.id){
                    return errorService.error("上传成功", null);
                }
            });

        }

    }
}

Controller.$inject = [
    '$scope',
    '$http',
    '$state',
    '$log',
    'authService',
    'loginService',
    'errorService',
    'Upload',
    '$filter',
    '$mdDialog',
    '$stateParams'
];

export default Controller ;
