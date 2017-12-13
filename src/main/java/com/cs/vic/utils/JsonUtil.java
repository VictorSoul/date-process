package com.cs.vic.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class JsonUtil {
	public static String readJson(String formPath) {
		String content = "";
		FileReader read = null;
		BufferedReader reader = null;
		try {
			read = new FileReader(new File(formPath));
			reader = new BufferedReader(read);
			StringBuffer buffer = new StringBuffer("");
			content = reader.readLine();
			while (content != null) {
				buffer.append(content).append("\n");
				content = reader.readLine();
			}
			return content = buffer.toString();// 返回
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)
					reader.close();
				if (read != null)
					read.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";// 没值就返回空
	}
}
