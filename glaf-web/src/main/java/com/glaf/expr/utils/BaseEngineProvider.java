package com.glaf.expr.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.boris.expr.Expr;
import org.boris.expr.ExprException;
import org.boris.expr.ExprFunction;
import org.boris.expr.ExprVariable;
import org.boris.expr.IEvaluationContext;
import org.boris.expr.IExprFunction;
import org.boris.expr.engine.EngineProvider;
import org.boris.expr.engine.GridReference;
import org.boris.expr.engine.Range;
import org.boris.expr.function.ExcelFunctionProvider;
import org.boris.expr.function.excel.DATE;
import org.boris.expr.function.excel.DATEVALUE;
import org.boris.expr.function.excel.SUM;

public class BaseEngineProvider implements EngineProvider {
	protected static final Log logger = LogFactory.getLog(BaseEngineProvider.class);
	//自定义方法
	static private Map<String, IExprFunction> functions = new HashMap<String, IExprFunction>();
	static {
		//添加自定义方法
		//functions.put("SUM", new SUM());
		//functions.put("DATE", new DATE());
		//functions.put("DATEVALUE", new DATEVALUE());
	}
	
	private int columns = 20;
	private int rows = 200;
	/**
	 * 验证是否超出范围
	 * @param ref
	 * @throws ExprException
	 */
	private void validate(GridReference ref) throws ExprException {
		if (ref.getColumn() < 1 || ref.getColumn() > columns)
			throw new ExprException("Invalid column: " + ref.getColumn());
		if (ref.getRow() < 1 || ref.getRow() > rows)
			throw new ExprException("Invalid row: " + ref.getRow());
	}
	
	@Override
	public void validate(ExprVariable variable) throws ExprException {
		Range r = (Range) variable.getAnnotation();
		if (r == null) {
			r = Range.valueOf(variable.getName());
		}
		validate(r.getDimension1());
		validate(r.getDimension2());
	}

	@Override
	public void inputChanged(Range range, Expr input) {
		//logger.debug("Input changed: " + range + "=\n" + input);
	}

	@Override
	public void valueChanged(Range range, Expr value) {
		//logger.debug("Value changed: " + range + "=\n" + value);
	}

	@Override
	public Expr evaluateFunction(IEvaluationContext context, ExprFunction function) throws ExprException {
		ExcelFunctionProvider excelFunc = new ExcelFunctionProvider();
		if(excelFunc.hasFunction(function)){
			return excelFunc.evaluate(context, function);
		}
		IExprFunction f = functions.get(function.getName().toUpperCase());
		if (f == null)
			throw new ExprException("Unknown function: " + function.getName());
		return f.evaluate(context, function.getArgs());
	}

	@Override
	public Expr evaluateVariable(IEvaluationContext context, ExprVariable variable) throws ExprException {
		// TODO Auto-generated method stub
		return null;
	}

}
