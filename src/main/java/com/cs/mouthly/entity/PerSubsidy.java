package com.cs.mouthly.entity;

import java.util.LinkedList;

import com.cs.vic.entity.DailyData;

public class PerSubsidy {
	private LinkedList<DailyData> dailyOver = new LinkedList<DailyData>();
	private int totalAmount = 0;
	private String desc;
	private String name;

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LinkedList<DailyData> getDailyOver() {
		return dailyOver;
	}

	public void addOver(DailyData dailyData) {
		dailyOver.add(dailyData);
	}

	public void total() {
		int workday = 0;
		int weekend = 0;
		int voer11 = 0;
		int weekcan = 0;
		for (DailyData dailyData : dailyOver) {
			this.totalAmount += dailyData.getAmount();
			if (dailyData.isWorkingday()) {
				workday++;
				if (dailyData.isOver1130()) {
					voer11++;
				}
			} else {
				weekend++;
				weekcan += dailyData.getWeekcan();
			}
		}
		this.desc = "工作日加班:" + workday + "天(包含加班超过23：30:" + voer11 + "次)  周末加班: " + weekend + "次 (交通补贴: " + weekend
				+ "次,餐补:" + weekcan + "次)";
	}

}
