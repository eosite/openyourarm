package org.wltea.expression;
/**
 * 
 * @author Dane.Dong
 *
 */
public class ExpressionRuntimeException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1600402994363855866L;
	/**
	 * 错误标识
	 */
	private String errorTokenText;
	/**
	 * 错误位置
	 */
	private int errorPosition = -1;

	public ExpressionRuntimeException() {
		super();
	}

	public ExpressionRuntimeException(String message) {
		super(message);
	}

	public ExpressionRuntimeException(String msg, String errorTokenText) {
		super(msg);
		this.errorTokenText = errorTokenText;
	}

	public ExpressionRuntimeException(String msg, String errorTokenText,
			int errorPosition) {
		super(msg);
		this.errorPosition = errorPosition;
		this.errorTokenText = errorTokenText;
	}

	public String getErrorTokenText() {
		return errorTokenText;
	}

	public void setErrorTokenText(String errorTokenText) {
		this.errorTokenText = errorTokenText;
	}

	public int getErrorPosition() {
		return errorPosition;
	}

	public void setErrorPosition(int errorPosition) {
		this.errorPosition = errorPosition;
	}

	public ExpressionRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExpressionRuntimeException(Throwable cause) {
		super(cause);
	}
}
