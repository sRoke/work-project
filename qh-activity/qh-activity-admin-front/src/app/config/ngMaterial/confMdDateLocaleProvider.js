// Chrome 浏览器中如果使用 mobile 模式，select 下拉列表打不开。
// https://github.com/angular/material/issues/9766

confMdDateLocaleProvider.$inject = ['$mdDateLocaleProvider', 'dateFilter'];
function confMdDateLocaleProvider($mdDateLocaleProvider, dateFilter ) {
    $mdDateLocaleProvider.formatDate = function(date) {

        console.log("===============================dddd 1: ", date);
        console.log("===============================dddd 2: ",  dateFilter(date, "yyyy-MM-dd"));
        return date;
    };;
}

export default confMdDateLocaleProvider;
