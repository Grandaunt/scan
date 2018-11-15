package com.bcm.havoc.wmapp_20181108.Entity;

public class OrderPack {

	private int id;
	private String expressNumber;//快递单号
	private String expressCompany;//快递公司
	private String endPosition;//目的地
	private Integer status;//状态（1：入库2：出库3：上车4：下车5：代收）
	private Integer upDown;//上下行（1：上行2：下行）
	private String kuWeiNumber;//库位编码
	private String operator;//操作人
	private String inTime;//入库时间
	private String outTime;//出库时间
	private String abnormal;//异常
	public OrderPack() {
	}

	public OrderPack(int id, String expressNumber, String expressCompany, String endPosition, Integer status, Integer upDown, String kuWeiNumber, String operator, String inTime, String outTime) {
		this.id = id;
		this.expressNumber = expressNumber;
		this.expressCompany = expressCompany;
		this.endPosition = endPosition;
		this.status = status;
		this.upDown = upDown;
		this.kuWeiNumber = kuWeiNumber;
		this.operator = operator;
		this.inTime = inTime;
		this.outTime = outTime;
	}

	public String getInTime() {
		return inTime;
	}

	public void setInTime(String inTime) {
		this.inTime = inTime;
	}

	public String getOutTime() {
		return outTime;
	}

	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getExpressNumber() {
		return expressNumber;
	}

	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber;
	}

	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	public String getEndPosition() {
		return endPosition;
	}

	public void setEndPosition(String endPosition) {
		this.endPosition = endPosition;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getUpDown() {
		return upDown;
	}

	public void setUpDown(Integer upDown) {
		this.upDown = upDown;
	}

	public String getKuWeiNumber() {
		return kuWeiNumber;
	}

	public void setKuWeiNumber(String kuWeiNumber) {
		this.kuWeiNumber = kuWeiNumber;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getAbnormal() {
		return abnormal;
	}

	public void setAbnormal(String abnormal) {
		this.abnormal = abnormal;
	}

	@Override
	public String toString() {
		return "OrderPack{" +
				"id=" + id +
				", expressNumber='" + expressNumber + '\'' +
				", expressCompany='" + expressCompany + '\'' +
				", endPosition='" + endPosition + '\'' +
				", status=" + status +
				", upDown=" + upDown +
				", kuWeiNumber='" + kuWeiNumber + '\'' +
				", operator='" + operator + '\'' +
				", inTime='" + inTime + '\'' +
				", outTime='" + outTime + '\'' +
				'}';
	}
}
