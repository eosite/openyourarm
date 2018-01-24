package com.glaf.platforms.rule;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 表达式转换工具类
 * @author magoo
 *
 */
public class HTMLExpressionConvertUtil {
		protected static final Log logger = LogFactory.getLog(HTMLExpressionConvertUtil.class);
		
		/**
		 HTML 转换 此方法只针对grid里面的template
		 * @param exptessionString
		 * @param type
		 * @param templateName
		 * @return
		 */
		public static String htmlConvert(String exptessionString,String templateName ){
//			templateName = " abc " ;
			
			//首先去除 无用的params参数
			String regex = "params=(\".+?\"|'.+?')" ; 
			
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(exptessionString);
			StringBuffer sb = new StringBuffer(); //替换后的字符串
//			String regexToString = "";
			while(matcher.find()) {   
//				regexToString = matcher.group() ;
				matcher.appendReplacement(sb, ""); 
			}  
			matcher.appendTail(sb);
			
			sb =  new StringBuffer(StringUtils.replace(sb.toString(), "\"", "\\\""));
			
			regex = "~F\\{(\\w*\\.)(\\w*\\.)(\\w*)\\}" ; 
			pattern = Pattern.compile(regex);
			matcher = pattern.matcher(sb.toString());
			StringBuffer sbr = new StringBuffer(); //替换后的字符串
			String regexToString = "" ;
			while(matcher.find()) {   
				regexToString = "\"+dataItem."+matcher.group(2).replace(".", "")+"_0_"+matcher.group(3)+"+\"";
				if(StringUtils.isNotEmpty(templateName)){
					regexToString = "\"+" + templateName + "(dataItem)+\"" ;
				}
				matcher.appendReplacement(sbr, java.util.regex.Matcher.quoteReplacement(regexToString)); 
			}  
			matcher.appendTail(sbr);  
			
			regex = "~F\\{(\\w*)\\}" ; 
			pattern = Pattern.compile(regex);
			matcher = pattern.matcher(sbr.toString());
			sbr = new StringBuffer(); //替换后的字符串
			regexToString = "" ;
			while(matcher.find()) {   
				regexToString = "\"+dataItem."+matcher.group(1)+"+\"";
				matcher.appendReplacement(sbr, java.util.regex.Matcher.quoteReplacement(regexToString)); 
			}  
			matcher.appendTail(sbr);  
			
			regex = "~V\\{(\\w*\\.)(\\w*\\.)(\\w*)\\}" ; 
			pattern = Pattern.compile(regex);
			matcher = pattern.matcher(sbr.toString());
			sbr = new StringBuffer(); //替换后的字符串
			regexToString = "" ;
			while(matcher.find()) {   
				regexToString = "\"+dataItem." +matcher.group(2).replace(".", "")+"_0_"+ matcher.group(3) + "+\"";
				matcher.appendReplacement(sbr, java.util.regex.Matcher.quoteReplacement(regexToString)); 
			}  
			matcher.appendTail(sbr);  
			
//			return getFunctionConvert(sb.toString(), templateName);
			return sbr.toString();
		}
		public static String htmlConvertForZtree(String exptessionString){
			return htmlConvertForZtree(exptessionString, "", "");
		}
		
		public static String htmlConvertForZtree(String exptessionString,String startTag,String endTag){
//			templateName = " abc " ;
			
			//首先去除 无用的params参数
			String regex = "params=(\".+?\"|'.+?')" ; 
			
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(exptessionString);
			StringBuffer sb = new StringBuffer(); //替换后的字符串
//			String regexToString = "";
			while(matcher.find()) {   
//				regexToString = matcher.group() ;
				matcher.appendReplacement(sb, ""); 
			}  
			matcher.appendTail(sb);
			
			sb =  new StringBuffer(StringUtils.replace(sb.toString(), "\"", "\\\""));
			
			regex = "~F\\{(\\w*\\.)(\\w*\\.)(\\w*)\\}" ; 
			pattern = Pattern.compile(regex);
			matcher = pattern.matcher(sb.toString());
			StringBuffer sbr = new StringBuffer(); //替换后的字符串
			String regexToString = "" ;
			String virtual = "" ;
			while(matcher.find()) {   
				virtual = matcher.group(2).replace(".", "");
				if(virtual.equalsIgnoreCase("row")){
					regexToString = startTag+"dataItem."+matcher.group(3) + endTag;
				}else{
					regexToString = startTag+"dataItem."+matcher.group(2).replace(".", "")+"_0_"+matcher.group(3) + endTag;
				}
				matcher.appendReplacement(sbr, java.util.regex.Matcher.quoteReplacement(regexToString)); 
			}  
			matcher.appendTail(sbr);  
			
			regex = "~F\\{(\\w*)\\}" ; 
			pattern = Pattern.compile(regex);
			matcher = pattern.matcher(sbr.toString());
			sbr = new StringBuffer(); //替换后的字符串
			regexToString = "" ;
			while(matcher.find()) {   
				regexToString = "\"+dataItem."+matcher.group(1)+"+\"";
				matcher.appendReplacement(sbr, java.util.regex.Matcher.quoteReplacement(regexToString)); 
			}  
			matcher.appendTail(sbr);  
			
			regex = "~V\\{(\\w*\\.)(\\w*\\.)(\\w*)\\}" ; 
			pattern = Pattern.compile(regex);
			matcher = pattern.matcher(sbr.toString());
			sbr = new StringBuffer(); //替换后的字符串
			regexToString = "" ;
			while(matcher.find()) {   
				regexToString = "\"+dataItem." +matcher.group(2).replace(".", "")+"_0_"+ matcher.group(3) + "+\"";
				matcher.appendReplacement(sbr, java.util.regex.Matcher.quoteReplacement(regexToString)); 
			}  
			matcher.appendTail(sbr);  
			
//			return getFunctionConvert(sb.toString(), templateName);
			return sbr.toString();
		}
		
		
		/**
		 * 自定义控件转换定义
		 * @param exptessionString
		 * @param templateName
		 * @return
		 */
		public static String imageConvert(String exptessionString){
//			templateName = " abc " ;
			
			//首先去除 无用的params参数
			String regex = "params=(\".+?\"|'.+?')" ; 
			
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(exptessionString);
			StringBuffer sb = new StringBuffer(); //替换后的字符串
			while(matcher.find()) {   
				matcher.appendReplacement(sb, ""); 
			}  
			matcher.appendTail(sb);
			
			sb =  new StringBuffer(StringUtils.replace(sb.toString(), "\"", "\\\""));
			
			regex = "~F\\{(\\w*\\.)(\\w*\\.)(\\w*)\\}" ; 
			pattern = Pattern.compile(regex);
			matcher = pattern.matcher(sb.toString());
			StringBuffer sbr = new StringBuffer(); //替换后的字符串
			String regexToString = "" ;
			while(matcher.find()) {   
				regexToString = "##"+matcher.group(3)+"##";
				matcher.appendReplacement(sbr, java.util.regex.Matcher.quoteReplacement(regexToString)); 
			}  
			matcher.appendTail(sbr);  
			
			return sbr.toString();
		}
		
		/**
		 * kendo模板 转换器
		 * @param exptessionString
		 * @param templateName
		 * @return
		 */
		public static String kendoTemplateConvert(String exptessionString,Map<String,String> map){
			return templateConvert(exptessionString, map, "#:", "#");
		}
		/**
		 * 模板 转换器
		 * @param exptessionString
		 * @param templateName
		 * @return
		 */
		public static String templateConvert(String exptessionString,Map<String,String> map,String startTag,String endTag){
//			templateName = " abc " ;
			
			//首先去除 无用的params参数
			String regex = "params=(\".+?\"|'.+?')" ; 
			
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(exptessionString);
			StringBuffer sb = new StringBuffer(); //替换后的字符串
			while(matcher.find()) {   
				matcher.appendReplacement(sb, ""); 
			}  
			matcher.appendTail(sb);
			
			sb =  new StringBuffer(StringUtils.replace(sb.toString(), "\"", "\\\""));
			
			regex = "~F\\{(\\w*\\.)(\\w*\\.)(\\w*)\\}" ; 
			pattern = Pattern.compile(regex);
			matcher = pattern.matcher(sb.toString());
			StringBuffer sbr = new StringBuffer(); //替换后的字符串
			String regexToString = "" ;
			String r = "" ;
			while(matcher.find()) {   
				r = map.get(matcher.group(0));
				regexToString = startTag+(r!=null ? r : matcher.group(3))+endTag;
				matcher.appendReplacement(sbr, java.util.regex.Matcher.quoteReplacement(regexToString)); 
			}  
			matcher.appendTail(sbr);  
			
			return sbr.toString();
		}
		
		public static void main(String[] args) {
			
//			String exptessionString = "<p><a title=\"hoho\" href=\"http://www.baidu.com?outparam=~V{default.cell_useradd7028.cell_useradd7028_user6}\" "
//					+ "target=\"_blank\" dialogwidth=\"900\" dialogheight=\"600\" params=\"[{cel}]\">~F{default.cell_useradd7028.cell_useradd7028_user6}</a>&nbsp;</p>" ;
//			String str = htmlConvert(exptessionString, null);
//			
//			//System.out.println(str);
			
			String exptessionString = "<p>我知道~F{col1442222550628.col1442222550628.col1442222550628}</p>" ;
			//System.out.println(imageConvert(exptessionString));
		}
		
			
}
