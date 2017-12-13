package com.cs.inter;

import java.text.ParseException;
import java.util.LinkedList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import com.cs.mouth.entity.MounthOver;
import com.cs.mouth.entity.PerOver;
import com.cs.vic.entity.DailyData;
import com.cs.vic.entity.TimeData;
import com.cs.vic.process.DailyProcess;
import com.cs.vic.process.FileProcess;
import com.cs.vic.utils.DateUtil;

/*
 * ÿ�¼Ӱ�ʱ��ͳ��
 * �����ֱ�Ϊ��
 * datePath: ���ݷż�Json�ļ�·����
 * source:�������ļ�·����
 * result������ļ����Ŀ¼
 */

public class TongJi {
	private LinkedList<PerOver> pers = new LinkedList<PerOver>();

	public void domain(String datePath, String source, String result) {
		try {
			DateUtil.init(datePath);
			process(source);
			genExcel(result + "/Result.xls");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void genExcel(String path) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("�¼Ӱ�ʱ��ͳ��");
		creattitle(sheet);
		int rowNum = 1;
		for (int i = 0; i < pers.size(); i++) {
			PerOver perOver = pers.get(i);
			for (int j = 0; j < perOver.getOvers().size(); j++) {
				MounthOver mounthOver = perOver.getOvers().get(j);
				HSSFRow mounth = sheet.createRow(rowNum++);
				HSSFCell cell = mounth.createCell(0);
				cell.setCellValue(perOver.getName());
				HSSFCell cell1 = mounth.createCell(1);
				cell1.setCellValue(mounthOver.getMounth());
				HSSFCell cell2 = mounth.createCell(2);
				cell2.setCellValue(mounthOver.getTotal());
			}
			sheet.createRow(rowNum++);
		}
		FileProcess.filewrite(workbook, path);
	}

	private static void creattitle(HSSFSheet sheet) {
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("����");
		HSSFCell cell1 = row.createCell(1);
		cell1.setCellValue("�·�");
		HSSFCell cell2 = row.createCell(2);
		cell2.setCellValue("�Ӱ�ʱ��");
	}

	private void process(String source) {
		Workbook workbook = FileProcess.fileRead(source);
		TimeData timeData = DailyProcess.process(workbook);
		LinkedList<DailyData> datas = timeData.getDailyDatas();
		calOver(datas);
	}

	private void calOver(LinkedList<DailyData> datas) {
		for (DailyData dailyData : datas) {
			boolean find = false;
			if (dailyData.getOverlength() > 0) {
				for (PerOver per : pers) {
					if (per.getName().equals(dailyData.getName())) {
						per.addOver(dailyData);
						find = true;
						break;
					}
				}
				if (!find) {
					PerOver perOver = new PerOver();
					perOver.setName(dailyData.getName());
					perOver.addOver(dailyData);
					pers.add(perOver);
				}
			}

		}
		for (PerOver per : pers) {
			per.total();
		}
	}

}
