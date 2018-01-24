package com.glaf.base.test;

import java.io.*;

import org.apache.commons.lang3.StringUtils;

import com.glaf.base.modules.image.domain.Image;
import com.glaf.base.modules.image.service.ImageService;
import com.glaf.core.context.ContextFactory;

public class ImportImageTest {

	public static void main(String[] args) throws Exception {
		ImageService imageService = ContextFactory.getBean("imageService");
		String path = "E:\\iss_develop\\Java\\glaf-web\\web\\images";
		File dir = new File(path);
		File[] filelist = dir.listFiles();
		if (filelist != null) {
			for (File file : filelist) {
				if (StringUtils.endsWithIgnoreCase(file.getName(), "gif")
						|| StringUtils
								.endsWithIgnoreCase(file.getName(), "jpg")
						|| StringUtils
								.endsWithIgnoreCase(file.getName(), "png")
						|| StringUtils
								.endsWithIgnoreCase(file.getName(), "bmp")
						|| StringUtils.endsWithIgnoreCase(file.getName(),
								"jpeg")) {
					Image img = new Image();
					img.setCreateBy("system");
					img.setDeleteFlag(0);
					img.setImagePath("/images/" + file.getName());
					img.setLocked(0);
					img.setName(file.getName());
					img.setFilename(file.getName());
					img.setType("sys");
					imageService.save(img);
				}
			}
		}
	}
}
