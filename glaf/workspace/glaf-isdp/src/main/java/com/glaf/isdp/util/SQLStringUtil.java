package com.glaf.isdp.util;

import java.util.ArrayList;
import java.util.List;

public class SQLStringUtil {
	/**
	 * 构造in语句，若valueList超过1000时，该函数会自动拆分成多个in语句
	 * 
	 * @param item
	 * @param valueList
	 * @return item in (valueList)
	 */
	public static String buildLogicIN(String item, List<?> valueList) {
		int n = (valueList.size() - 1) / 1000;
		StringBuffer rtnStr = new StringBuffer();
		Object obj = valueList.get(0);
		boolean isString = false;
		if (obj instanceof Character || obj instanceof String)
			isString = true;
		String tmpStr;
		for (int i = 0; i <= n; i++) {
			int size = i == n ? valueList.size() : (i + 1) * 1000;
			if (i > 0)
				rtnStr.append(" or ");
			rtnStr.append(item + " in (");
			if (isString) {
				StringBuffer tmpBuf = new StringBuffer();
				for (int j = i * 1000; j < size; j++) {
					tmpStr = valueList.get(j).toString().replaceAll("'", "''");
					tmpBuf.append(",'").append(tmpStr).append("'");
				}
				tmpStr = tmpBuf.substring(1);
			} else {
				tmpStr = valueList.subList(i * 1000, size).toString();
				tmpStr = tmpStr.substring(1, tmpStr.length() - 1);
			}
			rtnStr.append(tmpStr);
			rtnStr.append(")");
		}
		if (n > 0)
			return "(" + rtnStr.toString() + ")";
		else
			return rtnStr.toString();
	}
	
	/**
	 * 构造相反的like语句，若valueList超过1000时，该函数会自动拆分成多个in语句
	 * @param item
	 * @param valueList
	 * @return valueList like item+'%'
	 */
	public static String buildTurnLogicLike(String item,List<String> valueList){
		StringBuffer rtnStr = new StringBuffer();
		for(int i=0;i<valueList.size();i++){
			String value = valueList.get(i);
			if(i>0)
				rtnStr.append(" or ");
			rtnStr.append(" '").append(value).append("' like ").append(item).append("+'%'");
		}
		
		if(valueList.size()>1)
			return "("+rtnStr.toString()+")";
		else
			return rtnStr.toString();
	}
	
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("1|95|222|230|263|271|281|360|362|");
		//list.add("1|95|96|99|147|460|462|");
		System.out.println(buildTurnLogicLike("id", list));
	}
}
