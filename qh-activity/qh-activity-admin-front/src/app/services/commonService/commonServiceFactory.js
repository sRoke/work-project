commonServiceFactory.$inject = [];
function commonServiceFactory() {

    var _this = {
        compareDate: compareDate,
        checkIntNum: checkIntNum,
        checkFloatNum: checkFloatNum
    };

    /**
     * 比较时间大小
     * @param startDate
     * @param endDate
     * @return
     */
    function compareDate(startDate, endDate) {
        var start = new Date(startDate).getTime();
        var end = new Date(endDate).getTime();
        if (start < end) {
            return 1;
        } else if (start > end) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * 数字验证
     * @oaram str;
     * @return
     */
    function checkIntNum(str) {

    }

    function checkFloatNum(str) {

    }

    return _this;
}

export default  commonServiceFactory;


