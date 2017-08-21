package com.lvmeng.qm.base.commons.util;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class DownloadUtil {

	public static boolean downloadExcel(HttpServletResponse response, HSSFWorkbook workbook, String fileName){
		ServletOutputStream out = null;
		try {
			//设置文件MIME类型  
			response.setHeader("Content-disposition","attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
			// 设定输出文件头        
			response.setContentType("application/msexcel");// 定义输出类型
			out = response.getOutputStream();
			workbook.write(out);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (out != null){
					out.close();
				}
				if (workbook != null){
					workbook.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
}
