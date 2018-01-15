package net.kingsilk.qh.activity.server.resource.brandApp.bargain.wap;

import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.activity.api.UniResp;
import net.kingsilk.qh.activity.api.brandApp.bargain.wap.BargainApi;
import net.kingsilk.qh.activity.domain.BargainHelpUser;
import net.kingsilk.qh.activity.domain.BargainRecord;
import net.kingsilk.qh.activity.domain.QBargainRecord;
import net.kingsilk.qh.activity.repo.BargainActivityRepo;
import net.kingsilk.qh.activity.repo.BargainHelpUserRepo;
import net.kingsilk.qh.activity.repo.BargainRecordRepo;
import net.kingsilk.qh.activity.service.service.SecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BargainResource implements BargainApi {

    @Autowired
    private SecService secService;

    @Autowired
    private BargainActivityRepo bargainActivityRepo;

    @Autowired
    private BargainHelpUserRepo bargainHelpUserRepo;

    @Autowired
    private BargainRecordRepo bargainRecordRepo;

    @Override
    public UniResp<String> bargain(String brandAppId, String activityId, String userId, String skuId) {

        String curUserId = secService.curUserId();

        BargainRecord record = bargainRecordRepo.findOne(
                Expressions.allOf(
                        QBargainRecord.bargainRecord.userId.eq(userId),
                        QBargainRecord.bargainRecord.skuId.eq(skuId),
                        QBargainRecord.bargainRecord.bargainId.eq(activityId),
                        QBargainRecord.bargainRecord.deleted.ne(true)
                )
        );

        if (bargainRecord) {
            BargainHelpUser hasBargain = bargainRecord.helpUsers.find {
                it.helpUser == curUser
            }
            if (hasBargain) {
                render([
                        code:MsgCodeEnum.ERROR.name(),
                        data:"已砍价，邀请好友进行砍价"
                ]as JSON)
                return
            }
        }
        if (bargainAward.bargain.bargainRuleType == BargainRuleTypeEnum.LADDER && bargainRecord
                && bargainRecord.helpUsers.size() == bargainAward.maxNumOfUser) {
            render([
                    code:MsgCodeEnum.ERROR.name(),
                    data:"当前已达标"
            ]as JSON)
            return
        }
        if (bargainAward.bargain.bargainRuleType == BargainRuleTypeEnum.USER_TYPE_RANDOM
                && (bargainRecord && bargainRecord.finalPrice == bargainAward.minTargetPrice)) {
            render([
                    code:MsgCodeEnum.ERROR.name(),
                    data:"当前已达标"
            ]as JSON)
            return
        }
        def isSameUser = true
        if (curUser != user) {
            isSameUser = false
        }

        if (!bargainRecord) {
            bargainRecord = new BargainRecord()
            bargainRecord.dateCreated = new Date()
            bargainRecord.bargain = bargainAward.bargain
            bargainRecord.user = user
            bargainRecord.bargainAward = bargainAward
            bargainRecord.finalPrice = bargainAward.price
        }
        Integer helpPrice
        Bargain bargain = bargainRecord.bargain
        if (bargain.bargainRuleType == BargainRuleTypeEnum.USER_TYPE_RANDOM) {
            //根据用户类型区间内随机
//            boolean isNewUser = curUser.dateCreated

            List<BargainHelpUser> helpUsers = BargainHelpUser.findAllByHelpUser(curUser)

            BargainUserTypeEnum isNewUser
            if (helpUsers.size() <= 0 || isSameUser) {
                isNewUser = BargainUserTypeEnum.NEW_USER
            } else {
                isNewUser = BargainUserTypeEnum.OLD_USER
            }

            UserTypeRandom userTypeRandom = bargain.userTypeRandom.find {
                it.userType == isNewUser
            }
            Random random = new Random()
            Integer max = userTypeRandom.higherPrice
            Integer min = userTypeRandom.lowerPrice
            if (bargainRecord.finalPrice - bargainAward.minTargetPrice > max) {
                helpPrice = Math.abs(random.nextInt(max)) % (max - min + 1) + min
            } else {
                helpPrice = bargainRecord.finalPrice - bargainAward.minTargetPrice
            }
        } else {
            // 阶梯式算法
            //先算出可以砍的价格
            Integer targetPrice = bargainAward.price - bargainAward.minTargetPrice
            //（可以砍的价格-总共多少人砍价）*2
            BigDecimal tmpPrice = (int) Math.floor(targetPrice / bargainAward.maxNumOfUser) * 2
            //计算出砍价递减梯度
            BigDecimal gradientPrice = (int) Math.floor(tmpPrice / bargainAward.maxNumOfUser)
            //当前有多少人参与砍价
            Integer helpSize = bargainRecord.helpUsers.size()

            //如果当前参加人的位置小于可砍价人数的一半，直接根据梯度计算出相应的砍价价格
            if (helpSize < (int) Math.floor(bargainAward.maxNumOfUser / 2)) {
                helpPrice = (Math.random() * gradientPrice) + (tmpPrice - ((helpSize + 1) * gradientPrice))
            } else {
                //如果当前参加人的位置大于可砍价人数的一半，用tmpPrice-对应的人所砍的价格
                Integer currentHelpSize = bargainRecord.helpUsers.size()
                if (bargainAward.maxNumOfUser % 2 == 1 && currentHelpSize == (int) Math.floor(bargainAward.maxNumOfUser / 2)) {
                    helpPrice = (int) Math.floor(targetPrice / bargainAward.maxNumOfUser)
                } else if (currentHelpSize == bargainAward.maxNumOfUser - 1) {
                    helpPrice = bargainRecord.finalPrice - bargainAward.minTargetPrice
                } else {
                    helpPrice = tmpPrice - bargainRecord.helpUsers[bargainAward.maxNumOfUser - currentHelpSize - 1].helpPrice
                }

            }
        }


        bargainRecord.finalPrice = bargainRecord.finalPrice - helpPrice
        BargainHelpUser helpUser = new BargainHelpUser()
        String ip = commonService.getClientIpAddress(request)
        helpUser.ip = ip
        helpUser.helpTime = new Date()
        helpUser.helpUser = curUser
        helpUser.helpPrice = helpPrice
        bargainRecord.helpUsers.add(helpUser)
        bargainRecord.save(flush:true)
//        Bargain bargain = bargainRecord.bargain
        bargain.participantNum += 1
        bargain.save(flush:true)
        render([
                code           :MsgCodeEnum.SUCCESS.name(),
                bargainRecordId:bargainRecord.id,
                helpPrice      :helpPrice / 100,
                isSameUser     :isSameUser,
                helpCount      :bargainRecord.helpUsers.size(),
                currentPrice   :bargainRecord.finalPrice / 100

        ]as JSON)

        return null;
    }

    @Override
    public UniResp<String> info(String brandAppId, String activityId) {
        return null;
    }

    @Override
    public UniResp<String> getHeplerList(String brandAppId) {
        return null;
    }

    @Override
    public UniResp<String> getBargainResult(String brandAppId) {
        return null;
    }

    @Override
    public UniResp<String> check(String brandAppId) {
        return null;
    }

    @Override
    public UniResp<String> create(String brandAppId) {
        return null;
    }

    @Override
    public UniResp<String> generateOrder(String brandAppId) {
        return null;
    }

    @Override
    public UniResp<String> judgeJoined(String brandAppId) {
        return null;
    }
}
