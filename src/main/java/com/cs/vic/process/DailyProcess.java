package com.cs.vic.process;

import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.cs.vic.entity.TimeData;

public class DailyProcess {

	public static TimeData process(Workbook workbook) {
		TimeData timeData = new TimeData();
		Iterator<Sheet> Isheet = workbook.sheetIterator();
		while (Isheet.hasNext()) {
			Sheet sheet = Isheet.next();
			Iterator<Row> Irow = sheet.rowIterator();
			Irow.next();
			while (Irow.hasNext()) {
				Row row = Irow.next();
				timeData.addRow(row);
			}
		}
		return timeData;
	}

}
