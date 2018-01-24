package com.glaf.platforms.rule;

public enum KendoEnum {
	// role全部使用小写 className需要和类名一致
	GRID("grid", "kendoGrid"), COMBOBOX("combobox", "kendoCombobox"), BUTTONMODEL("button", "kendoButton"), MASKEDTEXTBOXMODEL("maskedtextbox", "kendoMaskedTextBox"), AUTOCOMPLETE(
			"autocomplete", "kendoAutoComplete"), DATETIMEPICKER("datetimepicker", "kendoDateTimePicker"), DATEPICKER("datepicker", "kendoDatePicker"), DROPDOWNLIST(
			"dropdownlist", "kendoDropDownList"), MULTISELECT("multiselect", "kendoMultiSelect"),NUMERICTEXTBOX("numerictextbox", "kendoNumericTextBox");;
	//NumericTextBox
	// 成员变量
	private String roleName;
	private String className;

	// 构造方法
	private KendoEnum(String roleName, String className) {
		this.roleName = roleName;
		this.className = className;
	}

	// 获取类名
	public static String getClassName(String roleName) {
		for (KendoEnum c : KendoEnum.values()) {
			if (c.roleName.equals(roleName)) {
				return c.className;
			}
		}
		return null;
	}
}
