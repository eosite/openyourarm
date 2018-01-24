package com.glaf.expr.poi.functions;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.formula.eval.ErrorEval;
import org.apache.poi.ss.formula.eval.EvaluationException;
import org.apache.poi.ss.formula.eval.ValueEval;
import org.apache.poi.ss.formula.functions.NumericFunction;

import com.glaf.expr.poi.utils.EvaluateArgsUtil;

public class KURT extends NumericFunction{

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
			//calculate the average value
			double average = 0d ;
			double sum = 0d ;
			for (Double val : vals) {
				sum += val ;
			}
			average = sum / vals.size();
			
			double stdev4 = 0d;	//stdev value的四次方，标准差四次方
			double stdevSum = 0d;
			double xdiviation4 = 0;	//样本与均值的偏差的四次方和。
			double middleParam = 0d;
			double middleParam2 = 0d;
			for (Double double1 : vals) {
				middleParam = double1-average;
				middleParam2 = Math.pow((double1-average), 2);
				stdevSum += middleParam2;
				xdiviation4 += middleParam2 * middleParam2;
			}
			stdev4 =  Math.pow(Math.sqrt(stdevSum/(vals.size()-1)),4);
			
			double kurt = ((n*(n+1)*xdiviation4)/((n-1)*(n-2)*(n-3)*stdev4)) - (3*Math.pow(n-1, 2)/((n-2)*(n-3)));
			
			return kurt;
		} catch (EvaluationException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
