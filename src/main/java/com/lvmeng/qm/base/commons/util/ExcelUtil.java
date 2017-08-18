package com.lvmeng.qm.base.commons.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.lvmeng.qm.base.vo.BaseQn;
import com.lvmeng.qm.base.vo.questionnaire.Questionnaire;

public class ExcelUtil {
	public static List<Questionnaire> toVO(InputStream file) throws Exception{
		HSSFWorkbook workbook = null;
		List<Questionnaire> list = new ArrayList<>();
		try {
			workbook = new HSSFWorkbook(file);
			if (workbook != null && workbook.getNumberOfSheets() > 0) {
				HSSFSheet sheet = workbook.getSheetAt(0);
				if (sheet != null && sheet.getLastRowNum() > 1) {
					int rows = sheet.getLastRowNum();
					for (int i = 1; i < rows; i++) {
						HSSFRow row = sheet.getRow(i);
						if (row == null) {
							continue;
						}
						int cells = row.getLastCellNum();
						Questionnaire qn = new Questionnaire();
						List<String> baseInfo = new ArrayList<>();
						List<String> questionnaire = new ArrayList<>();
						for (int j = 0; j < cells; j++) {
							HSSFCell cell = row.getCell(j);
							String value = getStringCellValue(cell);
							if (j <= 41) {
								baseInfo.add(value);
								setQnFieldsValue(qn, j, value);
							}else {
								questionnaire.add(value);
							}
						}
						qn.setBaseInfo(baseInfo);
						qn.setQuestionnaire(questionnaire);
						list.add(qn);
					}
				}else {
					throw new Exception("第一个sheet没有数据");
				}
			}else {
				throw new Exception("工作簿不存在或工作簿是空的");
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		return list;
	}

	private static void setQnFieldsValue(Questionnaire qn, int j, String value) {
		switch (j) {
		case 0:
			qn.setQnId(intValueOf(value));
			break;
		case 1:
			qn.setPanelId(intValueOf(value));
			break;
		case 2:
			qn.setStartTime(value);
			break;
		case 3:
			qn.setEndTime(value);
			break;
		case 16:
			qn.setCustomerName(value);
			break;
		case 17:
			qn.setName(value);
			break;
		case 18:
			qn.setEmail(value);
			break;
		case 19:
			qn.setPhone(value);
			break;
		case 20:
			qn.setTelephone(value);
			break;
		case 22:
			qn.setProName(value);
			break;
		case 23:
			qn.setSaler(value);
			break;
		case 26:
			qn.setProManager(value);
			break;
		case 27:
			qn.setSerialNum(value);
			break;
		case 28:
			qn.setProductName(value);
			break;
		case 31:
			qn.setProCoder(value);
			break;
		case 38:
			qn.setEngineer(value);
			break;
		case 39:
			qn.setProvince(value);
			break;
		default:
			break;
		}
	}
	
	private static Integer intValueOf(String value) {
		if (StringUtils.isNumeric(value)) {
			return Integer.valueOf(value);
		}
		return 0;
	}

	private static String getStringCellValue(HSSFCell cell) {
		String value;
		if (cell == null) {
			return "";
		}
		switch (cell.getCellTypeEnum()) {
		case NUMERIC:
			value = (int)cell.getNumericCellValue() + "";
			break;
		case STRING:
			value = cell.getStringCellValue();
			break;
		case BOOLEAN:
			value = cell.getBooleanCellValue() + "";
			break;
		default:
			value = "";
			break;
		}
		return value;
	}
	
	public static <T> void toExcel(OutputStream out, String[] headers, List<BaseQn> list, Class t){
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		HSSFRow row = sheet.createRow(0);
		for (short i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(headers[i]);
		}
		int lastRowNum = 1;
		for (BaseQn qn : list) {
			row = sheet.createRow(lastRowNum);
			Field[] fields = t.getDeclaredFields();
			for (short i = 0; i < fields.length; i++) {
				Field field = fields[i];
	            String fieldName = field.getName();
	            String getMethodName = "get"
	                   + fieldName.substring(0, 1).toUpperCase()
	                   + fieldName.substring(1);
	            try {
	                Method getMethod = t.getMethod(getMethodName, new Class[] {});
	                Object value = getMethod.invoke(t, new Object[] {});
	            }catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
	}
}
