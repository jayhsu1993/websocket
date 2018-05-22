/**
 * 
 */
package com.huixin.framework.utils;

/**
 * @author 兴园
 *
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.util.StringUtils;

/**
 * 操作Excel表格的功能类
 */
public class ExcelReader {
    private static POIFSFileSystem fs;
    private static HSSFWorkbook wb;
    private static HSSFSheet sheet;
    private static HSSFRow row;

    /**
     * 读取Excel表格表头的内容
     * @param InputStream
     * @return String 表头内容的数组
     */
    public String[] readExcelTitle(InputStream is) {
        try {
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = wb.getSheetAt(0);
        row = sheet.getRow(0);
        // 标题总列数
        int colNum = row.getPhysicalNumberOfCells();
        System.out.println("colNum:" + colNum);
        String[] title = new String[colNum];
        for (int i = 0; i < colNum; i++) {
            //title[i] = getStringCellValue(row.getCell((short) i));
            title[i] = getCellFormatValue(row.getCell((short) i));
        }
        return title;
    }

    /**
     * 读取Excel数据内容
     * @param InputStream
     * @return Map 包含单元格数据内容的Map对象
     */
    public static List<Map<Integer, String>> readExcelContent(InputStream is, int index) {
    	List<Map<Integer, String>> content = new ArrayList<Map<Integer, String>>();
        String str = "";
        try {
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace(); 
        }
        wb.setForceFormulaRecalculation(true);
        sheet = wb.getSheetAt(index);
      
//        wb.getCreationHelper().createFormulaEvaluator().evaluateAll();
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();
        // 正文内容应该从第二行开始,第一行为表头的标题
        for (int i = 1; i <= rowNum; i++) {
            row = sheet.getRow(i);
            int j = 0;
            Map<Integer, String> data = new HashMap<Integer, String>();
            while (j < colNum) {
                // 每个单元格的数据内容用"-"分割开，以后需要时用String类的replace()方法还原数据
                // 也可以将每个单元格的数据设置到一个javabean的属性中，此时需要新建一个javabean
                // str += getStringCellValue(row.getCell((short) j)).trim() +
                // "-";
//            	System.out.println(i + ":" + j  + " : "  + row.getCell((short) j));
                str += getCellFormatValue(row.getCell((short) j)).trim() + "    ";
            	String sr = getCellFormatValue(row.getCell((short) j)).trim();
//            	System.out.println(j + " :  "  + sr.length());
            	data.put(j + 1, sr);
                j++;
            }
//            System.out.println(str);
            content.add(data);
            str = "";
        }
        return content;
    }

    /**
     * 获取单元格数据内容为字符串类型的数据
     * 
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    private String getStringCellValue(HSSFCell cell) {
        String strCell = "";
        switch (cell.getCellType()) {
        case HSSFCell.CELL_TYPE_STRING:
            strCell = cell.getStringCellValue();
            break;
        case HSSFCell.CELL_TYPE_NUMERIC:
            strCell = String.valueOf(cell.getNumericCellValue());
            break;
        case HSSFCell.CELL_TYPE_BOOLEAN:
            strCell = String.valueOf(cell.getBooleanCellValue());
            break;
        case HSSFCell.CELL_TYPE_BLANK:
            strCell = "";
            break;
        default:
            strCell = "";
            break;
        }
        if (strCell.equals("") || strCell == null) {
            return "";
        }
        if (cell == null) {
            return "";
        }
        return strCell;
    }

    /**
     * 获取单元格数据内容为日期类型的数据
     * 
     * @param cell
     *            Excel单元格
     * @return String 单元格数据内容
     */
    private String getDateCellValue(HSSFCell cell) {
        String result = "";
        try {
            int cellType = cell.getCellType();
            if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
                Date date = cell.getDateCellValue();
                result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1)
                        + "-" + date.getDate();
            } else if (cellType == HSSFCell.CELL_TYPE_STRING) {
                String date = getStringCellValue(cell);
                result = date.replaceAll("[年月]", "-").replace("日", "").trim();
            } else if (cellType == HSSFCell.CELL_TYPE_BLANK) {
                result = "";
            }
        } catch (Exception e) {
            System.out.println("日期格式不正确!");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据HSSFCell类型设置数据
     * @param cell
     * @return
     */
    private static String getCellFormatValue(HSSFCell cell) {
        String cellvalue = "";
        if (cell != null) {
//        	System.out.println(cell.getCellType());
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
            // 如果当前Cell的Type为NUMERIC
            case HSSFCell.CELL_TYPE_NUMERIC:
            case HSSFCell.CELL_TYPE_FORMULA: {
                // 判断当前的cell是否为Date
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    // 如果是Date类型则，转化为Data格式
                    
                    //方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
                    //cellvalue = cell.getDateCellValue().toLocaleString();
                    
                    //方法2：这样子的data格式是不带带时分秒的：2011-10-12
                    Date date = cell.getDateCellValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    cellvalue = sdf.format(date);
                }
                // 如果是纯数字
                else {
                    // 取得当前Cell的数值                       
                    cellvalue = String.valueOf(cell.getNumericCellValue());
//                	try {  
//                		cellvalue = String.valueOf(cell.getStringCellValue());  
//                	} catch (IllegalStateException e) {  
//                		cellvalue = String.valueOf(cell.getNumericCellValue());  
//                	}  
                }
                break;
            }
            // 如果当前Cell的Type为STRIN
            case HSSFCell.CELL_TYPE_STRING:
                // 取得当前的Cell字符串
                cellvalue = cell.getRichStringCellValue().getString();
                break;
            // 默认的Cell值
            default:
                cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }

    public static void main(String[] args) {
    	//ssgs();
    	sshy();
    }

	private static void sshy() {
		  try {
	        	String path = "D:\\所属行业07x.xls";
	            // 对读取Excel表格标题测试
//	            InputStream is = new FileInputStream(path);
//	            String[] title = excelReader.readExcelTitle(is);
//	            System.out.println("获得Excel表格的标题:");
//	            for (String s : title) {
//	                System.out.print(s + " ");
//	            }

	            // 对读取Excel表格内容测试
	            InputStream is2 = new FileInputStream(path);
	            List<Map<Integer, String>> list = ExcelReader.readExcelContent(is2, 0);
	            System.out.println("获得Excel表格的内容:" + list.size());
	            Map<String, List<String>> map = new HashMap<String, List<String>>();
	            for (int i = 0; i <= list.size(); i++) {
	            	if(i <= list.size() -1){
	            		//System.out.println(i + "  "  +list.get(i)  + " "  + list.size());
	            		String sshy = list.get(i).get(2);
	            		if(!StringUtils.isEmpty(sshy)){
	            			String hy[] = sshy.split(" -- ");
	            			System.out.println(  hy+ "  " + hy.length);
	            			for (int j = 0; j < hy.length; j++) {
	            				if( null == map.get(hy[j])){
	            					List<String> codeList  = new ArrayList<String>();
	            					codeList.add(list.get(i).get(1));
	            					map.put(hy[j], codeList);
	            				} else {
	            					List<String> codeList  = map.get(hy[j]);
	            					codeList.add(list.get(i).get(1));
	            				}
							}
	            		}
	            	}
	            }

	        } catch (FileNotFoundException e) {
	            System.out.println("未找到指定路径的文件!");
	            e.printStackTrace();
	        }
		
	}

	private static void ssgs() {
		  try {
	        	String path = "D:\\上市公司信息-整理2015-11-5-07x.xls";
	            // 对读取Excel表格标题测试
//	            InputStream is = new FileInputStream(path);
//	            String[] title = excelReader.readExcelTitle(is);
//	            System.out.println("获得Excel表格的标题:");
//	            for (String s : title) {
//	                System.out.print(s + " ");
//	            }

	            // 对读取Excel表格内容测试
	            InputStream is2 = new FileInputStream(path);
	            List<Map<Integer, String>> list = ExcelReader.readExcelContent(is2, 0);
	            System.out.println("获得Excel表格的内容:" + list.size());
	            for (int i = 0; i <= list.size(); i++) {
	            	if(i <= list.size() -1){
	            		System.out.println(i + "  "  +list.get(i)  + " "  + list.size());
	            	}
	            }

	        } catch (FileNotFoundException e) {
	            System.out.println("未找到指定路径的文件!");
	            e.printStackTrace();
	        }
		
	}
}