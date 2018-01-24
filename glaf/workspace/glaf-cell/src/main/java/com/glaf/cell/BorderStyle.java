package com.glaf.cell;

public final class BorderStyle {
	
	public static final   String DASHED="dashed";//虚线框
	public static final   String DOTTED="dotted";//点线
	public static final   String DOUBLE="double";//双实线
	public static final   String GROOVE="groove";//压线
	public static final   String HIDDEN="hidden";//隐藏边框
	public static final   String INHERIT="inherit";//继承
	public static final   String INSET="inset";//内凹
	public static final   String NONE="none";//无边框线
	public static final   String OUT_SET="out_set";//
	public static final   String RIDGE="ridge";//隆起线
	public static final   String SOLID="solid";//实线
	
	public static String getBorderTyle(int styleIndex){
		switch (styleIndex) {
		case 0:
			return NONE;
		case 1:
			return HIDDEN;
		case 2:
			return "1pt "+SOLID;
		case 3:
			return "2pt "+SOLID;
		case 4:
			return "3pt "+SOLID;
		case 5:
			return "1pt "+DASHED;
		case 6:
			return "1pt "+DASHED;
		case 7:
			return "1pt "+DOTTED;
		case 8:
			return "1pt "+DOTTED;
		case 9:
			return "2pt "+DASHED;
		case 10:
			return "2pt "+DASHED;
		case 11:
			return "2pt "+DOTTED;
		case 12:
			return "2pt "+DOTTED;
		default:
			break;
		}
		return NONE;
	}
}