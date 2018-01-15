stateFactory.$inject = [
    '$q',
    '$log',
    '$ocLazyLoad',
    'futureState'
];
function stateFactory($q, $log, $ocLazyLoad, futureState) {

    console.log("----------- stateFactory : ", futureState.name);
    var def = $q.defer();

    $ocLazyLoad.load(futureState.src).then(function () {
        console.log("----------- stateFactory : then : ", futureState.name);
        def.resolve();
    }, function (err) {
        console.log("----------- stateFactory : err : ", futureState.name);
        def.reject(err);
    });

    return def.promise;
}


export default stateFactory;
