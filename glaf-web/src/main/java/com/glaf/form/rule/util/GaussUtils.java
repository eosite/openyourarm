package com.glaf.form.rule.util;

public class GaussUtils {
	private static int intSignOfDms, intSignOfRad;
	private static double radAngle, dmsAngle;
	
	public static final int BJ54 = 0;
	public static final int XA80 = 1;
	
	public static void main(String[] args) {
		/*double B = 2887501.4779 ;
		double L = 502485.8584 ;
		double l0 = 119.5833333333333 ;*/
		double B = 2712389.33934074 ;
		double L = 428284.043387004 ;
		double l0 = 111 ;
		//System.out.println("B:"+B);
		//System.out.println("L:"+L);
		//System.out.println("中央线:"+l0);
		//System.out.println("------北京54--------");
		double[] ret = new double[2];
		ret = GaussUtils.BJ54ToWGS84(B, L,l0);
		//System.out.println("X:" + ret[1]);
		//System.out.println("Y:" + ret[0]);
		//System.out.println("------西安80--------");
		ret = GaussUtils.XA80ToWGS84(B, L,l0);
		//System.out.println("X:" + ret[1]);
		//System.out.println("Y:" + ret[0]);
		
	}

	private static double DMSTORAD(double dmsAngle1) {
		intSignOfDms = 1;
		if (dmsAngle1 < 0)
			intSignOfDms = -1;
		dmsAngle1 = Math.abs(dmsAngle1);

		radAngle = dmsAngle1 * Math.PI / 180.0;
		radAngle = radAngle * intSignOfDms;
		return radAngle;
	}

	private static double RADTODMS(double radAngle) {
		intSignOfRad = 1;
		if (radAngle < 0)
			intSignOfRad = -1;
		radAngle = Math.abs(radAngle);

		dmsAngle = radAngle * 180 / Math.PI;
		dmsAngle = dmsAngle * intSignOfRad;
		return dmsAngle;
	}

	private static void a0a2a4a6a8(double a, double e, double[] Coeficient_a0) {
		double m0, m2, m4, m6, m8;
		m0 = a * (1 - e * e);
		m2 = 3 * e * e * m0 / 2;
		m4 = 5 * e * e * m2 / 4;
		m6 = 7 * e * e * m4 / 6;
		m8 = 9 * e * e * m6 / 8;

		/* 计算a0 a2 a4 a6 a8 */

		Coeficient_a0[0] = m0 + m2 / 2 + 3 * m4 / 8 + 5 * m6 / 16 + 35 * m8 / 128;
		Coeficient_a0[1] = m2 / 2 + m4 / 2 + 15 * m6 / 32 + 7 * m8 / 16;
		Coeficient_a0[2] = m4 / 8 + 3 * m6 / 16 + 7 * m8 / 32;
		Coeficient_a0[3] = m6 / 32 + m8 / 16;
		Coeficient_a0[4] = m8 / 128;
	}

	// / <summary>
	// / 中央经度
	// / </summary>
	// private static double _l0 = 108;
	private static double _l0 = 119.5833333333333;

	// / <summary>
	// / 东纬偏移FE = 500000米
	// / </summary>
	private static double _FE = 500000;

	// / <summary>
	// / 长半轴 a（米）
	// / </summary>
	private static double _a = 6378245;

	// / <summary>
	// / 扁率α
	// / </summary>
	private static double _af = 1 / 298.3;
	
	public static double[] toWGS84(double dmslon, double dmslat,double l0,int type){
		double[] ret = null ;
		switch (type) {
		case BJ54:
			ret = BJ54ToWGS84(dmslon, dmslat, l0);
			break;
		case XA80:
			ret = XA80ToWGS84(dmslon, dmslat, l0);
			break;
		default:
			break;
		}
		return ret ;
	}
	
	
	/**
	 * 北京54 转换
	 * @param dmslon
	 * @param dmslat
	 * @param l0
	 * @return
	 */
	public static double[] BJ54ToWGS84(double dmslon, double dmslat,double l0){
		_l0 = l0 ;
		
		// 54年北京坐标系参数
		_a = 6378245.0;
		_af = 1.0 / 298.3; 
		
		return toWGS84(dmslon, dmslat);
	}
	/**
	 * 西安80转换
	 * @param dmslon
	 * @param dmslat
	 * @param l0
	 * @return
	 */
	public static double[] XA80ToWGS84(double dmslon, double dmslat,double l0){
		_l0 = l0 ;
		
		//西安 80
		_a = 6378140.0;
		_af = 1/298.257 ;
		
		return toWGS84(dmslon, dmslat);
	}
	
	// / <summary>
	// / WGS84坐标转为北京54坐标
	// / </summary>
	// / <param name="dmslon"></param>
	// / <param name="dmslat"></param>
	// / <param name="dmsl0"></param>
	// / <param name="coor_x"></param>
	// / <param name="coor_y"></param>
	public static double[] WGS84ToBJ54(double dmslon, double dmslat) {
		// int h = 0, k = 0;
		double a = 0, Alfa = 0;
		double dmsl0 = 0;
		double a0, a2, a4, a6, a8;
		double radlat, radlon, radl0, l;
		double b, t, sb, cb, ita, e1, ee;
		double X, l0;
		double N, c, v;
		// double Bf0, Bf1, dB, FBf, bf;
		// double itaf, tf;
		// double Nf, Mf;
		// double B, L, dietaB, dietal;
		// double sinBf, cosBf;
		double[] Coeficient_a0 = new double[5];

		a = _a; // 长半轴 a（米）
		Alfa = _af;
		dmsl0 = _l0;

		/* 将角度转化为弧度 */
		radlat = GaussUtils.DMSTORAD(dmslat);
		radlon = GaussUtils.DMSTORAD(dmslon);
		radl0 = GaussUtils.DMSTORAD(dmsl0);
		l = radlon - radl0;

		/* 计算椭球的基本参数和中间变量 */

		b = a * (1 - Alfa);
		sb = Math.sin(radlat);
		cb = Math.cos(radlat);
		t = sb / cb;
		e1 = Math.sqrt((a / b) * (a / b) - 1);
		ee = Math.sqrt(1 - (b / a) * (b / a));
		ita = e1 * cb;

		/* 计算a0 a2 a4 a6 a8 */
		GaussUtils.a0a2a4a6a8(a, ee, Coeficient_a0);

		a0 = Coeficient_a0[0];
		a2 = Coeficient_a0[1];
		a4 = Coeficient_a0[2];
		a6 = Coeficient_a0[3];
		a8 = Coeficient_a0[4];

		/* 计算X */
		X = a0 * radlat - sb * cb * ((a2 - a4 + a6) + (2 * a4 - 16 * a6 / 3) * sb * sb + 16 * a6 * sb * sb * sb * sb / 3.0);

		/* 计算卯酉圈半径N */

		c = a * a / b;
		v = Math.sqrt(1 + e1 * e1 * cb * cb);
		N = c / v;

		/* 计算未知点的坐标 */

		double coor_x, coor_y;
		coor_x = X + N * sb * cb * l * l / 2 + N * sb * cb * cb * cb * (5 - t * t + 9 * ita * ita + 4 * ita * ita * ita * ita) * l * l * l * l / 24 + N * sb * cb * cb * cb * cb
				* cb * (61 - 58 * t * t + t * t * t * t) * l * l * l * l * l * l / 720;
		coor_y = N * cb * l + N * cb * cb * cb * (1 - t * t + ita * ita) * l * l * l / 6 + N * cb * cb * cb * cb * cb
				* (5 - 18 * t * t + t * t * t * t + 14 * ita * ita - 58 * ita * ita * t * t) * l * l * l * l * l / 120;

		// 东纬偏移FE = 500000米 + 带号 * 1000000；
		coor_y += _FE;
		//System.out.println(coor_x);
		//System.out.println(coor_y);
		double[] output = new double[2];
		output[0] = coor_x ;
		output[1] = coor_y ;
		return output;
	}
	
	private static double[] toWGS84(double coor_x, double coor_y) {
		// int h = 0, k = 0;
		double a = 0, Alfa = 0;
		double a0, a2, a4, a6, a8;
		// double radlat, radlon, radl0, l;
		double b, t, sb, cb, ita, e1, ee;
		double X, l0;
		double N, c, v;
		double Bf0, Bf1, dB, FBf, bf;
		double itaf, tf;
		double Nf, Mf;
		double B, L, dietaB, dietal;
		double sinBf, cosBf;
		double[] Coeficient_a0 = new double[5];

		l0 = _l0 * Math.PI / 180.0;
		a = _a; // 长半轴 a（米）
		Alfa = _af; // 扁率

		// 东纬偏移FE = 500000米 + 带号 * 1000000；
		coor_y -= _FE;

		/* 计算b,e1,e */
		b = a * (1 - Alfa);
		e1 = Math.sqrt((a / b) * (a / b) - 1);
		ee = Math.sqrt(1 - (b / a) * (b / a));

		/* 计算a0 a2 a4 a6 a8 */

		GaussUtils.a0a2a4a6a8(a, ee, Coeficient_a0);

		a0 = Coeficient_a0[0];
		a2 = Coeficient_a0[1];
		a4 = Coeficient_a0[2];
		a6 = Coeficient_a0[3];
		a8 = Coeficient_a0[4];

		X = coor_x;
		Bf0 = X / a0;

		do {
			sinBf = Math.sin(Bf0);
			cosBf = Math.cos(Bf0);
			FBf = -sinBf * cosBf * ((a2 - a4 + a6) + (2.0 * a4 - 16.0 * a6 / 3.0) * sinBf * sinBf + (16.0 / 3.0) * a6 * (sinBf * sinBf * sinBf * sinBf));
			Bf1 = (X - FBf) / a0;
			dB = Bf1 - Bf0;
			Bf0 = Bf1;
		} while (Math.abs(dB * 180.0 / Math.PI * 3600.0) > 0.00001);

		bf = Bf1;
		/* 计算c,v,N,M */
		c = a * a / b;
		v = Math.sqrt(1 + e1 * e1 * Math.cos(bf) * Math.cos(bf));
		Nf = c / v;
		Mf = c / (v * v * v);
		tf = Math.sin(bf) / Math.cos(bf);
		itaf = e1 * Math.cos(bf);

		/* 计算dietaB,dietal */
		dietaB = tf * coor_y * coor_y / (2 * Mf * Nf) - tf * (5 + 3 * tf * tf + itaf * itaf - 9 * tf * tf * itaf * itaf) * coor_y * coor_y * coor_y * coor_y
				/ (24 * Mf * Nf * Nf * Nf) + (61 + 90 * tf * tf + 45 * tf * tf * tf * tf) * coor_y * coor_y * coor_y * coor_y * coor_y * coor_y
				/ (720 * Mf * Nf * Nf * Nf * Nf * Nf);
		dietal = coor_y / (Nf * Math.cos(bf) + (1 + 2 * tf * tf + itaf * itaf) * Math.cos(bf) * coor_y * coor_y / (6 * Nf))
				+ (5 + 44 * tf * tf + 32 * tf * tf * tf * tf - 2 * itaf * itaf - 16 * itaf * itaf * tf * tf) / (360 * Nf * Nf * Nf * Mf * Mf * Math.cos(bf));

		B = bf - dietaB;
		L = l0 + dietal;
		double[] output = new double[2];
		output[0] = GaussUtils.RADTODMS(B);
		output[1] = GaussUtils.RADTODMS(L);
		return output;
	}

}
