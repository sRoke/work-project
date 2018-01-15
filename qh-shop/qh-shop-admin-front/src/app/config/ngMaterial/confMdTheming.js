/**
 * 配置主题
 */

confMdTheming.$inject = ['$mdThemingProvider'];
function confMdTheming($mdThemingProvider) {
    $mdThemingProvider
        .theme('default')
        .primaryPalette('light-green', {
            'default': '50'
        });
    //https://material.angularjs.org/latest/Theming/03_configuring_a_theme   去掉主题颜色
    //$mdThemingProvider.disableTheming();
}

export default confMdTheming;
