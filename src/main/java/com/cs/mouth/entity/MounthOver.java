package com.cs.mouth.entity;

import java.util.LinkedList;

import com.cs.vic.entity.DailyData;

public class MounthOver {
	private LinkedList<DailyData> dailyOver = new LinkedList<DailyData>();
	private String mounth;
	private int total = 0;

	public void total() {
		for (DailyData dailyData : dailyOver) {
			total += dailyData.getOverlength();
		}
	}

	public String getMounth() {
		return mounth;
	}

	public void setMounth(String mounth) {
		this.mounth = mounth;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void addOver(DailyData data) {
		dailyOver.add(data);
	}

}
