package com.glaf.expr.poi.functions;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.formula.eval.ErrorEval;
import org.apache.poi.ss.formula.eval.EvaluationException;
import org.apache.poi.ss.formula.eval.ValueEval;
import org.apache.poi.ss.formula.functions.NumericFunction;

import com.glaf.expr.poi.utils.EvaluateArgsUtil;

public class STDEVA extends NumericFunction{

	@Override
	protected double eval(ValueEval[] args, int srcCellRow, int srcCellCol) throws EvaluationException {
		// check params
		if (args.length < 1) {
			throw new EvaluationException(ErrorEval.VALUE_INVALID);
		}
		try {
			EvaluateArgsUtil util = new EvaluateArgsUtil();
			
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
			if(vals.size()==0){
				throw new EvaluationException(ErrorEval.NUM_ERROR);
			}
			//average value
			double average = 0d ;
			double sum = 0d ;
			for (Double val : vals) {
				sum += val ;
			}
			average = sum / vals.size();
			
			//stdev value
			double stdev = 0d;
			double stdevSum = 0d;
			for (Double double1 : vals) {
				stdevSum += Math.pow((double1-average), 2);  
			}
			stdev =  Math.sqrt(stdevSum/(vals.size()-1));
			
			return stdev;
		} catch (EvaluationException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
}
