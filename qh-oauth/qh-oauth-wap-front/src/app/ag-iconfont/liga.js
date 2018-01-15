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
            'complete': '&#xe932;',
            'authorize-o': '&#xe92b;',
            'minus': '&#xe92c;',
            'plus': '&#xe92d;',
            'triangle-down': '&#xe92e;',
            'triangle-up': '&#xe92f;',
            'wallet-o': '&#xe930;',
            'closed-o': '&#xe931;',
            'work-o': '&#xe929;',
            'order-arrow-o': '&#xe928;',
            'bag-o': '&#xe927;',
            'menu-list': '&#xe925;',
            'truck': '&#xe926;',
            'more-word': '&#xe924;',
            'edit-o': '&#xe91f;',
            'check-circle': '&#xe922;',
            'circle': '&#xe923;',
            'agency': '&#xe91e;',
            'edit-pen': '&#xe91d;',
            'money-board': '&#xe90a;',
            'charge': '&#xe91b;',
            'arrow_left': '&#xe91c;',
            'arrow-right-s': '&#xe91a;',
            'clock': '&#xe905;',
            'printer': '&#xe910;',
            'resize': '&#xe913;',
            'search-left': '&#xe915;',
            'sign': '&#xe9ea;',
            'shopping_cart_minus': '&#xe917;',
            'cart-line': '&#xe903;',
            'arrow-r': '&#xe901;',
            'arrow-two': '&#xe902;',
            'up-arrow': '&#xe9ee;',
            'chart_fill': '&#xe904;',
            'alignment-bottom': '&#xe900;',
            'object-alignment': '&#xe90d;',
            'showcase': '&#xe918;',
            'stats-o': '&#xe919;',
            'plus-circle': '&#xe90e;',
            'plus-o': '&#xe90f;',
            'minus-circle': '&#xe90b;',
            'closed': '&#xe9f8;',
            'right': '&#xe9f9;',
            'grid-system': '&#xe908;',
            'massage': '&#xe9e5;',
            'notebook': '&#xe90c;',
            'script': '&#xe914;',
            'fileboard': '&#xe906;',
            'menbers': '&#xe9cd;',
            'members-o': '&#xe9fb;',
            'profile-round': '&#xe911;',
            'menber-add': '&#xe9cc;',
            'profile': '&#xe912;',
            'home': '&#xe909;',
            'wifi-40': '&#xe9f1;',
            'share-o': '&#xe9e9;',
            'share-s-o': '&#xe9cf;',
            'link-40': '&#xe9e4;',
            'money-40': '&#xe97d;',
            'rmb-square': '&#xe97a;',
            'wallet，balance': '&#xe99c;',
            'carton-sort': '&#xe9b6;',
            'focus-horizontal': '&#xe907;',
            'qrcode': '&#xe9ac;',
            'qr，code': '&#xe920;',
            'phone': '&#xe921;',
            'all': '&#xe92a;',
            'carton': '&#xe948;',
            'caret-left': '&#xe983;',
            'caret-right': '&#xca;',
            'location': '&#xe951;',
            'location-o': '&#xe952;',
            'card': '&#xe953;',
            'card-r': '&#xe954;',
            'success': '&#xe956;',
            'selected-o': '&#xe957;',
            'gift-o': '&#xe95b;',
            'fileboard，down，': '&#xe95d;',
            'order-close': '&#xe9b7;',
            'done': '&#xe95e;',
            'file，order，manage': '&#xe95f;',
            'file，order': '&#xe960;',
            'exclamation-circle': '&#xe96d;',
            'trash-can-o': '&#xe987;',
            'loading': '&#xe98f;',
            'alipay': '&#xe998;',
            'wechat-o': '&#xe9f0;',
            'weixin': '&#xe999;',
            'more': '&#xe99b;',
            'bank-logo-ecitic': '&#xea00;',
            'bank-logo-boc': '&#xea04;',
            'bank-logo-abchina': '&#xea05;',
            'bank-logo-ccb': '&#xea06;',
            'bank-logo-czbank': '&#xea07;',
            'bank-logo-tccb': '&#xea08;',
            'bank-logo-cib': '&#xea09;',
            'bank-logo-cmbchina': '&#xea0a;',
            'bank-logo-icbc': '&#xea0b;',
            'bank-logo-bankofshanghai': '&#xea0c;',
            'bank-logo-bankofbeijing': '&#xea0d;',
            'bank-logo-hkbea': '&#xea0e;',
            'bank-logo-hrbcb': '&#xea0f;',
            'bank-logo-hzbank': '&#xea10;',
            'bank-logo-hsbank': '&#xea11;',
            'bank-logo-bankcomm': '&#xea12;',
            'bank-logo-njcb': '&#xea13;',
            'bank-logo-nbcb': '&#xea14;',
            'bank-logo-spdb': '&#xea15;',
            'bank-logo-pingan': '&#xea16;',
            'bank-logo-cmbc': '&#xea17;',
            'bank-logo-hxb': '&#xea18;',
            'bank-logo-hebbank': '&#xea19;',
            'bank-logo-cgbchina': '&#xea1a;',
            'bank-logo-cebbank': '&#xea1b;',
            'bank-logo-qdccb': '&#xea1c;',
            'bank-logo-psbc': '&#xea1d;',
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
                if (/ks-icon/.test(classes)) {
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
