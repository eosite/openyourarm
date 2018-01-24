package com.glaf.convert.engine;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ConvertWordEngine {
	/**
	 * 读取html文件到word
	 * 
	 * @param filepath
	 *            html文件的路径
	 * @return
	 * @throws Exception
	 */
	public boolean writeWordFile(String url) throws Exception {
		boolean flag = false;
		ByteArrayInputStream in = null;
		FileOutputStream fos = null;
		String path = "F:/"; // 根据实际情况写路径
		try {
			if (!"".equals(path)) {
				File fileDir = new File(path);
				if (fileDir.exists()) {
					// String content = readFile(filepath);
					// byte b[] = content.getBytes();
					// bais = new ByteArrayInputStream(b);
					String content = getHtmlContent(url);
					byte b[] = content.getBytes();
					in = new ByteArrayInputStream(b);
					POIFSFileSystem poifs = new POIFSFileSystem();
					DirectoryEntry directory = poifs.getRoot();
					DocumentEntry documentEntry = directory.createDocument("WordDocument", in);
					fos = new FileOutputStream(path + "temp.doc");
					poifs.writeFilesystem(fos);
					in.close();
					fos.close();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null)
				fos.close();
			if (in != null)
				in.close();
		}
		return flag;
	}

	/**
	 * 读取html文件到字符串
	 * 
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public String readFile(String filename) throws Exception {
		StringBuffer buffer = new StringBuffer("");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filename));
			buffer = new StringBuffer();
			while (br.ready())
				buffer.append((char) br.read());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null)
				br.close();
		}
		return buffer.toString();
	}

	// 根据地址抓取网页内容
	public String getHtmlContent(String url) throws UnsupportedEncodingException {
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(url);
		byte[] bytes = null;
		String charset=null;
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + getMethod.getStatusLine());
			} else {
				// 读取内容
				bytes = getMethod.getResponseBody();
				charset=getMethod.getResponseCharSet();
			}
			// 这里处理 inputStream
		} catch (Exception e) {
			System.err.println("页面无法访问");
		} finally {
			getMethod.releaseConnection();
		}
		return new String(bytes,"UTF-8");
	}

	public static void main(String[] args) throws Exception {

		new ConvertWordEngine().writeWordFile("http://www.sina.com.cn");
	}
}