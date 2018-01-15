/**
 * 阻止ios系统300ms延迟
 *
 * 参考： http://stackoverflow.com/questions/34575510/angular-ng-click-issues-on-safari-with-ios-8-3
 */
import angular from "angular";

directiveFactory.$inject = [];
function directiveFactory() {
    return function (scope, elem, attrs) {

        /*touchstart  暂时线去掉*/
        elem.bind(" click", function (e) {
            e.preventDefault();
            e.stopPropagation();
            scope.$apply(attrs["ngMobileClick"]);
        });
    };
}
export default directiveFactory;
