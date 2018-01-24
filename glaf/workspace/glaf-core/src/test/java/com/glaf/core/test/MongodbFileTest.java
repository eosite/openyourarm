package com.glaf.core.test;

import com.glaf.core.base.BlobItem;
import com.glaf.core.base.DataFile;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.domain.BlobItemEntity;
import com.glaf.core.mongodb.MongodbFileManager;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.UUID32;

public class MongodbFileTest {

	public static void main(String[] args) {
		System.out.println(SystemConfig.getSysCode());
		MongodbFileManager fileManager = new MongodbFileManager();
		BlobItem dataFile = new BlobItemEntity();
		dataFile.setLastModified(System.currentTimeMillis());
		String fileId = UUID32.getUUID();// 保证文件编号全局唯一，即整个应用唯一
		dataFile.setCreateBy("test");
		dataFile.setId(fileId);
		dataFile.setFileId(fileId);
		dataFile.setFilename("jdk.exe");
		dataFile.setName("jdk.exe");
		dataFile.setContentType("exe");
		dataFile.setType("exe");
		dataFile.setStatus(0);
		dataFile.setServiceKey("xxxx");
		dataFile.setData(FileUtils.getBytes("E:\\stg\\tools\\jdk-8u101-windows-x64.exe"));
		fileManager.save(dataFile);
		DataFile tmp = fileManager.getDataFileWithLob(fileId);
		FileUtils.save("c:\\temp\\jdk.exe", tmp.getData());
		fileManager.deleteDataFile(fileId);
		System.out.println("End!");
	}
}
