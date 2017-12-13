package com.cs.mouthly.entity;

import java.util.LinkedList;

import com.cs.vic.entity.DailyData;

public class PerTimes {
	private LinkedList<DailyData> dailydatas = new LinkedList<DailyData>();
	private String name;

	public LinkedList<DailyData> getDailyDatas() {
		return dailydatas;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addTime(DailyData dailyData) {
		dailydatas.add(dailyData);
	}
}
