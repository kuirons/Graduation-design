package com.bigao.backend.util;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ExportExcel2007 {
    private static Logger logger = LoggerFactory.getLogger(ExportExcel2007.class);

    /**
     * @param title    显示的导出表的标题
     * @param codes    激活码数据
     * @param paramMap 参数说明
     * @return
     * @throws Exception
     */
    public static XSSFWorkbook getWorkbook(String title, List<String> codes, List<Pair<String, String>> paramMap) throws Exception {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(); // 创建工作簿对象
            XSSFSheet sheet = workbook.createSheet(title); // 创建工作表

            // 产生表格标题行
            int nowRow = 0;
            XSSFRow titleRow = sheet.createRow(nowRow);
            XSSFCell cellTitle = titleRow.createCell(0);

            // sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面 - 可扩展】
            XSSFCellStyle columnTopStyle = getColumnTopStyle(workbook);// 获取列头样式对象

            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 4));
            cellTitle.setCellStyle(columnTopStyle);
            cellTitle.setCellValue(title);

            // 定义参数说明
            nowRow = 2;

            XSSFCellStyle titleCellStyle = titleCellStyle(workbook); // 表头样式
            XSSFCellStyle style = getStyle(workbook); // 表格数据样式
            for (int i = 0; i < paramMap.size(); i++) {
                Pair<String, String> data = paramMap.get(i);
                XSSFRow paramRow = sheet.createRow(nowRow);
                for (int k = 0; k < 4; k++) {
                    XSSFCell cell = paramRow.createCell(k);
                    cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(data.getKey());
                    cell.setCellStyle(titleCellStyle);
                }
                sheet.addMergedRegion(new CellRangeAddress(nowRow, nowRow, 0, 3));

                createCell(paramRow, data.getValue(), 4, style);
                nowRow++;
            }

            for (int i = 0; i < 5; i++) {
                sheet.setColumnWidth(i, 8000);
            }

            // 定义显示激活码行数
            int codeSize = codes.size();
            for (int k = 0; k < codeSize; k++) {
                XSSFRow codeRow = sheet.getRow(nowRow - 1);
                if (k % 5 == 0) {
                    codeRow = sheet.createRow(nowRow);
                    nowRow++;
                }
                XSSFCell cell = codeRow.createCell(k % 5, XSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(codes.get(k));
                cell.setCellStyle(style); // 设置单元格样式
            }

            return workbook;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 创建单元格并填充数据
     *
     * @param row
     * @param value
     * @param index
     * @param style
     */
    private static void createCell(XSSFRow row, String value, int index, XSSFCellStyle style) {
        XSSFCell cellRowName = row.createCell(index);
        cellRowName.setCellType(XSSFCell.CELL_TYPE_STRING);
        XSSFRichTextString text = new XSSFRichTextString(value);
        cellRowName.setCellValue(text);
        cellRowName.setCellStyle(style);
    }

    /**
     * 列头单元格样式
     *
     * @param workbook
     * @return
     */
    private static XSSFCellStyle getColumnTopStyle(XSSFWorkbook workbook) {
        // 设置字体
        XSSFCellStyle style = getStyle(workbook);
        // 设置水平对齐的样式为居中对齐;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return style;

    }

    /**
     * 列数据信息单元格样式
     *
     * @param workbook
     * @return
     */
    private static XSSFCellStyle titleCellStyle(XSSFWorkbook workbook) {
        XSSFCellStyle style = getStyle(workbook);
        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        return style;
    }

    /**
     * 列数据信息单元格样式
     *
     * @param workbook
     * @return
     */
    private static XSSFCellStyle getStyle(XSSFWorkbook workbook) {
        // 设置字体
        XSSFFont font = workbook.createFont();
        // 设置字体名字
        font.setFontName("Courier New");
        // 设置字体大小
        font.setFontHeightInPoints((short) 11);
        // 字体加粗
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 设置样式;
        XSSFCellStyle style = workbook.createCellStyle();
        // 设置底边框;
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        // 设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        // 设置左边框;
        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        // 设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        // 设置右边框;
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        // 设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        // 设置顶边框;
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        // 设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        // 在样式用应用设置的字体;
        style.setFont(font);
        // 设置自动换行;
        style.setWrapText(false);
        // 设置水平对齐的样式为左对齐;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return style;
    }


    /*public static void main(String[] args) throws Exception {
        List<Pair<String, String>> paramMap = Lists.newArrayList();
        paramMap.add(Pair.of("平台(说明：指定平台生效,-1表示无限制)", "-1"));
        paramMap.add(Pair.of("奖励Id(说明：奖励id,同样也用来当做批次id,对应q_card.q_id)", "1"));
        paramMap.add(Pair.of("是否复用(说明：1:相同reward的激活码同一个玩家仅仅能够使用一次 2:可多次使用不同id的激活码)", "1"));
        paramMap.add(Pair.of("服务器(说明：指定服务器生效,-1表示无限制)", "-1"));
        int num = 11;
        paramMap.add(Pair.of("生成的数量", String.valueOf(num)));

        List<String> codes = Lists.newArrayList();
        for (int i = 0; i < num; i++) {
            codes.add("kdkd" + i);
        }
        Workbook workbook = ExportExcel2007.getWorkbook("激活码", codes, paramMap);
        if (workbook == null) {
            return;
        }
        FileOutputStream outputStream = new FileOutputStream("D://test.xlsx");
        workbook.write(outputStream);
        outputStream.close();
        workbook.close();
    }*/
}
