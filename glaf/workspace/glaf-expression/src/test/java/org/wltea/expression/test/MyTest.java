package org.wltea.expression.test;

import java.util.ArrayList;
import java.util.List;

import org.wltea.expression.ExpressionEvaluator;
import org.wltea.expression.ExpressionRuntimeException;
import org.wltea.expression.datameta.Variable;

public class MyTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<String> expressions = new ArrayList<String>();
		//expressions.add("(11+22)+~F{数据源.数据表.字段}+\"2323\"");
		//expressions.add("\"asds\"+~F{数据源.数据表.字段}+~F{数据源.数据表.字段}");
		//expressions.add("\"asds\"+$CONTAINS(\"dabcaaaa\",~F{数据源.数据表.字段})+~F{数据源.数据表.字段}");
		//expressions.add("\"asds\"+~F{数据源.数据表.字段}+[2016-01-01]");
		expressions.add("211+2!232");
		for(String expression : expressions){
			System.out.println("expression : " + expression);
			List<Variable> variables = new ArrayList<Variable>();
			//variables.add(new Variable("~F{数据源.数据表.字段}" , DataType.DATATYPE_DATE , new Date()));
			try{
			     System.out.println(ExpressionEvaluator.compile(expression,variables));
			}catch(ExpressionRuntimeException e)
			{
				System.out.println(":::::::::::::::::::::::::::::"+e.getMessage()+e.getErrorTokenText()+e.getErrorPosition());
			}
			Object result = ExpressionEvaluator.evaluate(expression,variables);
			System.out.println("result = " + result);
			System.out.println();
		}
		System.out.println("----------------------------------------------------");		
		System.out.println("----------------testMess over------------------");
		System.out.println("----------------------------------------------------");
	}

}
