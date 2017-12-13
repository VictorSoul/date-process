package com.cs.vic.process;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FileProcess {
	public static Workbook fileRead(String filePath) {
		File file = new File(filePath);
		return fileRead(file);
	}

	public static Workbook fileRead(File file) {
		Workbook workbook = new XSSFWorkbook();
		try {
			if (file.getName().toLowerCase().matches("^.+\\.(?i)(xls|xlsx)$")) {// ÅÐ¶ÏÊÇ·ñexcelÎÄµµ
				FileInputStream fileInputStream;
				fileInputStream = new FileInputStream(file);
				boolean is03Excel = file.getName().toLowerCase().matches("^.+\\.(?i)(xlsx)$") ? true : false;
				workbook = is03Excel ? new XSSFWorkbook(fileInputStream) : new HSSFWorkbook(fileInputStream);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return workbook;
	}

	public static void filewrite(Workbook workbook, String filePath) {
		File file = new File(filePath);
		filewrite(workbook, file);
	}

	public static void filewrite(Workbook workbook, File file) {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			workbook.write(fileOutputStream);
			workbook.close();
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
