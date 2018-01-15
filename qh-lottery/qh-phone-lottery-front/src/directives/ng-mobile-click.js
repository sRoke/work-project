(function () {

    /**
     * 阻止ios系统300ms延迟
     *
     * 参考： http://stackoverflow.com/questions/34575510/angular-ng-click-issues-on-safari-with-ios-8-3
     */
    angular.module('qh-phone-lottery-front').directive("ngMobileClick", [function () {
        return function (scope, elem, attrs) {
            elem.bind("touchstart click", function (e) {
                e.preventDefault();
                e.stopPropagation();
                scope.$apply(attrs["ngMobileClick"]);
            });
        };
    }]);
})();