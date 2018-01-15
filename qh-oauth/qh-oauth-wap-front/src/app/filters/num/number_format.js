// 参考 https://github.com/kvz/phpjs/blob/master/functions/strings/number_format.js
// 并修改追加参数 thousands_step
function number_format(number, decimals, dec_point, thousands_sep, thousands_step) {

    number = (number + '').replace(/[^0-9+-Ee.]/g, '');


    var n = !isFinite(+number) ? 0 : +number,
        prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
        sep = (typeof thousands_sep === 'undefined') ? '' : thousands_sep,
        dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
        s = '',
        toFixedFix = function (n, prec) {
            var k = Math.pow(10, prec);
            return '' + Math.round(n * k) / k;
        };

    var step = (typeof thousands_step === 'undefined') ? 3 : parseInt(thousands_step);
    //console.log("===="+step);
    // Fix for IE parseFloat(0.55).toFixed(0) = 0;
    s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
    if (s[0].length > step) {
        s[0] = s[0].replace(new RegExp("\\B(?=(?:\\d{" + step + "})+(?!\\d))", "g"), sep);
        //s[0] = s[0].replace(/\B(?=(?:\d{3})+(?!\d))/g, sep);
    }
    //console.log("===="+s[0]);

    if ((s[1] || '').length < prec) {
        s[1] = s[1] || '';
        s[1] += new Array(prec - s[1].length + 1).join('0');
    }
    return s.join(dec);
}

export  default number_format;
