package com.glaf.core.test;

import com.glaf.core.util.FileUtils;
import com.glaf.core.util.UUID32;

public class FileTest {
	
	public static void main(String[] args){
		FileUtils.save("C:/temp/publish20160617a/common/案卷目录和卷内目录/excel/中文目录/1.1.2 委托书及工程可行性研究报告、合同（协议）、评审及报批文件、可行性研究报告2.txt", UUID32.getUUID().getBytes());
	}

}
