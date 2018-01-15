package net.kingsilk.qh.shop.server.controller.test;

import com.google.common.collect.Lists;
import net.kingsilk.qh.shop.service.util.ParamUtils;
import net.kingsilk.qh.shop.service.util.PinYinUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SpringBootTest
public class RegTest {
    @Test
    public static void main(String[] args) {

        ArrayList<Sort.Order> orders = Lists.newArrayList(ParamUtils.toSort("order,asc"));

        System.out.println(orders);
//        String s = "扒扒了";
//        boolean b = !isChinese(s) && !isEnglish(s);
//        System.out.println(s);
//        System.out.println(b);

//        String firstLetter = PinYinUtil.getFirstLetter(s);
//        System.out.println(firstLetter);
//        System.out.println(s);
//        System.out.println("----------------------------");
    }

    public static boolean isChinese(String word){
        if (StringUtils.isEmpty(word)){
            return false;
        }
        String regExp = "^[\u4e00-\u9fa5]+$";
        Pattern pattern = Pattern.compile(regExp);
        Matcher m = pattern.matcher(word);
        return m.matches();
    }

    public static boolean isEnglish(String word){
        if (StringUtils.isEmpty(word)){
            return false;
        }
        String regExp = "^[A-Za-z0-9]+$";
        Pattern pattern = Pattern.compile(regExp);
        Matcher m = pattern.matcher(word);
        return m.matches();
    }
}
