package net.kingsilk.qh.shop.service.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lit on 17-4-7.
 */
@Service
public  class ExcelWrite {
    static HSSFSheet putOneRowInExcel(HSSFSheet sheet, List<String> list, int rowNum) {
        HSSFRow row = sheet.createRow(rowNum);
//        int col = 0;
        for(int col=0;col<list.size();col++) {
            HSSFCell cell = row.createCell(col);
            cell.setCellValue(list.get(col));
        }
        return sheet;
    }
}
