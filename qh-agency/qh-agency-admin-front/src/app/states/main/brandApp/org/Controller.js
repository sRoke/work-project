var $scope;
class Controller {
    constructor(_$scope) {
        $scope = _$scope;
    }
}

Controller.$inject = [
    '$scope'
];

export default Controller ;
