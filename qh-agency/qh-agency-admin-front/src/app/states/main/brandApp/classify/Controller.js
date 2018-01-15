import conf from "../../../../conf";
import dialog from "!html-loader?minimize=true!./dialog.html";
import 'angular-ui-tree/dist/angular-ui-tree';

var $scope,
    $http,
    $stateParams,
    loginService,
    authService,
    alertService,
    errorService,
    $state,
    $mdDialog;

class Controller {
    constructor(_$scope, _$http, _$stateParams, _loginService, _authService, _alertService, _errorService, _$state, _$mdDialog) {
        $scope = _$scope;
        $http = _$http;
        $stateParams = _$stateParams;
        loginService = _loginService;
        authService = _authService;
        alertService = _alertService;
        errorService = _errorService;
        $state = _$state;
        $mdDialog = _$mdDialog;

        loginService.loginCtl(true);
        //权限相关
        $scope.CATEGORY_C = authService.hasAuthor("CATEGORY_C");    //增
        $scope.CATEGORY_U = authService.hasAuthor("CATEGORY_U");    //改
        $scope.CATEGORY_R = authService.hasAuthor("CATEGORY_R");    //读
        $scope.CATEGORY_D = authService.hasAuthor("CATEGORY_D");    //删


        $scope.data = {};
        $scope.id = '';
        $scope.brandAppId = $stateParams.brandAppId;

        $scope.curPage=1;



        $scope.authorityListShow = true;
        $scope.showAuthorityList=function () {
            $scope.authorityListShow = !$scope.authorityListShow
        };

        $scope.show = function (curPage) {

            // console.log('1==',curPage)
            // console.log('2==',$scope.curPage)
            $http({
                method: "get",
                url: conf.apiPath + "/brandApp/"+$scope.brandAppId+"/category",
                params: {
                    page: curPage ?curPage:0,
                    size: conf.pageSize,
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
            }).then(function successCallback(response) {
                $scope.datas = response.data.data;
                $scope.page=response.data.data;
                $scope.curPage =response.data.data.number+1;
                if($scope.datas.length == 0){
                    var tree = [];
                }else {
                    var tree = $scope.getTree($scope.datas.slice());
                }
                // console.log('tree',tree);
                $scope.exampleAuthorityList = tree;

                // console.log($scope.datas);
            }, function errorCallback(response) {
                // 请求失败执行代码
                console.log(response)
            });
        };
        $scope.show();

        // 表获取树
        $scope.getTree = function (list) {
            var root =[];
            var data ={};
            for(var i=0;i<list.length;i++){
                if(!list[i].parentId){
                    data = list[i];
                    data.lv =['true'];
                    root.push(data);
                    list.splice(i,1);
                    i--;
                }
            }
            console.log('root==',root);
            var find = function(child,list){
                for(var j=0;j<list.length; j++){
                    if(child.id==list[j].parentId){
                        data =list[j];
                        if(child.last){
                            data.parentLast = true;
                            data.lv = child.lv.concat('true');
                        }else {
                            data.lv = child.lv.concat('false');
                        }
                        if(child.child){
                            child.child.push(data);
                        }else {
                            child.child = [];
                            child.child.push(data);
                        }
                        list.splice(j,1);
                        j--;
                    }
                }
                if(child.child){
                    child.child[child.child.length-1].last = true;
                    for (var k=0;k<child.child.length; k++){
                        find(child.child[k],list);
                    }
                }
            };
            root[root.length-1].last = true;
            for(var j=0;j<root.length;j++){
                find (root[j],list);
            }
            return root;
        };

        //控制分类标签子目录i战士隐藏
        $scope.toggle = function (name) {
            name.collapsed = !name.collapsed;
        };

        $scope.showCategory = {};
        $scope.choose = function (data) {
            // console.log(data);
            // console.log(data.node);

            if(data == 'top-level'){
                $scope.status = 'top-level';
                $scope.showCategory = {};
            }else {
                $scope.status = 'edit';
                $scope.showCategory = angular.copy(data.node);
                if(data.node.parentId){
                        for(var i=0;i<$scope.datas.length;i++){
                            if($scope.datas[i].id == data.node.parentId){
                                $scope.showCategory.parentName = $scope.datas[i].name;
                                break;
                            }
                        }
                }
            }
        };

        $scope.status = 'error';
        $scope.addNew = function () {

            $scope.showCategory.parentName = $scope.showCategory.name;
            $scope.showCategory.parentId = $scope.showCategory.id;
            $scope.showCategory.name = '';
            $scope.showCategory.id = '';
            $scope.showCategory.icon = '';
            $scope.showCategory.order = '';
            $scope.showCategory.desp = '';


            $scope.status = 'add';
        };
        $scope.save = function (type) {
            var g = /^[1-9]*[1-9][0-9]*$/;
            if(!g.test($scope.showCategory.order)){
                return  errorService.error('显示排序必须是正整数', null);
            }
            // console.log('$scope.showCategory=============',$scope.showCategory);
            let methodType=null;
            if(type == 'edit') {
                methodType = "PUT";   //编辑
            }else if(type == 'add') {
                methodType = "POST";   //新增
            }
            $http({
                method:methodType,
                url:type == 'edit'? conf.apiPath +  "/brandApp/"+$scope.brandAppId+"/category/"+$scope.showCategory.id : conf.apiPath +  "/brandApp/"+$scope.brandAppId+"/category",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
                data: {
                    parent: $scope.showCategory.parentId ? $scope.showCategory.parentId : null,
                    name: $scope.showCategory.name,
                    icon: $scope.showCategory.icon,
                    order: $scope.showCategory.order,
                    desp: $scope.showCategory.desp,
                },
            }).then(function successCallback(response) {
                console.log(response);
                if (response.status == 200) {
                    console.log(2312);
                    $scope.show(0);
                }
            }, function errorCallback(response) {
                // 请求失败执行代码
                return errorService.error(response.data.message, null);

            });
        }













        $scope.enable = function (id, disabled) {
            $scope.disabled = disabled;
            $scope.id = id;

            $http({
                method: "POST",
                url: conf.apiPath + "/category/enable",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
                data: {
                    id: $scope.id,
                    disabled: !$scope.disabled
                },
            }).then(function successCallback(response) {
                if (response.status == 200) {
                    //讲图标变色
                    $scope.show();
                }
            }, function errorCallback(response) {
                // 请求失败执行代码
            });

        };

        $scope.showChild = function (parent) {
            parent.show = !parent.show;
        };

        $scope.showNum = function (num) {
            for (var i = 0; i < $scope.datas.length; i++) {

            }

        };

        $scope.update = function (classify) {
            $scope.classify = classify;
            var parentId = '';
            if ($scope.classify.parent != null) {
                parentId = $scope.classify.parent.id;
            }
            $state.go("main.brandApp.claAdd", {
                id: $scope.classify.id,
                parentId: parentId,
                name: $scope.classify.name,
                img: $scope.classify.img,
                icon: $scope.classify.icon,
                order: $scope.classify.order,
                desp: $scope.classify.desp
            });
        }
        ;
        $scope.openDialog = function (locals, ev) {
            locals = locals ? locals : {};
            $mdDialog.show({
                template: dialog,
                parent: angular.element(document.body).find('#qh-net.kingsilk.qh.agency-net.kingsilk.qh.net.kingsilk.qh.agency.admin-front'),
                targetEvent: ev,
                clickOutsideToClose: true,
                locals: locals,
                fullscreen: false,
                controller: [function () {
                    var vm = this;
                    vm.isAdd = locals.isAdd;
                    if (locals.classify) {
                        vm.id = locals.classify.id;
                        vm.name = locals.classify.name;
                        vm.img = locals.classify.img;
                        vm.icon = locals.classify.icon;
                        vm.order = locals.classify.order;
                        vm.desp = locals.classify.desp;
                    }
                    vm.parent = locals.parent;
                    if (vm.parent) {
                        vm.parentName = locals.parent.name;
                    }
                    vm.save = function () {
                        let methodType=null;
                        if(vm.id) {
                            methodType = "PUT";   //编辑
                        }else {
                            methodType = "POST";   //新增
                        }
                        $http({
                            method:methodType,
                            url:vm.id? conf.apiPath +  "/brandApp/"+$scope.brandAppId+"/partner/123/category/"+vm.id : conf.apiPath +  "/brandApp/"+$scope.brandAppId+"/partner/123/category",
                            headers: {
                                'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                "brandApp-Id": $scope.brandAppId
                            },
                            data: {
                                //isAdd: vm.isAdd,
                                //id: vm.id,
                                parent: vm.parent ? vm.parent.id : null,
                                name: vm.name,
                                icon: vm.icon,
                                order: vm.order,
                                desp: vm.desp,
                            },
                        }).then(function successCallback(response) {
                            // console.log(response);
                            if (response.status == 200) {
                                $mdDialog.cancel();
                                $scope.show();
                            }
                        }, function errorCallback(response) {
                            // 请求失败执行代码
                            return errorService.error(response.data.data, null);

                        });
                    };
                    vm.cancel = function () {
                        return $mdDialog.cancel();
                    };
                }],
                controllerAs: "vm"
            }).then(function (answer) {
            }, function () {
            });
        };
        //删除
        $scope.delete = function (id) {
            alertService.confirm(null, "确定删除该分类吗？", "温馨提示", "取消", "确认").then(function (data) {
                if (data == true) {
                    $scope.id = id;
                    $http({
                        method: "delete",
                        url: conf.apiPath +  "/brandApp/"+$scope.brandAppId+"/category/"+$scope.id,
                        headers: {
                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                            "brandApp-Id": $scope.brandAppId
                        },
                        // data: {
                        //     id: $scope.id,
                        // },
                    }).then(function successCallback(data) {
                        if (data.data.status == 200) {
                            //讲图标变色
                            $scope.showCategory = {};
                            $scope.status = 'error';
                            $scope.show('0');
                        } else {

                            return errorService.error(data.data.data, null);
                        }
                    }, function errorCallback(response) {
                        // 请求失败执行代码
                    });
                }
            });
        }
    }
}

Controller.$inject = [
    '$scope', '$http', '$stateParams', 'loginService', 'authService', 'alertService', 'errorService', '$state', '$mdDialog',
];

export default Controller ;
