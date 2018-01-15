package net.kingsilk.qh.vote.service.service.excelout;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.Expressions;
import msg.api.excelOut.voterecords.ExportVoteRecordsEvent;
import net.kingsilk.qh.vote.core.vote.TaskStatusEnum;
import net.kingsilk.qh.vote.domain.*;
import net.kingsilk.qh.vote.repo.TaskRepo;
import net.kingsilk.qh.vote.repo.VoteRecordRepo;
import net.kingsilk.qh.vote.repo.VoteWorksRepo;
import net.kingsilk.qh.vote.service.ParamUtils;
import net.kingsilk.qh.vote.service.service.ExcelGridFDService;
import net.kingsilk.qh.vote.service.util.DbUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.querydsl.core.types.dsl.Expressions.anyOf;

@Service
public class ExportVoteRecordsService {


    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private ExcelGridFDService excelGridFDService;

    @Autowired
    private VoteWorksRepo voteWorksRepo;

    @Autowired
    private VoteRecordRepo voteRecordRepo;

    public void findAndExcelOut(ExportVoteRecordsEvent exportVoteRecordsEvent) {
        String brandAppId = exportVoteRecordsEvent.getVoteAppId();
        String voteId = exportVoteRecordsEvent.getActivityId();
        String taskId = exportVoteRecordsEvent.getTaskId();

        Task task = taskRepo.findOne(taskId);
        List sort = task.getSort();
        String voteKeyword = task.getVoteKeyword();
        String workKeyword = task.getWorkKeyword();
        // 根据 task 的类型，调用 不同的 exportXxx()


        Sort s = ParamUtils.toSort(sort);

        Pageable pageable = new PageRequest(0, Integer.MAX_VALUE, s);

        List<VoteWorks> voteWorks = null;
        if (!StringUtils.isEmpty(workKeyword)) {
            voteWorks = Lists.newArrayList(voteWorksRepo.findAll(
                    Expressions.anyOf(
                            QVoteWorks.voteWorks.seq.eq(workKeyword),
                            QVoteWorks.voteWorks.phone.eq(workKeyword)
                    )
            ));
        }
        //转换成集合
        List<String> voteWorksId = new ArrayList<>();
        if (voteWorks != null && voteWorks.size() > 0) {
            for (VoteWorks voteWork : voteWorks) {
                voteWorksId.add(voteWork.getId());
            }
        }

        Page<VoteRecord> recordPage = voteRecordRepo.findAll(
                Expressions.allOf(
                        QVoteRecord.voteRecord.voteActivityId.eq(voteId),
                        voteWorksId != null && voteWorksId.size() > 0 ?
                                DbUtil.opIn(QVoteRecord.voteRecord.voteWorksId, voteWorksId) : null,
                        !StringUtils.isEmpty(voteKeyword) ? anyOf(
                                QVoteRecord.voteRecord.voterPhone.eq(voteKeyword),
                                QVoteRecord.voteRecord.voterNickName.eq(voteKeyword)
                        ) : null
                ), pageable);

        //设置头信息
        String title = "投票活动作品的投票记录表.xlsx";

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();

        XSSFRow row = sheet.createRow(0);
        row.setHeight((short) 500);
        row.createCell(0).setCellValue("投票时间");
        row.createCell(1).setCellValue("投票人");
        row.createCell(2).setCellValue("报名人");

        int rowIndex = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (VoteRecord voteRecord : recordPage.getContent()) {
            rowIndex++;
            row = sheet.createRow(rowIndex);
            row.setHeight((short) 400);
            row.createCell(0).setCellValue(sdf.format(voteRecord.getDateCreated()));
            row.createCell(1).setCellValue(
                    voteRecord.getVoterNickName()
                            + ","
                            + voteRecord.getVoterPhone());
            VoteWorks curVoteWorks = voteWorksRepo.findOne(voteRecord.getVoteWorksId());
            row.createCell(2).setCellValue(
                    curVoteWorks.getName()
                            + ","
                            + curVoteWorks.getPhone());
        }

        //workbook 存入gridfs 中。
        boolean isSuccess = excelGridFDService.saveWorkbook(workbook, title, task);
        if ( !isSuccess) {
            System.err.println(task.getFileName() + " 存入gridfs失败");
            task.setTaskStatus(TaskStatusEnum.FAIL_IMPORT);  //失败了
        } else {
            task.setTaskStatus(TaskStatusEnum.COMPLETE_IMPORT);   //成功了
        }
        taskRepo.save(task);
    }

}
