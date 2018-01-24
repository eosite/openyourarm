/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.report.test;

import java.io.*;
import java.util.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hssf.converter.*;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.w3c.dom.Document;

import com.glaf.core.util.FileUtils;
import com.glaf.core.util.StringTools;

public class ExcelToHtmlGen {

	public static void main(String[] args) {
		try {
			String filename = "./test.xls";
			String path = "./gen/";
			HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(filename));
			ExcelToHtmlConverter converter = new ExcelToHtmlConverter(
					DocumentBuilderFactory.newInstance().newDocumentBuilder()
							.newDocument());
			converter.setOutputColumnHeaders(false);
			converter.setOutputRowNumbers(false);
			converter.setUseDivsToSpan(false);
			converter.setOutputHiddenColumns(false);
			converter.setOutputHiddenRows(false);
			converter.setOutputLeadingSpacesAsNonBreaking(false);
			converter.processWorkbook(wb);
			String sheetName = wb.getSheetName(0);

			List<HSSFPictureData> pics = wb.getAllPictures();
			if (pics != null) {
				for (int i = 0; i < pics.size(); i++) {
					HSSFPictureData picData = pics.get(i);
					if (null == picData) {
						continue;
					}
					byte[] bytes = picData.getData();
					FileUtils.save(
							path + i + "." + picData.suggestFileExtension(),
							bytes);
				}
			}

			Document document = converter.getDocument();
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(outStream);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer serializer = tf.newTransformer();
			serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			serializer.setOutputProperty(OutputKeys.INDENT, "no");
			serializer.setOutputProperty(OutputKeys.METHOD, "html");
			serializer.transform(domSource, streamResult);
			outStream.close();
			byte[] bytes = outStream.toByteArray();
			String text = new String(bytes, "UTF-8");
			text = StringTools.replaceFirst(text, "<h2>" + sheetName + "</h2>",
					"");
			FileUtils.save("test.html", text.getBytes());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
