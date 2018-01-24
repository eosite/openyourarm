package com.glaf.datamgr.sqlparser;

import java.io.IOException;

import com.alibaba.druid.sql.ast.expr.SQLBinaryExpr;
import com.alibaba.druid.sql.ast.expr.SQLLiteralExpr;
import com.alibaba.druid.sql.visitor.SQLASTOutputVisitor;
import com.alibaba.druid.sql.visitor.SQLASTVisitor;

public class SQLObjectExpr extends SQLBinaryExpr implements SQLLiteralExpr {

	public SQLObjectExpr() {

	}

	public SQLObjectExpr(String value) {
		super.setValue(value);
	}

	public void accept0(SQLASTVisitor visitor) {
		if (visitor != null && visitor instanceof SQLASTOutputVisitor) {
			/**
			 * 不管类型set
			 */
			SQLASTOutputVisitor vis = (SQLASTOutputVisitor) visitor;
			try {
				vis.getAppender().append(this.getValue());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			visitor.visit(this);
		}
		visitor.endVisit(this);
	}

}
