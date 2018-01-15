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
        $scope.voteAppId = $stateParams.voteAppId;
        /////////////////////////////////
        loginService.loginCtl(true, $location.absUrl());

        $scope.clearKeyWord = function () {
            $scope.keyWord = '';
        };

        //搜索
        $scope.search = function () {
            $scope.pageTitle = '全部';
            $scope.status = [];
            $scope.pageChange(0);
        };

        $scope.focus = function (status) {
            if (status) {
                $scope.searchShow = true;
            } else {
                $scope.searchShow = false;
            }
        };



        $scope.currpage = 0 ;
        $scope.pageChange = function (currpage) {
            $http({
                method: "GET",
                url: conf.apiPath + "/voteApp/" + $stateParams.voteAppId + '/vote/admin/' + $stateParams.id + '/getvoteRecord',
                params: {
                    page:currpage?currpage:$scope.currpage,
                    size:conf.pageSize,
                    keyWord:$scope.keyWord,
                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken()
                }
            }).then(function (resp) {
                    $scope.currpage = currpage;
                    console.log('resprespresprespresp', resp);
                    if(currpage){
                        $scope.pageList.number = resp.data.data.number;
                        for (var i=0;i< resp.data.data.content.length;i++){
                            $scope.pageList.content.push(resp.data.data.content[i])
                        }
                    }else {
                        $scope.pageList = resp.data.data;
                    }
                }, function () {
                    //error
                    $scope.cliclSave = false;
                }
            );
            $http({
                method: "GET",
                url: conf.apiPath + "/voteApp/" + $stateParams.voteAppId + '/vote/admin/' + $stateParams.id + '/getNum',
                params: {},
                headers: {
                    // 'Authorization': 'Bearer ' + loginService.getAccessToken()
                }
            }).then(function (resp) {
                    console.log('resprespresprespresp333333333', resp);
                    $scope.num = resp.data.data;
            }, function () {
                    //error
            });
        };
        $scope.pageChange(0);











        $scope.friendsList = function (data) {
            $mdDialog.show({
                templateUrl: 'frends.html',
                parent: angular.element(document.body),
                clickOutsideToClose: true,   //true 点击界外的时候弹窗消失
                fullscreen: false,
                controller: ['$http', '$stateParams', '$mdDialog', '$rootScope', function ($http, $stateParams, $mdDialog, $rootScope) {
                    var vmd = this;
                    vmd.data = data;
                    vmd.currPage = 0;
                    vmd.friendList = [];
                    vmd.getFriendList = function (currpage) {
                        $http({
                            method: "GET",
                            url: conf.apiPath + "/voteApp/" + $stateParams.voteAppId + '/vote/admin/voteRecord/' + vmd.data.id + '/userId/' + vmd.data.userId + '/getvoteHelpUser',
                            params: {
                                page:currpage?currpage:vmd.currpage,
                                size:2,
                                keyWord:vmd.keyWord,
                            },
                            headers: {
                                // 'Authorization': 'Bearer ' + loginService.getAccessToken()
                            }
                        }).then(function (resp) {
                            vmd.currpage = currpage;
                            console.log('resprespresprespresp', resp);
                            if(currpage){
                                vmd.pageList.number = resp.data.data.number;
                                for (var i=0;i< resp.data.data.content.length;i++){
                                    vmd.pageList.content.push(resp.data.data.content[i])
                                }
                            }else {
                                vmd.pageList = resp.data.data;
                            }
                        }, function () {
                            //error
                            vmd.cliclSave = false;
                        });
                    };
                    vmd.getFriendList(0);

                    // vmd.getMore = function () {
                    //     $http({
                    //         method: 'GET',
                    //         url: conf.apiPath + "/voteApp/" + $scope.voteAppId + '/activity/' + $stateParams.activityId + '/voteRecord/' + $stateParams.id,
                    //         params: {
                    //             page: vmd.currPage,
                    //             size: 6,
                    //             // sort: ['totalVotes,desc','lastVoteTime,desc'],
                    //         },
                    //         headers: {
                    //             "voteApp-Id": $scope.voteAppId
                    //         }
                    //     }).then(function (resp) {
                    //         vmd.friendList.number = resp.data.data.number;
                    //         vmd.friendList.content.push.apply(vmd.friendList.content, resp.data.data.content);
                    //
                    //         console.log(vmd.friendList);
                    //
                    //         vmd.currPage++;
                    //     })
                    // };

                    vmd.cancel = function () {
                        return $mdDialog.cancel();
                    };
                    vmd.goAddress = function () {
                        $mdDialog.cancel().then(function () {
                            $state.go('main.voteApp.addAddress', {orderId: id}, {reload: true})
                        })
                    }
                }],
                controllerAs: "vmd"
            }).then(function (answer) {
            }, function () {
            });
        };

        // $scope.friendsList('123123')







        $scope.fallbackPage = function () {
            // $state.go("main.voteApp.vote.voteHome", {}, {reload: true});
            history.back();
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
