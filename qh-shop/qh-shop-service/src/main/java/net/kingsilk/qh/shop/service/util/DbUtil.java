package net.kingsilk.qh.shop.service.util;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.SimpleExpression;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.querydsl.core.types.dsl.Expressions.allOf;
import static com.querydsl.core.types.dsl.Expressions.anyOf;

/**
 * querydsl-mongodb 中 对于生成 QXxx 代码的  in/notIn 有问题。
 * 当 后面的list的长度为1时才能正常工作，否则出错，具体请参考 MongodbSerializer 相关代码。
 * <p>
 * 提供该工具类解决此问题，使用方法可参考qh-net.kingsilk.qh.agency-wap项目中ItemController中search方法
 * 如果有多个调用追加，则可只在最后面一次调用中传入deleted参数。
 * <p>
 * QCategory.category.ne(category)该方法不能查出结果
 */
@Service
public class DbUtil {

    public static <T> BooleanExpression opIn(
            SimpleExpression<T> path,
            List<T> args
    ) {


        List<BooleanExpression> expressions = new ArrayList<>();
        for (T t : args) {
            expressions.add(path.eq(t));
        }

        return anyOf(expressions.toArray(new BooleanExpression[0]));
    }

    public static <T extends Enum<T>> BooleanExpression opIn(
            EnumPath<T> path,
            List<T> args
    ) {


        List<BooleanExpression> expressions = new ArrayList<>();
        for (T t : args) {
            expressions.add(path.eq(t));
        }

        return anyOf(expressions.toArray(new BooleanExpression[0]));
    }

    public static <T> BooleanExpression opNotIn(
            SimpleExpression<T> path,
            List<T> args
    ) {


        List<BooleanExpression> expressions = new ArrayList<>();
        for (T t : args) {
            expressions.add(path.ne(t));
        }


        return allOf(expressions.toArray(new BooleanExpression[0]));
    }

    public static <T extends Enum<T>> BooleanExpression opNotIn(
            EnumPath<T> path,
            List<T> args
    ) {


        List<BooleanExpression> expressions = new ArrayList<>();
        for (T t : args) {
            expressions.add(path.ne(t));
        }


        return allOf(expressions.toArray(new BooleanExpression[0]));
    }

}
