package com.glaf.core.website.rest;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.service.DaoService;

@RestController
@RequestMapping("/dao/api")
public class RestfulDaoResource {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	protected DaoService daoService;

	/**
	 * 调用外部服务接口
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@RequestMapping("/call")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] callHttpRequest(@Context HttpServletRequest request) throws IOException, ClassNotFoundException {
		JSONObject returnJson = new JSONObject();
		try{
		
		// 获取sqlid
		String sqlid = request.getParameter("sqlId");
		// 从Mybatis配置中获取
		// 获取参数
		String params = request.getParameter("params");
		// 获取参数类型
		String paramType = request.getParameter("paramType");
		// 获取数据库操作类型
		String opera = request.getParameter("opera");
		Object query = null;
		if (StringUtils.isNotEmpty(params) && StringUtils.isNotEmpty(paramType)) {
			if (params.indexOf("{") < 0) {
				switch (paramType) {
				case "java.lang.String":
					query = params.substring(1, params.length() - 1);
					break;
				case "java.lang.Integer":
					query = Integer.parseInt(params);
					break;
				case "java.lang.Long":
					query = Long.parseLong(params);
					break;
				case "java.lang.Double":
					query = Double.parseDouble(params);
					break;
				case "java.lang.Float":
					query = Float.parseFloat(params);
					break;
				case "java.util.Date":
					query = DateUtils.parseDate(params);
					break;
				case "java.lang.Boolean":
					query = Boolean.parseBoolean(params);
					break;
				default:
					query = params;
					break;
				}
			} else {
				//创建class
				Class clazz = Class.forName(paramType);
				// 获取JSON转对象方法
				Method method = null;
				try {
					if(!sqlid.equals("com.glaf.form.core.mapper.FormAttachmentMapper.insertFormAttachment")){
						method =clazz.getMethod("jsonToObject", JSONObject.class);
					}else{
						method =clazz.getMethod("jsonToObjectFull", JSONObject.class);
					}
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				if (method != null) {
					// 创建class实例
					Object isntance = clazz.newInstance();
					query = method.invoke(isntance, JSONObject.parseObject(params));
				}else{
				  query = JSONObject.toJavaObject(JSONObject.parseObject(params), Class.forName(paramType));
				}
			}
			if (opera.equals("select")) {
				try {
					List<Object> list = daoService.selectList(sqlid, query);
					if (list != null && list.size() > 0) {
						// 创建class
						Class clazz = list.get(0).getClass();
						// 获取JSON转对象方法
						Method method = null;
						try {
							
							if(!sqlid.equals("com.glaf.form.core.mapper.FormAttachmentMapper.getFormAttachmentById")){
								method = clazz.getMethod("toJsonObject");
							}else{
								method = clazz.getMethod("toJsonObjectFull");
							}
						} catch (Exception e) {

						}
						if (method != null) {
							JSONArray jsonArr = new JSONArray();
							JSONObject jsonObj = null;
							for (Object obj : list) {
								jsonObj = (JSONObject) method.invoke(obj);
								jsonArr.add(jsonObj);
							}
							returnJson.put("data", jsonArr);
						} else {
							returnJson.put("data", list);
						}
					} else {
						returnJson.put("data", list);
					}
				} catch (Exception e) {
					Object resultObj = daoService.selectOne(sqlid, query);
					if (resultObj != null) {
						// 创建class
						Class clazz = resultObj.getClass();
						// 获取JSON转对象方法
						Method method = null;
						try {
							if(!sqlid.equals("com.glaf.form.core.mapper.FormAttachmentMapper.getFormAttachmentById")){
								method = clazz.getMethod("toJsonObject");
							}else{
								method = clazz.getMethod("toJsonObjectFull");
							}
							if (method != null) {
								JSONObject jsonObj = (JSONObject) method.invoke(resultObj);
								returnJson.put("data", jsonObj);
							}
						} catch (Exception e1) {
							logger.error(e.getMessage());
							returnJson.put("data", resultObj);
						}
					}

				}
			} else if (opera.equals("insert")) {
				int result = daoService.insert(sqlid, query);
				returnJson.put("data", result);
			} else if (opera.equals("update")) {
				int result = daoService.update(sqlid, query);
				returnJson.put("data", result);
			} else if (opera.equals("delete")) {
				int result = daoService.delete(sqlid, query);
				returnJson.put("data", result);
			}

		}
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return JSONObject.toJSONString(returnJson).getBytes("UTF-8");
	}
    @Resource
	public void setDaoService(DaoService daoService) {
		this.daoService = daoService;
	}

}
