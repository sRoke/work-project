import conf from "../../../../../conf";
var $scope,
    $state,
    $stateParams,
    loginService,
    $http,
    $location;

class Controller {
    constructor(_$scope, _$state, _$stateParams, _loginService,_$http, _$location) {
        $scope = _$scope;
        $state = _$state;
        $stateParams = _$stateParams;
        loginService = _loginService;
        $http=_$http;
        $location = _$location;
        $scope.brandAppId=$stateParams.brandAppId;
        loginService.loginCtl(true, $location.absUrl());
        $scope.go = function (state) {
            $state.go(state);
        };

        $scope.detailStatus = {};
        $scope.now = new Date();
//暂时去掉    切换类型
//         $scope.activeDetail=true;
//         $scope.getDetail=function(){
//             $scope.activeDetail=!$scope.activeDetail;
//         };
       // $scope.getList();
        $scope.detailStatus.title = "账户明细";
        // $scope.tab = (tabs) => {
        //     $scope.activeDetail=!$scope.activeDetail;
        //     $scope.tabs = tabs;
        //     switch ($scope.tabs) {
        //         case 'caigou':
        //             $scope.detailStatus.title = "采购明细";
        //             break;
        //         case 'zhanghu':
        //             $scope.detailStatus.title = "账户明细";
        //             break;
        //         case 'xiaoshou':
        //             $scope.detailStatus.title = "销售明细";
        //             break;
        //         case 'tixian':
        //             $scope.detailStatus.title = "提现明细";
        //             break;
        //         case 'shouyin':
        //             $scope.detailStatus.title = "收银明细";
        //             break;
        //         default:
        //             $scope.detailStatus.title = "账户明细";
        //     };
        // };
        //明细


        $scope.getTitle = function (item) {
            var linkUrl='';
            if(item.type!='RETAIL_ORDER'&& item.type!='RETAIL_REFUND'){
                linkUrl=conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/partnerAccount/log";
            }else{
                linkUrl=conf.shopPath+ "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/order/log";
            }
            $http({
                method: "GET",
                url:linkUrl,
                params: {
                    ids:item.id
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    'brandApp-Id': $stateParams.brandAppId,
                }
            }).then(function (resp) {
                   // console.log('qqqqqqqqqqqqq',resp.data);
                    item.title = resp.data.data[0].title;
                   // console.log(1111111111,item);
                }, function (resp) {
                    //error
                });
        };
        $scope.number=0;
        $scope.Lists=[];
        $scope.getAccount=function(number){
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/partnerAccount/logIds",
                params: {
                    logType:"",
                    size:10,
                    page:number?number:$scope.number
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    'brandApp-Id': $stateParams.brandAppId,
                }
            }).then(function (resp) {
                $scope.Lists= $scope.Lists.concat(resp.data.data.content);
                console.log(11111111,$scope.Lists.length);
                $scope.data=resp.data.data;
                $scope.number=resp.data.data.number+1;
                    //console.log(resp);
                    // var allMaps=$scope.Lists;  //存储所有信息
                    // var dest1=[];      //存储非收银id
                    // var dest2=[];      //存储收银的id
                    // var titles=[];     //存储非收银title
                    // var map={};
                   // console.log('qqqqqqqqqqqqq',resp.data.data);
                   //  for(var i=0;i<$scope.items.length;i++){
                   //      $scope.getTitle($scope.items[i]);
                   //  };
                   // console.log(2222222222222222,$scope.items);




                   // console.log('=================',$scope.Lists);
                    //agency拿对应的title-----非收银;
                    // $http({
                    //     method: "GET",
                    //     url:conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/partnerAccount/log",
                    //     params: {
                    //         ids:dest1
                    //     },
                    //     headers: {
                    //         'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    //         'brandApp-Id': $stateParams.brandAppId,
                    //     }
                    // }).then(function (resp) {
                    //         console.log('success---agency拿对应的title',resp);
                    //         //根据id拿对应的title,整理
                    //         for(var i=0;i<resp.data.data.length;i++){
                    //             var tit=resp.data.data[i];
                    //             titles.push({
                    //                 id:resp.data.data[i].id,
                    //                 title:resp.data.data[i].tilte
                    //             });
                    //             map[i]=tit;
                    //         };
                    //         console.log(dest1,dest2);
                    //         console.log(allMaps,allMaps.length);
                    //         console.log(map,map.length);
                    //        //   根据id去匹配
                    //         for(var i=0;i<allMaps.length;i++){
                    //             console.log('1--id',allMaps[i].id);
                    //             for(var j=0;j<map.length;j++){
                    //                 console.log('2--id',map[j].id);
                    //                 if(allMaps[i].id==map[j].id){
                    //                     console.log('id重合');
                    //                     allMaps[i].push({
                    //                         title:map[j].tilte
                    //                     })
                    //                 }
                    //             }
                    //         };
                    //         console.log('最终的信息集合',allMaps);
                    //
                    //
                    //
                    //
                    //
                    //
                    //
                    //
                    //
                    //     }, function (resp) {
                    //         //error
                    //     }
                    // );


                }, function (resp) {
                    //error
                    console.log('error',resp);
                    //alertService.msgAlert("exclamation-circle", "验证码错误");
                }
            );
        };
        $scope.getAccount();
        /*返回上级*/
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.center.main", null, {reload: true});
            } else {
                history.back();
            }
        };
    }
}

Controller.$inject = [
    '$scope',
    '$state',
    '$stateParams',
    'loginService',
    '$http',
    '$location',
];

export default Controller ;
