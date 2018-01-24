package com.glaf.form.rule.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.mvel2.optimizers.impl.refl.nodes.ArrayLength;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.form.core.domain.GisExcelModel;

public class GisExcellImportUtils {
	public static final String POINT = "点" ;
	public static final String POLYLINE = "线" ;
	
	
	public GisExcellImportUtils() {
	}

	public static Workbook getWorkbook(InputStream inp) throws Exception {
		return WorkbookFactory.create(inp);
	}

	public static void getCellValue(Cell cell) {
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				// map.put("", value);
				////System.out.println(cell.getNumericCellValue());
				break;
			case Cell.CELL_TYPE_BLANK:// 空白
				break;
			case Cell.CELL_TYPE_BOOLEAN:// 布尔
				////System.out.println(cell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA:// 公式
				////System.out.println(cell.getCellFormula());
				break;
			case Cell.CELL_TYPE_ERROR:// 错误
				////System.out.println(cell.getErrorCellValue());
				break;
			default:
				////System.out.println(cell.getStringCellValue());
				break;
			}
		}
	}

	public static void putCell(Map<String, List<GisExcelModel>> map, String key, GisExcelModel gisExcelModel) {
		if (map.containsKey(key)) {
			List<GisExcelModel> list = map.get(key);
			list.add(gisExcelModel);
		} else {
			List<GisExcelModel> list = new ArrayList<GisExcelModel>();
			list.add(gisExcelModel);
			map.put(key, list);
		}
	}
	
	/***
	 * 根据名称和点分组
	 * @param inp
	 * @param l0
	 * @param type
	 * @return
	 */
	public static JSONArray readExcel(InputStream inp,double l0,int type) {
		int startRow = 2;
		int startCell = 1;
		List<Map<String, List<GisExcelModel>>> retList = new ArrayList< Map<String, List<GisExcelModel>>>();
		try {
			Workbook wb = getWorkbook(inp);
			int sheetLength = wb.getNumberOfSheets();
			Sheet sheet = null;
			int lastRowNum = 0;
			Map<String, List<GisExcelModel>> map = null;
			GisExcelModel gisExcelModel = null;
			for (int i = 0; i < sheetLength; i++) {
				sheet = wb.getSheetAt(i);
				lastRowNum = sheet.getLastRowNum();
				map = new HashMap<String, List<GisExcelModel>>();
				while (startRow < lastRowNum) {
					Row row = sheet.getRow(startRow++);
					// lastCellNum = row.getLastCellNum();

					Cell stakeCell = row.getCell(1); // 桩号
					Cell namCell = row.getCell(2); // 名称
					Cell typeCell = row.getCell(3); // 类型 点 或者 线
					Cell xCell = row.getCell(4); // x 坐标
					Cell yCell = row.getCell(5); // y 坐标
					String key = namCell.getStringCellValue() + typeCell.getStringCellValue();
					double[] point = GaussUtils.toWGS84(xCell.getNumericCellValue(), yCell.getNumericCellValue(), l0,type);
					gisExcelModel = new GisExcelModel(stakeCell.getStringCellValue(), namCell.getStringCellValue(), typeCell.getStringCellValue(), 
							point);

					putCell(map, key, gisExcelModel);

					stakeCell = row.getCell(7); // 桩号
					namCell = row.getCell(8); // 名称
					typeCell = row.getCell(9); // 类型 点 或者 线
					xCell = row.getCell(10); // x 坐标
					yCell = row.getCell(11); // y 坐标
					key = namCell.getStringCellValue() + typeCell.getStringCellValue();
					point = GaussUtils.toWGS84(xCell.getNumericCellValue(), yCell.getNumericCellValue(), l0,type);
					gisExcelModel = new GisExcelModel(stakeCell.getStringCellValue(), namCell.getStringCellValue(), typeCell.getStringCellValue(), point);

					putCell(map, key, gisExcelModel);
				}
				if(map.size()>0){
					retList.add(map);
				}
			}
			return buildSource(retList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static JSONArray buildSource(List<Map<String, List<GisExcelModel>>> list){
		JSONArray retAry = new JSONArray();
		for (Map<String, List<GisExcelModel>> map : list) {
			Set<String> set = map.keySet();
			for (String key : set) {
				JSONObject retObj = new JSONObject();
				
				List<GisExcelModel> kk = map.get(key);
				JSONObject obj = new JSONObject();
				
				JSONObject sourceObj = new JSONObject();
				boolean isLine = false ;
				JSONArray firstAry = new JSONArray();
				JSONArray secondAry = new JSONArray();
				firstAry.add(secondAry);
				for (GisExcelModel gisExcelModel : kk) {
					if(kk.indexOf(gisExcelModel)==0){
						retObj.put("name", gisExcelModel.getName());
						retObj.put("stake", gisExcelModel.getStake());
						retObj.put("type", gisExcelModel.getType());
					}
					if(GisExcellImportUtils.POLYLINE.equals(gisExcelModel.getType())){
						isLine = true ;
						JSONArray ary = new JSONArray();
						double[] point = gisExcelModel.getPoint();
						ary.add(point[1]);
						ary.add(point[0]);
						secondAry.add(ary);
					}else if(GisExcellImportUtils.POINT.equals(gisExcelModel.getType())){
						double[] point = gisExcelModel.getPoint();
						sourceObj.put("x", point[1]);
						sourceObj.put("y", point[0]);
						break;
					}
				}
				if(isLine){
					obj.put("type", "polyline");
					sourceObj.put("paths", firstAry);
				}else{
					obj.put("type", "point");
				}
				obj.put("flag", "import");
				obj.put("source", sourceObj.toJSONString());
				retObj.put("psource", obj);
				retAry.add(retObj);
			}
		}
		return retAry;
	}

	public static void main(String[] args) throws Exception {
		InputStream inp = new FileInputStream("C:\\Users\\J\\Desktop\\逐桩坐标表A4（改）.xls");
		JSONArray retAry = readExcel(inp,111d,GaussUtils.XA80);
		//System.out.println("kkkkk="+retAry.toJSONString());

	}
}
