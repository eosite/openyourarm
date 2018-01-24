package com.glaf.cell;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

public class HtmlBuilder {
	/**
	 * 通过模板生成HTML元素
	 * 
	 * @param baseUri
	 * @param attributes
	 * @param resourceName
	 * @return
	 * @throws TemplateNotFoundException
	 * @throws MalformedTemplateNameException
	 * @throws ParseException
	 * @throws IOException
	 * @throws TemplateException
	 */
	public static Element createElementByTemplate(String baseUri,
			Map<String, String> attributes, String templateName,String resourceName)
			throws TemplateNotFoundException, MalformedTemplateNameException,
			ParseException, IOException, TemplateException {
		Element element = new Element(Tag.valueOf("div"), "");
		Configuration configuration = new Configuration(
				Configuration.VERSION_2_3_23);
		configuration.setDefaultEncoding("utf-8");
		configuration.setClassLoaderForTemplateLoading(HtmlBuilder.class.getClassLoader(),
				"/");
		Template temp = configuration.getTemplate(templateName+"/"+resourceName+".tmpl");
		StringWriter stringWriter = new StringWriter();
		temp.process(attributes, stringWriter);
		String resultHtml = stringWriter.toString();
		element.html(resultHtml);
		Element nelement=CssBuilder.builderCssByCellAttr(element.child(0),attributes);	
		return nelement;
	}

	/**
	 * 创建控件
	 * 
	 * @param baseUri
	 * @param attributes
	 * @param componentType
	 * @return
	 */
	public static Element createComponent(String baseUri,
			Map<String, String> attributes, String componentType,
			String templateFlag) {
		Element compElement = null;
		String formula=null;
		if (templateFlag != null && templateFlag.trim().length() > 0) {
			 String  title=attributes.get("title");
			 if(title!=null&&title.trim().length()>0)
			 {
				 title=title.replaceAll(" ", "&nbsp;");
				 title=title.replaceAll("	", "&nbsp;&nbsp;&nbsp;");
				 attributes.put("title",title);
			 }
			 else{
				 title=attributes.get("fieldName");
				 if(title!=null&&title.trim().length()>0)
				 {
					 attributes.put("title",title);
				 }
			 }
			try {
				if (componentType.equals("datefield")) {
					componentType = "datepicker";
					attributes.put("background", "transparent");
				} else if (componentType.equals("label")) {
					 formula=attributes.get("formula");
					 componentType = "label";
					 if(formula!=null&&formula.trim().length()>0){
						 componentType= "textbox";
						 attributes.put("background", "transparent");
					 }
					 
				} else if (componentType.equals("textfield")) {
					componentType = "textbox";
					attributes.put("background", "transparent");
				} else if (componentType.equals("double")) {
					componentType = "textbox";
					attributes.put("background", "transparent");
				} else if (componentType.equals("numberfield")) {
					componentType = "textbox";
					attributes.put("background", "transparent");
				} else {
					componentType = "textbox";
					attributes.put("background", "transparent");
				}
				compElement = createElementByTemplate(baseUri, attributes,
						templateFlag,componentType);
				compElement=CssBuilder.css(compElement,"border","none");
			} catch (TemplateNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedTemplateNameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TemplateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			switch (componentType) {
			case "label":
				compElement = LableBuilder.createLable(baseUri, attributes);
				break;
			case "textfield":
				compElement = InputBuilder.createInputText(baseUri, attributes);
				break;
			case "textarea":
				compElement = InputBuilder.createTextArea(baseUri, attributes);
				break;
			case "select":
				compElement = SelectBuilder.createSelect(baseUri, attributes);
				break;
			default:
				break;
			}
		}
		return compElement;
	}
}
