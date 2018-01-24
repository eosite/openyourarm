package com.glaf.datamgr.jdbc;

import java.lang.reflect.Field;
import java.util.ArrayList;

import javax.persistence.Column;

public class JdbcInsert<T> extends Crud<T> {

	@Override
	public void init(T t) {
		Class<?> cls = t.getClass();
		javax.persistence.Table table = cls.getAnnotation(javax.persistence.Table.class);
		if (table != null) {
			sqlBuffer = new StringBuffer("INSERT INTO ").append(table.name()).append(" (");
			StringBuffer VALUES = new StringBuffer(" ) VALUES ( ");
			Field[] fs = cls.getDeclaredFields();
			this.fields = new ArrayList<Field>();
			int index = 0;
			for (Field field : fs) {
				Column column = field.getAnnotation(Column.class);
				if (column != null) {
					switch (index) {
					case 0:
						break;
					default:
						sqlBuffer.append(", ");
						VALUES.append(", ");
						break;
					}
					sqlBuffer.append(column.name());
					VALUES.append("?");
					this.fields.add(field);
					index++;
				}
			}
			sqlBuffer.append(VALUES.append(")"));
			// System.out.println(this.sql.toString());
		}
	}

}
