package com.glaf.core.test;

import org.apache.commons.lang3.StringUtils;

public class DigitTest {

	public static void main(String[] args) {
		System.out.println(Integer.parseInt("000002450"));
		if (StringUtils.isNumeric("00000000000199")) {
			System.out.println(Integer.parseInt("00000000000199"));
		}

		if (StringUtils.isNumeric("-00000000000199")) {
			System.out.println(Integer.parseInt("-00000000000199"));
		}

		if (StringUtils.isNumeric("0.0000000000199")) {
			System.out.println(Double.parseDouble("0.0000000000199"));
		}

		if (StringUtils.isNumeric("-0.0000000000199")) {
			System.out.println(Double.parseDouble("-0.0000000000199"));
		}

		if (StringUtils.isNumeric("1000000000199")) {
			System.out.println(Long.parseLong("1000000000199"));
		}

		String paramValue = "-1000000000199";

		if (StringUtils.isNumeric(paramValue.substring(1, paramValue.length()))) {
			System.out.println(Long.parseLong("-1000000000199"));
		}

	}

}
