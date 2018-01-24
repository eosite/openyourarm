package com.glaf.mqdata.web.dubbo.client;

import java.io.IOException;
import java.util.List;

import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.service.IDatabaseService;
import com.glaf.maildata.domain.EmailSend;
import com.glaf.maildata.domain.EmailSendAtt;
import com.glaf.maildata.service.EmailRecService;
import com.glaf.maildata.service.EmailSendAttService;
import com.glaf.maildata.service.EmailSendService;
import com.glaf.mqdata.web.dubbo.server.ReceiveDataService;
import com.glaf.mqdata.web.dubbo.server.SendDataService;
import com.glaf.sys.domain.ProjMuiprojlist;
import com.glaf.sys.service.ProjMuiprojlistService;

@Component("com.glaf.mqdata.web.dubbo.client.mqClientService")
public class MQClientServiceImpl implements MQClientService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Reference(version = "4.0.0",protocol="dubbo",check=false)
	private ReceiveDataService receiveDataService;
	@Reference(version = "3.0.0",protocol="dubbo",check=false)
	private SendDataService sendDataService;
	@Autowired
	private ProjMuiprojlistService projMuiprojlistService;
	@Autowired
	protected EmailRecService emailRecService;
	@Autowired
	protected IDatabaseService databaseService;
	@Autowired
	protected EmailSendService emailSendService;
	@Autowired
	protected EmailSendAttService emailSendAttService;

	@Override
	public void receiveData() {
		// 获取当前系统ID
		ProjMuiprojlist projMuiprojlist = projMuiprojlistService.getLocalProjMuiprojlist();
		if(projMuiprojlist==null){
			logger.error("本地系统信息未定义！");
			return;
		}
		// 保存数据到数据库
		//String defaultDatabse = Environment.DEFAULT_SYSTEM_NAME;
		//Database database = databaseService.getDatabaseByCode(defaultDatabse);
		int i = 0;
		boolean saveFlag = true;
		byte[] dataPackage = null;
		while (saveFlag) {
			// 获取数据包内容
			dataPackage = receiveDataService.receiveMsg(projMuiprojlist.getSysId(), i == 0 ? false : true);
			if (dataPackage == null||dataPackage.length==1) {
				break;
			}
			// 解析xml定义，将接口数据以及附件放入数据库
			try {
				emailRecService.saveDataToDb(dataPackage);
			} catch (DocumentException e) {
				logger.error(e.getMessage());
				saveFlag = false;
			} catch (IOException e) {
				logger.error(e.getMessage());
				saveFlag = false;
			}
			i++;
		}

	}

	@Override
	public void sendData() {
		// 获取待发送接口数据列表
		List<EmailSend> emailSendList = emailSendService.getNeedSendEmails();
		// 获取邮件附件
		List<EmailSendAtt> emailSendAttrs = null;
		// 接口数据描述内容
		String xmlDesc = null;
		String recSysId = null;
		byte[] dataPackage = null;
		String subject=null;
		for (EmailSend emailSend : emailSendList) {
			xmlDesc = emailSend.getText();
			if (StringUtils.isEmpty(xmlDesc)) {
				xmlDesc = emailSend.getHtml();
			}
			if (StringUtils.isEmpty(xmlDesc)) {
				logger.info(emailSend.getId() + "描述文件为空！");
				continue;
			}
			// 获取接收方系统信息
			recSysId = emailSend.getToSysId();
			// 获取邮件附件信息
			emailSendAttrs = emailSendAttService.getEmailSendAttsByMailId(emailSend.getId());
			// 数据封包
			try {
				subject=emailSend.getSubject();
				dataPackage = emailSendService.generateSendDataPackage(subject,xmlDesc, emailSendAttrs);
				byte[] result=sendDataService.sendMsgtoSys(recSysId, dataPackage);
				JSONObject json=JSONObject.parseObject(new String(result));
				if(json.getString("statusCode").equals("200")){
					//更新发送状态
					emailSendService.updateEmailOperationStatus(emailSend.getId(), 1);
				}else{
					logger.error("发送数据【ID=" + emailSend.getId() + "】到系统" + recSysId + "出错!");
				}
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				logger.error("发送数据【ID=" + emailSend.getId() + "】到系统" + recSysId + ",封装数据包出错!");
				continue;
			}
		}
	}

}
