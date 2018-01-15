import conf from "../../../../../conf";
// import 'jquery';
import wangEditor from "wangeditor";


var $scope,
    $http,
    $state,
    Upload,
    $mdBottomSheet,
    loginService,
    $mdDialog,
    $timeout,
    $stateParams,
    $location,
    alertService,
    wxService,
    $templateCache,
    $rootScope;
class Controller {
    constructor(_$scope,
                _$http,
                _$state,
                _Upload,
                _$mdBottomSheet,
                _loginService,
                _$mdDialog,
                _$timeout,
                _$stateParams,
                _$location,
                _alertService,
                _wxService,
                _$templateCache,
                _$rootScope) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        $rootScope = _$rootScope;
        $timeout = _$timeout;
        loginService = _loginService;
        Upload = _Upload;
        $stateParams = _$stateParams;
        $mdBottomSheet = _$mdBottomSheet;
        $location = _$location;
        alertService = _alertService;
        $templateCache = _$templateCache;
        wxService = _wxService;
        /////////////////////////////////
        // loginService.loginCtl(true, $location.absUrl());
        $rootScope.voteAppId = $scope.voteAppId = $stateParams.voteAppId;
        $scope.from = $stateParams.from;
        $scope.type = $stateParams.type;
        $scope.id = $stateParams.id;

        if(!$rootScope.rootVoteData){
            $rootScope.rootVoteData = {};
        }
        //
        // if ($stateParams.skuData) {
        //     $scope.skuData = JSON.parse($stateParams.skuData);
        // }
        //
        // else {
        //     $scope.skuData = {};
        // }
        console.log('  $scope.jsons', $scope.jsons);
        $scope.gotoTop = function () {
            window.scrollTo(0, 0);//滚动到顶部
        };
        $scope.gotoTop();

        var ruleEditor = new wangEditor('div4');
        ruleEditor.config.uploadImgUrl = conf.yunApiPath + '/app/5988791a6b869f4e18d5c8d5/org/598878fc6b869f4e0f19fb47/yunFile';
        ruleEditor.config.uploadImgFileName = 'file';
        // ruleEditor.config.zindex = 200000000;
        ruleEditor.config.menus = [
            'head',
            // 'bold',
            // 'underline',
            // 'italic',
            // 'strikethrough',
            'forecolor',
            // 'bgcolor',
            // '|',
            // 'fontsize',
            'alignleft',
            'aligncenter',
            // 'alignright',
            'img',
            'undo',
            'redo',
            // 'fullscreen'
        ];
        // editor.config.uploadParams = {
        //     file: 'file',
        // };
        // 关闭菜单栏fixed
        // ruleEditor.config.menuFixed = false;
        // 修改菜单栏fixed的上边距（单位 px）
        // ruleEditor.config.menuFixed = 50;
        // 自定义load事件
        ruleEditor.config.uploadImgFns.onload = function (resultText, xhr) {
            // resultText 服务器端返回的text
            // xhr 是 xmlHttpRequest 对象，IE8、9中不支持
            console.log('resultText', resultText);
            console.log('xhr', xhr);
            //将resultText转成json
            var jsonValue = eval("(" + resultText + ")");
            $http({
                method: 'GET',
                url: conf.yunApiPath + '/app/5988791a6b869f4e18d5c8d5/org/598878fc6b869f4e0f19fb47/yunFile/' + jsonValue.data
            }).then(function (resp) {
                console.log('Success ' + resp.data.data.cdnUrls[0].url);
                var imgUrl = resp.data.data.cdnUrls[0].url;
                var originalName = ruleEditor.uploadImgOriginalName || '';
                ruleEditor.command(null, 'insertHtml', '<img src="' + imgUrl + '" alt="' + imgUrl + '" style="max-width:100%;"/>');
                console.log('resp', resp);
            }, function (resp) {
                // console.log('Error status: ' + resp.status);
            });
        };
        // 自定义timeout事件
        ruleEditor.config.uploadImgFns.ontimeout = function (xhr) {
            // xhr 是 xmlHttpRequest 对象，IE8、9中不支持
            alert('上传超时');
        };
        // 自定义error事件
        ruleEditor.config.uploadImgFns.onerror = function (xhr) {
            // xhr 是 xmlHttpRequest 对象，IE8、9中不支持
            alert('上传错误');
        };
        ruleEditor.create();

        if($scope.from == 'add'){
            if($scope.type == 'desp'){
                ruleEditor.$txt.html($rootScope.rootVoteData.desp);
            }else if($scope.type == 'rule'){
                ruleEditor.$txt.html($rootScope.rootVoteData.rule);
            }
        }else if($scope.from == 'edit'){
            if($scope.type == 'desp'){
                ruleEditor.$txt.html($rootScope.rootVoteDataEdit.desp);
            }else if($scope.type == 'rule'){
                ruleEditor.$txt.html($rootScope.rootVoteDataEdit.rule);
            }
        } else if($scope.from == 'view'){
            $http({
                method: "GET",
                url: conf.apiPath + "/voteApp/" + $stateParams.voteAppId + '/vote/admin/' + $stateParams.id ,
                params: {},
                headers: {
                    // 'Authorization': 'Bearer ' + loginService.getAccessToken()
                }
            }).then(function (resp) {
                console.log('resprespresprespresp', resp);
                $scope.voteDataview = resp.data.data;
                var tplUrl = "tpl.html";
                $scope.tplUrl = tplUrl;
                if($scope.type == 'desp'){
                    $templateCache.put(tplUrl, resp.data.data.desp);
                }else if($scope.type == 'rule'){
                    $templateCache.put(tplUrl, resp.data.data.rule);
                }
            }, function () {
                    //error
            });
        }




        // ruleEditor.$txt.html($scope.skuData.detail);



        $scope.finish = function () {
            // main.voteApp.store.itemManage.itemAdd({status:'add'})
            // console.log('        $scope.editor.txt.html()', ruleEditor.$txt.html());
            // console.log('       ruleEditor.$txt.text()', ruleEditor.$txt.text());
            // console.log("editor.$txt.find('img')"  , ruleEditor.$txt.find('img'));
            // $scope.skuData.detail = ruleEditor.$txt.html();
            if(ruleEditor.$txt.text() || ruleEditor.$txt.find('img').length>0){
                if($scope.from == 'add'){
                    if($scope.type == 'desp'){
                        $rootScope.rootVoteData.desp = ruleEditor.$txt.html();
                    }else if($scope.type == 'rule'){
                        $rootScope.rootVoteData.rule = ruleEditor.$txt.html();
                    }
                }else if($scope.from == 'edit'){
                    if($scope.type == 'desp'){
                        $rootScope.rootVoteDataEdit.desp = ruleEditor.$txt.html();
                    }else if($scope.type == 'rule'){
                        $rootScope.rootVoteDataEdit.rule = ruleEditor.$txt.html();
                    }
                }
            }else {
                if($scope.from == 'add'){
                    if($scope.type == 'desp'){
                        $rootScope.rootVoteData.desp = null;
                    }else if($scope.type == 'rule'){
                        $rootScope.rootVoteData.rule = null;
                    }
                }else if($scope.from == 'edit'){
                    if($scope.type == 'desp'){
                        $rootScope.rootVoteDataEdit.desp = null;
                    }else if($scope.type == 'rule'){
                        $rootScope.rootVoteDataEdit.rule = null;
                    }
                }
            }



            // history.back();
            $scope.fallbackPage()
        };
        /*返回上级*/
        $scope.fallbackPage = function () {
            // history.back();
            // var json = angular.toJson($scope.skuData);
            //


            console.log('55555555555555555555555555',$scope.id)






            if ($scope.from == 'add') {
                $state.go("main.voteApp.vote.voteAdd",{form:'text'},{reload: true})
            } else if ($scope.from == 'edit') {
                $state.go("main.voteApp.vote.voteEdit",{id: $scope.id,form:'text'},{reload: true})
            } else {
                history.back();
            }
        };
    }
}

Controller.$inject = [
    '$scope',
    '$http',
    '$state',
    'Upload',
    '$mdBottomSheet',
    'loginService',
    '$mdDialog',
    '$timeout',
    '$stateParams',
    '$location',
    'alertService',
    'wxService',
    '$templateCache',
    '$rootScope'
];

export default Controller ;
