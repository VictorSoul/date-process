package com.cs.vic.entity;

import java.util.Date;

public class DateRecord implements Cloneable {
	public String name;
	public Date datetime;

	public String getName() {
		return name;
	}

	public DateRecord setName(String name) {
		this.name = name;
		return this;
	}

	public Date getDatetime() {
		return datetime;
	}

	public DateRecord setDatetime(Date datetime) {
		this.datetime = datetime;
		return this;
	}

	public DateRecord clone() {
		DateRecord clone = new DateRecord();
		clone.setName(this.name);
		clone.setDatetime(this.datetime);
		return clone;
	}

	@SuppressWarnings("deprecation")
	public DateRecord add530() {
		DateRecord copy = clone();
		copy.getDatetime().setHours(17);
		copy.getDatetime().setMinutes(30);
		return copy;
	}
}
