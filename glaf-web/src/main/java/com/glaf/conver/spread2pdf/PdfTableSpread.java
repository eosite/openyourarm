package com.glaf.conver.spread2pdf;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.conver.excel2pdf.model.Resource;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class PdfTableSpread {
	// sheet
	protected JSONObject sheet;

	protected boolean setting = false;
	
	private final String PATTERN = "/OADate\\((\\d*\\.?\\d*)\\)/";
	private final String NUMBER_PATTERN = "^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$";
	private final int DAY = 24 * 60 * 60 * 1000;

	public PdfTableSpread(JSONObject sheet) {
		this.sheet = sheet;
	}

	/**
	 * 获取转换过的Spread内容Table
	 * 
	 * @return PdfPTable
	 * @throws BadElementException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public PdfPTable getTable() throws BadElementException, MalformedURLException, IOException {
		return toParseContent(sheet);
	}

	/**
	 * 解析sheet
	 * 
	 * @param sheet
	 * @return
	 * @throws BadElementException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	protected PdfPTable toParseContent(JSONObject sheet) throws BadElementException, MalformedURLException, IOException {
		
		List<PdfPCell> pdfCells = new ArrayList<PdfPCell>();
		
		if(!sheet.containsKey("data")){
			return null;
		}
		
		JSONObject data = sheet.getJSONObject("data");
		if(!data.containsKey("dataTable")){
			return null;
		}
		JSONObject rows = data.getJSONObject("dataTable");
		// 列宽度
		JSONArray columnWidths = sheet.getJSONArray("columns");
		// 行高度
		JSONArray rowWidths = sheet.getJSONArray("rows");
		
		boolean isDefaultRows = false;
		float deFaultRowWidth = 20 ;
		float defaultColWidth = 60 ;
		
		// 合并单元格
		JSONArray megers = sheet.getJSONArray("spans");

		// 初始化二维数据用来判断是否合并
		int activeRow = sheet.getIntValue(/* "activeRow" */"rowCount");
		int activeCol = sheet.getIntValue(/* "activeCol" */"columnCount");
		boolean[][] used = new boolean[activeRow][activeCol];

		Map<String, JSONObject> megerMap = new HashMap<>();
		JSONObject meger;
		if (megers != null && !megers.isEmpty()) {
			for (Object obj : megers) {
				meger = (JSONObject) obj;
				int row = meger.getIntValue("row");
				int rowCount = meger.getIntValue("rowCount");
				int col = meger.getIntValue("col");
				int colCount = meger.getIntValue("colCount");
				for (int k = 0; k < rowCount; k++) {
					for (int j = 0; j < colCount; j++) {
						used[row + k][col + j] = true;
					}
				}
				megerMap.put(row + "-" + col, meger);
			}

		}
		//Set<String> keys = rows.keySet();

		// 最大行
		int maxRow = rows.keySet().stream().mapToInt(y -> Integer.parseInt(y)).summaryStatistics().getMax();
		// 最大列
		int maxCol = rows.keySet().stream().mapToInt(x -> {
			return rows.getJSONObject(x).keySet().stream().mapToInt(y -> Integer.parseInt(y)).summaryStatistics().getMax();
		}).summaryStatistics().getMax();

		if(rowWidths==null){
			isDefaultRows = true ;
		}
		
		String rowkey = null;
		String cellkey = null;
		JSONObject cells = null;
		JSONObject cell = null;
		PdfPCell pdfpCell = new PdfPCell();
		float rowHeight = deFaultRowWidth;
		
		for (int i = 0; i <= maxRow; i++) {
			rowkey = i + "";
			cells = rows.getJSONObject(rowkey);
			
			//float[] cws = new float[keys.size()];
			rowHeight = deFaultRowWidth;
			if(!isDefaultRows && i < rowWidths.size()){
				JSONObject rowWidthObj = rowWidths.getJSONObject(i);
				if(rowWidthObj != null){
					boolean isVisible = rowWidthObj.containsKey("visible") && !rowWidthObj.getBooleanValue("visible") ;
					rowHeight = isVisible ? 0 :(rowWidthObj.containsKey("size")?rowWidthObj.getFloatValue("size"):deFaultRowWidth);
				}
			}

			for (int j = 0; j <= maxCol; j++) {
				cellkey = j + "";
				// 如果是被合并的单元格则跳过
				if (!megerMap.containsKey(rowkey + "-" + cellkey) && used[i][j]) {
					continue;
				}
				int rowspan = 1;
				int colspan = 1;
				// 如果当前是合并单元格，则计算出占用几格
				if (megerMap.containsKey(rowkey + "-" + cellkey)) {
					meger = megerMap.get(rowkey + "-" + cellkey);
					colspan = meger.getIntValue("colCount");
					rowspan = meger.getIntValue("rowCount");
				}
				pdfpCell = new PdfPCell();
				pdfpCell.setColspan(colspan);
				pdfpCell.setRowspan(rowspan);
				pdfpCell.setBorder(Rectangle.NO_BORDER);
				pdfpCell.setFixedHeight(rowHeight/1.5f);
				if(rowHeight == 0){
					pdfpCell.setLeading(0, 0);
				}
				pdfpCell.setPhrase(new Phrase(" ",new Font(Resource.BASE_FONT_CHINESE, 8, Font.NORMAL)));
				if (cells == null) {
					pdfCells.add(pdfpCell);
					continue;
				}
				cell = cells.getJSONObject(cellkey);
				if (cell == null) {
					pdfCells.add(pdfpCell);
					continue;
				}

				// PDF单元格
				cellStyle(pdfpCell, cell);
				// addImageByPOICell(pdfpCell , cell , cw);

				pdfCells.add(pdfpCell);
			}

		}
		maxCol += 1;
		float[] widths = new float[maxCol];
		JSONObject columnWidth = null;
		boolean isCol = columnWidths!=null /*&& columnWidths.size()>=maxCol*/;
		for (int k = 0; k < maxCol; k++) {
			if(isCol && k<columnWidths.size()){
				columnWidth = columnWidths.getJSONObject(k);
				if(columnWidth !=null){
					boolean isVisible = columnWidth.containsKey("visible") && !columnWidth.getBooleanValue("visible") ;
					widths[k] = isVisible?0:(columnWidth.containsKey("size")?columnWidth.getFloat("size"):defaultColWidth);
				}else{
					widths[k] = defaultColWidth;
				}
			}else{
				widths[k] = defaultColWidth;
			}
		}
		PdfPTable table = new PdfPTable(widths);
		table.setWidthPercentage(100);
		for (PdfPCell pdfpCell1 : pdfCells) {
			table.addCell(pdfpCell1);
		}
		return table;
	}
	
	
	
	/**
	 * 返回颜色
	 * @param colorStr
	 * @return
	 */
	private BaseColor getBaseColor(String colorStr){
		int red =0,green=0,blue=0 ;
		colorStr = colorStr.replace("rgb(", "").replace(")", "").replace(" ", "");
		String[] colors = colorStr.split(",");
		//有出现 color为text 1 情况
		try{
			red= Integer.parseInt(colors[0].trim());
			green= Integer.parseInt(colors[1].trim());
			blue= Integer.parseInt(colors[2].trim());
		}catch(Exception e){
			red = 0;
			green = 0;
			blue = 0;
		}
		return new BaseColor(red, green, blue);
	}

	/**
	 * 设置边框
	 * @param pdfpCell
	 * @param cell
	 * @param style
	 * @param borderDirection
	 */
	private void setBorder(PdfPCell pdfpCell, JSONObject cell,JSONObject style,String borderDirection){
		if(style.containsKey(borderDirection/*"borderLeft"*/)){
			JSONObject borderObj = style.getJSONObject(borderDirection/*"borderLeft"*/);
			if(borderObj.containsKey("color")){
				BaseColor baseColor = getBaseColor(borderObj.getString("color"));
				switch (borderDirection) {
				case "borderRight":
					pdfpCell.setBorderColorRight(baseColor);
					break;
				case "borderTop":
					pdfpCell.setBorderColorTop(baseColor);
					break;
				case "borderBottom":
					pdfpCell.setBorderColorBottom(baseColor);
					break;
				default://borderLeft
					pdfpCell.setBorderColorLeft(baseColor);
					break;
				}
			}
			if(borderObj.containsKey("style")){
				Object borderStyleObject = borderObj.get("style");
				if(borderStyleObject instanceof JSONObject){
					
				}else{
					int borderStyle = borderObj.getIntValue("style"); 
					// 1为普通实线    5为粗实线  7  为虚线
					float borderWidthSize = 0.5f;
					switch (borderStyle) {
					case 1:
						borderWidthSize = 0.5f;
						break;
					case 5:
						borderWidthSize = 2.0f;
						break;
					case 7:
						//PdfPCell pcell, Rectangle position, PdfContentByte[] canvases
						pdfpCell.setCellEvent((pcell,position,canvases)->{
							PdfContentByte cb = canvases[PdfPTable.LINECANVAS];
							cb.saveState();
							// cb.setLineCap(PdfContentByte.LINE_CAP_ROUND);
							// cb.setLineDash(0, 1, 1);
							cb.setLineWidth(0.5f);
							cb.setLineDash(new float[] { 1f, 1f }, 0.5f);
							switch (borderDirection) {
							case "borderRight":
								cb.moveTo(position.getRight(), position.getTop());
								cb.lineTo(position.getRight(), position.getBottom());
								break;
							case "borderTop":
								cb.moveTo(position.getLeft(), position.getTop());
								cb.lineTo(position.getRight(), position.getTop());
								break;
							case "borderBottom":
								cb.moveTo(position.getLeft(), position.getBottom());
								cb.lineTo(position.getRight(), position.getBottom());
								break;
							default://borderLeft
								cb.moveTo(position.getLeft(), position.getTop());
								cb.lineTo(position.getLeft(), position.getBottom());
								break;
							}
							cb.stroke();
							cb.restoreState();
						});
						return;
					default:
						break;
					}
					
					switch (borderDirection) {
					case "borderRight":
						pdfpCell.setBorderWidthRight(borderWidthSize);
						break;
					case "borderTop":
						pdfpCell.setBorderWidthTop(borderWidthSize);
						break;
					case "borderBottom":
						pdfpCell.setBorderWidthBottom(borderWidthSize);
						break;
					default://borderLeft
						pdfpCell.setBorderWidthLeft(borderWidthSize);
						break;
					}
				}
				
			}
			
		}
	}
	
	/**
	 * 单元格样式设置
	 * @param pdfpCell
	 * @param cell
	 */
	private void cellStyle(PdfPCell pdfpCell, JSONObject cell) {
		BaseColor foreColor = null;
		if(cell.containsKey("style")){
			JSONObject style = cell.getJSONObject("style");
			//设置背景色 backColor
			if(style.containsKey("backColor")){
				pdfpCell.setBackgroundColor(getBaseColor(style.getString("backColor")));
			}
			//前景色foreColor
			if(style.containsKey("foreColor")){
				foreColor = getBaseColor(style.getString("foreColor"));
			}
			//设置对齐样式  垂直
			if(style.containsKey("vAlign")){
				//PdfPCell.ALIGN_LEFT 
				// 0 上 1中 2下
				int vAlign = style.getIntValue("vAlign");
				int verticalAlignment = PdfPCell.ALIGN_TOP;
				switch (vAlign) {
				case 1:
					verticalAlignment = PdfPCell.ALIGN_MIDDLE;
					break;
				case 2:
					verticalAlignment = PdfPCell.ALIGN_BOTTOM;
					break;
				default:
					break;
				}
				pdfpCell.setVerticalAlignment(verticalAlignment);
			}
			//水平
			if(style.containsKey("hAlign")){
				// 0 左 1中 2右
				int hAlign = style.getIntValue("hAlign");
				int horizontalAlignment = PdfPCell.ALIGN_LEFT;
				switch (hAlign) {
				case 1:
					horizontalAlignment = PdfPCell.ALIGN_CENTER;
					break;
				case 2:
					horizontalAlignment = PdfPCell.ALIGN_RIGHT;
					break;
				default:
					break;
				}
				pdfpCell.setHorizontalAlignment(horizontalAlignment);
			}
			//设置边框
			setBorder(pdfpCell, cell, style, "borderLeft");
			setBorder(pdfpCell, cell, style, "borderRight");
			setBorder(pdfpCell, cell, style, "borderTop");
			setBorder(pdfpCell, cell, style, "borderBottom");
			/*if(style.containsKey("borderLeft")){
				JSONObject borderLeft = style.getJSONObject("borderLeft");
				if(borderLeft.containsKey("color")){
					pdfpCell.setBorderColorLeft(getBaseColor(borderLeft.getString("color")));
				}
				if(borderLeft.containsKey("style")){
					pdfpCell.setBorderWidthLeft(borderLeft.getFloatValue("style")/2.5f);
				}
			}
			
			if(style.containsKey("borderRight")){
				JSONObject borderRight = style.getJSONObject("borderRight");
				if(borderRight.containsKey("color")){
					pdfpCell.setBorderColorRight(getBaseColor(borderRight.getString("color")));
				}
				if(borderRight.containsKey("style")){
					pdfpCell.setBorderWidthRight(borderRight.getFloatValue("style")/2.5f);
				}
			}
			
			if(style.containsKey("borderTop")){
				JSONObject borderTop = style.getJSONObject("borderTop");
				if(borderTop.containsKey("color")){
					pdfpCell.setBorderColorTop(getBaseColor(borderTop.getString("color")));
				}
				if(borderTop.containsKey("style")){
					pdfpCell.setBorderWidthTop(borderTop.getFloatValue("style")/2.5f);
				}
			}
			if(style.containsKey("borderBottom")){
				JSONObject borderBottom = style.getJSONObject("borderBottom");
				if(borderBottom.containsKey("color")){
					pdfpCell.setBorderColorBottom(getBaseColor(borderBottom.getString("color")));
				}
				if(borderBottom.containsKey("style")){
					pdfpCell.setBorderWidthBottom(borderBottom.getFloatValue("style")/2.5f);
				}
			}*/
			//设置字体
			Font font = new Font(Resource.BASE_FONT_CHINESE, getFontSize(16), Font.NORMAL);
			if(style.containsKey("font")){
				String fontStr = style.getString("font");
				String[] fontStyles = fontStr.split(" ");
				int fontStyle = Font.NORMAL; 
				float fontSize = 16 ; 
				if (fontStr.contains("bold")) {
					fontStyle = Font.BOLD;
				}
				if (fontStr.contains("italic")) {
					fontStyle = Font.ITALIC;
				}

				if (fontStyles.length == 2) {
					//font.setFontName(fontStyles[fontStyles.length - 1]);
					Double d = Double.parseDouble(fontStyles[fontStyles.length - 2].replace("px", ""));
					//font.setFontHeightInPoints((short) (d.shortValue() - 6.0));
					fontSize = d.shortValue();
				}

				if (fontStyles.length > 2) {
					fontStyle = Font.BOLD;
					
					//font.setFontName(fontStyles[fontStyles.length - 1]);
					Double d = Double.parseDouble(fontStyles[fontStyles.length - 2].replace("px", ""));
					fontSize = d.shortValue();
					//font.setFontHeightInPoints((short) (d.shortValue() - 6.0));
				}
				font = new Font(Resource.BASE_FONT_CHINESE, getFontSize(fontSize), fontStyle);
			}
			if(foreColor!=null){
				font.setColor(foreColor);
			}
			//添加值
			pdfpCell.setPhrase(new Phrase(cell.containsKey("value") ? getCellValue(cell) : " ",font));
		}
	}
	
	/**
	 * 计算字体大小
	 * @param fontValue
	 * @return
	 */
	private Float getFontSize(float fontValue){
		return fontValue/1.5f;
	}
	
	/**
	 * 获取cell值  主要转换日期
	 * @param cell
	 * @return
	 */
	private String getCellValue(JSONObject cell){
		String expression = cell.getString("value");
		String typeName = null;
		JSONObject styleObj = null;
		JSONObject cellTypeObj = null;
		if(cell.containsKey("style") && (styleObj = cell.getJSONObject("style"))!=null && styleObj.containsKey("cellType") 
				&& (cellTypeObj = styleObj.getJSONObject("cellType") ) != null && cellTypeObj.containsKey("typeName")){
			typeName = cellTypeObj.getString("typeName");
		}
		if(StringUtils.isNotEmpty(expression)){
			if (!StringUtils.isEmpty(expression) && Pattern.matches(PATTERN, expression)) {
				// expression.
				Pattern r = Pattern.compile(PATTERN);
				// 现在创建 matcher 对象
				Matcher m = r.matcher(expression);
				if (m.find()) {
					String dateStrValue = m.group(1);
					Double dateDouble = Double.parseDouble(dateStrValue);
					// 这里有一个坑 如果日期小于1
					// 最后解析出的值会是-1，现强制使用数值类型来计算
					//日期显示格式
					String formatter = "yyyy-MM-dd";;
					if(styleObj !=null && styleObj.containsKey("formatter")){
						formatter = styleObj.getString("formatter");
					}
					DateFormat dateFormat = new SimpleDateFormat(formatter);
					long oaDate =  dateDouble.longValue();
					long t = oaDate - 25569;
			        Date i = new Date(t * DAY);
			        long rr = t >= 0 ? 1 : -1;
			        Date date = new Date((oaDate * 124416000000l + rr - 3181192704000000l + i.getTimezoneOffset() * DAY) / 1440);
					return dateFormat.format(date);
				}
			}else{
				if("104".equals(typeName)){
					String formatter = "yyyy-MM-dd";;
					if(styleObj !=null && styleObj.containsKey("formatter")){
						formatter = styleObj.getString("formatter");
					}
					DateFormat dateFormat = new SimpleDateFormat(formatter);
					if(Pattern.matches(NUMBER_PATTERN, expression)){
						return dateFormat.format(new Date(Long.parseLong(expression)));
					}
					return dateFormat.format(new Date(expression));
				}
			}
			return expression;
		}else{
			return " ";
		}
	}
	
	public static void main(String[] args) {
		//  /OADate(43118)/  2018-01-18
		long oaDate =  43118;
		long t = oaDate - 25569;
        Date i = new Date(t * 86400000);
        long r = t >= 0 ? 1 : -1;
        Date date3 = new Date((oaDate * 124416000000l + r - 3181192704000000l + i.getTimezoneOffset() * 86400000) / 1440);
		
		System.out.println(date3);
		System.out.println(new Date(1515142387000l));
		
	}
}
