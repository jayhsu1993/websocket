package com.huixin.framework.utils;

import java.io.InputStream;
import java.text.DecimalFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

/**
 * Excel导入工具类
 * 
 * @author slg
 *
 */
public class ExcelUtils {
	private static Log _log = LogFactory.getLog(ExcelUtils.class);
	
	private static final int version2003 = 2003;
	
	private static final int version2007 = 2007;
	
	private static int version = version2003;
	
	public static Workbook readExcel(MultipartFile file) {
		Workbook wb = null;
		
		try {
			if(file.getSize() > 0){
				String fileName = file.getOriginalFilename();
				// 获取文件后缀
				String excelFilePath = fileName.substring(fileName.lastIndexOf("."));
				if (excelFilePath.endsWith(".xls")){
					version = version2003;
				}else if (excelFilePath.endsWith(".xlsx")){
					version = version2007;
				}
	      InputStream stream= file.getInputStream();
	      if (version == version2003) {
	          //stream = new FileInputStream(excelFilePath);
	          wb = (Workbook) new HSSFWorkbook(stream);
	      } else if (version == version2007) {
	          wb = new XSSFWorkbook(stream);
	      }
			} 
		} catch(Exception e) {
			_log.error("文件读取失败！！！", e);
		}
		
		return wb;
	}
	
	public static String getCellValue(Cell cell) {// 判断单元格cell的类型并且做出相应的转换
		String strCell = "";
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				strCell = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				strCell = String.valueOf(cell.getNumericCellValue());
				break;
			case Cell.CELL_TYPE_BLANK:
				strCell = "";
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				strCell = String.valueOf(cell.getBooleanCellValue());
				break;
			default:
				strCell = "";
				break;
			}
		}
		return strCell;
	}
	
	/**
	 * 强制数字类型的数据转换成字符串
	 * @param cell
	 * @return
	 */

	public static String getCellValueNumber(Cell cell) {// 判断单元格cell的类型并且做出相应的转换
		DecimalFormat dfnm = new DecimalFormat("0"); 
		String strCell = "";
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				strCell = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				strCell = dfnm.format(cell.getNumericCellValue());
				break;
			case Cell.CELL_TYPE_BLANK:
				strCell = "";
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				strCell = String.valueOf(cell.getBooleanCellValue());
				break;
			default:
				strCell = "";
				break;
			}
		}
		return strCell;
	}

}