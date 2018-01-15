/**
 * Created by susf on 17-3-29.
 */
import conf from "../../../../conf";
import BSON from "bson";
import "bootstrap/dist/css/bootstrap.css";
import "font-awesome/css/font-awesome.css";
import "textangular/dist/textAngular.css";
import E from "wangeditor";


var $scope,
    $http,
    loginService,
    errorService,
    $state,
    Upload,
    $window,
    $filter,
    $stateParams,
    alertService;
class Controller {
    constructor(_$scope, _$http, _loginService, _errorService, _$state, _Upload, _$stateParams, _$window, _$filter, _alertService) {
        // $scope.authorityListTure = true;
        $scope = _$scope;
        loginService = _loginService;
        errorService = _errorService;
        alertService = _alertService;
        $http = _$http;
        Upload = _Upload;
        $window = _$window;
        $filter = _$filter;
        $state = _$state;
        $stateParams = _$stateParams;

        loginService.loginCtl(true);
        $scope.brandAppId = $stateParams.brandAppId;
        $scope.about = $stateParams.about;
        $scope.startSkus = [];
        $scope.authorityListShowValue = false; //商品分类默认收起
        $scope.authorityListStatus = '展示标签';
        //默认状态
        $scope.statu = "EDITING";
        //初始化可编辑状态,为查看页禁用准备
        $scope.views = {
            disabledTitle: false,
            disabledDesp: false,
            staffGroup: false,
            chooseImg: false,
            deleteImg: false,
            status: false,
            itemProp: false,
            itemSpec: false,
            htmlcontent: false,
            save: false
        };
        //一些数据声明
        $scope.baseData = {
            "title": "",
            "desp": "",
            "cpmc": [],
            "imgs": [],
            "status": [{status: "EDITING", value: '编辑中'},
                {status: "NORMAL", value: '正常'},
                {status: "SALE_OFF", value: '已下架'}],
            "specs": [{}],
        };
        $scope.itemPropKeyword = {};
        $scope.specsKeyword = {};
        //分类标签获取
        $scope.authorityList = [];//存储每个分类标签,分父子级 用于页面展示
        $scope.staffGroup = [];//存储每个分类标签,无父子级 存储每个id和状态
        $scope.categoryInfo = function () {
            $http({
                method: "get",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "`/category",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
            }).success(function (data) {
                //console.log(data);
                for (var i = 0; i < data.data.length; i++) {
                    $scope.staffGroup.push({id: data.data[i].id, status: false});
                    if (data.data[i].parentId == null) {
                        $scope.authorityList.push(data.data[i]);
                    } else {
                        for (var j = 0; j < data.data.length; j++) {
                            if (data.data[i].parentId == data.data[j].id) {
                                if (data.data[j].child) {
                                    data.data[j].child.push(data.data[i]);
                                } else {
                                    data.data[j].child = [];
                                    data.data[j].child.push(data.data[i]);
                                }
                            }
                        }
                    }
                }
            });
        };
        $scope.categoryInfo();//页面加载获取所以分类标签
        //编辑
        if ($stateParams.id) {//判断不是新增页
            //编辑页禁用

            if ($stateParams.about == 'editor') {
                $scope.authorityListShowValue = true;
                $scope.authorityListStatus = '收起标签';
                $scope.editor = {
                    itemSpec: true,
                }
            }

            //查看页禁止编辑
            if ($stateParams.about == 'view') {
                $scope.authorityListShowValue = true;
                $scope.authorityListStatus = '收起标签';
                $scope.views = {
                    disabledTitle: true,
                    disabledDesp: true,
                    staffGroup: true,
                    chooseImg: true,
                    deleteImg: true,
                    status: true,
                    itemProp: true,
                    htmlcontent: true,
                    itemSpec: true,
                    save: true,
                }
            }
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

                    $scope.baseData.title = data.data.title;   //短标题
                    $scope.baseData.desp = data.data.desp;     //描述
                    $scope.editor.txt.html(data.data.detail);  //富文本
                    // $scope.htmlcontent = data.data.detail;     //富文本
                    $scope.statu = data.data.status;           //状态
                    $scope.baseData.imgs = data.data.imgs;     //主图
                    //分类
                    $scope.uploadCategory = angular.copy(data.data.tags);
                    $scope.mapById($scope.staffGroup, 'id');
                    for (var i = 0; i < $scope.uploadCategory.length; i++) {

                        $scope.staffGroup[$scope.uploadCategory[i]].status = true;
                    }
                    //属性
                    // $scope.baseData.cpmc = [];
                    // $scope.baseData.props = [];
                    for (var j = 0; j < data.data.props.length; j++) {
                        $scope.baseData.cpmc[j] = {
                            objId: data.data.props[j].id,
                            id: data.data.props[j].itemProp.id,
                            realName: data.data.props[j].itemProp.name,
                            //选中的数据reclist 用于界面显示选中的值
                            content: [{value: data.data.props[j].itemPropValue.id}],
                            itemPropListItem: data.data.props[j].itemPropValueList,
                        };
                        $scope.baseData.props[j] = {
                            id: data.data.props[j].id,
                            itemPropId: data.data.props[j].itemProp.id,
                            itemPropValueId: data.data.props[j].itemPropValue.id,
                        };
                    }

                    //规格
                    for (var k = 0; k < data.data.specs.length; k++) {
                        for (var s = 0; s < data.data.specs[k].itemPropValueList.length; s++) {
                            for (var m = 0; m < data.data.specs[k].itemPropValue.length; m++) {
                                if (data.data.specs[k].itemPropValueList[s].id == data.data.specs[k].itemPropValue[m].id) {
                                    data.data.specs[k].itemPropValueList[s].isCheck = true;
                                    data.data.specs[k].itemPropValueList[s].disabled = true;

                                }
                            }
                        }
                        $scope.selectedSpecs[k] = {
                            id: data.data.specs[k].itemProp.id,
                            name: data.data.specs[k].itemProp.name,
                            specValues: data.data.specs[k].itemPropValueList
                        };
                        // $scope.selectedSpecsSkus[k] = {
                        //     id: data.data.specs[k].itemProp.id,
                        //     name: data.data.specs[k].itemProp.name,
                        //     specValues: data.data.specs[k].itemPropValue,
                        // };
                        $scope.specs[k] = {
                            objId: data.data.specs[k].id,
                            propId: data.data.specs[k].itemProp.id,
                            name: data.data.specs[k].itemProp.name,
                            values: data.data.specs[k].itemPropValue,
                            // values: data.data.specs[k].itemPropValueList
                        };
                    }
                    // $scope.generateSkus();
                    //sku
                    for (var n = 0; n < data.data.skuList.length; n++) {
                        var specses = []
                        for (var y = 0; y < $scope.selectedSpecs.length; y++) {
                            for (var x = 0; x < data.data.skuList[n].specList.length; x++) {
                                if ($scope.selectedSpecs[y].id == data.data.skuList[n].specList[x].itemProp.id) {
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
                        $scope.skus.push({
                            id: data.data.skuList[n].id,
                            generalAgencyPrice: $filter('number')(data.data.skuList[n].generalAgencyPrice / 100, 2).replace(/,/g, ""),
                            regionalAgencyPrice: $filter('number')(data.data.skuList[n].regionalAgencyPrice / 100, 2).replace(/,/g, ""),
                            leaguePrice: $filter('number')(data.data.skuList[n].leaguePrice / 100, 2).replace(/,/g, ""),
                            salePrice: $filter('number')(data.data.skuList[n].salePrice / 100, 2).replace(/,/g, ""),
                            labelPrice: $filter('number')(data.data.skuList[n].labelPrice / 100, 2).replace(/,/g, ""),
                            code:data.data.skuList[n].code,
                            storage: data.data.skuList[n].storage,
                            status: data.data.skuList[n].status,
                            specs: specses
                        })
                    }
                    $scope.startSkus = $scope.skus;
                    // console.log(' $scope.skus==========', $scope.skus);
                }
            )
        }


        //新增

        //控制分类标签子目录i战士隐藏
        $scope.toggle = function (name) {
            name.collapsed = !name.collapsed;
        };
        //分类选中
        $scope.uploadCategory = [];//存储被选中的分类标签 用来上传
        $scope.choose = function (scope) {
            if ($scope.uploadCategory.indexOf(scope.node.id) == -1) {
                $scope.uploadCategory.push(scope.node.id);
            } else {
                $scope.uploadCategory.splice($scope.uploadCategory.indexOf(scope.node.id), 1);
            }

        };
        //商品属性模糊搜索
        // $scope.propListResult = [] 属性搜索框下拉框数据 用于展示
        $scope.comAttr = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/itemProp/itemPropList",
                params: {
                    itemPropKeyword: $scope.itemPropKeyword.key,
                    page: 0,
                    size: conf.pageSize
                    //根据输入框模糊搜索
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
            }).success(function (data) {
                //console.log('111111', data);
                $scope.propListResult = data.data;
            });
        };
        // 商品分类标签收起展示
        $scope.authorityListShow = function () {
            $scope.authorityListShowValue = !$scope.authorityListShowValue;
            if ($scope.authorityListShowValue) {
                $scope.authorityListStatus = '收起标签';
            } else {
                $scope.authorityListStatus = '展示标签';
            }
        };
        // 商品属性选中获取  选择属性搜索下拉框数据绑定
        $scope.selectStaff = function (staff) {
            $scope.itemPropKeyword.key = staff.name;
            $scope.itemPropKeyID = staff.id;
        };
        //商品属性添加 点击+号触发
        // $scope.temPropList = [];
        $scope.addproplist = function () {
            if (!$scope.itemPropKeyID) { //判断输入框是否选择
                return;
            }
            var isSame = false;   //标记
            $scope.baseData.cpmc = $scope.baseData.cpmc ? $scope.baseData.cpmc : []; //用来存储已添加属性 用来页面展示
            for (var i = 0; i < $scope.baseData.cpmc.length; i++) {
                if ($scope.baseData.cpmc[i].id == $scope.itemPropKeyID) { //判断属性是否已经存在
                    isSame = true;
                    $scope.itemPropKeyID = "";
                    $scope.itemPropKeyword.key = null;
                    break;
                }
            }
            //没有相同的
            if (!isSame) { //属性不存在 根据id获取
                //console.log($scope.brandAppId);
                $http({
                    method: "get",
                    url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/itemProp/itemPropListItem",
                    params: {
                        itemPropKeyword: $scope.itemPropKeyID,
                        page: 0,
                        size: 200
                    },
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    },
                }).success(function (data) {
                    // console.log(data.data.content);
                    // $scope.itemPropListItem = data.data;
                    $scope.baseData.cpmc.push({    //$scope.baseData.cpmc 存储选中属性
                        objId: new BSON.ObjectID().toString(),  //前端生成objId
                        id: $scope.itemPropKeyID,               //该属性id
                        realName: $scope.itemPropKeyword.key,       //该属性名
                        itemPropListItem: data.data.content             //该属性可选值
                    });
                    $scope.itemPropKeyID = "";
                    $scope.itemPropKeyword.key = null;
                });
            }
        };
        //商品属性详细下拉框选择
        $scope.baseData.props = [];//存储属性可选值中选中的值,用来保存上传
        $scope.listPropChange = function (index, list, value) {
            var isSame = false;
            for (var i = 0; i < $scope.baseData.props.length; i++) {
                if ($scope.baseData.props[i].id === list.objId) { //判断是否已经存储过该属性
                    $scope.baseData.props[i].itemPropValueId = value;//存储过改变选中的值
                    isSame = true;
                }
            }
            if (isSame === false) {              //没存储过新push进去
                $scope.baseData.props.push({
                    "id": list.objId,         //对应$scope.baseData.cpmc的objId
                    "itemPropId": list.id,    //该属性的id
                    "itemPropValueId": value  //该属性中选中的可选值 的id
                });
            }
        };
        //删除商品属性
        $scope.removeProp = function (id) {
            for (var i = 0; i < $scope.baseData.cpmc.length; i++) {    //删除该熟悉的存储
                if ($scope.baseData.cpmc[i].id == id) {
                    $scope.baseData.cpmc.splice($scope.baseData.cpmc.indexOf(i), 1);
                }
            }
            for (var j = 0; j < $scope.baseData.props.length; j++) {  //删除该属性选中的相关值
                if ($scope.baseData.props[j].itemPropId == id) {
                    $scope.baseData.props.splice($scope.baseData.props.indexOf(j), 1);
                }
            }
        };
        //商品规格
        //添加规格搜索 模糊匹配
        // $scope.specsListResult =[] 存储模糊搜索结果作为下拉框展示数据
        $scope.selectedSpecsAttr = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/itemProp/itemPropList",
                params: {
                    itemPropKeyword: $scope.specsKeyword.key,
                    page: 0,
                    size: conf.pageSize
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
            }).success(function (data) {
                //.log(data);
                $scope.specsListResult = data.data;
            });
        };
        //添加规格选中获取  选中下拉框数据绑定
        $scope.selectSpecsLIst = function (staff) {
            $scope.specsKeyword.key = staff.name;
            $scope.specsKeyID = staff.id;
        };
        //添加规格 点击+号后触发
        $scope.specs = [];                   //用于生产sku对应数据 表示每个规格框
        // $scope.selectedSpecsSkus = [];
        $scope.selectedSpecs = [];          // 保存添加的规格id,name,可选值; 用于规格表生成
        $scope.addPropSpecsList = function () {
            if (!$scope.specsKeyID) {
                return;
            }
            var isSame = false;
            //已经存储过的不再重复存储
            for (var i = 0; i < $scope.selectedSpecs.length; i++) {
                if ($scope.selectedSpecs[i].id == $scope.specsKeyID) {
                    isSame = true;
                    $scope.specsKeyID = "";
                    $scope.specsKeyword.key = null;
                    break;
                }
            }
            //没有相同的
            if (!isSame) {                                      //没存储过的请求该规格的可选值
                $http({
                    method: "get",
                    url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/itemProp/itemPropListItem",
                    params: {
                        page: 0,
                        size: 200,
                        itemPropKeyword: $scope.specsKeyID
                    },
                    headers: {
                        'Authorization': 'Bearer ' + loginService.getAccessToken(),
                        "brandApp-Id": $scope.brandAppId
                    },
                }).success(function (data) {
                    $scope.selectedSpecs.push({
                        id: $scope.specsKeyID,        //该规格的id
                        name: $scope.specsKeyword.key,    //该规格名称
                        specValues: data.data.content //该规格可选值
                    });
                    // $scope.selectedSpecsSkus.push({             //===============================================??????
                    //     id: $scope.specsKeyID,
                    //     name: $scope.specsKeyword,
                    //     specValues: [],
                    // });
                    $scope.specs.push({
                        objId: new BSON.ObjectID().toString(),  //该规格框的objId
                        propId: $scope.specsKeyID,              //该规格框的规格Id
                        name: $scope.specsKeyword.key,              //该规格框的规格name
                        values: []                              //该规格框的规格选中的Id多选为数组
                    });
                    $scope.specsKeyID = "";
                    $scope.specsKeyword.key = null;
                });
            }
        };
        //sku 条目处理函数
        $scope.skus = [];   //用于生成sku表
        $scope.generateSkus = function () {
            //console.log('$scope.startSkus', $scope.startSkus);
            var specs = $scope.specs;
            var rows = 1;
            var cols = specs.length;
            specs.forEach(function (spec) {
                rows *= spec.values.length;
            });
            // console.info(rows);
            var skus = [];
            for (var i = 0; i < rows; i++) {

                var sku = {
                    id: '',
                    specs: [],
                    generalAgencyPrice: '',
                    regionalAgencyPrice: '',
                    leaguePrice: '',
                    salePrice: '',
                    labelPrice: '',
                    code:'',
                    storage: '',
                    status: 'false',
                };
                for (var j = 0; j < cols; j++) {

                    var m = 1;
                    specs.forEach(function (spec, index) {
                        if (index <= j) {
                            return;
                        }
                        m *= spec.values.length;
                    });

                    //if (i % m == 0) {
                    sku.specs.push({
                        name: specs[j].name,
                        objId: specs[j].objId,
                        propId: specs[j].propId,
                        //value: specs[j].values[Math.ceil(1.0 * i / m) % specs[j].values.length]
                        value: specs[j].values[Math.floor(1.0 * i / m) % specs[j].values.length]
                    });
                    //}
                }
                skus.push(sku);
            }
            updataSku($scope.startSkus, skus);
            $scope.skus = skus;

        };


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


        //数组处理方法函数
        $scope.mapById = function (arr, itemPropName) {
            arr.forEach(function (item) {
                arr[item[itemPropName]] = item
            })
        };
        //规格删除
        $scope.removeSpecsList = function (id) {
            for (var i = 0; i < $scope.selectedSpecs.length; i++) {
                if ($scope.selectedSpecs[i].id == id) {
                    $scope.selectedSpecs.splice(i, 1);
                }
            }
            // for (var j = 0; j < $scope.selectedSpecsSkus.length; j++) {
            //     if ($scope.selectedSpecsSkus[j].id == id) {
            //         $scope.selectedSpecsSkus.splice(j, 1);
            //     }
            // }
            for (var k = 0; k < $scope.specs.length; k++) {
                if ($scope.specs[k].propId == id) {
                    $scope.specs.splice(k, 1);
                    $scope.generateSkus(); //更新sku
                }
            }
        };
        //规格规格选择多选框
        $scope.checkedSpecValue = function (index, spec, specValue) {
            //更改$scope.specs对应规格的values值中的选中值
            for (var i = 0; i < $scope.specs.length; i++) {
                if ($scope.specs[i].propId == spec.id) {
                    if (specValue.isCheck == true) {
                        $scope.specs[i].values.push({id: specValue.id, name: specValue.name});
                        // console.log($scope.specs[i].values);
                    } else {
                        for (var j = 0; j < $scope.specs[i].values.length; j++) {
                            if ($scope.specs[i].values[j].id == specValue.id) {
                                $scope.specs[i].values.splice(j, 1)
                            }
                        }
                    }
                }
            }
            $scope.generateSkus(); //更新sku
        };
        // 富文本
        // $scope.orightml = '<p><p>'; // 初始化
        // $scope.htmlcontent = $scope.orightml; // 内容绑定
        // -----------------------------------------------------------------------------------------------------wangEditor
        $scope.editor = new E('#wangEditor');
        // $scope.editor.config.menuFixed = 50;
        //自定义图片上传
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
                    //console.log('Success ' + resp.config.data.file.name + 'uploaded. Response: ', resp.data);

                    $http({
                        method: 'GET',
                        url: conf.yunApiPath + '/app/5988791a6b869f4e18d5c8d5/org/598878fc6b869f4e0f19fb47/yunFile/' + resp.data.data
                    }).then(function (resp) {
                        //console.log('Success ' + resp.data.data.cdnUrls[0].url);
                        // 上传代码返回结果之后，将图片插入到编辑器中
                        insert(resp.data.data.cdnUrls[0].url);
                    }, function (resp) {
                        // console.log('Error status: ' + resp.status);
                    });
                }, function (resp) {
                    //console.log('Error status: ' + resp.status);
                }, function (evt) {
                    var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                    // console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
                });
            });
        };
        $scope.editor.create();
        // $scope.editor.txt.html('231231231231');
        // 保存
        $scope.save = function () {
            //整理数据根据接口要求整理sku数据生成 $scope.uploadSkus 便于上传
            $scope.uploadSkus = [];
            for (var i = 0; i < $scope.skus.length; i++) {
                $scope.uploadSkus[i] = {
                    id: $scope.skus[i].id,
                    generalAgencyPrice: $scope.skus[i].generalAgencyPrice * 100,
                    regionalAgencyPrice: $scope.skus[i].regionalAgencyPrice * 100,
                    salePrice: $scope.skus[i].salePrice * 100,
                    labelPrice: $scope.skus[i].labelPrice * 100,
                    leaguePrice: $scope.skus[i].leaguePrice * 100,
                    storage: $scope.skus[i].storage,
                    status: $scope.skus[i].status,
                    code:$scope.skus[i].code,
                    specList: []
                };
                for (var j = 0; j < $scope.skus[i].specs.length; j++) {
                    // $scope.valGroup = angular.copy($scope.uploadSku.specs);
                    $scope.uploadSkus[i].specList.push({
                        id: $scope.skus[i].specs[j].objId,
                        itemPropId: $scope.skus[i].specs[j].propId,
                        itemPropValueId: $scope.skus[i].specs[j].value.id
                    })
                }
                // $scope.uploadSkus.push($scope.uploadSku)
            }
            //根据$scope.specs 每个规格框整理数据用于上传
            $scope.specslist = [];
            for (var i = 0; i < $scope.specs.length; i++) {
                if ($scope.specs[i].values.length > 0) {
                    var indexId = [];
                    for (var j = 0; j < $scope.specs[i].values.length; j++) {
                        indexId.push($scope.specs[i].values[j].id);
                    }
                    $scope.specslist.push({
                        "id": $scope.specs[i].objId,
                        "itemPropId": $scope.specs[i].propId,
                        "itemPropValueIds": indexId
                    })
                }
            }
            if ($stateParams.about == 'copy') {
                $stateParams.id = '';
                // console.log('      $scope.uploadSkus =', $scope.uploadSkus)
                // console.log('$scope.skus', $scope.skus);
            }
//发送请求前非空验证
            if (!$scope.baseData.title) {
                return errorService.error("短标题不能为空！", null)
            }
            if (!$scope.baseData.desp) {
                return errorService.error("描述不能为空！", null)
            }
            if ($scope.uploadCategory.length < 1) {
                return errorService.error("分类标签至少选择一项！", null)
            }
            if ($scope.baseData.imgs.length < 1) {
                return errorService.error("主图至少上传一张！", null)
            }
            // if ( $scope.statu == "EDITING") {
            //     return errorService.error("状态！", null)
            // }
            if ($scope.baseData.props.length < 1) {
                return errorService.error("商品属性至少添加一个！", null)
            }
            if ($scope.specslist.length < 1) {
                return errorService.error("商品规格至少选择一个！", null)
            }

            let methodType = null;
            if ($stateParams.id) {
                methodType = "PUT";     //编辑
            } else {
                methodType = "POST";       //新增
            }
            $http({
                method: methodType,
                url: $stateParams.id ? conf.apiPath + "/brandApp/" + $scope.brandAppId + "/item/" + $stateParams.id : conf.apiPath + "/brandApp/" + $scope.brandAppId + "/item",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
                data: {
                    'id': methodType ? null : $stateParams.id,
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
                    "status": $scope.statu,
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


        //yun图片上传
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
         * 移动到图片上时，显示操作按钮
         * @param index
         */
        $scope.showOperate = function (index) {
            if (!$scope.views.deleteImg) {
                document.getElementById("img" + index).getElementsByTagName("div")[0].style.height = '30px';
            }
        };
        /**
         * 图片上移走时，隐藏操作按钮
         * @param index
         */
        $scope.hideOperate = function (index) {
            if (!$scope.views.deleteImg) {
                document.getElementById("img" + index).getElementsByTagName("div")[0].style.height = '0';
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
            //console.log($scope.editor.txt.html());
            if (history.length === 1) {
                $state.go("main.brandApp.index", null, {reload: true});
            } else {
                history.back();
            }
        };

        //禁用sku
        $scope.disabledSku = function (sku) {
            if (sku.status == 'false') {
                sku.status = 'true';
            } else {
                sku.status = 'false';
            }
            // sku.status = !sku.status;
        };
    }
}
Controller.$inject = [
    '$scope', '$http', 'loginService', 'errorService', '$state', 'Upload', '$stateParams', '$window', '$filter', 'alertService'
];
export default Controller ;
