package net.kingsilk.qh.shop.service.service;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import net.kingsilk.qh.shop.service.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.gridfs.GridFsCriteria.whereFilename;


@Component
public class ExcelGridFDService {

    @Autowired
    private GridFsTemplate gridFsTemplate;


    public static Query query(CriteriaDefinition criteriaDefinition) {
        return new Query(criteriaDefinition);
    }

    public GridFSFile saveWorkbook(
            InputStream uploadedInputStream,
            FormDataContentDisposition fileDetail){
        try {
            String title = new String(fileDetail.getFileName().getBytes("ISO-8859-1"), "UTF-8");
            //检查是否已经存有名为title的文件
            String prefix = title.substring(0,title.lastIndexOf("."));
            String suffix = title.substring(title.lastIndexOf("."));
            GridFSDBFile dbFile = gridFsTemplate.findOne(query(whereFilename().is(title)));
            if (dbFile != null) {
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                prefix = prefix + df.format(new Date());
            }
//        log.info("========================="+prefix+suffix);
            //要保存的文件名
            String fileName = prefix + suffix;
            GridFSFile fsFile = gridFsTemplate.store(uploadedInputStream, fileName, null,fileDetail);
            return fsFile;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
