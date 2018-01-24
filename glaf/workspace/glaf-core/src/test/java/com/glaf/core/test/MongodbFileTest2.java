package com.glaf.core.test;

import java.io.IOException;

import com.glaf.core.base.BlobItem;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.domain.BlobItemEntity;
import com.glaf.core.mongodb.MongodbFileManager;
import com.glaf.core.util.UUID32;

public class MongodbFileTest2 {

	public static void main(String[] args) throws IOException {
		for (int k = 0; k < 1000; k++) {
			System.out.println(SystemConfig.getSysCode());
			StringBuilder buffer = new StringBuilder();
			String userId = "mtxx0026@188.com";
			buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			buffer.append("<文件交换信息>");
			buffer.append("<送出单位 系统ID=\"{6EEAC73C-D6D6-4EE0-A3E6-FDC8F864BB1A}\">");
			buffer.append("<电子邮箱>mtxx0012@188.com</电子邮箱>");
			buffer.append("<流程位置/>");
			buffer.append("<单位>J3</单位>");
			buffer.append("<邮件ID>{" + UUID32.getUUID() + "}</邮件ID>");
			buffer.append("<邮件序号>2</邮件序号>");
			buffer.append("</送出单位>");
			buffer.append("<接收单位 系统ID=\"{6C71C09C-AF52-40F1-8098-98B1158DE7C8}\">");
			buffer.append("<电子邮箱>" + userId + "</电子邮箱>");
			buffer.append("<流程位置/>");
			buffer.append("<单位>A7</单位>");
			buffer.append("</接收单位>");
			buffer.append("<邮件作用 类型=\"2\">");
			buffer.append("<类型说明>回复邮件互连</类型说明>");
			buffer.append("<文档类型>无</文档类型>");
			buffer.append("<回复邮件ID>{" + UUID32.getUUID() + "}</回复邮件ID>");
			buffer.append("</邮件作用>");
			buffer.append("<附件列表>");
			buffer.append("<附件>");
			buffer.append("<文件名>test.txt</文件名>");
			buffer.append("<内容>");
			for (int i = 0; i < 300; i++) {
				buffer.append(UUID32.getUUID());
			}
			buffer.append("</内容>");
			buffer.append("</附件>");
			buffer.append("</附件列表>");
			buffer.append("</文件交换信息>");
			String text = buffer.toString();
			MongodbFileManager fileManager = new MongodbFileManager();
			BlobItem dataFile = new BlobItemEntity();
			dataFile.setLastModified(System.currentTimeMillis());
			String fileId = UUID32.getUUID();
			dataFile.setId(fileId);
			dataFile.setFileId(fileId);
			dataFile.setFilename(fileId);
			dataFile.setName(fileId);
			dataFile.setContentType("txt");
			dataFile.setType("txt");
			dataFile.setStatus(0);
			dataFile.setServiceKey(fileId);
			dataFile.setCreateBy("test");
			dataFile.setData(text.getBytes("UTF-8"));
			fileManager.save(dataFile);
			// DataFile tmp = fileManager.getDataFileWithLob(fileId);
			// FileUtils.save("c:\\temp\\jdk.exe", tmp.getData());
			// System.out.println("End!");
		}
	}
}
