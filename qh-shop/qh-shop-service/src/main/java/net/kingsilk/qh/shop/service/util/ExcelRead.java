package net.kingsilk.qh.shop.service.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *仅使用于 商品的导入模板
 */
@Service
public class ExcelRead {
    int totalRows; //sheet中总行数
    static int totalCells; //每一行总单元格数
    /**
     * read the Excel .xlsx,.xls 
     * @param file jsp中的上传文件 
     * @return
     * @throws IOException
     */
    List<ArrayList<String>> readExcel(MultipartFile file) throws IOException {
        if (file == null || ExcelUtil.EMPTY == file.getOriginalFilename().trim()) {
            return null;
        } else {
            String postfix = ExcelUtil.getPostfix(file.getOriginalFilename());
            if (ExcelUtil.EMPTY != postfix) {
                if (ExcelUtil.OFFICE_EXCEL_2003_POSTFIX == postfix) {
                    return readXls(file);
                } else if (ExcelUtil.OFFICE_EXCEL_2010_POSTFIX == postfix) {
                    return readXlsx(file);
                } else {
                    return null;
                }
            }
        }
        return null;
    }
    /**
     * read the Excel 2010 .xlsx 
     * @param file
     * @param beanclazz
     * @param titleExist
     * @return
     * @throws IOException
     */
    @SuppressWarnings("deprecation")
    List<ArrayList<String>> readXlsx(MultipartFile file) {
        List<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        // IO流读取文件  
        InputStream input = null;
        XSSFWorkbook wb = null;
        ArrayList<String> rowList = null;
        try {
            input = file.getInputStream();
            // 创建文档  
            wb = new XSSFWorkbook(input);
            //读取sheet(页)  
            for (int numSheet = 0; numSheet < wb.getNumberOfSheets(); numSheet++) {
                XSSFSheet xssfSheet = wb.getSheetAt(numSheet);
                if (xssfSheet == null) {
                    continue;
                }
                totalRows = xssfSheet.getLastRowNum();
                totalCells = xssfSheet.getLastRowNum();
                //判断是否是 有9列的数据（一共有两种数据格式，9列和10列）
                if (totalCells == 9) {
                    //读取Row,从第三行开始
                    String cell0="";
                    String cell1="";
                    String cell3="";

                    //第一列数据 标记
                    if (xssfSheet.getRow(2).getCell(0) != null){
                        cell0 = xssfSheet.getRow(2).getCell(0).toString();
                    }else{
                        System.out.println("第三行第一列数据不能为空");
                        return null;
                    }
                    //第二列数据 标记
                    if (xssfSheet.getRow(2).getCell(1) != null){
                        cell1 = xssfSheet.getRow(2).getCell(1).toString();
                    }else{
                        return null;
                    }
                    //第四列数据 标记
                    if (xssfSheet.getRow(2).getCell(3) != null) {
                        cell3 = xssfSheet.getRow(2).getCell(3).toString();
                    }
                    //第四列数据 标记
                    if (xssfSheet.getRow(2).getCell(4) != null){
                        String cell4 = xssfSheet.getRow(2).getCell(4).toString();
                    }else{
                        return null;
                    }
                    for (int rowNum = 2; rowNum <= totalRows; rowNum++) {        //1
                        XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                        if (xssfRow != null) {
                            rowList = new ArrayList<String>();
                            //                        totalCells = xssfRow.getLastCellNum();
                            //读取列，从第一列开始
                            for (int c = 0; c <= totalCells + 1; c++) {
                                XSSFCell cell = xssfRow.getCell(c);
                                if (cell == null && c != 0 && c != 1 && c != 3 && c != 4) {
                                    rowList.add(ExcelUtil.EMPTY);
                                    continue;
                                }else if(cell == null && c == 0){
                                    rowList.add(cell0);
                                }else if(cell == null && c == 1){
                                    rowList.add(cell1);
                                }else if(cell == null && c == 3){
                                    rowList.add(cell3);
                                }
                                rowList.add(ExcelUtil.getXValue(cell).trim());
                            }
                        }
                    }
                } else if(totalCells == 10){
                    String cell0="";
                    String cell1="";
                    String cell3="";
                    String cell4="";
                    //第一列数据 标记
                    if (xssfSheet.getRow(2).getCell(0) != null){
                        cell0 = xssfSheet.getRow(2).getCell(0).toString();
                    }else{
                        System.out.println("第三行第一列数据不能为空");
                        return null;
                    }
                    //第二列数据 标记
                    if (xssfSheet.getRow(2).getCell(1) != null){
                        cell1 = xssfSheet.getRow(2).getCell(1).toString();
                    }else{
                        return null;
                    }
                    //第四列数据 标记
                    if (xssfSheet.getRow(2).getCell(3) != null){
                        cell3 = xssfSheet.getRow(2).getCell(3).toString();
                    }else{
                        return null;
                    }
                    //第五列数据 标记
                    if (xssfSheet.getRow(2).getCell(4) != null){
                        cell3 = xssfSheet.getRow(2).getCell(4).toString();
                    }else{
                        return null;
                    }
                    for (int rowNum = 2; rowNum <= totalRows; rowNum++) {        //1
                        XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                        if (xssfRow != null) {
                            rowList = new ArrayList<String>();
                            //                        totalCells = xssfRow.getLastCellNum();
                            //读取列，从第一列开始
                            for (int c = 0; c <= totalCells + 1; c++) {
                                XSSFCell cell = xssfRow.getCell(c);
                                if (cell == null && c != 0 && c != 1 && c != 3) {
                                    rowList.add(ExcelUtil.EMPTY);
                                    continue;
                                }else if(cell == null && c == 0){
                                    rowList.add(cell0);
                                }else if(cell == null && c == 1){
                                    rowList.add(cell1);
                                }else if(cell == null && c == 3){
                                    rowList.add(cell3);
                                }else if(cell == null && c == 4){
                                    rowList.add(cell4);
                                }
                                rowList.add(ExcelUtil.getXValue(cell).trim());
                            }
                        }
                    }
                }else{
                    System.out.println("数据格式不是9列或10列，格式不对");
                }
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }
    /**
     * read the Excel 2003-2007 .xls 
     * @param file
     * @param beanclazz
     * @param titleExist
     * @return
     * @throws IOException
     */
    List<ArrayList<String>> readXls(MultipartFile file) {
        List<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        // IO流读取文件
        InputStream input = null;
        HSSFWorkbook wb = null;
        ArrayList<String> rowList = null;
        try {
            input = file.getInputStream();
            // 创建文档
            wb = new HSSFWorkbook(input);
            //读取sheet(页)
            for (int numSheet = 0; numSheet < wb.getNumberOfSheets(); numSheet++) {
                HSSFSheet hssfSheet = wb.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }
                totalRows = hssfSheet.getLastRowNum();
                totalCells = hssfSheet.getLastRowNum();
                //判断是否是 有9列的数据（一共有两种数据格式，9列和10列）
                if (totalCells == 9) {
                    //读取Row,从第三行开始
                    String cell0="";
                    String cell1="";
                    String cell3="";;

                    //第一列数据 标记
                    if (hssfSheet.getRow(2).getCell(0) != null){
                        cell0 = hssfSheet.getRow(2).getCell(0).toString();
                    }else{
                        System.out.println("第三行第一列数据不能为空");
                        return null;
                    }
                    //第二列数据 标记
                    if (hssfSheet.getRow(2).getCell(1) != null){
                        cell1 = hssfSheet.getRow(2).getCell(1).toString();
                    }else{
                        return null;
                    }
                    //第四列数据 标记
                    if (hssfSheet.getRow(2).getCell(3) != null) {
                        cell3 = hssfSheet.getRow(2).getCell(3).toString();
                    }
                    //第四列数据 标记
                    if (hssfSheet.getRow(2).getCell(4) != null){
                        String cell4 = hssfSheet.getRow(2).getCell(4).toString();
                    }else{
                        return null;
                    }
                    for (int rowNum = 2; rowNum <= totalRows; rowNum++) {        //1
                        HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                        if (hssfRow != null) {
                            rowList = new ArrayList<String>();
                            //                        totalCells = xssfRow.getLastCellNum();
                            //读取列，从第一列开始
                            for (int c = 0; c <= totalCells + 1; c++) {
                                HSSFCell cell = hssfRow.getCell(c);
                                if (cell == null && c != 0 && c != 1 && c != 3 && c != 4) {
                                    rowList.add(ExcelUtil.EMPTY);
                                    continue;
                                }else if(cell == null && c == 0){
                                    rowList.add(cell0);;
                                }else if(cell == null && c == 1){
                                    rowList.add(cell1);
                                }else if(cell == null && c == 3){
                                    rowList.add(cell3);
                                }
                                rowList.add(ExcelUtil.getHValue(cell).trim());
                            }
                        }
                    }
                } else if(totalCells == 10){
                    String cell0="";
                    String cell1="";
                    String cell3="";;
                    String cell4="";
                    //第一列数据 标记
                    if (hssfSheet.getRow(2).getCell(0) != null){
                        cell0 = hssfSheet.getRow(2).getCell(0).toString();
                    }else{
                        System.out.println("第三行第一列数据不能为空");
                        return null;
                    }
                    //第二列数据 标记
                    if (hssfSheet.getRow(2).getCell(1) != null){
                        cell1 = hssfSheet.getRow(2).getCell(1).toString();
                    }else{
                        return null;
                    }
                    //第四列数据 标记
                    if (hssfSheet.getRow(2).getCell(3) != null){
                        cell3 = hssfSheet.getRow(2).getCell(3).toString();
                    }else{
                        return null;
                    }
                    //第五列数据 标记
                    if (hssfSheet.getRow(2).getCell(4) != null){
                        cell3 = hssfSheet.getRow(2).getCell(4).toString();
                    }else{
                        return null;
                    }
                    for (int rowNum = 2; rowNum <= totalRows; rowNum++) {        //1
                        HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                        if (hssfRow != null) {
                            rowList = new ArrayList<String>();
                            //                        totalCells = xssfRow.getLastCellNum();
                            //读取列，从第一列开始
                            for (int c = 0; c <= totalCells + 1; c++) {
                                HSSFCell cell = hssfRow.getCell(c);
                                if (cell == null && c != 0 && c != 1 && c != 3) {
                                    rowList.add(ExcelUtil.EMPTY);
                                    continue;
                                }else if(cell == null && c == 0){
                                    rowList.add(cell0);;
                                }else if(cell == null && c == 1){
                                    rowList.add(cell1);
                                }else if(cell == null && c == 3){
                                    rowList.add(cell3);;
                                }else if(cell == null && c == 4){
                                    rowList.add(cell4);
                                }
                                rowList.add(ExcelUtil.getHValue(cell).trim());
                            }
                        }
                    }
                }else{
                    System.out.println("数据格式不是9列或10列，格式不对");
                }
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

//    List<ArrayList<String>> readXls(MultipartFile file) {
//        List<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
//        // IO流读取文件
//        InputStream input = null;
//        HSSFWorkbook wb = null;
//        ArrayList<String> rowList = null;
//        try {
//            input = file.getInputStream();
//            // 创建文档
//            wb = new HSSFWorkbook(input);
//            //读取sheet(页)
//            for (int numSheet = 0; numSheet < wb.getNumberOfSheets(); numSheet++) {
//                HSSFSheet hssfSheet = wb.getSheetAt(numSheet);
//                if (hssfSheet == null) {
//                    continue;
//                }
//                totalRows = hssfSheet.getLastRowNum();
//                //读取Row,从第二行开始
//                for (int rowNum = 1; rowNum <= totalRows; rowNum++) {
//                    HSSFRow hssfRow = hssfSheet.getRow(rowNum);
//                    if (hssfRow != null) {
//                        rowList = new ArrayList<String>();
//                        totalCells = hssfRow.getLastCellNum();
//                        //读取列，从第一列开始
//                        for (short c = 0; c <= totalCells + 1; c++) {
//                            HSSFCell cell = hssfRow.getCell(c);
//                            if (cell == null) {
//                                rowList.add(ExcelUtil.EMPTY);
//                                continue;
//                            }
//                            rowList.add(ExcelUtil.getHValue(cell).trim());
//                        }
//                        list.add(rowList);
//                    }
//                }
//            }
//            return list;
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                input.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
}  
