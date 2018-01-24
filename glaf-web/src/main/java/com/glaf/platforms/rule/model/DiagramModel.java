package com.glaf.platforms.rule.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.form.core.domain.FormDictory;
import com.glaf.form.core.util.FormDictoryFactory;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;

public class DiagramModel extends CommonModel implements IRule, CssRule, AttrRule {

	private static final long serialVersionUID = 1L;

	@Override
	public String getBind() {
		return null;
	}
	
	public String getWritingMode(){
		return this.getStringValue("writingMode", "lr-tb");
	}

	public String getChildrenColumn() {
		return this.getStringValue("childrenColumn", "items");
	}

	public String getLayoutType() {
		return this.getStringValue("layoutType", "tree");
	}

	public String getSubType() {
		return this.getStringValue("subType", "down");
	}

	public int getHorizontalSeparation() {
		return this.getIntValue("horizontalSeparation", 90);
	}

	public int getVerticalSeparation() {
		return this.getIntValue("verticalSeparation", 50);
	}

	public int getStartRadialAngle() {
		return this.getIntValue("startRadialAngle", 0);
	}

	public int getEndRadialAngle() {
		return this.getIntValue("endRadialAngle", 360);
	}

	public int getRadialSeparation() {
		return this.getIntValue("radialSeparation", 200);
	}

	public String getConnectionType() {
		return this.getStringValue("connectionType", "cascading");
	}

	public String getConnectionHoverColor() {
		return this.getStringValue("connectionHoverColor", null);
	}

	public String getConnectionColor() {
		return this.getStringValue("connectionColor", null);
	}

	public int getConnectionWidth() {
		return this.getIntValue("connectionWidth", 2);
	}

	public String getStartCap() {
		return this.getStringValue("startCap", "none");
	}

	public String getEndCap() {
		return this.getStringValue("endCap", "none");
	}

	public int getShapeWidth() {
		return this.getIntValue("shapeWidth", 100);
	}

	public int getShapeHeight() {
		return this.getIntValue("shapeHeight", 50);
	}

	public String getShapeType() {
		return this.getStringValue("shapeType", "rectangle");
	}

	public boolean getSelectable() {
		return this.getBooleanValue("selectable", true);
	}

	public String getShapeHoverColor() {
		return this.getStringValue("shapeHoverColor", null);
	}

	public int getShapeRotation() {
		return this.getIntValue("shapeRotation", 0);
	}

	public String getShapeColor() {
		return this.getStringValue("shapeColor", null);
	}

	public int getShapeContentFontSize() {
		return this.getIntValue("shapeContentFontSize", 16);
	}

	public String getShapeContentFontColor() {
		return this.getStringValue("shapeContentFontColor", null);
	}

	public String getShapgeContentTextName() {
		return this.getStringValue("shapgeContentTextName", "name");
	}

	public double getZoom() {
		return this.getDoubleValue("zoom", 1.0);
	}

	public double getZoomMax() {
		return this.getDoubleValue("zoomMax", 2.0);
	}

	public double getZoomMin() {
		return this.getDoubleValue("zoomMin", 0.1);
	}

	public boolean getConnectionDrag() {
		return this.getBooleanValue("connectionDrag", true);
	}

	public boolean getConnectionRemove() {
		return this.getBooleanValue("connectionRemove", true);
	}

	public boolean getEditable() {
		return this.getBooleanValue("editable", true);
	}

	public double getShapeOpacity() {
		return this.getDoubleValue("shapeOpacity", 1.0);
	}

	public boolean getIsGradient() {
		return this.getBooleanValue("isGradient", false);
	}

	public String getGradientType() {
		String code = this.getStringValue("gradientType", "linear");
		FormDictory dict = FormDictoryFactory.getInstance().getFormDictoryByCode(code);
		return dict == null ? "linear" : dict.getValue();
	}

	public String getGradientCenter() {
		return this.getStringValue("gradientCenter", "[0.5,0.5]");
	}

	public String getGradientStart() {
		return this.getStringValue("gradientStart", "[0,0]");
	}

	public String getGradientEnd() {
		return this.getStringValue("gradientEnd", "[1,1]");
	}

	public String getClickFunction() {
		String linkPage = source.get("linkPage"); // 联动页面
		JSONArray linkPages = JSON.parseArray(linkPage);
		JSONObject params = new JSONObject();
		if (linkPages != null && !linkPages.isEmpty()) {
			String linkstr = linkPages.getJSONObject(0).toJSONString();
			params.put("link", linkstr);
		}

		String jumpType = source.get("jumpType"); // 跳转类型
		params.put("jumpType", jumpType);
		String title = source.get("windowTitle"); // 窗口名称
		params.put("title", title);
		String model = source.get("windowModal"); // 是否为模态窗口
		params.put("model", model);
		String width = source.get("windowWidth"); // 窗口宽度
		params.put("width", width);
		String height = source.get("windowHeight");// 窗口高度
		params.put("height", height);

		String paraType = source.get("paraType"); // 规则
		JSONArray paraTypes = JSON.parseArray(paraType);
		String rules = null;
		if (paraTypes != null && !paraTypes.isEmpty()) {
			rules = paraTypes.getJSONObject(0).getJSONObject("datas").toJSONString();
		}

		String clickType = source.get("click");// 事件类型

		StringBuffer sb = new StringBuffer();
		sb.append("function(e){");
		sb.append("pubsub.pub('" + clickType + "'," + rules + "," + params.toJSONString() + ",e.item.dataItem)");
		sb.append("}");

		return sb.toString();
	}

	private String getStringValue(String key, String defaultValue) {
		return source.get(key) == null ? defaultValue : source.get(key);
	}

	private boolean getBooleanValue(String key, boolean defaultValue) {
		if (source.get(key) == null) {
			return defaultValue;
		} else {
			return Boolean.parseBoolean(source.get(key));
		}
	}

	private int getIntValue(String key, Integer defaultValue) {
		if (source.get(key) == null) {
			return defaultValue;
		} else {
			return Integer.parseInt(source.get(key));
		}
	}

	private double getDoubleValue(String key, Double defaultValue) {
		if (source.get(key) == null) {
			return defaultValue;
		} else {
			return Double.parseDouble(source.get(key));
		}
	}
}
