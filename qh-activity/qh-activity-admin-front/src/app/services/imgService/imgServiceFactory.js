import conf from "../../conf";


imgFactory.$inject = [];
function imgFactory() {
    /**
     * 主页轮播图图片
     */
    function slideImg() {
        var dpr = window.devicePixelRatio;
        var containerWidth = document.body.clientWidth;
        var img = {
            w: parseInt((containerWidth > 640 ? 640 : containerWidth) * dpr)
        };

        img.h = parseInt(img.w / 2);

        return img;
    }

    /**
     * 主页热搜
     */
    function hotImg() {
        var dpr = window.devicePixelRatio / 2;
        var containerWidth = document.body.clientWidth;
        var img = {
            w: parseInt((containerWidth > 640 ? 640 : containerWidth) * dpr)
        };

        img.h = parseInt(img.w / 2 * 3); //高度等于宽度除2成3
        return img;
    }

    /**
     * 主页下面的图片
     */
    function indexBelowImg() {
        var dpr = window.devicePixelRatio;
        var containerWidth = document.body.clientWidth;
        var img = {
            w: parseInt((containerWidth > 640 ? 640 : containerWidth) * dpr)
        };

        img.h = parseInt(img.w / 3 * 2);
        return img;
    }

    /**
     * 商品主图类图片
     * @returns {{w: Number}}
     */
    function itemImg() {
        var dpr = window.devicePixelRatio;
        var containerWidth = document.body.clientWidth;
        var img = {
            w: parseInt((containerWidth > 1200 ? 1200 : containerWidth) * dpr)
        };
        img.h = parseInt(img.w);

        return img;
    }

    // 缩略图类,购物车,用户头像
    function simpleImg() {
        var dpr = window.devicePixelRatio;
        var containerWidth = document.body.clientWidth;

        var img = {
            w: parseInt((containerWidth > 200 ? 200 : containerWidth) * dpr)
        };
        //img.h = img.w;
        img.h = parseInt(img.w);
        return img;
    }

    // 缩略图类,购物车,用户头像
    function minSizeImg() {
        var dpr = window.devicePixelRatio;
        var containerWidth = document.body.clientWidth;

        var img = {
            w: parseInt((containerWidth > 50 ? 50 : containerWidth) * dpr)
        };
        //img.h = img.w;
        img.h = parseInt(img.w);
        return img;
    }

    /**
     * 根据url获取图片地址
     * @param url 图片的url
     * @param w 图片的宽
     * @param h 图片的高
     * @param params 图片自定义参数
     */
    function imgUrl(url, w, h, params) {
        if (!url) {
            return;
        }
        // {{vm.appConfig.imgUrl}}{{s.img}}?imageView2/2/w/{{vm.imgService.slideImg.w}}/h/{{vm.imgService.slideImg.h}}
        if (url.indexOf("http") > -1) {
            return url;
        }
        url = conf.imgUrl + url;
        if (w && h) {
            url = url + "?imageView2/2/w/" + w + "/h/" + h;
        }
        if (params) {
            url = url + params;
        }
        return url;
    }

    return {
        hotImg: hotImg(),
        slideImg: slideImg(),
        indexBelowImg: indexBelowImg(),
        itemImg: itemImg(),
        simpleImg: simpleImg(),
        minSizeImg: minSizeImg(),
        imgUrl: imgUrl
    };
}

export default imgFactory;

