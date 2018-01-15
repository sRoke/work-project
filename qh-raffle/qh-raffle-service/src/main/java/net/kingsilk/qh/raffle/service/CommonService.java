package net.kingsilk.qh.raffle.service;

import groovy.transform.CompileStatic;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

/**
 * Created by zcw on 3/16/17.
 */
@Service
@CompileStatic
public class CommonService {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 创建日期搜索，精确到天
     * @param beginDate 开始时间
     * @param endDate 结束时间
     * @param key 搜索的字段
     * @param criteria
     * @return
     */
    @Deprecated
    Criteria searchDate(Date beginDate, Date endDate, String key, Criteria criteria) {
        if (key==null) {
            key = "dateCreated";
        }
        if (criteria==null) {
            criteria = new Criteria();
        }
        if (endDate!=null) {
            // 加一天
            // 添加原因：如果只根据日期搜索，结束时间是指当天的0点，即当天的数据是不符合条件的
            //TODO 当前天加1
            //endDate = endDate.plus(1);
        }
        if (beginDate!=null && endDate!=null) {
            criteria = criteria.andOperator(Criteria.where(key).gt(beginDate).lt(endDate));
        } else if (beginDate!=null) {
            criteria = criteria.where(key).gt(beginDate);
        } else if (endDate!=null) {
            criteria = criteria.where(key).lt(endDate);
        }
        return criteria;
    }

    /**
     * 创建单字段模糊匹配
     * @param content 匹配的内容
     * @param key 要匹配的字段
     * @param criteria
     * @return
     */
    @Deprecated
    Criteria regex(String content, String key, Criteria criteria) {
        if (criteria==null) {
            criteria = new Criteria();
        }
        if (key==null || content==null) {
            return criteria;
        }
        return criteria.where(key).regex(content);
    }

    /**
     * 生成基于时间的编号
     */
    public String getDateString() {
        return DateTime.now().toString("yyyyMMddHHmmss") + new Random().nextInt(1000);
    }

    /**
     * 生成随机8位邀请码
     * @return
     */
    String genRandomNum() {
        int maxNum = 36;
        int i;
        int count = 0;
        char[] str = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                      'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                      'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < 8) {
            i = Math.abs(r.nextInt(maxNum));
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }

}
