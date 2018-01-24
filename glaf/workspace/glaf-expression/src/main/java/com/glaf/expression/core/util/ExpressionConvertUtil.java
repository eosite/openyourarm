package com.glaf.expression.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.wltea.expression.ExpressionEvaluator;
import org.wltea.expression.datameta.Variable;

import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.security.BaseIdentityFactory;
import com.glaf.core.config.Environment;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.identity.User;
import com.glaf.core.security.Authentication;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.EntityService;
import com.glaf.core.util.IOUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.PropertiesUtils;
import com.glaf.core.util.UUID32;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 表达式转换工具类
 * 
 * @author magoo
 * 
 */
public class ExpressionConvertUtil {
	private static final Log logger = LogFactory.getLog(ExpressionConvertUtil.class);

	public static final String DATABASE_TYPE = "database";
	public static final String JAVASCRIPT_TYPE = "javascript";

	private static AtomicBoolean loading = new AtomicBoolean(false);
	private static volatile Properties properties = new Properties();

	private static Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);

	/*
	 * $VALIDATELENGTH $VALIDATEDATE $VALIDATETYPE $VALIDATE $VALIDATECHECKNUM
	 */
	private static List<String> vaildateVars = new ArrayList<String>() {
		{
			add("$VALIDATELENGTH");
			add("$VALIDATEDATE");
			add("$VALIDATETYPE");
			add("$VALIDATE");
			add("$VALIDATECHECKNUM");
			add("$VALIDATEFILESUPLOAD");
		}
	};

	/**
	 * 获取用户角色sql
	 */
	static String roleSql = "(SELECT r.code ROLECODE FROM net_role r INNER JOIN userrole ur ON r.ID = ur.ROLEID INNER JOIN UserInfo u ON ur.USERID = u.USERID WHERE r.CODE IS NOT NULL AND (r.CODE <> '' or r.CODE <> ' ') AND UR.UserID = '%s')";

	static {
		reload();
	}

	/***
	 * 验证定义器函数转换(专用)
	 * 
	 * @param exptessionString
	 * @param type
	 * @return
	 */
	public static String vaildateFunConvert(String exptessionString, String varName) {
		String str = constantConvert(exptessionString, JAVASCRIPT_TYPE, null);
		if (varName == null) {
			varName = "";
		}

		// 解析函数 方法 $CONC
		String regex = "(\\$\\w*)(\\()(~| |((-)?\\d+)|(\\\")|(\\w*))";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sbr2 = new StringBuffer(); // 替换后的字符串
		String regexToString;
		String varNameTemp = varName;
		while (matcher.find()) {
			regexToString = replaceFunctionName(matcher.group(1), JAVASCRIPT_TYPE) + matcher.group(2);
			if (null != regexToString) {
				varNameTemp = varName;
				if (matcher.group(3) != null && !"".equals(matcher.group(3))) {
					if (varName != null && !"".equals(varName)) {
						varNameTemp = varName + ",";
					}
				}
				if (!vaildateVars.contains(matcher.group(1))) {
					varNameTemp = "";
				}
				matcher.appendReplacement(sbr2,
						java.util.regex.Matcher.quoteReplacement(regexToString + varNameTemp + matcher.group(3)));
			}
		}
		matcher.appendTail(sbr2);

		return sbr2.toString();
	}

	/**
	 * 表达式转换
	 * 
	 * @param exptessionString
	 * @param type
	 * @param request
	 * @return
	 */
	public static String expressionConvert(String exptessionString, String type) {
		return expressionConvert(exptessionString, type, "");
	}

	/**
	 * 带参 表达式转换
	 * 
	 * @param exptessionString
	 * @param type
	 * @param request
	 * @return
	 */
	public static String expressionConvert(String exptessionString, String type, Map<String, Object> map) {
		return getFunctionConvert(constantConvert(exptessionString, type, map), type, "", map);
	}

	/**
	 * 表达式转换
	 * 
	 * @param exptessionString
	 * @param type
	 * @param request
	 * @param prefix
	 *            字段前缀(增加在字段和变量前面的)
	 * @return
	 */
	public static String expressionConvert(String exptessionString, String type, String prefix) {
		return getFunctionConvert(constantConvert(exptessionString, type, null), type, prefix, null);
	}

	public static String expressionConvert2(String exptessionString, String type, String prefix) {
		return getFunctionConvert2(constantConvert(exptessionString, type, null), type, prefix, null);
	}

	/**
	 * 常量转换
	 * 
	 * @param exptessionString
	 * @param type
	 * @return
	 */
	public static String constantConvert(String exptessionString, String type, Map<String, Object> map) {
		// 常量处理 SysConstantFunc
		/*
		 * $SYS_NAME() ${sys.name} $SYS_VERSION() $CURR_USERNAME(~CONST)
		 * $CURR_ACCOUNT(~CONST) $CURR_DEPT(~CONST)
		 */
		String regex = "(\\$\\w*)\\([~CONST]{0,}\\)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(exptessionString);
		StringBuffer sbr = new StringBuffer(); // 替换后的字符串
		String regexToString = "";

		while (matcher.find()) {
			String convertName = matcher.group(1);
			regexToString = replaceConstantName(convertName, type, map);
			if (null != regexToString) {
				matcher.appendReplacement(sbr, java.util.regex.Matcher.quoteReplacement(regexToString));
			}
		}
		matcher.appendTail(sbr);
		return sbr.toString();
	}

	/**
	 * 函数转换
	 * 
	 * @param exptessionString
	 * @param type
	 * @return
	 */
	public static String getFuncConvert(String exptessionString, String type) {
		// 解析函数 方法 $CONC //(\\(\\w*\\.?\\(?\\w*\\)?(,\\w*\\.?\\w*\\)*))
		String regex = "(\\$\\w*)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(exptessionString);
		StringBuffer sbr2 = new StringBuffer(); // 替换后的字符串
		String regexToString = "";
		while (matcher.find()) {
			regexToString = replaceFunctionName(matcher.group(1),
					type)/*
							 * + matcher . group (2)
							 */;
			if (null != regexToString) {
				matcher.appendReplacement(sbr2, java.util.regex.Matcher.quoteReplacement(regexToString));
			}
		}
		matcher.appendTail(sbr2);
		return getJavaFuncConvert(sbr2.toString(), type);
	}

	/**
	 * java函数转换
	 * 
	 * @return
	 */
	public static String getJavaFuncConvert(String exptessionString, String type) {
		if (DATABASE_TYPE.equals(type)) {
			String regex = "((\\~\\!\\w*)(\\([^\\(\\)]*\\)))";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(exptessionString);
			StringBuffer sbr2 = new StringBuffer(); // 替换后的字符串
			String regexToString = "";
			while (matcher.find()) {
				System.out.println(matcher.group(1));
				regexToString = evaluate(matcher.group(1).replace("~!", "$"));
				if (null != regexToString) {
					matcher.appendReplacement(sbr2, java.util.regex.Matcher.quoteReplacement(regexToString));
				}
			}
			matcher.appendTail(sbr2);
			return sbr2.toString();
		} else {
			return exptessionString;
		}
	}

	private static String evaluate(String expression) {
		Object result = ExpressionEvaluator.evaluate(expression, new ArrayList<Variable>());
		if (result != null) {
			return result.toString();
		} else {
			return "";
		}
	}

	/**
	 * 函数转换
	 * 
	 * @param exptessionString
	 * @param type
	 *            DATABASE_TYPE 和 JAVASCRIPT_TYPE
	 * @return
	 */
	private static String getFunctionConvert(String exptessionString, String type, String prefix,
			Map<String, Object> map) {

		// String exptessionString =
		// "$CONC(~F{default.cell_useradd7028.name},~F{default.cell_useradd7028.cell_useradd7028_user2})"
		// ;
		// 首先解析字段~F{}
		// String regex = "~F\\{(.+?)\\}" ;
		// String regex = "~F\\{([\\w\\.]*)\\}" ;
		String regex = "~F\\{(\\w*\\.)(\\w*\\.)(\\w*)\\}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(exptessionString);
		StringBuffer sbr = new StringBuffer(); // 替换后的字符串
		String regexToString = "";
		while (matcher.find()) {
			switch (type) {
			case DATABASE_TYPE: // 数据库
				Object val;
				// if (map == null) {
				// regexToString = matcher.group(2) + matcher.group(3);
				// } else {
				// String key = matcher.group(3);
				// if (map.containsKey(key)) {
				// if ((val = map.get(key)) != null
				// && StringUtils.isNotBlank(val.toString())) {
				// regexToString = val.toString();
				// } else {
				// regexToString = "";
				// }
				// } else {
				// regexToString = matcher.group(2) + matcher.group(3);
				// }
				// }

				regexToString = matcher.group(2) + matcher.group(3);
				if (map != null) {
					String m1 = matcher.group(1);
					String m2 = matcher.group(2);
					String key = matcher.group(3);
					if (m1.equalsIgnoreCase(m2) && m2.equalsIgnoreCase(key + ".")) {
						if ((val = map.get(key)) != null && StringUtils.isNotBlank(val.toString())) {
							regexToString = val.toString();
						} else {
							regexToString = "";
						}
					}
				}
				break;
			case JAVASCRIPT_TYPE:// javascript
				regexToString = prefix + matcher.group(3);
				break;
			default:
				break;
			}
			matcher.appendReplacement(sbr, java.util.regex.Matcher.quoteReplacement(regexToString));
		}
		matcher.appendTail(sbr);

		/*
		 * // 解析函数 方法 $CONC //(\\(\\w*\\.?\\(?\\w*\\)?(,\\w*\\.?\\w*\\)*)) regex
		 * = "(\\$\\w*)"; pattern = Pattern.compile(regex); matcher =
		 * pattern.matcher(sbr.toString()); StringBuffer sbr2 = new
		 * StringBuffer(); // 替换后的字符串 while (matcher.find()) { regexToString =
		 * replaceFunctionName(matcher.group(1), type) + matcher . group (2) ;
		 * if (null != regexToString) { matcher.appendReplacement(sbr2,
		 * java.util.regex.Matcher.quoteReplacement(regexToString)); } }
		 * matcher.appendTail(sbr2); return sbr2.toString();
		 */
		return getFuncConvert(sbr.toString(), type);
	}

	/**
	 * 函数转换
	 * 
	 * @param exptessionString
	 * @param type
	 *            DATABASE_TYPE 和 JAVASCRIPT_TYPE
	 * @return
	 */
	private static String getFunctionConvert2(String exptessionString, String type, String prefix,
			Map<String, Object> map) {

		// String exptessionString =
		// "$CONC(~F{default.cell_useradd7028.name},~F{default.cell_useradd7028.cell_useradd7028_user2})"
		// ;
		// 首先解析字段~F{}
		// String regex = "~F\\{(.+?)\\}" ;
		// String regex = "~F\\{([\\w\\.]*)\\}" ;
		String regex = "~F\\{(\\w*\\.)(\\w*\\.)(\\w*)\\}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(exptessionString);
		StringBuffer sbr = new StringBuffer(); // 替换后的字符串
		String regexToString = "";
		while (matcher.find()) {
			switch (type) {
			case DATABASE_TYPE: // 数据库
				Object val;
				// if (map == null) {
				// regexToString = matcher.group(2) + matcher.group(3);
				// } else {
				// String key = matcher.group(3);
				// if (map.containsKey(key)) {
				// if ((val = map.get(key)) != null
				// && StringUtils.isNotBlank(val.toString())) {
				// regexToString = val.toString();
				// } else {
				// regexToString = "";
				// }
				// } else {
				// regexToString = matcher.group(2) + matcher.group(3);
				// }
				// }

				regexToString = matcher.group(2) + matcher.group(3);
				if (map != null) {
					String m1 = matcher.group(1);
					String m2 = matcher.group(2);
					String key = matcher.group(3);
					if (m1.equalsIgnoreCase(m2) && m2.equalsIgnoreCase(key + ".")) {
						if ((val = map.get(key)) != null && StringUtils.isNotBlank(val.toString())) {
							regexToString = val.toString();
						} else {
							regexToString = "";
						}
					}
				}
				break;
			case JAVASCRIPT_TYPE:// javascript
				if (matcher.group(2).equals(matcher.group(3))) {
					regexToString = prefix.split("\\.")[0] + "." + matcher.group(3);
				} else if (!prefix.contains(matcher.group(2).substring(0, matcher.group(2).length() - 1) + "_0_")) {
					regexToString = prefix.split("\\.")[0] + "."
							+ matcher.group(2).substring(0, matcher.group(2).length() - 1) + "_0_" + matcher.group(3);
				} else {
					regexToString = prefix + matcher.group(3);
				}
				break;
			default:
				break;
			}
			matcher.appendReplacement(sbr, java.util.regex.Matcher.quoteReplacement(regexToString));
		}
		matcher.appendTail(sbr);

		/*
		 * // 解析函数 方法 $CONC //(\\(\\w*\\.?\\(?\\w*\\)?(,\\w*\\.?\\w*\\)*)) regex
		 * = "(\\$\\w*)"; pattern = Pattern.compile(regex); matcher =
		 * pattern.matcher(sbr.toString()); StringBuffer sbr2 = new
		 * StringBuffer(); // 替换后的字符串 while (matcher.find()) { regexToString =
		 * replaceFunctionName(matcher.group(1), type) + matcher . group (2) ;
		 * if (null != regexToString) { matcher.appendReplacement(sbr2,
		 * java.util.regex.Matcher.quoteReplacement(regexToString)); } }
		 * matcher.appendTail(sbr2); return sbr2.toString();
		 */
		return getFuncConvert(sbr.toString(), type);
	}

	/**
	 * 替换常量名称
	 * 
	 * @param group
	 * @param type
	 * @return
	 * @throws Exception
	 */
	private static String replaceConstantName(String name, String type, Map<String, Object> map)
			throws RuntimeException {
		String replaceName = null;
		switch (type) {
		case DATABASE_TYPE: // 数据库
			type = "sqlserver";
			String convert = properties.getProperty(type + ".convert." + name);
			if (convert != null) {
				LoginContext loginContext = getDefaultLoginContext();
			
				JSONObject dataModel =null;
				
				if(loginContext != null){
					
					User currentUser = loginContext.getUser();
					
					dataModel = initDataModel(currentUser); // currentUser.toJsonObject();
				} else {
					dataModel = new JSONObject();
				}
				
				Long deptId = dataModel.getLong("deptId");
				
				String actorId = dataModel.getString("actorId");

				dataModel.put("params", map);

				switch (name) {
				case "$SYS_NAME":
					replaceName = convert.replace("{0}", dataModel.getString("res_system_name"));
					break;
				case "$SYS_VERSION":
					replaceName = convert.replace("{0}", dataModel.getString("res_version"));
					break;
				case "$CURR_USERNAME":
					replaceName = convert.replace("{0}", dataModel.getString("name") /*currentUser.getName()*/);
					break;
				case "$CURR_ACCOUNT":
					replaceName = convert.replace("{0}", actorId /*currentUser.getActorId()*/);
					break;
				case "$CURR_DEPT":
					
					String departName = BaseIdentityFactory.getDepartmentById( deptId /*currentUser.getDeptId()*/) != null
							? BaseIdentityFactory.getDepartmentById(deptId).getName() : "";
					replaceName = convert.replace("{0}", departName);
					break;
				case "$CURR_ROLE": // 用户角色code(多个[返回sql])

					replaceName = String.format(roleSql,actorId/* currentUser.getActorId()*/);

					// departName =
					// BaseIdentityFactory.getDepartmentById(currentUser.getDeptId())
					// != null
					// ?
					// BaseIdentityFactory.getDepartmentById(currentUser.getDeptId()).getName()
					// : "";

					replaceName = convert.replace("{0}", replaceName);
					break;
				case "$CURR_DEPT_CODE":
					
					deptId = dataModel.getLong("deptId");
					
					String departId = BaseIdentityFactory.getDepartmentById(deptId) != null
							? BaseIdentityFactory.getDepartmentById(deptId).getCode() : "";
					replaceName = convert.replace("{0}", departId);
					break;
				case "$USER_ID":
					if (map != null && map.containsKey("tableName")) {
						// IdGenerator idGenerator =
						// ContextFactory.getBean("idGenerator");
						EntityService entityService = ContextFactory.getBean("entityService");
						String id = entityService.getNextId(ParamUtils.getString(map, "tableName"),
								ParamUtils.getString(map, "idColumn", "id"), actorId/*currentUser.getActorId()*/);
						logger.debug("$USER_ID-->" + id);
						replaceName = convert.replace("{0}", id);
					}
					break;
				case "$UUID":
					replaceName = convert.replace("{0}", UUID32.getUUID());
					break;
				case "$OID":
					if (map != null && map.containsKey("tableName")) {
						IdGenerator idGenerator = ContextFactory.getBean("idGenerator");
						String id = idGenerator.getNextId(ParamUtils.getString(map, "tableName") + "_SEQ");

						replaceName = convert.replace("{0}", id);
					}
					break;
				default:
					if (StringUtils.isNotBlank(convert)) {
						replaceName = process(convert, dataModel);
					}
					break;
				}
			}
			break;
		case JAVASCRIPT_TYPE:// javascript
			/*
			 * $SYS_NAME() ${res_system_name} 系统名称 $SYS_VERSION() ${res_version}
			 * 系统版本 $CURR_USERNAME(~CONST) ${username} 用户名 $CURR_ACCOUNT(~CONST)
			 * ${actorId} 用户帐户 $CURR_DEPT(~CONST) ${userdept} 用户部门
			 */
			convert = properties.getProperty(type + ".convert." + name);
			if (convert != null) {
				replaceName = convert;
			}
		default:
			break;
		}
		return replaceName;
	}

	static JSONObject initDataModel(User currentUser) {

		JSONObject dataModel = currentUser.toJsonObject();

		SysDepartment sysDepartment = BaseIdentityFactory.getDepartmentById(currentUser.getDeptId());

		dataModel.put("userdept", sysDepartment != null ? sysDepartment.getName() : "");// 用户部门
		dataModel.put("deptId", currentUser.getDeptId() + "");// 部门ID
		dataModel.put("deptCode", sysDepartment != null ? sysDepartment.getCode() : "");// 部门代码
		SysDepartment parentSysDepartment = sysDepartment != null
				? BaseIdentityFactory.getDepartmentById(sysDepartment.getParentId()) : null;
		dataModel.put("parentDeptId", parentSysDepartment != null ? parentSysDepartment.getId() + "" : ""); // 父部门ID
		dataModel.put("parentDept", parentSysDepartment != null ? parentSysDepartment.getName() : "");// 父部门名
		dataModel.put("parentDeptCode", parentSysDepartment != null ? parentSysDepartment.getCode() : "");// 父部门代码

		for (String sc : new String[] { "res_system_name", "res_version" }) {
			dataModel.put(sc, SystemConfig.getString(sc));
		}
		
		dataModel.put("currentDate", new Date());

		return dataModel;
	}

	/**
	 * 模版转换
	 * 
	 * @param str
	 * @param dataModel
	 * @return
	 */
	static String process(String str, Object dataModel) {
		try {
			str = FreeMarkerTemplateUtils.processTemplateIntoString(//
					new Template("", str, cfg), dataModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 替换函数名称
	 * 
	 * @param name
	 * @param type
	 * @return
	 */
	private static String replaceFunctionName(String name, String type) {
		// 通过读取配置文件
		// CustomProperties.reload();
		// return CustomProperties.getString(type+"."+name);
		if (DATABASE_TYPE.equalsIgnoreCase(type)) {
			type = "sqlserver";
		}
		return properties.getProperty(type + "." + name);
	}

	public static void main(String[] args) {
		// String exptessionString =
		// "我是蛮蛮$SYS_NAME()$CURR_USERNAME(~CONST)$CONC(~F{default.cell_useradd7028.name},~F{default.cell_useradd7028.cell_useradd7028_user2})"
		// ;
		// System.out.println(expressionConvert(exptessionString,
		// ExpressionConvertUtil.JAVASCRIPT_TYPE,null));
		/*
		 * String exptessionString =
		 * "$VALIDATE()$VALIDATE2(A)你知道什么啊$VALIDATE(\"xxx\")嘻嘻嘻$VALIDATE2()$VALIDATE(~F{default.cell_useradd7028.name})就$VALIDATE2(-1)";
		 * System.out.println(vaildateFunConvert(exptessionString, "k"));
		 * exptessionString = "$UUID()"; exptessionString =
		 * constantConvert(exptessionString, "database", null);
		 */

		// String exptessionString = "~!SPLITSTR(\"\",\",\",\"'\")wddnKKK(sss)";
		// exptessionString = getJavaFuncConvert(exptessionString, "database");
		// System.out.println(exptessionString);

		// String str = "1231a=123456aa6789aa123";
		// str = "~!SPLITSTR(\"11\",\",\",\"'\")kkk$(sss)";
		// // [^ab]*
		// String reg = ".*(?<=a=)([^aa]*)(?=aa).*";
		// reg = "(\\~\\!\\w*)(\\([^\\(\\)]*\\)).*";

		// System.out.println(str.replaceFirst(reg, "$2"));

		// exptessionString = "~!OID(\"KLAUS\"), ~!UUID(), ~!SEQ(\"KLAUS\")，
		// ~!MAXVAL(\"sys_user\",\"userid\")，
		// ~!FORMAT(\"8989\",\"00000000\")，"//
		// + "~!GETFD(\"yyyyMMdd\")";
		//
		//
		// exptessionString = expressionConvert("~!GETFD(\"yyyyMMdd\")",
		// ExpressionConvertUtil.DATABASE_TYPE,
		// new HashMap<String, Object>());
		//
		//
		// System.out.println(exptessionString);
		//
		// System.out.println(exptessionString);
		//
		// str = "$SEQ(MY, you)";

		// "(\\$\\w*)\\([~CONST]{0,}\\)"

		// String regex = "(\\$\\w*)\\((.*?)\\)";

		// regex = "~F\\{(\\w*\\.)(\\w*\\.)(\\w*)\\}";
		/*
		 * Pattern pattern = Pattern.compile(regex); Matcher matcher =
		 * pattern.matcher(str); while (matcher.find()) {
		 * System.out.println(matcher.group(0));
		 * System.out.println(matcher.group(1)); }
		 */

		// constantConvert();

		System.out.println(vaildateFunConvert("$VALIDATEDATE($DIFFDATETIME('','s'))", "kk"));
	}

	private static void reload() {
		if (!loading.get()) {
			InputStream inputStream = null;
			try {
				loading.set(true);
				String config = SystemProperties.getConfigRootPath() + "/conf/props/expression";
				logger.debug(config);
				File directory = new File(config);
				if (directory.exists()) {
					File[] filelist = directory.listFiles();
					if (filelist != null) {
						setPropertys(filelist);
					}
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			} finally {
				loading.set(false);
				IOUtils.closeStream(inputStream);
			}
		}
	}

	private static void setPropertys(File[] filelist) {
		InputStream inputStream = null;
		try {
			if (filelist != null) {
				for (int i = 0, len = filelist.length; i < len; i++) {
					File file = filelist[i];
					if (file.isDirectory()) {
						File[] subfile = file.listFiles();
						setPropertys(subfile); // 循环子目录
					}
					if (file.isFile() && file.getName().endsWith(".properties")) {
						logger.info("load properties:" + file.getAbsolutePath());
						inputStream = new FileInputStream(file);
						Properties p = PropertiesUtils.loadProperties(inputStream);
						if (p != null) {
							Enumeration<?> e = p.keys();
							while (e.hasMoreElements()) {
								String key = (String) e.nextElement();
								String value = p.getProperty(key);
								properties.setProperty(key, value);
								properties.setProperty(key.toLowerCase(), value);
								properties.setProperty(key.toUpperCase(), value);
							}
						}
						IOUtils.closeStream(inputStream);
					}
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			loading.set(false);
			IOUtils.closeStream(inputStream);
		}
	}

	/**
	 * 获取当前系统用户session
	 * 
	 * @return
	 */
	public static LoginContext getDefaultLoginContext() {
		String systemName = Environment.getCurrentSystemName();
		LoginContext loginContext = null;
		try {
			Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
			loginContext = Authentication.getLoginContext();
		} finally {
			Environment.setCurrentSystemName(systemName);
		}
		return loginContext;
	}

}
