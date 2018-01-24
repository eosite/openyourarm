package com.glaf.dts.test;

import java.io.IOException;

public class RunCmd {

	public static void main(String[] args) {
		try {
			Runtime.getRuntime().exec(" net start \"MySQL_GLAF\" ");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
