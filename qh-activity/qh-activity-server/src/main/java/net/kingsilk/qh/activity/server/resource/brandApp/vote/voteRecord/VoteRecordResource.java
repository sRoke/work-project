package net.kingsilk.qh.activity.server.resource.brandApp.vote.voteRecord;

import com.mongodb.gridfs.GridFSDBFile;
import com.querydsl.core.types.dsl.Expressions;
import msg.EventPublisher;
import msg.api.excelOut.voterecords.ExportVoteRecordsEvent;
import net.kingsilk.qh.activity.api.ErrStatus;
import net.kingsilk.qh.activity.api.UniPage;
import net.kingsilk.qh.activity.api.UniResp;
import net.kingsilk.qh.activity.api.brandApp.vote.voteRecord.VoteRecordApi;
import net.kingsilk.qh.activity.api.brandApp.vote.voteRecord.dto.VoteRecordPageResp;
import net.kingsilk.qh.activity.core.vote.TaskStatusEnum;
import net.kingsilk.qh.activity.core.vote.TaskTypeEnum;
import net.kingsilk.qh.activity.domain.*;
import net.kingsilk.qh.activity.repo.TaskRepo;
import net.kingsilk.qh.activity.repo.VoteRecordRepo;
import net.kingsilk.qh.activity.repo.VoteWorksRepo;
import net.kingsilk.qh.activity.service.ParamUtils;
import net.kingsilk.qh.activity.service.service.ExcelGridFDService;
import net.kingsilk.qh.activity.service.util.DbUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.querydsl.core.types.dsl.Expressions.anyOf;
import static org.springframework.data.mongodb.gridfs.GridFsCriteria.whereFilename;

@Component
public class VoteRecordResource implements VoteRecordApi {

    @Autowired
    private VoteRecordRepo voteRecordRepo;

    @Autowired
    @Qualifier("mvcConversionService")
    private ConversionService conversionService;

    @Autowired
    private VoteWorksRepo voteWorksRepo;

    @Context
    HttpServletResponse response;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private EventPublisher eventPublisher;


    @Override
    public UniResp<UniPage<VoteRecordPageResp>> page(
            String brandAppId,
            String activityId,
            int size,
            int page,
            String voteKeyword,
            String workKeyword,
            List<String> sort) {

        Sort s = ParamUtils.toSort(sort);

        Pageable pageable = new PageRequest(page, size, s);

        Iterable<VoteWorks> voteWorks = null;
        if (!StringUtils.isEmpty(workKeyword)) {
            voteWorks = voteWorksRepo.findAll(
                    Expressions.anyOf(
                            QVoteWorks.voteWorks.seq.eq(workKeyword),
                            QVoteWorks.voteWorks.phone.eq(workKeyword)
                    )
            );
        }

        int lenth = 0;
        if (voteWorks != null) {
            for (VoteWorks voteWork : voteWorks) {
                lenth++;
            }
        }
        //转换成集合
        List<String> voteWorksId = new ArrayList<>();
        if (lenth > 0) {
            for (VoteWorks voteWork : voteWorks) {
                voteWorksId.add(voteWork.getId());
            }
        }


        Page<VoteRecord> recordPage = voteRecordRepo.findAll(
                Expressions.allOf(
                        QVoteRecord.voteRecord.voteActivityId.eq(activityId),
                        voteWorksId != null && voteWorksId.size() > 0 ?
                                DbUtil.opIn(QVoteRecord.voteRecord.voteWorksId, voteWorksId) : null,
                        !StringUtils.isEmpty(voteKeyword) ? anyOf(
                                QVoteRecord.voteRecord.voterPhone.eq(voteKeyword),
                                QVoteRecord.voteRecord.voterNickName.eq(voteKeyword)
                        ) : null
                ), pageable);

        Page<VoteRecordPageResp> respPage = recordPage.map(voteRecord -> {
            VoteRecordPageResp voteRecordPageResp = new VoteRecordPageResp();
            VoteWorks voteWork = voteWorksRepo.findOne(voteRecord.getVoteWorksId());

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            voteRecordPageResp.setVoteDate(format.format(voteRecord.getDateCreated()));
            voteRecordPageResp.setVoteName(voteRecord.getVoterNickName());
            voteRecordPageResp.setVotePhoe(voteRecord.getVoterPhone());
            voteRecordPageResp.setVoterHeaderImgUrl(voteRecord.getVoterHeaderImgUrl());

            voteRecordPageResp.setWorkName(voteWork.getName());
            voteRecordPageResp.setWorkPhone(voteWork.getPhone());
            return voteRecordPageResp;
        });

        UniPage<VoteRecordPageResp> resp = conversionService.convert(respPage, UniPage.class);
        resp.setContent(respPage.getContent());
        UniResp<UniPage<VoteRecordPageResp>> uniResp = new UniResp<>();
        uniResp.setData(resp);
        uniResp.setStatus(ErrStatus.OK);
        return uniResp;
    }


    /**
     * 根据导出操作的类型，导出唯一的一个文件（因为下载完就立即删除）
     *
     * @param brandAppId
     * @param activityId
     * @param taskTypeEnum
     * @return
     * @throws IOException
     */

    @Override
    public UniResp exportVoteRecords(
            String brandAppId,
            String activityId,
            String taskTypeEnum) throws IOException {

        Sort s = ParamUtils.toSort("dateCreated,desc");
        Pageable pageable = new PageRequest(0, 1, s);

        Page<Task> pageData = taskRepo.findAll(
                Expressions.allOf(
                        QTask.task.taskTypeEnum.eq(TaskTypeEnum.valueOf(taskTypeEnum))
                ), pageable);
        List<Task> allTask = pageData.getContent();
        if (allTask != null && !allTask.isEmpty()) {
            Task task = allTask.get(0);
            //找到对应的文件
            GridFSDBFile fsFile = gridFsTemplate.findOne(ExcelGridFDService.
                    query(whereFilename().is(task.getFileName())));

            //返回响应
            //设置头信息
            String title = "投票活动作品的投票记录表";
            // 输出Excel文件

            ServletOutputStream out = response.getOutputStream();
            response.reset();
            String fileName = new String(title.getBytes("UTF-8"), "ISO-8859-1");
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ".xlsx");
            response.setContentType("application/x-xls");
            response.setCharacterEncoding("UTF-8");

            if (fsFile == null) {
                taskRepo.delete(task);
                UniResp<Task> uniResp = new UniResp<>();
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


    @Override
    public UniResp<Task> voteRecordsToGridfs(
            String brandAppId,
            String activityId,
            List<String> sort,
            String voteKeyword,
            String workKeyword) throws IOException {

        Task task = new Task();
//        task = taskRepo.save(task);
//        task.setDateCreated(new Date());
        task.setTaskStatus(TaskStatusEnum.IN_IMPORT);
        task.setSort(sort);
        task.setVoteKeyword(voteKeyword);
        task.setWorkKeyword(workKeyword);
        task.setTaskTypeEnum(TaskTypeEnum.VOTERECORDS_TASK);
        task = taskRepo.save(task);

        ExportVoteRecordsEvent outEvent = new ExportVoteRecordsEvent();
        outEvent.setBrandAppId(brandAppId);
        outEvent.setActivityId(activityId);
        outEvent.setTaskId(task.getId());

        eventPublisher.publish(outEvent);
        UniResp<Task> uniResp = new UniResp<>();
        task.setTaskStatus(TaskStatusEnum.IN_IMPORT);
        uniResp.setStatus(ErrStatus.OK);
        uniResp.setData(task);

        return uniResp;


//        Sort s = ParamUtils.toSort(sort);
//
//        Pageable pageable = new PageRequest(0, Integer.MAX_VALUE, s);
//
//        Iterable<VoteWorks> voteWorks = null;
//        if (!StringUtils.isEmpty(workKeyword)) {
//            voteWorks = voteWorksRepo.findAll(
//                    Expressions.anyOf(
//                            QVoteWorks.voteWorks.seq.eq(workKeyword),
//                            QVoteWorks.voteWorks.phone.eq(workKeyword)
//                    )
//            );
//        }
//        int lenth = 0;
//        if (voteWorks != null) {
//            for (VoteWorks voteWork : voteWorks) {
//                lenth++;
//            }
//        }
//        //转换成集合
//        List<String> voteWorksId = new ArrayList<>();
//        if (lenth > 0) {
//            for (VoteWorks voteWork : voteWorks) {
//                voteWorksId.add(voteWork.getId());
//            }
//        }
//
//
//        Page<VoteRecord> recordPage = voteRecordRepo.findAll(
//                Expressions.allOf(
//                        QVoteRecord.voteRecord.voteActivityId.eq(activityId),
//                        voteWorksId != null && voteWorksId.size() > 0 ?
//                                DbUtil.opIn(QVoteRecord.voteRecord.voteWorksId, voteWorksId) : null,
//                        !StringUtils.isEmpty(voteKeyword) ? anyOf(
//                                QVoteRecord.voteRecord.voterPhone.eq(voteKeyword),
//                                QVoteRecord.voteRecord.voterNickName.eq(voteKeyword)
//                        ) : null
//                ), pageable);
//
//        //设置头信息
//        String title = "投票活动作品的投票记录表";
//        // 输出Excel文件
//
//        OutputStream out = response.getOutputStream();
//
//        response.reset();
//        String fileName = new String(title.getBytes("UTF-8"), "ISO-8859-1");
//        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ".xlsx");
//        response.setContentType("application/x-xls");
//        response.setCharacterEncoding("UTF-8");
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        XSSFSheet sheet = workbook.createSheet();
//
//        XSSFRow row = sheet.createRow(0);
//        row.setHeight((short) 500);
//        row.createCell(0).setCellValue("投票时间");
//        row.createCell(1).setCellValue("投票人");
//        row.createCell(2).setCellValue("报名人");
//
//        int rowIndex = 0;
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        for (VoteRecord voteRecord : recordPage.getContent()) {
//            rowIndex++;
//            row = sheet.createRow(rowIndex);
//            row.setHeight((short) 400);
//            row.createCell(0).setCellValue(sdf.format(voteRecord.getDateCreated()));
//            row.createCell(1).setCellValue(
//                    voteRecord.getVoterNickName()
//                            + ","
//                            + voteRecord.getVoterPhone());
//            VoteWorks curVoteWorks = voteWorksRepo.findOne(voteRecord.getVoteWorksId());
//            row.createCell(2).setCellValue(
//                    curVoteWorks.getName()
//                            + ","
//                            + curVoteWorks.getPhone());
//        }
//
//        workbook.write(out);
//        out.close();
//        out.flush();
//        return null;
    }
}
