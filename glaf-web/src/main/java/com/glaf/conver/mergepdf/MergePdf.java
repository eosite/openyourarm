package com.glaf.conver.mergepdf;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;

public class MergePdf {
	OutputStream os;
	public MergePdf(OutputStream os){
		this.os = os;
	}
	public void merge(List<InputStream> inputStreams) {
		PDDocument doc = new PDDocument();
		PDDocument inDoc;
		try {
			if(inputStreams!=null && !inputStreams.isEmpty()){
				for (InputStream inputStream : inputStreams) {
					inDoc = PDDocument.load(inputStream);
					inDoc.getPages().forEach((page) -> {
						doc.addPage(page);
					});
				}
			}
			doc.save(os);
			doc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
