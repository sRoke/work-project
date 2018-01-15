
import conf from "../../../../conf";
import BSON from "bson";
import "bootstrap/dist/css/bootstrap.css";
import E from 'wangeditor';
import 'angular-ui-tree/dist/angular-ui-tree';
var $scope,
    $http,
    loginService,
    errorService,
    $state,
    Upload,
    $window,
    $stateParams,
    alertService,
    $filter;
class Controller {
    constructor(_$scope, _$http, _loginService, _errorService, _$state, _Upload, _$stateParams, _$window, _alertService,_$filter) {
        // $scope.authorityListTure = true;
        $scope = _$scope;
        loginService = _loginService;
        errorService = _errorService;
        alertService = _alertService;
        $http = _$http;
        Upload = _Upload;
        $window = _$window;
        $state = _$state;
        $filter = _$filter;
        $stateParams = _$stateParams;
        loginService.loginCtl(true);
        $scope.brandAppId = $stateParams.brandAppId;
        $scope.about = $stateParams.about;
        $scope.startSkus = [];
        $scope.authorityListShowValue = false; //商品分类默认收起
        $scope.authorityListStatus = '展示标签';
        $scope.currentNavItem = 'page1';




        $scope.hideList = function () {
            //隐藏商品标签
            $scope.categoryShow = false;
        };
        $scope.stopPropagation = function($event){
            $event.stopPropagation();
        };


        // -----------------------------------------------------------------------------------------------------wangEditor 副文本初始化
        $scope.editor = new E('#div4');
        $scope.editor.customConfig.customUploadImg = function (files, insert) {
            // files 是 input 中选中的文件列表
            // insert 是获取图片 url 后，插入到编辑器的方法
            files.forEach(function (file) {
                Upload.upload({
                    url: conf.yunApiPath + '/app/5988791a6b869f4e18d5c8d5/org/598878fc6b869f4e0f19fb47/yunFile',
                    data: {
                        file: file,
                    }
                }).then(function (resp) {
                    $http({
                        method: 'GET',
                        url: conf.yunApiPath + '/app/5988791a6b869f4e18d5c8d5/org/598878fc6b869f4e0f19fb47/yunFile/' + resp.data.data
                    }).then(function (resp) {
                        // console.log('Success ' + resp.data.data.cdnUrls[0].url);
                        insert(resp.data.data.cdnUrls[0].url);
                    }, function (resp) {
                        // console.log('Error status: ' + resp.status);
                    });
                }, function (resp) {
                    // console.log('Error status: ' + resp.status);
                }, function (evt) {
                    var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                    // console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
                });
            });
            // 上传代码返回结果之后，将图片插入到编辑器中
        };
        $scope.editor.create();
        //-------------------------------------------------------------------------------------------------------------------
        //---------------------------------------------切换tab
        $scope.changePage =function (page) {
            if(page == 1){
                $scope.currentNavItem = 'page1';
            }else {
                //发送请求前非空验证
                if (!$scope.baseData.title) {
                    return errorService.error("标题不能为空！", null)
                }
                if ($scope.uploadCategory.length < 1) {
                    return errorService.error("分类标签至少选择一项！", null)
                }
                if ($scope.baseData.imgs.length < 1) {
                    return errorService.error("主图至少上传一张！", null)
                }
                if ($scope.baseData.Specifications.length < 1) {
                    return errorService.error("商品规格至少选择一个！", null)
                }
                if ($scope.baseData.skus.length < 1) {
                    return errorService.error("sku至少生成一个！", null)
                }
                $scope.currentNavItem = 'page2';
            }
            scrollTo(0,0);
        };
        //----------------------------------------------------------------------------------------------------------------------
        // 一些数据声明
        $scope.baseData = {
            'title':'', //标题
            'desp':'',//副标题
            'chips':[],//标签
            "imgs": [],//主图
            "status": [{status: "EDITING", value: '编辑中'},
                {status: "APPLYING", value: '申请上架中'},
                {status: "UNAPPROVED", value: '审核未通过'},
                {status: "NORMAL", value: '正常'},
                {status: "OUT_OF_STOCK", value: '缺货'},
                {status: "SALE_OFF", value: '已下架'}],
            'Specifications':[], // 规格
            'skus':[],//sku,
        };
//--------------------------------------------------------------------------------------------------------------------------
        if ($stateParams.id) {//判断不是新增页
            //根据id获取信息
            $http.get(conf.apiPath + "/brandApp/" + $scope.brandAppId + "/item/" + $stateParams.id, {
                // params: {
                //     id: ,
                // },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
            }).success(function (data) {
                // $scope.baseData = {};
                $scope.baseData.title = data.data.title;   //短标题
                $scope.baseData.desp = data.data.desp;     //描述
                $scope.editor.txt.html(data.data.detail);  //富文本
                // $scope.htmlcontent = data.data.detail;     //富文本
                $scope.statu = data.data.status;           //状态
                $scope.baseData.imgs = data.data.imgs;     //主图
                //分类
                $scope.uploadCategory = angular.copy(data.data.tags);
                // console.log('uploadCategory',$scope.uploadCategory);
                $scope.categoryInfo();//页面加载获取所以分类标签
                //规格
                $scope.baseData.Specifications =[];
                for (var k = 0; k < data.data.specs.length; k++) {
                    for (var i = 0;i<data.data.specs[k].itemPropValue.length;i++){
                        data.data.specs[k].itemPropValue[i].flog = true;
                    }
                    $scope.baseData.Specifications[k] = {
                        objId:data.data.specs[k].id,
                        id: data.data.specs[k].itemProp.id,
                        realName: data.data.specs[k].itemProp.name,
                        showPop:false,
                        itemProp:data.data.specs[k].itemPropValue,
                        itemPropList:data.data.specs[k].itemPropValueList,
                        selectItemPropChips:[],
                    };
                }
                //sku
                $scope.baseData.skus = [];
                for (var n = 0; n < data.data.skuList.length; n++) {
                    var specses = [];
                    for (var y = 0; y < $scope.baseData.Specifications.length; y++) {
                        for (var x = 0; x < data.data.skuList[n].specList.length; x++) {
                            if ($scope.baseData.Specifications[y].id == data.data.skuList[n].specList[x].itemProp.id) {
                                specses.push({
                                    name: data.data.skuList[n].specList[x].itemProp.name,
                                    objId: data.data.skuList[n].specList[x].id,
                                    propId: data.data.skuList[n].specList[x].itemProp.id,
                                    value: {
                                        id: data.data.skuList[n].specList[x].itemPropValue.id,
                                        name: data.data.skuList[n].specList[x].itemPropValue.name
                                    }
                                })
                            }
                        }
                    }
                    $scope.baseData.skus.push({
                        id: data.data.skuList[n].id,
                        generalAgencyPrice: $filter('number')(data.data.skuList[n].generalAgencyPrice / 100, 2).replace(/,/g, ""),
                        regionalAgencyPrice: $filter('number')(data.data.skuList[n].regionalAgencyPrice / 100, 2).replace(/,/g, ""),
                        leaguePrice: $filter('number')(data.data.skuList[n].leaguePrice / 100, 2).replace(/,/g, ""),
                        salePrice: $filter('number')(data.data.skuList[n].salePrice / 100, 2).replace(/,/g, ""),
                        labelPrice: $filter('number')(data.data.skuList[n].labelPrice / 100, 2).replace(/,/g, ""),
                        // code:data.data.skuList[n].code,
                        storage: data.data.skuList[n].storage,
                        status: data.data.skuList[n].status,
                        specs: specses,
                        skuCode:data.data.skuList[n].code,
                        img:data.data.skuList[n].imgs,
                    })
                }
                $scope.startSkus = $scope.baseData.skus;

                // console.log(' $scope.skus==========', $scope.skus);
                }
            )
        }




        //编辑状态查找已经存在的sku
        function updataSku(startSkus, skus) {
            // console.log('startSkus=======',startSkus);
            // console.log('skus=======',skus);
            var flog = true;
            for (var i = 0; i < skus.length; i++) {
                for (var j = 0; j < startSkus.length; j++) {
                    for (var m = 0; m < startSkus[j].specs.length; m++) {
                        // console.log('start',startSkus[j].specs[m].value.id )
                        // console.log('skus',skus[i].specs[m].id )
                        if (startSkus[j].specs[m].value.id !== skus[i].specs[m].value.id) {
                            flog = false;
                            // console.log('00000000000');
                            break;
                        } else {
                            flog = true;
                        }
                        // console.log('1111111');
                    }
                    if (flog == true) {
                        // console.log('startSkusjjjjjjjjjj=======',startSkus[j]);
                        // console.log('skusiiiiiiiiiii=======',skus[i]);
                        skus[i] = startSkus[j];
                    }
                    // console.log('222222222');
                }
                // console.log('333333333');
            }
            // console.log('44444444');
        }
















// -------------------------------------------------------------------------------------------------------------新版商品发布

        //----------------------------------------------------标签
        $scope.categoryShow = false;
        $scope.changeCategory = function () {
            $scope.categoryShow = !$scope.categoryShow
        };
        $scope.authorityListShow = true;
        $scope.showAuthorityList=function () {
            $scope.authorityListShow = !$scope.authorityListShow
        };
        //分类标签获取
        $scope.exampleAuthorityList = [];//存储每个分类标签,分父子级 用于页面展示
        $scope.staffGroup = [];//存储每个分类标签,无父子级 存储每个id和状态
        $scope.categoryInfo = function () {
            $http({
                method: "get",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/category",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
            }).success(function (data) {
                $scope.baseData.chips = [];
                for (var i=0;i<data.data.length;i++){
                    if($scope.uploadCategory.indexOf(data.data[i].id) !== -1 ){
                        $scope.baseData.chips.push(data.data[i]);
                    }
                }
                var tree = $scope.getTree(data.data);
                // console.log('tree',tree);
                $scope.exampleAuthorityList = tree;
            });
        };
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
            // console.log('root==',root);
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

        //--------------选中标签
        //分类选中
        $scope.uploadCategory = [];//--------------------------------------------------------------------存储被选中的分类标签 用来上传
        $scope.choose = function (scope) {
            if (scope.node.status){
                $scope.uploadCategory.push(scope.node.id);
                $scope.baseData.chips.push(scope.node);
            }else {
                $scope.uploadCategory.splice($scope.uploadCategory.indexOf(scope.node.id), 1);
                $scope.baseData.chips.splice($scope.baseData.chips.indexOf(scope.node), 1);
            }
        };
        // $scope.chipDel = function(index,chips){
        //     chips.splice(index, 1);
        // };
        //控制分类标签子目录i战士隐藏
        $scope.toggle = function (name) {
            name.collapsed = !name.collapsed;
        };




        //------------------------------------------------添加商品规格

        $scope.baseData.Specifications = [];         //-----------------------存储创建的商品规格
        $scope.SpecificationsList = [];     //-----------------------存储规格列表
        //---------------------------------显示该规格点击添加后显示的模块
        $scope.showPopup = function (Specification,status) {
            // console.log('Specification',Specification);
            if (Specification.id){
                if(status == 'true'){
                    // console.log('==========',Specification.itemProp);
                    for (var i=0;i<Specification.selectItemPropChips.length;i++){
                        var flog = false;
                        for (var j=0;j<Specification.itemPropList.length;j++){
                            if (Specification.itemPropList[j].name == Specification.selectItemPropChips[i]){
                                Specification.itemProp.push(Specification.itemPropList[j]);
                                flog = true;
                                break;
                            }
                        }
                        if (flog){
                            // console.log('true',Specification.selectItemPropChips[i]); //----------------已经存在的
                        }else {

                            // console.log('false',Specification.selectItemPropChips[i]); //-------------- 需要新增的
                            // console.log('false',Specification); //-------------- 需要新增的


                            //规格属性添加 -------------------todo
                            $http({
                                method: "GET",
                                url: conf.apiPath +"/brandApp/"+$scope.brandAppId+"/itemProp/"+Specification.id+'/propValue',
                                headers: {
                                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                    "brandApp-Id": $scope.brandAppId
                                },
                                params: {
                                    name: Specification.selectItemPropChips[i],
                                }
                            }).then(function successCallback(response) {
                                if (response.status == 200) {
                                    Specification.itemProp.push(response.data.data);
                                    $scope.getSkus($scope.baseData.Specifications);
                                    $http({
                                        method: "get",
                                        url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/itemProp/itemPropListItem",
                                        params: {
                                            itemPropKeyword: Specification.id,
                                            // page: 0,
                                            // size: 1000
                                        },
                                        headers: {
                                            'Authorization': 'Bearer ' + loginService.getAccessToken(),
                                            "brandApp-Id": $scope.brandAppId
                                        },
                                    }).success(function (data) {
                                        Specification.itemPropList = data.data;
                                    });
                                }
                            }, function errorCallback(response) {
                                // 请求失败执行代码
                            });
                        }
                    }
                    $scope.getSkus($scope.baseData.Specifications);
                    Specification.showPop = !Specification.showPop;
                }else {
                    Specification.selectItemPropChips = [];
                    Specification.showPop = !Specification.showPop;
                }
            }else {
                return errorService.error("请选选择规格！", null)
            }



        };
        //------------------------------------删除某个规格
        $scope.closeSpec =function (index,Specification) {
            $scope.baseData.Specifications.splice(index,1);
            $scope.getSkus($scope.baseData.Specifications);
        };
        
        //-----------------------------------------------------
        $scope.popupToggle = false;
        $scope.popupInputShowToggle =function () {
            $scope.popupToggle = !$scope.popupToggle;
        };

        //------------------------------------选择规格属性
        $scope.selectItemProp = function (itemProp,Specification) {
                //------------------------------------------判断输入框内是否重复
            for (var i = 0; i < Specification.selectItemPropChips.length; i++) {
                if (Specification.selectItemPropChips[i] == itemProp.name) {
                    return;
                }
            }
                //------------------------------------------判断是否已经添加过
            for (var j = 0; j < Specification.itemProp.length; j++) {
                if (Specification.itemProp[j].id == itemProp.id) {
                    return errorService.error("该规格属性已经存在！", null)
                }
            }
            Specification.selectItemPropChips.push(itemProp.name);
            // Specification.itemProp.push(itemProp);
            // console.log('itemProp',itemProp);
            // console.log('Specification',Specification);
        };

        //----------------------------------------------删除规格属性
        $scope.attrDel = function (Specification,itemProp,index) {
            // console.log('Specification',Specification);
            // console.log('itemProp',itemProp);
            // console.log('index',index);
            Specification.itemProp.splice(index,1);
            $scope.getSkus($scope.baseData.Specifications);
        };


        //------------------------------------------------------------------选择sku图片
        $scope.chooseSkuImg = function(file,sku){
            if (file && sku) {
                // file.upload = Upload.upload({
                //     url: conf.apiPath + "/uploadImg/uploadImg?imgType=UPLOAD&type=UPLOAD&X-Requested-With=XMLHttpRequest",
                //     data: {excelFile: file}
                // });
                // file.upload.then(function (data) {
                //     if (data.data.data.code == 'SUCCESS') {
                //         sku.img = data.data.data.file_path;
                //     } else {
                //         var msg = object.msg;
                //         $window.alert(msg);
                //     }
                // })
                Upload.upload({
                    url: conf.yunApiPath + '/app/5988791a6b869f4e18d5c8d5/org/598878fc6b869f4e0f19fb47/yunFile',
                    data: {
                        file: file,
                    }
                }).then(function (resp) {
                    $http({
                        method: 'GET',
                        url: conf.yunApiPath + '/app/5988791a6b869f4e18d5c8d5/org/598878fc6b869f4e0f19fb47/yunFile/' + resp.data.data
                    }).then(function (resp) {
                        sku.img = resp.data.data.cdnUrls[0].url;
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
        //--------------------------------------------------------------删除sku图片
        $scope.delSkuImg = function (sku) {
            sku.img = null;
        };


        //--------------------------------------------------------------------------------------生成获取SKU
        $scope.getSku = function () {
            // console.log('用于生成sku的Specifications===',$scope.baseData.Specifications);
            $scope.getSkus($scope.baseData.Specifications);
            // console.log('生成的sku==============',$scope.baseData.skus);
        };
        //sku 条目处理函数
        $scope.baseData.skus = [];   //用于生成sku表
        $scope.getSkus = function (specs) {
            // var specs = $scope.specs;
            var rows = 1;
            var cols = specs.length;
            specs.forEach(function (spec) {
                rows *= spec.itemProp.length;   //--------------------一共需要生成rows条sku
            });
            var skus = [];
            for (var i = 0; i < rows; i++) {     //第一个sku开始生成
                var sku = {
                    id: '',
                    specs: [],
                    leaguePrice: '',
                    agencyPrice: '',
                    img:'',
                    price: '',
                    storage: '',
                    status: 'false',
                };
                for (var j = 0; j < cols; j++) {              // -----地一个规格开始循环
                    var m = 1;
                    specs.forEach(function (spec, index) {
                        if (index <= j) {
                            return;
                        }
                        m *= spec.itemProp.length;
                    });
                    //if (i % m == 0) {
                    sku.specs.push({
                        name: specs[j].realName,
                        objId: specs[j].objId,
                        propId: specs[j].id,
                        //value: specs[j].values[Math.ceil(1.0 * i / m) % specs[j].values.length]
                        value: specs[j].itemProp[Math.floor(1.0 * i / m) % specs[j].itemProp.length]
                    });
                    //}
                }
                skus.push(sku);
            }
            updataSku($scope.startSkus,skus);
            $scope.baseData.skus = skus;
            // console.log('123456$scope.baseData.skus',$scope.baseData.skus);
        };
        $scope.freightToggle = function (num) {
            $scope.freight = !$scope.freight;
        };
        //------------------------------------------------------------------------------图片上传
        $scope.baseData.imgs = [];
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
                        // 上传代码返回结果之后，将图片插入到编辑器中
                        $scope.baseData.imgs.push(resp.data.data.cdnUrls[0].url);
                    }, function (resp) {
                        console.log('Error status: ' + resp.status);
                    });
                }, function (resp) {
                    //console.log('Error status: ' + resp.status);
                }, function (evt) {
                    // var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                    // console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
                });
                //
                // file.upload = Upload.upload({
                //     url: conf.apiPath + "/uploadImg/uploadImg?imgType=UPLOAD&type=UPLOAD&X-Requested-With=XMLHttpRequest",
                //     data: {excelFile: file}
                // });
                // file.upload.then(function (data) {
                //     if (data.data.data.code == 'SUCCESS') {
                //         $scope.baseData.imgs.push(data.data.data.file_path);
                //     } else {
                //         var msg = object.msg;
                //         $window.alert(msg);
                //     }
                // })
            }
        };
        /**
         * 删除指定的图片
         * @param index
         */
        $scope.cancelDel = function (index) {
            $scope.baseData.imgs.splice(index, 1);
        };
        //返回按钮
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.index", null, {reload: true});
            } else {
                history.back();
            }
        };


        $scope.goto =function (index) {
            $state.go(index, null, {reload: true});
        }


        //禁用sku
        $scope.disabledSku = function (sku) {
            if (sku.status == 'false') {
                sku.status = 'true';
            } else {
                sku.status = 'false';
            }
            // sku.status = !sku.status;
        };




        //=================================================================================================保存
        $scope.pageSave = function () {
            // console.log('标题=', $scope.baseData.title);
            // console.log('副标题=', $scope.baseData.desp);
            // console.log('标签=', $scope.baseData.chips);
            // console.log('标签2=', $scope.uploadCategory);
            // console.log('主图=', $scope.baseData.imgs);
            // console.log('规格=', $scope.baseData.Specifications);
            // console.log('sku=', $scope.baseData.skus);
            // console.log('副文本=', $scope.editor.txt.html());

            //-------------------------------------------------------------------------sku整理
            $scope.uploadSkus = [];
            for (var i = 0; i < $scope.baseData.skus.length; i++) {
                $scope.uploadSkus[i] = {
                    id: $scope.baseData.skus[i].id,
                    imgs:$scope.baseData.skus[i].img,
                    generalAgencyPrice: $scope.baseData.skus[i].generalAgencyPrice * 100,
                    regionalAgencyPrice: $scope.baseData.skus[i].regionalAgencyPrice * 100,
                    salePrice: $scope.baseData.skus[i].salePrice * 100,
                    labelPrice: $scope.baseData.skus[i].labelPrice * 100,
                    leaguePrice: $scope.baseData.skus[i].leaguePrice * 100,
                    storage: $scope.baseData.skus[i].storage,
                    status: $scope.baseData.skus[i].status,
                    code:$scope.baseData.skus[i].skuCode,
                    specList: []
                };
                for (var j = 0; j < $scope.baseData.skus[i].specs.length; j++) {
                    // $scope.valGroup = angular.copy($scope.uploadSku.specs);
                    $scope.uploadSkus[i].specList.push({
                        id: $scope.baseData.skus[i].specs[j].objId,
                        itemPropId: $scope.baseData.skus[i].specs[j].propId,
                        itemPropValueId: $scope.baseData.skus[i].specs[j].value.id
                    })
                }
            }


            //---------------------------------------根据$scope.specs 每个规格框整理数据用于上传
            $scope.specslist = [];
            for (var i = 0; i < $scope.baseData.Specifications.length; i++) {
                if ($scope.baseData.Specifications[i].itemProp.length > 0) {
                    var indexId = [];
                    for (var j = 0; j < $scope.baseData.Specifications[i].itemProp.length; j++) {
                        indexId.push($scope.baseData.Specifications[i].itemProp[j].id);
                    }
                    $scope.specslist.push({
                        "id": $scope.baseData.Specifications[i].objId,
                        "itemPropId": $scope.baseData.Specifications[i].id,
                        "itemPropValueIds": indexId
                    })
                }
            }


            //发送请求前非空验证
            if (!$scope.baseData.title) {
                return errorService.error("标题不能为空！", null)
            }
            if ($scope.uploadCategory.length < 1) {
                return errorService.error("分类标签至少选择一项！", null)
            }
            if ($scope.baseData.imgs.length < 1) {
                return errorService.error("主图至少上传一张！", null)
            }
            if ($scope.baseData.Specifications.length < 1) {
                return errorService.error("商品规格至少选择一个！", null)
            }
            if ($scope.baseData.skus.length < 1) {
                return errorService.error("sku至少生成一个！", null)
            }
            if (!$scope.editor.txt.html()) {
                return errorService.error("商品详情不能为空！", null)
            }


            $http({
                method: 'PUT',
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/item/" + $stateParams.id ,
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
                data: {
                    'id': $stateParams.id,
                    //短标题
                    "title": $scope.baseData.title,
                    //描述
                    "desp": $scope.baseData.desp,
                    //富文本
                    "detail": $scope.editor.txt.html(),
                    //分类标签
                    "tags": $scope.uploadCategory,
                    //主图
                    "imgs": $scope.baseData.imgs,
                    //状态
                    "status": 'SALE_OFF',
                    //商品属性
                    "props": $scope.baseData.props,
                    //商品规格
                    "specs": $scope.specslist,
                    //sku
                    'skuList': $scope.uploadSkus,
                },
            }).success(function (data) {
                if (data.status == 200) {
                    $scope.fallbackPage();
                } else if (data.status == 10024) {
                    return errorService.error(data.data, null)
                }
            });
        };
    }
}
Controller.$inject = [
    '$scope', '$http', 'loginService', 'errorService', '$state', 'Upload', '$stateParams', '$window', 'alertService','$filter'
];
export default Controller ;
