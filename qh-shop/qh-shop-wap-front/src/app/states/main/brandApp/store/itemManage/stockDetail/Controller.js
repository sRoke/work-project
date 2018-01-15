import conf from "../../../../../../conf";


var $scope,
    loginService,
    $state,
    $stateParams,
    $timeout,
    $location,
    $http;
class Controller {
    constructor(_$scope,
                _loginService,
                _$state,
                _$stateParams,
                _$timeout,
                _$location,
                _$http) {
        $scope = _$scope;
        loginService = _loginService;
        $state = _$state;
        $stateParams = _$stateParams;
        $location = _$location;
        $http = _$http;
        $timeout=_$timeout;
        loginService.loginCtl(true, $location.absUrl());

        $scope.isClick = 1;
        $scope.clickThis = function (num) {
            $scope.isClick = num;
        };

        $scope.com = new Array(3);

        $timeout(function () {
            var swiper3 = new Swiper('.swiper-container2', {
                // pagination: '.swiper-pagination',
                wrapperClass: 'my-wrapper2',
                slideClass: 'my-slide2',
                slidesPerView: 3,
                // centeredSlides: true,
                paginationClickable: true,
                spaceBetween: 10,
                iOSEdgeSwipeDetection: true,
                setWrapperSize: true,
                // loop : true,
            });

            // swiper3.slideTo(1, 1000, false);//切换到第一个slide，速度为1秒
        }, 100);

        /*返回上级*/
        $scope.fallbackPage = function () {
            if (history.length === 1) {
                $state.go("main.brandApp.center.main", {brandAppId: $stateParams.brandAppId}, {reload: true});
            } else {
                history.back();
            }
        };
    }
}

Controller.$inject = [
    '$scope',
    'loginService',
    '$state',
    '$stateParams',
    '$timeout',
    '$location',
    '$http',
];

export default Controller ;
