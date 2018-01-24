package com.glaf.expr.poi.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.formula.eval.AreaEvalBase;
import org.apache.poi.ss.formula.eval.EvaluationException;
import org.apache.poi.ss.formula.eval.OperandResolver;
import org.apache.poi.ss.formula.eval.RefEvalBase;
import org.apache.poi.ss.formula.eval.StringEval;
import org.apache.poi.ss.formula.eval.ValueEval;

/**
 * 用来计算excel范围
 * @author J
 *
 */
public class EvaluateArgsUtil {
	public double evaluateArg(ValueEval arg, int srcCellRow, int srcCellCol) throws EvaluationException {
		ValueEval ve = OperandResolver.getSingleValue(arg, srcCellRow, (short) srcCellCol);

		if (ve instanceof StringEval) {
			String strVal = ((StringEval) ve).getStringValue();
			Double dVal = OperandResolver.parseDouble(strVal);
			if (dVal != null) {
				return dVal.doubleValue();
			}
		}
		return OperandResolver.coerceValueToDouble(ve);
	}

	public List<Double> evaluateArgs(ValueEval arg, int srcCellRow, int srcCellCol) throws EvaluationException {
		List<Double> list = new ArrayList<>();
		if (arg == null) {
			return list;
		}

		if (arg instanceof StringEval) {
			list.add(evaluateArg(arg, srcCellRow, srcCellCol));
			return list;
		} else if (arg instanceof AreaEvalBase) {
			AreaEvalBase area = (AreaEvalBase) arg;
			for (int i = area.getFirstRow(); i <= area.getLastRow(); i++) {
				for (int j = area.getFirstColumn(); j <= area.getLastColumn(); j++) {
					list.add(evaluateArg(area.getAbsoluteValue(i, j), i, j));
				}
			}
			return list;
		} else if (arg instanceof RefEvalBase) {
			return null;
		} else {
			list.add(OperandResolver.coerceValueToDouble(arg));
		}
		return list;
	}
}
