package com.glaf.expression.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wltea.expression.ExpressionEvaluator;
import org.wltea.expression.ExpressionRuntimeException;
import org.wltea.expression.datameta.Variable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.sys.mapper.DictoryTreeMapper;
import com.glaf.base.modules.sys.model.BaseDataInfo;
import com.glaf.base.modules.sys.model.DictoryTree;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.DBUtils;
import com.glaf.expression.core.util.SysConstant;
import com.glaf.expression.service.ExpressionService;

@Service("expressionService")
@Transactional(readOnly = true)
public class ExpressionServiceImpl implements ExpressionService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected DictoryTreeMapper dictoryTreeMapper;

	@Resource
	public void setDictoryTreeMapper(DictoryTreeMapper dictoryTreeMapper) {
		this.dictoryTreeMapper = dictoryTreeMapper;
	}

	public String getAllExpressions(String category) {
		List<DictoryTree> dictoryTreeList = getDictoryTreeList(
				"expressions_edit", category);
		List<JSONObject> jsonObjList = changeDictoryTreeListToJSONList(dictoryTreeList);
		return JSON.toJSONString(jsonObjList);
	}

	/**
	 * List<DictoryTree>转 List<JSONObject>
	 * 
	 * @param dictoryTreeList
	 * @return
	 */
	public List<JSONObject> changeDictoryTreeListToJSONList(
			List<DictoryTree> dictoryTreeList) {
		List<JSONObject> jsonObjList = new ArrayList<JSONObject>();
		JSONObject jsonObject = null;
		for (DictoryTree dictoryTree : dictoryTreeList) {
			jsonObject = new JSONObject();
			jsonObject.put("id", dictoryTree.getDictoryTreeId());
			jsonObject.put("pId", dictoryTree.getParentId());
			jsonObject.put("name", dictoryTree.getTreeName());
			jsonObject.put("code", dictoryTree.getValue());
			jsonObject.put("t", dictoryTree.getDesc());
			jsonObject.put("value", dictoryTree.getValue());
			jsonObject.put("isParent", !dictoryTree.getLeafFlag());
			jsonObjList.add(jsonObject);
		}
		return jsonObjList;
	}

	/**
	 * 获取DictoryTree集合
	 * 
	 * @return
	 */
	public List<DictoryTree> getDictoryTreeList(String treeCode, String category) {
		
		String dt = DBConnectionFactory.getDatabaseType();
		
		if(dt.equalsIgnoreCase(DBUtils.ORACLE) || dt.equalsIgnoreCase(DBUtils.DM_DBMS)){
		//if(org.apache.commons.codec.binary.StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())){
			return changeDictoryTreeList(dictoryTreeMapper.getDictoryTreesByTreeCode_oracle(treeCode, category));
		}
		List<DictoryTree> dictoryTreeList = dictoryTreeMapper
				.getDictoryTreesByTreeCode(treeCode, category);
		return changeDictoryTreeList(dictoryTreeList);
	}

	/**
	 * 将数据库表查询出的DictoryTree转换为输出DictoryTree
	 * 将字典数据SysTree的TreeId、treeName、treeCode、treeSort设置为字典表的Id、Name、Code、Sort
	 * 
	 * @param dictoryTreeList
	 * @return
	 */
	public List<DictoryTree> changeDictoryTreeList(
			List<DictoryTree> dictoryTreeList) {
		List<DictoryTree> newDictoryTreeList = new ArrayList<DictoryTree>();
		List<Long> treeIdList = new ArrayList<Long>();
		for (DictoryTree dictoryTree : dictoryTreeList) {
			// 判断是否属于字典数据
			if (dictoryTree.getId() > 0l) {
				if (!treeIdList.contains(dictoryTree.getDictoryTreeId())) {
					DictoryTree ndictoryTree = new DictoryTree();
					ndictoryTree.setDictoryTreeId(dictoryTree
							.getDictoryTreeId());
					ndictoryTree.setParentId(dictoryTree.getParentId());
					ndictoryTree.setTreeName(dictoryTree.getTreeName());
					ndictoryTree.setTreeCode(dictoryTree.getTreeCode());
					ndictoryTree.setIcon(dictoryTree.getIcon());
					ndictoryTree.setUrl(dictoryTree.getUrl());
					ndictoryTree.setLeafFlag(false);
					newDictoryTreeList.add(ndictoryTree);
					treeIdList.add(dictoryTree.getDictoryTreeId());
				}
				dictoryTree.setParentId(dictoryTree.getNodeId());
				dictoryTree.setTreeName(dictoryTree.getCode() + " "
						+ dictoryTree.getName());
				dictoryTree.setTreeCode(dictoryTree.getCode());
				dictoryTree.setDictoryTreeId(dictoryTree.getId());
				dictoryTree.setTreeSort(dictoryTree.getSort());
				dictoryTree.setLeafFlag(true);
			} else {
				dictoryTree.setLeafFlag(false);
			}
			newDictoryTreeList.add(dictoryTree);
		}
		return newDictoryTreeList;

	}

	/**
	 * 表达式测试方法
	 * 
	 * @param category
	 * @param expression
	 * @param expJson
	 * @return
	 */
	public JSONObject expressionTest(String category, String expression,
			JSONArray expVariableJson, SysConstant constant) {
		JSONObject resultJson = new JSONObject();
		int execFlag = 0;
		String message = null;
		String errorToken = "";
		int errorPosition = -1;
		if (expression != null && expression.trim().length() > 0) {
			try {

				List<Variable> variables = getVariablesByVariableJson(expVariableJson);
				if (variables == null) {
					variables = new ArrayList<Variable>();
				}
				// 补增系统常量
				variables.add(Variable.createVariable("~CONST", constant));
				// 执行表达式
				Object result = ExpressionEvaluator.evaluate(expression,
						variables);
				execFlag = 1;
				message = "" + result;
				if (message.equals("null")) {
					execFlag = 0;
					message = "参数未设置";
				}
			} catch (ExpressionRuntimeException e) {
				execFlag = 0;
				message = e.getMessage();
				errorToken = e.getErrorTokenText();
				errorPosition = e.getErrorPosition();
			}
		}
		resultJson.put("execFlag", execFlag);
		resultJson.put("message", message);
		resultJson.put("errorToken", errorToken);
		resultJson.put("errorPosition", errorPosition);
		logger.debug("Expression" + expression + " Test Result:::::::::::::"
				+ message);
		return resultJson;
	}

	/**
	 * 表达式变量赋值
	 * 
	 * @param expVariableJson
	 * @return
	 */
	public List<Variable> getVariablesByVariableJson(JSONArray expVariableJson) {
		// 给表达式中的变量付上下文的值
		List<Variable> variables = null;
		if (expVariableJson != null && expVariableJson.size() > 0) {
			variables = new ArrayList<Variable>();
			JSONObject jsonObject = null;
			String variableName = null;
			String dataType = null;
			for (int i = 0; i < expVariableJson.size(); i++) {
				jsonObject = expVariableJson.getJSONObject(i);
				if (jsonObject != null) {
					variableName = jsonObject.getString("variableName");
					dataType = jsonObject.getString("dataType");
					if (dataType != null && dataType.trim().length() > 0) {
						switch (dataType) {
						case "string":
							variables.add(Variable.createVariable(variableName,
									"test"));
							break;
						case "int":
							variables.add(Variable.createVariable(variableName,
									1));
							break;
						case "long":
							variables.add(Variable.createVariable(variableName,
									1l));
							break;
						case "double":
							variables.add(Variable.createVariable(variableName,
									1.11d));
							break;
						case "float":
							variables.add(Variable.createVariable(variableName,
									1.1f));
							break;
						case "date":
							variables.add(Variable.createVariable(variableName,
									new Date()));
							break;
						default:
							break;
						}
					}
				}
			}
		}
		return variables;
	}

	/**
	 * 根据领域获取表达式
	 */
	@Override
	public List<BaseDataInfo> getExpressionsByCategory(String expType,
			String category) {
		// 获取运算符合分隔符
		BaseDataManager bm = BaseDataManager.getInstance();
		// bm.refreshBaseData();
		List<BaseDataInfo> expressionList = bm.getBaseData(expType);
		List<BaseDataInfo> nexpressionList = new ArrayList<BaseDataInfo>();
		for (BaseDataInfo baseDataInfo : expressionList) {
			if (StringUtils.isEmpty(category)) {
				nexpressionList.add(baseDataInfo);
			} else if (!StringUtils.isEmpty(baseDataInfo.getCategory())) {
				if (category.equals("back")&& baseDataInfo.getCategory().length()>=1
						&& baseDataInfo.getCategory().substring(0, 1)
								.equals("1")) {
					nexpressionList.add(baseDataInfo);
				} 
				//数据库、数据集表达式
				else if (category.equals("db")&& baseDataInfo.getCategory().length()>=2
						&& baseDataInfo.getCategory().substring(1, 2)
								.equals("1")) {
					nexpressionList.add(baseDataInfo);
				} 
				//前端页面表达式
				else if (category.equals("front")&& baseDataInfo.getCategory().length()>=3
						&& baseDataInfo.getCategory().substring(2, 3)
								.equals("1")) {
					nexpressionList.add(baseDataInfo);
				} 
				//工作流条件表达式
				else if (category.equals("workflow")&& baseDataInfo.getCategory().length()>=4
						&& baseDataInfo.getCategory().substring(3, 4)
								.equals("1")) {
					nexpressionList.add(baseDataInfo);
				}
			}
		}
		return nexpressionList;
	}
}
