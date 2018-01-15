package net.kingsilk.qh.agency.util

import java.time.ZoneId
import java.time.ZonedDateTime

/**
 * Created by yanfq on 17-4-11.
 */
class DateUtil {
    static Date dateToStartDateTime(Date date){
        //example：2017-4-11  to 2017-4-11 0:0:0:0
        return Date.from(
                ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
                        .withHour(0)
                        .withMinute(0)
                        .withSecond(0)
                        .withNano(0)
                        .toInstant()
        )
    }

    static Date dateToEndDateTime(Date date){
        //example：2017-4-11  to 2017-4-11 23:59:59:99999
        return Date.from(
                ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
                        .withHour(23)
                        .withMinute(59)
                        .withSecond(59)
                        .withNano(10**9 - 1)
                        .toInstant()
        )
    }
}
