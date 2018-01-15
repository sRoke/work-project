package net.kingsilk.qh.activity.server.resource.brandApp.bargain


import net.kingsilk.qh.activity.api.UniResp;
import net.kingsilk.qh.activity.api.brandApp.bargain.BargainApi;
import net.kingsilk.qh.activity.api.brandApp.bargain.convert.BargainReq;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.text.SimpleDateFormat;

/**
 *
 */
@Component
public class BargainResource implements BargainApi {


    public UniResp<String> save(String BrandAppId, BargainReq bargainReq) {
        JSONObject json = request.getJSON()

        Assert.notNull(json.name, "活动名称不能为空.")
        Assert.hasText(json.seq, "自定义编码不能为空.")
        Assert.hasText(json.beginTime, "活动开始时间 不能为空")
        Assert.hasText(json.endTime, "活动结束时间 不能为空")
        Assert.hasText(json.rule, "活动规则说明 不能为空")
        Assert.hasText(json.desp, "活动图文不能为空")
        Assert.notNull(json.participantNum, "参与人数不能为空")
//        Assert.hasText(json.mustFollow, "活动入口不能为空")
        Assert.hasText(json.wxAuthorizerAppId, "关联公众号不能为空")
        Assert.hasText(json.brandId, "品牌不能为空")
        Assert.hasText(json.shareTitle, "分享标题不能为空")
//        Assert.hasText(json.shareLink, "分享链接不能为空")
        Assert.hasText(json.shareDesp, "分享描述页图片不能为空")
        Assert.hasText(json.shareImg, "分享图片不能为空")
        Assert.notNull(json.awards, "活动奖品列表不能为空")
        Assert.hasText(json.status, "活动状态不能为空")
        Assert.hasText(json.receiveType, "购买方式不能为空")
        Assert.notNull(json.titleImg, "活动标题不能为空！")


        Bargain
        Bargain bargain = null
        if (json.id) {
            bargain = Bargain.get(json.id as String)
            bargain.awards.clear()
        } else {
            bargain = new Bargain()
            bargain.dateCreated = new Date()
        }

        bargain.name = json.optString("name")
        bargain.seq = json.optString("seq")
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")
        bargain.beginTime = format.parseDateTime(json.optString("beginTime")).toDate()
        bargain.endTime = format.parseDateTime(json.optString("endTime")).toDate()
        bargain.desp = json.optString("desp")
        bargain.rule = json.optString("rule")
        bargain.participantNum = Integer.valueOf(json.optString("participantNum"))
        bargain.mustFollow = Boolean.valueOf(json.optString("mustFollow"))

        WxAuthorizerApp wxAuthorizerApp = WxAuthorizerApp.findById(json.optString("wxAuthorizerAppId"))
        bargain.wxAuthorizerApp = wxAuthorizerApp
        Brand brand = Brand.findById(json.optString("brandId"))
        bargain.brand = brand
        bargain.shareTitle = json.optString("shareTitle")
//        bargain.shareLink = json.optString("shareLink")
        bargain.shareDesp = json.optString("shareDesp")
        YunFile shareImg = YunFile.findByKey(json.optString("shareImg"))
        bargain.shareImg = shareImg
        bargain.shareDesp = json.optString("shareDesp")
        bargain.status = BargainStatusEnum.valueOf(json.optString("status"))
        bargain.receiveType = BargainReceiveTypeEnum.valueOf(json.optString("receiveType"))
        YunFile titleImg = YunFile.findByKey(json.optString("titleImg"))
        bargain.titleImg = titleImg

        bargain.awards = new LinkedList<BargainAward>()
        def jsonAwards = json.awards
        for (JSONObject jsonAward : jsonAwards) {
//            JSONObject jsonAward = jsonAwards.getJSONObject(title)
            String id = jsonAward.opt("awardId")
            BargainAward award
            if (id) {
                award = BargainAward.get(id)
                if (!award) {
                    award = new BargainAward()
                    award.dateCreated = new Date()
                }
                award.lastUpdated = new Date()
            } else {
                award = new BargainAward()
                award.dateCreated = new Date()
                award.lastUpdated = new Date()
            }
            award.bargain = bargain
            Assert.notNull(jsonAward.optString("name"), "商品标题不能为空")
            Assert.notNull(jsonAward.optString("num"), "商品数量不能为空")
            Assert.notNull(jsonAward.optString("price"), "商品原价不能为空")
            Assert.notNull(jsonAward.optString("awardImg"), "商品主图不能为空")
            Assert.notNull(jsonAward.optString("maxNumOfUser"), "砍价人数不能为空")
            Assert.notNull(jsonAward.optString("minTargetPrice"), "目标价格不能为空")
            Assert.notNull(jsonAward.optString("skuId"), "商品Sku不能为空")

            award.name = jsonAward.optString("name")
            award.num = jsonAward.optInt("num")
            award.price = jsonAward.optDouble("price") * 100
            YunFile awardImg = YunFile.findByKey(jsonAward.optString("awardImg"))
            award.awardImg = awardImg
            award.maxNumOfUser = jsonAward.optInt("maxNumOfUser")
            award.minTargetPrice = jsonAward.optDouble("minTargetPrice") * 100
            Sku awardSku = Sku.findById(jsonAward.optString("skuId"))
            award.sku = awardSku

            def jsonVirtualUserList = jsonAwards.virtualUserList
            if (jsonVirtualUserList) {
                if (award.virtualUserList && award.virtualUserList.size() > 0) {
                    award.virtualUserList.each {
                        User user = User.findById(it.get("userId"))
                        BargainRecord bargainRecord = BargainRecord.findByBargainAwardAndUser(award, user)
                        if (bargainRecord) {
                            bargainRecord.delete(flush:true)
                        }
                    }
                    award.virtualUserList.clear()
                } else {
                    award.virtualUserList = new ArrayList<>()
                }
                for (def jsonUser : jsonVirtualUserList[0]) {
                    String userId = jsonUser.userId
                    User user
                    if (userId) {
                        user = User.get(userId)
                        if (!user) {
                            Assert.notNull(null, "虚拟用户不存在！")
                        }
                        Integer virtualPrice = Double.valueOf(jsonUser.virtualPrice as String) * 100
//                    Date createTime = format.parseDateTime(json.optString("createTime")).toDate()
                        Map<String, String> virtualUser = new HashMap<>()
                        virtualUser.put("userId", user.id)
                        virtualUser.put("virtualPrice", virtualPrice.toString())
                        virtualUser.put("createTime", jsonUser.createTime as String)
                        award.virtualUserList.add(virtualUser)

                        BargainRecord bargainRecord = new BargainRecord()
                        bargainRecord.dateCreated = new Date()
                        bargainRecord.bargain = award.bargain
                        bargainRecord.user = user
                        bargainRecord.bargainAward = award
                        bargainRecord.finalPrice = virtualPrice
                        bargainRecord.save(flush:true)
                    }
                }
            }

            award.save(flush:true)
            bargain.awards.add(award)
        }


        bargain.save(flush:true)


        render([
                code:MsgCodeEnum.SUCCESS.name(),
                msg :"保存成功"
        ]as JSON)
    }

    //    @Secured(["isAuthenticated() && hasAnyRole('RAFFLE_DETAIL','SUPER_ADMIN')"])
    public UniResp<String> info(String BrandAppId) {
        String id = params.get("id")
        Assert.notNull(id, "参数异常")
        Bargain bargain = Bargain.findById(id)
        Assert.notNull(bargain, "活动不存在")

        def awards = []

        bargain.awards.each {
            BargainAward bargainAward ->
            Sku sku = Sku.findById(bargainAward.sku.id)
            Item item = Item.findById(sku.item.id)
            def specs = itemService.getProps(sku.specs)
            if (sku.item ?.series){
                //以下代码key作了修改，经查找，受影响的有活动管理、优惠券管理、批次管理的添加和编辑
                //有需要可以再改回来，name->specName;value->name
                //zcw 2016/06/30
                specs.add([
                        name :"产品系列",
                        value:sku.item.series.name
                    ])
            }
            bargainAward.virtualUserList.each {
                def user = User.get(it.get("userId"))
                it.put("virtualPrice", (Integer.valueOf(it.get("virtualPrice")) / 100).toString())
                it.put("phone", user ?.phone)
            }
            def award = [
            name:
            bargainAward.name,
                    num            :bargainAward.num,
                    awardId        :bargainAward.id,
                    price          :bargainAward.price / 100,
                    skuId          :sku.id,
                    itemId         :item.id,
                    itemName       :item.title,
                    skuSpec        :specs,
                    awardImg       :bargainAward.awardImg.key,
                    maxNumOfUser   :bargainAward.maxNumOfUser,
                    minTargetPrice :bargainAward.minTargetPrice / 100,
                    virtualUserList:bargainAward.virtualUserList
                ]
            awards.add(award)
        }
        render([
                code               :MsgCodeEnum.SUCCESS.name(),
                id                 :bargain.id,
                name               :bargain.name,
                seq                :bargain.seq,
                beginTime          :bargain.beginTime,
                endTime            :bargain.endTime,
                desp               :bargain.desp,
                rule               :bargain.rule,
                participantNum     :bargain.participantNum,
                mustFollow         :bargain.mustFollow,
                wxAuthorizerAppId  :bargain.wxAuthorizerApp.id,
                wxAuthorizerAppName:bargain.wxAuthorizerApp.nick_name,
                brandId            :bargain.brand.id,
                brandNameCN        :bargain.brand.nameCN,
                awards             :awards,
                shareTitle         :bargain.shareTitle,
                shareDesp          :bargain.shareDesp,
                shareImg           :bargain.shareImg.key,
                titleImg           :bargain.titleImg.key,
                receiveType        :bargain.receiveType ?.name(),
                status             :bargain.status.name()
        ]as JSON)

    }

    public UniResp<String> page(String BrandAppId) {
        def curPage = params.int('curPage', 1)
        def pageSize = params.int('pageSize', 15);
        def name = params.name;
        def status = params.status
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
        Date startDate = null;
        if (params.startDate) {
            startDate = dateFormat.parse(params.startDate)
        }
        Date endDate = null;
        if (params.endDate) {
            endDate = dateFormat.parse(params.endDate)
        }
        Map<String, Integer> searchMap = [first:
        1, pageSize:1];
        def search = {
        if (name) {
            like("name", "%${name}%");
        }
        if (status) {
            eq("status", BargainStatusEnum.valueOf(status as String));
        }
        and {
            if (endDate) {
                'le' ('dateCreated', endDate)
            }
            if (startDate) {
                'ge' ('dateCreated', startDate)
            }
        }
        'in' ('deleted', [false, null]);
        order("dateCreated", "desc")
        maxResults(searchMap.pageSize)  // 必须一致
        firstResult(searchMap.first) // 必须一致
        };
        def resultMap = commentLogService.getDomainPages(Bargain, search, searchMap, curPage, pageSize);
        def data = [];
        resultMap.list.each {
            Bargain bargain ->
            List<BargainRecord> bargainRecords = BargainRecord.findAllByBargainAndStatus(bargain, BargainRecordStatusEnum.RECEIVED)

            def orgId
            if (bargain.brandId == "56f8e97c0cf2a98fe2d4d6e0") {
                orgId = "58a127c0041ccb190f09a6ca"      //江南古韵
            } else if (bargain.brandId == "57e8b51d3213037b28092ce8") {
                orgId = "59545b702c5efb0242c1c88d"  //三月三
            } else {
                orgId = "584662c0dc8d4c2a21c37234"  //钱皇
            }

            data.add([
                    id            :bargain.id,
                    name          :bargain.name,
                    dateCreated   :bargain.dateCreated,
                    beginTime     :bargain.beginTime,
                    endTime       :bargain.endTime,
                    brandNameCN   :bargain.brand.nameCN,
                    participantNum:bargain.participantNum,
                    receivedNum   :bargainRecords.size(),
                    status        :bargain.status.name(),
                    orgId         :orgId
                ])
        }
        render([
                code      :MsgCodeEnum.SUCCESS.name(),
                totalCount:resultMap.totalCount,
                curPage   :resultMap.curPage,
                pageSize  :resultMap.pageSize,
                recList   :data
        ]as JSON)
    }


    public UniResp<String> changeStatus(String brandAppId) {
        String id = params.get("id")
        Bargain bargain = Bargain.get(id)
        String data = "启用成功"
        if (BargainStatusEnum.ENABLE != bargain.status) {
            bargain.status = BargainStatusEnum.ENABLE
        } else {
            bargain.status = BargainStatusEnum.CLOSED
            data = "关闭成功"
        }
        bargain.save()
        render([
                code:MsgCodeEnum.SUCCESS.name(),
                data:data
        ]as JSON)
    }

    public UniResp<String> delete(String brandAppId) {
        String id = params.get("id")
        Bargain bargain = Bargain.get(id)
        bargain.deleted = true
        bargain.save()
        render([
                code:MsgCodeEnum.SUCCESS.name(),
                data:"删除成功"
        ]as JSON)
    }

    public UniResp<String> getBargainRecord(String brandAppId) {
//        List<BargainRecord> bargainRecords = BargainRecord.findAll()

        def curPage = params.int('curPage', 1)
        def pageSize = params.int('pageSize', 15);
        def name = params.name;
        String phone = params.phone
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
        Date startDate = null;

        if (params.startDate) {
            startDate = dateFormat.parse(params.startDate)
        }
        Date endDate = null;
        if (params.endDate) {
            endDate = dateFormat.parse(params.endDate)
        }
        BoolFilterBuilder boolFilterBuilder = new BoolFilterBuilder()
        List<Bargain> bargain = Bargain.findAllByNameLike("%${name}%")

        def userData
        if (phone) {
//            List<BargainRecord> bargainRecords=BargainRecord.findAll()
            boolFilterBuilder.should(
//                    FilterBuilders.inFilter("userPhone",bargainRecords*.user.phone),
                    FilterBuilders.prefixFilter("userPhone", phone),
                    )
            QueryBuilder queryBuilder = QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), boolFilterBuilder)
            userData = esUserService.nextSearch(queryBuilder, curPage, 300)
        }

//        List<User> users=User.findAllByVirtualAndPhoneLike(false,"%${phone}%")
        Map<String, Integer> searchMap = [first:
        1, pageSize:1];
        def search = {
        if (name) {
            'in' ("bargain", bargain *.id)
        }
        if (userData) {
            'in' ("user", userData.recList *.id)
        }
        and {
            if (endDate) {
                'le' ('dateCreated', endDate)
            }
            if (startDate) {
                'ge' ('dateCreated', startDate)
            }
        }
        'in' ('deleted', [false, null]);
        order("dateCreated", "desc")
        maxResults(searchMap.pageSize)  // 必须一致
        firstResult(searchMap.first) // 必须一致
        };
        def bargainRecords = commentLogService.getDomainPages(BargainRecord, search, searchMap, curPage, pageSize);
        def records = []
        bargainRecords.list.each {
            BargainRecord bargainRecord ->
            User user = User.findById(bargainRecord.user.id)
            UserDetails userDetails = UserDetails.findById(user.userDetails.id)
            records.add([
                    dateCreated :bargainRecord.dateCreated,
                    userAvatar  :userDetails.avatar ?.key,
                    userNickName:userDetails.nickName,
                    userPhone   :user.phone,
                    currentPrice:bargainRecord.finalPrice / 100,
                    status      :bargainRecord.status.name(),
                    name        :bargainRecord.bargain.name
                ])
        }

        render([
                code   :MsgCodeEnum.SUCCESS.name(),
                records:records
        ]as JSON)
    }

}
