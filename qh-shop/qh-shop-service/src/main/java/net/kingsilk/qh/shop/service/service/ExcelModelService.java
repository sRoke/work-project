package net.kingsilk.qh.shop.service.service;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import net.kingsilk.qh.shop.api.ErrStatus;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.service.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import java.io.*;

import static org.springframework.data.mongodb.gridfs.GridFsCriteria.whereFilename;

@Service
public class ExcelModelService {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Context
    HttpServletResponse response;

    public static Query query(CriteriaDefinition criteriaDefinition) {
        return new Query(criteriaDefinition);
    }

    public UniResp<String> uploadExcelModel(
            InputStream uploadedInputStream, FormDataContentDisposition fileDetail){

        UniResp<String> uniResp = new UniResp<>();
        try{
            String fileName = new String(fileDetail.getFileName().getBytes("ISO-8859-1"), "UTF-8");
            String prefix = fileName.substring(0,fileName.lastIndexOf("."));
            String suffix = fileName.substring(fileName.lastIndexOf("."));

            Workbook wb =null;
            if (ExcelUtil.OFFICE_EXCEL_2003_POSTFIX.equalsIgnoreCase(suffix)){
                wb = new HSSFWorkbook(uploadedInputStream);
            }else if (ExcelUtil.OFFICE_EXCEL_2010_POSTFIX.equalsIgnoreCase(suffix)){
                wb = new XSSFWorkbook(uploadedInputStream);
            }else {
                uniResp.setData("上传的文件格式是："+suffix+"。 这不是excel的文件格式");
                uniResp.setStatus(ErrStatus.UNKNOWN);
                return uniResp;
            }

            if (wb != null){
                File tempFile = File.createTempFile(prefix, suffix);
                OutputStream outputStream = new FileOutputStream(tempFile);
                wb.write(outputStream);
                outputStream.flush();
                outputStream.close();

                GridFSFile store = gridFsTemplate.store(new FileInputStream(tempFile), fileName, "application/x-xls");

                tempFile.deleteOnExit();
                uniResp.setData("上传成功");
                uniResp.setStatus(ErrStatus.OK);
            }


        }catch (Exception e){
            e.printStackTrace();
            uniResp.setData("上传失败");
            uniResp.setStatus(ErrStatus.UNKNOWN);
            return uniResp;
        }
        uniResp.setData("上传失败");
        uniResp.setStatus(ErrStatus.UNKNOWN);
        return uniResp;
    }


    public boolean download(){

        GridFSDBFile fsFile = gridFsTemplate.findOne(query(whereFilename().is("qh-shop商品导入模板.xlsx")));

        try{
            ServletOutputStream out = response.getOutputStream();
            response.reset();
            String fileName = new String("qh-shop商品导入模板.xlsx".getBytes("UTF-8"), "ISO-8859-1");
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ".xlsx");
            response.setContentType("application/x-xls");
            response.setCharacterEncoding("UTF-8");

            if (fsFile == null) {
               return false;
            }
            fsFile.writeTo(out);
            out.close();
            out.flush();
        }catch (Exception e){
            e.printStackTrace();
        }

        return true;
    }
}
