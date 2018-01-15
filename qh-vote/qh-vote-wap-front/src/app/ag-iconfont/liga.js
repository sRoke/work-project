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
            'ticket': '&#xe913;',
            'question': '&#xe974;',
            'delete': '&#xe912;',
            'all-1': '&#xe911;',
            'sales-return': '&#xe910;',
            'add-0': '&#xe96b;',
            'dai-fa-huo': '&#xe96c;',
            'dai-fu-kuan': '&#xe96e;',
            'dai-shou-huo': '&#xe96f;',
            'express': '&#xe970;',
            'all-0': '&#xe971;',
            'rectangle-four': '&#xe972;',
            'manage-qd': '&#xe96a;',
            'arrow-up': '&#xe968;',
            'arrow-down': '&#xe969;',
            'edit-0': '&#xe900;',
            'cancel-0': '&#xe901;',
            'screen-0': '&#xe902;',
            'delete-s': '&#xe903;',
            'chukudengji': '&#xe904;',
            'chukujilu': '&#xe905;',
            'obligation': '&#xe906;',
            'receiving-w': '&#xe907;',
            'fans-mission': '&#xe908;',
            'fans-foot': '&#xe909;',
            'billboard': '&#xe90a;',
            'collect-money': '&#xe90b;',
            'inventory': '&#xe90c;',
            'rukudengji': '&#xe90d;',
            'rukujilu': '&#xe90e;',
            'manage-sp': '&#xe90f;',
            'my-fans': '&#xe914;',
            'my-partner': '&#xe915;',
            'yeji': '&#xe916;',
            'add-fans': '&#xe917;',
            'misson-yeji': '&#xe918;',
            'manage-ck': '&#xe919;',
            'manage-dp': '&#xe91a;',
            'manage-gys': '&#xe91b;',
            'default-head': '&#xe91c;',
            'address-1': '&#xe91d;',
            'my-income': '&#xe91e;',
            'my-account': '&#xe91f;',
            'buy-0': '&#xe920;',
            'manage-buy': '&#xe921;',
            'buy-return': '&#xe922;',
            'manage-fahuo': '&#xe923;',
            'manage-sale': '&#xe924;',
            'return-sale': '&#xe967;',
            'circle-right': '&#xe966;',
            'money-shou': '&#xe965;',
            'clock-circle': '&#xe964;',
            'complete': '&#xe963;',
            'authorize-o': '&#xe962;',
            'minus': '&#xe961;',
            'plus': '&#xe95c;',
            'triangle-down': '&#xe95a;',
            'triangle-up': '&#xe959;',
            'wallet-o': '&#xe958;',
            'closed-o': '&#xe955;',
            'work-o': '&#xe950;',
            'order-arrow-o': '&#xe94f;',
            'bag-o': '&#xe94e;',
            'menu-list': '&#xe94d;',
            'truck': '&#xe94c;',
            'more-word': '&#xe94b;',
            'edit-o': '&#xe94a;',
            'check-circle': '&#xe949;',
            'circle': '&#xe947;',
            'agency': '&#xe946;',
            'edit-pen': '&#xe945;',
            'money-board': '&#xe944;',
            'charge': '&#xe943;',
            'arrow_left': '&#xe942;',
            'arrow-right-s': '&#xe941;',
            'clock': '&#xe940;',
            'printer': '&#xe93f;',
            'resize': '&#xe93e;',
            'search-left': '&#xe93d;',
            'sign': '&#xe9ea;',
            'shopping_cart_minus': '&#xe93c;',
            'cart-line': '&#xe93b;',
            'arrow-r': '&#xe93a;',
            'arrow-two': '&#xe939;',
            'up-arrow': '&#xe9ee;',
            'chart_fill': '&#xe938;',
            'alignment-bottom': '&#xe937;',
            'object-alignment': '&#xe936;',
            'showcase': '&#xe935;',
            'stats-o': '&#xe933;',
            'plus-circle': '&#xe932;',
            'plus-o': '&#xe931;',
            'minus-circle': '&#xe930;',
            'closed': '&#xe9f8;',
            'right': '&#xe9f9;',
            'grid-system': '&#xe92f;',
            'massage': '&#xe9e5;',
            'notebook': '&#xe92d;',
            'script': '&#xe92c;',
            'fileboard': '&#xe92b;',
            'menbers': '&#xe9cd;',
            'members-o': '&#xe9fb;',
            'profile-round': '&#xe929;',
            'menber-add': '&#xe9cc;',
            'profile': '&#xe928;',
            'home': '&#xe927;',
            'wifi-40': '&#xe9f1;',
            'share-o': '&#xe9e9;',
            'share-s-o': '&#xe9cf;',
            'link-40': '&#xe9e4;',
            'money-40': '&#xe97d;',
            'rmb-square': '&#xe97a;',
            'wallet，balance': '&#xe99c;',
            'carton-sort': '&#xe9b6;',
            'focus-horizontal': '&#xe926;',
            'qrcode': '&#xe9ac;',
            'qr，code': '&#xe925;',
            'phone': '&#xe92a;',
            'all': '&#xe934;',
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
