/**
 * 防止md-bottom-sheet中的 md-content 无法滚动，需要: <md-content stop-touch-event > 。
 *
 * 参考： https://github.com/angular/material/issues/2073
 */
import angular from "angular";

directiveFactory.$inject = [];
function directiveFactory() {
    return {
        restrict: 'A',
        link: function (scope, element) {
            element.on('touchmove', function (evt) {
                evt.stopPropagation();
            });
        }
    };
}
export default directiveFactory;

