package net.kingsilk.qh.agency.server.service

import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.springframework.stereotype.Service

/**
 * Created by lit on 17-4-7.
 */
@Service
class ExcelWrite {
    static HSSFSheet putOneRowInExcel(HSSFSheet sheet, List<String> list, int rowNum) {
        HSSFRow row = sheet.createRow(rowNum);
        int col = 0
        list.each {
            HSSFCell cell = row.createCell(col);
            cell.setCellValue(it)
            col++
        }
        return sheet;
    }
}
