package com.glaf.base.handler;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.config.BaseConfiguration;
import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.sys.model.BaseDataInfo;
import com.glaf.base.modules.sys.model.SysRole;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.AuthorizeService;
import com.glaf.base.modules.sys.service.SysRoleService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.online.service.UserOnlineLogService;
import com.glaf.base.utils.ParamUtil;
import com.glaf.core.config.Configuration;
import com.glaf.core.config.Environment;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.StringTools;

/**
 * 手机短信登陆验证
 * @author ASUS
 *
 */
public class ValidateCodeLoginHandler implements LoginHandler {
	private static final Log logger = LogFactory.getLog(ValidateCodeLoginHandler.class);

	private static Configuration conf = BaseConfiguration.create();

	public boolean checkCode(HttpServletRequest request,String telephone,String validateCode){
		BaseDataManager bm = BaseDataManager.getInstance();
		com.glaf.base.modules.sys.model.BaseDataInfo info =null;
		//从字典中获取间隔时间
		info = bm.getBaseData("MsgValidTime","loginmsgvalid");
		String loginMsgValidTime = info.getValue();
		long maxLoginMsgValidTime = 0l;
		if(StringUtils.isNotEmpty(loginMsgValidTime)){
			maxLoginMsgValidTime = Long.valueOf(loginMsgValidTime);
		}
		
		String MsgValidCode = (String) request.getSession().getAttribute("MsgValidCode");
		String MsgValidTelephone = (String) request.getSession().getAttribute("MsgValidTelephone");
		long MsgValidTime =  (long) request.getSession().getAttribute("MsgValidTime");
		
		if(System.currentTimeMillis() - MsgValidTime > maxLoginMsgValidTime){
			//时间超过60s时，验证码过时
			return false;
		}
		
		//验证电话号码是否一致
		if(!StringUtils.equals(MsgValidTelephone,telephone)){
			return false;
		}
		//验证验证码是否一致
		if(!StringUtils.equals(MsgValidCode,validateCode)){
			return false;
		}
		
		request.getSession().removeAttribute("MsgValidCode");
		request.getSession().removeAttribute("MsgValidTelephone");
		request.getSession().removeAttribute("MsgValidTime");
		return true;
	}
	
	@Override
	public SysUser doLogin(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("----------------------ValidateCodeLoginHandler--------------------");
		String telephone = ParamUtil.getParameter(request, "x");
		//获取短信验证码
		String validateCode = ParamUtil.getParameter(request, "k");

		HttpSession session = request.getSession(false);
		String rand = (String) session.getAttribute("x_y");
		String rand2 = (String) session.getAttribute("x_z");

		SysUser bean = null;

		if (StringUtils.isNotEmpty(rand) && StringUtils.isNotEmpty(rand2)) {
//			if (rand != null) {
//				password = StringTools.replace(password, rand, "");
//			}
//
//			if (rand2 != null) {
//				password = StringTools.replace(password, rand2, "");
//			}
			
			BaseDataManager bm = BaseDataManager.getInstance();
			com.glaf.base.modules.sys.model.BaseDataInfo info =null;
			//从字典中获取间隔时间
			info = bm.getBaseData("MsgValidTime","loginmsgvalid");
			String loginMsgValidTime = info.getValue();
			long maxLoginMsgValidTime = 0l;
			if(StringUtils.isNotEmpty(loginMsgValidTime)){
				maxLoginMsgValidTime = Long.valueOf(loginMsgValidTime);
			}
			
			String MsgValidCode = (String) request.getSession().getAttribute("MsgValidCode");
			String MsgValidTelephone = (String) request.getSession().getAttribute("MsgValidTelephone");
			long MsgValidTime =  (long) request.getSession().getAttribute("MsgValidTime");
			
			if(System.currentTimeMillis() - MsgValidTime > maxLoginMsgValidTime){
				//时间超过60s时，验证码过时
				return null;
			}
			
			//验证电话号码是否一致
			if(!StringUtils.equals(MsgValidTelephone,telephone)){
				return null;
			}
			//验证验证码是否一致
			if(!StringUtils.equals(MsgValidCode,validateCode)){
				return null;
			}
			
			request.getSession().removeAttribute("MsgValidCode");
			request.getSession().removeAttribute("MsgValidTelephone");
			request.getSession().removeAttribute("MsgValidTime");
			
			logger.debug(telephone + " start login........................");
			logger.debug("currentSystemName:" + Environment.getCurrentSystemName());

			// 用户登陆，返回系统用户对象
			AuthorizeService authorizeService = ContextFactory.getBean("authorizeService");
			bean = authorizeService.authorizeByTelephone(telephone);
			
			if(bean == null){
				//创建新的用户，以该电话号码为用户账号。
				SysUserService sysUserService = ContextFactory.getBean("sysUserService");
				bean = new SysUser();
				bean.setAccount(telephone);
				bean.setMobile(telephone);
				bean.setCreateDate(new Date());
				bean.setStatus("0");
				bean.setName("游客");
				sysUserService.create(bean);
				//获取默认的角色id，添加对应的角色
				info = bm.getBaseData("defaultRoleIds","loginmsgvalid");
				//默认角色id
				SysRoleService sysRoleService = ContextFactory.getBean("sysRoleService");
				String objectIds = info.getValue();
				Set<SysRole> newRoles = new HashSet<SysRole>();
				if (StringUtils.isNotEmpty(objectIds)) {
					List<Long> ids = StringTools.splitToLong(objectIds);// 获取页面参数
					if (ids != null) {
						for (int i = 0; i < ids.size(); i++) {
							logger.debug("id[" + i + "]=" + ids.get(i));
							SysRole role = sysRoleService.findById(ids.get(i));// 查找角色对象
							if (role != null) {
								newRoles.add(role);// 加入到角色列表
							}
						}
						//角色授权
						sysUserService.updateUserRole(bean, newRoles);
					}
				}
			}
			
			logger.debug("current authorize User--->"+bean.getAccount());
			UserOnlineLogService userOnlineLogService = ContextFactory.getBean("userOnlineLogService");
			// 如果每天登录次数超过设置值，设置用户对象为空，防止恶意操作
			if (userOnlineLogService.getLoginCount(bean.getAccount()) > conf.getInt("limit.loginCount", 1000)) {
				bean = null;
			}
			logger.debug("sysuser is null --->"+(bean == null));
		}

		return bean;
	}

}
