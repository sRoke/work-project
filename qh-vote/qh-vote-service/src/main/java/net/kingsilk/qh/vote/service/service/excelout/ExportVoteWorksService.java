package net.kingsilk.qh.vote.service.service.excelout;

import com.querydsl.core.types.dsl.Expressions;
import msg.api.excelOut.voteworks.ExportVoteWorksEvent;
import net.kingsilk.qh.vote.core.vote.TaskStatusEnum;
import net.kingsilk.qh.vote.domain.QVoteWorks;
import net.kingsilk.qh.vote.domain.Task;
import net.kingsilk.qh.vote.domain.VoteWorks;
import net.kingsilk.qh.vote.repo.TaskRepo;
import net.kingsilk.qh.vote.repo.VoteRecordRepo;
import net.kingsilk.qh.vote.repo.VoteWorksRepo;
import net.kingsilk.qh.vote.service.ParamUtils;
import net.kingsilk.qh.vote.service.service.ExcelGridFDService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.List;

import static com.querydsl.core.types.dsl.Expressions.anyOf;

@Service
public class ExportVoteWorksService {


    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private ExcelGridFDService excelGridFDService;

    @Autowired
    private VoteWorksRepo voteWorksRepo;

    @Autowired
    private VoteRecordRepo voteRecordRepo;

    public void findAndExcelOut(ExportVoteWorksEvent exportVoteWorksEvent) {
        String brandAppId = exportVoteWorksEvent.getVoteAppId();
        String voteId = exportVoteWorksEvent.getActivityId();
        String taskId = exportVoteWorksEvent.getTaskId();

        Task task = taskRepo.findOne(taskId);
        List sort = task.getSort();
        String workKeyword = task.getWorkKeyword();

        // 根据 task 的类型，调用 不同的 exportXxx()

        Sort s = ParamUtils.toSort(sort);
        try {
            Thread.sleep(6 * 1000);
        } catch (Exception e) {
        }
        System.out.println("ewerwerw");
        PageRequest pageRequest = new PageRequest(0, Integer.MAX_VALUE, s); //todo 32000

        Page<VoteWorks> pageData = voteWorksRepo.findAll(
                Expressions.allOf(
//                        staff == null ? QVoteWorks.voteWorks.status.eq(VoteWorksStatusEnum.NORMAL) : null,
                        QVoteWorks.voteWorks.voteActivityId.eq(voteId),
                        QVoteWorks.voteWorks.deleted.ne(true),
                        !StringUtils.isEmpty(workKeyword) ? anyOf(
                                QVoteWorks.voteWorks.phone.eq(workKeyword),
                                QVoteWorks.voteWorks.seq.eq(workKeyword),
                                QVoteWorks.voteWorks.name.eq(workKeyword)
                        ) : null), pageRequest
        );

        List<VoteWorks> voteWorks = pageData.getContent();


        //设置头信息
        String title = "参与投票活动作品表.xlsx";
        // 输出Excel文件

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();

        XSSFRow row = sheet.createRow(0);
        row.setHeight((short) 500);
        row.createCell(0).setCellValue("排名");
        row.createCell(1).setCellValue("编号");
        row.createCell(2).setCellValue("姓名");
        row.createCell(3).setCellValue("手机号码");
        row.createCell(4).setCellValue("累计票数");
        row.createCell(5).setCellValue("报名时间");
        row.createCell(6).setCellValue("状态");

        int rowIndex = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (VoteWorks voteWork : voteWorks) {
            rowIndex++;
            row = sheet.createRow(rowIndex);
            row.setHeight((short) 400);
            row.createCell(0).setCellValue(rowIndex);
            row.createCell(1).setCellValue(voteWork.getSeq());
            row.createCell(2).setCellValue(voteWork.getName());
            row.createCell(3).setCellValue(voteWork.getPhone());
            row.createCell(4).setCellValue(voteWork.getTotalVotes());
            row.createCell(5).setCellValue(sdf.format(voteWork.getDateCreated()));
            row.createCell(6).setCellValue(voteWork.getStatus().getDesp());
        }

        //workbook 存入gridfs 中。
        boolean isSuccess = excelGridFDService.saveWorkbook(workbook, title, task);
        if (!isSuccess) {
            System.err.println(task.getFileName() + " 存入gridfs失败");
            task.setTaskStatus(TaskStatusEnum.FAIL_IMPORT);  //失败了
        } else {
            task.setTaskStatus(TaskStatusEnum.COMPLETE_IMPORT);   //成功了
        }
        taskRepo.save(task);
    }

}
