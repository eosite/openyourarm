package com.glaf.datamgr.sqlparser;

import com.alibaba.druid.sql.ast.expr.SQLMethodInvokeExpr;
import com.alibaba.fastjson.JSONObject;

public interface ITranslator {
	void convert(SQLMethodInvokeExpr function);

	void setResource(String template, String source, String target, String methodName);

	String getType();

	String getSource();

	String getMethodName();

	JSONObject getMetadata();

}
