package com.glaf.form.web.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.dep.base.domain.DepBaseCategory;
import com.glaf.dep.base.domain.DepBaseCompProp;
import com.glaf.dep.base.domain.DepBaseComponent;
import com.glaf.dep.base.domain.DepBaseProp;
import com.glaf.dep.base.domain.DepBasePropCategory;
import com.glaf.dep.base.query.DepBaseCategoryQuery;
import com.glaf.dep.base.query.DepBaseCompPropQuery;
import com.glaf.dep.base.query.DepBaseComponentQuery;
import com.glaf.dep.base.query.DepBasePropCategoryQuery;
import com.glaf.dep.base.query.DepBasePropQuery;
import com.glaf.dep.base.service.DepBaseCategoryService;
import com.glaf.dep.base.service.DepBaseCompPropService;
import com.glaf.dep.base.service.DepBaseComponentService;
import com.glaf.dep.base.service.DepBasePropCategoryService;
import com.glaf.dep.base.service.DepBasePropService;
import com.glaf.form.core.util.FormDictoryFactory;

@Controller
@Path("/rs/form/spreadjs")
public class FormSpreadJSResource {
	protected static final Log logger = LogFactory.getLog(FormSpreadJSResource.class);

	@Resource(name = "com.glaf.dep.base.service.depBaseCategoryService")
	protected DepBaseCategoryService depBaseCategoryService;

	@Resource(name = "com.glaf.dep.base.service.depBasePropService")
	protected DepBasePropService depBasePropService;

	@Resource(name = "com.glaf.dep.base.service.depBasePropCategoryService")
	protected DepBasePropCategoryService depBasePropCategoryService;

	@Resource(name = "com.glaf.dep.base.service.depBaseCompPropService")
	protected DepBaseCompPropService depBaseCompPropService;

	@Resource(name = "com.glaf.dep.base.service.depBaseComponentService")
	protected DepBaseComponentService depBaseComponentService;

	@GET
	@POST
	@Path("/readAttribute")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] readAttribute(@Context HttpServletRequest request) throws IOException {
		long start = System.currentTimeMillis();
		logger.debug("查询属性计时>>>");

		JSONArray results = new JSONArray();
		try {
			String systemType = request.getParameter("systemType");// 系统类型
			if (StringUtils.isEmpty(systemType)) {
				throw new Exception("systemType is empty!please set parameter width systemType!");
			}

			// 查询所有的分类
			List<Long> cateids = new ArrayList<Long>();//保存所有分类id
			DepBaseCategory rootCategory = depBaseCategoryService.getDepBaseCategorysByCode(systemType);

			DepBaseCategoryQuery query1 = new DepBaseCategoryQuery();
			query1.setDelFlag("0");
			query1.setTreeIdLike(rootCategory.getTreeId());
			query1.setSqlCondition(" and E.PID_ != -1 ");
			query1.setOrderBy(" ORDERNO_ ASC");
			List<DepBaseCategory> categorys = depBaseCategoryService.list(query1);//所有分类集合
			for (DepBaseCategory cate : categorys) {
				cateids.add(cate.getId());
				List<DepBaseCategory> childrens = new ArrayList<DepBaseCategory>();
				for (DepBaseCategory cate2 : categorys) {
					if (cate2.getPid().longValue() == cate.getId().longValue()) {
						childrens.add(cate2);
					}
				}
				cate.setChildrens(childrens);
			}
			//end

			// 查询所有的规则
			List<DepBaseProp> props = new ArrayList<DepBaseProp>();//保存所有规则
			Map<String, Long> catepropMap = new HashMap<String, Long>();//规则与分类关系集合Map<规则id,分类id>
			
			List<String> ruleIds = new ArrayList<String>();
			if (props == null || props.isEmpty() || catepropMap == null || catepropMap.isEmpty()) {
				// 查询规则id
				DepBasePropCategoryQuery query2 = new DepBasePropCategoryQuery();
				query2.setDepBaseCategoryIds(cateids);
				List<DepBasePropCategory> propcategorys = depBasePropCategoryService.list(query2);
				for (DepBasePropCategory propcate : propcategorys) {
					ruleIds.add(propcate.getRuleId());
					catepropMap.put(propcate.getRuleId(), propcate.getDepBaseCategoryId());
				}

				// 查找规则
				DepBasePropQuery query3 = new DepBasePropQuery();
				query3.setRuleIds(ruleIds);
				query3.setDelFlag("0");
				query3.setOrderBy(" ORDERNO_ ASC");
				props = depBasePropService.list(query3);
			}
			//end
			
			// 查询所有组件 
			Map<String,List<String>> compPropIdMap = new HashMap<String,List<String>>();//规则与组件关系集合Map<规则id,组件id集合>
			List<String> compIds = new ArrayList<String>();//保存所有组件id
			
			DepBaseCompPropQuery query2 = new DepBaseCompPropQuery();
			query2.setRuleIds(ruleIds);
			List<DepBaseCompProp> compProps = depBaseCompPropService.list(query2);
			for(DepBaseCompProp compProp : compProps){
				if(!compPropIdMap.containsKey(compProp.getRuleId())){
					List<String> tmps = new ArrayList<String>();
					tmps.add(compProp.getDepBaseComponentId());
					compPropIdMap.put(compProp.getRuleId(), tmps);
				}else{
					List<String> tmps = compPropIdMap.get(compProp.getRuleId());
					tmps.add(compProp.getDepBaseComponentId());
				}
				
				if(!compIds.contains(compProp.getDepBaseComponentId())){
					compIds.add(compProp.getDepBaseComponentId());
				}
			}
			
			Map<String,DepBaseComponent> componentMap = new HashMap<String,DepBaseComponent>();//保存所有组件Map<组件id,组件对象>
			DepBaseComponentQuery query3 = new DepBaseComponentQuery();
			query3.setIds(compIds);
			List<DepBaseComponent> components = depBaseComponentService.list(query3);
			for(DepBaseComponent component : components){
				componentMap.put(component.getId(), component);
			}
			//end 

			for (DepBaseCategory category : categorys) {
				if (category.getPid().longValue() != rootCategory.getId().longValue()) {
					continue;
				}
				JSONObject cateObj = category.toJsonObject();
				cateObj.put("type", category.getCode());
				cateObj.put("name", category.getName());

				JSONArray propertyArray = new JSONArray();
				List<DepBaseCategory> childrens = category.getChildrens();
				if (childrens != null && !childrens.isEmpty()) {
					for (DepBaseCategory children : childrens) {
						JSONObject childrenCateobj = createAttributes(children,props,catepropMap,compPropIdMap,componentMap);
						propertyArray.add(childrenCateobj);
					}
				}
				cateObj.put("propertyPackages", propertyArray);

				results.add(cateObj);
			}

		} catch (Exception e) {
			logger.error("Error while loading Component properties:" + e.getMessage());
			e.printStackTrace();
		}
		logger.debug("查询属性，共使用:" + ((System.currentTimeMillis() - start) / 1000) + "s");

		return results.toJSONString().getBytes("utf-8");
	}

	private JSONObject createAttributes(DepBaseCategory children, List<DepBaseProp> props,
			Map<String, Long> catepropMap, Map<String, List<String>> compPropIdMap,
			Map<String, DepBaseComponent> componentMap) throws Exception {
		JSONObject childrenCateobj = new JSONObject();
		childrenCateobj.put("id", children.getId());
		childrenCateobj.put("title", children.getName());
		childrenCateobj.put("code", children.getCode());
		childrenCateobj.put("expandFlag", children.getExpandFlag());
		childrenCateobj.put("toolBarTemplate", children.getToolBarTemplate());

		if (props != null && catepropMap != null) {
			JSONArray propArray = new JSONArray();

			for (DepBaseProp prop : props) {
				if (catepropMap.get(prop.getRuleId()) !=null && catepropMap.get(prop.getRuleId()).longValue() == children.getId().longValue()) {
					JSONObject temp = prop.toJsonObject();
					temp.put("id", prop.getRuleId());
					temp.put("name", prop.getRuleName());
					temp.put("type", prop.getInputType());
					
					List<DepBaseComponent> components = new ArrayList<DepBaseComponent>();
					List<String> compIds = compPropIdMap.get(prop.getRuleId());
					if(compIds!=null){
						for(String compId : compIds){
							components.add(componentMap.get(compId));
						}
					}
					
					JSONArray componentArr = new JSONArray();
					if (components != null && components.size() > 0) {
						for (DepBaseComponent component : components) {
							componentArr.add(component.toJsonObject());
						}
					}
					temp.put("components", componentArr);

					JSONObject attrs = JSONObject.parseObject(prop.getExtJson());
					if (attrs.getBooleanValue("extJsonCheck")) {
						// 处理数据源
						if (attrs.getInteger("dataSource") != null) {
							if (attrs.getInteger("dataSource").intValue() == 10) {
								// 基础数据
								FormDictoryFactory factory = FormDictoryFactory.getInstance();
								JSONArray dicts = factory
										.getFormDictoryJSONArrayByTreeCode(attrs.getString("dictCode"));
								temp.put("values", dicts);
							} else if (attrs.getInteger("dataSource").intValue() == 20) {
								// 数据表
							} else if (attrs.getInteger("dataSource").intValue() == 30) {
								// 用户自定义数据
							}
						}

					}
					propArray.add(temp);
				}
			}
			childrenCateobj.put("attributes", propArray);
		}

		return childrenCateobj;
	}

}
