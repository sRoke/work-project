package net.kingsilk.qh.shop.service.service;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.border.Border;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class DownloadModelService {

    public void downloadModel(HttpServletResponse response) {

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);

        try {
            HSSFSheet sheet = wb.createSheet();
            for (int j = 0; j < 38; j++) {
                HSSFRow row = sheet.createRow(j);
                row.setHeight((short) 550);
//                for (int i = 0; i < 9; i++) {
//                    row.createCell(i).setCellStyle(cellStyle);
//                }
            }
            HSSFRow row1 = sheet.getRow(0);
            row1.setHeight((short) 700);
            row1.createCell(0).setCellValue(new HSSFRichTextString("xx店汇总表"));
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
            row1.getCell(0).setCellStyle(cellStyle);

            String[] strings = new String[]{"产品分类", "产品名称", "产品编号", "颜色", "规格", "初库存", "吊牌价格", "采购价格", "促销价"};
            List<String> list = new ArrayList<>();
            Collections.addAll(list, strings);
            HSSFRow row2 = sheet.createRow(1);
            row2.setHeight((short) 700);
            for (int i = 0; i < 9; i++) {
                HSSFCell cell = row2.createCell(i);
                cell.setCellValue(new HSSFRichTextString(list.get(i)));
                cell.setCellStyle(cellStyle);
            }

            sheet.addMergedRegion(new CellRangeAddress(2, 37, 0, 0));
            sheet.getRow(2).createCell(0).setCellValue(new HSSFRichTextString("内衣"));

            sheet.addMergedRegion(new CellRangeAddress(2, 17, 1, 1));
            sheet.getRow(2).createCell(1).setCellValue(new HSSFRichTextString("15F101A\n" +
                    "半杯蕾丝文胸"));
//            sheet.getRow(2).createCell(1).setCellStyle(cellStyle);

            sheet.addMergedRegion(new CellRangeAddress(18, 37, 1, 1));
            sheet.getRow(18).createCell(1).setCellValue(new HSSFRichTextString("15F103A\n" +
                    "梭织光面文胸"));
//            sheet.getRow(18).createCell(1).setCellStyle(cellStyle);

            for (int k = 2; k < 38; k++) {
                String s = k < 10 ? "0"+k : "" + k;
                sheet.getRow(k).createCell(2).setCellValue(new HSSFRichTextString("QHJ00101" + s));
                sheet.getRow(k).createCell(4).setCellValue(new HSSFRichTextString("M/75B"));
                sheet.getRow(k).createCell(5).setCellValue(new HSSFRichTextString("5"));
                sheet.getRow(k).createCell(6).setCellValue(new HSSFRichTextString("20"));
                sheet.getRow(k).createCell(7).setCellValue(new HSSFRichTextString("20"));
                sheet.getRow(k).createCell(8).setCellValue(new HSSFRichTextString("20"));
            }
            sheet.addMergedRegion(new CellRangeAddress(2, 5, 3, 3));
            sheet.getRow(2).createCell(3).setCellValue(new HSSFRichTextString("紫红色"));
            sheet.addMergedRegion(new CellRangeAddress(6, 9, 3, 3));
            sheet.getRow(6).createCell(3).setCellValue(new HSSFRichTextString("洋红色"));
            sheet.addMergedRegion(new CellRangeAddress(10, 13, 3, 3));
            sheet.getRow(10).createCell(3).setCellValue(new HSSFRichTextString("宝蓝色"));
            sheet.addMergedRegion(new CellRangeAddress(14, 17, 3, 3));
            sheet.getRow(14).createCell(3).setCellValue(new HSSFRichTextString("湖蓝色"));
            sheet.addMergedRegion(new CellRangeAddress(18, 21, 3, 3));
            sheet.getRow(18).createCell(3).setCellValue(new HSSFRichTextString("香槟色"));
            sheet.addMergedRegion(new CellRangeAddress(22, 25, 3, 3));
            sheet.getRow(22).createCell(3).setCellValue(new HSSFRichTextString("大红色"));
            sheet.addMergedRegion(new CellRangeAddress(26, 29, 3, 3));
            sheet.getRow(26).createCell(3).setCellValue(new HSSFRichTextString("黑色"));
            sheet.addMergedRegion(new CellRangeAddress(30, 33, 3, 3));
            sheet.getRow(30).createCell(3).setCellValue(new HSSFRichTextString("银灰色"));
            sheet.addMergedRegion(new CellRangeAddress(34, 37, 3, 3));
            sheet.getRow(34).createCell(3).setCellValue(new HSSFRichTextString("宝蓝色"));

            for (int i = 0; i < 38 ; i++) {
                HSSFRow row = sheet.getRow(i);
                for (int j = 0; j < 9; j++) {
                    if (row.getCell(j) == null) {
                        row.createCell(j).setCellStyle(cellStyle);
                    }else {
                        row.getCell(j).setCellStyle(cellStyle);
                    }
                }
            }

            sheet.setColumnWidth(1, 256*14+184);
            sheet.setColumnWidth(2, 256*14+184);

            //返回响应
            //设置头信息
            String title = "商品导入模板.xls";
            // 输出Excel文件

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            wb.write(out);
            wb.close();

            byte[] content = out.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            // 设置response参数，可以打开下载页面
            response.reset();

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");

            String urlEncodedTitle = URLEncoder.encode(title, "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + urlEncodedTitle);
            response.setContentLength(content.length);
            ServletOutputStream outputStream = response.getOutputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            BufferedOutputStream bos = new BufferedOutputStream(outputStream);
            byte[] buff = new byte[8192];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);

            }
            bis.close();
            bos.close();
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static HSSFCellStyle initColumnHeadStyle(HSSFWorkbook wb) {
//        HSSFCellStyle columnHeadStyle = wb.createCellStyle();
//        HSSFFont columnHeadFont = wb.createFont();
//        columnHeadFont.setFontName("宋体");
//        columnHeadFont.setFontHeightInPoints((short) 10);
//        columnHeadFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//        columnHeadStyle.setFont(columnHeadFont);
//        columnHeadStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
//        columnHeadStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
//        columnHeadStyle.setLocked(true);
//        columnHeadStyle.setWrapText(true);
//        columnHeadStyle.setLeftBorderColor(HSSFColor.BLACK.index);// 左边框的颜色
//        columnHeadStyle.setBorderLeft((short) 1);// 边框的大小
//        columnHeadStyle.setRightBorderColor(HSSFColor.BLACK.index);// 右边框的颜色
//        columnHeadStyle.setBorderRight((short) 1);// 边框的大小
//        columnHeadStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单元格的边框为粗体
//        columnHeadStyle.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色
//        // 设置单元格的背景颜色（单元格的样式会覆盖列或行的样式）
//        columnHeadStyle.setFillForegroundColor(HSSFColor.WHITE.index);
//        return columnHeadStyle;
//    }
//
}
