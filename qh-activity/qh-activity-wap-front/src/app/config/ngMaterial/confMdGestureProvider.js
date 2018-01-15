// Chrome 浏览器中如果使用 mobile 模式，select 下拉列表打不开。
// https://github.com/angular/material/issues/9766

confMdGestureProvider.$inject = ['$mdGestureProvider'];
function confMdGestureProvider($mdGestureProvider) {
    $mdGestureProvider.skipClickHijack();
}

export default confMdGestureProvider;
