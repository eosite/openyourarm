package com.glaf.platforms.rule.app;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.platforms.rule.FormPageParserUtil;

/**
 * 用于处理数据源规则
 *
 */
public class DataSourceApp {
	protected static final Log logger = LogFactory
			.getLog(DataSourceApp.class);
	
	public DataSourceApp() {
	}
	
	public static String getDataStr(Object model,Method[] methods,String templateStr){
		String outPutStr = templateStr.toString() ;
		//正则替换对应的信息
		String regex = "" ;
		try {
			String name = "" ;
			for (Method m : methods) {
				if(m.getName().startsWith("get") || m.getName().startsWith("is")){
					Object[] args = null;
					Object methodReturnValue = m.invoke(model, args);
					if(methodReturnValue != null && !"".equals(methodReturnValue.toString())){
						name = (m.getName().startsWith("get")?m.getName().replaceFirst("get", ""):m.getName().replaceFirst("is", "")).toLowerCase();
						regex = "\\{\\{"+name+"\\}\\}" ;
						outPutStr = FormPageParserUtil.replaceByRegex(outPutStr, regex, methodReturnValue.toString());
					}
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			logger.error("DataSourceApp : "+e.getMessage());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			logger.error("DataSourceApp : "+e.getMessage());
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			logger.error("DataSourceApp : "+e.getMessage());
		}
		//正则匹配  匹配    xxx : "{{xxxx}}"
		regex = "\\w+\\ *\\:\\ *\\\"?\\{\\{\\w+\\}\\}\\\"?,?" ;
		outPutStr = FormPageParserUtil.replaceByRegex(outPutStr, regex, "");
		regex = "\\\"?\\{\\{\\w+\\}\\}" ;
		outPutStr = FormPageParserUtil.replaceByRegex(outPutStr, regex, "");
		regex = "'##=" ;
		outPutStr = FormPageParserUtil.replaceByRegex(outPutStr, regex, "");
		regex = "=##'" ;
		outPutStr = FormPageParserUtil.replaceByRegex(outPutStr, regex, "");
		return outPutStr ;
	}
}
