package com.glaf.form.core.query;

import java.util.ArrayList;
import java.util.List;

public class SqlParameterQuery {

	private final static String eq = "=";

	private final static String notEq = "<>";

	private final static String gt = ">";

	private final static String lt = "<";

	private final static String and = "and";

	private final static String in = "in";

	private final static String like = "like";

	private final static String notLike = "not like";

	private final static String isNull = "is null";

	private final static String isNotNull = "is not null";

	private List<SqlParameter> params = new ArrayList<SqlParameter>();

	public void set(String name, Object value, String syn, String combine) {
		params.add(new SqlParameter(name, value, syn, combine));
	}

	public void set(String name, Object value, String syn) {
		params.add(new SqlParameter(name, value, syn));
	}

	public void eq(String name, Object value) {
		this.eq(name, value, and);
	}

	public void eq(String name, Object value, String combine) {
		this.set(name, value, eq, combine);
	}

	public void notEq(String name, Object value) {
		this.notEq(name, value, and);
	}

	public void notEq(String name, Object value, String combine) {
		this.set(name, value, notEq, combine);
	}

	public void in(String name, Object value) {
		this.in(name, value, and);
	}

	public void in(String name, Object value, String combine) {
		this.set(name, value, in, combine);
	}

	public void like(String name, Object value) {
		this.like(name, value, and);
	}

	public void like(String name, Object value, String combine) {
		this.setLike(name, value, like, combine);
	}

	public void notLike(String name, Object value) {
		this.notLike(name, value, and);
	}

	public void notLike(String name, Object value, String combine) {
		this.setLike(name, value, notLike, combine);
	}

	private void setLike(String name, Object value, String syn, String combine) {
		if (value != null) {
			String like = value.toString();
			if (like.trim().length() > 0) {
				if (!like.startsWith("%"))
					like = "%" + like;
				if (!like.endsWith("%"))
					like = like + "%";
				value = like;
			}
		}
		this.set(name, value, syn, combine);
	}

	public void gt(String name, Object value) {
		this.gt(name, value, and);
	}

	public void gt(String name, Object value, String combine) {
		this.set(name, value, gt, combine);
	}

	public void lt(String name, Object value) {
		this.lt(name, value, and);
	}

	public void lt(String name, Object value, String combine) {
		this.set(name, value, lt, combine);
	}

	public void isNull(String name) {
		this.isNull(name, and);
	}

	public void isNull(String name, String combine) {
		this.set(name, isNull, "", combine);
	}

	public void isNotNull(String name) {
		this.isNotNull(name, and);
	}

	public void isNotNull(String name, String combine) {
		this.set(name, isNotNull, "", combine);
	}

	/**
	 * 获取条件语句
	 * 
	 * @return
	 */
	public String getWhereClause() {
		if (params.size() == 0)
			return null;
		StringBuffer SB = new StringBuffer("");
		for (SqlParameter sp : params) {
			SB.append(" ").append(sp.getCombine()).append(" ")
					.append(sp.getName()).append(" ").append(sp.getSyn())
					.append(" ");
			if (sp.getValue() instanceof String && !isNullOrEmpty(sp.getSyn())) {
				SB.append("'").append(sp.getValue()).append("'");
			} else if (!isNullOrEmpty(sp.getSyn())
					&& sp.getSyn().equalsIgnoreCase("in")) {
				SB.append("(");
				if (sp.getValue() != null && sp.getValue().getClass().isArray()) {
					Object[] values = (Object[]) sp.getValue();
					for (int i = 0; i < values.length; i++) {
						Object v = values[i];
						switch (i) {
						case 0:
							break;
						default:
							SB.append(",");
							break;
						}
						appendVal(SB, v);
					}
				} else {
					appendVal(SB, sp.getValue());
				}
				SB.append(")");
			} else {
				SB.append(sp.getValue());
			}
		}
		return SB.toString();
	}

	private static boolean isNullOrEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	private static void appendVal(StringBuffer SB, Object v) {
		if (v instanceof String) {
			SB.append("'").append(v).append("'");
		} else {
			SB.append(v);
		}
	}

	/**
	 * 获取预处理条件语句
	 * 
	 * @return
	 */
	public String getPrepareWhereClause() {
		if (params.size() == 0)
			return null;
		StringBuffer SB = new StringBuffer();
		for (SqlParameter sp : params) {
			SB.append(" ").append(sp.getCombine()).append(" ")
					.append(sp.getName()).append(" ").append(sp.getSyn())
					.append(" ");
			if (isNullOrEmpty(sp.getSyn())) {
				SB.append(sp.getValue());
			} else if (sp.getSyn().equalsIgnoreCase("in")) {
				SB.append("(");
				if (sp.getValue() != null && sp.getValue().getClass().isArray()) {
					Object[] values = (Object[]) sp.getValue();
					for (int i = 0; i < values.length; i++) {
						switch (i) {
						case 0:
							break;
						default:
							SB.append(",");
							break;
						}
						SB.append(" ?");
					}
				} else {
					SB.append(" ?");
				}
				SB.append(")");
			} else {
				SB.append(" ?");
			}
		}
		return SB.toString();
	}

	public List<Object> getValues() {
		if (params.size() > 0) {
			Object value;
			List<Object> values = new ArrayList<Object>();
			for (SqlParameter sp : params) {
				value = sp.getValue();
				if (value != null) {
					if (value instanceof java.util.Date) {
						value = new java.sql.Date(
								((java.util.Date) value).getTime());
					} else if (value.getClass().isArray()) {
						Object[] vals = (Object[]) value;
						for (Object v : vals) {
							values.add(v);
						}
						continue;
					}
				}
				values.add(value);
			}
			return values;
		}

		return null;
	}

	public class SqlParameter {
		private String name;
		private Object value;
		private String syn = eq;
		private String combine = and;

		public SqlParameter() {
		}

		public SqlParameter(String name, Object value, String syn,
				String combine) {
			this.name = name;
			this.value = value;
			this.syn = syn;
			this.combine = combine;
		}

		public SqlParameter(String name, Object value, String syn) {
			this.name = name;
			this.value = value;
			this.syn = syn;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}

		public String getSyn() {
			return syn;
		}

		public void setSyn(String syn) {
			this.syn = syn;
		}

		public String getCombine() {
			return combine;
		}

		public void setCombine(String combine) {
			this.combine = combine;
		}

	}

	public static void main(String[] args) {
		SqlParameterQuery query = new SqlParameterQuery();
		query.like("name", "klaus");
		query.like("remark", "klaus", "or");
		query.in("id", new String[] { "34", "45" });
		//System.out.println(query.getWhereClause());
		//System.out.println(query.getPrepareWhereClause());
		//System.out.println(query.getValues());
	}
}
