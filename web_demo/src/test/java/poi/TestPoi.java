package poi;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.junit.Test;

public class TestPoi {

	Map<String, CellStyle> styleMap = null;

	private void createStyle(SXSSFWorkbook wb) {
		styleMap = new HashMap<String, CellStyle>();
		// 标题样式
		CellStyle style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		Font titleFont = wb.createFont();
		titleFont.setFontName("微软雅黑");
		titleFont.setFontHeightInPoints((short) 22);
		titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(titleFont);
		styleMap.put("title", style);
		
		// 报告创建时间样式
		style = wb.createCellStyle();
		Font timeFont = wb.createFont();
		timeFont.setFontName("宋体");
		timeFont.setFontHeightInPoints((short) 9);
		style.setFont(timeFont);
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		styleMap.put("time", style);

		// 状态表头样式
		style = wb.createCellStyle();
		Font statusHeaderFont = wb.createFont();
		statusHeaderFont.setFontName("宋体");
		statusHeaderFont.setFontHeightInPoints((short) 11);
		statusHeaderFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(statusHeaderFont);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		styleMap.put("statueHeader", style);

		// 状态表头样式
		style = wb.createCellStyle();
		Font statusDataFont = wb.createFont();
		statusDataFont.setFontName("微软雅黑");
		statusDataFont.setFontHeightInPoints((short) 11);
		statusDataFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(statusDataFont);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		styleMap.put("statueData", style);
		
		//项目标题
		style = wb.createCellStyle();
		Font itemTitleFont = wb.createFont();
		itemTitleFont.setFontName("微软雅黑");
		itemTitleFont.setFontHeightInPoints((short) 11);
		itemTitleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		itemTitleFont.setColor(IndexedColors.WHITE.getIndex());
		style.setFont(itemTitleFont);
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		styleMap.put("itemTitle", style);
		
		//报警项标题
		style = wb.createCellStyle();
		Font alertItemTitleFont = wb.createFont();
		alertItemTitleFont.setFontName("宋体");
		alertItemTitleFont.setFontHeightInPoints((short) 11);
		alertItemTitleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		alertItemTitleFont.setColor(IndexedColors.WHITE.getIndex());
		style.setFont(alertItemTitleFont);
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(IndexedColors.RED.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		styleMap.put("alertItemTitle", style);
		
		//建议项标题
		style = wb.createCellStyle();
		style.setFont(alertItemTitleFont);
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(IndexedColors.ROYAL_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		styleMap.put("adviceItemTitle", style);
		
		// 状态表头样式
		style = wb.createCellStyle();
		Font dataFont = wb.createFont();
		dataFont.setFontName("宋体");
		dataFont.setFontHeightInPoints((short) 9);
		style.setFont(dataFont);
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setWrapText(true);
		styleMap.put("data", style);
	}

	@Test
	public void mainTest() throws IOException {
		SXSSFWorkbook wb = new SXSSFWorkbook();
		createStyle(wb);
		
		Sheet sheet = wb.createSheet("5月份分析报告");
		int rowIndex = 0;
		int cellIndex = 0;
		//标题
		Row row = sheet.createRow(rowIndex);
		Cell cell = row.createCell(0);
		cell.setCellStyle(styleMap.get("title"));
		cell.setCellValue("服务器与软件服务运行分析报告");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 10));
		//日期
		rowIndex += 1;
		row = sheet.createRow(rowIndex);
		cell = row.createCell(0);
		cell.setCellStyle(styleMap.get("time"));
		cell.setCellValue("日期：" + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 10));
		
		//状态表头
		rowIndex += 1;
		cellIndex = 0;
		row = sheet.createRow(rowIndex);
		row.setHeightInPoints(20);
		Row secondRow = sheet.createRow(rowIndex + 1);
		secondRow.setHeightInPoints(20);
		Cell secondCell = null;
		Cell thirdCell = null;
		//服务器
		cell = row.createCell(cellIndex);
		cell.setCellStyle(styleMap.get("statueHeader"));
		secondCell = secondRow.createCell(cellIndex);
		secondCell.setCellStyle(styleMap.get("statueHeader"));
		cell.setCellValue("监控服务器总数");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));

		cellIndex += 1;
		cell = row.createCell(cellIndex);
		cell.setCellStyle(styleMap.get("statueHeader"));
		secondCell = row.createCell(cellIndex + 1);
		secondCell.setCellStyle(styleMap.get("statueHeader"));
		thirdCell = row.createCell(cellIndex + 2);
		thirdCell.setCellStyle(styleMap.get("statueHeader"));
		cell.setCellValue("可用服务器台数（22）");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 2));
		
		cell = secondRow.createCell(cellIndex);
		cell.setCellStyle(styleMap.get("statueHeader"));
		cell.setCellValue("正常");
		cellIndex += 1;
		
		cell = secondRow.createCell(cellIndex);
		cell.setCellStyle(styleMap.get("statueHeader"));
		cell.setCellValue("预警");
		cellIndex += 1;
		
		cell = secondRow.createCell(cellIndex);
		cell.setCellStyle(styleMap.get("statueHeader"));
		cell.setCellValue("报警");
		cellIndex += 1;
		
		cell = row.createCell(cellIndex);
		cell.setCellStyle(styleMap.get("statueHeader"));
		secondCell = secondRow.createCell(cellIndex);
		secondCell.setCellStyle(styleMap.get("statueHeader"));
		cell.setCellValue("不可用服务器台数");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cellIndex += 2;
		
		//软件服务
		cell = row.createCell(cellIndex);
		cell.setCellStyle(styleMap.get("statueHeader"));
		secondCell = secondRow.createCell(cellIndex);
		secondCell.setCellStyle(styleMap.get("statueHeader"));
		cell.setCellValue("软件服务总数");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cellIndex += 1;
		
		cell = row.createCell(cellIndex);
		cell.setCellStyle(styleMap.get("statueHeader"));
		secondCell = row.createCell(cellIndex + 1);
		secondCell.setCellStyle(styleMap.get("statueHeader"));
		thirdCell = row.createCell(cellIndex + 2);
		thirdCell.setCellStyle(styleMap.get("statueHeader"));
		cell.setCellValue("可用软件服务数（49）");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 2));
		
		cell = secondRow.createCell(cellIndex);
		cell.setCellStyle(styleMap.get("statueHeader"));
		cell.setCellValue("正常");
		cellIndex += 1;
		
		cell = secondRow.createCell(cellIndex);
		cell.setCellStyle(styleMap.get("statueHeader"));
		cell.setCellValue("预警");
		cellIndex += 1;
		
		cell = secondRow.createCell(cellIndex);
		cell.setCellStyle(styleMap.get("statueHeader"));
		cell.setCellValue("报警");
		cellIndex += 1;
		
		cell = row.createCell(cellIndex);
		cell.setCellStyle(styleMap.get("statueHeader"));
		secondCell = secondRow.createCell(cellIndex);
		secondCell.setCellStyle(styleMap.get("statueHeader"));
		cell.setCellValue("不可用软件服务数");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		
		rowIndex += 2;
		
		//状态数据
		cellIndex = 0;
		row = sheet.createRow(rowIndex);
		row.setHeightInPoints(20);
		//监控服务器总数
		cell = row.createCell(cellIndex);
		cell.setCellStyle(styleMap.get("statueData"));
		cell.setCellValue(24);
		cellIndex += 1;
		//正常服务器
		cell = row.createCell(cellIndex);
		cell.setCellStyle(styleMap.get("statueData"));
		cell.setCellValue(10);
		cellIndex += 1;
		//预警服务器
		cell = row.createCell(cellIndex);
		cell.setCellStyle(styleMap.get("statueData"));
		cell.setCellValue(4);
		cellIndex += 1;
		//报警服务器
		cell = row.createCell(cellIndex);
		cell.setCellStyle(styleMap.get("statueData"));
		cell.setCellValue(8);
		cellIndex += 1;
		//不可用服务器
		cell = row.createCell(cellIndex);
		cell.setCellStyle(styleMap.get("statueData"));
		cell.setCellValue(2);
		cellIndex += 2;
		//软件服务总数
		cell = row.createCell(cellIndex);
		cell.setCellStyle(styleMap.get("statueData"));
		cell.setCellValue(78);
		cellIndex += 1;
		//正常软件服务数
		cell = row.createCell(cellIndex);
		cell.setCellStyle(styleMap.get("statueData"));
		cell.setCellValue(8);
		cellIndex += 1;
		//预警软件服务数
		cell = row.createCell(cellIndex);
		cell.setCellStyle(styleMap.get("statueData"));
		cell.setCellValue(0);
		cellIndex += 1;
		//报警软件服务数
		cell = row.createCell(cellIndex);
		cell.setCellStyle(styleMap.get("statueData"));
		cell.setCellValue(41);
		cellIndex += 1;
		//不可用软件服务数
		cell = row.createCell(cellIndex);
		cell.setCellStyle(styleMap.get("statueData"));
		cell.setCellValue(29);
		
		rowIndex += 2;
		cellIndex = 0;
		//服务器标题
		row = sheet.createRow(rowIndex);
		row.setHeightInPoints(20);
		cell = row.createCell(cellIndex);
		cell.setCellStyle(styleMap.get("itemTitle"));
		cell.setCellValue("服务器");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 10));
		
		rowIndex += 1;
		row = sheet.createRow(rowIndex);
		row.setHeightInPoints(20);
		//服务器报警项标题
		cellIndex = 0;
		cell = row.createCell(cellIndex);
		cell.setCellStyle(styleMap.get("alertItemTitle"));
		cell.setCellValue("报警项");
		//服务器建议项标题
		cellIndex = 6;
		cell = row.createCell(cellIndex);
		cell.setCellStyle(styleMap.get("adviceItemTitle"));
		cell.setCellValue("建议项");
		
		//第一行服务器报警/建议数据项
		rowIndex += 1;
		row = sheet.createRow(rowIndex);
		row.setHeightInPoints(30);
		//报警项数据
		cellIndex = 0;
		cell = row.createCell(cellIndex);
		cell.setCellStyle(styleMap.get("data"));
		cell.setCellValue("1.1【通信服务】组服务器【10.10.1.1】CPU使用率达到【100%】已经持续【12】小时；");
		for (int i = 1;i <= 4; i ++) {
			secondCell = row.createCell(cellIndex + i);
			secondCell.setCellStyle(styleMap.get("data"));
		}
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 4));
		//建议项数据
		cellIndex = 6;
		cell = row.createCell(cellIndex);
		cell.setCellStyle(styleMap.get("data"));
		cell.setCellValue("1.【通信服务】组服务器【10.10.1.2】CPU平均使用率在【60%】，建议优化部署；\n2.【通信服务】组服务器已经全部属于繁忙状态，建议增加CPU资源；");
		for (int i = 1;i <= 4; i ++) {
			secondCell = row.createCell(cellIndex + i);
			secondCell.setCellStyle(styleMap.get("data"));
		}
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 4));
		
		//第二行服务器报警/建议数据项
		rowIndex += 1;
		//报警项数据
		cellIndex = 0;
		row = sheet.createRow(rowIndex);
		row.setHeightInPoints(30);
		cell = row.createCell(cellIndex);
		cell.setCellStyle(styleMap.get("data"));
		cell.setCellValue("2.1【缓存服务】组服务器【10.10.2.1】内存使用率达到【100%】已经持续【20】小时；");
		for (int i = 1;i <= 4; i ++) {
			secondCell = row.createCell(cellIndex + i);
			secondCell.setCellStyle(styleMap.get("data"));
		}
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 4));
		//建议项数据
		cellIndex = 6;
		cell = row.createCell(cellIndex);
		cell.setCellStyle(styleMap.get("data"));
		cell.setCellValue("1.【缓存服务】组服务器【10.10.2.2】内存平均使用率在【50%】，建议优化部署；\n2.【缓存服务】组服务器已经全部属于繁忙状态，建议增加内存资源；");
		for (int i = 1;i <= 4; i ++) {
			secondCell = row.createCell(cellIndex + i);
			secondCell.setCellStyle(styleMap.get("data"));
		}
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 4));
		
		//设置列宽
        sheet.setColumnWidth(0, 4100);
        sheet.setColumnWidth(1, 3500);
        sheet.setColumnWidth(2, 3500);
        sheet.setColumnWidth(3, 3500);
        sheet.setColumnWidth(4, 4300);
        sheet.setColumnWidth(5, 400);
        sheet.setColumnWidth(6, 4000);
        sheet.setColumnWidth(7, 3500);
        sheet.setColumnWidth(8, 3500);
        sheet.setColumnWidth(9, 3500);
        sheet.setColumnWidth(10, 4300);
		
		sheet = wb.createSheet("颜色");
		for (int i = 0; i < 80; i++) {
			row = sheet.createRow(i);
			cell = row.createCell(0);
			CellStyle style = wb.createCellStyle();
			style.setFillForegroundColor((short)i);
	        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			cell.setCellStyle(style);
			cell.setCellValue(i);
			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		}

		File file = new File("/var/ftp/pub/test.xlsx");
		OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
		wb.write(out);
		wb.dispose();
		out.flush();
		out.close();
	}

}
