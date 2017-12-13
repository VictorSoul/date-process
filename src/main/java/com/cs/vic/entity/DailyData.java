package com.cs.vic.entity;

import java.util.Date;

import com.cs.vic.utils.DateUtil;

public class DailyData {
	public String name;
	public Date start;
	public Date end;
	private boolean overTime;
	private boolean over1130;
	private boolean workingday;
	private int amount = 0;
	private double overlength;
	private double worklength;
	private String workstring;
	private int weekcan;

	public int getWeekcan() {
		return weekcan;
	}

	public void setWeekcan(int weekcan) {
		this.weekcan = weekcan;
	}

	public boolean isOverTime() {
		return overTime;
	}

	public void setOverTime(boolean overTime) {
		this.overTime = overTime;
	}

	public boolean isOver1130() {
		return over1130;
	}

	public void setOver1130(boolean over1130) {
		this.over1130 = over1130;
	}

	public boolean isWorkingday() {
		return workingday;
	}

	public void setWorkingday(boolean workingday) {
		this.workingday = workingday;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getOverlength() {
		return overlength;
	}

	public void setOverlength(double overlength) {
		this.overlength = overlength;
	}

	public double getWorklength() {
		return worklength;
	}

	public void setWorklength(double worklength) {
		this.worklength = worklength;
	}

	public String getWorkstring() {
		return workstring;
	}

	public void setWorkstring(String workstring) {
		this.workstring = workstring;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public void process() {
		this.worklength = DateUtil.calWorkTime(this);
		this.workingday = DateUtil.isWorkingDay(this.start);
		this.overlength = DateUtil.calOverTime(this);
		calAmount();
	}

	private void calAmount() {
		if (this.workingday) {
			if (DateUtil.afterSeven(this.getEnd())) {
				this.amount += 15;
				this.overTime = true;
				if (DateUtil.afterEleven(this.getEnd())) {
					this.amount += 20;
					this.over1130 = true;
				}
			}
		} else {
			this.overTime = true;
			this.amount += 15;
			this.weekcan = (int) ((this.overlength + 0.5) / 4);
			this.amount += 15 * this.weekcan;
		}
	}
}
