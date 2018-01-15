import conf from "../../../../conf";
// import "jquery";
//var echarts = require('echarts');
// 引入 ECharts 主模块
var echarts = require('echarts/lib/echarts');
// 引入柱状图
require('echarts/lib/chart/bar');
//引入折线图
require('echarts/lib/chart/line');      //详情见官网教程  --在webpack中使用echarts;
var $scope,
    $http,
    $state,
    $log,
    loginService,
    $mdDialog,
    $stateParams,
    $location,
    $filter,
    $q;
class Controller {
    constructor(_$scope,
                _$http,
                _$state,
                _$log,
                _loginService,
                _$mdDialog,
                _$stateParams,
                _$location,
                _$filter,
                _$q) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        loginService = _loginService;
        $log = _$log;
        $q = _$q;
        $location = _$location;
        $filter=_$filter;
        $stateParams = _$stateParams;
        $scope.brandAppId = $stateParams.brandAppId;
        /////////////////////////////////

        loginService.loginCtl(true, $location.absUrl());
        $scope.brandAppId = $stateParams.brandAppId;
        // https://kingsilk.net/agency/rs/local/16700/api/brandApp/59782691f8fdbc1f9b2c4323/partner/111111/sale

        $scope.oneData = {
            day:[],
            bar:[],
            line:[]
        };
        $scope.allRecord=false;
        //初始化图表标签

        var options = {
            //定义一个标题
            // title:{
            //     text:'xxxxx'
            // },
            // legend:{
            //     data:['xxxxx']
            // },
            //X轴设置
            xAxis: {
                data: $scope.oneData.day,
                //splitLine:{show: false},//去除网格线
                show: true,   //去除坐标为false
                //设置x轴坐标颜色
                axisLine: {
                    lineStyle: {
                        color: '#45c08a',
                    }
                },
                //设置x轴文本颜色
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: 'rgba(255,255,255,0.7)'                    //x轴文本颜色
                    }
                }
            },
            yAxis: {
                splitLine: {show: false},//去除网格线
                show: false,             //去除坐标
                //  type: 'value',
                //  min: 0,
                //  max: 600
            },
            //name=legend.data的时候才能显示图例
            //设置容器上下左右间隔
            grid: {
                // left: '50',
                // right: '10',
                //  bottom: '20',              //left,right,bottom,为容器距离外侧包含块的距离
                // containLabel: true
                x: 0,
                y: 20,
                x2: 0,
                y2: 30              //x,y代表纵坐标的左上角距离容器的大小     ---x2,y2代表横坐标的右下角距离容器的大小
            },
            series: [
                {
                    name: '销量',
                    type: 'bar',
                    barWidth: 24,
                    data:  $scope.oneData.bar,
                   // data:[10,20,30,40,20,60,50],
                    itemStyle: {
                        normal: {
                            // color:'#fff'     //柱状图颜色tongyi
                            color: function (params) {     //单独设置没一个柱状图颜色
                                var colorList = ['rgb(255,255,255)', 'rgb(255,255,255)', 'rgb(255,255,255)',
                                    'rgb(255,255,255)', 'rgb(255,255,255)', 'rgb(255,255,255)',
                                    '#e1c8a4',];
                                return colorList[params.dataIndex];
                            }
                        }
                    },
                    barGap: '20'          //柱间距    目前不好使
                },
                //折线数据
                {
                    name: '交易量',
                    type: 'line',
                    symbol: 'square',   //将折线原点改为方块
                    symbolSize: 6,     //拐点大小
                    itemStyle: {
                        normal: {
                            color: function (params) {     //单独设置每一个折点颜色
                                var colorList = ['rgb(255,255,255)', 'rgb(255,255,255)', 'rgb(255,255,255)', 'rgb(255,255,255)', 'rgb(255,255,255)', 'rgb(255,255,255)', '#e1c8a4'];
                                return colorList[params.dataIndex];
                            },       //折点颜色
                            lineStyle: {
                                color: 'rgba(255,255,255,0.7)',    //折线颜色
                            }
                        }
                    },
                    data:  $scope.oneData.line
                    //data:[40,50,60,70,50,90,80]
                },
            ]

        };
        $scope.date={};
        $scope.getRrcord = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/partner/1111111111111/sale",
                params: {},
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                    // 成功
                    console.log('121111resp', resp.data.data);
                    $scope.item = resp.data.data;
                    $scope.data = resp.data.data.saleRecordeList;
                    if($scope.data[$scope.data.length-1].day==1){
                        $scope.date.day='上月';
                    }else{
                        $scope.date.day='本月';
                    };
                    for(let i=0;i<$scope.data.length;i++){
                        $scope.oneData.day.push($scope.data[i].day);
                        $scope.oneData.bar.push($scope.data[i].sale/100);
                        $scope.oneData.line.push($scope.data[i].sale/70);

                    };
                    $scope.oneData.day.splice($scope.oneData.day.length-1,1,'今日');
                    console.log( $scope.oneData.bar,$scope.oneData.line);
                    if($scope.item.num==0){
                        $scope.allRecord=true;

                    }else{
                        $scope.allRecord=false;
                        var myChart = echarts.init(document.getElementById('recordBar'));
                        myChart.setOption(options);
                    }
                }, function () {
                    //error
                }
            );
        };
        $scope.getRrcord();
    }
}

Controller.$inject = [
    '$scope',
    '$http',
    '$state',
    '$log',
    'loginService',
    '$mdDialog',
    '$stateParams',
    '$location',
    '$filter',
    '$q',
];

export default Controller ;
