import angular from "angular";

directiveFactory.$inject = [];
function directiveFactory() {
    return {
        restrict: 'A', link: function (scope, element, attrs) {
            element.bind('load', function () {
                //call the function that was passed
                scope.$apply(attrs.imageonload);
            });
        }
    };
}
export default directiveFactory;

