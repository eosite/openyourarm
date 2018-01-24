package com.glaf.form.rule.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
@SuppressWarnings("unchecked")
public class PermissionFreemarkerDisabledFunction implements TemplateMethodModelEx   {

	@Override
	public String exec(List args) throws TemplateModelException {
		if(args.size()!=2){
			return "";
		}
		String lis = args.get(0).toString();
		String cont = args.get(1).toString();
		String[] lisAry = lis.split(",");
		String[] contAry = cont.split(",");
		List<String> contlist = new ArrayList<String>();
		Collections.addAll(contlist, contAry);
		for (String string : lisAry) {
			if(contlist.contains(string)){
				return "disabled";
			}
		}
		return "";
	}

}