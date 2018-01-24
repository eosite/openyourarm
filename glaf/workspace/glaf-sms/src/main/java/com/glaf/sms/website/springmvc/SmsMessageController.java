/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.sms.website.springmvc;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.identity.User;
import com.glaf.core.util.Hex;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.sms.domain.SmsClient;
import com.glaf.sms.domain.SmsMessage;
import com.glaf.sms.domain.SmsPerson;
import com.glaf.sms.query.SmsPersonQuery;
import com.glaf.sms.service.SmsClientService;
import com.glaf.sms.service.SmsMessageService;
import com.glaf.sms.service.SmsPersonService;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/sms/message")
@RequestMapping("/sms/message")
public class SmsMessageController {
	protected static final Log logger = LogFactory.getLog(SmsMessageController.class);

	protected SmsClientService smsClientService;

	protected SmsMessageService smsMessageService;

	protected SmsPersonService smsPersonService;

	public SmsMessageController() {

	}
	@RequestMapping("/checkSendMessage")
	@ResponseBody
	public byte[] checkSendMessage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		if(true){
//			OutputStream os = response.getOutputStream();
//			os.write("YES".getBytes());
//			return;
			return "YES".getBytes();
		}
		return "YES".getBytes();
	}
	@ResponseBody
	@RequestMapping("/saveMsg")
	public byte[] saveMsg(HttpServletRequest request) {
		String sysCode = request.getParameter("sysCode");
		String pwd = request.getParameter("pwd");
		String json = request.getParameter("data");
		try {
			JSONObject result = new JSONObject();
			JSONArray errorDataArry = new JSONArray();
			SmsClient client = smsClientService.getSmsClientBySysCode(sysCode);
			
			if (client != null && StringUtils.isNotEmpty(pwd) && StringUtils.isNotEmpty(json)) {
				if (StringUtils.equals(client.getEncryptionFlag(), "ALL")) {
					pwd = new String(smsClientService.decryptText(client.getId(), Hex.decodeHex(pwd)));
				}
				String str = DigestUtils.sha512Hex(sysCode + pwd);
				if (StringUtils.equals(str, client.getSysPwd())) {
					if (StringUtils.equals(client.getEncryptionFlag(), "ALL")
							|| StringUtils.equals(client.getEncryptionFlag(), "DATA")) {
						json = new String(smsClientService.decryptText(client.getId(), Hex.decodeHex(json)));
					}
					JSONArray array = JSON.parseArray(json);
					if (array != null && array.size() > 0) {
						SmsPersonQuery query = new SmsPersonQuery();
						query.clientId(client.getId());
						List<SmsPerson> persons = smsPersonService.list(query);
						if (persons != null && !persons.isEmpty()) {
							Map<String, SmsPerson> personMap = new HashMap<String, SmsPerson>();
							for (SmsPerson p : persons) {
								personMap.put(p.getMobile(), p);
							}
							List<SmsMessage> rows = new ArrayList<SmsMessage>();
							for (int i = 0, len = array.size(); i < len; i++) {
								JSONObject jsonObject = array.getJSONObject(i);
								String mobile = jsonObject.getString("mobile");
								
								if (personMap.get(mobile) != null) {
									SmsMessage model = new SmsMessage();
									model.setClientId(client.getId());
									model.setName(personMap.get(mobile).getName());
									model.setMobile(mobile);
									if (jsonObject.containsKey("subject")) {
										model.setSubject(jsonObject.getString("subject"));
									}
									if (jsonObject.containsKey("message")) {
										model.setMessage(jsonObject.getString("message"));
									}
									if (jsonObject.containsKey("sendLaterTime")) {
										model.setSendLaterTime(jsonObject.getDate("sendLaterTime"));
									}
									rows.add(model);
								}else{
									jsonObject.put("result", "该号码不可接收短信".getBytes("UTF-8"));
									errorDataArry.add(jsonObject);
								}
							}
							if(errorDataArry.size() > 0){
								logger.error(errorDataArry.toJSONString());
							}
							smsMessageService.bulkInsert(rows);
							result.put("errorData", errorDataArry);
							result.put("statusCode", "200");
							return result.toJSONString().getBytes("UTF-8");
//							return ResponseUtils.responseJsonResult(true);
						}
					}
				}else{
					logger.error("密码不正确，短信接受失败!");
				}
			}
		} catch (Exception ex) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			logger.error(sw.toString());
			logger.error("短信导入失败，失败结果如下");
			logger.error(ex);
			return ResponseUtils.responseJsonResult(false, "错误信息:"+sw.toString());
		}
		return ResponseUtils.responseJsonResult(false,"密码错误");
	}

	@ResponseBody
	@RequestMapping("/savePersons")
	public byte[] savePersons(HttpServletRequest request) {
		String sysCode = request.getParameter("sysCode");
		String pwd = request.getParameter("pwd");
		String json = request.getParameter("data");
		try {
			SmsClient client = smsClientService.getSmsClientBySysCode(sysCode);
			if (client != null && StringUtils.isNotEmpty(pwd) && StringUtils.isNotEmpty(json)) {
				if (StringUtils.equals(client.getEncryptionFlag(), "ALL")) {
					pwd = new String(smsClientService.decryptText(client.getId(), Hex.decodeHex(pwd)));
				}
				String str = DigestUtils.sha512Hex(sysCode + pwd);
				if (StringUtils.equals(str, client.getSysPwd())) {
					if (StringUtils.equals(client.getEncryptionFlag(), "ALL")
							|| StringUtils.equals(client.getEncryptionFlag(), "DATA")) {
						json = new String(smsClientService.decryptText(client.getId(), Hex.decodeHex(json)));
					}
					JSONArray array = JSON.parseArray(json);
					if (array != null && array.size() > 0) {
						Map<String, SmsPerson> personMap = new HashMap<String, SmsPerson>();
						SmsPersonQuery query = new SmsPersonQuery();
						query.clientId(client.getId());
						List<SmsPerson> persons = smsPersonService.list(query);
						if (persons != null && !persons.isEmpty()) {
							for (SmsPerson p : persons) {
								personMap.put(p.getMobile(), p);
							}
						}

						List<SmsPerson> list = new ArrayList<SmsPerson>();
						for (int i = 0, len = array.size(); i < len; i++) {
							JSONObject jsonObject = array.getJSONObject(i);
							String operation = jsonObject.getString("operation");
							String mobile = jsonObject.getString("mobile");
							if (StringUtils.equals(operation, "insert")) {
								if (personMap.get(mobile) == null) {
									SmsPerson p = new SmsPerson();
									p.setClientId(client.getId());
									p.setMobile(mobile);
									if (jsonObject.containsKey("name")) {
										p.setName(jsonObject.getString("name"));
									}
									if (jsonObject.containsKey("limit")) {
										p.setLimit(jsonObject.getInteger("limit"));
									}
									list.add(p);
								}
							} else if (StringUtils.equals(operation, "delete")) {
								SmsPerson p = personMap.get(mobile);
								if (p != null) {
									smsClientService.deleteById(p.getId());
								}
							}
						}
						smsPersonService.bulkInsert(list);
						return ResponseUtils.responseJsonResult(true);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}

		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.sms.service.smsClientService")
	public void setSmsClientService(SmsClientService smsClientService) {
		this.smsClientService = smsClientService;
	}

	@javax.annotation.Resource(name = "com.glaf.sms.service.smsMessageService")
	public void setSmsMessageService(SmsMessageService smsMessageService) {
		this.smsMessageService = smsMessageService;
	}

	@javax.annotation.Resource(name = "com.glaf.sms.service.smsPersonService")
	public void setSmsPersonService(SmsPersonService smsPersonService) {
		this.smsPersonService = smsPersonService;
	}

}
