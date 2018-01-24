package com.glaf.core.interceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.glaf.core.util.http.HttpClientUtils;

@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }),
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
				RowBounds.class, ResultHandler.class }) })
public class MybatisInterceptor implements Interceptor {
	private Properties properties;

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		// 获取是否远程访问的环境变量
		String DAO_TYPE = System.getProperty("DAO_TYPE");
		if (StringUtils.isEmpty(DAO_TYPE) || !DAO_TYPE.equals("P")) {
			return invocation.proceed();
		}
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		// 获取参数Id
		String sqlId = mappedStatement.getId();
		// 获取数据库ID
		String databaseId = mappedStatement.getDatabaseId();
		//返回类型
		ResultSetType resultSetType = mappedStatement.getResultSetType();
		//返回参数类型
		String returnClassName = null;
		if (resultSetType != null && resultSetType.getDeclaringClass() != null) {
			returnClassName = resultSetType.getDeclaringClass().getName();
		}
		returnClassName = mappedStatement.getResultMaps() != null && mappedStatement.getResultMaps().size() > 0
				? mappedStatement.getResultMaps().get(0).getType().getName() : null;
		//获取操作类型
		StatementType statementType = mappedStatement.getStatementType();
		String commandName = mappedStatement.getSqlCommandType().name();
		String opera = "";
		if (commandName.startsWith("INSERT")) {
			opera = "insert";
		} else if (commandName.startsWith("UPDATE")) {
			opera = "update";
		} else if (commandName.startsWith("DELETE")) {
			opera = "delete";
		} else if (commandName.startsWith("SELECT")) {
			opera = "select";
		}
		/*
		 * // 获取参数值 ParameterMap parameterMap =
		 * mappedStatement.getParameterMap(); List<ParameterMapping> paramList =
		 * parameterMap.getParameterMappings(); String paramsJSON =
		 * JSONObject.toJSONString(paramList);
		 */
		//获取方法名
		String methodName = invocation.getMethod().getName();
		// 获取参数对象
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("opera", opera);
		SerializeConfig.getGlobalInstance().setAsmEnable(false); // 序列化的时候关闭ASM
		ParserConfig.getGlobalInstance().setAsmEnable(false); // 反序列化的时候关闭ASM
		//获取后台服务根路径
		String serverAddress=System.getProperty("SERVER_ADDRESS");
		if (methodName.equals("query")) {
			Object parameter = invocation.getArgs()[1];
			// 获取参数类类型
			String paramType = parameter.getClass().getName();
			paramMap.put("paramType", paramType);
			paramMap.put("sqlId", sqlId);
			String paramsJSON = JSONObject.toJSONString(parameter);
			paramMap.put("params", paramsJSON);
			String resultStr = HttpClientUtils.doPost(serverAddress+"/service/dao/api/call", paramMap);

			if (StringUtils.isNotEmpty(resultStr)) {
				JSONObject resultJson = JSONObject.parseObject(resultStr);
				Object dataObj = resultJson.get("data");
				List result = new ArrayList();
				if (dataObj instanceof JSONArray) {
					ResultHandler resultHandler = (ResultHandler) invocation.getArgs()[3];
					// JSON转Object

					JSONArray jsonArray = (JSONArray)dataObj;
					JSONObject json = null;
					Object returnClassObj = null;
					// 创建class
					Class clazz = Class.forName(returnClassName);
					// 获取JSON转对象方法
					Method method = null;
					try {
						if(!sqlId.equals("com.glaf.form.core.mapper.FormAttachmentMapper.getFormAttachmentById")&&!sqlId.equals("com.glaf.web.mapper.PageResourceMapper.getPageResourceByFilePath")){
							method = clazz.getMethod("jsonToObject", JSONObject.class);
						}else{
							method = clazz.getMethod("jsonToObjectFull", JSONObject.class);
						}
						
					} catch (Exception e) {

					}
					if (method != null) {
						for (int i = 0; i < jsonArray.size(); i++) {
							json = jsonArray.getJSONObject(i);
							// 创建class实例
							returnClassObj = clazz.newInstance();
							Object obj = method.invoke(returnClassObj, json);
							result.add(obj);
						}
					} else {
						List addResult = getReturnList(jsonArray, returnClassName);
						result.addAll(addResult);
					}
				} else {
					result = new ArrayList();
					result.add(dataObj);
				}
				return result;
			}
		} else if (methodName.equals("update")) {
			Object parameter = invocation.getArgs()[1];
			// 获取参数类类型
			String paramType = parameter.getClass().getName();
			String paramsJSON = JSONObject.toJSONString(parameter);
			paramMap.put("paramType", paramType);
			paramMap.put("sqlId", sqlId);
			paramMap.put("params", paramsJSON);
			String resultStr = HttpClientUtils.doPost(serverAddress+"/service/dao/api/call", paramMap);
			return 1;
		}
		return invocation.proceed();
	}

	public List getReturnList(JSONArray jsonArray, String classType) throws ClassNotFoundException {
		return JSONObject.parseArray(jsonArray.toJSONString(), Class.forName(classType));
	}

	@Override
	public Object plugin(Object target) {
		// TODO Auto-generated method stub
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub
		this.properties = properties;
	}

}
