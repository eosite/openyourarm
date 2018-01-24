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

package com.glaf.datamgr.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.glaf.core.util.IOUtils;

public class ExcelUtils {

	private static void setImageToCell(HSSFWorkbook wb, String sheetName, short col1, int row1, short col2, int row2,
			byte[] imageBytes, String imageType) {
		BufferedImage bufferImg = null;
		ByteArrayOutputStream baos = null;
		ByteArrayInputStream bais = null;
		try {
			bais = new ByteArrayInputStream(imageBytes);
			bufferImg = ImageIO.read(bais);
			baos = new ByteArrayOutputStream();
			ImageIO.write(bufferImg, imageType, baos);

			HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, col1, row1, col2, row2);
			// anchor.setAnchorType(2);
			// anchor.setAnchorType(AnchorType.MOVE_AND_RESIZE);
			HSSFSheet sheet = wb.createSheet(sheetName);

			// 能否写多张图片，关键在与HSSFPatriarch patriarch 这个变量。写多张图片时，
			// HSSFPatriarch patriarch 应该时一个对象，不应该每次多新创建对象。
			HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
			if (StringUtils.equalsIgnoreCase(imageType, "jpg")) {
				patriarch.createPicture(anchor, wb.addPicture(baos.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
			} else if (StringUtils.equalsIgnoreCase(imageType, "png")) {
				patriarch.createPicture(anchor, wb.addPicture(baos.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			IOUtils.closeStream(bais);
			IOUtils.closeStream(baos);
		}
	}

	public static HSSFWorkbook writeImageToCell(byte[] xlsBytes, String sheetName, byte[] imageBytes,
			String imageType) {
		HSSFWorkbook wb = null;
		ByteArrayInputStream bais = null;
		try {
			bais = new ByteArrayInputStream(xlsBytes);
			wb = new HSSFWorkbook(bais);
			setImageToCell(wb, sheetName, (short) 0, 0, (short) 1, 1, imageBytes, imageType);
			return wb;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			if (wb != null) {
				try {
					wb.close();
				} catch (IOException e) {
				}
			}
		}
	}

}
