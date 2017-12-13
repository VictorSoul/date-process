package com.cs.vic.entity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import org.apache.poi.ss.usermodel.Row;

@SuppressWarnings("deprecation")
public class TimeData {
	private static DateFormat dtf = new SimpleDateFormat("yy-MM-dd kk:mm");
	private LinkedList<DateRecord> records = new LinkedList<DateRecord>();
	private LinkedList<DateRecord> daily = new LinkedList<DateRecord>();
	private LinkedList<DailyData> dailyDatas = new LinkedList<DailyData>();

	public void addRow(Row row) {
		try {
			DateRecord record = new DateRecord();
			String name = row.getCell(0).getStringCellValue();
			Date dt = dtf.parse(row.getCell(1).getStringCellValue() + " " + row.getCell(2).getStringCellValue());
			record.setName(name).setDatetime(dt);
			dailyRecord(record);
			records.add(record);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void dailyRecord(DateRecord record) {
		if (daily.isEmpty() || (!daily.isEmpty() && daily.getFirst().getName().equals(record.getName())
				&& daily.getFirst().getDatetime().getDate() == record.getDatetime().getDate())) {
			daily.add(record);
		} else {
			if (daily.size() == 1) {
				daily.add(daily.getFirst().add530());
			} else if (daily.size() > 2) {
				DateRecord start = daily.getFirst();
				DateRecord end = daily.getLast();
				daily.clear();
				daily.addFirst(start);
				daily.addLast(end);
			}
			genDailyData(daily);
			daily.clear();
			daily.add(record);
		}
	}

	private void genDailyData(LinkedList<DateRecord> dailyRec) {
		DailyData dailyData = new DailyData();
		dailyData.setName(dailyRec.getFirst().getName());
		dailyData.setStart(dailyRec.getFirst().getDatetime());
		dailyData.setEnd(dailyRec.getLast().getDatetime());
		dailyData.process();
		dailyDatas.add(dailyData);
	}

	public LinkedList<DateRecord> getRecords() {
		return records;
	}

	public LinkedList<DailyData> getDailyDatas() {
		return dailyDatas;
	}

}
