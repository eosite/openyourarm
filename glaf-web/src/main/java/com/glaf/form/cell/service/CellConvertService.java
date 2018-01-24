package com.glaf.form.cell.service;

public interface CellConvertService {

	boolean convert();

	boolean convert(String cvtId);

	static String prefix = "zygs-fzmt-com-cn-convert-cell-";
	
	static String createBy = "system";
}
