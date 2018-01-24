package com.glaf.expression.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.sys.model.BaseDataInfo;
import com.glaf.expression.core.util.SysConstant;


@Transactional(readOnly = true)
public interface ExpressionService {
	/**
	 * 获取所有运算符、函数 
	 * @return
	 * @param category 使用领域 （back 后端JAVA db 数据库 front 前段JS）
	 */
	String getAllExpressions(String category);
	
	List<BaseDataInfo> getExpressionsByCategory(String expType,String category);
	 /**
	  * 表达式测试方法
	  * @param category 领域
	  * @param expression 表达式
	  * @param expParamJson  表达式参数
	  * @return
	  */
	 public JSONObject expressionTest(String category,String expression,JSONArray expParamJson,SysConstant constant);
}
