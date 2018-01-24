package com.glaf.form.cell.service;

public interface FormulaConvertService {
	/**
	 * 替换公式
	 * 
	 * @param formulaStr
	 *            需要替换的公式字符串
	 * @param cvtId
	 *            表CVT_PAGE_TMPL的CVT_ID_
	 * @param cvtElemId
	 *            表CVT_ELEM_TMPL的CVT_ELEM_ID_
	 * @return
	 */
	String convertFormula(String formulaStr, String cvtId, String cvtElemId);

	/**
	 * 查询表中所有公式并替换
	 */
	void convertAllFormula();
}
