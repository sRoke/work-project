import conf from "../../../../../conf";
// import "jquery";
var $scope,
    $http,
    $state,
    $log,
    loginService,
    $mdDialog,
    $stateParams,
    $location,
    $q,
    Upload,
    wxService,
    $interval,
    alertService,
    $templateCache;
class Controller {
    constructor(_$scope,
                _$http,
                _$state,
                _$log,
                _loginService,
                _$mdDialog,
                _$stateParams,
                _$location,
                _$q,
                _Upload,
                _wxService,
                _$interval,
                _alertService,
                _$templateCache) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        loginService = _loginService;
        $log = _$log;
        $q = _$q;
        alertService = _alertService;
        $interval = _$interval;
        $templateCache = _$templateCache;
        Upload = _Upload;
        $location = _$location;
        wxService = _wxService;
        $stateParams = _$stateParams;
        $scope.brandAppId = $stateParams.brandAppId;
        /////////////////////////////////


        $scope.brandAppId = $stateParams.brandAppId;


        $scope.tabs = 1;
        $scope.changeTabs = true;
        $scope.checkTabs = function (tabIndex) {
            if (tabIndex == $scope.tabs) {
                return;
            }
            if ($scope.changeTabs) {
                $scope.changeTabs = false;
                $scope.tabs = tabIndex;
                $scope.currPage = 0;
                $scope.keyWord = null;
                if ($scope.tabs == 1) {
                    $scope.getList(1, ['dateCreated,desc']);
                } else if ($scope.tabs == 2) {
                    $scope.getList(1, ['dateCreated,asc']);
                } else {
                    $scope.changeTabs = true;
                }
            }
            ;
        };


        $scope.currentPage = 1;
        $scope.changePage = function () {
            console.log($scope.currentPage);
        }


        if (loginService.getAccessToken()) {
            $http({
                method: 'GET',
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + '/activity/' + $stateParams.activityId + '/voteWorks/s/isSignup',
                params: {},
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                if (resp.data.data) {
                    if(resp.data.data.logOut){
                        jso.wipeTokens();
                        loginService.setBrandAppId(null);
                        loginService.setAccessToken(null);
                        $http({
                            method: "POST",
                            url: "https:" + conf.oauthPath +"/logout",
                            headers: {},
                            params: {},
                            withCredentials:true,
                        }).success(function (resp) {
                            location.reload();
                        },function(resp){
                            console.log('ERR', resp);
                        });
                    }else {
                        if(resp.data.data.workId){
                            $scope.myVoteId = resp.data.data.workId;
                        }
                        if ($stateParams.formOauth) {
                            $scope.getData(true);
                        } else {
                            $scope.pageShow = true;
                        }
                    }
                }else {
                    if ($stateParams.formOauth) {
                        $scope.getData(true);
                    } else {
                        $scope.pageShow = true;
                    }
                }




            })
        } else {
            $scope.pageShow = true;
        }
        ;


        $scope.getData = function (signup) {
            $http({
                method: 'GET',
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + '/voteActivity/' + $stateParams.activityId,
                params: '',
                headers: {
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $scope.data = resp.data.data;



                $scope.signUpStartTime = resp.data.data.signUpStartTime.substring(0,resp.data.data.signUpStartTime.length-3) ;
                $scope.signUpEndTime = resp.data.data.signUpEndTime.substring(0,resp.data.data.signUpEndTime.length-3);
                $scope.voteStartTime = resp.data.data.voteStartTime.substring(0,resp.data.data.voteStartTime.length-3);
                $scope.voteEndTime = resp.data.data.voteEndTime.substring(0,resp.data.data.voteEndTime.length-3);






                if (signup) {
                    $scope.singUp();
                    return
                }

                if (new Date(Date.parse($scope.data.signUpEndTime.replace(/-/g, "/"))).getTime() < new Date().getTime()) {
                    $scope.signUpisEnd = true;
                }

                $scope.changeDate($scope.data.voteStartTime, $scope.data.voteEndTime);
                if ($scope.data.rule) {
                    $scope.tplUrlRule = "/___/store/xxxx/index/tplRule";
                    $templateCache.put($scope.tplUrlRule, $scope.data.rule);
                }
                if ($scope.data.desp) {
                    $scope.tplUrlDesp = "/___/store/xxxx/index/tplDesp";
                    $templateCache.put($scope.tplUrlDesp, $scope.data.desp);
                }

                if (wxService.isInWx()) {
                    wxService.init().then(function (data) {
                        if (data) {
                            var conf = {
                                title: $scope.data.shareTitle,
                                desc: $scope.data.shareContent,
                                link: $location.absUrl(),
                                imgUrl: $scope.data.primaryImgUrl,
                            };
                            wxService.shareRing(conf);
                            wxService.shareFriend(conf);
                        }
                    })
                }
            })
        };
        $scope.getData();


        $scope.currPage = 1;


        //-----------------------------------最新参赛
        $scope.getList = function (currPage, status) {
            if (!status) {
                if ($scope.tabs == 1) {
                    status = ['dateCreated,desc']
                } else if ($scope.tabs == 2) {
                    status = ['dateCreated,asc'];
                }
            }
            $http({
                method: 'GET',
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + '/activity/' + $stateParams.activityId + '/voteWorks',
                params: {
                    page: currPage - 1,
                    size: 6,
                    sort: status,
                    // ['dateCreated,desc'],  1
                    //['totalVotes,desc']     2
                    keyWord: $scope.keyWord,
                },
                headers: {
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                $scope.list = resp.data.data;
                $scope.changeTabs = true;
            })
        };
        $scope.getList($scope.currPage, ['dateCreated,desc']);


        $scope.changeDate = function (startDate, endDate) {
            $scope.votStartDate = new Date(Date.parse(startDate.replace(/-/g, "/"))).getTime();
            $scope.votEndDate = new Date(Date.parse(endDate.replace(/-/g, "/"))).getTime();
            $interval(function () {
                $scope.nowDate = new Date().getTime();
                if ($scope.votStartDate >= $scope.nowDate) {
                    // console.log('投票活动未开始');
                    $scope.activityStatus = -1;
                } else if ($scope.votEndDate <= $scope.nowDate) {
                    // console.log('活动已结束');
                    $scope.activityStatus = 1;
                } else if ($scope.votStartDate < $scope.nowDate && $scope.votEndDate > $scope.nowDate) {
                    $scope.activityStatus = 0;
                    $scope.lastTime = $scope.votEndDate - $scope.nowDate;
                    $scope.lastDays = Math.floor($scope.lastTime / 1000 / 60 / 60 / 24);
                    $scope.lastHours = Math.floor($scope.lastTime / 1000 / 60 / 60 - (24 * $scope.lastDays));
                    $scope.lastMinutes = Math.floor($scope.lastTime / 1000 / 60 - (24 * 60 * $scope.lastDays) - (60 * $scope.lastHours));
                    $scope.lastSeconds = Math.floor($scope.lastTime / 1000 - (24 * 60 * 60 * $scope.lastDays) - (60 * 60 * $scope.lastHours) - (60 * $scope.lastMinutes));
                    // console.log('活动正在进行中',$scope.lastTime);
                } else {
                    $scope.activityStatus = -1;
                }
            }, 1000);
        };


        $scope.singUp = function () {

            if (new Date(Date.parse($scope.data.signUpStartTime.replace(/-/g, "/"))).getTime() > new Date().getTime()) {
                return alertService.msgAlert("exclamation-circle", "活动还未开始");
            }

            // 已经登录
            if (loginService.getAccessToken()) {


                /*
                if(用户尚未绑定指定的微信公众号){
                    logout();
                    login();
                }
                 */

                $http({
                    method: 'GET',
                    url: conf.apiPath + "/brandApp/" + $scope.brandAppId + '/activity/' + $stateParams.activityId + '/voteWorks',
                    params: {},
                    headers: {
                        "brandApp-Id": $scope.brandAppId
                    }
                }).then(function (resp) {
                    $scope.list = resp.data.data;
                    $scope.changeTabs = true;
                })














                if ($scope.myVoteId) {
                    $state.go('main.brandApp.vote.myVote', {id: $scope.myVoteId});
                } else {
                    $state.go('main.brandApp.vote.votSignUp');
                }

                // 尚未登录
            } else {
                // logout();
                loginService.loginCtl(true, $location.absUrl() + '?q=11&formOauth=true');
            }
        };
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
    '$q',
    'Upload',
    'wxService',
    '$interval',
    'alertService',
    '$templateCache'
];

export default Controller ;
