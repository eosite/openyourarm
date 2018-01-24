/**   
*    
* 项目名称：glaf-web   
* 类名称：FullPageModel   
* 类描述：   
* 创建人：Administrator   
* 创建时间：2017年5月3日 上午10:36:59   
* 修改人：Administrator   
* 修改时间：2017年5月3日 上午10:36:59   
* 修改备注：   
* @version    
*    
*/
package com.glaf.platforms.rule.model.bootstrap;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.platforms.rule.model.CommonModel;


/**
 * @author Administrator
 *
 */
public class FullPageModel extends CommonModel {

	private String navAndArrow;
	/**
	 * 
	 */
	public String getTemplateScript() {
		StringBuffer rs = new StringBuffer();
		rs.append("<link href=\"${contextPath}/scripts/plugins/bootstrap/fullpage/css/jquery.fullPage.css\" rel=\"stylesheet\" type=\"text/css\" />");
		rs.append("<script src=\"${contextPath}/scripts/plugins/bootstrap/fullpage/js/jquery.fullPage.js\" type=\"text/javascript\"/>");
		rs.append("<script src=\"${contextPath}/scripts/plugins/bootstrap/fullpage/js/juqery.fullpage.js\" type=\"text/javascript\"/>");
		scriptMap.put("fullpage", rs.toString());
		return this.templateScript;
	}

	/**
	 * 返回标签 自动生成 id role属性
	 */
	public String getElementTagName() {
		/**
		 * 使用场景 本身是属于一个容器 里面有很多控件的时候处理 获取到原来的DOM结构
		 */
		/**
		 * element2 == > do some
		 */
		return null;
	}
	public String getArrowAndNav(){
		navAndArrow=source.get("arrowAndNav");
		return navAndArrow;
	}
	
	@Override
	public String getElementHtml() {
		return null;
	}

	public String getNavigation() {
		String string = source.get("navigation"); 
		return string;
	}

	public String getSectionColor(){
		ArrayList<String> colors=new ArrayList<String>();
		String sectionColor = source.get("sectionColor");	
	     
		com.alibaba.fastjson.JSONArray ary = JSON.parseArray(sectionColor);
		if(null!=ary){
		JSONObject object = (JSONObject) ary.get(0);
			List<JSONObject> objs=(List<JSONObject>) object.get("colors"); 
			for(JSONObject o:objs){
				String a=(String) o.get("color");
				String s="\'"+a+"\'";
				colors.add(s); 
			}
		}
		String arrayString=colors.toString();
		return arrayString;
	}
	
	public String getNavigationPosition() {
		String string = source.get("navigationPosition");
		return string;
	}

	public String getSlidesNavigation() {
		String navAndArrow = source.get("arrowAndNav");
		if("slidesNavigation".equals(navAndArrow)){
			return "true";
		}else{
			return "false";
		}		
	}

	public String getSlidesNavPosition() {
		String string = source.get("slidesNavPosition");
		return string;
	}

	public String getVerticalCentered() {
		String string = source.get("verticalCentered");
		return string;
	}
	public String getScrollingSpeed() {
		 
	    String string = source.get("scrollingSpeed");
		return string;
	}

	public String getLoopHorizontal() {
		 String string = source.get("loopHorizontal");
		 if("pro".equals(string)){
			 return "true";
		 }else{
			 return "false";
		 }
	}

	public String getContinuousVertical() {
		String s = source.get("continuousVertical");
		if("yes".equals(s)){
			return "true";
		}else{
			return "false";
		}
	}


	public String getControlArrow() {
		String navAndArrow = source.get("arrowAndNav");
		if("controlArrow".equals(navAndArrow)){
			return "true";
		}else{
			return "false";
		}
	}
	
	public String getKeyboardScrolling() {
		return source.get("keyboardScrolling");
	}
	
	public String getVstart(){
		return source.get("Vstart");
	}
	public String getHstart(){
		return source.get("Hstart");
	}

	public String getVspeed(){
		return source.get("Vspeed");
	}
	public String getHspeed(){
		return source.get("Hspeed");
	}
	
	public String getControlArrowColor(){
		String s="#adf";
		s=source.get("controlArrowColor");
		return s;
	}
}
