package view;

import java.util.ArrayList;
import java.util.Scanner;

import controller.Controller;
import model.Account;
import model.Schedule;

public class UI {
	private Controller manager;
	private Scanner sc; // int 등의 데이터 타입 입력 받기
	private Scanner scLine; //String 문자열 입력 받기

	public UI() { //생성자

		manager = new Controller();
		sc = new Scanner(System.in); 
		scLine = new Scanner(System.in);

		start();
	}

	public void start() {
		//boolean accountFlag = true;

		//while (accountFlag) {
		while(true) {
			mainMenu();
			boolean carerFlag = true;
			boolean customerFlag = true;

			switch (sc.nextInt()) {
			case 1://로그인
				int mode = loginAccount(); //요양사일때와 일반 회원일때를 구분
				switch(mode) {
				case 1: // 요양사 메뉴 출력
					while(carerFlag) {
						System.out.println("[SYSTEM] <"+manager.getLoginUser().getName()+" 요양사님 접속중>");
						System.out.println("------------------------");
						showCarerMenu();
						switch(sc.nextInt()) {
						case 1: //예약확인
							//confirmBooking();
							showSchedule();
							break;
						case 2://스케줄 등록
							insertSchedule();
							break;
						case 3://스케줄 수정
							updateSchedule();
							break;
						case 4://스케줄 삭제
							deleteSchedule();
							break;
						case 5://나의 등급 보기
							showGrade();
							break;
						case 6://내 정보 수정
							updateAccount();
							break;
						case 7://지난스케줄보기
							showPreviousSchedule();
							break;
						case 8://회원탈퇴
							if(!deleteAccount())
								break;
						//case 9://요양사메뉴종료
						case 0: //logout
							logout();
							carerFlag=false;
							break;
						default:
							System.out.println("[ERROR] 존재하지 않는 메뉴입니다.");
						}
					}
					break;
				case 2: // 고객 메뉴 출력
					while(customerFlag) {
						System.out.println("[SYSTEM] <"+ manager.getLoginUser().getName()+" 고객님 접속중>");
						System.out.println("------------------------");
						showCustomerMenu();
						switch(sc.nextInt()){
						case 1://예약확인
							showBooking();
							break;
						case 2://요양사 예약 하기
							insertBooking();
							break;
						case 3://예약취소
							deleteBooking();
							break;
						case 4://평가하기
							evaluateCarer();
							break;
						case 5://내 정보 수정
							updateAccount();
							break;
						case 6://회원탈퇴
							if(!deleteAccount())
								break;
						//case 9://프로그램종료
						case 0: //logout
							logout();
							customerFlag=false;
							break;
						default:
							System.out.println("[ERROR] 존재하지 않는 메뉴입니다.");
						}
					}
					break;
				case 0: // 상위 메뉴 출력
					//mainMenu();
//mainMenu 출력이 2번 되게된다.			
					//mode가 0이라는 얘기는 로그인에 실패했다는 얘기
					//상위메뉴출력이 아니라 여기 switch문을 빠져나가면 된다
					break;
				}
				break;
			case 2: //계정 찾기
				findAccount();
				break;
			case 3: //회원가입
				insertAccount();
				break;
			case 0: // 프로그램 종료
				//accountFlag = false;
				System.exit(0);
			default : //잘못된 메뉴 입력
				System.out.println("[ERROR] 존재하지 않는 메뉴입니다.");
			}
		}
	}
	public void mainMenu() {
		System.out.println("===============");
		System.out.println("[요양사예약프로그램]");
		System.out.println("===============");
		System.out.println("1. 로그인");
		System.out.println("2. 계정 찾기");
		System.out.println("3. 회원가입");
		System.out.println("0. 프로그램 종료");
		System.out.println("---------------");
		System.out.print("메뉴 선택=>");
	}

	public int loginAccount() { //mainMenu중 1번  로그인 UI
		int mode = 0;
		//while(true) {
			String id = null;
			String password = null;
			System.out.println("아이디=>");
			id = scLine.nextLine();
			System.out.println("비밀번호=>");
			password = scLine.nextLine();
			if ((id == null || id.equals("")) || (password == null || password.equals(""))) {
				System.out.println("[ERROR] 다시입력해주세요.");
			} 
			else {
				//Account result = manager.loginAccount(new Account(null, id, password));
				boolean result = manager.loginAccount(id, password);
				if (result) {
					//System.out.println("로그인 성공!\n" + manager.getId() + "(" + manager.getName() + "님) 환영합니다.");
					System.out.println("[SYSTEM] 로그인 성공!\n[SYSTEM] "+ manager.getLoginUser().getId() + "(" + manager.getLoginUser().getName() + "님) 환영합니다.");
					
					if(manager.getLoginUser().getType().equals("carer")) 
						mode = 1;
					else 
						mode = 2;
					//break;
				} else {
					System.out.println("[ERROR] 아이디/비밀번호가 일치하지 않습니다.");
				}
			}
		//}
		return mode;
	}
	
	public void logout() {//[요양사+고객] 로그아웃
		if(manager.logout())
			System.out.println("[SYSTEM] 로그아웃 성공\n[SYSTEM] 상위 메뉴로 돌아갑니다.");		
	}

	public void findAccount(){//mainMenu중 2번 계정 찾기UI
		
		while(true) {
			System.out.print("회원 가입시 작성한 이메일을 입력해주세요\n=>");
			//이메일이 입력되면 아이디랑 힌트를 알려준다.
			String email = scLine.nextLine();
			//			if( email == null ) {
			//				System.out.println("[ERROR] 다시 입력해주세요.");
			//			}else {
//예외처리는 나중에
//email도 중복방지해야하네........ㅋㅋ 패쓰~~

			Account account = manager.findAccount(email);
			boolean flag=false;
			
			if(account != null) {
				//System.out.println("아이디:"+manager.getID()+"입니다.");
				//System.out.println("비밀번호힌트:"+manager.getHintForPw());
				System.out.println("아이디 : "+account.getId());
				System.out.println("비밀번호 힌트 : "+account.getHintForPw());
				return ;
			}
			else {
				flag=true;
				System.out.println("[ERROR] 존재하지 않는 정보입니다.");
			}
			
			while(flag) {
				System.out.println("1. 다시 입력하기");
				System.out.println("2. 상위메뉴로 돌아가기");
				System.out.println("---------------");
				System.out.print("메뉴 선택=>");
				switch(sc.nextInt()){
				case 1:
					flag=false;
					break;
				case 2:
					return ; 
				default : 
					System.out.println("[ERROR] 존재하지 않는 메뉴입니다.");
				}
			}
		}
	}

	public void insertAccount() {//mainMenu중 3번 회원가입 UI > 문제사항있음
		boolean flag=true;
		String id = "";
		String type = "";
		String grade = "C";
		int cost = 0;

		while(flag) {
			try {
				System.out.println("1. 요양사");
				System.out.println("2. 고객");
				System.out.println("0. 상위 메뉴로 돌아가기");
				System.out.println("메뉴 선택=>");

				switch(sc.nextInt()) {
				case 1: 
					type = "carer"; 
					cost = 10_000;
					flag=false;
					break;
				case 2:
					type = "customer";
					flag=false;
					break;
				case 0:
					return ; 
					//break;
				default:
					System.out.println("[ERROR] 다시 선택해주세요.");
				}
			}catch(Exception e) {
				//e.printStackTrace();
			}
		}
		//입력을 여기서 다 받고, type 1이면 
		//new Account carer > Account객체를 carer로

//아이디 중복가입방지를 위한 예외처리
//밑에서 저렇게 처리하면 아이디가 이미 있을 때 모든 정보를 또 다시 쳐야함
		while(true) {
			System.out.println("아이디/자격증번호 =>");
			id = scLine.nextLine();
			Account account = manager.selectAccount(id);
			if(account==null)
				break;
			else
				System.out.println("[ERROR] 동일한 아이디가 존재합니다. 다시 입력해주세요.");
		}
		//		System.out.println("신분(1.요양사/2.고객) =>");
		//		String type = scLine.nextLine();		
//위에서 choice로 1이면 요양사, 2이면 고객임을 이미 입력 받음

		System.out.println("비밀번호 =>");
		String password = scLine.nextLine();
		System.out.println("이름 =>");
		String name = scLine.nextLine();
		System.out.println("전화번호 =>");
		String tel = scLine.nextLine();
		System.out.println("지역 =>");
		String location = scLine.nextLine();
		System.out.println("이메일[비밀번호찾기에 사용] =>");
		String email = scLine.nextLine();
		System.out.println("비밀번호 찾기 힌트 =>");
		String hintForPw = scLine.nextLine();

		//		if ((id == null || id.equals("")) || (type == null || type.equals(""))
		//				|| (password == null || password.equals(""))) {
		//			System.out.println("[ERROR] 다시입력해주세요.");
		//		} else {

//위 주석친 코드는 while로 반복해야하지만 예외처리는 일단 배제하기로 했으므로 나중에..			

		// grade랑 cost는 생성자에 들어가면 안되는거 아닌가?
		// cost는 int, grade는 char로 해야하는거 아닌가?
//grade는 초기값이 무조건 C등급, cost는 초기값이 무조건 10000원
//cost는 int가 맞고, grade는 String해도 상관없음 char하면 byte를 아낄 뿐 
//용량이 부족하거나 속도가 중요한 프로그램일 때 최적화가 중요할 때는 char로 쓰는게 맞음
//char를 쓰게되면 홑따옴표 ' ' 를 써야하고 dao에서 statement에 set할때도 귀찮아짐

		boolean result = manager.insertAccount(	new Account(id, type, password, name, tel, location, email,hintForPw, grade, cost));
		if (result)
			System.out.println("[SYSTEM] 계정등록 완료");			
		else 			
			System.out.println("[SYSTEM] 계정등록 실패");		
		
//		 else {
//				System.out.println("******ERROR 같은아이디가 존재합니다.");
//			}
	}

	public void showCarerMenu() { // 요양사 메뉴 선택
		System.out.println("1.예약 확인");
		System.out.println("2.스케줄 등록");
		System.out.println("3.스케줄 수정");
		System.out.println("4.스케줄 삭제");
		System.out.println("5.나의 등급 보기");
		System.out.println("6.내 정보 수정");
		System.out.println("7.지난 스케쥴 보기");		
		System.out.println("8.회원탈퇴");
		//System.out.println("9.프로그램 종료");
		System.out.println("0.로그아웃");
		System.out.println("메뉴 선택 =>");
	}

	//계정정보수정, 계정삭제 는 하나로 합쳐도 될된다.
	
	public void showSchedule() {//[요양사] 스케줄 확인
		ArrayList<Schedule> sList = null;
		System.out.println(manager.getLoginUser().getName()+"님의 스케줄 목록");
		System.out.println("-----------------------------");
		sList = manager.selectSchedule(manager.getLoginUser().getId());

		if(sList.size()<1)
			System.out.println("[SYSTEM] 스케줄 목록이 없습니다. ");
		else {
			int count=1;
			for(Schedule s : sList) {
				if(s.getCustomerName().equals("dummy")){
					System.out.println("[" + count++ +"][예약대기]\n   [업무날짜 : " + s.getDate() +", 업무시간 : "+s.getBeginTime()+"시~"+s.getEndTime()+
							"시, 위치 : "+s.getLocation()+"]");
				}
				else {
					System.out.println("[" + count++ +"][예약완료]\n   [업무날짜 : " + s.getDate() +", 업무시간 : "+s.getBeginTime()
					+"시~"+s.getEndTime()+"시, 위치 : "+s.getLocation() 
					+"]\n   [고객명 : "+s.getCustomerName()+", 고객 연락처 : "+s.getCustomerTel()+"]");
					System.out.println("   [고객이 전한 메세지 : "+s.getMessageFromCustomer()+"]");
				}
			}
		}
	}
	
	public void insertSchedule() {//[요양사] 스케줄 등록
		//날짜 입력 형식을 어떻게 할 것인가?2019-06-04 String형
		//시작시작, 끝나는 시간입력>단순 number인가? >> 0~24까지만받을수있도록 int형
		String carerId = manager.getLoginUser().getId();
		//String carerId = manager.getLoginUser().
		System.out.println("[SYSTEM] 스케줄 등록");
		System.out.println("업무 날짜  (입력 예시[190529]) =>");
		String date = scLine.nextLine();			
		System.out.println("업무 시작 시간 =>");
		int beginTime = sc.nextInt();
		System.out.println("업무 종료 시간 =>");
		int endTime = sc.nextInt();
		System.out.println("업무 지역 =>");
		String location = scLine.nextLine();			
				
		boolean flag = manager.insertSchedule(new Schedule(carerId, date, beginTime, endTime, location));
		if(flag)
			System.out.println("[SYSTEM] 스케줄 등록 성공");
		else
			System.out.println("[SYSTEM] 스케줄 등록 실패");		
	}

	public void updateSchedule() {//[요양사]스케줄 수정
		//수정가능한 리스트들을 보여주고, 그곳에 index를 매겨서 index를 선택하면 수정하도록한다.
		System.out.println("[SYSTEM] 아직 예약되지 않은 스케줄만 수정가능합니다.");
		System.out.println("[SYSTEM] 현재 수정가능한 스케줄(미예약) 목록 ");
		System.out.println("--------------------------------");
		ArrayList<Schedule> sList = null;
		sList = manager.selectCarerUpdatableSchedule(manager.getLoginUser().getId());
		int choice=1;
		if(sList.size()<1) {
			System.out.println("[SYSTEM] 수정가능한 스케줄이 없습니다.");			
		}
		else {
			for(Schedule s:sList) {				
				System.out.println(choice++ +" 번째 "+s);
			}
			System.out.println("수정할 스케줄 번호를 입력하세요(이전메뉴로 돌아가시려면 '0') =>");			
			choice = sc.nextInt();
			if(choice==0)
				return ;
			System.out.println("[SYSTEM] 다음 스케줄을 수정합니다.");
			System.out.println(sList.get(choice-1));
			System.out.println("------------------------------");			
			System.out.println("업무 날짜  (입력 예시[190529]) =>");
			String date = scLine.nextLine();			
			
			System.out.println("업무 시작 시간 =>");
			int beginTime = sc.nextInt();
			System.out.println("업무 종료 시간 =>");
			int endTime = sc.nextInt();
			System.out.println("업무 지역 =>");
			String location = scLine.nextLine();
			Schedule s = new Schedule();
			s.setDate(date);
			s.setBeginTime(beginTime);
			s.setEndTime(endTime);
			s.setLocation(location);
			s.setId(sList.get(choice-1).getId());
						
			boolean flag = manager.updateCarerSchedule(s);
			if(flag)
				System.out.println("[SYSTEM] 스케줄 수정 성공");
			else
				System.out.println("[SYSTEM] 스케줄 수정 실패");	
		}
	}

	public void deleteSchedule() {//[요양사]스케줄 삭제
		// 아직 시간이 지나지 않고, dummy값인 곳을 index로 삭제가능하도록한다.
		System.out.println("[SYSTEM] 아직 예약되지 않은 스케줄만 삭제가능합니다.");
		System.out.println("[SYSTEM] 현재 삭제가능한 스케줄(미예약) 목록 ");
		System.out.println("--------------------------------");
		ArrayList<Schedule> sList = null;
		sList = manager.selectCarerUpdatableSchedule(manager.getLoginUser().getId());
		int choice=1;
		if(sList.size()<1)
			System.out.println("[SYSTEM] 삭제가능한 스케줄이 없습니다.");
		else {
			for(Schedule s:sList) {				
				System.out.println(choice++ +" 번째 "+s);
			}
			System.out.println("삭제할 스케줄 번호를 입력하세요(이전메뉴로 돌아가시려면 '0') =>");			
			choice = sc.nextInt();
			if(choice==0)
				return ;
			System.out.println("[SYSTEM] 다음 스케줄을 삭제합니다.");
			System.out.println(sList.get(choice-1));
			System.out.println("------------------------------");			
			
			String sche_id = sList.get(choice-1).getId();
						
			boolean flag = manager.deleteCarerSchedule(sche_id);
			if(flag)
				System.out.println("[SYSTEM] 스케줄 삭제 성공");
			else
				System.out.println("[SYSTEM] 스케줄 삭제 실패");	
		}
	}

	public void showGrade() {//[요양사]나의 등급 보기
		//현재로그인 중인 아이디를 가져와서 type을 가져와서 매칭? 
		//Account result = manager.searchAccount();
		//System.out.println("현재 등급은"+result.getGrade()+"입니다.");
		//접속해 있는 id를 인식해서 그 값을 가져오게 해야한다.? 아니면 type도 같이 인식해야하나?
		System.out.println("[SYSTEM] "+manager.getLoginUser().getName()+"님의 등급 : " + manager.getLoginUser().getGrade()+", 시급 : "+manager.getLoginUser().getCost());
	}
	
	public void updateAccount() {//[요양사+고객] 계정 정보 수정
		showUpdateAccountMenu();
	}
	
	public void showUpdateAccountMenu() {
		System.out.println("변경할 정보를 선택해주세요.");
		System.out.println("1.비밀번호 변경");
		System.out.println("2.이름 변경");
		System.out.println("3.전화번호 변경");
		System.out.println("4.지역 변경");
		System.out.println("5.이메일 변경");
		System.out.println("6.비밀번호 찾기 힌트 변경");
		System.out.println("0.상위메뉴");
		System.out.println("메뉴선택 =>");
	}
	
	public void showPreviousSchedule(){//[요양사]이전스케쥴보기
		System.out.println(manager.getLoginUser().getName()+"님의 이전 스케줄 목록");
		System.out.println("-----------------------------");
		ArrayList<Schedule> sList = null;
		sList = manager.selectCarerPreviousSchedule(manager.getLoginUser().getId());

		if(sList.size()==0)
			System.out.println("[SYSTEM] 이전 스케줄 목록이 없습니다. ");
		else {
			for(Schedule s : sList) {
				System.out.print("[업무날짜 : " + s.getDate() +", 업무시간 : "+s.getBeginTime()+"시~"+s.getEndTime()+
						"시, 위치 : "+s.getLocation() +", 고객에게 받은 평점 : ");
				if(s.getServiceGrade()==0) {
					System.out.println(" ]");
				}
				else {
					System.out.println(s.getServiceGrade()+"]");
				}
			}
		}
	}
	
	public boolean deleteAccount() {//[요양사+고객] 계정삭제

		System.out.println("[SYSTEM] 계정 삭제");
		System.out.print("비밀번호를 입력하시면 ");
		System.out.println(manager.getLoginUser().getId()+" (" +manager.getLoginUser().getName()
				+") 님의 계정을 삭제합니다.");
		System.out.println("비밀번호 입력 =>");
		String password = scLine.nextLine();
		boolean flag = false;
		if(manager.getLoginUser().getPassword().equals(password)) {
			flag = manager.deleteAccount(manager.getLoginUser().getId());
			if(flag) {
				System.out.println("[SYSTEM] 계정삭제 성공");
				System.out.println("[SYSTEM] 그동안 저희 서비스를 이용해주셔서 감사합니다.");
			}
			else
				System.out.println("[SYSTEM] 계정삭제 실패");
		}
		else
			System.out.println("비밀번호가 맞지 않습니다.");

		return flag;

	}
	public void showCustomerMenu() { //고객 메뉴 선택
		System.out.println("1.예약 확인");
		System.out.println("2.요양사 예약 하기");
		System.out.println("3.예약 취소");
		System.out.println("4.평가 하기");
		System.out.println("5.내 정보 수정");
		System.out.println("6.회원탈퇴");
		//System.out.println("9.프로그램 종료");
		System.out.println("0.로그아웃");
		System.out.println("메뉴 선택=>");
	}
	
	public void showBooking() {//[고객] 예약확인
		// 요양사, 고객 하나로 두고 type에 따라 셀렉트 문을 다르게 처리하는 것인가?
		// 메서드를 두개로 나누자 ~
		//이렇게 짜면 안된다!!
		ArrayList<Schedule> sList = null;
		System.out.println(manager.getLoginUser().getName()+"님의 예약 목록");
		System.out.println("-----------------------------");
		sList = manager.selectBooking();
		
		//날짜 시간 요양사_이름  전번
		
		if(sList.size()==0)
			System.out.println("[SYSTEM] 예약 목록이 없습니다. ");
		else {
			int count=1;
			for(Schedule s : sList) {
				System.out.println("[" + count++ +"][예약날짜 : " + s.getDate() 
				+", 예약시간 : "+s.getBeginTime()+"시~"+s.getEndTime()
				+"시, 요양사이름 : "+s.getCustomerName()
				+", 연락처 : "+s.getCustomerTel()+"]");
			}
		}
	}
	
	public void insertBooking() {//[고객] 요양사 예약하기 > 조건검색
		System.out.println("[SYSTEM] 요양사 예약");
		System.out.println("출장지역=>");
		String location = scLine.nextLine();
		System.out.println("예약날짜 (입력 예시[190529]) =>");
		String date = scLine.nextLine();

		ArrayList<Account> cList = null;
		cList = manager.selectBookingAvailableCarer(location, date);
		int count=1;
		if(cList.size()>0) {
			System.out.println("["+date+"] ["+location+"]지역에서 예약가능한 요양사 목록");
			System.out.println("----------------------------------");
			for(Account carer : cList){
				System.out.println("["+ count++ +"][이름 : " + carer.getName()
						+", 등급 : " + carer.getGrade()
						+", 시급 : " + carer.getCost()
						+"원, 업무시간 : " + carer.getBeginTime() + " ~ "+carer.getEndTime() +"시]");
			}
			System.out.println("예약할 요양사 선택 =>");
			count = sc.nextInt();
			System.out.println("요양사에게 남길 메세지 =>");
			String message = scLine.nextLine();
			boolean flag = manager.insertBooking(cList.get(count-1).getId(), message);
			//원래는 이렇게 쓰면안됨.. cList는 carer들의 리스트
			//이거는 account의 id인데..schedule의 id를 여기다가 저장해뒀기때문에 임시편법..
			if(flag)
				System.out.println("[SYSTEM] 요양사 예약 성공");
			else
				System.out.println("[SYSTEM] 요양사 예약 실패");
		}
		else {
			System.out.println("[SYSTEM] 죄송합니다. 예약가능한 요양사가 없습니다.");
		}
		//지역을 입력하면 그 지역에 존재하며, customer_id에 데이터가 없는 요양사 데이터들만 출력
		
	}

	public void deleteBooking() {//[고객] 예약취소
		//동일한 customer_id가 입력된 테이블 행의 모든 정보를 가져온다. + 요양 서비스가 진행되기 전에 날짜들만 출력
		ArrayList<Schedule> sList = null;
		System.out.println(manager.getLoginUser().getName()+"님의 예약 목록");
		System.out.println("-----------------------------");
		sList = manager.selectBooking();
		//이렇게 짜면 안된다!!
		//날짜 시간 요양사_이름  전번
		
		if(sList.size()==0)
			System.out.println("[SYSTEM] 예약 목록이 없습니다. ");
		else {
			int count=1;
			for(Schedule s : sList) {
				System.out.println("[" + count++ +"][예약날짜 : " + s.getDate() 
				+", 예약시간 : "+s.getBeginTime()+"시~"+s.getEndTime()
				+"시, 요양사이름 : "+s.getCustomerName()
				
				+", 연락처 : "+s.getCustomerTel()+"]");
			}
			System.out.println("취소할 예약 일련번호 선택 =>");
			count = sc.nextInt();
			boolean flag = manager.deleteBooking(sList.get(count-1).getId());
			if(flag)
				System.out.println("[SYSTEM] 예약 취소 성공");
			else
				System.out.println("[SYSTEM] 예약 취소 실패");
		}
	}

	public void evaluateCarer() { //[고객] 요양사 평가
		// 이미 서비스를 진행했던 요양사에 대한 평가
		// 현재 로그인 아이디의 정보를 이용해 DB에서 예약 내역 + 날짜에 조건을 걸어서 출력
		System.out.println("[SYSTEM] 요양사 평가");
		System.out.println("아직 평가하지 않은 요양사 목록");
		System.out.println("--------------------------------");
		ArrayList<Schedule> sList = null;
		sList = manager.selectEvaluableBooking();
		if(sList.size()==0)
			System.out.println("[SYSTEM] 평가할 요양사 목록이 없습니다. ");
		else {
			int count=1;
			for(Schedule s : sList) {
				System.out.println("[" + count++ +"][출장날짜 : " + s.getDate()
				+", 출장시간 : " + s.getBeginTime()+"~"+s.getEndTime()
				+"시, 요양사이름 : "+s.getCustomerName()+" ]");
			}
			System.out.println("평가할 요양사를 선택하세요 =>");
			count = sc.nextInt();
			System.out.println("점수를 입력하세요(1~5) => ");
			double score = sc.nextDouble();
			boolean flag = manager.evaluateCarer(sList.get(count-1).getCustomerId(), score);
			if(flag)
				System.out.println("[SYSTEM] 요양사 평가 성공");
			else
				System.out.println("[SYSTEM] 요양사 평가 실패");
		}
	}
}