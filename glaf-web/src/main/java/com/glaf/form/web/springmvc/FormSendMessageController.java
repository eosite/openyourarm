package com.glaf.form.web.springmvc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.identity.User;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.form.core.domain.FormDictory;
import com.glaf.form.core.domain.FormNodeMessage;
import com.glaf.form.core.query.FormNodeMessageQuery;
import com.glaf.form.core.service.FormNodeMessageService;
import com.glaf.form.core.service.IFormDictoryService;


@Controller("/form/sendMessage")
@RequestMapping("/form/sendMessage")
public class FormSendMessageController {
	protected final static Log logger = LogFactory.getLog(FormSendMessageController.class);
	
	@Autowired
	protected IFormDictoryService formDictoryService;
	
	@Autowired
	protected FormNodeMessageService formNodeMessageService;
	
	@RequestMapping
	@ResponseBody
	public byte[] getToken(HttpServletRequest request, HttpServletResponse response) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		String ret = "";	//返回结果
		try{
	    	String params = RequestUtils.getString(request, "params");
	    	if(params == null){
	    		return ResponseUtils.responseJsonResult(false, "无任何信息");
	    	}
	    	JSONObject paramsJson = (JSONObject)JSON.parse(params);
	    	List<FormDictory> list = formDictoryService.getFormDictoryList("noteMessage");
	    	String telphoneValue = "";	//参数值
	    	String msgValue = "";	//参数值
	    	String subjectValue = paramsJson.getString("subject");	//标题
	    	String sendLaterTimeValue = paramsJson.getString("sendLaterTime");	//定时发送
	    	Date sendLaterTime = null;	//定时发送
	    	if(StringUtils.isNotEmpty(sendLaterTimeValue)){
	    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    		sendLaterTime = sdf.parse(sendLaterTimeValue);
	    	}
	    	
	    	for(FormDictory dictory : list){
	    		if(dictory.getCode().equals("x")){
	    			telphoneValue = paramsJson.getString("x");
	    		}else if(dictory.getCode().equals("y")){
	    			msgValue = paramsJson.getString("y");
	    		}
	    	}
	    	
	    	if(telphoneValue == null || telphoneValue.isEmpty() || msgValue == null || msgValue.isEmpty()){
	    		//若信息和号码任意一个为空，返回信息
	    		return ResponseUtils.responseJsonResult(false, "号码或信息为空");
	    	}
	    	
	    	logger.debug("--------------------------短信服务--------------------------");
	    	Date nowDate = new Date();
	    	
	    	FormNodeMessage newNode = null;	//新的短信纪录
	    	String[] telphoneValues =  telphoneValue.split(",");
	    	StringBuffer errMessage = new StringBuffer();	//纪录错误信息
	    	for(String telphone : telphoneValues){
	    		if(telphone.isEmpty()){
	    			errMessage.append("已跳过号码为空的短信发送！");
	    			continue;
	    		}
	    		if(!isMobileNO(telphone)){
	    			errMessage.append("号码:\""+telphone+"\"不是手机号码，跳过!");
	    			continue;
	    		}
	    		FormNodeMessageQuery newNodeQuery = new FormNodeMessageQuery();
	    		newNodeQuery.setSortColumn("createDate");
	    		newNodeQuery.setSortOrder(" desc ");
	    		newNodeQuery.setTelephone(telphone);

	    		newNode = new FormNodeMessage();	//新的短信纪录
    			newNode.setTelephone(telphone);
				newNode.setMsg(msgValue);
				newNode.setCreator(actorId);
				newNode.setCreateDate(nowDate);
				
				if(StringUtils.isNotEmpty(subjectValue)){
					newNode.setSubject(subjectValue);
				}
				if(sendLaterTime != null){
					newNode.setSendLaterTime(sendLaterTime);
				}
				
				
				
//	    		List<FormNodeMessage> nodeList = formNodeMessageService.list(newNodeQuery);	//查询是否已有该电话信息
//	    		if(nodeList != null && nodeList.size() > 0){
//	    			newNode = nodeList.get(0);
//	    			//如果短信超过250，则分开另加一条纪录
//	    			if(newNode.getMsg().length() + msgValue.length() > 250){
//	    				newNode = new FormNodeMessage();
//	    				newNode.setTelephone(telphone);
//	    				newNode.setMsg(msgValue);
//	    				newNode.setCreator(actorId);
//	    				newNode.setCreateDate(nowDate);
//	    			}
//	    			newNode.setMsg(newNode.getMsg() + msgValue);
//	    		}else{
//	    			newNode = new FormNodeMessage();	//新的短信纪录
//	    			newNode.setTelephone(telphone);
//    				newNode.setMsg(msgValue);
//    				newNode.setCreator(actorId);
//    				newNode.setCreateDate(nowDate);
//	    		}
	    		try{
	    			formNodeMessageService.save(newNode);	//插入一条纪录
	    		}catch(Exception e){
	    			logger.error("号码:\"" + telphone + "\"插入待发送列表出错，信息为\""+msgValue+"\"");
	    			errMessage.append("号码:\""+telphone+"\"插入待发送列表出错！");
	    		}
//	    		errMessage.append("号码:\""+telphone+"\"成功插入待发送列表!");
	    		logger.debug("号码:\""+telphone+"\"成功插入待发送列表!");
	    	}
	    	ret = errMessage.toString();
	    	if(ret.isEmpty()){
	    		return ResponseUtils.responseJsonResult(true,"短信信息已成功插入待发送列表中!");
	    	}
	    	return ResponseUtils.responseJsonResult(false,ret);
	    }catch (Exception e) {
			// TODO: handle exception
	    	e.printStackTrace();
	    	logger.error(e);
		}finally{
		}
		return ResponseUtils.responseJsonResult(false,"出现异常，输入信息有误！");
	}
	/**
	 * 判断是否是手机号码
	 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
	 * 联通：130、131、132、152、155、156、185、186
　　	 * 电信：133、153、180、189、（1349卫通）
	 * @param mobiles
	 * @return
	 */
	public boolean isMobileNO(String mobiles){  
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,2,5-9]))\\d{8}$");  
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}  
	
}
