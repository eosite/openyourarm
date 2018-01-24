package com.glaf.form.rule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.sys.model.SysRole;
import com.glaf.base.security.BaseIdentityFactory;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.security.Authentication;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.DateUtils;
import com.glaf.form.rule.util.ListUtil;

public class Global {
	public final static String MT_TOKEN = "mt_token";
	
	public final static String NAME = "A001-1-002"; // 控件名称

	public final static String TYPE = "A001-1-001"; // 控件类型

	public final static String PROPERTIES = "A001-3"; // 数据属性
	public final static String QUOTE_PROPERTIES = "A001-2"; // 引用属性

	public final static String PLATFORMS_PATH = "com.glaf.platforms.rule.";
	public final static String PAGE_PARSER_CLASS_NAME = PLATFORMS_PATH + "FormPageParserUtil";

	public static Map<String, Object> getGlobalVariables() {
		LoginContext loginContext = Authentication.getLoginContext();
		Map<String, Object> context = new HashMap<String, Object>();
		String departName = BaseIdentityFactory.getDepartmentById(loginContext.getUser().getDeptId()) != null
				? BaseIdentityFactory.getDepartmentById(loginContext.getUser().getDeptId()).getName() : "";
		context.put("res_system_name", SystemConfig.getString("res_system_name"));// 系统名称
		context.put("res_version", SystemConfig.getString("res_version"));// 系统版本
		context.put("userdept", departName);// 用户部门
		List<String> actorIds = new ArrayList<String>();
		actorIds.add(loginContext.getActorId());
		List<String> roles = BaseIdentityFactory.getUserRoleCodes(actorIds);
		context.put("roleArys", ListUtil.joinList(roles, ","));
		List<SysRole> sysRoles = BaseIdentityFactory.getUserRoles(actorIds);
		StringBuilder buff = new StringBuilder();
		for (SysRole role : sysRoles) {
			buff.append(role.getName()).append(",");
		}
		if (buff.length() > 2) {
			buff.delete(buff.length() - 1, buff.length());
		}
		context.put("roleNames", buff.toString());
		context.put("user", loginContext.getUser());
		context.put("actorId", loginContext.getActorId()); // 角色账号
		context.put("username", loginContext.getUser().getName());
		context.put("date", new SimpleDateFormat(DateUtils.SECOND_FORMAT).format(new Date()));
		return context;
	}

	public static String getOriginalColumnName(JSONObject mapping, String columnName) {
		if (mapping != null && mapping.containsKey(columnName)) {
			return mapping.getString(columnName);
		}
		return columnName;
	}
	
	/**
	 * 把一个JSONArray 转换成 树结构模型
	 * @param ary  原始数组
	 * @param id   主键
	 * @param pid  父id
	 * @param children  存放的下级节点名称
	 * @return
	 */
	public static JSONArray transformToTreeFormat(JSONArray ary, String id,String pid,String children){
		JSONArray rAry = new JSONArray();
		JSONObject hash = new JSONObject();
		int i = 0, j = 0, len = ary.size();
		for (; i < len; i++) {
			hash.put(ary.getJSONObject(i).getString(id), ary.get(i));
		}
		for (; j < len; j++) {
			JSONObject aVal = ary.getJSONObject(j), hashVP = hash.getJSONObject(aVal.getString(pid));
			if (hashVP!=null) {
				if(!hashVP.containsKey(children)){
					hashVP.put(children, new JSONArray());
				}
				hashVP.getJSONArray(children).add(aVal);
			} else {
				rAry.add(aVal);
			}
		}
		return rAry;
	}
	
	public static List<Map<String,Object>> transformToTreeFormat(List<Map<String,Object>> ary, String id,String pid,String children){
		List<Map<String,Object>> rAry = new ArrayList<Map<String,Object>>();
		Map<String,Object> hash = new HashMap<String,Object>();
		int i = 0, j = 0, len = ary.size();
		for (; i < len; i++) {
			hash.put(ary.get(i).get(id).toString(), ary.get(i));
		}
		for (; j < len; j++) {
			Map<String,Object> aVal = ary.get(j), hashVP = hash.containsKey(aVal.get(pid).toString())?(Map<String, Object>) hash.get(aVal.get(pid).toString()):null;
			if (hashVP!=null) {
				if(!hashVP.containsKey(children)){
					hashVP.put(children, new ArrayList<Map<String,Object>>());
				}
				((List<Map<String,Object>>)hashVP.get(children)).add(aVal);
			} else {
				rAry.add(aVal);
			}
		}
		return rAry;
	}
}
