package com.glaf.monitor.server.util;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.FilterDescriptor;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.util.DBUtils;



/**
 * 
 * 实体数据工厂类
 *
 */
public class MonitorTerminalDomainFactory {

    public static final String TABLENAME = "MONITOR_TERMINAL";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("id", "ID_");
        columnMap.put("name", "NAME_");
        columnMap.put("type", "TYPE_");
        columnMap.put("desc", "DESC_");
        columnMap.put("level", "LEVEL_");
        columnMap.put("prod", "PROD_");
        columnMap.put("domain", "DOMAIN_");
        columnMap.put("address", "ADDRESS_");
        columnMap.put("monitorServiceAddress", "MONITOR_SERVICE_ADDRESS_");
        columnMap.put("status", "STATUS_");
        columnMap.put("platform", "PLATFORM_");
        columnMap.put("osName", "OS_NAME_");
        columnMap.put("osFac", "OS_FAC_");
        columnMap.put("osVer", "OS_VER_");
        columnMap.put("cpuFac", "CPU_FAC_");
        columnMap.put("cpuCores", "CPU_CORES_");
        columnMap.put("coreMhz", "CORE_MHZ_");
        columnMap.put("memType", "MEM_TYPE_");
        columnMap.put("memSize", "MEM_SIZE_");
        columnMap.put("diskType", "DISK_TYPE_");
        columnMap.put("diskSize", "DISK_SIZE_");
        columnMap.put("otherItems", "OTHER_ITEMS_");
        columnMap.put("createby", "CREATEBY_");
        columnMap.put("createtime", "CREATETIME_");
        columnMap.put("updateby", "UPDATEBY_");
        columnMap.put("updatetime", "UPDATETIME_");
        columnMap.put("deleteFlag", "DELETE_FLAG_");

	javaTypeMap.put("id", "String");
        javaTypeMap.put("name", "String");
        javaTypeMap.put("type", "String");
        javaTypeMap.put("desc", "String");
        javaTypeMap.put("level", "String");
        javaTypeMap.put("prod", "String");
        javaTypeMap.put("domain", "String");
        javaTypeMap.put("address", "String");
        javaTypeMap.put("monitorServiceAddress", "String");
        javaTypeMap.put("status", "Integer");
        javaTypeMap.put("platform", "String");
        javaTypeMap.put("osName", "String");
        javaTypeMap.put("osFac", "String");
        javaTypeMap.put("osVer", "String");
        javaTypeMap.put("cpuFac", "String");
        javaTypeMap.put("cpuCores", "Integer");
        javaTypeMap.put("coreMhz", "Integer");
        javaTypeMap.put("memType", "String");
        javaTypeMap.put("memSize", "Integer");
        javaTypeMap.put("diskType", "String");
        javaTypeMap.put("diskSize", "Integer");
        javaTypeMap.put("otherItems", "String");
        javaTypeMap.put("createby", "String");
        javaTypeMap.put("createtime", "Date");
        javaTypeMap.put("updateby", "String");
        javaTypeMap.put("updatetime", "Date");
        javaTypeMap.put("deleteFlag", "Integer");
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
        tableDefinition.setName("MonitorTerminal");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id");
        idColumn.setColumnName("ID_");
        idColumn.setJavaType("String");
        idColumn.setLength(50);
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition name = new ColumnDefinition();
        name.setName("name");
        name.setColumnName("NAME_");
        name.setJavaType("String");
        name.setLength(100);
        tableDefinition.addColumn(name);

        ColumnDefinition type = new ColumnDefinition();
        type.setName("type");
        type.setColumnName("TYPE_");
        type.setJavaType("String");
        type.setLength(30);
        tableDefinition.addColumn(type);

        ColumnDefinition desc = new ColumnDefinition();
        desc.setName("desc");
        desc.setColumnName("DESC_");
        desc.setJavaType("String");
        desc.setLength(150);
        tableDefinition.addColumn(desc);

        ColumnDefinition level = new ColumnDefinition();
        level.setName("level");
        level.setColumnName("LEVEL_");
        level.setJavaType("String");
        level.setLength(10);
        tableDefinition.addColumn(level);

        ColumnDefinition prod = new ColumnDefinition();
        prod.setName("prod");
        prod.setColumnName("PROD_");
        prod.setJavaType("String");
        prod.setLength(50);
        tableDefinition.addColumn(prod);

        ColumnDefinition domain = new ColumnDefinition();
        domain.setName("domain");
        domain.setColumnName("DOMAIN_");
        domain.setJavaType("String");
        domain.setLength(50);
        tableDefinition.addColumn(domain);

        ColumnDefinition address = new ColumnDefinition();
        address.setName("address");
        address.setColumnName("ADDRESS_");
        address.setJavaType("String");
        address.setLength(50);
        tableDefinition.addColumn(address);

        ColumnDefinition monitorServiceAddress = new ColumnDefinition();
        monitorServiceAddress.setName("monitorServiceAddress");
        monitorServiceAddress.setColumnName("MONITOR_SERVICE_ADDRESS_");
        monitorServiceAddress.setJavaType("String");
        monitorServiceAddress.setLength(150);
        tableDefinition.addColumn(monitorServiceAddress);

        ColumnDefinition status = new ColumnDefinition();
        status.setName("status");
        status.setColumnName("STATUS_");
        status.setJavaType("Integer");
        tableDefinition.addColumn(status);

        ColumnDefinition platform = new ColumnDefinition();
        platform.setName("platform");
        platform.setColumnName("PLATFORM_");
        platform.setJavaType("String");
        platform.setLength(30);
        tableDefinition.addColumn(platform);

        ColumnDefinition osName = new ColumnDefinition();
        osName.setName("osName");
        osName.setColumnName("OS_NAME_");
        osName.setJavaType("String");
        osName.setLength(50);
        tableDefinition.addColumn(osName);

        ColumnDefinition osFac = new ColumnDefinition();
        osFac.setName("osFac");
        osFac.setColumnName("OS_FAC_");
        osFac.setJavaType("String");
        osFac.setLength(30);
        tableDefinition.addColumn(osFac);

        ColumnDefinition osVer = new ColumnDefinition();
        osVer.setName("osVer");
        osVer.setColumnName("OS_VER_");
        osVer.setJavaType("String");
        osVer.setLength(20);
        tableDefinition.addColumn(osVer);

        ColumnDefinition cpuFac = new ColumnDefinition();
        cpuFac.setName("cpuFac");
        cpuFac.setColumnName("CPU_FAC_");
        cpuFac.setJavaType("String");
        cpuFac.setLength(30);
        tableDefinition.addColumn(cpuFac);

        ColumnDefinition cpuCores = new ColumnDefinition();
        cpuCores.setName("cpuCores");
        cpuCores.setColumnName("CPU_CORES_");
        cpuCores.setJavaType("Integer");
        tableDefinition.addColumn(cpuCores);

        ColumnDefinition coreMhz = new ColumnDefinition();
        coreMhz.setName("coreMhz");
        coreMhz.setColumnName("CORE_MHZ_");
        coreMhz.setJavaType("Integer");
        tableDefinition.addColumn(coreMhz);

        ColumnDefinition memType = new ColumnDefinition();
        memType.setName("memType");
        memType.setColumnName("MEM_TYPE_");
        memType.setJavaType("String");
        memType.setLength(20);
        tableDefinition.addColumn(memType);

        ColumnDefinition memSize = new ColumnDefinition();
        memSize.setName("memSize");
        memSize.setColumnName("MEM_SIZE_");
        memSize.setJavaType("Integer");
        tableDefinition.addColumn(memSize);

        ColumnDefinition diskType = new ColumnDefinition();
        diskType.setName("diskType");
        diskType.setColumnName("DISK_TYPE_");
        diskType.setJavaType("String");
        diskType.setLength(20);
        tableDefinition.addColumn(diskType);

        ColumnDefinition diskSize = new ColumnDefinition();
        diskSize.setName("diskSize");
        diskSize.setColumnName("DISK_SIZE_");
        diskSize.setJavaType("Integer");
        tableDefinition.addColumn(diskSize);

        ColumnDefinition otherItems = new ColumnDefinition();
        otherItems.setName("otherItems");
        otherItems.setColumnName("OTHER_ITEMS_");
        otherItems.setJavaType("String");
        otherItems.setLength(0);
        tableDefinition.addColumn(otherItems);

        ColumnDefinition createby = new ColumnDefinition();
        createby.setName("createby");
        createby.setColumnName("CREATEBY_");
        createby.setJavaType("String");
        createby.setLength(30);
        tableDefinition.addColumn(createby);

        ColumnDefinition createtime = new ColumnDefinition();
        createtime.setName("createtime");
        createtime.setColumnName("CREATETIME_");
        createtime.setJavaType("Date");
        tableDefinition.addColumn(createtime);

        ColumnDefinition updateby = new ColumnDefinition();
        updateby.setName("updateby");
        updateby.setColumnName("UPDATEBY_");
        updateby.setJavaType("String");
        updateby.setLength(30);
        tableDefinition.addColumn(updateby);

        ColumnDefinition updatetime = new ColumnDefinition();
        updatetime.setName("updatetime");
        updatetime.setColumnName("UPDATETIME_");
        updatetime.setJavaType("Date");
        tableDefinition.addColumn(updatetime);

        ColumnDefinition deleteFlag = new ColumnDefinition();
        deleteFlag.setName("deleteFlag");
        deleteFlag.setColumnName("DELETE_FLAG_");
        deleteFlag.setJavaType("Integer");
        tableDefinition.addColumn(deleteFlag);

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

    private MonitorTerminalDomainFactory() {

    }

}
