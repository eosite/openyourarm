package com.glaf.form.core.helper;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.glaf.core.base.DataRequest.FilterDescriptor;
import com.glaf.form.rule.OperatorEnum;

public class SqlHelper {

	public void getWhereSql(StringBuilder whereSql,
			FilterDescriptor filterDescriptor, int count, boolean flag) {
		List<FilterDescriptor> filterDescriptors = filterDescriptor
				.getFilters();
		if (filterDescriptors != null && filterDescriptors.size() > 0) {
			if (count == 1 && !flag) {
				whereSql.append(" and ");
			}
			int t = 1;
			for (FilterDescriptor fd : filterDescriptors) {
				if (fd.getFilters() != null && fd.getFilters().size() > 0) {
					if (count > 1) {
						whereSql.append(" and ( ");
					} else if (count == 1) {
						whereSql.append(" ( ");
					}

					this.getWhereSql(whereSql, fd, count, (count == 1) ? true
							: false);

					whereSql.append(" ) ");
				} else {
					if (StringUtils.isNotEmpty(filterDescriptor.getLogic())
							&& count > 1 && t > 1) {
						whereSql.append(" " + filterDescriptor.getLogic() + " ");
					}
					if (fd.getValue() != null) {
						jointWhereSql(whereSql, fd);
					}
				}
				t++;
				count++;
			}
		}
	}

	public void jointWhereSql(StringBuilder whereSql, FilterDescriptor fd) {
		String field = fd.getField().split("_0_")[1];
		String operator = fd.getOperator();
		Object value = fd.getValue();
		whereSql.append(" " + field + " ");
		whereSql.append(OperatorEnum.getValue(operator));
		if (value instanceof String) {
			whereSql.append(" '" + fd.getStringValue() + "' ");
		} else if (value instanceof Integer) {
			whereSql.append(fd.getIntValue());
		} else if (value instanceof Double) {
			whereSql.append(fd.getDoubleValue());
		} else if (value instanceof Date) {
			whereSql.append(" '" + fd.getStringValue() + "' ");
		} else if (value instanceof Boolean){
			if("false".equals(fd.getStringValue()))
				whereSql.append(" '0' ");
			else
				whereSql.append(" '1' ");
		}
	}

}
