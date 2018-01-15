
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
        //数组处理方法函数
        $scope.mapById = function (arr, itemPropName) {
            arr.forEach(function (item) {
                arr[item[itemPropName]] = item
            })
        };

        // -----------------------------------------------------------------------------------------------------wangEditor 副文本初始化
        $scope.editor = new E('#div4');
        $scope.editor.customConfig.customUploadImg = function (files, insert) {
            // files 是 input 中选中的文件列表
            // insert 是获取图片 url 后，插入到编辑器的方法
            files.forEach(function (file) {
                Upload.upload({
                    url: conf.yunApiPath + '/app/988791a6b869f4e18d5c8d5/org/598878fc6b869f4e0f19fb47/yunFile',
                    data: {
                        file: file,
                    }
                }).then(function (resp) {
                    $http({
                        method: 'GET',
                        url: conf.yunApiPath + '/app/5988791a6b869f4e18d5c8d5/org/598878fc6b869f4e0f19fb47/yunFile/' + resp.data.data
                    }).then(function (resp) {
                        console.log('Success ' + resp.data.data.cdnUrls[0].url);
                        insert(resp.data.data.cdnUrls[0].url);
                    }, function (resp) {
                        console.log('Error status: ' + resp.status);
                    });
                }, function (resp) {
                    console.log('Error status: ' + resp.status);
                }, function (evt) {
                    var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                    console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
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
                    $scope.categoryInfo();
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
                        var specses = []
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
            });
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

        // //编辑状态查找已经存在的sku
        // function updataSku(startSkus, skus) {
        //     // console.log('startSkus=======',startSkus);
        //     // console.log('skus=======',skus);
        //     var flog = true;
        //     for (var i = 0; i < skus.length; i++) {
        //         for (var j = 0; j < startSkus.length; j++) {
        //             for (var m = 0; m < startSkus[j].specs.length; m++) {
        //                 // console.log('start',startSkus[j].specs[m].value.id )
        //                 // console.log('skus',skus[i].specs[m].id )
        //                 if (startSkus[j].specs[m].value.id !== skus[i].specs[m].value.id) {
        //                     flog = false;
        //                     // console.log('00000000000');
        //                     break;
        //                 } else {
        //                     flog = true;
        //                 }
        //                 // console.log('1111111');
        //             }
        //             if (flog == true) {
        //                 // console.log('startSkusjjjjjjjjjj=======',startSkus[j]);
        //                 // console.log('skusiiiiiiiiiii=======',skus[i]);
        //                 skus[i] = startSkus[j];
        //             }
        //             // console.log('222222222');
        //         }
        //         // console.log('333333333');
        //     }
        //     // console.log('44444444');
        // }
    }
}
Controller.$inject = [
    '$scope', '$http', 'loginService', 'errorService', '$state', 'Upload', '$stateParams', '$window', 'alertService','$filter'
];
export default Controller ;
