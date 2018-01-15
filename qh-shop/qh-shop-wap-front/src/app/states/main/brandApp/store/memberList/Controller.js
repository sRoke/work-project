import conf from "../../../../../conf";
import makePy from './searchFname' //引入查询charAt  js

var $scope,
    $http,
    $state,
    $log,
    $mdBottomSheet,
    loginService,
    $mdDialog,
    $timeout,
    $stateParams,
    $location,
    alertService,
    wxService,
    authService,
    $rootScope;
class Controller {
    constructor(_$scope,
                _$http,
                _$state,
                _$log,
                _$mdBottomSheet,
                _loginService,
                _$mdDialog,
                _$timeout,
                _$stateParams,
                _$location,
                _alertService,
                _wxService,
                _authService,
                _$rootScope) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        $rootScope = _$rootScope;
        $timeout = _$timeout;
        loginService = _loginService;
        $log = _$log;
        authService = _authService;
        $stateParams = _$stateParams;
        $mdBottomSheet = _$mdBottomSheet;
        $location = _$location;
        alertService = _alertService;
        wxService = _wxService;
        /////////////////////////////////
        loginService.loginCtl(true, $location.absUrl());
        authService.setAuthorities($stateParams.brandAppId, $stateParams.storeId).then(function (data) {
            $scope.viewShow = data;
        });
        $rootScope.brandAppId = $scope.brandAppId = $stateParams.brandAppId;
        $scope.storeId = $stateParams.storeId;

        //获取微信id
        $scope.getWxInfo = function () {
            $http({
                method: "GET",
                url: conf.pfApiPath + "/brandApp/" + $stateParams.brandAppId,
                headers: {
                    "brandAppp-Id": $scope.brandAppId
                },
                params: {}
            }).success(function (resp) {
                // console.log(resp);
                if (resp.data.wxMpId && resp.data.wxComAppId) {
                    $rootScope.wxComAppId = resp.data.wxComAppId;
                    $rootScope.wxMpAppId = resp.data.wxMpId;
                    if (loginService.getAccessToken()) {
                        $scope.getMember('0');
                    }
                    // console.log($rootScope.wxComAppId, $rootScope.wxMpAppId);
                }
            });
        };


        function asc_sort(a, b) {//按首字母排序
            // console.log('a', a);
            // console.log('b', b);
            if (a && b) {
                return makePy(a.nickName.charAt(0))[0].toUpperCase() < makePy(b.nickName.charAt(0))[0].toUpperCase() ? -1 : 1;
            }
        }

        function abc_sort(a, b) {//按首字母排序
            // console.log('a', a);
            // console.log('b', b);
            if (a && b) {
                return a < b ? -1 : 1;
            }
        }

        function ila_sort(arr) {
            var initials = [];
            var num = 0;

            for (let i = 0; i < arr.length; i++) {

                if (!arr[i].nickName) {
                    arr[i].nickName = '#'
                }
                var initial = makePy(arr[i].nickName.charAt(0))[0].toUpperCase();
                if (initial >= 'A' && initial <= 'Z') {
                    if (initials.indexOf(initial) === -1)
                        initials.push(initial);
                } else {
                    num++;
                }
                console.log('initials', initials);
            }
            initials.push("#")
            return initials;
            // console.log('initials1', initials);

        }


        //获取会员信息
        $scope.getMember = function (page) {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/shop/" + $scope.storeId + "/member/page",
                params: {
                    size: conf.pageSize,
                    page: page,
                    // sort:'',
                    keyword: $scope.keyWord,
                    wxComAppId: $rootScope.wxComAppId,
                    wxMpAppId: $rootScope.wxMpAppId
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $scope.data = resp.data.data;


                var dataList = $scope.data.content;
                var ilaArr = ila_sort(dataList);
                dataList.sort(asc_sort);
                ilaArr.sort(abc_sort);


                // console.log('ilaArr', ilaArr);
                // console.log('dataList', dataList);

                $scope.showDataList = [];

                for (let i = 0; i < ilaArr.length; i++) {
                    for (let j = 0; j < dataList.length; j++) {
                        if (!dataList[j].nickName) {
                            dataList[j].nickName = '#';
                        }

                        // console.log('dataList[j].nickName ', dataList[j].nickName);
                        // console.log('ilaArr[i] ', ilaArr[i]);
                        // console.log('makePy(dataList[j].nickName.charAt(0))[0].toUpperCase() ', makePy(dataList[j].nickName.charAt(0))[0].toUpperCase());

                        if (ilaArr[i] == makePy(dataList[j].nickName.charAt(0))[0].toUpperCase() && makePy(dataList[j].nickName.charAt(0))[0].toUpperCase() ) {
                            $scope.showDataList.push({
                                abcName: ilaArr[i],
                                abcData: [dataList[j]]
                            });
                        } else if (ilaArr[i] == '#' && makePy(dataList[j].nickName.charAt(0))[0].toUpperCase() >= 0) {

                            // console.log(' typeof makePy(dataList[j].nickName.charAt(0))[0].toUpperCase() == number', typeof makePy(dataList[j].nickName.charAt(0))[0].toUpperCase())
                            // console.log(' typeof makePy(dataList[j].nickName.charAt(0))[0].toUpperCase() == number', makePy(dataList[j].nickName.charAt(0))[0].toUpperCase())

                            $scope.showDataList.push({
                                abcName: ilaArr[i],
                                abcData: [dataList[j]]
                            });

                        }
                    }
                }


                $scope.content = [];

                for (let k = 0; k < $scope.showDataList.length; k++) {
                    var flog = false;
                    if ($scope.content.length == 0) {
                        $scope.content.push($scope.showDataList[0]);
                    } else {
                        for (let i = 0; i < $scope.content.length; i++) {
                            if ($scope.content[i].abcName == $scope.showDataList[k].abcName) {
                                $scope.content[i].abcData.push($scope.showDataList[k].abcData[0]);
                                flog = true;
                                break;
                            } else {
                            }
                        }
                        if (!flog) {
                            $scope.content.push($scope.showDataList[k]);
                        }
                    }
                    console.log('  $scope.content', $scope.content);
                }


                // var showDataList = $scope.showDataList;
                // showDataList.sort(abc_sort);


                // console.log('showDataList', showDataList);
                $scope.showData = dataList;
                // console.log(' $scope.showDataList', $scope.showDataList);

                // console.log('$scope.showData', $scope.showData);

            }, function (resp) {
                //TODO 错误处理
            });
        };


        $scope.getWxInfo();
        // var persons = [
        //     {"name": "xingoo", "age": 25},
        //     {"name": "zhangsan", "age": 18},
        //     {"name": "lisi", "age": 20},
        //     {"name": "王", "age": 22},
        //     {"name": "A", "age": 21},
        //     {"name": "粑粑", "age": 20},
        //     {"name": "wangwu", "age": 30}
        // ];
        //
        //
        // $scope.sortData = function () {
        //     persons.sort(asc_sort);//按首字母排序
        //
        //     $scope.persons = persons;
        //     console.log('persons', $scope.persons);
        //
        //     // $scope.$digest()
        // };


        // var initials = [];
        // var num = 0;
        //
        // for (let i = 0; i < $scope.persons.length; i++) {
        //
        //     var initial = makePy($scope.persons[i].name.charAt(0))[0].toUpperCase();
        //     if (initial >= 'A' && initial <= 'Z') {
        //         if (initials.indexOf(initial) === -1)
        //             initials.push(initial);
        //     } else {
        //         num++;
        //     }
        //     console.log('initials', initials);
        // }

        // for (let i = 0; i < $scope.persons.length; i++) {
        //
        //     // if (i >= 0 && i < $scope.persons.length - 1) {
        //     //     asc_sort($scope.persons[i].name, $scope.persons[i + 1].name)
        //     //     console.log('asc_sort', asc_sort());
        //     // }
        //
        //     console.log('($scope.persons[i])[0].toUpperCase()', ($scope.persons[i].name.charAt(0))[0].toUpperCase());
        //     var letter = makePy(($scope.persons[i].name.charAt(0))[0].toUpperCase());
        //     console.log('letter', letter);
        //     // switch (letter) {
        //     //     case "A":
        //     //         console.log(2)
        //     //         break;
        //     //     case "B":
        //     //         console.log(3)
        //     //         break;
        //     //     case "C":
        //     //         console.log(4)
        //     //         break;
        //     //     case "D":
        //     //         console.log(5)
        //     //         break;
        //     //     case "E":
        //     //         console.log(6)
        //     //         break;
        //     //     case "F":
        //     //         console.log(7)
        //     //         break;
        //     //     case "G":
        //     //         console.log(8)
        //     //         break;
        //     //     case "H":
        //     //         console.log(9)
        //     //         break;
        //     //     case "I":
        //     //         console.log(0)
        //     //         break;
        //     //     case "J":
        //     //         console.log(11)
        //     //         break;
        //     //     case "K":
        //     //         console.log(12)
        //     //         break;
        //     //     case "L":
        //     //         console.log(13)
        //     //         break;
        //     //     case "M":
        //     //         console.log(14)
        //     //         break;
        //     //     case "O":
        //     //         console.log(15)
        //     //         break;
        //     //     case "P":
        //     //         console.log(16)
        //     //         break;
        //     //     case "Q":
        //     //         console.log(17)
        //     //         break;
        //     //     case "R":
        //     //         console.log(18)
        //     //         break;
        //     //     case "S":
        //     //         console.log(19)
        //     //         break;
        //     //     case "T":
        //     //         console.log(20)
        //     //         break;
        //     //     case "U":
        //     //         console.log(21)
        //     //         break;
        //     //     case "V":
        //     //         console.log(22)
        //     //         break;
        //     //     case "W":
        //     //         console.log(23)
        //     //         break;
        //     //     case "X":
        //     //         console.log(24)
        //     //         break;
        //     //     case "Y":
        //     //         console.log(25)
        //     //         break;
        //     //     case "Z":
        //     //         console.log(26)
        //     //         break;
        //     //     default:
        //     //         console.log(1)
        //     //         break;
        //     // }
        // }


        $scope.clearKeyWord = function () {
            $scope.keyWord = '';
        };

        //搜索

        $scope.focus = function (status) {
            if (status) {
                $scope.searchShow = true;
            } else {
                $scope.searchShow = false;
            }
        };

        /*返回上级*/
        $scope.fallbackPage = function () {
            $state.go("main.brandApp.store.home", {}, {reload: true})
        };

    }
}

Controller.$inject = [
    '$scope',
    '$http',
    '$state',
    '$log',
    '$mdBottomSheet',
    'loginService',
    '$mdDialog',
    '$timeout',
    '$stateParams',
    '$location',
    'alertService',
    'wxService',
    'authService',
    '$rootScope'
];

export default Controller ;
