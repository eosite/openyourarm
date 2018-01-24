package com.glaf.datamgr.sqlparser.method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLMethodInvokeExpr;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.datamgr.sqlparser.ITranslator;
import com.glaf.datamgr.sqlparser.SQLObjectExpr;
import com.glaf.datamgr.sqlparser.TranlateFactory;
import com.glaf.datamgr.sqlparser.util.ScriptEngineUtils;

public class CommonMethod implements IMethod {

	private static final String NAME = "name";

	private static final String ARGS = "args";

	protected String type;

	protected String methodName;

	/**
	 * 参数对照\模版
	 */
	protected JSONObject metadata;

	protected String source;

	protected String target;

	public CommonMethod(String type) {
		this.type = type;
	}

	public void convert(SQLMethodInvokeExpr function) {
		ITranslator tran = TranlateFactory.getTMNBySMN(source, methodName, target);

		if (tran == null) {
			// return;
		}

		List<String> exps = new ArrayList<>();

		List<SQLExpr> args = function.getParameters();
		if (CollectionUtils.isNotEmpty(args)) {
			for (SQLExpr e : args) {
				exps.add(e.toString());
			}
		}

		JSONObject datas = this.convert(exps, tran);

		/**
		 * 方法名转换
		 */
		if (StringUtils.isNotBlank(datas.getString(NAME))) {
			function.setMethodName(datas.getString(NAME));
		} else if (tran != null) {
			function.setMethodName(tran.getMethodName());
		} else {
			TranlateFactory.CANTTRANLATE(function);
		}

		/**
		 * 参数转换
		 */
		if (datas.containsKey(ARGS)) {
			args.clear();
			JSONArray arr = datas.getJSONArray(ARGS);
			for (int i = 0; i < arr.size(); i++) {
				function.addParameter(new SQLObjectExpr(arr.getString(i)));
			}
		}

	}

	/**
	 * 转换具体的函数
	 */
	protected JSONObject convert(List<String> exps, ITranslator targetTran) {

		if (metadata == null) {
			return new JSONObject();
		}

		JSONObject json, ret;
		/**
		 * 模版引擎转换
		 */
		String temp = metadata.getString("template");
		if (StringUtils.isNotBlank(temp)) {

			String func = "$CONVERT$";
			try {
				Object value = ScriptEngineUtils.eval("var " + func + " = " //
						+ temp, func + "." + this.target, JSON.toJSONString(exps));
				return JSON.parseObject(value.toString());
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			json = metadata.getJSONObject("template");
			temp = json.getString(this.target);
			if (StringUtils.isNotBlank(temp)) {
				func = "$CONVERT$";
				Object value = ScriptEngineUtils.eval("var " + func + " = " //
						+ temp, func, JSON.toJSONString(exps));
				return JSON.parseObject(value.toString());
			}
		} else if (CollectionUtils.isNotEmpty(exps)) {
			/**
			 * 参数对照转换
			 */
			JSONArray descs = this.metadata.getJSONArray("desc");
			int len = exps.size(), size = descs.size();
			Map<String, String> map = new HashMap<>();
			for (int i = 0; i < size; i++) {
				json = descs.getJSONObject(i);
				if (i > len) {
					break;
				}
				map.put(json.getString(NAME), exps.get(i));
			}

			ret = new JSONObject();
			if (targetTran != null) {
				/**
				 * 转换目标参数
				 */
				List<String> params = new ArrayList<>();
				JSONObject metadata = targetTran.getMetadata();
				descs = metadata.getJSONArray("desc");
				size = descs.size();
				for (int i = 0; i < size; i++) {
					json = descs.getJSONObject(i);
					String val = map.getOrDefault(json.getString(NAME), //
							json.getString("val"));
					params.add(val);
				}
				ret.put(ARGS, params);
			}

			return ret;
		}

		return new JSONObject();
	}

	public void setResource(String template, String source, String target, String methodName) {
		if (template != null)
			this.metadata = JSON.parseObject(template);
		if (source != null)
			this.source = source;
		if (target != null)
			this.target = target;
		if (methodName != null)
			this.methodName = methodName;
	}

	public String getType() {
		return this.type;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public JSONObject getMetadata() {
		return metadata;
	}

}
