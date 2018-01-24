package com.glaf.form.rule.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageOutputStream;

import com.sun.imageio.plugins.jpeg.*;

public class ImageCompressUtils {
	public static byte[] compressImageByFile(InputStream fis) {
		return ImgCompress(fis, 1024);
	}

	public static byte[] compressImage(byte[] ins) {
		InputStream input = new ByteArrayInputStream(ins);
		return compressImageByFile(input);
	}

	public static void main(String[] args) throws Exception {
		String url = "C:\\Users\\J\\Desktop\\长门特大桥效果图.jpg";
		File file = new File(url);
		InputStream fis = new FileInputStream(file);
		ImgCompress(fis, 2048);
	}

	public static byte[] ImgCompress(InputStream fis, int w) {
		try {
			BufferedImage bufferedImage = ImageIO.read(fis);
			int old_w = bufferedImage.getWidth(null); // 得到源图宽
			int old_h = bufferedImage.getHeight(null); // 得到源图高
			// 如果不是图片 或者 图片宽度小于1024 则不进行压缩
			if (bufferedImage.getWidth() == -1 || bufferedImage.getWidth() <= w) {
			    return null;
			}
			int new_w = w;
			int new_h = (old_h * w / old_w);

			BufferedImage image_to_save = new BufferedImage(new_w, new_h, bufferedImage.getType());
			image_to_save.getGraphics().drawImage(bufferedImage.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0, 0, null);

			return saveAsJPEG(image_to_save);
			// 关闭输出流
			// 返回压缩后的图片地址
		} catch (IOException ex) {

		}
		return null;
	}

	/**
	 * 以JPEG编码保存图片
	 * 
	 * @param dpi
	 *            分辨率
	 * @param image_to_save
	 *            要处理的图像图片
	 * @param JPEGcompression
	 *            压缩比
	 * @param fos
	 *            文件输出流
	 * @throws IOException
	 */
	public static byte[] saveAsJPEG(BufferedImage image_to_save) throws IOException {
		ByteArrayOutputStream fos = new ByteArrayOutputStream();
		JPEGImageWriter imageWriter = (JPEGImageWriter) ImageIO.getImageWritersBySuffix("jpg").next();
		ImageOutputStream ios = ImageIO.createImageOutputStream(fos);
		imageWriter.setOutput(ios);
		IIOMetadata imageMetaData = imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(image_to_save), null);
		imageWriter.write(imageMetaData, new IIOImage(image_to_save, null, null), null);
		byte[] bytes = new byte[] {};
		bytes = fos.toByteArray();
		ios.close();
		imageWriter.dispose();
		return bytes;

	}
}
