package com.glaf.form.web.springmvc;

import java.io.IOException;
import java.util.*;
 
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import com.alibaba.fastjson.*;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.TableModel;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.factory.TableFactory;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;
import com.glaf.core.util.http.HttpUtils;
import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;
import com.glaf.form.core.service.*;
import com.glaf.form.core.util.*;
import com.glaf.form.rule.DataSourceRequest;
import com.glaf.form.rule.Global;
import com.glaf.form.rule.ParamsSqlHelper;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/wpf")
@RequestMapping("/wpf")
public class FormWPFController {
	protected static final Log logger = LogFactory.getLog(FormWPFController.class);

	@Autowired
	private MutilDatabaseBean mutilDatabaseBean;
	
	public FormWPFController() {

	}
	
	
	/**
	 * 在线查看文件（CELL或TIF）
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getFiledotByIdForSoa")
	public @ResponseBody byte[] createData(HttpServletRequest request)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String uri = /*"http://zygs.fzmt.com.cn:6078"*/ RequestUtils.getString(request, "uri","");
		if(StringUtils.isEmpty(uri)){
			uri = SystemConfig.getString("wpfSoaUri") ;
		}
		String wuri = RequestUtils.getString(request, "wuri","");
		if(StringUtils.isEmpty(wuri)){
			wuri = uri ;
		}
		String did = RequestUtils.getString(request, "did", null);
		String databaseId = RequestUtils.getString(request, "databaseId", null);
		String sysId = RequestUtils.getString(request, "sysId", null);
		String fileId = RequestUtils.getString(request, "fileId", null);
		String fileType = RequestUtils.getString(request, "fileType", "tif");
		String queryTable = RequestUtils.getString(request, "queryTable", "filedot");
		String queryField = RequestUtils.getString(request, "queryField", "fileID,fname,file_content");
		String IdField = RequestUtils.getString(request, "IdField", "fileID");
		String FileField = RequestUtils.getString(request, "FileField", "file_content");
		
		String sid = UUID32.getUUID();
		String sql = "select PASSWORD_HASH as password from userinfo where UserID = '" + loginContext.getActorId() +"'";
		List<Map<String, Object>> list = mutilDatabaseBean.getDataListBySql(sql, StringUtils.isNotEmpty(databaseId)?Long.parseLong(databaseId):-1l);
		String pwd = null;
		if(list!=null && !list.isEmpty()){
			pwd = (String) list.get(0).get("password");
		}
		String uid = loginContext.getActorId() ;
		//String pwd = "fj87668438";
		String retUrl = null;
		String code = "500";
		String message = "操作成功！" ;
		
		try {
			logger.debug("params-->uri=" + uri +",did="+ did+",sid="+sid+",uid="+uid+",pwd="+ pwd);
			JSONObject loginRetBackObj = WpfSoaHttpUtils.loginHttpMethod(uri, did, sid, uid, pwd);
			logger.debug("loginRetBackObj===="+loginRetBackObj!=null?loginRetBackObj.toJSONString():null);
			if(loginRetBackObj!=null && loginRetBackObj.containsKey("code") && loginRetBackObj.getIntValue("code") == 0){
				String token = loginRetBackObj.getString("token");
				String timestamp = loginRetBackObj.getString("timestamp");
				if(token==null || timestamp == null){
					message = "登录异常！";
				}else{
					JSONObject param = new JSONObject();
					/*"sysId":"{ABDC29F8-0846-4D7D-B241-D60A75609A34}", 
				    "fileId": "20131210/admin-0000002",    
				    "fileType": "cell",                   　
				    "queryTable": "filedot",              
				    "queryField": "fileID,fname,file_content",　
				    "IdField":    "fileID",　　　　　　　　　　　　
				    "FileField":  "file_content"*/
					param.put("sysId", sysId);
					param.put("fileId", fileId);
					param.put("fileType", fileType);
					param.put("queryTable", queryTable);
					param.put("queryField", queryField);
					param.put("IdField", IdField);
					param.put("FileField", FileField);
					
					logger.debug("params2-->uri=" + uri +",did="+ did+",sid="+sid+",param="+ param.toJSONString());
					JSONObject filedotByIdHttpMethod = WpfSoaHttpUtils.getFiledotByIdHttpMethod(uri, did, sid, token, timestamp, param);
					logger.debug("filedotByIdHttpMethod===="+filedotByIdHttpMethod!=null?filedotByIdHttpMethod.toJSONString():null);
					if(filedotByIdHttpMethod!=null && filedotByIdHttpMethod.containsKey("code") && filedotByIdHttpMethod.getIntValue("code") == 0){
						if(filedotByIdHttpMethod!=null && filedotByIdHttpMethod.containsKey("data")){
							retUrl = filedotByIdHttpMethod.getJSONArray("data").getJSONObject(0).getString("fileUrl");
							code = "200" ; 
						}else{
							message = filedotByIdHttpMethod.getString("msg");
						}
					}else{
						if(filedotByIdHttpMethod!=null && filedotByIdHttpMethod.containsKey("msg") && StringUtils.isNotEmpty(filedotByIdHttpMethod.getString("msg"))){
							message = filedotByIdHttpMethod.getString("msg");
						}else{
							message = "查看tif文件失败！";
						}
					}
				}
			}else{
				if(loginRetBackObj!=null && loginRetBackObj.containsKey("msg") && StringUtils.isNotEmpty(loginRetBackObj.getString("msg"))){
					message = loginRetBackObj.getString("msg");
				}else{
					message = "登录失败！";
				}
				//登录失败
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "操作异常！";
		}
		if("200".equals(code) && retUrl==null){
			code = "500";
			message = "tif文件查询异常或者失败!";
		}
		JSONObject result = new JSONObject();
		result.put("code", code);
		result.put("message", message);
		result.put("data", (wuri.endsWith("/")?wuri:wuri+"/")+retUrl);
		return result.toJSONString().getBytes("UTF-8");
	}
	
	public static void main(String[] args) {
        String uri = "http://zygs.fzmt.com.cn:6078";
        String action = "mobileLogin";
        String did = "120";
        String sid = UUID32.getUUID();
        JSONObject obj = new JSONObject();
    	obj.put("form", "");
    	obj.put("action", action);
    	obj.put("did", did);
    	obj.put("sid", sid);
    	obj.put("pageIndex", "0");
    	obj.put("pageSize", "0");
    	JSONObject param = new JSONObject();
    	param.put("uid", "admin");
    	param.put("pwd", "fj87668438");
    	obj.put("param", param);
    	
        String retStr = WpfSoaHttpUtils.baseHttpMethod(uri, obj);
        if(StringUtils.isNotEmpty(retStr)){
        	System.out.println(retStr);
        	JSONObject retObj = JSONObject.parseObject(retStr);
        	//登录成功
        	if(retObj.getIntValue("code") == 0){
        		
        	}
        }
	}

}
