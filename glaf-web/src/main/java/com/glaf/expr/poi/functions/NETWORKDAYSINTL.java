package com.glaf.expr.poi.functions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.formula.OperationEvaluationContext;
import org.apache.poi.ss.formula.atp.DateParser;
import org.apache.poi.ss.formula.eval.AreaEvalBase;
import org.apache.poi.ss.formula.eval.ErrorEval;
import org.apache.poi.ss.formula.eval.EvaluationException;
import org.apache.poi.ss.formula.eval.NumberEval;
import org.apache.poi.ss.formula.eval.OperandResolver;
import org.apache.poi.ss.formula.eval.StringEval;
import org.apache.poi.ss.formula.eval.ValueEval;
import org.apache.poi.ss.formula.functions.FreeRefFunction;
import org.apache.poi.ss.usermodel.DateUtil;

import com.glaf.core.util.DateUtils;
import com.glaf.expr.poi.utils.DateCalculate;

public class NETWORKDAYSINTL implements FreeRefFunction {
	class NETWORKDAYSINTLUTIL {
		public double evaluateDateArg(ValueEval arg, int srcCellRow, int srcCellCol) throws EvaluationException {
			ValueEval ve = OperandResolver.getSingleValue(arg, srcCellRow, (short) srcCellCol);

			if (ve instanceof StringEval) {
				String strVal = ((StringEval) ve).getStringValue();
				Double dVal = OperandResolver.parseDouble(strVal);
				if (dVal != null) {
					return dVal.doubleValue();
				}
				Calendar date;
				try {
					date = DateParser.parseDate(strVal);
				} catch (Exception e) {
					date = Calendar.getInstance();
					date.setTime(DateUtils.toDate(strVal));
				}
				return DateUtil.getExcelDate(date, false);
			}
			return OperandResolver.coerceValueToDouble(ve);
		}

		public Date[] evaluateDatesArg(ValueEval arg, int srcCellRow, int srcCellCol) throws EvaluationException {
			if (arg == null) {
				return new Date[0];
			}

			if (arg instanceof StringEval) {
				return new Date[] { DateUtil.getJavaDate(evaluateDateArg(arg, srcCellRow, srcCellCol)) };
			} else if (arg instanceof AreaEvalBase) {
				List<Double> valuesList = new ArrayList<Double>();
				AreaEvalBase area = (AreaEvalBase) arg;
				for (int i = area.getFirstRow(); i <= area.getLastRow(); i++) {
					for (int j = area.getFirstColumn(); j <= area.getLastColumn(); j++) {
						valuesList.add(evaluateDateArg(area.getAbsoluteValue(i, j), i, j));
					}
				}
				Date[] values = new Date[valuesList.size()];
				for (int i = 0; i < valuesList.size(); i++) {
					values[i] = DateUtil.getJavaDate(valuesList.get(i).doubleValue());
				}
				return values;
			}
			return new Date[] { DateUtil.getJavaDate(OperandResolver.coerceValueToDouble(arg)) };
		}
	}

	/**
	 * NETWORKDAYS.INTL(start_date, end_date, [weekend], [holidays])
	 */
	@Override
	public ValueEval evaluate(ValueEval[] args, OperationEvaluationContext ec) {
		// 检查参数
		if (args.length < 2 && args.length > 4) {
			return ErrorEval.VALUE_INVALID;
		}

		try {
			NETWORKDAYSINTLUTIL util = new NETWORKDAYSINTLUTIL();
			int srcCellRow = ec.getRowIndex();
			int srcCellCol = ec.getColumnIndex();

			// ValueEval startDay = OperandResolver.getSingleValue(args[0],
			// srcCellRow , srcCellCol);

			double start = util.evaluateDateArg(args[0], srcCellRow, srcCellCol);
			Date startDate = DateUtil.getJavaDate(start);
			double end = util.evaluateDateArg(args[1], srcCellRow, srcCellCol);
			Date endDate = DateUtil.getJavaDate(end);

			String weekend = args.length >= 3 ? OperandResolver.coerceValueToString(args[2]) : null;

			ValueEval holidaysCell = args.length == 4 ? args[3] : null;
			Date[] holidays = util.evaluateDatesArg(holidaysCell, srcCellRow, srcCellCol);
			DateCalculate dc = new DateCalculate();
			int result = 0;
			try {
				result = dc.calculateWorkDay(startDate, endDate, weekend, holidays);
			} catch (Exception e) {
				e.printStackTrace();
				throw new EvaluationException(ErrorEval.VALUE_INVALID);
			}

			// 检查结果 检查是否为Num 或者超出界限
			if (Double.isNaN(result) || Double.isInfinite(result)) {
				throw new EvaluationException(ErrorEval.NUM_ERROR);
			}

			return new NumberEval(result);
		} catch (EvaluationException e) {
			e.printStackTrace();
		}
		return new NumberEval(0);
	}

}
