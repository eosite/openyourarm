package com.glaf.form.core.util;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.SystemProperties;
import com.glaf.teim.service.EimServerTmpService;

import sun.misc.BASE64Encoder;  
  
@Component
public class OcrHelper  
{  
	protected static final Log logger = LogFactory.getLog(OcrHelper.class);
	
	@Autowired
	private EimServerTmpService eimServerTmpService;
	
	public JSONObject aliOrcIdCard(byte[] imageData,String tmpId,String baseId){
		JSONObject ret = new JSONObject();
		if (StringUtils.isNotEmpty(tmpId) || StringUtils.isNotEmpty(baseId)) {
	    	
	    	JSONObject paramsJson = new JSONObject();
	    	
	    	paramsJson.put("dataValue", getBase64(imageData));
	    	JSONObject returnJson = eimServerTmpService.callService(tmpId, baseId, paramsJson);
	    	return returnJson;
	    }
		ret.put("statusCode", "500");
		return ret;
	}
	
  
	public static String getBase64(byte[] data){
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);
	}
}  