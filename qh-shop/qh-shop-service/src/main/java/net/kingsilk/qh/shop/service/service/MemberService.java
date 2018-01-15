package net.kingsilk.qh.shop.service.service;

import net.kingsilk.qh.shop.api.ErrStatus;
import net.kingsilk.qh.shop.api.ErrStatusException;
import net.kingsilk.qh.shop.domain.Member;
import net.kingsilk.qh.shop.service.util.PinYinUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MemberService {


    public void setOrder(Member member,String nickName){
        if (StringUtils.hasText(nickName)){
            String firstWord = nickName.substring(0,1);

            if (isEnglish(firstWord)){
                if (enbleUpCase(firstWord)){
                    firstWord = firstWord.toUpperCase();
                    char firstLetter = firstWord.charAt(0);
                    member.setOrder((int)firstLetter);
                }else {
                    char firstLetter = firstWord.charAt(0);
                    member.setOrder((int)firstLetter);
                }
                System.out.println("--------------英文-----------------");
                System.out.println(firstWord);
            }else if (isChinese(firstWord)) {
                String firstLetter = PinYinUtil.getFirstLetter(firstWord);
                firstLetter = firstLetter.toUpperCase();
                char c = firstLetter.charAt(0);
                member.setOrder((int) c);
                System.out.println("--------------中文-----------------");
                System.out.println(firstWord);
            }else if ((!isChinese(firstWord))&&(!isEnglish(firstWord))){
                    //非字母和中文的特殊字符
                    member.setOrder(-1);
                    System.out.println("-------------非字母和中文的特殊字符---------------");
            }else {
                System.out.println("--------------解析nickName出错:"+nickName);
                throw new ErrStatusException(ErrStatus.UNKNOWN,"存储member.order时解析nickName出错．");
            }
        }
        //可能存在　没有nickName 的情况
        if (member.getOrder()==null && StringUtils.isEmpty(nickName)){
            member.setOrder(50000);
        }
    }

    public boolean isPhone(String word){
        if (StringUtils.isEmpty(word)){
            return false;
        }
        String  regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern pattern = Pattern.compile(regExp);
        Matcher m =pattern.matcher(word);
        return m.matches();
    }

    public boolean isChinese(String word){
        if (StringUtils.isEmpty(word)){
            return false;
        }
        String regExp = "^[\u4e00-\u9fa5]+$";
        Pattern pattern = Pattern.compile(regExp);
        Matcher m = pattern.matcher(word);
        return m.matches();
    }

    public boolean isEnglish(String word){
        if (StringUtils.isEmpty(word)){
            return false;
        }
        String regExp = "^[A-Za-z0-9]+$";
        Pattern pattern = Pattern.compile(regExp);
        Matcher m = pattern.matcher(word);
        return m.matches();
    }

    public boolean enbleUpCase(String word){
        if (StringUtils.isEmpty(word)){
            return false;
        }
        String regExp = "^[A-Za-z]+$";
        Pattern pattern = Pattern.compile(regExp);
        Matcher m = pattern.matcher(word);
        return m.matches();

    }
}
