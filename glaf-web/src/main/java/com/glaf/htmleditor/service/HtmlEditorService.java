package com.glaf.htmleditor.service;

import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.glaf.expression.core.util.SysConstant;


@Transactional(readOnly = true)
public interface HtmlEditorService {
     /**
      * 获取预览输出html
      * @param htmlTemplate
      * @param htmlParamJson
      * @param constant
      * @return
      */
	 public String getPreviewHtml(String htmlTemplate,JSONArray htmlParamJson,SysConstant constant);
}
