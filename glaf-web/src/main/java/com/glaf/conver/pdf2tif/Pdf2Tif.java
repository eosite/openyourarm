package com.glaf.conver.pdf2tif;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageEncoder;
import com.sun.media.jai.codec.TIFFEncodeParam;

/**
 * pdf 转 tif
 * @author J
 *
 */
public class Pdf2Tif {
	
	public static final String IMG_FORMAT = "tiff";
	public static final int DPI = 160; // 图片的像素

	/**
	 * 转换
	 * @param is  输入流
	 * @return 字节
	 */
	public static byte[] convert(InputStream is){
		return convert(is, DPI);
	}
	
	/***
	 * 转换
	 * @param is
	 * @param dpi 转换tif的图片像素，越大越清晰 默认160
	 * @return
	 */
	public static byte[] convert(InputStream is,int dpi){
		ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
		PDDocument doc = null;
		try {
			doc = PDDocument.load(is);
			int pageCount = doc.getNumberOfPages();
			PDFRenderer renderer = new PDFRenderer(doc); // 根据PDDocument对象创建pdf渲染器

			List<PlanarImage> piList = new ArrayList<PlanarImage>(pageCount - 1);
			for (int i = 0 + 1; i < pageCount; i++) {
				BufferedImage image = renderer.renderImageWithDPI(i, dpi, ImageType.RGB);
				PlanarImage pimg = JAI.create("mosaic", image);
				piList.add(pimg);
			}

			TIFFEncodeParam param = new TIFFEncodeParam();// 创建tiff编码参数类
			param.setCompression(TIFFEncodeParam.COMPRESSION_DEFLATE);// 压缩参数
			param.setExtraImages(piList.iterator());// 设置图片的迭代器

			BufferedImage fimg = renderer.renderImageWithDPI(0, dpi, ImageType.RGB);
			PlanarImage fpi = JAI.create("mosaic", fimg); // 通过JAI的create()方法实例化jai的图片对象
			
			
			ImageEncoder enc = ImageCodec.createImageEncoder(IMG_FORMAT, os, param);
			enc.encode(fpi);// 指定第一个进行编码的jai图片对象,并将输出写入到与此
			
			byte [] o = new byte[]{};
			
			o = os.toByteArray();
			os.flush();
			os.close();
			
			return o ;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (doc != null)
					doc.close();
				if (os != null)
					os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null; 
	}
	
	
	
}
