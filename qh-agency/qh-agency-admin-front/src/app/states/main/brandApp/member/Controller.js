import conf from "../../../../conf";
import moment from "moment";
import laydate from "layui-laydate";
var $scope,
    $http,
    loginService,
    Upload,
    $stateParams,
    authService,
    errorService,
    alertService,
    dateFilter,
    $state;


UserListController.$inject = [
    '$scope',
    '$http',
    'loginService',
    'Upload',
    '$stateParams',
    'authService',
    'errorService',
    'alertService',
    'dateFilter',
    '$state'];
function UserListController(_$scope,
                            _$http,
                            _loginService,
                            _Upload,
                            _$stateParams,
                            _authService,
                            _errorService,
                            _alertService,
                            _dateFilter,
                            _$state) {
    $scope = _$scope;
    $http = _$http;
    $stateParams = _$stateParams;
    loginService = _loginService;
    authService = _authService;
    errorService = _errorService;
    Upload = _Upload;
    $state = _$state;
    dateFilter = _dateFilter;
    alertService = _alertService;
    loginService.loginCtl(true);
    $scope.brandAppId = $stateParams.brandAppId;
    conf.title = "人员管理>会员管理";
    $scope.data = {};
    $scope.user = {};
    $scope.curPage= 1;
    $scope.userList =[];
    $scope.pageChanged = function (curPage) {
        if ($scope.data.number >$scope.data.totalPages){
            return;
        };
        //权限相关
        // $scope.MEMBER_C = authService.hasAuthor("MEMBER_C");    //增
        $scope.PARTNERINFO_U = authService.hasAuthor("PARTNERINFO_U");    //改
        $scope.PARTNERINFO_R = authService.hasAuthor("PARTNERINFO_R");    //读
        $scope.PARTNERINFO_D = authService.hasAuthor("PARTNERINFO_D");    //禁用

        //时间选择器
        laydate.render({
            elem: '#test1', //指定元素
            done: function (value, date) {      //事件完成回调 将value赋值给自己定义的字符串
                $scope.user.startDate = value;
            }

        });
        laydate.render({
            elem: '#test2', //指定元素
            done: function (value, date) {      //事件完成回调 将value赋值给自己定义的字符串
                $scope.user.endDate = value;
            }
        });
        if ($scope.user.startDate) {
            $scope.user.startDate = moment($scope.user.startDate).format('YYYY-MM-DD');
        }
        if ($scope.user.endDate) {
            $scope.user.endDate = moment($scope.user.endDate).format('YYYY-MM-DD');
        }
        $http({
            method:"GET",
            url:conf.apiPath+"/brandApp/"+$scope.brandAppId+"/partnerStaff",
            params: {
                page:curPage ?curPage:$scope.curPage-1,
                size: conf.pageSize,
                keyWord: $scope.user.keyword,
                startDate: $scope.user.startDate,
                endDate: $scope.user.endDate,
                idList: $scope.searchList,
            },
            headers: {
                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                "brandApp-Id": $scope.brandAppId
            },
        }).success(function (data) {
            $scope.curPage =data.data.number+1;
            $scope.data = data.data;
            for (var i = 0 ;i<data.data.content.length;i++){
                $scope.userList.push(data.data.content[i].userId)
            }
            $http({
                method: 'GET',
                url:conf.oauthPath + '/api/user',
                params: {
                    userIds:$scope.userList,
                    size:100
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                for (var i = 0 ;i<resp.data.data.content.length;i++){
                    for (var j = 0 ;j<$scope.data.content.length;j++){
                        if(resp.data.data.content[i].id == $scope.data.content[j].userId){
                            $scope.data.content[j].realName = resp.data.data.content[i].realName;
                            $scope.data.content[j].phone = resp.data.data.content[i].phone;
                        }
                    }
                }
                $scope.data.recList = $scope.data.content;
            })
        });
    };
    $scope.pageChanged();




    $scope.search = function () {
        $http({
            method: 'GET',
            url:conf.oauthPath + '/api/user/search',
            params: {
                keyWords: $scope.user.keyword,
            },
            headers: {
                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                "brandApp-Id": $scope.brandAppId
            }
        }).then(function (resp) {
            console.log('search',resp);
            $scope.searchList = resp.data.data;
            if( $scope.searchList.length == 0 ){
                $scope.data.recList = [];
            }else {
                $scope.pageChanged()
            }

        })
    };














    $scope.s = {
        // a: "aaa",
        formatDate: function (date) {
            return dateFilter(date, "yyyy-MM-dd");
        }
    };
    $scope.initDateTime = function (ev) {
        $scope.user.startDate = null;
        $scope.user.endDate = null;
        this.isOpen = false;
    };
    $scope.initDateTime();


    // 初始化的时候就获取第一页的数据
    //获取用户手机号
    // $scope.getUserPhone = () => {
    //     $http({
    //         method: "GET",
    //         url: conf.oauthPath + "/api/user/info",
    //         //url: `https://login.kingsilk.net/local/16600/rs/api/user/info`,
    //         params: {
    //         },
    //         headers: {
    //             'Authorization': 'Bearer ' + loginService.getAccessToken(),
    //         }
    //     }).then(function (resp) {
    //            console.log(resp);
    //         }, function () {
    //
    //         }
    //     );
    // };
    // $scope.getUserPhone();
    $scope.alert = function (userId, enabled) {
        console.log(enabled);
        $scope.userId = userId;
        $scope.disabled =enabled;
        var tmp;
        if (enabled) {
            tmp = "禁用"
        } else {
            tmp = "启用"
        }
        alertService.confirm(null, "确定" + tmp + "该会员？", "温馨提示", "取消", "确认").then(function (data) {

            if (data == true) {
                $http({
                    method:"PUT",
                    url:conf.apiPath +"/brandApp/"+$scope.brandAppId+"/partnerStaff/"+$scope.userId+"/enable",
                    params: {
                        disabled: $scope.disabled
                    },
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    },
                }).success(function (data) {
                    if (data.code === "SUCCESS") {
                        $scope.pageChanged();
                    }
                });
            }
        });
    };

    $scope.export = function () {
        window.location.href = conf.apiPath + "/member/export"
    }

    $scope.uploadFiles = function (file) {
        $scope.f = file;
        // $scope.errFile = errFiles && errFiles[0];
        if (file) {

            file.upload = Upload.upload({
                url: conf.apiPath + "/member/upload",
                data: {excelFile: file}
            });

            file.upload.then(function (response) {
                if (response.data.code == "SUCCESS") {
                    return errorService.error("上传成功", null)
                }
                // $timeout(function () {
                //     file.result = response.data;
                // });
            }, function (response) {
                if (response.status > 0)
                    $scope.errorMsg = response.status + ': ' + response.data;
            }, function (evt) {
                file.progress = Math.min(100, parseInt(100.0 *
                    evt.loaded / evt.total));
            });
        }
    }

    // $scope.uploadImg = function (file) {
    //     $scope.f = file;
    //     // $scope.errFile = errFiles && errFiles[0];
    //     if (file) {
    //         file.upload = Upload.upload({
    //             url: conf.apiPath + "/uploadImg/uploadImg?imgType=UPLOAD&type=UPLOAD&X-Requested-With=XMLHttpRequest",
    //             data: {excelFile: file,_umeditor:false}
    //         });
    //
    //         file.upload.then(function (data) {
    //             console.log(data)
    //             if (data.data.data.code == 'SUCCESS') {
    //                 $scope.imgs=data.data.data.file_path;
    //                 console.log($scope.imgs)
    //             } else {
    //                 var msg = object.msg;
    //                 $window.alert(msg);
    //             }
    //         });
    //     }
    // }
    // $scope.uploadImg = function (file) {
    //     $scope.f = file;
    //     // $scope.errFile = errFiles && errFiles[0];
    //     if (file) {
    //         file.upload = Upload.upload({
    //             url: conf.apiPath + "/uploadImg/uploadImg?imgType=UPLOAD&type=UPLOAD&X-Requested-With=XMLHttpRequest",
    //             data: {excelFile: file}
    //         });
    //
    //         file.upload.then(function (data) {
    //             console.log(data)
    //             if (data.data.data.code == 'SUCCESS') {
    //                 $scope.imgs = data.data.data.file_path;
    //                 console.log($scope.imgs)
    //             } else {
    //                 var msg = object.msg;
    //                 $window.alert(msg);
    //             }
    //         });
    //     }
    // }
    //
    // $scope.upload = function () {
    //
    //     console.log(document.getElementById("file"));
    //
    //     if (document.getElementById("file").files && document.getElementById("file").files.length > 0) {
    //         // var file = angular.element("#file")[0].files[0];
    //         var file = document.getElementById("file").files[0];
    //         if (!file) {
    //             return;
    //         }
    //         //开始上传图片
    //         qiniuUploadService.upload(file, true).then(function (data) {
    //             if (data.data.data.key) {
    //                 $scope.data.key = data.data.data.key;
    //                 $scope.imgs = data.data.data.file_path;
    //             }
    //         });
    //     } else {
    //         alertService.alert(null, '请选择图片', null, '取消');
    //     }
    // };

    // $scope.submit = function () {
    //     if ($scope.form.file.$valid && $scope.file) {
    //         qiniuUploadService.upload(file, true).then(function (data) {
    //             if (data.data.data.key) {
    //                 $scope.data.key = data.data.data.key;
    //                 $scope.imgs = data.data.data.file_path;
    //             }
    //         });
    //     }
    // };

}

export default UserListController;
