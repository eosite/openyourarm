package com.glaf.expr.poi.functions;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.formula.eval.ErrorEval;
import org.apache.poi.ss.formula.eval.EvaluationException;
import org.apache.poi.ss.formula.eval.ValueEval;
import org.apache.poi.ss.formula.functions.NumericFunction;

import com.glaf.expr.poi.utils.EvaluateArgsUtil;

public class SKEW extends NumericFunction{

	@Override
	protected double eval(ValueEval[] args, int srcCellRow, int srcCellCol) throws EvaluationException {
		if (args.length < 1) {
			throw new EvaluationException(ErrorEval.VALUE_INVALID);
		}
		try {
			EvaluateArgsUtil util = new EvaluateArgsUtil();
			//begin get all parameters and convert to the value which is double
			List<Double> vals = new ArrayList<>();
			try {
				List<Double> reList = null ;
				for (int i = 0; i < args.length; i++) {
					reList = util.evaluateArgs(args[i], srcCellRow, srcCellCol) ;
					if(reList!=null){
						vals.addAll(reList);
					}
				}
			} catch (Exception e) {
				throw new EvaluationException(ErrorEval.VALUE_INVALID);
			}
			//end get and convert
			int n = vals.size();
			if(n==0){
				throw new EvaluationException(ErrorEval.NUM_ERROR);
			}
			if(n < 3){
				return 0;
			}
			//calculate by the SKEW formula
			// SKEW 公式: n/ (n-1)(n-2)再乘以（样本与均值的偏差的立方和/样本标准差的立方)。
			
			//calculate the average value
			double average = 0d ;
			double sum = 0d ;
			for (Double val : vals) {
				sum += val ;
			}
			average = sum / vals.size();
			
			double stdev3 = 0d;	//stdev value
			double stdevSum = 0d;
			double xdiviation3 = 0;	//样本与均值的偏差的m³和。
			double middleParam = 0d;
			double middleParam2 = 0d;
			for (Double double1 : vals) {
				middleParam = double1-average;
				middleParam2 = Math.pow((double1-average), 2);
				stdevSum += middleParam2;
				xdiviation3 += middleParam2 * middleParam;
			}
			stdev3 =  Math.pow(Math.sqrt(stdevSum/(vals.size()-1)),3);
			
			double skew = (n*xdiviation3)/((n-1)*(n-2)*stdev3);
			return skew;
		} catch (EvaluationException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
