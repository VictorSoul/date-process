package com.cs.mouth.entity;

import java.util.Date;
import java.util.LinkedList;

import com.cs.vic.entity.DailyData;
import com.cs.vic.utils.DateUtil;

@SuppressWarnings("deprecation")
public class PerOver {
	private LinkedList<MounthOver> overs = new LinkedList<MounthOver>();
	private String name;

	public void addOver(DailyData data) {
		Date moun = data.getEnd();
		if (moun.getDate() > 25) {
			moun = DateUtil.addOneMou(moun);
		}
		String mounth = DateUtil.ym.format(moun);
		boolean find = false;
		for (MounthOver mounthOver : overs) {
			if (mounthOver.getMounth().equals(mounth)) {
				mounthOver.addOver(data);
				find = true;
				break;
			}
		}
		if (!find) {
			MounthOver mounthOver = new MounthOver();
			mounthOver.setMounth(mounth);
			mounthOver.addOver(data);
			overs.add(mounthOver);
		}
	}

	public void total() {
		for (MounthOver mounthOver : overs) {
			mounthOver.total();
			System.out.println(name + ":" + mounthOver.getMounth() + "   " + mounthOver.getTotal());
		}
	}

	public LinkedList<MounthOver> getOvers() {
		return overs;
	}

	public void setOvers(LinkedList<MounthOver> overs) {
		this.overs = overs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
