package view;

import java.util.ArrayList;
import java.util.Scanner;

import controller.Controller;
import model.Account;
import model.Schedule;

public class UI {
	private Controller manager;
	private Scanner sc; // int ���� ������ Ÿ�� �Է� �ޱ�
	private Scanner scLine; //String ���ڿ� �Է� �ޱ�

	public UI() { //������

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
			case 1://�α���
				int mode = loginAccount(); //�����϶��� �Ϲ� ȸ���϶��� ����
				switch(mode) {
				case 1: // ���� �޴� ���
					while(carerFlag) {
						System.out.println("[SYSTEM] <"+manager.getLoginUser().getName()+" ����� ������>");
						System.out.println("------------------------");
						showCarerMenu();
						switch(sc.nextInt()) {
						case 1: //����Ȯ��
							//confirmBooking();
							showSchedule();
							break;
						case 2://������ ���
							insertSchedule();
							break;
						case 3://������ ����
							updateSchedule();
							break;
						case 4://������ ����
							deleteSchedule();
							break;
						case 5://���� ��� ����
							showGrade();
							break;
						case 6://�� ���� ����
							updateAccount();
							break;
						case 7://���������ٺ���
							showPreviousSchedule();
							break;
						case 8://ȸ��Ż��
							if(!deleteAccount())
								break;
						//case 9://����޴�����
						case 0: //logout
							logout();
							carerFlag=false;
							break;
						default:
							System.out.println("[ERROR] �������� �ʴ� �޴��Դϴ�.");
						}
					}
					break;
				case 2: // �� �޴� ���
					while(customerFlag) {
						System.out.println("[SYSTEM] <"+ manager.getLoginUser().getName()+" ���� ������>");
						System.out.println("------------------------");
						showCustomerMenu();
						switch(sc.nextInt()){
						case 1://����Ȯ��
							showBooking();
							break;
						case 2://���� ���� �ϱ�
							insertBooking();
							break;
						case 3://�������
							deleteBooking();
							break;
						case 4://���ϱ�
							evaluateCarer();
							break;
						case 5://�� ���� ����
							updateAccount();
							break;
						case 6://ȸ��Ż��
							if(!deleteAccount())
								break;
						//case 9://���α׷�����
						case 0: //logout
							logout();
							customerFlag=false;
							break;
						default:
							System.out.println("[ERROR] �������� �ʴ� �޴��Դϴ�.");
						}
					}
					break;
				case 0: // ���� �޴� ���
					//mainMenu();
//mainMenu ����� 2�� �ǰԵȴ�.			
					//mode�� 0�̶�� ���� �α��ο� �����ߴٴ� ���
					//�����޴������ �ƴ϶� ���� switch���� ���������� �ȴ�
					break;
				}
				break;
			case 2: //���� ã��
				findAccount();
				break;
			case 3: //ȸ������
				insertAccount();
				break;
			case 0: // ���α׷� ����
				//accountFlag = false;
				System.exit(0);
			default : //�߸��� �޴� �Է�
				System.out.println("[ERROR] �������� �ʴ� �޴��Դϴ�.");
			}
		}
	}
	public void mainMenu() {
		System.out.println("===============");
		System.out.println("[���翹�����α׷�]");
		System.out.println("===============");
		System.out.println("1. �α���");
		System.out.println("2. ���� ã��");
		System.out.println("3. ȸ������");
		System.out.println("0. ���α׷� ����");
		System.out.println("---------------");
		System.out.print("�޴� ����=>");
	}

	public int loginAccount() { //mainMenu�� 1��  �α��� UI
		int mode = 0;
		//while(true) {
			String id = null;
			String password = null;
			System.out.println("���̵�=>");
			id = scLine.nextLine();
			System.out.println("��й�ȣ=>");
			password = scLine.nextLine();
			if ((id == null || id.equals("")) || (password == null || password.equals(""))) {
				System.out.println("[ERROR] �ٽ��Է����ּ���.");
			} 
			else {
				//Account result = manager.loginAccount(new Account(null, id, password));
				boolean result = manager.loginAccount(id, password);
				if (result) {
					//System.out.println("�α��� ����!\n" + manager.getId() + "(" + manager.getName() + "��) ȯ���մϴ�.");
					System.out.println("[SYSTEM] �α��� ����!\n[SYSTEM] "+ manager.getLoginUser().getId() + "(" + manager.getLoginUser().getName() + "��) ȯ���մϴ�.");
					
					if(manager.getLoginUser().getType().equals("carer")) 
						mode = 1;
					else 
						mode = 2;
					//break;
				} else {
					System.out.println("[ERROR] ���̵�/��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
				}
			}
		//}
		return mode;
	}
	
	public void logout() {//[����+��] �α׾ƿ�
		if(manager.logout())
			System.out.println("[SYSTEM] �α׾ƿ� ����\n[SYSTEM] ���� �޴��� ���ư��ϴ�.");		
	}

	public void findAccount(){//mainMenu�� 2�� ���� ã��UI
		
		while(true) {
			System.out.print("ȸ�� ���Խ� �ۼ��� �̸����� �Է����ּ���\n=>");
			//�̸����� �ԷµǸ� ���̵�� ��Ʈ�� �˷��ش�.
			String email = scLine.nextLine();
			//			if( email == null ) {
			//				System.out.println("[ERROR] �ٽ� �Է����ּ���.");
			//			}else {
//����ó���� ���߿�
//email�� �ߺ������ؾ��ϳ�........���� �о�~~

			Account account = manager.findAccount(email);
			boolean flag=false;
			
			if(account != null) {
				//System.out.println("���̵�:"+manager.getID()+"�Դϴ�.");
				//System.out.println("��й�ȣ��Ʈ:"+manager.getHintForPw());
				System.out.println("���̵� : "+account.getId());
				System.out.println("��й�ȣ ��Ʈ : "+account.getHintForPw());
				return ;
			}
			else {
				flag=true;
				System.out.println("[ERROR] �������� �ʴ� �����Դϴ�.");
			}
			
			while(flag) {
				System.out.println("1. �ٽ� �Է��ϱ�");
				System.out.println("2. �����޴��� ���ư���");
				System.out.println("---------------");
				System.out.print("�޴� ����=>");
				switch(sc.nextInt()){
				case 1:
					flag=false;
					break;
				case 2:
					return ; 
				default : 
					System.out.println("[ERROR] �������� �ʴ� �޴��Դϴ�.");
				}
			}
		}
	}

	public void insertAccount() {//mainMenu�� 3�� ȸ������ UI > ������������
		boolean flag=true;
		String id = "";
		String type = "";
		String grade = "C";
		int cost = 0;

		while(flag) {
			try {
				System.out.println("1. ����");
				System.out.println("2. ��");
				System.out.println("0. ���� �޴��� ���ư���");
				System.out.println("�޴� ����=>");

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
					System.out.println("[ERROR] �ٽ� �������ּ���.");
				}
			}catch(Exception e) {
				//e.printStackTrace();
			}
		}
		//�Է��� ���⼭ �� �ް�, type 1�̸� 
		//new Account carer > Account��ü�� carer��

//���̵� �ߺ����Թ����� ���� ����ó��
//�ؿ��� ������ ó���ϸ� ���̵� �̹� ���� �� ��� ������ �� �ٽ� �ľ���
		while(true) {
			System.out.println("���̵�/�ڰ�����ȣ =>");
			id = scLine.nextLine();
			Account account = manager.selectAccount(id);
			if(account==null)
				break;
			else
				System.out.println("[ERROR] ������ ���̵� �����մϴ�. �ٽ� �Է����ּ���.");
		}
		//		System.out.println("�ź�(1.����/2.��) =>");
		//		String type = scLine.nextLine();		
//������ choice�� 1�̸� ����, 2�̸� ������ �̹� �Է� ����

		System.out.println("��й�ȣ =>");
		String password = scLine.nextLine();
		System.out.println("�̸� =>");
		String name = scLine.nextLine();
		System.out.println("��ȭ��ȣ =>");
		String tel = scLine.nextLine();
		System.out.println("���� =>");
		String location = scLine.nextLine();
		System.out.println("�̸���[��й�ȣã�⿡ ���] =>");
		String email = scLine.nextLine();
		System.out.println("��й�ȣ ã�� ��Ʈ =>");
		String hintForPw = scLine.nextLine();

		//		if ((id == null || id.equals("")) || (type == null || type.equals(""))
		//				|| (password == null || password.equals(""))) {
		//			System.out.println("[ERROR] �ٽ��Է����ּ���.");
		//		} else {

//�� �ּ�ģ �ڵ�� while�� �ݺ��ؾ������� ����ó���� �ϴ� �����ϱ�� �����Ƿ� ���߿�..			

		// grade�� cost�� �����ڿ� ���� �ȵǴ°� �ƴѰ�?
		// cost�� int, grade�� char�� �ؾ��ϴ°� �ƴѰ�?
//grade�� �ʱⰪ�� ������ C���, cost�� �ʱⰪ�� ������ 10000��
//cost�� int�� �°�, grade�� String�ص� ������� char�ϸ� byte�� �Ƴ� �� 
//�뷮�� �����ϰų� �ӵ��� �߿��� ���α׷��� �� ����ȭ�� �߿��� ���� char�� ���°� ����
//char�� ���ԵǸ� Ȭ����ǥ ' ' �� ����ϰ� dao���� statement�� set�Ҷ��� ��������

		boolean result = manager.insertAccount(	new Account(id, type, password, name, tel, location, email,hintForPw, grade, cost));
		if (result)
			System.out.println("[SYSTEM] ������� �Ϸ�");			
		else 			
			System.out.println("[SYSTEM] ������� ����");		
		
//		 else {
//				System.out.println("******ERROR �������̵� �����մϴ�.");
//			}
	}

	public void showCarerMenu() { // ���� �޴� ����
		System.out.println("1.���� Ȯ��");
		System.out.println("2.������ ���");
		System.out.println("3.������ ����");
		System.out.println("4.������ ����");
		System.out.println("5.���� ��� ����");
		System.out.println("6.�� ���� ����");
		System.out.println("7.���� ������ ����");		
		System.out.println("8.ȸ��Ż��");
		//System.out.println("9.���α׷� ����");
		System.out.println("0.�α׾ƿ�");
		System.out.println("�޴� ���� =>");
	}

	//������������, �������� �� �ϳ��� ���ĵ� �ɵȴ�.
	
	public void showSchedule() {//[����] ������ Ȯ��
		ArrayList<Schedule> sList = null;
		System.out.println(manager.getLoginUser().getName()+"���� ������ ���");
		System.out.println("-----------------------------");
		sList = manager.selectSchedule(manager.getLoginUser().getId());

		if(sList.size()<1)
			System.out.println("[SYSTEM] ������ ����� �����ϴ�. ");
		else {
			int count=1;
			for(Schedule s : sList) {
				if(s.getCustomerName().equals("dummy")){
					System.out.println("[" + count++ +"][������]\n   [������¥ : " + s.getDate() +", �����ð� : "+s.getBeginTime()+"��~"+s.getEndTime()+
							"��, ��ġ : "+s.getLocation()+"]");
				}
				else {
					System.out.println("[" + count++ +"][����Ϸ�]\n   [������¥ : " + s.getDate() +", �����ð� : "+s.getBeginTime()
					+"��~"+s.getEndTime()+"��, ��ġ : "+s.getLocation() 
					+"]\n   [���� : "+s.getCustomerName()+", �� ����ó : "+s.getCustomerTel()+"]");
					System.out.println("   [���� ���� �޼��� : "+s.getMessageFromCustomer()+"]");
				}
			}
		}
	}
	
	public void insertSchedule() {//[����] ������ ���
		//��¥ �Է� ������ ��� �� ���ΰ�?2019-06-04 String��
		//���۽���, ������ �ð��Է�>�ܼ� number�ΰ�? >> 0~24�������������ֵ��� int��
		String carerId = manager.getLoginUser().getId();
		//String carerId = manager.getLoginUser().
		System.out.println("[SYSTEM] ������ ���");
		System.out.println("���� ��¥  (�Է� ����[190529]) =>");
		String date = scLine.nextLine();			
		System.out.println("���� ���� �ð� =>");
		int beginTime = sc.nextInt();
		System.out.println("���� ���� �ð� =>");
		int endTime = sc.nextInt();
		System.out.println("���� ���� =>");
		String location = scLine.nextLine();			
				
		boolean flag = manager.insertSchedule(new Schedule(carerId, date, beginTime, endTime, location));
		if(flag)
			System.out.println("[SYSTEM] ������ ��� ����");
		else
			System.out.println("[SYSTEM] ������ ��� ����");		
	}

	public void updateSchedule() {//[����]������ ����
		//���������� ����Ʈ���� �����ְ�, �װ��� index�� �Űܼ� index�� �����ϸ� �����ϵ����Ѵ�.
		System.out.println("[SYSTEM] ���� ������� ���� �����ٸ� ���������մϴ�.");
		System.out.println("[SYSTEM] ���� ���������� ������(�̿���) ��� ");
		System.out.println("--------------------------------");
		ArrayList<Schedule> sList = null;
		sList = manager.selectCarerUpdatableSchedule(manager.getLoginUser().getId());
		int choice=1;
		if(sList.size()<1) {
			System.out.println("[SYSTEM] ���������� �������� �����ϴ�.");			
		}
		else {
			for(Schedule s:sList) {				
				System.out.println(choice++ +" ��° "+s);
			}
			System.out.println("������ ������ ��ȣ�� �Է��ϼ���(�����޴��� ���ư��÷��� '0') =>");			
			choice = sc.nextInt();
			if(choice==0)
				return ;
			System.out.println("[SYSTEM] ���� �������� �����մϴ�.");
			System.out.println(sList.get(choice-1));
			System.out.println("------------------------------");			
			System.out.println("���� ��¥  (�Է� ����[190529]) =>");
			String date = scLine.nextLine();			
			
			System.out.println("���� ���� �ð� =>");
			int beginTime = sc.nextInt();
			System.out.println("���� ���� �ð� =>");
			int endTime = sc.nextInt();
			System.out.println("���� ���� =>");
			String location = scLine.nextLine();
			Schedule s = new Schedule();
			s.setDate(date);
			s.setBeginTime(beginTime);
			s.setEndTime(endTime);
			s.setLocation(location);
			s.setId(sList.get(choice-1).getId());
						
			boolean flag = manager.updateCarerSchedule(s);
			if(flag)
				System.out.println("[SYSTEM] ������ ���� ����");
			else
				System.out.println("[SYSTEM] ������ ���� ����");	
		}
	}

	public void deleteSchedule() {//[����]������ ����
		// ���� �ð��� ������ �ʰ�, dummy���� ���� index�� ���������ϵ����Ѵ�.
		System.out.println("[SYSTEM] ���� ������� ���� �����ٸ� ���������մϴ�.");
		System.out.println("[SYSTEM] ���� ���������� ������(�̿���) ��� ");
		System.out.println("--------------------------------");
		ArrayList<Schedule> sList = null;
		sList = manager.selectCarerUpdatableSchedule(manager.getLoginUser().getId());
		int choice=1;
		if(sList.size()<1)
			System.out.println("[SYSTEM] ���������� �������� �����ϴ�.");
		else {
			for(Schedule s:sList) {				
				System.out.println(choice++ +" ��° "+s);
			}
			System.out.println("������ ������ ��ȣ�� �Է��ϼ���(�����޴��� ���ư��÷��� '0') =>");			
			choice = sc.nextInt();
			if(choice==0)
				return ;
			System.out.println("[SYSTEM] ���� �������� �����մϴ�.");
			System.out.println(sList.get(choice-1));
			System.out.println("------------------------------");			
			
			String sche_id = sList.get(choice-1).getId();
						
			boolean flag = manager.deleteCarerSchedule(sche_id);
			if(flag)
				System.out.println("[SYSTEM] ������ ���� ����");
			else
				System.out.println("[SYSTEM] ������ ���� ����");	
		}
	}

	public void showGrade() {//[����]���� ��� ����
		//����α��� ���� ���̵� �����ͼ� type�� �����ͼ� ��Ī? 
		//Account result = manager.searchAccount();
		//System.out.println("���� �����"+result.getGrade()+"�Դϴ�.");
		//������ �ִ� id�� �ν��ؼ� �� ���� �������� �ؾ��Ѵ�.? �ƴϸ� type�� ���� �ν��ؾ��ϳ�?
		System.out.println("[SYSTEM] "+manager.getLoginUser().getName()+"���� ��� : " + manager.getLoginUser().getGrade()+", �ñ� : "+manager.getLoginUser().getCost());
	}
	
	public void updateAccount() {//[����+��] ���� ���� ����
		showUpdateAccountMenu();
	}
	
	public void showUpdateAccountMenu() {
		System.out.println("������ ������ �������ּ���.");
		System.out.println("1.��й�ȣ ����");
		System.out.println("2.�̸� ����");
		System.out.println("3.��ȭ��ȣ ����");
		System.out.println("4.���� ����");
		System.out.println("5.�̸��� ����");
		System.out.println("6.��й�ȣ ã�� ��Ʈ ����");
		System.out.println("0.�����޴�");
		System.out.println("�޴����� =>");
	}
	
	public void showPreviousSchedule(){//[����]���������캸��
		System.out.println(manager.getLoginUser().getName()+"���� ���� ������ ���");
		System.out.println("-----------------------------");
		ArrayList<Schedule> sList = null;
		sList = manager.selectCarerPreviousSchedule(manager.getLoginUser().getId());

		if(sList.size()==0)
			System.out.println("[SYSTEM] ���� ������ ����� �����ϴ�. ");
		else {
			for(Schedule s : sList) {
				System.out.print("[������¥ : " + s.getDate() +", �����ð� : "+s.getBeginTime()+"��~"+s.getEndTime()+
						"��, ��ġ : "+s.getLocation() +", ������ ���� ���� : ");
				if(s.getServiceGrade()==0) {
					System.out.println(" ]");
				}
				else {
					System.out.println(s.getServiceGrade()+"]");
				}
			}
		}
	}
	
	public boolean deleteAccount() {//[����+��] ��������

		System.out.println("[SYSTEM] ���� ����");
		System.out.print("��й�ȣ�� �Է��Ͻø� ");
		System.out.println(manager.getLoginUser().getId()+" (" +manager.getLoginUser().getName()
				+") ���� ������ �����մϴ�.");
		System.out.println("��й�ȣ �Է� =>");
		String password = scLine.nextLine();
		boolean flag = false;
		if(manager.getLoginUser().getPassword().equals(password)) {
			flag = manager.deleteAccount(manager.getLoginUser().getId());
			if(flag) {
				System.out.println("[SYSTEM] �������� ����");
				System.out.println("[SYSTEM] �׵��� ���� ���񽺸� �̿����ּż� �����մϴ�.");
			}
			else
				System.out.println("[SYSTEM] �������� ����");
		}
		else
			System.out.println("��й�ȣ�� ���� �ʽ��ϴ�.");

		return flag;

	}
	public void showCustomerMenu() { //�� �޴� ����
		System.out.println("1.���� Ȯ��");
		System.out.println("2.���� ���� �ϱ�");
		System.out.println("3.���� ���");
		System.out.println("4.�� �ϱ�");
		System.out.println("5.�� ���� ����");
		System.out.println("6.ȸ��Ż��");
		//System.out.println("9.���α׷� ����");
		System.out.println("0.�α׾ƿ�");
		System.out.println("�޴� ����=>");
	}
	
	public void showBooking() {//[��] ����Ȯ��
		// ����, �� �ϳ��� �ΰ� type�� ���� ����Ʈ ���� �ٸ��� ó���ϴ� ���ΰ�?
		// �޼��带 �ΰ��� ������ ~
		//�̷��� ¥�� �ȵȴ�!!
		ArrayList<Schedule> sList = null;
		System.out.println(manager.getLoginUser().getName()+"���� ���� ���");
		System.out.println("-----------------------------");
		sList = manager.selectBooking();
		
		//��¥ �ð� ����_�̸�  ����
		
		if(sList.size()==0)
			System.out.println("[SYSTEM] ���� ����� �����ϴ�. ");
		else {
			int count=1;
			for(Schedule s : sList) {
				System.out.println("[" + count++ +"][���೯¥ : " + s.getDate() 
				+", ����ð� : "+s.getBeginTime()+"��~"+s.getEndTime()
				+"��, �����̸� : "+s.getCustomerName()
				+", ����ó : "+s.getCustomerTel()+"]");
			}
		}
	}
	
	public void insertBooking() {//[��] ���� �����ϱ� > ���ǰ˻�
		System.out.println("[SYSTEM] ���� ����");
		System.out.println("��������=>");
		String location = scLine.nextLine();
		System.out.println("���೯¥ (�Է� ����[190529]) =>");
		String date = scLine.nextLine();

		ArrayList<Account> cList = null;
		cList = manager.selectBookingAvailableCarer(location, date);
		int count=1;
		if(cList.size()>0) {
			System.out.println("["+date+"] ["+location+"]�������� ���డ���� ���� ���");
			System.out.println("----------------------------------");
			for(Account carer : cList){
				System.out.println("["+ count++ +"][�̸� : " + carer.getName()
						+", ��� : " + carer.getGrade()
						+", �ñ� : " + carer.getCost()
						+"��, �����ð� : " + carer.getBeginTime() + " ~ "+carer.getEndTime() +"��]");
			}
			System.out.println("������ ���� ���� =>");
			count = sc.nextInt();
			System.out.println("���翡�� ���� �޼��� =>");
			String message = scLine.nextLine();
			boolean flag = manager.insertBooking(cList.get(count-1).getId(), message);
			//������ �̷��� ����ȵ�.. cList�� carer���� ����Ʈ
			//�̰Ŵ� account�� id�ε�..schedule�� id�� ����ٰ� �����صױ⶧���� �ӽ����..
			if(flag)
				System.out.println("[SYSTEM] ���� ���� ����");
			else
				System.out.println("[SYSTEM] ���� ���� ����");
		}
		else {
			System.out.println("[SYSTEM] �˼��մϴ�. ���డ���� ���簡 �����ϴ�.");
		}
		//������ �Է��ϸ� �� ������ �����ϸ�, customer_id�� �����Ͱ� ���� ���� �����͵鸸 ���
		
	}

	public void deleteBooking() {//[��] �������
		//������ customer_id�� �Էµ� ���̺� ���� ��� ������ �����´�. + ��� ���񽺰� ����Ǳ� ���� ��¥�鸸 ���
		ArrayList<Schedule> sList = null;
		System.out.println(manager.getLoginUser().getName()+"���� ���� ���");
		System.out.println("-----------------------------");
		sList = manager.selectBooking();
		//�̷��� ¥�� �ȵȴ�!!
		//��¥ �ð� ����_�̸�  ����
		
		if(sList.size()==0)
			System.out.println("[SYSTEM] ���� ����� �����ϴ�. ");
		else {
			int count=1;
			for(Schedule s : sList) {
				System.out.println("[" + count++ +"][���೯¥ : " + s.getDate() 
				+", ����ð� : "+s.getBeginTime()+"��~"+s.getEndTime()
				+"��, �����̸� : "+s.getCustomerName()
				
				+", ����ó : "+s.getCustomerTel()+"]");
			}
			System.out.println("����� ���� �Ϸù�ȣ ���� =>");
			count = sc.nextInt();
			boolean flag = manager.deleteBooking(sList.get(count-1).getId());
			if(flag)
				System.out.println("[SYSTEM] ���� ��� ����");
			else
				System.out.println("[SYSTEM] ���� ��� ����");
		}
	}

	public void evaluateCarer() { //[��] ���� ��
		// �̹� ���񽺸� �����ߴ� ���翡 ���� ��
		// ���� �α��� ���̵��� ������ �̿��� DB���� ���� ���� + ��¥�� ������ �ɾ ���
		System.out.println("[SYSTEM] ���� ��");
		System.out.println("���� ������ ���� ���� ���");
		System.out.println("--------------------------------");
		ArrayList<Schedule> sList = null;
		sList = manager.selectEvaluableBooking();
		if(sList.size()==0)
			System.out.println("[SYSTEM] ���� ���� ����� �����ϴ�. ");
		else {
			int count=1;
			for(Schedule s : sList) {
				System.out.println("[" + count++ +"][���峯¥ : " + s.getDate()
				+", ����ð� : " + s.getBeginTime()+"~"+s.getEndTime()
				+"��, �����̸� : "+s.getCustomerName()+" ]");
			}
			System.out.println("���� ���縦 �����ϼ��� =>");
			count = sc.nextInt();
			System.out.println("������ �Է��ϼ���(1~5) => ");
			double score = sc.nextDouble();
			boolean flag = manager.evaluateCarer(sList.get(count-1).getCustomerId(), score);
			if(flag)
				System.out.println("[SYSTEM] ���� �� ����");
			else
				System.out.println("[SYSTEM] ���� �� ����");
		}
	}
}