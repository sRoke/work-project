import conf from "../../conf";
import angular from "angular";
import number_format from "./number_format";


num.$inject = [];
function num() {
    return number_format;
};

export default angular.module(`${conf.app}.filters.num`, [])
    .filter('num', num);

