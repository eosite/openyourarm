package com.glaf.expression.core.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.math3.util.FastMath;

public class MathFunc {
	
	private final static String TYPE_LONG="java.lang.Long";
	private final static String TYPE_INTEGER="java.lang.Integer";
	private final static String TYPE_FLOAT="java.lang.Float";
	private final static String TYPE_DOUBLE="java.lang.Double";
	private final static String TYPE_STRING="java.lang.String";
	/**
	 * 取绝对值
	 * 
	 * @param x
	 * @return
	 */
	public static Object abs(Object x) {
		String dataType = (x.getClass()).getName();
		switch (dataType) {
		case TYPE_LONG:
			return FastMath.abs((Long) x);
		case TYPE_INTEGER:
			return FastMath.abs((int) x);
		case TYPE_FLOAT:
			return FastMath.abs((Float) x);
		case TYPE_DOUBLE:
			return FastMath.abs((Double) x);
		case TYPE_STRING:
			if(x!=null&&((String)x).equals("test"))
			{
				x="1";
			}
			return FastMath.abs(Double.valueOf((String)x) );
		default:
			break;
		}
		return 0;
	}

	/**
	 * 反余弦
	 * 
	 * @param x
	 * @return
	 */
	public static Object acos(Object x) {
		String dataType = (x.getClass()).getName();
		switch (dataType) {
		case TYPE_LONG:
			return FastMath.acos((Long) x);
		case TYPE_INTEGER:
			return FastMath.acos((int) x);
		case TYPE_FLOAT:
			return FastMath.acos((Float) x);
		case TYPE_DOUBLE:
			return FastMath.acos((Double) x);
		case TYPE_STRING:
			if(x!=null&&((String)x).equals("test"))
			{
				x="1";
			}
			return FastMath.acos(Double.valueOf((String)x) );
		default:
			break;
		}
		return 0;
	}

	/**
	 * 反双曲反余弦
	 * 
	 * @param x
	 * @return
	 */
	public static Object acosh(Object x) {
		String dataType = (x.getClass()).getName();
		switch (dataType) {
		case TYPE_LONG:
			return FastMath.acosh((Long) x);
		case TYPE_INTEGER:
			return FastMath.acosh((int) x);
		case TYPE_FLOAT:
			return FastMath.acosh((Float) x);
		case TYPE_DOUBLE:
			return FastMath.acosh((Double) x);
		case TYPE_STRING:
			if(x!=null&&((String)x).equals("test"))
			{
				x="1";
			}
			return FastMath.acosh(Double.valueOf((String)x) );
		default:
			break;
		}
		return 0;
	}

	/**
	 * 反正弦
	 * 
	 * @param x
	 * @return
	 */
	public static Object asin(Object x) {
		String dataType = (x.getClass()).getName();
		switch (dataType) {
		case TYPE_LONG:
			return FastMath.asin((Long) x);
		case TYPE_INTEGER:
			return FastMath.asin((int) x);
		case TYPE_FLOAT:
			return FastMath.asin((Float) x);
		case TYPE_DOUBLE:
			return FastMath.asin((Double) x);
		case TYPE_STRING:
			if(x!=null&&((String)x).equals("test"))
			{
				x="1";
			}
			return FastMath.asin(Double.valueOf((String)x) );
		default:
			break;
		}
		return 0;
	}

	/**
	 * 反双曲反正弦
	 * 
	 * @param x
	 * @return
	 */
	public static Object asinh(Object x) {
		String dataType = (x.getClass()).getName();
		switch (dataType) {
		case TYPE_LONG:
			return FastMath.asinh((Long) x);
		case TYPE_INTEGER:
			return FastMath.asinh((int) x);
		case TYPE_FLOAT:
			return FastMath.asinh((Float) x);
		case TYPE_DOUBLE:
			return FastMath.asinh((Double) x);
		case TYPE_STRING:
			if(x!=null&&((String)x).equals("test"))
			{
				x="1";
			}
			return FastMath.asinh(Double.valueOf((String)x) );
		default:
			break;
		}
		return 0;
	}

	/**
	 * 反正切
	 * 
	 * @param x
	 * @return
	 */
	public static Object atan(Object x) {
		String dataType = (x.getClass()).getName();
		switch (dataType) {
		case TYPE_LONG:
			return FastMath.atan((Long) x);
		case TYPE_INTEGER:
			return FastMath.atan((int) x);
		case TYPE_FLOAT:
			return FastMath.atan((Float) x);
		case TYPE_DOUBLE:
			return FastMath.atan((Double) x);
		case TYPE_STRING:
			if(x!=null&&((String)x).equals("test"))
			{
				x="1";
			}
			return FastMath.atan(Double.valueOf((String)x) );
		default:
			break;
		}
		return 0;
	}

	/**
	 * 反正切（XY轴）
	 * 
	 * @param x
	 * @return
	 */
	public static Object atan2(Object x, Object y) {
		String dataType = (x.getClass()).getName();
		switch (dataType) {
		case TYPE_LONG:
			return FastMath.atan2((Long) x, (Long) y);
		case TYPE_INTEGER:
			return FastMath.atan2((int) x, (int) y);
		case TYPE_FLOAT:
			return FastMath.atan2((Float) x, (Float) y);
		case TYPE_DOUBLE:
			return FastMath.atan2((Double) x, (Double) y);
		case TYPE_STRING:
			if(x!=null&&((String)x).equals("test"))
			{
				x="1";
				y="1";
			}
			return FastMath.atan2(Double.valueOf((String)x), Double.valueOf((String)y));
		default:
			break;
		}
		return FastMath.atan2((Double) x, (Double) y);
	}

	/**
	 * 反双曲正切值
	 * 
	 * @param x
	 * @return
	 */
	public static Object atanh(Object x) {
		String dataType = (x.getClass()).getName();
		switch (dataType) {
		case TYPE_LONG:
			return FastMath.atanh((Long) x);
		case TYPE_INTEGER:
			return FastMath.atanh((int) x);
		case TYPE_FLOAT:
			return FastMath.atanh((Float) x);
		case TYPE_DOUBLE:
			return FastMath.atanh((Double) x);
		case TYPE_STRING:
			if(x!=null&&((String)x).equals("test"))
			{
				x="1";
			}
			return FastMath.atanh(Double.valueOf((String)x));
		default:
			break;
		}
		return 0;
	}

	/**
	 * 余弦值
	 * 
	 * @param x
	 * @return
	 */
	public static Object cos(Object x) {
		String dataType = (x.getClass()).getName();
		switch (dataType) {
		case TYPE_LONG:
			return FastMath.cos((Long) x);
		case TYPE_INTEGER:
			return FastMath.cos((int) x);
		case TYPE_FLOAT:
			return FastMath.cos((Float) x);
		case TYPE_DOUBLE:
			return FastMath.cos((Double) x);
		case TYPE_STRING:
			if(x!=null&&((String)x).equals("test"))
			{
				x="1";
			}
			return FastMath.cos(Double.valueOf((String)x));
		default:
			break;
		}
		return 0;
	}

	/**
	 * 反双曲余弦值
	 * 
	 * @param x
	 * @return
	 */
	public static Object cosh(Object x) {
		String dataType = (x.getClass()).getName();
		switch (dataType) {
		case TYPE_LONG:
			return FastMath.cosh((Long) x);
		case TYPE_INTEGER:
			return FastMath.cosh((int) x);
		case TYPE_FLOAT:
			return FastMath.cosh((Float) x);
		case TYPE_DOUBLE:
			return FastMath.cosh((Double) x);
		case TYPE_STRING:
			if(x!=null&&((String)x).equals("test"))
			{
				x="1";
			}
			return FastMath.cosh(Double.valueOf((String)x));
		default:
			break;
		}
		return 0;
	}

	/**
	 * 立方根
	 * 
	 * @param x
	 * @return
	 */
	public static Object cbrt(Object x) {
		String dataType = (x.getClass()).getName();
		switch (dataType) {
		case TYPE_LONG:
			return FastMath.cbrt((Long) x);
		case TYPE_INTEGER:
			return FastMath.cbrt((int) x);
		case TYPE_FLOAT:
			return FastMath.cbrt((Float) x);
		case TYPE_DOUBLE:
			return FastMath.cbrt((Double) x);
		case TYPE_STRING:
			if(x!=null&&((String)x).equals("test"))
			{
				x="1";
			}
			return FastMath.cbrt(Double.valueOf((String)x));
		default:
			break;
		}
		return 0;
	}

	/**
	 * 向上取整
	 * 
	 * @param x
	 * @return
	 */
	public static Object ceil(Object x) {
		String dataType = (x.getClass()).getName();
		switch (dataType) {
		case TYPE_LONG:
			return FastMath.ceil((Long) x);
		case TYPE_INTEGER:
			return FastMath.ceil((int) x);
		case TYPE_FLOAT:
			return FastMath.ceil((Float) x);
		case TYPE_DOUBLE:
			return FastMath.ceil((Double) x);
		case TYPE_STRING:
			if(x!=null&&((String)x).equals("test"))
			{
				x="1.5";
			}
			return FastMath.ceil(Double.valueOf((String)x));
		default:
			break;
		}
		return 0;
	}

	/**
	 * e的n次方
	 * 
	 * @param x
	 * @return
	 */
	public static Object exp(Object x) {
		String dataType = (x.getClass()).getName();
		switch (dataType) {
		case TYPE_LONG:
			return FastMath.exp((Long) x);
		case TYPE_INTEGER:
			return FastMath.exp((int) x);
		case TYPE_FLOAT:
			return FastMath.exp((Float) x);
		case TYPE_DOUBLE:
			return FastMath.exp((Double) x);
		case TYPE_STRING:
			if(x!=null&&((String)x).equals("test"))
			{
				x="1";
			}
			return FastMath.exp(Double.valueOf((String)x));
		default:
			break;
		}
		return 0;
	}

	/**
	 * 
	 * @param x
	 * @return
	 */
	public static Object floor(Object x) {
		String dataType = (x.getClass()).getName();
		switch (dataType) {
		case TYPE_LONG:
			return FastMath.floor((Long) x);
		case TYPE_INTEGER:
			return FastMath.floor((int) x);
		case TYPE_FLOAT:
			return FastMath.floor((Float) x);
		case TYPE_DOUBLE:
			return FastMath.floor((Double) x);
		case TYPE_STRING:
			if(x!=null&&((String)x).equals("test"))
			{
				x="1";
			}
			return FastMath.floor(Double.valueOf((String)x));
		default:
			break;
		}
		return 0;
	}

	/**
	 * 自然对数
	 * 
	 * @param x
	 * @return
	 */
	public static Object log(Object x) {
		String dataType = (x.getClass()).getName();
		switch (dataType) {
		case TYPE_LONG:
			return FastMath.log((Long) x);
		case TYPE_INTEGER:
			return FastMath.log((int) x);
		case TYPE_FLOAT:
			return FastMath.log((Float) x);
		case TYPE_DOUBLE:
			return FastMath.log((Double) x);
		case TYPE_STRING:
			if(x!=null&&((String)x).equals("test"))
			{
				x="1";
			}
			return FastMath.log(Double.valueOf((String)x));
		default:
			break;
		}
		return 0;
	}

	/**
	 * 底数为10的自然对数
	 * 
	 * @param x
	 * @return
	 */
	public static Object log10(Object x) {
		String dataType = (x.getClass()).getName();
		switch (dataType) {
		case TYPE_LONG:
			return FastMath.log10((Long) x);
		case TYPE_INTEGER:
			return FastMath.log10((int) x);
		case TYPE_FLOAT:
			return FastMath.log10((Float) x);
		case TYPE_DOUBLE:
			return FastMath.log10((Double) x);
		case TYPE_STRING:
			if(x!=null&&((String)x).equals("test"))
			{
				x="1";
			}
			return FastMath.log10(Double.valueOf((String)x));
		default:
			break;
		}
		return 0;
	}

	/**
	 * 参数与1之和的自然对数
	 * 
	 * @param x
	 * @return
	 */
	public static Object log1P(Object x) {
		String dataType = (x.getClass()).getName();
		switch (dataType) {
		case TYPE_LONG:
			return FastMath.log1p(((Long) x).doubleValue());
		case TYPE_INTEGER:
			return FastMath.log1p(((Integer) x).doubleValue());
		case TYPE_FLOAT:
			return FastMath.log1p(((Float) x).doubleValue());
		case TYPE_DOUBLE:
			return FastMath.log1p((Double) x);
		case TYPE_STRING:
			if(x!=null&&((String)x).equals("test"))
			{
				x="1";
			}
			return FastMath.log1p(Double.valueOf((String)x));
		default:
			break;
		}
		return 0;
	}

	/**
	 * 取相邻的浮点数
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static Object nextAfter(Object x, Object y) {
		String dataType = (x.getClass()).getName();
		switch (dataType) {
		case TYPE_LONG:
			return FastMath.nextAfter(((Long) x).doubleValue(),
					((Long) y).doubleValue());
		case TYPE_INTEGER:
			return FastMath.nextAfter(((Integer) x).doubleValue(),
					((Integer) y).doubleValue());
		case TYPE_FLOAT:
			return FastMath.nextAfter(((Float) x).doubleValue(),
					((Float) y).doubleValue());
		case TYPE_DOUBLE:
			return FastMath.nextAfter((Double) x, (Double) y);
		default:
			break;
		}
		return 0;
	}

	/**
	 * 取相邻的浮点数
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static Object nextUp(Object x) {
		String dataType = (x.getClass()).getName();
		switch (dataType) {
		case TYPE_LONG:
			return FastMath.nextUp(((Long) x).doubleValue());
		case TYPE_INTEGER:
			return FastMath.nextUp(((Integer) x).doubleValue());
		case TYPE_FLOAT:
			return FastMath.nextUp(((Float) x).doubleValue());
		case TYPE_DOUBLE:
			return FastMath.nextUp((Double) x);
		case TYPE_STRING:
			if(x!=null&&((String)x).equals("test"))
			{
				x="1";
			}
			return FastMath.nextUp(Double.valueOf((String)x));
		default:
			break;
		}
		return 0;
	}

	/**
	 * 获取x、y之间的最大值
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static Object max(Object x, Object y) {
		String dataType = (x.getClass()).getName();
		switch (dataType) {
		case TYPE_LONG:
			return FastMath.max(((Long) x).doubleValue(),
					((Long) y).doubleValue());
		case TYPE_INTEGER:
			return FastMath.max(((Integer) x).doubleValue(),
					((Integer) y).doubleValue());
		case TYPE_FLOAT:
			return FastMath.max(((Float) x).doubleValue(),
					((Float) y).doubleValue());
		case TYPE_DOUBLE:
			return FastMath.max((Double) x, (Double) y);
		case TYPE_STRING:
			if(x!=null&&((String)x).equals("test"))
			{
				x="1.0";
				y="1.0";
			}
			return FastMath.max(Double.valueOf((String)x),Double.valueOf((String)y));
		default:
			break;
		}
		return 0;
	}

	/**
	 * 获取x、y之间的最小值
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static Object min(Object x, Object y) {
		String dataType = (x.getClass()).getName();
		switch (dataType) {
		case TYPE_LONG:
			return FastMath.min(((Long) x).doubleValue(),
					((Long) y).doubleValue());
		case TYPE_INTEGER:
			return FastMath.min(((Integer) x).doubleValue(),
					((Integer) y).doubleValue());
		case TYPE_FLOAT:
			return FastMath.min(((Float) x).doubleValue(),
					((Float) y).doubleValue());
		case TYPE_DOUBLE:
			return FastMath.min((Double) x, (Double) y);
		case TYPE_STRING:
			if(x!=null&&((String)x).equals("test"))
			{
				x="1.0";
				y="1.0";
			}
			return FastMath.min(Double.valueOf((String)x),Double.valueOf((String)y));
		default:
			break;
		}
		return 0;
	}

	/**
	 * 获取PI值
	 * 
	 * @return
	 */
	public static Object pi() {
		return FastMath.PI;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static Object power(Object x, Object y) {
		String dataType = (x.getClass()).getName();
		switch (dataType) {
		case TYPE_LONG:
			return FastMath.pow(((Long) x).doubleValue(),
					((Long) y).doubleValue());
		case TYPE_INTEGER:
			return FastMath.pow(((Integer) x).doubleValue(),
					((Integer) y).doubleValue());
		case TYPE_FLOAT:
			return FastMath.pow(((Float) x).doubleValue(),
					((Float) y).doubleValue());
		case TYPE_DOUBLE:
			return FastMath.pow((Double) x, (Double) y);
		case TYPE_STRING:
			if(x!=null&&((String)x).equals("test"))
			{
				x="1.0";
				y="1.0";
			}
			return FastMath.pow(Double.valueOf((String)x),Double.valueOf((String)y));
		default:
			break;
		}
		return 0;
	}

	/**
	 * 四舍五入
	 * 
	 * @param x
	 * @return
	 */
	public static Object round(Object x) {
		String dataType = (x.getClass()).getName();
		switch (dataType) {
		case TYPE_LONG:
			return FastMath.round(((Long) x).doubleValue());
		case TYPE_INTEGER:
			return FastMath.round(((Integer) x).doubleValue());
		case TYPE_FLOAT:
			return FastMath.round((Float) x);
		case TYPE_DOUBLE:
			return FastMath.round((Double) x);
		case TYPE_STRING:
			if(x!=null&&((String)x).equals("test"))
			{
				x="1.0";
			}
			return FastMath.round(Double.valueOf((String)x));
		default:
			break;
		}
		return 0;
	}

	/**
	 * 取随机数
	 * 
	 * @return
	 */
	public static Object rand() {
		return FastMath.random();
	}

	/**
	 * 获取x-y间的随机数
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static Object randBetween(Object x, Object y) {
		String dataType = (x.getClass()).getName();
		switch (dataType) {
		case TYPE_LONG:
			return RandomUtils.nextLong((Long) x, (Long) y);
		case TYPE_INTEGER:
			return RandomUtils.nextInt((Integer) x, (Integer) y);
		case TYPE_FLOAT:
			return RandomUtils.nextFloat((Float) x, (Float) y);
		case TYPE_DOUBLE:
			return RandomUtils.nextDouble((Double) x, (Double) y);
		case TYPE_STRING:
			if(x!=null&&((String)x).equals("test"))
			{
				x="1.0";
				y="1.0";
			}
			return RandomUtils.nextDouble(Double.valueOf((String)x),Double.valueOf((String)y));
		default:
			break;
		}
		return 0;
	}

	/**
	 * 判断数值正负号
	 * 
	 * @param x
	 * @return
	 */
	public static int sign(Object x) {
		String dataType = (x.getClass()).getName();
		switch (dataType) {
		case TYPE_LONG:
			return (int) FastMath.signum(((Long) x).doubleValue());
		case TYPE_INTEGER:
			return (int) FastMath.signum(((Integer) x).doubleValue());
		case TYPE_FLOAT:
			return (int) FastMath.signum((Float) x);
		case TYPE_DOUBLE:
			return (int) FastMath.signum((Double) x);
		case TYPE_STRING:
			if(x!=null&&((String)x).equals("test"))
			{
				x="1.0";
			}
			return (int) FastMath.signum(Double.valueOf((String)x) );	
		default:
			break;
		}
		return 0;
	}

	/**
	 * 正弦
	 * 
	 * @param x
	 * @return
	 */
	public static Object sin(Object x) {
		String dataType = (x.getClass()).getName();
		switch (dataType) {
		case TYPE_LONG:
			return FastMath.sin(((Long) x).doubleValue());
		case TYPE_INTEGER:
			return FastMath.sin(((Integer) x).doubleValue());
		case TYPE_FLOAT:
			return FastMath.sin(((Float) x).doubleValue());
		case TYPE_DOUBLE:
			return FastMath.sin((Double) x);
		case TYPE_STRING:
			if(x!=null&&((String)x).equals("test"))
			{
				x="1.0";
			}
			return (int) FastMath.sin(Double.valueOf((String)x) );	
		default:
			break;
		}
		return 0;
	}

	/**
	 * 双曲正弦
	 * 
	 * @param x
	 * @return
	 */
	public static Object sinh(Object x) {
		String dataType = (x.getClass()).getName();
		switch (dataType) {
		case TYPE_LONG:
			return FastMath.sinh(((Long) x).doubleValue());
		case TYPE_INTEGER:
			return FastMath.sinh(((Integer) x).doubleValue());
		case TYPE_FLOAT:
			return FastMath.sinh(((Float) x).doubleValue());
		case TYPE_DOUBLE:
			return FastMath.sinh((Double) x);
		case TYPE_STRING:
			if(x!=null&&((String)x).equals("test"))
			{
				x="1.0";
			}
			return (int) FastMath.sinh(Double.valueOf((String)x) );	
		default:
			break;
		}
		return 0;
	}

	/**
	 * 正切
	 * 
	 * @param x
	 * @return
	 */
	public static Object tan(Object x) {
		String dataType = (x.getClass()).getName();
		switch (dataType) {
		case TYPE_LONG:
			return FastMath.tan(((Long) x).doubleValue());
		case TYPE_INTEGER:
			return FastMath.tan(((Integer) x).doubleValue());
		case TYPE_FLOAT:
			return FastMath.tan(((Float) x).doubleValue());
		case TYPE_DOUBLE:
			return FastMath.tan((Double) x);
		case TYPE_STRING:
			if(x!=null&&((String)x).equals("test"))
			{
				x="1.0";
			}
			return (int) FastMath.tan(Double.valueOf((String)x) );	
		default:
			break;
		}
		return 0;
	}

	/**
	 * 双曲正切
	 * 
	 * @param x
	 * @return
	 */
	public static Object tanh(Object x) {
		String dataType = (x.getClass()).getName();
		switch (dataType) {
		case TYPE_LONG:
			return FastMath.tanh(((Long) x).doubleValue());
		case TYPE_INTEGER:
			return FastMath.tanh(((Integer) x).doubleValue());
		case TYPE_FLOAT:
			return FastMath.tanh(((Float) x).doubleValue());
		case TYPE_DOUBLE:
			return FastMath.tanh((Double) x);
		case TYPE_STRING:
			if(x!=null&&((String)x).equals("test"))
			{
				x="1.0";
			}
			return (int) FastMath.tanh(Double.valueOf((String)x) );	
		default:
			break;
		}
		return 0;
	}

	/**
	 * 数值截取
	 * 
	 * @param x
	 * @param bits
	 * @return
	 */
	public static Object trunc(Object x, int bits) {
		String dataType = (x.getClass()).getName();
		BigDecimal b = new BigDecimal(Double.toString((Double) x));
		switch (dataType) {
		case TYPE_LONG:
			b = new BigDecimal(Long.toString((Long) x));
			break;
		case TYPE_INTEGER:
			b = new BigDecimal(Integer.toString((Integer) x));
			break;
		case TYPE_FLOAT:
			b = new BigDecimal(Float.toString((Float) x));
			break;
		case TYPE_DOUBLE:
			b = new BigDecimal(Double.toString((Double) x));
			break;
		case TYPE_STRING:
			if(x!=null&&((String)x).equals("test"))
			{
				x="1.0";
			}
			b = new BigDecimal((String) x);
			break;
		default:
			break;
		}
		if (bits < 1)
			bits = 1;
		BigDecimal one = new BigDecimal("1");
		double nx = b.divide(one, bits, BigDecimal.ROUND_HALF_UP).doubleValue();

		return nx;
	}

	/**
	 * 弧度转角度
	 * 
	 * @param x
	 * @return
	 */
	public static Object toDegrees(Object x) {
		String dataType = (x.getClass()).getName();
		switch (dataType) {
		case TYPE_LONG:
			return FastMath.toDegrees(((Long) x).doubleValue());
		case TYPE_INTEGER:
			return FastMath.toDegrees(((Integer) x).doubleValue());
		case TYPE_FLOAT:
			return FastMath.toDegrees(((Float) x).doubleValue());
		case TYPE_DOUBLE:
			return FastMath.toDegrees((Double) x);
		case TYPE_STRING:
			return FastMath.toDegrees(Double.valueOf((String)x) );
		default:
			break;
		}
		return 0;
	}

	/**
	 * 角度转弧度
	 * 
	 * @param x
	 * @return
	 */
	public static Object toRadians(Object x) {
		String dataType = (x.getClass()).getName();
		switch (dataType) {
		case TYPE_LONG:
			return FastMath.toRadians(((Long) x).doubleValue());
		case TYPE_INTEGER:
			return FastMath.toRadians(((Integer) x).doubleValue());
		case TYPE_FLOAT:
			return FastMath.toRadians(((Float) x).doubleValue());
		case TYPE_DOUBLE:
			return FastMath.toRadians((Double) x);
		case TYPE_STRING:
			if(x!=null&&((String)x).equals("test"))
			{
				x="1.0";
			}
			return FastMath.toRadians(Double.valueOf((String)x) );
		default:
			break;
		}
		return 0;
	}

	/**
	 * 精确求和
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static Object add(Object x, Object y) {
		String xDataType = (x.getClass()).getName();
		String yDataType = (y.getClass()).getName();
		BigDecimal b1 =null;
		BigDecimal b2 =null;
		switch (xDataType) {
		case TYPE_LONG:
			b1 = new BigDecimal(Long.toString((Long) x));
			break;
		case TYPE_INTEGER:
			b1 = new BigDecimal(Integer.toString((Integer) x));
			break;
		case TYPE_FLOAT:
			b1 = new BigDecimal(Float.toString((Float) x));
			break;
		case TYPE_DOUBLE:
			b1 = new BigDecimal(Double.toString((Double) x));
			break;
		case TYPE_STRING:
			if(x!=null&&((String)x).equals("test"))
			{
				x="1.0";
			}
			b1 = new BigDecimal((String) x);
			break;
		default:
			break;
		}
		switch (yDataType) {
		case TYPE_LONG:
			b2 = new BigDecimal(Long.toString((Long) y));
			break;
		case TYPE_INTEGER:
			b2 = new BigDecimal(Integer.toString((Integer) y));
			break;
		case TYPE_FLOAT:
			b2 = new BigDecimal(Float.toString((Float) y));
			break;
		case TYPE_DOUBLE:
			b2 = new BigDecimal(Double.toString((Double) y));
			break;
		case TYPE_STRING:
			if(y!=null&&((String)y).equals("test"))
			{
				y="1.0";
			}
			b2 = new BigDecimal((String) y);
			break;
		default:
			break;
		}
		return b1.add(b2).toString();
	}
	/**
	 * 精确求差
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static Object sub(Object x, Object y) {
		String xDataType = (x.getClass()).getName();
		String yDataType = (y.getClass()).getName();
		BigDecimal b1 =null;
		BigDecimal b2 =null;
		switch (xDataType) {
		case TYPE_LONG:
			b1 = new BigDecimal(Long.toString((Long) x));
			break;
		case TYPE_INTEGER:
			b1 = new BigDecimal(Integer.toString((Integer) x));
			break;
		case TYPE_FLOAT:
			b1 = new BigDecimal(Float.toString((Float) x));
			break;
		case TYPE_DOUBLE:
			b1 = new BigDecimal(Double.toString((Double) x));
			break;
		case TYPE_STRING:
			if(x!=null&&((String)x).equals("test"))
			{
				x="1.0";
			}
			b1 = new BigDecimal((String) x);
			break;
		default:
			break;
		}
		switch (yDataType) {
		case TYPE_LONG:
			b2 = new BigDecimal(Long.toString((Long) y));
			break;
		case TYPE_INTEGER:
			b2 = new BigDecimal(Integer.toString((Integer) y));
			break;
		case TYPE_FLOAT:
			b2 = new BigDecimal(Float.toString((Float) y));
			break;
		case TYPE_DOUBLE:
			b2 = new BigDecimal(Double.toString((Double) y));
			break;
		case TYPE_STRING:
			if(y!=null&&((String)y).equals("test"))
			{
				y="1.0";
			}
			b2 = new BigDecimal((String) y);
			break;
		default:
			break;
		}
		return b1.subtract(b2).toString();
	}
	/**
	 * 精确乘法
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static Object mul(Object x, Object y) {
		String xDataType = (x.getClass()).getName();
		String yDataType = (y.getClass()).getName();
		BigDecimal b1 =null;
		BigDecimal b2 =null;
		switch (xDataType) {
		case TYPE_LONG:
			b1 = new BigDecimal(Long.toString((Long) x));
			break;
		case TYPE_INTEGER:
			b1 = new BigDecimal(Integer.toString((Integer) x));
			break;
		case TYPE_FLOAT:
			b1 = new BigDecimal(Float.toString((Float) x));
			break;
		case TYPE_DOUBLE:
			b1 = new BigDecimal(Double.toString((Double) x));
			break;
		case TYPE_STRING:
			if(x!=null&&((String)x).equals("test"))
			{
				x="1.0";
			}
			b1 = new BigDecimal((String) x);
			break;
		default:
			break;
		}
		switch (yDataType) {
		case TYPE_LONG:
			b2 = new BigDecimal(Long.toString((Long) y));
			break;
		case TYPE_INTEGER:
			b2 = new BigDecimal(Integer.toString((Integer) y));
			break;
		case TYPE_FLOAT:
			b2 = new BigDecimal(Float.toString((Float) y));
			break;
		case TYPE_DOUBLE:
			b2 = new BigDecimal(Double.toString((Double) y));
			break;
		case TYPE_STRING:
			if(y!=null&&((String)y).equals("test"))
			{
				y="1.0";
			}
			b2 = new BigDecimal((String) y);
			break;
		default:
			break;
		}
		return b1.multiply(b2).toString();
	}
	/**
	 * 精确除法
	 * 
	 * @param x 被除数
	 * @param y 除数
	 * @param scale 位数
	 * @return
	 */
	public static Object div(Object x, Object y,int scale) {
		String xDataType = (x.getClass()).getName();
		String yDataType = (y.getClass()).getName();
		BigDecimal b1 =null;
		BigDecimal b2 =null;
		switch (xDataType) {
		case TYPE_LONG:
			b1 = new BigDecimal(Long.toString((Long) x));
			break;
		case TYPE_INTEGER:
			b1 = new BigDecimal(Integer.toString((Integer) x));
			break;
		case TYPE_FLOAT:
			b1 = new BigDecimal(Float.toString((Float) x));
			break;
		case TYPE_DOUBLE:
			b1 = new BigDecimal(Double.toString((Double) x));
			break;
		case TYPE_STRING:
			if(x!=null&&((String)x).equals("test"))
			{
				x="1.0";
			}
			b1 = new BigDecimal((String) x);
			break;
		default:
			break;
		}
		switch (yDataType) {
		case TYPE_LONG:
			b2 = new BigDecimal(Long.toString((Long)y));
			break;
		case TYPE_INTEGER:
			b2 = new BigDecimal(Integer.toString((Integer) y));
			break;
		case TYPE_FLOAT:
			b2 = new BigDecimal(Float.toString((Float) y));
			break;
		case TYPE_DOUBLE:
			b2 = new BigDecimal(Double.toString((Double) y));
			break;
		case TYPE_STRING:
			if(y!=null&&((String)y).equals("test"))
			{
				y="1.0";
			}
			b2 = new BigDecimal((String) y);
			break;
		default:
			break;
		}
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toString();
	}
	/**
	 * 数值格式化
	 * @param x  被格式数值
	 * @param bits 小数位数
	 * @return
	 */
	public static Object formatNumber(Object x,int bits)
	{
		String dataType = (x.getClass()).getName();
	 	String formatStr="######0";
	 	for(int i=0;i<bits;i++){
	 		if(i==0)
	 		{
	 			formatStr+=".";
	 		}
	 		formatStr+="0";
	 	}
	 	BigDecimal b =null;
	 	BigDecimal one = new BigDecimal("1");
	 	DecimalFormat df=new DecimalFormat(formatStr);
	 	switch (dataType) {
		case TYPE_LONG:
			b = new BigDecimal(Long.toString((Long) x));
		 	return df.format(b.divide(one, bits, BigDecimal.ROUND_HALF_UP).longValue());
		case TYPE_INTEGER:
			b = new BigDecimal(Integer.toString((Integer) x));
		 	return df.format(b.divide(one, bits, BigDecimal.ROUND_HALF_UP).intValue());
		case TYPE_FLOAT:
			b = new BigDecimal(Float.toString((Float) x));
			return df.format(b.divide(one, bits, BigDecimal.ROUND_HALF_UP).floatValue());
		case TYPE_DOUBLE:
			b = new BigDecimal(Double.toString((Double) x));
			return df.format(b.divide(one, bits, BigDecimal.ROUND_HALF_UP).doubleValue());
		case TYPE_STRING:
			if(x!=null&&((String)x).equals("test"))
			{
				x="100.11";
			}
			b = new BigDecimal((String)x);
			return df.format(b.divide(one, bits, BigDecimal.ROUND_HALF_UP).doubleValue());
		default:
			break;
		}
	 	return 0;
	}
	public static void main(String[] args) {
//		System.out.println(MathFunc.abs(-1l));
//		System.out.println(MathFunc.abs(-1f));
//		System.out.println(MathFunc.abs(-1d));
//		System.out.println(MathFunc.abs(-1));
//		
//		System.out.println(MathFunc.formatNumber(121.323f, 1));
//		System.out.println(MathFunc.formatNumber(121.323f, 0));
//		System.out.println(MathFunc.formatNumber(121.323d, 1));
//		System.out.println(MathFunc.formatNumber(121.323d, 0));
//		System.out.println(MathFunc.formatNumber(23232323, 0));
//		System.out.println(MathFunc.formatNumber("23232323.323", 2));
		
		System.out.println(MathFunc.div(121.323f, 10.1f,1));
		System.out.println(MathFunc.div(121.323f, 10.1f, 0));
		System.out.println(MathFunc.div(121.323d, 10.1d, 1));
		System.out.println(MathFunc.div(121.323d, 10.1d, 0));
		System.out.println(MathFunc.div(10, 100,0));
		System.out.println(MathFunc.div(23232323, 21, 2));
	}
}
