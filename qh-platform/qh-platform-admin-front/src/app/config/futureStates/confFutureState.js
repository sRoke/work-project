import stateFactory from "./stateFactory";

var futureStates = global.myStates; // FIXME :

confFutureState.$inject = ['$futureStateProvider'];
function confFutureState($futureStateProvider) {

    $futureStateProvider.stateFactory('load', stateFactory);

    futureStates.forEach(function (s) {
        //console.log("---------reg future state :" + s[0]);
        let state = {
            name: s[0],
            url: s[1],
            src: s[2],
            type: "load"
        };
        if (s.length >= 4) {
            state.type = s[3];
        }
        $futureStateProvider.futureState(state);
    });
}


export default confFutureState;
