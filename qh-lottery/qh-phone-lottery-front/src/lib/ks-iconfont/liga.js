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
            'OK': '&#xe900;',
            'Disinfection-Cabinet': '&#xe901;',
            'category': '&#xe90b;',
            'mix-o': '&#xe903;',
            'mix': '&#xe904;',
            'silk-o': '&#xe905;',
            'silk': '&#xe906;',
            'duvet-o': '&#xe907;',
            'duvet': '&#xe908;',
            'leather-o': '&#xe909;',
            'leather': '&#xe90a;',
            'clothes-o': '&#xe929;',
            'clothes': '&#xe92a;',
            'Store-Service': '&#xe92b;',
            'store': '&#xe92c;',
            'key-o': '&#xe92f;',
            'Wi-Fi-o': '&#xe92d;',
            'money-o': '&#xe92e;',
            'time-o': '&#xe90c;',
            'acarid-o': '&#xe902;',
            'acarid': '&#xe90d;',
            'dry-o': '&#xe90e;',
            'dry': '&#xe90f;',
            'sterilization-o': '&#xe910;',
            'sterilization': '&#xe911;',
            'credit-card-o': '&#xe918;',
            'credit-card': '&#xe924;',
            'wallet': '&#xe922;',
            'wallet-o': '&#xe930;',
            'angle-left': '&#xe931;',
            'arrow-right': '&#xe932;',
            'angle-right': '&#xe933;',
            'minus-circle': '&#xe936;',
            'times-circle': '&#xe934;',
            'remove': '&#xe935;',
            'camera-plus': '&#xe912;',
            'camera-o': '&#xe913;',
            'circle-o': '&#xe923;',
            'check-circle-o': '&#xe914;',
            'map-marker': '&#xe915;',
            'crosshairs': '&#xe916;',
            'download': '&#xe917;',
            'share': '&#xe919;',
            'envelope': '&#xe91a;',
            'commenting': '&#xe91b;',
            'logo': '&#xe937;',
            'plus': '&#xe938;',
            'minus': '&#xe939;',
            'edit': '&#xe93a;',
            'truck': '&#xe93d;',
            'gear': '&#xe93b;',
            'phone': '&#xe93c;',
            'chain': '&#xe91c;',
            'user': '&#xe91d;',
            'scanning': '&#xe91e;',
            'clock': '&#xe91f;',
            'signal-tower': '&#xe920;',
            'wechat': '&#xe921;',
            'alipay': '&#xe925;',
            'qrcode': '&#xe926;',
            'question-circle': '&#xe927;',
            'navicon': '&#xe928;',
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
                if (/lq-icon/.test(classes)) {
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
