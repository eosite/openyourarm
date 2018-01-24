package com.glaf.form.core.domain;

public class GisExcelModel {
	private String stake;
	private String name;
	private String type;
	private double[] point;

	public GisExcelModel() {
	}

	public GisExcelModel(String stake, String name, String type, double[] point) {
		super();
		this.stake = stake;
		this.name = name;
		this.type = type;
		this.point = point;
	}

	public String getStake() {
		return stake;
	}

	public void setStake(String stake) {
		this.stake = stake;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double[] getPoint() {
		return point;
	}

	public void setPoint(double[] point) {
		this.point = point;
	}

}
