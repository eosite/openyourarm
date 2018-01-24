package com.glaf.expr.poi;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.formula.WorkbookEvaluator;
import org.apache.poi.ss.formula.functions.FreeRefFunction;
import org.apache.poi.ss.formula.functions.Function;
import org.apache.poi.ss.formula.udf.AggregatingUDFFinder;
import org.apache.poi.ss.formula.udf.DefaultUDFFinder;
import org.apache.poi.ss.formula.udf.UDFFinder;
import org.apache.poi.ss.usermodel.Workbook;

import com.glaf.expr.poi.functions.KURT;
import com.glaf.expr.poi.functions.NETWORKDAYSINTL;
import com.glaf.expr.poi.functions.SKEW;
import com.glaf.expr.poi.functions.STDEVA;

public class POIFunctionsRegister {
	// 自定义函数
	private static final Map<String, FreeRefFunction> coustomMap = new HashMap<>();

	static {
		coustomMap.put("NETWORKDAYS.INTL", new NETWORKDAYSINTL());
		
		//原有方法 只需要注册一次
		WorkbookEvaluator.registerFunction("STDEVA", new STDEVA());
		WorkbookEvaluator.registerFunction("SKEW", new SKEW());
		WorkbookEvaluator.registerFunction("KURT", new KURT());
	}

	public POIFunctionsRegister() {

	}

	public static void register(Workbook workbook) {
		// 注入方法 自定义
		String[] functionNames = { /* "NETWORKDAYS.INTL" */ };
		FreeRefFunction[] functionImpls = { /* new NETWORKDAYSINTL() */ };

		Set<String> keys = coustomMap.keySet();
		functionNames = keys.toArray(functionNames);
		functionImpls = coustomMap.values().toArray(functionImpls);

		UDFFinder udfs = new DefaultUDFFinder(functionNames, functionImpls);
		UDFFinder udfToolpack = new AggregatingUDFFinder(udfs);

		workbook.addToolPack(udfToolpack);

		
		// WorkbookEvaluator.registerFunction("STDEVA", new STDEVA());
	}
}
