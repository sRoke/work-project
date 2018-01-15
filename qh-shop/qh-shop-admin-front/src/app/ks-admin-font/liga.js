/* A polyfill for browsers that don't support ligatures. */
/* The script tag referring to this file must be placed before the ending body tag. */

/* To provide support for elements dynamically added, this script adds
   method 'icomoonLiga' to the window object. You can pass element references to this method.
*/
(function () {
    'use strict';
    function supportsProperty(p) {
        var prefixes = ['Webkit', 'Moz', 'O', 'ms'],
            i,
            div = document.createElement('div'),
            ret = p in div.style;
        if (!ret) {
            p = p.charAt(0).toUpperCase() + p.substr(1);
            for (i = 0; i < prefixes.length; i += 1) {
                ret = prefixes[i] + p in div.style;
                if (ret) {
                    break;
                }
            }
        }
        return ret;
    }
    var icons;
    if (!supportsProperty('fontFeatureSettings')) {
        icons = {
            'arrow-up': '&#xe94a;',
            'arrow-down': '&#xe94b;',
            'arrow-right': '&#xe94c;',
            'into': '&#xe949;',
            'asset': '&#xe900;',
            'manage-s': '&#xe947;',
            'logo-platform': '&#xe901;',
            'dashed-l': '&#xe943;',
            'dashed-line': '&#xe944;',
            'dashed-t': '&#xe945;',
            'agency': '&#xe942;',
            'refuse-refund': '&#xe940;',
            'rebate': '&#xe941;',
            'information': '&#xe93f;',
            'minus': '&#xe93b;',
            'add-o': '&#xe93c;',
            'caret-left': '&#xe93d;',
            'caret-right': '&#xe93e;',
            'refresh-o': '&#xe938;',
            'refresh-line': '&#xe939;',
            'refresh-arrow': '&#xe93a;',
            'cancel': '&#xe936;',
            'record': '&#xe937;',
            'menu': '&#xe902;',
            'logo-word': '&#xe934;',
            'sort-right': '&#xe903;',
            'refresh': '&#xe933;',
            'washer': '&#xe932;',
            'service': '&#xe904;',
            'safety': '&#xe905;',
            'diamond': '&#xe906;',
            'rent': '&#xe907;',
            'shelf': '&#xe908;',
            'category': '&#xe909;',
            'Disinfection-Cabinet': '&#xe90a;',
            'edit': '&#xe90b;',
            'credit-limit': '&#xe90c;',
            'order': '&#xe90d;',
            'publish': '&#xe90e;',
            'goods': '&#xe919;',
            'audit': '&#xe90f;',
            'preview': '&#xe910;',
            'long-arrow-up': '&#xf178;',
            'to lead': '&#xf019;',
            'eye': '&#xf06e;',
            'eye-slash': '&#xf070;',
            'sign-out': '&#xf08b;',
            'sign-in': '&#xf090;',
            'plus-circle': '&#xf055;',
            'minus-circle': '&#xf056;',
            'ban': '&#xf05e;',
            'tishi': '&#xe913;',
            'exclamation': '&#xf06a;',
            'plus': '&#xf068;',
            'clock': '&#xf017;',
            'calendr-plus': '&#xf271;',
            '': '&#xf274;',
            'calendr-check': '&#xf274;',
            'comments-o': '&#xf0e6;',
            'wechat': '&#xf1d8;',
            'sort-up': '&#xf0d8;',
            'sort-down': '&#xf0dd;',
            'user': '&#xf007;',
            'users': '&#xf0c0;',
            'star': '&#xf005;',
            'star-o': '&#xf006;',
            'check-circle': '&#xf058;',
            'qiyong': '&#xe928;',
            'close': '&#xf00d;',
            'lock': '&#xf023;',
            'unlock-alt': '&#xf13e;',
            'zengsong': '&#xe91c;',
            'logo': '&#xe92e;',
            'barcode': '&#xe92f;',
            'bullhorn': '&#xe930;',
            'database': '&#xe931;',
            'retweet': '&#xe946;',
            'wrench': '&#xe948;',
            'search': '&#xf002;',
            'cog': '&#xf013;',
            'trash': '&#xf014;',
            'inbox': '&#xf01c;',
            'tags': '&#xf02c;',
            'pront': '&#xf02f;',
            'list': '&#xf03a;',
            'edit': '&#xf044;',
            'question-circle': '&#xf059;',
            'shopping-cart': '&#xf07a;',
            'bar-chart': '&#xf080;',
            'hand-o-right': '&#xf0a4;',
            'file-text': '&#xf0f6;',
            'copy': '&#xf0c5;',
            'save': '&#xf0c7;',
            'truck': '&#xf0d1;',
            'smile-o': '&#xf118;',
            'cny': '&#xf157;',
            'calculator': '&#xf1ec;',
            'pie-chart': '&#xf200;',
          '0': 0
        };
        delete icons['0'];
        window.icomoonLiga = function (els) {
            var classes,
                el,
                i,
                innerHTML,
                key;
            els = els || document.getElementsByTagName('*');
            if (!els.length) {
                els = [els];
            }
            for (i = 0; ; i += 1) {
                el = els[i];
                if (!el) {
                    break;
                }
                classes = el.className;
                if (/ks-admin-font/.test(classes)) {
                    innerHTML = el.innerHTML;
                    if (innerHTML && innerHTML.length > 1) {
                        for (key in icons) {
                            if (icons.hasOwnProperty(key)) {
                                innerHTML = innerHTML.replace(new RegExp(key, 'g'), icons[key]);
                            }
                        }
                        el.innerHTML = innerHTML;
                    }
                }
            }
        };
        window.icomoonLiga();
    }
}());
