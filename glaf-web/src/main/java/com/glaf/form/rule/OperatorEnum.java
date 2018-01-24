package com.glaf.form.rule;

public enum OperatorEnum {
	//operator{eq(等于),neq(不等于),lt(小于),lte(小于等于),gt(大于),gte(大于或等于),startswith(开头为),endswith(结束为),contains(包含),doesnotcontain(不包含)}
	
	EQ("eq", " = "), NEQ("neq", " <> "),LT("lt"," < "),LTE("lte"," <= "),GT("gt"," > "),
	GTE("gte"," >= "),STARTSWITH("startswith"," like "),ENDSWITH("endswith"," like "),
	CONTAIN("contains"," like "),DOESNOTCONTAIN("doesnotcontain"," not like ");
	// 成员变量
	private String name;
	private String value;
	
	public static final String START_SWITH = "startswith";
	public static final String END_SWITH = "endswith";
	public static final String CONTAINS = "contains";
	public static final String DOES_NOT_CONTAIN = "doesnotcontain";

	// 构造方法
	private OperatorEnum(String name, String value) {
		this.name = name;
		this.value = value;
	}

	// 获取类名
	public static String getValue(String name) {
		for (OperatorEnum c : OperatorEnum.values()) {
			if (c.name.equals(name)) {
				return c.value;
			}
		}
		return null;
	}
}
