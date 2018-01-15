import conf from "../../../../conf";
import info from "!html-loader?minimize=true!./info.html";

var $scope,
    $http,
    $stateParams,
    loginService,
    authService,
    alertService,
    $mdDialog;
class Controller {
    constructor(_$scope, _$http, _loginService, _authService, _$mdDialog, _alertService, _$stateParams) {
        $scope = _$scope;
        $http = _$http;
        $stateParams = _$stateParams;
        loginService = _loginService;
        authService = _authService;
        $mdDialog = _$mdDialog;
        alertService = _alertService;

        loginService.loginCtl(true);

        //权限相关
        $scope.STAFF_C = authService.hasAuthor("STAFF_C");    //增
        $scope.STAFF_U = authService.hasAuthor("STAFF_U");    //改
        $scope.STAFF_R = authService.hasAuthor("STAFF_R");    //读
        $scope.STAFF_D = authService.hasAuthor("STAFF_D");    //删

        $scope.brandAppId = $stateParams.brandAppId;
        var vm = this;
        $scope.data = {};
        // $scope.data.number = $scope.data.number?$scope.data.number:1;\
        $scope.curPage=1;
        $scope.userList = [];
        $scope.pageChanged = function (curPage) {
            $http.get(conf.apiPath + "/brandApp/"+$scope.brandAppId+"/staff", {
                params: {
                    curPage: curPage?curPage:$scope.curPage-1,
                    pageSize: conf.pageSize,
                    keyWord: $scope.keyword,
                    idList:$scope.searchList,
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
            }).success(function (resp) {
                console.log('2222222',resp.data);
                $scope.data = resp.data;
                $scope.curPage = resp.data.number + 1;

                // for (var i = 0 ;i<resp.data.content.length;i++){
                //   $scope.userList.push(resp.data.content[i].userId)
                // }
                //console.log(111111111111111111,$scope.userList);
                // $http({
                //     method: 'GET',
                //     url:conf.oauthPath + '/user',
                //     params: {
                //         users:$scope.userList
                //     },
                //     headers: {
                //         'Authorization': 'Bearer ' + loginService.getAccessToken(),
                //         "brandApp-Id": $scope.brandAppId
                //     }
                // }).then(function (resp) {
                //     console.log('user-phone-name',resp);
                //     for (var i = 0 ;i<resp.data.data.length;i++){
                //         for (var j = 0 ;j<$scope.data.content.length;j++){
                //            if(resp.data.data[i].userId == $scope.data.content[j].userId){
                //                $scope.data.content[j].realName = resp.data.data[i].realName;
                //                $scope.data.content[j].phone = resp.data.data[i].phone;
                //            }
                //         }
                //     }
                // })
            });
        };
        $scope.pageChanged();

        
        
        
        $scope.search = function () {
            $http({
                method: 'GET',
                url:conf.oauthPath + '/api/user/search',
                params: {
                    keyWords: $scope.keyword,
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                console.log('search',resp);
                $scope.searchList = resp.data.data;
                if( $scope.searchList.length == 0 ){
                    $scope.data.content = [];
                }else {
                    $scope.pageChanged()
                }
            })
        };

        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        $scope.alert = function (userId, enabled, locked) {
            if (enabled) {
                $scope.statusDesp = "启用";
            } else {
                $scope.statusDesp = "禁用";
            }
            if (locked) {
                $scope.statusDesp = "解锁";
            }
            $scope.userId = userId;
            $scope.enabled = enabled;
            $("#myModal").modal("show");
        };

        /**
         * 禁用或者启用
         */
        $scope.setEnabled = function (staff) {
            $scope.id = staff.id;
            var status = '';
            if (staff.disabled) {
                status = '启用';
                $scope.sta=false;
            } else {
                status = '禁用';
                $scope.sta=true;
            }
            alertService.confirm(null, "确定" + status + "该员工？", "温馨提示", "取消", "确认")
                .then(function (data) {
                    if (data) {


                        $http({
                            method: 'put',
                            // url: conf.apiPath + "/item/search",
                            url:    conf.apiPath + '/brandApp/'+$scope.brandAppId+'/staff/'+staff.id+'/enable',
                            params: {
                                status: $scope.sta,
                            },
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            }
                        }).then(function (resp) {
                            console.log(resp);
                            if (resp.data.code === "SUCCESS") {
                                $scope.pageChanged();
                            }
                        })
                    }
                });


        };

        $scope.openDialog = function (locals, ev) {
            locals = locals ? locals : {};
            $mdDialog.show({
                template: info,
                parent: angular.element(document.body).find('#qh-net.kingsilk.qh.agency-net.kingsilk.qh.net.kingsilk.qh.agency.admin-front'),
                targetEvent: ev,
                clickOutsideToClose: true,
                locals: locals,
                fullscreen: false,
                controller: [function () {
                    var vm = this;
                    vm.realName = locals.staff.realName;
                    vm.nickName = locals.staff.nickName;
                    vm.phone = locals.staff.phone;
                    vm.idNumber = locals.staff.idNumber;
                    vm.staffGroupList = locals.staff.staffGroupList;
                    vm.disabled = locals.staff.disabled;
                    vm.dateCreated = locals.staff.dateCreated;
                    vm.createdBy = locals.staff.createdBy;

                    vm.cancel = function () {
                        return $mdDialog.cancel();
                    };
                }],
                controllerAs: "vm"
            }).then(function (answer) {
            }, function () {
            });
        };


    }


}

Controller.$inject = [
    '$scope', '$http', 'loginService', 'authService', '$mdDialog', 'alertService', '$stateParams'
];

export default Controller ;
