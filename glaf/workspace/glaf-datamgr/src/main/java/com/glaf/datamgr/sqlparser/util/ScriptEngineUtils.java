package com.glaf.datamgr.sqlparser.util;

import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleBindings;

public class ScriptEngineUtils {

	protected static final ScriptEngineManager scriptEngineManager;

	protected static final ScriptEngine nashorn;

	static {
		scriptEngineManager = new ScriptEngineManager();
		nashorn = scriptEngineManager.getEngineByName("nashorn");
	}

	public static Object eval(String template, String method, Object data) {
		try {
			nashorn.eval(template);
			return nashorn.eval(String.format("%s(%s)", method, data));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object eval(String template, Map<String, Object> map) {
		try {
			return nashorn.eval(template, new SimpleBindings(map));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
