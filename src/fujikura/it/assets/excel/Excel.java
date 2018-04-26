/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fujikura.it.assets.excel;


import fujikura.it.assets.dao.ExcelConstructor;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Excel {


    public void ExportCount(String fileName, ArrayList<ExcelConstructor> data) {
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(fileName);
            // Ствооюємо таблицю з назвою ExcelReport
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
            XSSFSheet sheet = xssfWorkbook.createSheet("All Objects");

            // Font setting for sheet.
            XSSFFont font = xssfWorkbook.createFont();
            font.setBoldweight((short) 700);
            sheet.setDefaultColumnWidth(20);
            sheet.setDefaultRowHeight((short) 310);

            // Create Styles for sheet.
            XSSFCellStyle headerStyle = xssfWorkbook.createCellStyle();
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            //headerStyle.setFont(font);


            XSSFCellStyle headerStyleYellow = xssfWorkbook.createCellStyle();
            headerStyleYellow.setFillForegroundColor(HSSFColor.YELLOW.index);
            headerStyleYellow.setFillPattern(XSSFCellStyle.BORDER_DASHED);
            headerStyleYellow.setBorderBottom(BorderStyle.MEDIUM);
            headerStyleYellow.setBorderLeft(BorderStyle.MEDIUM);
            headerStyleYellow.setBorderRight(BorderStyle.MEDIUM);
            headerStyleYellow.setBorderTop(BorderStyle.MEDIUM);
            headerStyleYellow.setAlignment(HorizontalAlignment.CENTER);
            headerStyleYellow.setFont(font);


            XSSFCellStyle dataStyle = xssfWorkbook.createCellStyle();
            dataStyle.setWrapText(true);


            // Create First Data Row
            XSSFRow dataRow = sheet.createRow(0);
            // Write data in data row

            XSSFCell cell1 = dataRow.createCell(0);
            cell1.setCellStyle(headerStyleYellow);
            cell1.setCellValue(new XSSFRichTextString("SERIAL NUMBER"));
            XSSFCell cell2 = dataRow.createCell(1);
            cell2.setCellStyle(headerStyleYellow);
            cell2.setCellValue(new XSSFRichTextString("MODEL"));
             XSSFCell cell3 = dataRow.createCell(2);
            cell3.setCellStyle(headerStyleYellow);
            cell3.setCellValue(new XSSFRichTextString("FACTORY"));
             XSSFCell cell4 = dataRow.createCell(3);
            cell4.setCellStyle(headerStyleYellow);
            cell4.setCellValue(new XSSFRichTextString("DEPARTMENT"));
             XSSFCell cell5 = dataRow.createCell(4);
            cell5.setCellStyle(headerStyleYellow);
            cell5.setCellValue(new XSSFRichTextString("1C ID"));
             XSSFCell cell6 = dataRow.createCell(5);
            cell6.setCellStyle(headerStyleYellow);
            cell6.setCellValue(new XSSFRichTextString("SUPPLIER"));
             XSSFCell cell7 = dataRow.createCell(6);
            cell7.setCellStyle(headerStyleYellow);
            cell7.setCellValue(new XSSFRichTextString("WARRANTY"));
             XSSFCell cell8 = dataRow.createCell(7);
            cell8.setCellStyle(headerStyleYellow);
            cell8.setCellValue(new XSSFRichTextString("TIME"));
            

            for (int i = 0; i < data.size(); i++) {

                XSSFRow headerRow_0 = sheet.createRow(i + 1);

                for (int cellnum = 0; cellnum < 9; cellnum++) {
                    XSSFCell headerCell_0 = headerRow_0.createCell(cellnum);
                    headerCell_0.setCellStyle(headerStyle);

                    switch (cellnum) {

                        case 0:
                            headerCell_0.setCellValue(data.get(i).getSn());
                            break;
                        case 1:
                            headerCell_0.setCellValue(data.get(i).getMod());
                            break;
                        case 2:
                            headerCell_0.setCellValue(data.get(i).getLoc());
                            break;
                        case 3:
                            headerCell_0.setCellValue(data.get(i).getDept());
                            break;
                        case 4:
                            headerCell_0.setCellValue(data.get(i).getId_1CId());
                            break;
                        case 5:
                            headerCell_0.setCellValue(data.get(i).getSup());
                            break;
                        case 6:
                            headerCell_0.setCellValue(data.get(i).getWarranty());
                            break;
                        case 7:
                            headerCell_0.setCellValue(data.get(i).getTm());
                            break;
                    }
                }
            }
            // write in excel
            xssfWorkbook.write(fileOutputStream);
        } catch (IOException e) {
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
            }
        }
    }
}
