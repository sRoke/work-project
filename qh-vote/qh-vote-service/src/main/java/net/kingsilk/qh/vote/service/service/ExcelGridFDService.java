package net.kingsilk.qh.vote.service.service;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import net.kingsilk.qh.vote.api.UniResp;
import net.kingsilk.qh.vote.domain.Task;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.data.mongodb.gridfs.GridFsCriteria.whereFilename;


@Component
public class ExcelGridFDService {

    @Autowired
    private GridFsTemplate gridFsTemplate;


    public static Query query(CriteriaDefinition criteriaDefinition) {
        return new Query(criteriaDefinition);
    }


    public boolean saveWorkbook(Workbook workbook, String title,Task task){


        GridFSDBFile dbFile = gridFsTemplate.findOne(query(whereFilename().is(title)));
        String prefix = title.substring(0,title.lastIndexOf("."));
        String suffix = title.substring(title.lastIndexOf("."));
        try {

            if (dbFile != null) {
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                prefix = prefix + df.format(new Date());
            }
//        log.info("========================="+prefix+suffix);
            String fileName = prefix + suffix;

            File tempFile = File.createTempFile(prefix, suffix);
            final FileOutputStream out = new FileOutputStream(tempFile);
            workbook.write(out);
            out.close();
            GridFSFile fsFile = gridFsTemplate.store(new FileInputStream(tempFile), fileName, "application/x-xls");
            if (fsFile != null) {
                tempFile.deleteOnExit();

                task.setName(title);
                task.setFileName(fileName);
                task.setFileId(fsFile.getId().toString());
                task.setFileName(fsFile.getFilename());
                return true;
            }else {
                return false;
            }

        }catch (Exception e){
            e.printStackTrace();
            task.setDesp("导入VoteRecords失败");
            return false;
        }
    }
}
