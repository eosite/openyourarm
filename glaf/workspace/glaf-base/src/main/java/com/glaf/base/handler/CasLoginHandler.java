package com.glaf.base.handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.config.BaseConfiguration;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.AuthorizeService;
import com.glaf.base.online.service.UserOnlineLogService;
import com.glaf.base.utils.CasClientUtils;
import com.glaf.core.config.Configuration;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.context.ContextFactory;

public class CasLoginHandler implements LoginHandler {
	private static final Log logger = LogFactory.getLog(CasLoginHandler.class);

	private static Configuration conf = BaseConfiguration.create();
	@Override
	public SysUser doLogin(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		//获取系统ID
		String appId=request.getParameter("appId");
		//从系统参数获取
		if(StringUtils.isEmpty(appId))
		{
			appId=SystemConfig.getString("sso_app_id");
		}
		if(StringUtils.isEmpty(appId)){
			logger.error("系统未在统一认证平台注册");
			return null;
		}
		//获取TGT
		String tgt=request.getParameter("TGT");
		SysUser bean = null;
		if(StringUtils.isEmpty(tgt)){
			//从cookie获取
			Cookie[] cookies=request.getCookies();
			for(Cookie cookie:cookies){
				if(cookie.getName().equals("TGT")){
					tgt=cookie.getValue();
					break;
				}
			}
		}
		if(StringUtils.isEmpty(tgt)){
			logger.error("登录TGT未发现");
			return null;
		}else{
			//获取ticket服务地址
			String ticketServer=SystemConfig.getString("ticket_service_address");
			if(StringUtils.isEmpty(ticketServer)){
				logger.error("SSO认证服务器未定义,请到系统参数页面进行配置");
				return null;
			}
			//获取ticket验证地址
			String validate_address=SystemConfig.getString("sso_validate_address");
			if(StringUtils.isEmpty(validate_address)){
				logger.error("客户端登录票据验证服务地址未定义,请到系统参数页面进行配置");
				return null;
			}
			//获取SSO当前注册应用的服务id
			String service=request.getParameter("service");
			if(StringUtils.isEmpty(service)){
				service=SystemConfig.getString("register_service_id");
			}
			if(StringUtils.isEmpty(service)){
				String path = request.getContextPath();
				service = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
			}
			
			String ticket=CasClientUtils.getServiceTicket(ticketServer, tgt, service,appId);
			if(StringUtils.isEmpty(ticket)){
				logger.error("未获取到客户端服务登录的有效票据");
				return null;
			}
			//获取st验证结果信息
			String userinfo=CasClientUtils.getTicketValidateInfo(validate_address, ticket, service,appId);
			if(StringUtils.isEmpty(userinfo)){
				logger.error("未获取到登录用户身份信息");
				return null;
			}
			 //获取appUserid
	        String appUserId=null;
	        Pattern p = Pattern.compile("cas:appUser>([^</]+)</cas:appUser");//正则表达式 commend by danielinbiti  
	        Matcher m = p.matcher(userinfo);//  
	        while (m.find()) {  
	        	appUserId=m.group(1);
	        	break;
	        }
			//appUser为空则获取userid
	        if(appUserId==null){
		        p = Pattern.compile("cas:user>([^</]+)</cas:user");//正则表达式 commend by danielinbiti  
		        m = p.matcher(userinfo);//  
		        while (m.find()) {  
		        	appUserId=m.group(1);
		        	break;
		        }
	        }
	        AuthorizeService authorizeService = ContextFactory.getBean("authorizeService");
			bean = authorizeService.login(appUserId);
			if(bean==null){
				return bean;
			}

			UserOnlineLogService userOnlineLogService = ContextFactory.getBean("userOnlineLogService");
			// 如果每天登录次数超过设置值，设置用户对象为空，防止恶意操作
			if (bean != null
					&& userOnlineLogService.getLoginCount(appUserId) > conf.getInt("limit.loginCount", 1000)) {
				return null;
			}
		}
		return bean;
	}

}
