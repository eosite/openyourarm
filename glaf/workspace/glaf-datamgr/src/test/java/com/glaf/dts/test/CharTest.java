package com.glaf.dts.test;

import java.util.*;

public class CharTest {

	public static void main(String[] args) {
		int firstEnglish, lastEnglish;
		char firstE = 'A', lastE = 'Z'; // 获取首字母与末字母的值

		firstEnglish = (int) firstE;
		lastEnglish = (int) lastE;

		System.out.println("英文字母表： ");
		Map<String, Integer> dataMap = new LinkedHashMap<String, Integer>();
		int index = 0;
		for (int i = firstEnglish; i <= lastEnglish; ++i) {
			char uppercase = (char) i;
			System.out.print(" " + uppercase);
			dataMap.put(String.valueOf(uppercase), index++);
			int k = 0;
			for (int j = firstEnglish; j <= lastEnglish; ++j) {
				char uppercase2 = (char) j;
				dataMap.put(String.valueOf(uppercase) + String.valueOf(uppercase2), 26 * index + k++);
			}
		}
		System.out.println();
		System.out.println(dataMap);
		System.out.println(dataMap.size());
	}
}
