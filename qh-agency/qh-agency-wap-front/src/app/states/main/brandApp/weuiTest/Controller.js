import weui from 'weui.js';

var $scope,
    $rootScope,
    $http,
    addressService,
    $state,
    $log,
    $mdBottomSheet,
    loginService,
    $mdDialog,
    $timeout,
    $stateParams,
    numFilter,
    $location;
class Controller {
    constructor(_$scope,
                _$rootScope,
                _$http,
                _addressService,
                _$state,
                _$log,
                _$mdBottomSheet,
                _loginService,
                _$mdDialog,
                _$timeout,
                _$stateParams,
                _numFilter,
                _$location) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        $timeout = _$timeout;
        loginService = _loginService;
        $log = _$log;
        $stateParams = _$stateParams;
        $mdBottomSheet = _$mdBottomSheet;
        $location = _$location;
        addressService = _addressService;
        numFilter = _numFilter;
        $rootScope = _$rootScope;
        /////////////////////////////////
        // loginService.loginCtl(true, $location.absUrl());

        // if(store.get('测试localstory')){
        //     alert(store.get('测试localstory'));
        // }else{
        //     alert('setLocalstory');
        //     store.set('测试localstory', '1234567890');
        // }


        $scope.brandAppId = $stateParams.brandAppId;
        // addressService.getAdc();
        /*$scope.aaa = [
         {
         label: 'aaa',
         value: '111'
         }, {
         label: 'bbb',
         value: '21'
         }, {
         label: 'ccc',
         value: '31'
         }
         ];
         $scope.bbb = [
         {
         label: '111',
         value: 'A1'
         }, {
         label: '2222',
         value: 'B1'
         }, {
         label: '3333',
         value: 'C1'
         }
         ];

         $scope.ccc = [
         {
         label: '00',
         value: 'A111'
         }, {
         label: '000000000',
         value: 'B111'
         }, {
         label: '000000000000000',
         value: 'C111'
         }
         ];
         $scope.open = function () {
         weui.picker($scope.aaa, $scope.bbb, $scope.ccc, {
         defaultValue: [$scope.aaa[0].value, $scope.bbb[0].value, $scope.ccc[2].value],
         onChange: function (result) {
         console.log('1', result);
         },
         onConfirm: function (result) {
         console.log('2', result);
         },
         id: 'multiPickerBtn'
         });
         };*/
        // Mock.mock('http://picker', {
        //     "data": [
        //         {
        //             label: '广东',
        //             value: 0,
        //             children: [
        //                 {
        //                     label: '广州',
        //                     value: 0,
        //                     children: [
        //                         {
        //                             label: '海珠',
        //                             value: 0
        //                         }, {
        //                             label: '番禺',
        //                             value: 1
        //                         }
        //                     ]
        //                 }, {
        //                     label: '佛山',
        //                     value: 1,
        //                     children: [
        //                         {
        //                             label: '禅城',
        //                             value: 0
        //                         }, {
        //                             label: '南海',
        //                             value: 1
        //                         }
        //                     ]
        //                 }
        //             ]
        //         }, {
        //             label: '广西',
        //             value: 1,
        //             children: [
        //                 {
        //                     label: '南宁',
        //                     value: 0,
        //                     children: [
        //                         {
        //                             label: '青秀',
        //                             value: 0
        //                         }, {
        //                             label: '兴宁',
        //                             value: 1
        //                         }
        //                     ]
        //                 }, {
        //                     label: '桂林',
        //                     value: 1,
        //                     children: [
        //                         {
        //                             label: '象山',
        //                             value: 0
        //                         }, {
        //                             label: '秀峰',
        //                             value: 1
        //                         }
        //                     ]
        //                 }
        //             ]
        //         }
        //     ]
        // });

        // $scope.getData = function () {
        //     $http({
        //         method: 'GET',
        //         url: 'http://picker',
        //         params: {},
        //     }).then(function (resp) {
        //         $scope.comments = resp.data;
        //     })
        // };
        // // $scope.getData();
        //
        // $scope.getTestData = () => {
        //     $http({
        //         method: 'GET',
        //         url: 'https://agency.kingsilk.net/admin/local/14300/rs/api/testLit/init',
        //         params: {},
        //     }).then(function (resp) {
        //         $scope.comments = resp.data;
        //         console.log('123', $scope.comments);
        //     })
        // };
        // // $scope.getTestData();
        //
        // $scope.open = function () {
        //     weui.picker($rootScope.adc.data, {
        //         depth: 3,
        //         defaultValue: [620000, 620100, 620102],
        //         onChange: function (result) {
        //         },
        //         onConfirm: function (result) {
        //         },
        //         id: 'cascadePicker'
        //     });
        // };

        var _that = this;

        $scope.roundedRect = function (ctx, x, y, width, height, radius) {
            ctx.beginPath();
            ctx.moveTo(x, y + radius);
            ctx.lineTo(x, y + height - radius);
            ctx.quadraticCurveTo(x, y + height, x + radius, y + height);
            ctx.lineTo(x + width - radius, y + height);
            ctx.quadraticCurveTo(x + width, y + height, x + width, y + height - radius);
            ctx.lineTo(x + width, y + radius);
            ctx.quadraticCurveTo(x + width, y, x + width - radius, y);
            ctx.lineTo(x + radius, y);
            ctx.quadraticCurveTo(x, y, x, y + radius);
            ctx.stroke();
        };
        $scope.drawQrcode = function () {
            var nWidth = document.body.clientWidth,//屏幕可视区域 宽度
                nHeight = document.body.clientHeight,//屏幕可视区域 高度
                _canvasWidth = document.body.clientWidth * 2,//画布 宽度
                _canvasHeight = document.body.clientHeight * 2;//画布 高度
            //开始画图，获取上下文；
            var _canvas = document.getElementById("canvas_box");
            _canvas.width = _canvasWidth;
            _canvas.height = _canvasHeight;
            var _context = _canvas.getContext('2d');
            //背景
            _context.fillStyle = "#f3af4c";
            _context.fillRect(0, 0, nWidth * 2, nHeight * 2);
            //白色矩形部分
            _context.moveTo(40, 203);
            _context.strokeStyle = 'rgba(255,255,255,1)';
            _context.fillStyle = 'rgba(255,255,255,1)';
            $scope.roundedRect(_context, 40, 70 * 2, 335 * 2, 489 * 2, 10 * 2);
            _context.fill();
            _context.closePath();
            var _imagehead = new Image();//头像
            //如果有跨域问题，请给img对象添加如下属性
            _imagehead.crossOrigin = "anonymous";
            _imagehead.src = 'http://img1.imgtn.bdimg.com/it/u=3664893832,2142990655&fm=26&gp=0.jpg';
            _imagehead.onload = function () {
                _context.save(); // 保存当前_context的状态
                _context.beginPath();
                _context.moveTo(((nWidth) / 2 + 40 / 375 * nWidth) * 2, 70.28 / 603 * nHeight * 2);
                _context.lineWidth = "20";
                //画出圆
                _context.arc(nWidth, 70.28 / 603 * nHeight * 2, 40 / 375 * nWidth * 2, 0, 2 * Math.PI, true);
                //圆有个边框
                _context.lineWidth = 20;
                _context.strokeStyle = 'rgba(255,197,108,14)';
                _context.fill();
                _context.stroke();
                //裁剪上面的圆形
                _context.clip();
                // 在刚刚裁剪的园上画图
                _context.drawImage(_imagehead, (nWidth / 2 - 40) * 2, 30 * 2, 90 * 2, 90 * 2);
                _context.restore();
                _context.stroke();
                //头像下面的文字
                _context.beginPath();
                _context.textAlign = "center";
                //设置字体
                _context.font = '30px Arial';
                _context.lineWidth = 1.0;
                _context.fillStyle = 'rgb(73,73,73)';
                _context.fillText("任小超", nWidth, 150 * 2);


                //onload是异步加载，所以要等第一个onload 加载完毕再画第二张图片
                //代言文字图片
                var _imagetext = new Image();
                //解决跨域，如果有跨域错误信息一定要加此属性；
                _imagetext.crossOrigin = "anonymous";
                _imagetext.src = 'https://cdn.kaishuhezi.com/kstory/activity_flow/image/a0364809-6289-474e-a5da-4aca336541cb.png';
                _imagetext.onload = function () {
                    _context.save(); // 保存当前_context的状态
                    _context.drawImage(_imagetext, (nWidth - 200) / 2 * 2, 170 * 2, 200 * 2, 25 * 2);
                    _context.stroke();//
                    _context.closePath();

                    //canvas 画完图 一定要生成图片流，作为img 的src属性值，同时隐藏canvas，只展示img 就ok了，在手机上试试长按保存功能吧

                    var _imgSrc = _canvas.toDataURL("image/png", 1);
                    _canvas.style.display = "none";
                    var imgShow = document.getElementById('imgShow');
                    imgShow.setAttribute('src', _imgSrc);
                }
            }
        }

        $scope.drawQrcode();
    }
}

Controller.$inject = [
    '$scope',
    '$rootScope',
    '$http',
    'addressService',
    '$state',
    '$log',
    '$mdBottomSheet',
    'loginService',
    '$mdDialog',
    '$timeout',
    '$stateParams',
    'numFilter',
    '$location'
];

export default Controller ;
