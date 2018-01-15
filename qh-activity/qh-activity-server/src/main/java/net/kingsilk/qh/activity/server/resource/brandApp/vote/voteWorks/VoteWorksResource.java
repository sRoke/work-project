package net.kingsilk.qh.activity.server.resource.brandApp.vote.voteWorks;


import com.google.common.collect.Lists;
import com.mongodb.gridfs.GridFSDBFile;
import com.querydsl.core.types.dsl.Expressions;
import msg.EventPublisher;
import msg.api.excelOut.voteworks.ExportVoteWorksEvent;
import net.kingsilk.qh.activity.api.ErrStatus;
import net.kingsilk.qh.activity.api.UniPage;
import net.kingsilk.qh.activity.api.UniResp;
import net.kingsilk.qh.activity.api.brandApp.vote.voteWorks.VoteWorksApi;
import net.kingsilk.qh.activity.api.brandApp.vote.voteWorks.dto.VoteWorksResp;
import net.kingsilk.qh.activity.api.brandApp.vote.voteWorks.dto.WxSendReq;
import net.kingsilk.qh.activity.core.vote.TaskStatusEnum;
import net.kingsilk.qh.activity.core.vote.TaskTypeEnum;
import net.kingsilk.qh.activity.core.vote.VoteSoureEnum;
import net.kingsilk.qh.activity.core.vote.VoteWorksStatusEnum;
import net.kingsilk.qh.activity.domain.*;
import net.kingsilk.qh.activity.repo.StaffRepo;
import net.kingsilk.qh.activity.repo.TaskRepo;
import net.kingsilk.qh.activity.repo.VoteActivityRepo;
import net.kingsilk.qh.activity.repo.VoteWorksRepo;
import net.kingsilk.qh.activity.server.resource.brandApp.vote.voteWorks.convert.VoteWorksConvert;
import net.kingsilk.qh.activity.service.ParamUtils;
import net.kingsilk.qh.activity.service.QhActivityProperties;
import net.kingsilk.qh.activity.service.service.ExcelGridFDService;
import net.kingsilk.qh.activity.service.service.SecService;
import net.kingsilk.wx4j.broker.api.wxCom.mp.at.WxComMpAtApi;
import net.kingsilk.wx4j.client.mp.api.tplMsg.SendReq;
import net.kingsilk.wx4j.client.mp.api.tplMsg.TplMsgApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.querydsl.core.types.dsl.Expressions.allOf;
import static com.querydsl.core.types.dsl.Expressions.anyOf;
import static org.springframework.data.mongodb.gridfs.GridFsCriteria.whereFilename;

@Component
public class VoteWorksResource implements VoteWorksApi {

    @Autowired
    private VoteWorksRepo voteWorksRepo;

    @Autowired
    private VoteWorksConvert voteWorksConvert;

    @Autowired
    @Qualifier("mvcConversionService")
    private ConversionService conversionService;

    @Autowired
    private SecService secService;

    @Autowired
    private VoteActivityRepo voteActivityRepo;

    @Autowired
    private StaffRepo staffRepo;


    @Context
    HttpServletResponse response;

    @Autowired
    private TplMsgApi tplMsgApi;

    @Autowired
    private WxComMpAtApi wxComMpAtApi;

    @Autowired
    private QhActivityProperties qhActivityProperties;


    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private EventPublisher eventPublisher;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    /**
     * 审核
     */
    @Override

    public UniResp<String> check(String brandAppId, String activityId, String id, WxSendReq wxSendReq) {

        Assert.notNull(brandAppId, "brandApp不能为空");
        Assert.notNull(id, "作品id不能为空");
        VoteWorks curvoteWorks = voteWorksRepo.findOne(id);
        VoteActivity voteActivity = voteActivityRepo.findOne(
                allOf(QVoteActivity.voteActivity.id.eq(activityId),
                        QVoteActivity.voteActivity.brandAppId.eq(brandAppId)));

        //做一些时间处理
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
        String beginTime = simpleDateFormat.format(voteActivity.getSignUpStartTime());
        String endTime = simpleDateFormat.format(voteActivity.getVoteEndTime());
        String voteTime = simpleDateFormat.format(voteActivity.getVoteStartTime());

        //审核通过，做排名初始化。
        Iterable<VoteWorks> voteWorksAll = voteWorksRepo.findAll(
                Expressions.allOf(
                        QVoteWorks.voteWorks.voteActivityId.eq(activityId),
                        QVoteWorks.voteWorks.status.eq(VoteWorksStatusEnum.NORMAL),
                        QVoteWorks.voteWorks.deleted.ne(true)
                ));
        int lenth = 0;
        for (VoteWorks voteWorks : voteWorksAll) {
            lenth++;
        }
        curvoteWorks.setRank(lenth + 1);

        if (wxSendReq.getStatus()) {
            curvoteWorks.setStatus(VoteWorksStatusEnum.NORMAL);
            //todo 消息推送
            net.kingsilk.wx4j.broker.api.UniResp<net.kingsilk.wx4j.broker.api.wxCom.mp.at.GetResp> respUniResp =
                    wxComMpAtApi.get(wxSendReq.getWxComAppId(), wxSendReq.getWxMpAppId());
            SendReq sendReq = new SendReq();
            sendReq.setTouser(curvoteWorks.getOpenId());
            sendReq.setUrl(wxSendReq.getUrl());
            sendReq.setTemplate_id(qhActivityProperties.getWx4jUt().getTplId());
            Map<String, SendReq.Val> data = new LinkedHashMap<>();
            SendReq.Val first = new SendReq.Val();
            first.setValue("亲，你参加的投票活动审核通过啦!");
            first.setColor("#743A3A");
            SendReq.Val keyword1 = new SendReq.Val();
            keyword1.setValue(voteActivity.getActivityName());
            keyword1.setColor("#743A3A");
            SendReq.Val keyword2 = new SendReq.Val();
            keyword2.setValue(beginTime + "~" + endTime);
            keyword2.setColor("#743A3A");
            SendReq.Val remark = new SendReq.Val();
            remark.setValue("投票将在" + voteTime + "开始," + endTime + "结束,记得去分享拉票哦！");
            remark.setColor("#743A3A");
            data.put("first", first);
            data.put("keyword1", keyword1);
            data.put("keyword2", keyword2);
            data.put("remark", remark);
            sendReq.setData(data);
            tplMsgApi.send(respUniResp.getData().getAccessToken(), sendReq);
            //统计参与人数 审核通过加一
            voteActivity.setTotalJoinPeople(voteActivity.getTotalJoinPeople() + 1);
            voteActivityRepo.save(voteActivity);
        } else {
            curvoteWorks.setStatus(VoteWorksStatusEnum.REJECT);
            curvoteWorks.setRank(Integer.MAX_VALUE);
        }
        UniResp uniResp = new UniResp();
        voteWorksRepo.save(curvoteWorks);
        uniResp.setStatus(200);
        return uniResp;
    }

    /**
     * 分页查询
     */
    @Override
    public UniResp<UniPage<VoteWorksResp>> page(

            String brandAppId, String activityId, int size, int page, List<String> sort, String keyWord) {

        Assert.notNull(brandAppId, "brandApp不能为空");

        Sort s = ParamUtils.toSort(sort);

        PageRequest pageRequest = new PageRequest(page, size, s);

        Page<VoteWorks> pageData = voteWorksRepo.findAll(
                Expressions.allOf(
                        QVoteWorks.voteWorks.voteActivityId.eq(activityId),
                        QVoteWorks.voteWorks.deleted.ne(true),
                        !StringUtils.isEmpty(keyWord) ? anyOf(
                                QVoteWorks.voteWorks.phone.eq(keyWord),
                                QVoteWorks.voteWorks.seq.eq(keyWord),
                                QVoteWorks.voteWorks.name.eq(keyWord)
                        ) : null), pageRequest
        );

        Page<VoteWorksResp> respPage = pageData.map(voteWorks -> {
            VoteWorksResp voteWorksResp = new VoteWorksResp();
            voteWorksResp.setWorksImgUrl(voteWorks.getWorksImgUrl());
            voteWorksResp.setName(voteWorks.getName());
            voteWorksResp.setTotalVotes(voteWorks.getTotalVotes());
            voteWorksResp.setSeq(voteWorks.getSeq());
            voteWorksResp.setId(voteWorks.getId());
            voteWorksResp.setDateCreated(voteWorks.getDateCreated());
            voteWorksResp.setPhone(voteWorks.getPhone());
            voteWorksResp.setStatus(voteWorks.getStatus());
            if (voteWorks.getRank() != null) {
                voteWorksResp.setRank(voteWorks.getRank());
            }
            return voteWorksResp;
        });
        UniPage<VoteWorksResp> resp = conversionService.convert(respPage, UniPage.class);
        resp.setContent(respPage.getContent());

        UniResp<UniPage<VoteWorksResp>> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(resp);

        return uniResp;
    }

    /**
     * 加票
     */
    @Override


    public UniResp<String> addVote(String brandAppId, String activityId, String id, String votes, String sourceType) {


        UniResp<String> uniResp = new UniResp<>();

        if (StringUtils.isEmpty(brandAppId) || StringUtils.isEmpty(id) ||
                StringUtils.isEmpty(sourceType) || StringUtils.isEmpty(activityId)) {
            uniResp.setData("brandAppId 、activityId 和 id 都不能为空");
            uniResp.setStatus(ErrStatus.VARIABLEERROR);
            return uniResp;
        }

        Integer voteNum = Integer.parseInt(votes);
        if (voteNum < 0) {
            uniResp.setData("票数不能是负数");
            uniResp.setStatus(ErrStatus.VARIABLEERROR);
            return uniResp;
        }

        VoteWorks curVoteWorks = voteWorksRepo.findOne(id);
        VoteActivity voteActivity = voteActivityRepo.findOne(
                allOf(
                        QVoteActivity.voteActivity.id.eq(activityId),
                        QVoteActivity.voteActivity.brandAppId.eq(brandAppId)));

        if (VoteSoureEnum.VIRTUAL.equals(VoteSoureEnum.valueOf(sourceType))) {
            //todo 排名控制
            //查处票数在 (getVirtualVotes() + voteNum) > num >= getVirtualVotes()
            Iterable<VoteWorks> voteWorksAll = voteWorksRepo.findAll(
                    Expressions.allOf(
                            QVoteWorks.voteWorks.voteActivityId.eq(activityId),
                            QVoteWorks.voteWorks.totalVotes.goe(curVoteWorks.getTotalVotes()),
                            QVoteWorks.voteWorks.totalVotes.lt(curVoteWorks.getTotalVotes() + voteNum),
                            QVoteWorks.voteWorks.status.eq(VoteWorksStatusEnum.NORMAL),
                            QVoteWorks.voteWorks.deleted.ne(true)
                    )
            );

            int lenth = 0;   // 记录是否重票
            int min = Integer.MAX_VALUE;    //记录最小排名数
            int curRank = 0;
            curRank = curVoteWorks.getRank();
            for (VoteWorks voteWorks : voteWorksAll) {
                lenth++;
            }
            if (lenth > 1) {
                for (VoteWorks voteWorks : voteWorksAll) {
                    int rank = voteWorks.getRank();
                    min = rank < min ? rank : min;
                }

                for (VoteWorks VoteWorks : voteWorksAll) {
                    if (VoteWorks.getRank() < curRank) {
                        VoteWorks.setRank(VoteWorks.getRank() + 1);
                    }
                    voteWorksRepo.save(VoteWorks);
                }
                //对当前加票的作品 重新设置排名
                curVoteWorks.setRank(min);
                voteWorksRepo.save(curVoteWorks);
                ;
            }

            //加后台票
            curVoteWorks.setTotalVotes(curVoteWorks.getTotalVotes() + voteNum);
            curVoteWorks.setVirtualVotes(curVoteWorks.getVirtualVotes() + voteNum);
//          voteWorks.setOrder(voteWorks.getOrder() - voteNum);
            voteWorksRepo.save(curVoteWorks);
        } else {
            uniResp.setData("票的来源只有VIRTUAL");
            uniResp.setStatus(ErrStatus.VARIABLEERROR);
            return uniResp;
        }
        //活动统计票数
        voteActivity.setTotalVote(voteActivity.getTotalVote() + voteNum);
        voteActivityRepo.save(voteActivity);

        uniResp.setStatus(200);
        return uniResp;
    }

    /**
     * 作品详情
     */
    @Override

    public UniResp<VoteWorksResp> info(String brandAppId, String activityId, String id) {

        String userId = secService.curUserId();
        Staff staff = null;
        if (userId != null) {
            staff = staffRepo.findOne(
                    QStaff.staff.userId.eq(userId));
        }

        UniResp<VoteWorksResp> uniResp = new UniResp<>();
        VoteWorks voteWorks = voteWorksRepo.findOne(
                allOf(
                        staff == null ? QVoteWorks.voteWorks.status.ne(VoteWorksStatusEnum.REJECT) : null,
                        QVoteWorks.voteWorks.deleted.ne(true),
                        QVoteWorks.voteWorks.id.eq(id)
                )
        );
        Assert.notNull(voteWorks, "该作品可能已被拒绝或删除");
        List<String> sort = new ArrayList<>();
        sort.add("totalVotes,desc");
        sort.add("lastVoteTime,asc");
        Sort s = ParamUtils.toSort(sort);
        List<VoteWorks> list = voteWorksRepo.findAll();
        PageRequest pageRequest = new PageRequest(0, list.size(), s);
        Page<VoteWorks> page = voteWorksRepo.findAll(
                allOf(
                        QVoteWorks.voteWorks.voteActivityId.eq(activityId),
                        QVoteWorks.voteWorks.status.eq(VoteWorksStatusEnum.NORMAL),
                        QVoteWorks.voteWorks.deleted.ne(true)
                ), pageRequest);

        VoteWorksResp voteWorksResp = voteWorksConvert.RespConvert(voteWorks);

        //排名和总票数
        voteWorksResp.setRanking(page.getContent().indexOf(voteWorks) + 1);
        Integer i = page.getContent().indexOf(voteWorks);
        if (i > 0) {
            Integer num = page.getContent().get(i - 1).getTotalVotes();
            voteWorksResp.setLessVoteNum(num - voteWorks.getTotalVotes());
        }
        uniResp.setData(voteWorksResp);
        uniResp.setStatus(200);
        return uniResp;
    }

    /**
     * 假删
     */
    @Override

    public UniResp<Void> delete(String brandAppId, String activityId, String id) {


        VoteWorks voteWorks = voteWorksRepo.findOne(id);
        voteWorks.setDeleted(true);
        voteWorksRepo.save(voteWorks);

        //参与人数统计
        VoteActivity voteActivity = voteActivityRepo.findOne(
                allOf(QVoteActivity.voteActivity.brandAppId.eq(brandAppId),
                        QVoteActivity.voteActivity.id.eq(activityId))
        );
        if (voteActivity.getTotalJoinPeople() > 0) {
            voteActivity.setTotalJoinPeople(voteActivity.getTotalJoinPeople() - 1);
        }

        //对排名重新排序
        PageRequest pageRequest = new PageRequest(0, 2000);
        Page<VoteWorks> pageData = voteWorksRepo.findAll(
                Expressions.allOf(
                        QVoteWorks.voteWorks.voteActivityId.eq(activityId),
                        QVoteWorks.voteWorks.status.eq(VoteWorksStatusEnum.NORMAL),
                        QVoteWorks.voteWorks.deleted.ne(true),
                        QVoteWorks.voteWorks.rank.gt(voteWorks.getRank())
                ), pageRequest
        );
        for (VoteWorks voteWork : pageData.getContent()) {
            voteWork.setRank(voteWork.getRank() - 1);
            voteWorksRepo.save(voteWork);
        }

        UniResp<Void> uniResp = new UniResp<>();
        uniResp.setStatus(ErrStatus.OK);
        return uniResp;
    }

    @Override
    public UniResp voteWorksToGridsf(
            String brandAppId,
            String activityId,
            List<String> sort,
            String workKeyword) throws IOException {

        Task task = new Task();
        task.setDateCreated(new Date());
        task.setTaskStatus(TaskStatusEnum.IN_IMPORT);
        task.setSort(sort);
        task.setWorkKeyword(workKeyword);
        task.setTaskTypeEnum(TaskTypeEnum.VOTEWORKS_TASK);
        task = taskRepo.save(task);

        ExportVoteWorksEvent outEvent = new ExportVoteWorksEvent();
        outEvent.setBrandAppId(brandAppId);
        outEvent.setActivityId(activityId);
        outEvent.setTaskId(task.getId());
        taskRepo.save(task);

        eventPublisher.publish(outEvent);
        UniResp<Task> uniResp = new UniResp<>();
        task.setTaskStatus(TaskStatusEnum.IN_IMPORT);
        uniResp.setStatus(ErrStatus.OK);
        uniResp.setData(task);
        return uniResp;
    }

    @Override
    public UniResp exportVoteworks(
            String brandAppId,
            String activityId,
            String taskTypeEnum) throws IOException {

        Sort s = ParamUtils.toSort("dateCreated,desc");
        Pageable pageable = new PageRequest(0, 1, s);
        ArrayList<Task> allTask = Lists.newArrayList(taskRepo.findAll(
                Expressions.allOf(
                        QTask.task.taskTypeEnum.eq(TaskTypeEnum.valueOf(taskTypeEnum))
                ), pageable
        ));
        if (allTask != null && !allTask.isEmpty()) {
            Task task = allTask.get(0);
            //找到对应的文件
            GridFSDBFile fsFile = gridFsTemplate.findOne(ExcelGridFDService.
                    query(whereFilename().is(task.getFileName())));

            //返回响应
            //设置头信息
            String title = "投票活动作品列表";
            // 输出Excel文件

            ServletOutputStream out = response.getOutputStream();
            response.reset();
            ;
            String fileName = new String(title.getBytes("UTF-8"), "ISO-8859-1");
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ".xlsx");
            response.setContentType("application/x-xls");
            response.setCharacterEncoding("UTF-8");

            if (fsFile == null) {
                taskRepo.delete(task);
                UniResp<Task> uniResp = new UniResp<>();
                ;
                uniResp.setException("任务已经生产，但文件文件未能生成。");
                return uniResp;
            }

            fsFile.writeTo(out);
            out.close();
            out.flush();

            //成功下载，删除
            taskRepo.delete(task);
            gridFsTemplate.delete(ExcelGridFDService.
                    query(whereFilename().is(task)));
            return null;
        }

        //返回数据封装
        UniResp<Task> uniResp = new UniResp<>();
        Task task = new Task();
        uniResp.setStatus(ErrStatus.OK);
        task.setTaskStatus(TaskStatusEnum.IN_IMPORT);
        uniResp.setData(task);
        return uniResp;
    }

//        Sort s = ParamUtils.toSort(sort);
//
//        PageRequest pageRequest = new PageRequest(0, Integer.MAX_VALUE, s); //todo 32000
//
//        Page<VoteWorks> pageData = voteWorksRepo.findAll(
//                Expressions.allOf(
////                        staff == null ? QVoteWorks.voteWorks.status.eq(VoteWorksStatusEnum.NORMAL) : null,
//                        QVoteWorks.voteWorks.voteActivityId.eq(activityId),
//                        QVoteWorks.voteWorks.deleted.ne(true),
//                        !StringUtils.isEmpty(workKeyword) ? anyOf(
//                                QVoteWorks.voteWorks.phone.eq(workKeyword),
//                                QVoteWorks.voteWorks.seq.eq(workKeyword),
//                                QVoteWorks.voteWorks.name.eq(workKeyword)
//                        ) : null), pageRequest
//        );
//
//        List<VoteWorks> voteWorks = pageData.getContent();
//
//        //设置头信息
//        String title = "参与投票活动作品表";
//        // 输出Excel文件
//
//        OutputStream out = response.getOutputStream();
//
//        response.reset();
//        String fileName = new String(title.getBytes("UTF-8"), "ISO-8859-1");
//        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ".xls");
//        response.setContentType("application/x-xls");
//        response.setCharacterEncoding("UTF-8");
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        XSSFSheet sheet = workbook.createSheet();
//
//        XSSFRow row = sheet.createRow(0);
//        row.setHeight((short) 500);
//        row.createCell(0).setCellValue("排名");
//        row.createCell(1).setCellValue("编号");
//        row.createCell(2).setCellValue("姓名");
//        row.createCell(3).setCellValue("手机号码");
//        row.createCell(4).setCellValue("累计票数");
//        row.createCell(5).setCellValue("报名时间");
//        row.createCell(6).setCellValue("状态");
//
//        int rowIndex = 0;
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        for (VoteWorks voteWork : voteWorks) {
//            rowIndex++;
//            row = sheet.createRow(rowIndex);
//            row.setHeight((short) 400);
//            row.createCell(0).setCellValue(rowIndex);
//            row.createCell(1).setCellValue(voteWork.getSeq());
//            row.createCell(2).setCellValue(voteWork.getName());
//            row.createCell(3).setCellValue(voteWork.getPhone());
//            row.createCell(4).setCellValue(voteWork.getTotalVotes());
//            row.createCell(5).setCellValue(sdf.format(voteWork.getDateCreated()));
//            row.createCell(6).setCellValue(voteWork.getStatus().getDesp());
//        }
//
//        workbook.write(out);
//        out.close();
//        out.flush();
}




