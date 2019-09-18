package model;

public class Account {
	private String id;
	private String type;
	private String password;
	private String name;
	private String tel;
	private String location;
	private String email;
	private String hintForPw;
	private String grade;
	private int cost;
	private int beginTime;
	private int endTime;
	
	public Account() {
		super();
	}

	public Account(String id, String type, String password, String name, String tel, String location, String email,
			String hintForPw, String grade, int cost) {
		super();
		this.id = id;
		this.type = type;
		this.password = password;
		this.name = name;
		this.tel = tel;
		this.location = location;
		this.email = email;
		this.hintForPw = hintForPw;
		this.grade = grade;
		this.cost = cost;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHintForPw() {
		return hintForPw;
	}

	public void setHintForPw(String hintForPw) {
		this.hintForPw = hintForPw;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
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

	@Override
	public String toString() {
		return "Account [id=" + id + ", type=" + type + ", password=" + password + ", name=" + name + ", tel=" + tel
				+ ", location=" + location + ", email=" + email + ", hintForPw=" + hintForPw + ", grade=" + grade
				+ "]";
	}
	
	

}
