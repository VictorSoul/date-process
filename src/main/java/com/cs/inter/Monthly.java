package com.cs.inter;

import java.text.ParseException;
import java.util.LinkedList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import com.cs.mouthly.entity.PerSubsidy;
import com.cs.mouthly.entity.PerTimes;
import com.cs.vic.entity.DailyData;
import com.cs.vic.entity.TimeData;
import com.cs.vic.process.DailyProcess;
import com.cs.vic.process.FileProcess;
import com.cs.vic.utils.DateUtil;

/*
 * 每月工时及餐补交通补贴计算入口
 * 参数分别为：
 * datePath: 调休放假Json文件路径，
 * source:打卡数据文件路径，
 * result：结果文件存放目录
 */

public class Monthly {
	private LinkedList<DailyData> datas;

	private LinkedList<PerSubsidy> perSubsidies = new LinkedList<PerSubsidy>();

	private LinkedList<PerTimes> perTimes = new LinkedList<PerTimes>();

	public void domain(String datePath, String source, String result) {
		try {
			DateUtil.init(datePath);
			process(source);
			genExcel(result);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void genExcel(String result) {
		genWorkTime(result);
		genSubsidies(result);
	}

	private void genSubsidies(String result) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("餐补交通补贴");
		creatSubsidiestitle(sheet);
		int rowNum = 1;
		for (int i = 0; i < perSubsidies.size(); i++) {
			PerSubsidy perSubsidiy = perSubsidies.get(i);
			for (int j = 0; j < perSubsidiy.getDailyOver().size(); j++) {
				DailyData daily = perSubsidiy.getDailyOver().get(j);
				HSSFRow dailyRow = sheet.createRow(rowNum++);
				HSSFCell cell = dailyRow.createCell(0);
				cell.setCellValue(daily.getName());
				HSSFCell cell1 = dailyRow.createCell(1);
				cell1.setCellValue(DateUtil.ymd.format((daily.getStart())));
				HSSFCell cell2 = dailyRow.createCell(2);
				cell2.setCellValue(DateUtil.hm.format((daily.getStart())));
				HSSFCell cell3 = dailyRow.createCell(3);
				cell3.setCellValue(DateUtil.hm.format((daily.getEnd())));
				HSSFCell cell4 = dailyRow.createCell(4);
				cell4.setCellValue(daily.isWorkingday() ? "工作日" : "非工作日");
				HSSFCell cell5 = dailyRow.createCell(5);
				cell5.setCellValue(daily.getAmount());
			}
			HSSFRow row = sheet.createRow(rowNum++);
			HSSFCell cell = row.createCell(4);
			cell.setCellValue("补贴总额");
			HSSFCell cell1 = row.createCell(5);
			cell1.setCellValue(perSubsidiy.getTotalAmount());
			HSSFCell cell2 = row.createCell(6);
			cell2.setCellValue(perSubsidiy.getDesc());

		}
		FileProcess.filewrite(workbook, result + "/Subsidies.xls");
	}

	private void creatSubsidiestitle(HSSFSheet sheet) {
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("姓名");
		HSSFCell cell1 = row.createCell(1);
		cell1.setCellValue("日期");
		HSSFCell cell2 = row.createCell(2);
		cell2.setCellValue("上班时间");
		HSSFCell cell3 = row.createCell(3);
		cell3.setCellValue("下班时间");
		HSSFCell cell4 = row.createCell(4);
		cell4.setCellValue("是否工作日");
		HSSFCell cell5 = row.createCell(5);
		cell5.setCellValue("补贴总额");
		HSSFCell cell6 = row.createCell(6);
		cell6.setCellValue("补贴说明");
	}

	private void genWorkTime(String result) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("WorkTime");
		creatWorkTimetitle(sheet);
		int rowNum = 1;
		for (int i = 0; i < perTimes.size(); i++) {
			PerTimes pertime = perTimes.get(i);
			for (int j = 0; j < pertime.getDailyDatas().size(); j++) {
				DailyData daily = pertime.getDailyDatas().get(j);
				HSSFRow dailyRow = sheet.createRow(rowNum++);
				HSSFCell cell = dailyRow.createCell(0);
				cell.setCellValue(daily.getName());
				HSSFCell cell1 = dailyRow.createCell(1);
				cell1.setCellValue(DateUtil.ymd.format((daily.getStart())));
				HSSFCell cell2 = dailyRow.createCell(2);
				cell2.setCellValue(DateUtil.hm.format((daily.getStart())));
				HSSFCell cell3 = dailyRow.createCell(3);
				cell3.setCellValue(DateUtil.hm.format((daily.getEnd())));
				HSSFCell cell4 = dailyRow.createCell(4);
				cell4.setCellValue(daily.getWorklength());
				HSSFCell cell5 = dailyRow.createCell(5);
				cell5.setCellValue(daily.getWorkstring());
			}
			sheet.createRow(rowNum++);
		}
		FileProcess.filewrite(workbook, result + "/WorkTimes.xls");
	}

	private void creatWorkTimetitle(HSSFSheet sheet) {
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("姓名");
		HSSFCell cell1 = row.createCell(1);
		cell1.setCellValue("日期");
		HSSFCell cell2 = row.createCell(2);
		cell2.setCellValue("上班时间");
		HSSFCell cell3 = row.createCell(3);
		cell3.setCellValue("下班时间");
		HSSFCell cell4 = row.createCell(4);
		cell4.setCellValue("工作时长1");
		HSSFCell cell5 = row.createCell(5);
		cell5.setCellValue("工作时长2");
	}

	private void process(String source) {
		Workbook workbook = FileProcess.fileRead(source);
		TimeData timeData = DailyProcess.process(workbook);
		datas = timeData.getDailyDatas();
		calSubsidies();
		calTimes();
	}

	private void calTimes() {
		for (DailyData dailyData : datas) {
			boolean find = false;
			for (PerTimes per : perTimes) {
				if (per.getName().equals(dailyData.getName())) {
					per.addTime(dailyData);
					find = true;
					break;
				}
			}
			if (!find) {
				PerTimes per = new PerTimes();
				per.setName(dailyData.getName());
				per.addTime(dailyData);
				perTimes.add(per);
			}
		}
	}

	private void calSubsidies() {
		for (DailyData dailyData : datas) {
			boolean find = false;
			if (dailyData.isOverTime()) {
				for (PerSubsidy per : perSubsidies) {
					if (per.getName().equals(dailyData.getName())) {
						per.addOver(dailyData);
						find = true;
						break;
					}
				}
				if (!find) {
					PerSubsidy per = new PerSubsidy();
					per.setName(dailyData.getName());
					per.addOver(dailyData);
					perSubsidies.add(per);
				}
			}
		}
		for (PerSubsidy per : perSubsidies) {
			per.total();
		}
	}
}
