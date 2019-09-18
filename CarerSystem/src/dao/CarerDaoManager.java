package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Account;
import model.Schedule;


public class CarerDaoManager {

	private Connection conn;

	public Account selectAccount(String id) {
		//both
		Account account=null;
		conn = ConnectionManager.getConnection();
		String sql="select * from account where account_id = ? ";
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();			
			while(rs.next()) {
				//System.out.print(rs.getString(1)+" ");
				//System.out.print(rs.getString(2)+" ");
				//System.out.println(rs.getString(4));
				account = new Account(
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getString(7),
						rs.getString(8),
						rs.getString(9),
						rs.getInt(10)
						);
			}
			ConnectionManager.close(conn);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return account;
	}
	
	public Account findAccount(String email){
		//both
		//1-2 findAccount
		Account account = null;
		conn = ConnectionManager.getConnection();
		String sql="select * from account where account_email = ?";
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				account = new Account();
				account.setId(rs.getString("account_id"));
				account.setHintForPw(rs.getString("account_hintForPw"));
			}
			ConnectionManager.close(conn);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return account;
	}
	
	public boolean insertAccount(Account account){
		//both
		//1-3 enrollment
		boolean flag=false;
		conn = ConnectionManager.getConnection();
		String sql="insert into account values(?,?,?,?,?,?,?,?,?,?)";
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, account.getId());
			pstmt.setString(2, account.getType());
			pstmt.setString(3, account.getPassword());
			pstmt.setString(4, account.getName());
			pstmt.setString(5, account.getTel());
			pstmt.setString(6, account.getLocation());
			pstmt.setString(7, account.getEmail());
			pstmt.setString(8, account.getHintForPw());
			pstmt.setString(9, account.getGrade());
			pstmt.setInt(10, account.getCost());

			int row = pstmt.executeUpdate();
			if(row == 1)
				flag=true;
			ConnectionManager.close(conn);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
		
	public boolean deleteSchedule(String account_id) {
		boolean flag=false;
		conn = ConnectionManager.getConnection();
		String sql="delete from schedule where carer_id = ? or customer_id = ? ";
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, account_id);
			pstmt.setString(2, account_id);
			int row = pstmt.executeUpdate();
			if(row >0)
				flag=true;
			ConnectionManager.close(conn);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
		
	public boolean deleteAccount(String account_id) {
		//both
		//2-6, 3-6 delete account
		boolean flag=false;
		conn = ConnectionManager.getConnection();
		String sql="delete from account where account_id = ? ";
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, account_id);
			int row = pstmt.executeUpdate();
			if(row >0)
				flag=true;
			ConnectionManager.close(conn);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public ArrayList<Schedule> selectSchedule(String id) {
		//for carer
		//2-1 check schedule
		//select * from schedule where carer_id = id and  not customer_id = 'dummy' and  sysdate < sche_date
		ArrayList<Schedule> sList = new ArrayList<>();
		conn = ConnectionManager.getConnection();
		String sql="select to_char(s.sche_date,'YYMMDD'), s.sche_begintime, s.sche_endtime, s.sche_location,"
				+" a.account_name, a.account_tel, s.sche_messagefromcustomer"
				+" from schedule s, account a where s.customer_id = a.account_id and s.sche_date > sysdate-1"
				+" and carer_id = ? order by s.sche_date";
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Schedule s = new Schedule();
				s.setDate(rs.getString(1));
				s.setBeginTime(rs.getInt(2));
				s.setEndTime(rs.getInt(3));
				s.setLocation(rs.getString(4));				
				s.setCustomerName(rs.getString(5));
				
				s.setCustomerTel(rs.getString(6));				
				s.setMessageFromCustomer(rs.getString("sche_messagefromcustomer"));
				sList.add(s);
			}
			ConnectionManager.close(conn);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return sList;
	}

	public boolean insertSchedule(Schedule schedule) {
		//for carer
		//2-2 insert schedule
		boolean flag=false;
		conn = ConnectionManager.getConnection();
		String sql="insert into schedule values(sche_id_seq.nextVal, ?, 'dummy', to_date(?,'YYMMDD'), ?, ?, ?, null, null)";
		//시간은 입력하지않고 날짜만 입력되게 to_date
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, schedule.getCarerId());			
			pstmt.setString(2, schedule.getDate());
			pstmt.setInt(3, schedule.getBeginTime());
			pstmt.setInt(4, schedule.getEndTime());
			pstmt.setString(5, schedule.getLocation());
			int row = pstmt.executeUpdate();
			if(row==1)
				flag=true;
			ConnectionManager.close(conn);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public ArrayList<Schedule> selectCarerUpdatableSchedule(String id) {
		//for carer
		ArrayList<Schedule> sList = new ArrayList<>();
		conn = ConnectionManager.getConnection();
		String sql="select to_char(sche_date, 'YYMMDD'), sche_begintime, sche_endtime, sche_location, sche_id from schedule where carer_id = ? and sche_date > sysdate order by sche_date";
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Schedule s = new Schedule();
				s.setDate(rs.getString(1));
				s.setBeginTime(rs.getInt("sche_begintime"));
				s.setEndTime(rs.getInt("sche_endtime"));
				s.setLocation(rs.getString("sche_location"));
				s.setId(rs.getString("sche_id"));
				sList.add(s);				
			}
			ConnectionManager.close(conn);
		}catch (SQLException e) {
			e.printStackTrace();
		}		
		return sList;
	}	
	
	public boolean updateCarerSchedule(Schedule schedule) {
		//2-3 update schedule
		boolean flag=false;
		conn = ConnectionManager.getConnection();
		System.out.println(schedule.getDate());
		String sql="update schedule set sche_date = to_date(?, 'YYMMDD'), sche_begintime = ? , sche_endtime = ? , sche_location = ? where sche_id = ?";
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, schedule.getDate());			
			pstmt.setInt(2, schedule.getBeginTime());
			pstmt.setInt(3, schedule.getEndTime());
			pstmt.setString(4, schedule.getLocation());
			pstmt.setString(5, schedule.getId());			
			int row = pstmt.executeUpdate();
			if(row ==1)
				flag = true;
			ConnectionManager.close(conn);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean deleteCarerSchedule(String id) {
		//2-4 delete schedule;
		boolean flag=false;
		conn = ConnectionManager.getConnection();
		String sql="delete from schedule where sche_id = ? ";
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, id);
			int row = pstmt.executeUpdate();
			if(row ==1)
				flag=true;
			ConnectionManager.close(conn);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public ArrayList<Schedule> selectCarerPreviousSchedule(String id){
		ArrayList<Schedule> sList = new ArrayList<>();
		conn = ConnectionManager.getConnection();
		String sql =" select to_char(s.sche_date, 'YYMMDD'), s.sche_begintime, s.sche_endtime, s.sche_location, s.sche_servicegrade" 
				+" from schedule s where s.carer_id = ? and s.sche_date < to_date(to_char(sysdate, 'YYMMDD'),'YYMMDD')";
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Schedule s = new Schedule();
				s.setDate(rs.getString(1));
				s.setBeginTime(rs.getInt(2));
				s.setEndTime(rs.getInt(3));
				s.setLocation(rs.getString(4));
				s.setServiceGrade(rs.getInt(5)); 
				sList.add(s);
			}
			ConnectionManager.close(conn);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return sList;
	}
	
	public ArrayList<Schedule> selectBooking(String account_id){
		//for customer
		//3-1 check booking
		//select * from schedule where customer_id = id and sysdate < sche_date
		ArrayList<Schedule> sList = new ArrayList<>();
		conn = ConnectionManager.getConnection();
//		String sql ="select * from account a, schedule s where s.customer_id = ? "
//				+ " and a.account_id = s.carer_id"
//				+ " and sche_date > sysdate-1";
		String sql ="select to_char(s.sche_date,'YYMMDD'), s.sche_begintime, s.sche_endtime,"
				+ " a.account_name, a.account_tel, s.sche_id"
				+ " from account a, schedule s"
				+ " where s.customer_id = ? "
				+ " and a.account_id = s.carer_id"
				+ " and sche_date > sysdate-1 order by s.sche_date";
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, account_id);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {				
				Schedule s = new Schedule();
				s.setDate(rs.getString(1));	
				s.setBeginTime(rs.getInt(2));
				s.setEndTime(rs.getInt(3));
				s.setCustomerName(rs.getString(4));
				s.setCustomerTel(rs.getString(5)); 
				s.setId(rs.getString(6));
				sList.add(s);
			}
			ConnectionManager.close(conn);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return sList;
	}
	
	public ArrayList<Account> selectBookingAvailableCarer(String location, String date){
		ArrayList<Account> cList = new ArrayList<>();
		conn = ConnectionManager.getConnection();
		//String sql = "select * from schedule where sche_location = 'gangnam' and sche_date = '190608'";
		String sql = "select account_name, account_grade, account_cost, sche_begintime, sche_endtime," 
		+" sche_id"//이건 억지로 넣은거.. 원래 이렇게 하면 안됨
		+" from account, schedule where account_id=carer_id and sche_date = ? and customer_id = 'dummy'"
		+" and sche_location = ? ";
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, date);
			pstmt.setString(2, location);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Account carer = new Account();
				carer.setName(rs.getString(1));
				carer.setGrade(rs.getString(2));
				carer.setCost(rs.getInt(3));
				carer.setBeginTime(rs.getInt(4));
				carer.setEndTime(rs.getInt(5));
				carer.setId(rs.getString(6));
				//schedule의 id를 account의 id에 넣음 이러면 안됨..임시로..
				cList.add(carer);				
			}
			ConnectionManager.close(conn);
		}catch (SQLException e) {
			e.printStackTrace();
		}		
		return cList;
	}
	
	public boolean insertBooking(String sche_id, String account_id, String message) {
		//for customer
		//3-2 insert booking
		
		boolean flag=false;
		conn = ConnectionManager.getConnection();
		String sql = "update schedule set customer_id = ?, sche_messagefromcustomer = ?"
				+ " where sche_id = ? ";
		//시간은 입력하지않고 날짜만 입력되게 to_date
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, account_id);
			pstmt.setString(2, message);
			pstmt.setString(3, sche_id);
			
			int row = pstmt.executeUpdate();
			if(row==1)
				flag=true;
			ConnectionManager.close(conn);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean deleteBooking(String sche_id) {
		//for customer
		//3-3 cancel booking
		//update schedule set customer_id = 'dummy'
		boolean flag=false;
		conn = ConnectionManager.getConnection();
		String sql="update schedule set customer_id = 'dummy' where sche_id = ?";
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, sche_id);
			int row = pstmt.executeUpdate();
			if(row == 1)
				flag=true;
			ConnectionManager.close(conn);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
//민정
	public ArrayList<Schedule> selectEvaluableBooking(String customer_id) {
		//customer
		//3-4 evaluate grade
		//select * from schedule where customer_id = id and sysdate < sche_date
		System.out.println(customer_id);
		ArrayList<Schedule> evaluableList = new ArrayList<>();
		conn = ConnectionManager.getConnection();
		String sql = "select to_char(s.sche_date,'YYMMDD'), s.sche_begintime, s.sche_endtime, a.account_name,"
				+" s.sche_id from schedule s, account a where s.customer_id = ? "
				+" and sysdate-1 > s.sche_date"  
				+" and s.sche_servicegrade is null and s.carer_id = a.account_id order by s.sche_date";
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, customer_id);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Schedule s = new Schedule();
				s.setDate(rs.getString(1));
				s.setBeginTime(rs.getInt(2));
				s.setEndTime(rs.getInt(3));
				s.setCustomerName(rs.getString(4));
				s.setCustomerId(rs.getString(5));
				evaluableList.add(s);
			}
			ConnectionManager.close(conn);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return evaluableList;
	}
		
	public boolean evaluateCarer(String sche_id, double score) {
		//customer
		//3-4 evaluate grade		
		//update schedule set sche_servicegrade= 'grade' where sche_id = 'id';
		boolean flag=false;
		conn = ConnectionManager.getConnection();
		String sql="update schedule set sche_servicegrade = ? where sche_id = ? ";
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setInt(1, (int)score);
			pstmt.setString(2, sche_id);
			int row = pstmt.executeUpdate();
			if(row == 1)
				flag=true;
			ConnectionManager.close(conn);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
//민정
	public boolean updateAccountInfo(Account account) {
		//both
		//2-5, 3-5 update my info
		return false;
	}
//민정
	
	public double[] selectServiceGrade(String account_id) {
		
		conn = ConnectionManager.getConnection();
		String sql="select avg(sche_servicegrade), count(sche_servicegrade) from schedule where carer_id = ? "
				+ "and sche_servicegrade is not null";
		double[] avgGrade = new double[2];;
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, account_id);
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				avgGrade[0] = rs.getDouble(1);
				avgGrade[1] = rs.getDouble(2);
			}
			ConnectionManager.close(conn);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return avgGrade;
	}
	
	public boolean updateGradeAndCost(String id, String grade, int cost) {
		//schedule에서 sysdate를 기준으로 지금까지의 모든 평점을 평균을 내서
		//grade와 cost를 update
		//구현해보시게
		boolean flag = false;
		conn = ConnectionManager.getConnection();
		String sql="update account set account_grade = ? , account_cost = ? where account_id = ? ";
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, grade);
			pstmt.setInt(2, cost);
			pstmt.setString(3, id);
			
			int row = pstmt.executeUpdate();
			if(row == 1)
				flag = true;
			ConnectionManager.close(conn);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(flag);
		return flag;
		
	}
	
	public boolean sample() {
		boolean flag=false;
		conn = ConnectionManager.getConnection();
		String sql="";
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){

			ConnectionManager.close(conn);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
}
