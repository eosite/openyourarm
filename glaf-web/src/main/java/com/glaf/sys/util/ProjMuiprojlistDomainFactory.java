package com.glaf.sys.util;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;

import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.FilterDescriptor;
import com.glaf.core.base.TableModel;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.Resources;
import com.glaf.core.xml.XmlMappingReader;


/**
 * 
 * 实体数据工厂类
 *
 */
public class ProjMuiprojlistDomainFactory {

    public static final String TABLENAME = "PROJ_MUIPROJLIST";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    protected static TableModel tableModel;

    static{
        columnMap.put("indexId", "INDEX_ID");
        columnMap.put("id", "ID");
        columnMap.put("intFlag", "INTFLAG");
        columnMap.put("sysId", "SYS_ID");
        columnMap.put("projName", "PROJNAME");
        columnMap.put("num", "NUM");
        columnMap.put("cTime", "CTIME");
        columnMap.put("content", "CONTENT");
        columnMap.put("sDbName", "SDBNAME");
        columnMap.put("sServerName", "SSERVERNAME");
        columnMap.put("sUser", "SUSER");
        columnMap.put("spassword", "SPASSWORD");
        columnMap.put("listNo", "LISTNO");
        columnMap.put("email", "EMAIL");
        columnMap.put("iParentId", "PARENT_ID");
        columnMap.put("nodeIco", "NODEICO");
        columnMap.put("intLine", "INTLINE");
        columnMap.put("domainIndex", "DOMAIN_INDEX");
        columnMap.put("inLocal", "INTLOCAL");
        columnMap.put("emailPsw", "EMAIL_PSW");
        columnMap.put("intConnected", "INTCONNECTED");
        columnMap.put("emailsStr", "EMAIL_S");
        columnMap.put("intOrgLevel", "INTORGLEVEL");
        columnMap.put("intSendType", "INTSENDTYPE");
        columnMap.put("emailBaskUp", "EMAIL_BACKUP");
        columnMap.put("emailImplement", "EMAIL_IMPLEMENT");
        columnMap.put("createDate", "CREATEDATE");
        columnMap.put("updateDate", "UPDATEDATE");
        columnMap.put("createBy", "CREATEBY");
        columnMap.put("updateBy", "UPDATEBY");
        columnMap.put("smsUrl", "SMS_URL");
        columnMap.put("toDbName", "TODBNAME");
        columnMap.put("toServerName", "TOSERVERNAME");
        columnMap.put("toUser", "TOUSER");
        columnMap.put("toPassword", "TOPASSWORD");

	javaTypeMap.put("indexId", "Integer");
        javaTypeMap.put("id", "String");
        javaTypeMap.put("intFlag", "Integer");
        javaTypeMap.put("sysId", "String");
        javaTypeMap.put("projName", "String");
        javaTypeMap.put("num", "String");
        javaTypeMap.put("cTime", "Date");
        javaTypeMap.put("content", "String");
        javaTypeMap.put("sDbName", "String");
        javaTypeMap.put("sServerName", "String");
        javaTypeMap.put("sUser", "String");
        javaTypeMap.put("spassword", "String");
        javaTypeMap.put("listNo", "Integer");
        javaTypeMap.put("email", "String");
        javaTypeMap.put("iParentId", "Integer");
        javaTypeMap.put("nodeIco", "Integer");
        javaTypeMap.put("intLine", "Integer");
        javaTypeMap.put("domainIndex", "Integer");
        javaTypeMap.put("inLocal", "Integer");
        javaTypeMap.put("emailPsw", "String");
        javaTypeMap.put("intConnected", "Integer");
        javaTypeMap.put("emailsStr", "String");
        javaTypeMap.put("intOrgLevel", "Integer");
        javaTypeMap.put("intSendType", "Integer");
        javaTypeMap.put("emailBaskUp", "String");
        javaTypeMap.put("emailImplement", "String");
        javaTypeMap.put("createDate", "Date");
        javaTypeMap.put("updateDate", "Date");
        javaTypeMap.put("createBy", "String");
        javaTypeMap.put("updateBy", "String");
        javaTypeMap.put("smsUrl", "String");
        javaTypeMap.put("toDbName", "String");
        javaTypeMap.put("toServerName", "String");
        javaTypeMap.put("toUser", "String");
        javaTypeMap.put("toPassword", "String");
    }


    public static Map<String, String> getColumnMap() {
	return columnMap;
    }

    public static Map<String, String> getJavaTypeMap() {
	return javaTypeMap;
    }

    public static TableDefinition getTableDefinition(){
        return getTableDefinition(TABLENAME);
    }

    public static TableDefinition getTableDefinition(String tableName) {
        tableName = tableName.toUpperCase();
        TableDefinition tableDefinition = new TableDefinition();
        tableDefinition.setTableName(tableName);
        tableDefinition.setName("ProjMuiprojlist");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("indexId");
        idColumn.setColumnName("INDEX_ID");
        idColumn.setJavaType("Integer");
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition id = new ColumnDefinition();
        id.setName("id");
        id.setColumnName("ID");
        id.setJavaType("String");
        id.setLength(100);
        tableDefinition.addColumn(id);

        ColumnDefinition intFlag = new ColumnDefinition();
        intFlag.setName("intFlag");
        intFlag.setColumnName("INTFLAG");
        intFlag.setJavaType("Integer");
        tableDefinition.addColumn(intFlag);

        ColumnDefinition sysId = new ColumnDefinition();
        sysId.setName("sysId");
        sysId.setColumnName("SYS_ID");
        sysId.setJavaType("String");
        sysId.setLength(50);
        tableDefinition.addColumn(sysId);

        ColumnDefinition projName = new ColumnDefinition();
        projName.setName("projName");
        projName.setColumnName("PROJNAME");
        projName.setJavaType("String");
        projName.setLength(250);
        tableDefinition.addColumn(projName);

        ColumnDefinition num = new ColumnDefinition();
        num.setName("num");
        num.setColumnName("NUM");
        num.setJavaType("String");
        num.setLength(50);
        tableDefinition.addColumn(num);

        ColumnDefinition cTime = new ColumnDefinition();
        cTime.setName("cTime");
        cTime.setColumnName("CTIME");
        cTime.setJavaType("Date");
        tableDefinition.addColumn(cTime);

        ColumnDefinition content = new ColumnDefinition();
        content.setName("content");
        content.setColumnName("CONTENT");
        content.setJavaType("String");
        content.setLength(200);
        tableDefinition.addColumn(content);

        ColumnDefinition sDbName = new ColumnDefinition();
        sDbName.setName("sDbName");
        sDbName.setColumnName("SDBNAME");
        sDbName.setJavaType("String");
        sDbName.setLength(100);
        tableDefinition.addColumn(sDbName);

        ColumnDefinition sServerName = new ColumnDefinition();
        sServerName.setName("sServerName");
        sServerName.setColumnName("SSERVERNAME");
        sServerName.setJavaType("String");
        sServerName.setLength(150);
        tableDefinition.addColumn(sServerName);

        ColumnDefinition sUser = new ColumnDefinition();
        sUser.setName("sUser");
        sUser.setColumnName("SUSER");
        sUser.setJavaType("String");
        sUser.setLength(100);
        tableDefinition.addColumn(sUser);

        ColumnDefinition spassword = new ColumnDefinition();
        spassword.setName("spassword");
        spassword.setColumnName("SPASSWORD");
        spassword.setJavaType("String");
        spassword.setLength(50);
        tableDefinition.addColumn(spassword);

        ColumnDefinition listNo = new ColumnDefinition();
        listNo.setName("listNo");
        listNo.setColumnName("LISTNO");
        listNo.setJavaType("Integer");
        tableDefinition.addColumn(listNo);

        ColumnDefinition email = new ColumnDefinition();
        email.setName("email");
        email.setColumnName("EMAIL");
        email.setJavaType("String");
        email.setLength(100);
        tableDefinition.addColumn(email);

        ColumnDefinition iParentId = new ColumnDefinition();
        iParentId.setName("iParentId");
        iParentId.setColumnName("PARENT_ID");
        iParentId.setJavaType("Integer");
        tableDefinition.addColumn(iParentId);

        ColumnDefinition nodeIco = new ColumnDefinition();
        nodeIco.setName("nodeIco");
        nodeIco.setColumnName("NODEICO");
        nodeIco.setJavaType("Integer");
        tableDefinition.addColumn(nodeIco);

        ColumnDefinition intLine = new ColumnDefinition();
        intLine.setName("intLine");
        intLine.setColumnName("INTLINE");
        intLine.setJavaType("Integer");
        tableDefinition.addColumn(intLine);

        ColumnDefinition domainIndex = new ColumnDefinition();
        domainIndex.setName("domainIndex");
        domainIndex.setColumnName("DOMAIN_INDEX");
        domainIndex.setJavaType("Integer");
        tableDefinition.addColumn(domainIndex);

        ColumnDefinition inLocal = new ColumnDefinition();
        inLocal.setName("inLocal");
        inLocal.setColumnName("INTLOCAL");
        inLocal.setJavaType("Integer");
        tableDefinition.addColumn(inLocal);

        ColumnDefinition emailPsw = new ColumnDefinition();
        emailPsw.setName("emailPsw");
        emailPsw.setColumnName("EMAIL_PSW");
        emailPsw.setJavaType("String");
        emailPsw.setLength(50);
        tableDefinition.addColumn(emailPsw);

        ColumnDefinition intConnected = new ColumnDefinition();
        intConnected.setName("intConnected");
        intConnected.setColumnName("INTCONNECTED");
        intConnected.setJavaType("Integer");
        tableDefinition.addColumn(intConnected);

        ColumnDefinition emailsStr = new ColumnDefinition();
        emailsStr.setName("emailsStr");
        emailsStr.setColumnName("EMAIL_S");
        emailsStr.setJavaType("String");
        emailsStr.setLength(100);
        tableDefinition.addColumn(emailsStr);

        ColumnDefinition intOrgLevel = new ColumnDefinition();
        intOrgLevel.setName("intOrgLevel");
        intOrgLevel.setColumnName("INTORGLEVEL");
        intOrgLevel.setJavaType("Integer");
        tableDefinition.addColumn(intOrgLevel);

        ColumnDefinition intSendType = new ColumnDefinition();
        intSendType.setName("intSendType");
        intSendType.setColumnName("INTSENDTYPE");
        intSendType.setJavaType("Integer");
        tableDefinition.addColumn(intSendType);

        ColumnDefinition emailBaskUp = new ColumnDefinition();
        emailBaskUp.setName("emailBaskUp");
        emailBaskUp.setColumnName("EMAIL_BACKUP");
        emailBaskUp.setJavaType("String");
        emailBaskUp.setLength(50);
        tableDefinition.addColumn(emailBaskUp);

        ColumnDefinition emailImplement = new ColumnDefinition();
        emailImplement.setName("emailImplement");
        emailImplement.setColumnName("EMAIL_IMPLEMENT");
        emailImplement.setJavaType("String");
        emailImplement.setLength(50);
        tableDefinition.addColumn(emailImplement);

        ColumnDefinition createDate = new ColumnDefinition();
        createDate.setName("createDate");
        createDate.setColumnName("CREATEDATE");
        createDate.setJavaType("Date");
        tableDefinition.addColumn(createDate);

        ColumnDefinition updateDate = new ColumnDefinition();
        updateDate.setName("updateDate");
        updateDate.setColumnName("UPDATEDATE");
        updateDate.setJavaType("Date");
        tableDefinition.addColumn(updateDate);

        ColumnDefinition createBy = new ColumnDefinition();
        createBy.setName("createBy");
        createBy.setColumnName("CREATEBY");
        createBy.setJavaType("String");
        createBy.setLength(50);
        tableDefinition.addColumn(createBy);

        ColumnDefinition updateBy = new ColumnDefinition();
        updateBy.setName("updateBy");
        updateBy.setColumnName("UPDATEBY");
        updateBy.setJavaType("String");
        updateBy.setLength(50);
        tableDefinition.addColumn(updateBy);

        ColumnDefinition smsUrl = new ColumnDefinition();
        smsUrl.setName("smsUrl");
        smsUrl.setColumnName("SMS_URL");
        smsUrl.setJavaType("String");
        smsUrl.setLength(250);
        tableDefinition.addColumn(smsUrl);

        ColumnDefinition toDbName = new ColumnDefinition();
        toDbName.setName("toDbName");
        toDbName.setColumnName("TODBNAME");
        toDbName.setJavaType("String");
        toDbName.setLength(100);
        tableDefinition.addColumn(toDbName);

        ColumnDefinition toServerName = new ColumnDefinition();
        toServerName.setName("toServerName");
        toServerName.setColumnName("TOSERVERNAME");
        toServerName.setJavaType("String");
        toServerName.setLength(150);
        tableDefinition.addColumn(toServerName);

        ColumnDefinition toUser = new ColumnDefinition();
        toUser.setName("toUser");
        toUser.setColumnName("TOUSER");
        toUser.setJavaType("String");
        toUser.setLength(100);
        tableDefinition.addColumn(toUser);

        ColumnDefinition toPassword = new ColumnDefinition();
        toPassword.setName("toPassword");
        toPassword.setColumnName("TOPASSWORD");
        toPassword.setJavaType("String");
        toPassword.setLength(50);
        tableDefinition.addColumn(toPassword);

       return tableDefinition;
     }

	 
    public static TableDefinition createTable() {
        TableDefinition tableDefinition = getTableDefinition(TABLENAME);
        if (!DBUtils.tableExists(TABLENAME)) {
            DBUtils.createTable(tableDefinition);
        } else {
            DBUtils.alterTable(tableDefinition);
        }
        return tableDefinition;
    }


    public static TableDefinition createTable(String tableName) {
        TableDefinition tableDefinition = getTableDefinition(tableName);
        if (!DBUtils.tableExists(tableName)) {
            DBUtils.createTable(tableDefinition);
        } else {
            DBUtils.alterTable(tableDefinition);
        }
        return tableDefinition;
    }


    public static void processDataRequest(DataRequest dataRequest) {
	    if (dataRequest != null) {
		if (dataRequest.getFilter() != null) {
			if (dataRequest.getFilter().getField() != null) {
				dataRequest.getFilter().setColumn(
						columnMap.get(dataRequest.getFilter().getField()));
				dataRequest.getFilter().setJavaType(
						javaTypeMap.get(dataRequest.getFilter().getField()));
			}

			List<FilterDescriptor> filters = dataRequest.getFilter()
					.getFilters();
			for (FilterDescriptor filter : filters) {
				filter.setParent(dataRequest.getFilter());
				if (filter.getField() != null) {
					filter.setColumn(columnMap.get(filter.getField()));
					filter.setJavaType(javaTypeMap.get(filter.getField()));
				}

				List<FilterDescriptor> subFilters = filter.getFilters();
				for (FilterDescriptor f : subFilters) {
					f.setColumn(columnMap.get(f.getField()));
					f.setJavaType(javaTypeMap.get(f.getField()));
					f.setParent(filter);
				}
			}
		}
	    }
    }

    public static TableModel readTableModel() {
		if (tableModel == null) {
			XmlMappingReader reader = new XmlMappingReader();
			InputStream inputStream = null;
			try {
				inputStream = Resources
						.getResourceAsStream("com/glaf/sys/domain/ProjMuiprojlist.mapping.xml");
				tableModel = reader.read(inputStream);
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				IOUtils.closeQuietly(inputStream);
			}
		}
		TableModel bean = new TableModel();
		try {
			BeanUtils.copyProperties(bean, tableModel);
		} catch (Exception ex) {
			org.springframework.beans.BeanUtils
					.copyProperties(tableModel, bean);
		}
		return bean;
	}

    private ProjMuiprojlistDomainFactory() {

    }

}
