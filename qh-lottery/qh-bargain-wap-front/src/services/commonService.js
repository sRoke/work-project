angular.module('qh-bargain-wap-front').factory('commonService', [ function () {

    /**
     * 判断是否是最后一页，同样没什么卵用
     * @param curPage;当前页
     * @param pageSize;页面大小
     * @param totalCount;总记录数
     * @returns {boolean}
     */
    function isPageEnd(curPage, pageSize, totalCount) {

        if (totalCount % pageSize === 0) {
            return curPage >= totalCount / pageSize;      //避免出错，大于等于吧
        } else {
            return curPage >= (totalCount / pageSize + 1);
        }
        return true;
    }

    /**
     * 追加数据，用于分页，没什么卵用
     * @param baseArr;原数组
     * @param newArr;新获取的数据
     * @param curPage;当前页
     * @returns 总数据
     */
    function appendData(baseArr, newArr, curPage) {
        if (curPage === 1) {
            return newArr;
        } else {
            return baseArr.concat(newArr);
        }
    }

    return {
        isPageEnd: isPageEnd,
        appendData: appendData
    };

}]);