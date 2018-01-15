import conf from "../../../../../conf";
import E from "wangeditor";
//引入js
import laydate from "layui-laydate";
var $scope,
    $http,
    authService,
    $state,
    errorService,
    $filter,
    loginService,
    alertService,
    $mdDialog,
    Upload,
    $stateParams;
class Controller {
    constructor(_$scope,
                _$http,
                _$state,
                _errorService,
                _$filter,
                _authService,
                _loginService,
                _alertService,
                _$mdDialog,
                _Upload,
                _$stateParams) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        $filter = _$filter;
        authService = _authService;
        loginService = _loginService;
        errorService = _errorService;
        Upload = _Upload;
        $stateParams = _$stateParams;
        alertService = _alertService;
        /////////////////////////////////
        loginService.loginCtl(true);
        $scope.brandAppId = $stateParams.brandAppId;
        $scope.id = $stateParams.id;

        //获取权限，勿删
        // authService.setAuthorities($scope.brandAppId);
        //console.log("====================", $stateParams);
        $scope.activeNum = '1';
        $scope.changeTab = function (num) {
            $scope.activeNum = num;
        };
        $scope.activeData = {};

        //设置投票人数默认是1
        $scope.activeData.setNum = $scope.activeData.votePeoplePerDay = '1';
        $scope.setVoteNum = function () {
            if (!(/^[1-9]\d*|0$/.test($scope.activeData.setNum))) {
                $scope.activeData.setNum = $scope.activeData.setNum.substr(0, 1);
                // return errorService.error('请输入正确的手机号', null);
            }
            $scope.activeData.votePeoplePerDay = $scope.activeData.setNum;
        };


// -----------------------------------------------------------------------------------------------------wangEditor
//         $scope.editor = new E('#div4');
//         $scope.editor1 = new E('#div5');
//
//
//         $scope.editor.customConfig.customUploadImg = function (files, insert) {
//             // files 是 input 中选中的文件列表
//             // insert 是获取图片 url 后，插入到编辑器的方法
//             files.forEach(function (file) {
//                 Upload.upload({
//                     url: conf.yunApiPath + '/app/5988791a6b869f4e18d5c8d5/org/598878fc6b869f4e0f19fb47/yunFile',
//                     data: {
//                         file: file,
//                     }
//                 }).then(function (resp) {
//                     $http({
//                         method: 'GET',
//                         url: conf.yunApiPath + '/app/5988791a6b869f4e18d5c8d5/org/598878fc6b869f4e0f19fb47/yunFile/' + resp.data.data
//                     }).then(function (resp) {
//                         // console.log('Success ' + resp.data.data.cdnUrls[0].url);
//                         insert(resp.data.data.cdnUrls[0].url);
//                     }, function (resp) {
//                         // console.log('Error status: ' + resp.status);
//                     });
//                 }, function (resp) {
//                     // console.log('Error status: ' + resp.status);
//                 }, function (evt) {
//                     var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
//                     // console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
//                 });
//             });
//             // 上传代码返回结果之后，将图片插入到编辑器中
//         };
//         $scope.editor.create();
//
//
//         $scope.editor1.customConfig.customUploadImg = function (files, insert) {
//             // files 是 input 中选中的文件列表
//             // insert 是获取图片 url 后，插入到编辑器的方法
//             files.forEach(function (file) {
//                 Upload.upload({
//                     url: conf.yunApiPath + '/app/5988791a6b869f4e18d5c8d5/org/598878fc6b869f4e0f19fb47/yunFile',
//                     data: {
//                         file: file,
//                     }
//                 }).then(function (resp) {
//                     $http({
//                         method: 'GET',
//                         url: conf.yunApiPath + '/app/5988791a6b869f4e18d5c8d5/org/598878fc6b869f4e0f19fb47/yunFile/' + resp.data.data
//                     }).then(function (resp) {
//                         // console.log('Success ' + resp.data.data.cdnUrls[0].url);
//                         insert(resp.data.data.cdnUrls[0].url);
//                     }, function (resp) {
//                         // console.log('Error status: ' + resp.status);
//                     });
//                 }, function (resp) {
//                     // console.log('Error status: ' + resp.status);
//                 }, function (evt) {
//                     var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
//                     // console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
//                 });
//             });
//             // 上传代码返回结果之后，将图片插入到编辑器中
//         };


//         $scope.editor = new E('#div4');
//         $scope.editor1 = new E('#div5');va



        var ruleEditor = new wangEditor('div4');
        var despEditor = new wangEditor('div5');


        ruleEditor.config.uploadImgUrl = conf.yunApiPath + '/app/5988791a6b869f4e18d5c8d5/org/598878fc6b869f4e0f19fb47/yunFile';
        despEditor.config.uploadImgUrl = conf.yunApiPath + '/app/5988791a6b869f4e18d5c8d5/org/598878fc6b869f4e0f19fb47/yunFile';

        ruleEditor.config.uploadImgFileName = 'file';
        despEditor.config.uploadImgFileName = 'file';

        ruleEditor.config.zindex = 200000000;
        despEditor.config.zindex = 200000000;


        ruleEditor.config.menus = [
            'source',
            '|',
            'bold',
            'underline',
            'italic',
            'strikethrough',
            'eraser',
            'forecolor',
            'bgcolor',
            '|',
            'quote',
            'fontfamily',
            'fontsize',
            'head',
            'unorderlist',
            'orderlist',
            'alignleft',
            'aligncenter',
            'alignright',
            '|',
            'link',
            'unlink',
            'table',
            'emotion',
            '|',
            'img',
            'video',
            'insertcode',
            '|',
            'undo',
            'redo',
            'fullscreen'
        ];
        despEditor.config.menus = [
            'source',
            '|',
            'bold',
            'underline',
            'italic',
            'strikethrough',
            'eraser',
            'forecolor',
            'bgcolor',
            '|',
            'quote',
            'fontfamily',
            'fontsize',
            'head',
            'unorderlist',
            'orderlist',
            'alignleft',
            'aligncenter',
            'alignright',
            '|',
            'link',
            'unlink',
            'table',
            'emotion',
            '|',
            'img',
            'video',
            'insertcode',
            '|',
            'undo',
            'redo',
            'fullscreen'
        ];
        // editor.config.uploadParams = {
        //     file: 'file',
        // };

        // 自定义load事件

        despEditor.config.uploadImgFns.onload = function (resultText, xhr) {
            // resultText 服务器端返回的text
            // xhr 是 xmlHttpRequest 对象，IE8、9中不支持
            console.log('resultText1', resultText);
            console.log('xhr1', xhr);

            //将resultText转成json
            var jsonValue = eval("(" + resultText + ")");

            $http({
                method: 'GET',
                url: conf.yunApiPath + '/app/5988791a6b869f4e18d5c8d5/org/598878fc6b869f4e0f19fb47/yunFile/' + jsonValue.data
            }).then(function (resp) {
                console.log('Success1 ' + resp.data.data.cdnUrls[0].url);
                var imgUrl = resp.data.data.cdnUrls[0].url;
                var originalName = despEditor.uploadImgOriginalName || '';
                ruleEditor.command(null, 'insertHtml', '<img src="' + imgUrl + '" alt="' + imgUrl + '" style="max-width:100%;"/>');
                despEditor.command(null, 'insertHtml', '<img src="' + imgUrl + '" alt="' + imgUrl + '" style="max-width:100%;"/>');
                console.log('resp1', resp);
            }, function (resp) {
                // console.log('Error status: ' + resp.status);
            });

        };
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
                despEditor.command(null, 'insertHtml', '<img src="' + imgUrl + '" alt="' + imgUrl + '" style="max-width:100%;"/>');
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

        despEditor.config.uploadImgFns.ontimeout = function (xhr) {
            // xhr 是 xmlHttpRequest 对象，IE8、9中不支持
            alert('上传超时');
        };

        // 自定义error事件
        despEditor.config.uploadImgFns.onerror = function (xhr) {
            // xhr 是 xmlHttpRequest 对象，IE8、9中不支持
            alert('上传错误');
        };

        setTimeout(function () {
            ruleEditor.create();
            despEditor.create();
        }, 500);

        $scope.activeData.imgs = [];


        $scope.getInfo = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/voteActivity/" + $scope.id,
                params: {},
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                    console.log('respSave======', resp);
                    $scope.activeData = resp.data.data;
                    despEditor.$txt.html(resp.data.data.desp);
                    ruleEditor.$txt.html(resp.data.data.rule);
                    $scope.activeData.imgs = [];
                    $scope.activeData.setNum = $scope.activeData.votePeoplePerDay;
                    $scope.activeData.imgs.push(resp.data.data.primaryImgUrl);
                    // $scope.activeData.imgs.push(resp.data.data.cdnUrls[0].url);
                    console.log($scope.activeData.imgs);
                }, function (err) {
                    console.log('errSave======', err);
                }
            );
        };


        setTimeout(function () {
            if ($scope.id) {
                $scope.getInfo();
            }
        }, 500);
        //--------------图片上传
        $scope.activeData.imgs = [];

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
                        $scope.activeData.imgs.push(resp.data.data.cdnUrls[0].url);
                        console.log('$scope.activeData.imgs', $scope.activeData.imgs)

                    }, function (resp) {
                        console.log('Error status: ' + resp.status);
                    });
                }, function (resp) {
                    //console.log('Error status: ' + resp.status);
                }, function (evt) {
                    // var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                    // console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
                });
            }

        };


        $scope.checkDate = function () {

        };

        //时间控制
        laydate.render({
            elem: '#signUpStartTime',           //选择器
            type: 'datetime',                   //定义时间显示方式  如显示时分秒
            // theme: '#666',                      //自定义主题颜色
            // format: 'yyyy-MM-dd HH:mm',      //可任意组合
            done: function (value, date) {      //事件完成回调 将value赋值给自己定义的字符串
                $scope.activeData.signUpStartTime = value;
            }
        });

        laydate.render({
            elem: '#signUpEndTime',             //选择器
            type: 'datetime',                   //定义时间显示方式  如显示时分秒
            // theme: 'qqqq',                   //自定义主题颜色
            done: function (value, date) {      //事件完成回调 将value赋值给自己定义的字符串
                $scope.activeData.signUpEndTime = value;
            }
        });

        laydate.render({
            elem: '#voteStartTime',             //选择器
            type: 'datetime',                   //定义时间显示方式  如显示时分秒
            // theme: '#1b32d8',                   //自定义主题颜色
            done: function (value, date) {      //事件完成回调 将value赋值给自己定义的字符串
                $scope.activeData.voteStartTime = value;
            }
        });
        laydate.render({
            elem: '#voteEndTime',               //选择器
            type: 'datetime',                   //定义时间显示方式  如显示时分秒
            // theme: '#ff0081',                   //自定义主题颜色
            done: function (value, date) {      //事件完成回调 将value赋值给自己定义的字符串
                $scope.activeData.voteEndTime = value;
            }
        });

        /**
         * 删除指定的图片
         * @param index
         */
        $scope.cancelDel = function (index) {
            $scope.activeData.imgs.splice(index, 1);
        };
        $scope.activeData.forcePhone = '' ? true : false;
        $scope.activeData.forceFollow = '' ? true : false;


        $scope.save = function () {
            console.log('$scope.activeData.rule', ruleEditor.$txt.html());

            console.log('$scope.activeData.desp', despEditor.$txt.html());

            console.log('$scope.activeData.primaryImgUrl', $scope.activeData);
            // signUpStartTime: $scope.activeData.signUpStartTime,
            // signUpEndTime: $scope.activeData.signUpEndTime,
            // voteStartTime: $scope.activeData.voteStartTime,
            // voteEndTime: $scope.activeData.voteEndTime,
            var signStart = new Date($scope.activeData.signUpStartTime).getTime();
            var signEnd = new Date($scope.activeData.signUpEndTime).getTime();
            var voteStart = new Date($scope.activeData.voteStartTime).getTime();
            var voteEnd = new Date($scope.activeData.voteEndTime).getTime();
            if ($scope.activeData.signUpStartTime && $scope.activeData.signUpEndTime) {
                if (signEnd < signStart) {
                    console.log('报名开始时间不能大于报名结束时间')
                    return errorService.error('报名开始时间不能大于报名结束时间', null);
                }
            }

            if ($scope.activeData.voteStartTime && $scope.activeData.voteEndTime) {
                if (voteEnd < voteStart) {
                    console.log('投票开始时间不能大于投票结束时间')
                    return errorService.error('投票开始时间不能大于投票结束时间', null);
                }
            }

            if (voteStart < signStart) {
                return errorService.error('投票开始时间不能小于报名开始时间', null);
            }

            if (!$scope.activeData.activityName) {
                return errorService.error('请填写活动名称', null);
            }
            if (!$scope.activeData.signUpStartTime) {
                return errorService.error('请填写报名开始时间', null);
            }
            if (!$scope.activeData.signUpEndTime) {
                return errorService.error('请填写报名结束时间', null);
            }
            if (!$scope.activeData.voteStartTime) {
                return errorService.error('请填写投票开始时间', null);
            }
            if (!$scope.activeData.voteEndTime) {
                return errorService.error('请填写投票结束时间', null);
            }
            if (!$scope.activeData.maxVotePerDay) {
                return errorService.error('请填写每天投票次数', null);
            }
            if (!$scope.activeData.totalVoteCount) {
                return errorService.error('请填写每人总投票数', null);
            }
            if (!$scope.activeData.maxTicketPerDay) {
                return errorService.error('请填写参赛者每人每天被投票数', null);
            }
            if ($scope.activeData.imgs.length < 1) {
                $scope.activeNum = '2';
                return errorService.error('请选择主图', null);
            }
            console.log('$scope.ruleEditor..$txt.text()', ruleEditor.$txt.text());
            if (!ruleEditor.$txt.text()) {
                $scope.activeNum = '3';
                return errorService.error('请填写活动规则', null);
            }

            if (!$scope.activeData.shareTitle) {
                $scope.activeNum = '5';
                return errorService.error('请填写微信分享标题', null);
            }
            if (!$scope.activeData.shareContent) {
                $scope.activeNum = '5';
                return errorService.error('请填写微信分享内容', null);
            }
            if ($scope.id) {
                $http({
                    method: "PUT",
                    url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/voteActivity/" + $scope.id,
                    data: {
                        activityName: $scope.activeData.activityName,
                        signUpStartTime: $scope.activeData.signUpStartTime,
                        signUpEndTime: $scope.activeData.signUpEndTime,
                        voteStartTime: $scope.activeData.voteStartTime,
                        voteEndTime: $scope.activeData.voteEndTime,
                        maxVotePerDay: $scope.activeData.maxVotePerDay,
                        totalVoteCount: $scope.activeData.totalVoteCount,
                        maxTicketPerDay: $scope.activeData.maxTicketPerDay,
                        // totalTickect: $scope.activeData.totalTickect,
                        votePeoplePerDay: $scope.activeData.votePeoplePerDay,
                        forcePhone: $scope.activeData.forcePhone ? $scope.activeData.forcePhone : false,
                        forceFollow: $scope.activeData.forceFollow ? $scope.activeData.forceFollow : false,
                        primaryImgUrl: $scope.activeData.imgs[0],
                        rule: ruleEditor.$txt.html(),
                        desp: despEditor.$txt.html(),
                        wordsOfThanks: '',
                        shareTitle: $scope.activeData.shareTitle,
                        shareContent: $scope.activeData.shareContent,
                        voteStatusEnum: $scope.activeData.voteStatusEnum,
                    },
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    }
                }).then(function (resp) {
                        if (resp.data.status == 200) {
                            $scope.fallbackPage();
                        }
                    }, function (err) {
                        console.log('errSave======', err);
                    }
                );
            } else {
                $http({
                    method: "POST",
                    url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/voteActivity",
                    data: {
                        activityName: $scope.activeData.activityName,
                        signUpStartTime: $scope.activeData.signUpStartTime,
                        signUpEndTime: $scope.activeData.signUpEndTime,
                        voteStartTime: $scope.activeData.voteStartTime,
                        voteEndTime: $scope.activeData.voteEndTime,
                        maxVotePerDay: $scope.activeData.maxVotePerDay,
                        totalVoteCount: $scope.activeData.totalVoteCount,
                        maxTicketPerDay: $scope.activeData.maxTicketPerDay,
                        // totalTickect: $scope.activeData.totalTickect,
                        votePeoplePerDay: $scope.activeData.votePeoplePerDay,
                        forcePhone: $scope.activeData.forcePhone ? $scope.activeData.forcePhone : false,
                        forceFollow: $scope.activeData.forceFollow ? $scope.activeData.forceFollow : false,
                        primaryImgUrl: $scope.activeData.imgs[0],
                        rule: ruleEditor.$txt.html(),
                        desp: despEditor.$txt.html(),
                        wordsOfThanks: '',
                        shareTitle: $scope.activeData.shareTitle,
                        shareContent: $scope.activeData.shareContent,
                        voteStatusEnum: $scope.activeData.voteStatusEnum,
                    },
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    }
                }).then(function (resp) {
                        if (resp.data.status == 200) {
                            $scope.fallbackPage();
                        }
                    }, function (err) {
                        console.log('errSave======', err);
                    }
                );
            }

        };


        // laydate.render({
        //     elem: '#test5',                     //选择器
        //     type: 'datetime',                   //定义时间显示方式  如显示时分秒
        //     done: function (value, date) {      //事件完成回调 将value赋值给自己定义的字符串
        //         $scope.myData = value;
        //     }
        // });
        //
        // $scope.testSave = function () {
        //     console.log('$scope.activeData', $scope.activeData)
        // };

        /*返回上级*/
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.shelves", null, {reload: true});
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
    'errorService',
    '$filter',
    'authService',
    'loginService',
    'alertService',
    '$mdDialog',
    'Upload',
    '$stateParams'
];

export default Controller ;