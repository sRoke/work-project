import conf from "../../../../../conf";
// import wangEditor from "../../../../../thirdJs/wangeditor/dist/js/wangEditor.js";


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
    authService,
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
                _authService,
                _$rootScope) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        $rootScope = _$rootScope;
        $timeout = _$timeout;
        loginService = _loginService;
        Upload = _Upload;
        authService = _authService;
        $stateParams = _$stateParams;
        $mdBottomSheet = _$mdBottomSheet;
        $location = _$location;
        alertService = _alertService;
        wxService = _wxService;
        /////////////////////////////////
        loginService.loginCtl(true, $location.absUrl());
        authService.setAuthorities($stateParams.brandAppId, $stateParams.storeId);
        $rootScope.brandAppId = $scope.brandAppId = $stateParams.brandAppId;
        $scope.choose = $stateParams.choose;
        $scope.from = $stateParams.from;
        $scope.editStatus = $stateParams.editStatus;
        $scope.id = $stateParams.id;
        if ($stateParams.skuData) {
            $scope.skuData = JSON.parse($stateParams.skuData);
        }
        else {
            $scope.skuData = {};
        }
        // console.log('  $scope.jsons', $scope.jsons);
        $scope.gotoTop = function () {
            window.scrollTo(0, 0);//滚动到顶部
        };
        $scope.gotoTop();


        // window.onresize = function () {
        //     if (document.body.clientHeight < 500) {
        //         alert(1)
        //         // document.getElementById('div4').style.display = 'none';
        //     } else {
        //         alert(2)
        //
        //         // if (document.getElementById('div4').style.display == 'none') {
        //         //     document.getElementById('div4').style.display = 'block';
        //         // }
        //     }
        // }


        // $("#div4").focus(function () {
        //     var wh = document.documentElement.clientHeight;
        //     console.log('wh', wh);
        //     console.log(123);
        //     $('.ks-textImg').scrollTop(0);//滚动到顶部
        //     $(document).scrollTop(0);
        //
        //
        //     document.body.style.height = viewHeight + "px";
        //
        //     // $('.ks-textImg').css('height', '200px')
        //     // $("#div4").css('height', '200px')
        //     // $('body').css('height', '200px')
        //     // document.body.style.height = '200px'
        // }).blur(function () {
        //     console.log(123);
        //     $('.ks-textImg').scrollTop(0);//滚动到顶部
        //     $("#div4").css('height', '500px')
        //     document.body.style.height = "100%";
        //
        //     $(document).scrollTop(0);
        // });

//         if (jQuery) {
// // jQuery 已加载
//         } else {
// // jQuery 未加载
//         }
//         function getContentSize() {
//             var wh = document.documentElement.clientHeight;
//             console.log('wh', wh);
//
//             var eh = 300;
//             var ch = (wh - eh) + "px";
//
//             console.log('ch', ch);
//             document.getElementById("ens").style.height = ch;
//         }
//
//         window.onload = getContentSize;
//         window.onresize = getContentSize;
//
//         var viewHeight = window.innerHeight; //获取可视区域高度
//         console.log('viewHeight ', viewHeight);
//         if (typeof jQuery == 'undefined') {
//             console.log(typeof jQuery);
//             console.log('typeof jQuery', jQuery, $);
// // jQuery 未加载
//         } else {
// // jQuery 已加载
//             console.log(typeof jQuery);
//             console.log('typeof jQuery', jQuery, $);
//         }
//
//         $("#test").focus(function () {
//             console.log(123);
//             $("#ens").css("height", viewHeight);
//         }).blur(function () {
//             console.log(456);
//             $("#ens").css("height", "100%");
//         });


        // var Edr = new Eleditor({
        //     el: '#articleEditor', //容器
        //     upload:{
        //         server: '/upload.json', //填写你的后端上传路径
        //         fileSizeLimit: 2 //限制图片上传大小，单位M
        //     },
        //     //placeHolder: '请输入内容',
        // });

        //请记住下面常用方法---------------------------------------->
        //Edr.append( str ); 往编辑器追加内容
        //Edr.getEditNode(); 获取当前选中的编辑节点
        //Edr.getContent(); 获取编辑器内容
        //Edr.getContentText(); 获取编辑器纯文本
        //Edr.destory(); 移除编辑器


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
            // console.log('resultText', resultText);
            // console.log('xhr', xhr);

            //将resultText转成json
            var jsonValue = eval("(" + resultText + ")");

            $http({
                method: 'GET',
                url: conf.yunApiPath + '/app/5988791a6b869f4e18d5c8d5/org/598878fc6b869f4e0f19fb47/yunFile/' + jsonValue.data
            }).then(function (resp) {
                // console.log('Success ' + resp.data.data.cdnUrls[0].url);
                var imgUrl = resp.data.data.cdnUrls[0].url;
                var originalName = ruleEditor.uploadImgOriginalName || '';
                ruleEditor.command(null, 'insertHtml', '<img src="' + imgUrl + '" alt="' + imgUrl + '" style="max-width:100%;"/>');
                // console.log('resp', resp);
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

        // $scope.focus = function (status) {
        //     // var viewHeight = window.innerHeight; //获取可视区域高度
        //     // console.log('viewHeight ', viewHeight);
        //
        //     if (status) {
        //
        //         // setTimeout(function () {
        //         //     var viewHeight = window.innerHeight; //获取可视区域高度
        //         //     console.log('viewHeight ', viewHeight);
        //         // }, 500);
        //         setTimeout(function () {
        //             var viewHeight = window.innerHeight; //获取可视区域高度
        //             var i = 1;
        //             console.log('viewHeight ', viewHeight);
        //             document.getElementById("ens").innerHTML = viewHeight + "i=" + i++;
        //             document.getElementById("ens").style.height = viewHeight;
        //         }, 1000);
        //         setInterval(function () {
        //             var viewHeight = window.innerHeight; //获取可视区域高度
        //             console.log('viewHeight ', viewHeight);
        //             document.getElementById("ens").innerHTML = viewHeight;
        //             document.getElementById("ens").style.height = viewHeight;
        //         }, 1000)
        //         // alert(viewHeight)
        //
        //     } else {
        //         document.getElementById("ens").style.height = '100%';
        //
        //     }
        // };

        // $("input").focus(function () {
        //     $(".wrap").css("height", viewHeight);
        // }).blur(function () {
        //     $("#ens").css("height", "100%");
        // });


        ruleEditor.$txt.html($scope.skuData.detail);
        // console.log('$stateParams.selectMoreSpec', $stateParams.selectMoreSpec);
        $scope.finish = function () {
            // main.brandApp.store.itemManage.itemAdd({status:'add'})
            // console.log('        $scope.editor.txt.html()', ruleEditor.$txt.html());
            $scope.skuData.detail = ruleEditor.$txt.html();
            var json = angular.toJson($scope.skuData);
            if ($scope.from == 'itemAdd') {
                $state.go("main.brandApp.store.itemManage.itemAdd", {
                    id: $scope.id,
                    skuData: json,
                    status: 'add',
                    selectMoreSpec: $stateParams.selectMoreSpec,
                })
            } else {
                $state.go("main.brandApp.store.itemManage.itemEdit", {
                    id: $scope.id,
                    skuData: json,
                    status: 'edit',
                    editStatus: $scope.editStatus,
                    selectMoreSpec: $stateParams.selectMoreSpec,
                })
            }
        };
        $scope.getLead = function () {
            $http({
                method: "GET",  ///brandApp/{brandAppId}/shop/{shopId}/itemProp/itemPropList
                url: conf.apiPath + "/common/guidePage",
                params: {
                    type: 'ITEM_DETAIL',

                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                // console.log('common/guidePage', resp);

                $scope.showLead = resp.data.data;

                if ($scope.showLead) {
                    $scope.lead = 1;
                }
            }, function (resp) {
                //error
            });
        };
        $scope.getLead();

        //控制引导页
        $scope.leading = function (num) {
            $scope.lead = num;
        };
        /*返回上级*/
        $scope.fallbackPage = function () {

            var json = angular.toJson($scope.skuData);

            if ($scope.from == 'itemAdd') {
                $state.go("main.brandApp.store.itemManage.itemAdd", {
                    id: $scope.id,
                    skuData: json,
                    status: 'add',
                    selectMoreSpec: $stateParams.selectMoreSpec,
                })
            } else if ($scope.from == 'itemEdit') {
                $state.go("main.brandApp.store.itemManage.itemEdit", {
                    id: $scope.id,
                    skuData: json,
                    status: 'add',
                    editStatus: $scope.editStatus,
                    selectMoreSpec: $stateParams.selectMoreSpec,
                })
            }
            else {
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
    'authService',
    '$rootScope'
];

export default Controller ;
