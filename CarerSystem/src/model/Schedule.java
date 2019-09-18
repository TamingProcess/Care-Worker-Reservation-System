package model;

public class Schedule {
	private String carerId;
	private String id;
	private String date;
	private int beginTime;
	private int endTime;
	private String location;
	private String messageFromCustomer;
	private String customerId;
	private int serviceGrade;
	private String customerName;
	private String customerTel;
	
	
	public Schedule() {
	}
	
	public Schedule(String carerId, String date, int beginTime, int endTime, String location) {
		super();
		this.carerId = carerId;
		this.date = date;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.location = location;
	}

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(int beginTime) {
		this.beginTime = beginTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMessageFromCustomer() {
		return messageFromCustomer;
	}


	public void setMessageFromCustomer(String messageFromCustomer) {
		this.messageFromCustomer = messageFromCustomer;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCarerId() {
		return carerId;
	}

	public void setCarerId(String carerId) {
		this.carerId = carerId;
	}

	
	public int getServiceGrade() {
		return serviceGrade;
	}

	public void setServiceGrade(int serviceGrade) {
		this.serviceGrade = serviceGrade;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerTel() {
		return customerTel;
	}

	public void setCustomerTel(String customerTel) {
		this.customerTel = customerTel;
	}

	@Override
	public String toString() {
		return "Schedule [date=" + date + ", beginTime=" + beginTime
				+ ", endTime=" + endTime + ", location=" + location + "]";
	}

}
