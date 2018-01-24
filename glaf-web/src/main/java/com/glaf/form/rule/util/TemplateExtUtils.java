package com.glaf.form.rule.util;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import com.glaf.template.util.StringTemplateLoader;
import com.glaf.template.util.TemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TemplateExtUtils extends TemplateUtils {
	private static final Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

	public static void process(Map<String, Object> context, String content,
			Writer writer) {
		if (content == null) {
			return;
		}
		try {
			cfg.setTemplateLoader(new StringTemplateLoader(content));
			cfg.setDefaultEncoding("UTF-8");
			cfg.setTagSyntax(2);
			cfg.setSharedVariable("mtpk", new PermissionFreemarkerFunction());
			cfg.setSharedVariable("mtpd", new PermissionFreemarkerDisabledFunction());
			Template template = cfg.getTemplate("");
			template.process(context, writer);
			writer.flush();
		} catch (TemplateException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	
}
