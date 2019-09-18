package controller;

import java.util.ArrayList;

import dao.CarerDaoManager;
import model.Account;
import model.Schedule;

public class Controller {
	CarerDaoManager cdm;

	private Account loginUser;
	
	public Controller() {
		cdm = new CarerDaoManager();
		
	}
	
	public Account selectAccount(String id) {
		return cdm.selectAccount(id);
	}
	
	public boolean insertAccount(Account account) {
		return cdm.insertAccount(account);
	}
	
	public Account findAccount(String email) {
		return cdm.findAccount(email);
	}
	
	public boolean loginAccount(String id, String password) {
		boolean flag = false;
		Account account = cdm.selectAccount(id);
		
		if(account != null) {
			if(account.getPassword().equals(password)) {
				if(account.getType().equals("carer")) {
					double d[] = cdm.selectServiceGrade(id);
					String grade = "C";
					int cost = 10000;
					if(d[1] >5 && d[0]>3) {
						grade = "A";
						cost = 20000;
					}
					else if(d[1] >3 && d[0] >3) {
						grade = "B";								
						cost = 15000;
					}			
					
					//3,2
					cdm.updateGradeAndCost(id, grade, cost);
					account = cdm.selectAccount(id);
				}
				loginUser = account;
				flag = true;
			}
		}
		return flag;
	}
	
	public boolean logout() {
		if(loginUser==null)
			return false;				
		
		loginUser=null;
		return true;
	}
	
	public boolean deleteAccount(String id) {
		if(cdm.deleteSchedule(id))
			return cdm.deleteAccount(id);
		else
			return false;
	}
	
	public ArrayList<Schedule> selectSchedule(String id) {
		return cdm.selectSchedule(id);
	}
	
	public boolean insertSchedule(Schedule schedule){
		return cdm.insertSchedule(schedule);
	}
	
	public ArrayList<Schedule> selectCarerUpdatableSchedule(String id){
		return cdm.selectCarerUpdatableSchedule(id);
	}
	
	public boolean updateCarerSchedule(Schedule schedule) {
		return cdm.updateCarerSchedule(schedule);
	}
	
	public boolean deleteCarerSchedule(String id) {
		return cdm.deleteCarerSchedule(id);
	}
	public ArrayList<Schedule> selectCarerPreviousSchedule(String id){
		return cdm.selectCarerPreviousSchedule(id);
	}
	
	public ArrayList<Account> selectBookingAvailableCarer(String location, String date){
		return cdm.selectBookingAvailableCarer(location, date);
	}
	
	public boolean insertBooking(String sche_id, String message) {
		//로그인한 유저가 자신의 정보를 dao에 넘길땐 여기서 넘기는게 맞음
		//ui에서 manager.get()을 통해서 보내는게 아니라..
		//다른걸 다 잘못했다. 원칙적으로 이게 정석임
		return cdm.insertBooking(sche_id, loginUser.getId(), message);
	}
	
	public ArrayList<Schedule> selectBooking(){
		return cdm.selectBooking(loginUser.getId());
	}
	
	public boolean deleteBooking (String sche_id) {
		return cdm.deleteBooking(sche_id);
	}
	
	public ArrayList<Schedule> selectEvaluableBooking(){
		return cdm.selectEvaluableBooking(loginUser.getId());
	}
	
	public boolean evaluateCarer(String she_id, double score) {
		return cdm.evaluateCarer(she_id, score);
	}
	
	public Account getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(Account loginUser) {
		this.loginUser = loginUser;
	}
	
}
