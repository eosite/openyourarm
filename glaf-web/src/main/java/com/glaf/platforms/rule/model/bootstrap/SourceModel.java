package com.glaf.platforms.rule.model.bootstrap;

import java.util.Map;

public class SourceModel {
	protected Map<String, String> source;
	
	protected String getStringValue(String key, String defaultValue) {
		return source.get(key) == null ? defaultValue : source.get(key);
	}

	protected boolean getBooleanValue(String key, boolean defaultValue) {
		if (source.get(key) == null) {
			return defaultValue;
		} else {
			return Boolean.parseBoolean(source.get(key));
		}
	}

	protected int getIntValue(String key, Integer defaultValue) {
		if (source.get(key) == null) {
			return defaultValue;
		} else {
			return Integer.parseInt(source.get(key));
		}
	}

	protected double getDoubleValue(String key, Double defaultValue) {
		if (source.get(key) == null) {
			return defaultValue;
		} else {
			return Double.parseDouble(source.get(key));
		}
	}
}
