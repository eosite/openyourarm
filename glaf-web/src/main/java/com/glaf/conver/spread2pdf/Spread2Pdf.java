package com.glaf.conver.spread2pdf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.Set;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.conver.excel2pdf.model.PdfTool;
import com.glaf.conver.excel2pdf.model.Resource;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

public class Spread2Pdf extends PdfTool {
	JSONObject spreadJson;
	//使用页码
	Boolean usePageSize = false;
	
	public Spread2Pdf(JSONObject spreadJson , OutputStream os) {
		this(spreadJson,os,false);
    }
	
	public Spread2Pdf(JSONObject spreadJson , OutputStream os,Boolean usePageSize) {
		this.spreadJson = spreadJson;
        this.os = os;
        this.usePageSize = usePageSize;
    }
	
	public void convert() throws DocumentException, MalformedURLException, IOException {
        getDocument().setPageSize(PageSize.A4);
        getDocument().setMargins(5, 5, 5, 5);
        PdfWriter writer = PdfWriter.getInstance(getDocument(), os);
        writer.setPageEvent(new PDFPageEvent(usePageSize));
        //Open document
        getDocument().open();
        if(!spreadJson.isEmpty()){
        	JSONObject sheets = spreadJson.getJSONObject("sheets");
        	JSONObject sheet = null ;
        	
        	Set<String> keySet = sheets.keySet();
        	for (String key : keySet) {
        		sheet = (JSONObject) sheets.getJSONObject(key) ;
        		PdfPTable table = this.toCreatePdfTable(sheet ,  getDocument() , writer);
        		if(table!=null){
        			getDocument().add(table);
        		}
			}
        }
        getDocument().close();
    }
	
	  protected PdfPTable toCreatePdfTable(JSONObject object , Document document , PdfWriter writer) throws MalformedURLException, IOException, DocumentException{
	       	PdfPTable table = new PdfTableSpread(object).getTable();
	       	if(table!=null){
	       		table.setKeepTogether(true);
	       		table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
	       	}
	        return table;
	    }
	  
	 /**
     * <p>ClassName: PDFPageEvent</p>
     * <p>Description: 事件 -> 页码控制</p>
     * <p>Author: Cary</p>
     * <p>Date: Oct 25, 2013</p>
     */
    private static class PDFPageEvent extends PdfPageEventHelper{
        protected PdfTemplate template;
        public BaseFont baseFont;
        Boolean usePageSize ;
        public PDFPageEvent(Boolean usePageSize){
        	this.usePageSize = usePageSize;
        }
        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            try{
                this.template = writer.getDirectContent().createTemplate(100, 100);
                this.baseFont = new Font(Resource.BASE_FONT_CHINESE , 8, Font.NORMAL).getBaseFont();
            } catch(Exception e) {
                throw new ExceptionConverter(e);
            }
        }
        
        @Override
        public void onEndPage(PdfWriter writer, Document document) {
        	if(usePageSize){
        		//在每页结束的时候把“第x页”信息写道模版指定位置
                PdfContentByte byteContent = writer.getDirectContent();
                String text = "第" + writer.getPageNumber() + "页";
                float textWidth = this.baseFont.getWidthPoint(text, 8);
                float realWidth = document.right() - textWidth;
                //
                byteContent.beginText();
                byteContent.setFontAndSize(this.baseFont , 10);
                byteContent.setTextMatrix(realWidth , document.bottom());
                byteContent.showText(text);
                byteContent.endText();
                byteContent.addTemplate(this.template , realWidth , document.bottom());
        	}
        }
    }  
    
    public static void main(String[] args) throws Exception {
    	BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("d://b.txt"))));
    	OutputStream os = new FileOutputStream(new File("d://b.pdf"));
		try {
			StringBuffer sb = new StringBuffer();
			
			String data = null;
	        while ((data = br.readLine())!=null) {    
	        	sb.append(data);
	        }   
			//System.out.println(sb.toString());
			JSONObject obj = JSONObject.parseObject(sb.toString());
			
			Spread2Pdf sp = new Spread2Pdf(obj,os,true);
			//System.out.println("-->开始转换");
			sp.convert();
			//System.out.println("-->开始结束");
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(br!=null){
				br.close();
			}
			if(os!=null){
				os.close();
			}
		}
		
		
	}
}
