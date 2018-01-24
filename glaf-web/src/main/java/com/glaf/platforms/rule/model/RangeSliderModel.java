/**   
*    
* 项目名称：glaf-web   
* 类名称：RangeSliderModel   
* 类描述：   
* 创建人：Administrator   
* 创建时间：2017年4月27日 上午10:36:04   
* 修改人：Administrator   
* 修改时间：2017年4月27日 上午10:36:04   
* 修改备注：   
* @version    
*    
*/
package com.glaf.platforms.rule.model;

/**
 * @author Administrator
 *
 */
public class RangeSliderModel extends CommonModel {

	/**
	 * 
	 */
	public String getTemplateScript() {
		StringBuffer rs = new StringBuffer();
		rs.append("<link href=\"${contextPath}/scripts/plugins/bootstrap/rangeSlider/css/ion.rangeSlider.css\" rel=\"stylesheet\" type=\"text/css\" />");
		rs.append("<link href=\"${contextPath}/scripts/plugins/bootstrap/rangeSlider/css/ion.rangeSlider.skinFlat.css\" rel=\"stylesheet\" type=\"text/css\" />");
		rs.append("<link href=\"${contextPath}/scripts/plugins/bootstrap/rangeSlider/css/normalize.css\" rel=\"stylesheet\" type=\"text/css\" />");
		rs.append("<script src=\"${contextPath}/scripts/plugins/bootstrap/rangeSlider/js/ion.rangeSlider.js\" type=\"text/javascript\"/>");
		rs.append("<script src=\"${contextPath}/scripts/plugins/bootstrap/rangeSlider/js/jquery.rangeSlider.extends.js\" type=\"text/javascript\"/>");
		scriptMap.put("rangeSlider", rs.toString());
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

	@Override
	public String getElementHtml() {

		return null;
	}
	
	/**
	 * 
	*    
	* 项目名称：glaf-web   
	* 类描述：   
	* 创建人：Administrator   
	* 创建时间：2017年4月27日 上午10:45:24   
	* 修改人：Administrator   
	* 修改时间：2017年4月27日 上午10:45:24   
	* 修改备注：   
	* @version    
	*
	 */
	public String getType(){
	  String type=source.get("type");
	  return type;
	}
	
	/**
	 * 
	* 获取最小值
	* 项目名称：glaf-web   
	* 类描述：   
	* 创建人：Administrator   
	* 创建时间：2017年4月27日 上午10:46:38   
	* 修改人：Administrator   
	* 修改时间：2017年4月27日 上午10:46:38   
	* 修改备注：   
	* @version    
	*
	 */
	public int getMin(){
		String string = source.get("min");
		int parseInt = Integer.parseInt(string);
		return parseInt;
	}
	
	/**
	 * 
	*  最大值  
	* 项目名称：glaf-web   
	* 类描述：   
	* 创建人：Administrator   
	* 创建时间：2017年4月27日 上午10:47:53   
	* 修改人：Administrator   
	* 修改时间：2017年4月27日 上午10:47:53   
	* 修改备注：   
	* @version    
	*
	 */
	public int getMax(){
		String string = source.get("max");
		int parseInt = Integer.parseInt(string);
		return parseInt;
	}
	
	public int getFrom(){
		String string = source.get("from");
		int parseInt = Integer.parseInt(string);
		return parseInt;
	}
	
	public int getTo(){
		String string = source.get("to");
		int parseInt = Integer.parseInt(string);
		return parseInt;
	}
	
	public int getStep(){
		String string = source.get("step");
		int parseInt = Integer.parseInt(string);
		return parseInt;
	}
	
	public String getGrid(){
		String string = source.get("Grid");
		return string;	
	}
	
	public String getHide_min_max(){
		String string = source.get("hide_min_max");
		return string;
	}
	
	public String getKeyBoard(){
		String string = source.get("keyboard");
		return string;
	}
	
	public String getPrefix(){
		String string = source.get("prefix");
		return string;
	}
	
	public String getPostfix(){
		String string = source.get("postfix");
		return string;
	}
}
