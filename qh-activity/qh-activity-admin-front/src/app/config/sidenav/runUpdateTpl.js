/*
 * 更新 angular-material-sidenav 所使用到的 template。
 */
import linkTpl from "!html-loader?minimize=true!./views/ss/menu-link.tmpl.html";
import sidenavTpl from "!html-loader?minimize=true!./views/ss/menu-sidenav.tmpl.html";
import toggleTpl from "!html-loader?minimize=true!./views/ss/menu-toggle.tmpl.html";

runUpdateTpl.$inject = ['$templateCache', '$rootScope'];
function runUpdateTpl($templateCache, $rootScope) {

    // 当前登录用户的权限列表，需要在用登录后、切换组织后，获取最新，并保存到 $rootScope 内。
    $rootScope._userAuthorities = [];  // FIXME : 放到该文件里貌似并不合适。

    $templateCache.put('views/ss/menu-link.tmpl.html', linkTpl);
    $templateCache.put('views/ss/menu-toggle.tmpl.html', toggleTpl);
    $templateCache.put('views/ss/menu-sidenav.tmpl.html', sidenavTpl);
}

export default runUpdateTpl;

