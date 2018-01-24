package com.glaf.expression.test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.time.DateUtils;
import org.wltea.expression.ExpressionEvaluator;
import org.wltea.expression.datameta.BaseDataMeta.DataType;
import org.wltea.expression.datameta.Variable;

public class MyTest {

	public static void main(String[] args) throws ParseException {
		
		ArrayList<String> expressions = new ArrayList<String>();
		// expressions.add("(11+22)+~F{数据源.数据表.字段}+\"2323\"");
		// expressions.add("\"asds\"+~F{数据源.数据表.字段}+~F{数据源.数据表.字段}");
		// expressions.add("\"asds\"+$CONTAINS(\"dabcaaaa\",~F{数据源.数据表.字段})+~F{数据源.数据表.字段}");
		// expressions.add("\"asds\"+~F{数据源.数据表.字段}+[2016-01-01]");
		//expressions.add("$SYSDATE()");
		//expressions.add("$ADD(10,10.1)");
	//	expressions.add("admin");
	//	expressions.add("cell_useradd8136.id+\"|\"+901");
	//	expressions.add("$FORMATDATETIME($SYSDATE(),\"yyyyMMdd\")");
		//expressions.add("~V{TEXT}");
		
		//expressions.add("$SPLIT(\"JAVA,C#,JDBC\",\",\")");
		expressions.add("$UUID()");
		expressions.add("$FORMATDATETIME(\"2017-01-13\", \"yyyyMMdd\")");
		
		for (String expression : expressions) {
			System.out.println("expression : " + expression);
			List<Variable> variables = new ArrayList<Variable>();
			variables.add(Variable.createVariable("cell_useradd8136.id",
					8));
			variables.add(Variable.createVariable("b",
					2));
			// variables.add(new Variable("~F{数据源.数据表.字段}" ,
			// DataType.DATATYPE_DATE , new Date()));
			//System.out.println(ExpressionEvaluator.compile(expression, variables));
			Object result = ExpressionEvaluator.evaluate(expression, variables);
			System.out.println("result = " + result);
			if(result != null){
				if(result.getClass() == ArrayList.class){
					List list = (List) result;
					System.out.println(list.size());
					System.out.println(variables);
				}
			}
		}
		//String str = "{desc:操作符\"!\"参数类型错误,position:5,result:\"211+2!232\" 编译期检查异常}";

		System.out.println("----------------------------------------------------");
		System.out.println("----------------testMess over------------------");
		System.out.println("----------------------------------------------------");
	//	test();
	}
	
	static void test(){
		String regexToString = "fsdsdfd#JAVA#SPLIT(\"JAVA234,KLAUS\",\",\")sdfsdfsde3333#JAVA#SUBSTRING(sdf)";
		String regex = "(\\#JAVA\\#\\w*\\(.*?\\))";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(regexToString);
		while (matcher.find()) {
			String convertName = matcher.group(1);
		//	regexToString = replaceConstantName(convertName, type, map);
			if (null != convertName) {
			//	matcher.appendReplacement(sbr,
			//			java.util.regex.Matcher.quoteReplacement(regexToString));
				
				List<Variable> variables = new ArrayList<Variable>();
				// variables.add(new Variable("~F{数据源.数据表.字段}" ,
				// DataType.DATATYPE_DATE , new Date()));
				//System.out.println(ExpressionEvaluator.compile(expression, variables));
				Object result = ExpressionEvaluator.evaluate(convertName, null);
				
				System.out.println(result);
			}
		}
		
	}

}
