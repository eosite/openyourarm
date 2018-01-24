package com.glaf.sys.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode; 
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.sys.domain.*;


/**
 * 
 * JSON工厂类
 *
 */
public class ProjMuiprojlistJsonFactory {

	public static ProjMuiprojlist jsonToObject(JSONObject jsonObject) {
            ProjMuiprojlist model = new ProjMuiprojlist();
            if (jsonObject.containsKey("indexId")) {
		    model.setIndexId(jsonObject.getInteger("indexId"));
		}
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("intFlag")) {
			model.setIntFlag(jsonObject.getInteger("intFlag"));
		}
		if (jsonObject.containsKey("sysId")) {
			model.setSysId(jsonObject.getString("sysId"));
		}
		if (jsonObject.containsKey("projName")) {
			model.setProjName(jsonObject.getString("projName"));
		}
		if (jsonObject.containsKey("num")) {
			model.setNum(jsonObject.getString("num"));
		}
		if (jsonObject.containsKey("cTime")) {
			model.setCTime(jsonObject.getDate("cTime"));
		}
		if (jsonObject.containsKey("content")) {
			model.setContent(jsonObject.getString("content"));
		}
		if (jsonObject.containsKey("sDbName")) {
			model.setSDbName(jsonObject.getString("sDbName"));
		}
		if (jsonObject.containsKey("sServerName")) {
			model.setSServerName(jsonObject.getString("sServerName"));
		}
		if (jsonObject.containsKey("sUser")) {
			model.setSUser(jsonObject.getString("sUser"));
		}
		if (jsonObject.containsKey("spassword")) {
			model.setSpassword(jsonObject.getString("spassword"));
		}
		if (jsonObject.containsKey("listNo")) {
			model.setListNo(jsonObject.getInteger("listNo"));
		}
		if (jsonObject.containsKey("email")) {
			model.setEmail(jsonObject.getString("email"));
		}
		if (jsonObject.containsKey("iParentId")) {
			model.setIParentId(jsonObject.getInteger("iParentId"));
		}
		if (jsonObject.containsKey("nodeIco")) {
			model.setNodeIco(jsonObject.getInteger("nodeIco"));
		}
		if (jsonObject.containsKey("intLine")) {
			model.setIntLine(jsonObject.getInteger("intLine"));
		}
		if (jsonObject.containsKey("domainIndex")) {
			model.setDomainIndex(jsonObject.getInteger("domainIndex"));
		}
		if (jsonObject.containsKey("inLocal")) {
			model.setInLocal(jsonObject.getInteger("inLocal"));
		}
		if (jsonObject.containsKey("emailPsw")) {
			model.setEmailPsw(jsonObject.getString("emailPsw"));
		}
		if (jsonObject.containsKey("intConnected")) {
			model.setIntConnected(jsonObject.getInteger("intConnected"));
		}
		if (jsonObject.containsKey("emailsStr")) {
			model.setEmailsStr(jsonObject.getString("emailsStr"));
		}
		if (jsonObject.containsKey("intOrgLevel")) {
			model.setIntOrgLevel(jsonObject.getInteger("intOrgLevel"));
		}
		if (jsonObject.containsKey("intSendType")) {
			model.setIntSendType(jsonObject.getInteger("intSendType"));
		}
		if (jsonObject.containsKey("emailBaskUp")) {
			model.setEmailBaskUp(jsonObject.getString("emailBaskUp"));
		}
		if (jsonObject.containsKey("emailImplement")) {
			model.setEmailImplement(jsonObject.getString("emailImplement"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("updateDate")) {
			model.setUpdateDate(jsonObject.getDate("updateDate"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("updateBy")) {
			model.setUpdateBy(jsonObject.getString("updateBy"));
		}
		if (jsonObject.containsKey("smsUrl")) {
			model.setSmsUrl(jsonObject.getString("smsUrl"));
		}
		if (jsonObject.containsKey("toDbName")) {
			model.setToDbName(jsonObject.getString("toDbName"));
		}
		if (jsonObject.containsKey("toServerName")) {
			model.setToServerName(jsonObject.getString("toServerName"));
		}
		if (jsonObject.containsKey("toUser")) {
			model.setToUser(jsonObject.getString("toUser"));
		}
		if (jsonObject.containsKey("toPassword")) {
			model.setToPassword(jsonObject.getString("toPassword"));
		}

            return model;
	}

	public static JSONObject toJsonObject(ProjMuiprojlist model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("indexId", model.getIndexId());
		jsonObject.put("_indexId_", model.getIndexId());
		if (model.getId() != null) {
			jsonObject.put("id", model.getId());
                        jsonObject.put("_id_", model.getId());
			jsonObject.put("_oid_", model.getId());
		} 
        jsonObject.put("intFlag", model.getIntFlag());
		if (model.getSysId() != null) {
			jsonObject.put("sysId", model.getSysId());
		} 
		if (model.getProjName() != null) {
			jsonObject.put("projName", model.getProjName());
		} 
		if (model.getNum() != null) {
			jsonObject.put("num", model.getNum());
		} 
                if (model.getCTime() != null) {
                      jsonObject.put("cTime", DateUtils.getDate(model.getCTime()));
		      jsonObject.put("cTime_date", DateUtils.getDate(model.getCTime()));
		      jsonObject.put("cTime_datetime", DateUtils.getDateTime(model.getCTime()));
                }
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		} 
		if (model.getSDbName() != null) {
			jsonObject.put("sDbName", model.getSDbName());
		} 
		if (model.getSServerName() != null) {
			jsonObject.put("sServerName", model.getSServerName());
		} 
		if (model.getSUser() != null) {
			jsonObject.put("sUser", model.getSUser());
		} 
		if (model.getSpassword() != null) {
			jsonObject.put("spassword", model.getSpassword());
		} 
        jsonObject.put("listNo", model.getListNo());
		if (model.getEmail() != null) {
			jsonObject.put("email", model.getEmail());
		} 
        jsonObject.put("iParentId", model.getIParentId());
        jsonObject.put("nodeIco", model.getNodeIco());
        jsonObject.put("intLine", model.getIntLine());
        jsonObject.put("domainIndex", model.getDomainIndex());
        jsonObject.put("inLocal", model.getInLocal());
		if (model.getEmailPsw() != null) {
			jsonObject.put("emailPsw", model.getEmailPsw());
		} 
        jsonObject.put("intConnected", model.getIntConnected());
		if (model.getEmailsStr() != null) {
			jsonObject.put("emailsStr", model.getEmailsStr());
		} 
        jsonObject.put("intOrgLevel", model.getIntOrgLevel());
        jsonObject.put("intSendType", model.getIntSendType());
		if (model.getEmailBaskUp() != null) {
			jsonObject.put("emailBaskUp", model.getEmailBaskUp());
		} 
		if (model.getEmailImplement() != null) {
			jsonObject.put("emailImplement", model.getEmailImplement());
		} 
                if (model.getCreateDate() != null) {
                      jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
                }
                if (model.getUpdateDate() != null) {
                      jsonObject.put("updateDate", DateUtils.getDate(model.getUpdateDate()));
		      jsonObject.put("updateDate_date", DateUtils.getDate(model.getUpdateDate()));
		      jsonObject.put("updateDate_datetime", DateUtils.getDateTime(model.getUpdateDate()));
                }
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		} 
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		} 
		if (model.getSmsUrl() != null) {
			jsonObject.put("smsUrl", model.getSmsUrl());
		} 
		if (model.getToDbName() != null) {
			jsonObject.put("toDbName", model.getToDbName());
		} 
		if (model.getToServerName() != null) {
			jsonObject.put("toServerName", model.getToServerName());
		} 
		if (model.getToUser() != null) {
			jsonObject.put("toUser", model.getToUser());
		} 
		if (model.getToPassword() != null) {
			jsonObject.put("toPassword", model.getToPassword());
		} 
		return jsonObject;
	}


	public static ObjectNode toObjectNode(ProjMuiprojlist model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("indexId", model.getIndexId());
		jsonObject.put("_indexId_", model.getIndexId());
                if (model.getId() != null) {
                     jsonObject.put("id", model.getId());
                     jsonObject.put("_id_", model.getId());
		     jsonObject.put("_oid_", model.getId());
                } 
                jsonObject.put("intFlag", model.getIntFlag());
                if (model.getSysId() != null) {
                     jsonObject.put("sysId", model.getSysId());
                } 
                if (model.getProjName() != null) {
                     jsonObject.put("projName", model.getProjName());
                } 
                if (model.getNum() != null) {
                     jsonObject.put("num", model.getNum());
                } 
                if (model.getCTime() != null) {
                      jsonObject.put("cTime", DateUtils.getDate(model.getCTime()));
		      jsonObject.put("cTime_date", DateUtils.getDate(model.getCTime()));
		      jsonObject.put("cTime_datetime", DateUtils.getDateTime(model.getCTime()));
                }
                if (model.getContent() != null) {
                     jsonObject.put("content", model.getContent());
                } 
                if (model.getSDbName() != null) {
                     jsonObject.put("sDbName", model.getSDbName());
                } 
                if (model.getSServerName() != null) {
                     jsonObject.put("sServerName", model.getSServerName());
                } 
                if (model.getSUser() != null) {
                     jsonObject.put("sUser", model.getSUser());
                } 
                if (model.getSpassword() != null) {
                     jsonObject.put("spassword", model.getSpassword());
                } 
                jsonObject.put("listNo", model.getListNo());
                if (model.getEmail() != null) {
                     jsonObject.put("email", model.getEmail());
                } 
                jsonObject.put("iParentId", model.getIParentId());
                jsonObject.put("nodeIco", model.getNodeIco());
                jsonObject.put("intLine", model.getIntLine());
                jsonObject.put("domainIndex", model.getDomainIndex());
                jsonObject.put("inLocal", model.getInLocal());
                if (model.getEmailPsw() != null) {
                     jsonObject.put("emailPsw", model.getEmailPsw());
                } 
                jsonObject.put("intConnected", model.getIntConnected());
                if (model.getEmailsStr() != null) {
                     jsonObject.put("emailsStr", model.getEmailsStr());
                } 
                jsonObject.put("intOrgLevel", model.getIntOrgLevel());
                jsonObject.put("intSendType", model.getIntSendType());
                if (model.getEmailBaskUp() != null) {
                     jsonObject.put("emailBaskUp", model.getEmailBaskUp());
                } 
                if (model.getEmailImplement() != null) {
                     jsonObject.put("emailImplement", model.getEmailImplement());
                } 
                if (model.getCreateDate() != null) {
                      jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
                }
                if (model.getUpdateDate() != null) {
                      jsonObject.put("updateDate", DateUtils.getDate(model.getUpdateDate()));
		      jsonObject.put("updateDate_date", DateUtils.getDate(model.getUpdateDate()));
		      jsonObject.put("updateDate_datetime", DateUtils.getDateTime(model.getUpdateDate()));
                }
                if (model.getCreateBy() != null) {
                     jsonObject.put("createBy", model.getCreateBy());
                } 
                if (model.getUpdateBy() != null) {
                     jsonObject.put("updateBy", model.getUpdateBy());
                } 
                if (model.getSmsUrl() != null) {
                     jsonObject.put("smsUrl", model.getSmsUrl());
                } 
                if (model.getToDbName() != null) {
                     jsonObject.put("toDbName", model.getToDbName());
                } 
                if (model.getToServerName() != null) {
                     jsonObject.put("toServerName", model.getToServerName());
                } 
                if (model.getToUser() != null) {
                     jsonObject.put("toUser", model.getToUser());
                } 
                if (model.getToPassword() != null) {
                     jsonObject.put("toPassword", model.getToPassword());
                } 
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<ProjMuiprojlist> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (ProjMuiprojlist model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<ProjMuiprojlist> arrayToList(JSONArray array) {
		java.util.List<ProjMuiprojlist> list = new java.util.ArrayList<ProjMuiprojlist>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			ProjMuiprojlist model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private ProjMuiprojlistJsonFactory() {

	}

}
