package com.glaf.maildata.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode; 
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.maildata.domain.*;


/**
 * 
 * JSON工厂类
 *
 */
public class EmailSendJsonFactory {

	public static EmailSend jsonToObject(JSONObject jsonObject) {
            EmailSend model = new EmailSend();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("msgId")) {
			model.setMsgId(jsonObject.getString("msgId"));
		}
		if (jsonObject.containsKey("inReplyTo")) {
			model.setInReplyTo(jsonObject.getString("inReplyTo"));
		}
		if (jsonObject.containsKey("from")) {
			model.setFrom(jsonObject.getString("from"));
		}
		if (jsonObject.containsKey("to")) {
			model.setTo(jsonObject.getString("to"));
		}
		if (jsonObject.containsKey("cc")) {
			model.setCc(jsonObject.getString("cc"));
		}
		if (jsonObject.containsKey("date")) {
			model.setDate(jsonObject.getDate("date"));
		}
		if (jsonObject.containsKey("subject")) {
			model.setSubject(jsonObject.getString("subject"));
		}
		if (jsonObject.containsKey("replyTo")) {
			model.setReplyTo(jsonObject.getString("replyTo"));
		}
		if (jsonObject.containsKey("text")) {
			model.setText(jsonObject.getString("text"));
		}
		if (jsonObject.containsKey("html")) {
			model.setHtml(jsonObject.getString("html"));
		}
		if (jsonObject.containsKey("intFlag")) {
			model.setIntFlag(jsonObject.getInteger("intFlag"));
		}
		if (jsonObject.containsKey("email")) {
			model.setEmail(jsonObject.getString("email"));
		}
		if (jsonObject.containsKey("fromSysId")) {
			model.setFromSysId(jsonObject.getString("fromSysId"));
		}
		if (jsonObject.containsKey("intAction")) {
			model.setIntAction(jsonObject.getInteger("intAction"));
		}
		if (jsonObject.containsKey("intOperat")) {
			model.setIntOperat(jsonObject.getInteger("intOperat"));
		}
		if (jsonObject.containsKey("listNo")) {
			model.setListNo(jsonObject.getInteger("listNo"));
		}
		if (jsonObject.containsKey("toSysId")) {
			model.setToSysId(jsonObject.getString("toSysId"));
		}

            return model;
	}

	public static JSONObject toJsonObject(EmailSend model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getMsgId() != null) {
			jsonObject.put("msgId", model.getMsgId());
		} 
		if (model.getInReplyTo() != null) {
			jsonObject.put("inReplyTo", model.getInReplyTo());
		} 
		if (model.getFrom() != null) {
			jsonObject.put("from", model.getFrom());
		} 
		if (model.getTo() != null) {
			jsonObject.put("to", model.getTo());
		} 
		if (model.getCc() != null) {
			jsonObject.put("cc", model.getCc());
		} 
                if (model.getDate() != null) {
                      jsonObject.put("date", DateUtils.getDate(model.getDate()));
		      jsonObject.put("date_date", DateUtils.getDate(model.getDate()));
		      jsonObject.put("date_datetime", DateUtils.getDateTime(model.getDate()));
                }
		if (model.getSubject() != null) {
			jsonObject.put("subject", model.getSubject());
		} 
		if (model.getReplyTo() != null) {
			jsonObject.put("replyTo", model.getReplyTo());
		} 
		if (model.getText() != null) {
			jsonObject.put("text", model.getText());
		} 
		if (model.getHtml() != null) {
			jsonObject.put("html", model.getHtml());
		} 
        jsonObject.put("intFlag", model.getIntFlag());
		if (model.getEmail() != null) {
			jsonObject.put("email", model.getEmail());
		} 
		if (model.getFromSysId() != null) {
			jsonObject.put("fromSysId", model.getFromSysId());
		} 
        jsonObject.put("intAction", model.getIntAction());
        jsonObject.put("intOperat", model.getIntOperat());
        jsonObject.put("listNo", model.getListNo());
		if (model.getToSysId() != null) {
			jsonObject.put("toSysId", model.getToSysId());
		} 
		if (model.getAtts()!=null) {
			jsonObject.put("atts",model.getAtts());
		}
		return jsonObject;
	}


	public static ObjectNode toObjectNode(EmailSend model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                if (model.getMsgId() != null) {
                     jsonObject.put("msgId", model.getMsgId());
                } 
                if (model.getInReplyTo() != null) {
                     jsonObject.put("inReplyTo", model.getInReplyTo());
                } 
                if (model.getFrom() != null) {
                     jsonObject.put("from", model.getFrom());
                } 
                if (model.getTo() != null) {
                     jsonObject.put("to", model.getTo());
                } 
                if (model.getCc() != null) {
                     jsonObject.put("cc", model.getCc());
                } 
                if (model.getDate() != null) {
                      jsonObject.put("date", DateUtils.getDate(model.getDate()));
		      jsonObject.put("date_date", DateUtils.getDate(model.getDate()));
		      jsonObject.put("date_datetime", DateUtils.getDateTime(model.getDate()));
                }
                if (model.getSubject() != null) {
                     jsonObject.put("subject", model.getSubject());
                } 
                if (model.getReplyTo() != null) {
                     jsonObject.put("replyTo", model.getReplyTo());
                } 
                if (model.getText() != null) {
                     jsonObject.put("text", model.getText());
                } 
                if (model.getHtml() != null) {
                     jsonObject.put("html", model.getHtml());
                } 
                jsonObject.put("intFlag", model.getIntFlag());
                if (model.getEmail() != null) {
                     jsonObject.put("email", model.getEmail());
                } 
                if (model.getFromSysId() != null) {
                     jsonObject.put("fromSysId", model.getFromSysId());
                } 
                jsonObject.put("intAction", model.getIntAction());
                jsonObject.put("intOperat", model.getIntOperat());
                jsonObject.put("listNo", model.getListNo());
                if (model.getToSysId() != null) {
                     jsonObject.put("toSysId", model.getToSysId());
                } 
                if (model.getAtts()!=null) {
        			jsonObject.put("atts",model.getAtts());
        		}
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<EmailSend> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (EmailSend model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<EmailSend> arrayToList(JSONArray array) {
		java.util.List<EmailSend> list = new java.util.ArrayList<EmailSend>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			EmailSend model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private EmailSendJsonFactory() {

	}

}
