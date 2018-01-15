package net.kingsilk.qh.platform.service;

import com.mysema.commons.lang.Assert;
import org.springframework.data.domain.*;
import org.springframework.util.*;

import java.util.*;
import java.util.stream.*;

/**
 *
 */
public class ParamUtils {

    public static Sort.Order toOrder(String sortStr) {
        String[] strs = sortStr.split(",");
        Assert.isTrue(strs.length == 1 || strs.length == 2, "排序字符串格式正确 : `" + sortStr + "`");
        Assert.isTrue(StringUtils.hasText(strs[0]), "没有指定要排序的字段");

        Sort.Direction dir = Sort.Direction.ASC;
        if (strs.length == 2 && StringUtils.hasText(strs[1])) {
            dir = Sort.Direction.fromString(strs[1]);
        }

        return new Sort.Order(dir, strs[0]);
    }


    public static Sort toSort(String... sortStrs) {

        List<Sort.Order> orders = Arrays.stream(sortStrs)
                .filter(sortStr -> !StringUtils.isEmpty(sortStr))
                .map(sortStr -> toOrder(sortStr))
                .collect(Collectors.toList());

        if (orders.size() == 0) {
            return null;
        }
        return new Sort(orders);

    }


}
