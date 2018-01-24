package com.glaf.maildata.util;

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
public class EmailRecDomainFactory {

    public static final String TABLENAME = "EMAIL_REC";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    protected static TableModel tableModel;

    static{
        columnMap.put("iD", "ID");
        columnMap.put("email", "EMAIL");
        columnMap.put("msgId", "MSGID");
        columnMap.put("inReplyTo", "INREPLYTO");
        columnMap.put("from", "FROM");
        columnMap.put("to", "TO");
        columnMap.put("cc", "CC");
        columnMap.put("date", "DATE");
        columnMap.put("subject", "SUBJECT");
        columnMap.put("replyTo", "REPLYTO");
        columnMap.put("text", "TEXT");
        columnMap.put("html", "HTML");
        columnMap.put("intFlag", "INTFLAG");
        columnMap.put("guidFrom", "GUID_FROM");
        columnMap.put("fromSysId", "FROMSYSID");
        columnMap.put("intAction", "INTACTION");
        columnMap.put("intOperat", "INTOPERAT");
        columnMap.put("listNo", "LISTNO");
        columnMap.put("toSysId", "TOSYSID");

	javaTypeMap.put("iD", "String");
        javaTypeMap.put("email", "String");
        javaTypeMap.put("msgId", "String");
        javaTypeMap.put("inReplyTo", "String");
        javaTypeMap.put("from", "String");
        javaTypeMap.put("to", "String");
        javaTypeMap.put("cc", "String");
        javaTypeMap.put("date", "Date");
        javaTypeMap.put("subject", "String");
        javaTypeMap.put("replyTo", "String");
        javaTypeMap.put("text", "Clob");
        javaTypeMap.put("html", "Clob");
        javaTypeMap.put("intFlag", "Integer");
        javaTypeMap.put("guidFrom", "String");
        javaTypeMap.put("fromSysId", "String");
        javaTypeMap.put("intAction", "Integer");
        javaTypeMap.put("intOperat", "Integer");
        javaTypeMap.put("listNo", "Integer");
        javaTypeMap.put("toSysId", "String");
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
        tableDefinition.setName("EmailRec");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("iD");
        idColumn.setColumnName("ID");
        idColumn.setJavaType("String");
        idColumn.setLength(50);
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition email = new ColumnDefinition();
        email.setName("email");
        email.setColumnName("EMAIL");
        email.setJavaType("String");
        email.setLength(50);
        tableDefinition.addColumn(email);

        ColumnDefinition msgId = new ColumnDefinition();
        msgId.setName("msgId");
        msgId.setColumnName("MSGID");
        msgId.setJavaType("String");
        msgId.setLength(100);
        tableDefinition.addColumn(msgId);

        ColumnDefinition inReplyTo = new ColumnDefinition();
        inReplyTo.setName("inReplyTo");
        inReplyTo.setColumnName("INREPLYTO");
        inReplyTo.setJavaType("String");
        inReplyTo.setLength(100);
        tableDefinition.addColumn(inReplyTo);

        ColumnDefinition from = new ColumnDefinition();
        from.setName("from");
        from.setColumnName("FROM");
        from.setJavaType("String");
        from.setLength(100);
        tableDefinition.addColumn(from);

        ColumnDefinition to = new ColumnDefinition();
        to.setName("to");
        to.setColumnName("TO");
        to.setJavaType("String");
        to.setLength(100);
        tableDefinition.addColumn(to);

        ColumnDefinition cc = new ColumnDefinition();
        cc.setName("cc");
        cc.setColumnName("CC");
        cc.setJavaType("String");
        cc.setLength(100);
        tableDefinition.addColumn(cc);

        ColumnDefinition date = new ColumnDefinition();
        date.setName("date");
        date.setColumnName("DATE");
        date.setJavaType("Date");
        tableDefinition.addColumn(date);

        ColumnDefinition subject = new ColumnDefinition();
        subject.setName("subject");
        subject.setColumnName("SUBJECT");
        subject.setJavaType("String");
        subject.setLength(100);
        tableDefinition.addColumn(subject);

        ColumnDefinition replyTo = new ColumnDefinition();
        replyTo.setName("replyTo");
        replyTo.setColumnName("REPLYTO");
        replyTo.setJavaType("String");
        replyTo.setLength(100);
        tableDefinition.addColumn(replyTo);

        ColumnDefinition text = new ColumnDefinition();
        text.setName("text");
        text.setColumnName("TEXT");
        text.setJavaType("Clob");
        tableDefinition.addColumn(text);

        ColumnDefinition html = new ColumnDefinition();
        html.setName("html");
        html.setColumnName("HTML");
        html.setJavaType("Clob");
        tableDefinition.addColumn(html);

        ColumnDefinition intFlag = new ColumnDefinition();
        intFlag.setName("intFlag");
        intFlag.setColumnName("INTFLAG");
        intFlag.setJavaType("Integer");
        tableDefinition.addColumn(intFlag);

        ColumnDefinition guidFrom = new ColumnDefinition();
        guidFrom.setName("guidFrom");
        guidFrom.setColumnName("GUID_FROM");
        guidFrom.setJavaType("String");
        guidFrom.setLength(50);
        tableDefinition.addColumn(guidFrom);

        ColumnDefinition fromSysId = new ColumnDefinition();
        fromSysId.setName("fromSysId");
        fromSysId.setColumnName("FROMSYSID");
        fromSysId.setJavaType("String");
        fromSysId.setLength(50);
        tableDefinition.addColumn(fromSysId);

        ColumnDefinition intAction = new ColumnDefinition();
        intAction.setName("intAction");
        intAction.setColumnName("INTACTION");
        intAction.setJavaType("Integer");
        tableDefinition.addColumn(intAction);

        ColumnDefinition intOperat = new ColumnDefinition();
        intOperat.setName("intOperat");
        intOperat.setColumnName("INTOPERAT");
        intOperat.setJavaType("Integer");
        tableDefinition.addColumn(intOperat);

        ColumnDefinition listNo = new ColumnDefinition();
        listNo.setName("listNo");
        listNo.setColumnName("LISTNO");
        listNo.setJavaType("Integer");
        tableDefinition.addColumn(listNo);

        ColumnDefinition toSysId = new ColumnDefinition();
        toSysId.setName("toSysId");
        toSysId.setColumnName("TOSYSID");
        toSysId.setJavaType("String");
        toSysId.setLength(50);
        tableDefinition.addColumn(toSysId);

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
						.getResourceAsStream("com/glaf/maildata/domain/EmailRec.mapping.xml");
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

    private EmailRecDomainFactory() {

    }

}
