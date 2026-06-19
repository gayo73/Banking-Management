package controller;

import java.util.Scanner;

import lombok.AllArgsConstructor;
import service.AccountService;
import view.AccountView;

public class AccountController {
	private final AccountService service;
	private final AccountView view;
	private final Scanner sc;
	
	public AccountController(AccountService service, AccountView view) {
		super();
		this.service = service;
		this.view = view;
		this.sc = new Scanner(System.in);
	}
	
	// 앱 진입
	public void run() {
		while(true) {
			view.printMenu();
			int choice = inputInt("선택: ");
			
			if(choice==0) {
				view.printMessage("프로그램을 종료합니다.");
				break;
			}
			handleMenu(choice);
		}
	}

	// 메뉴 번호에 따라 기능 메서드 호출
	private void handleMenu(int choice) {
		try {
			switch(choice) {
				case 1 -> openAccount();
				case 2 -> listAccounts();
				case 3 -> getAccount();
				case 4 -> deposit();
				case 5 -> withdraw();
				case 6 -> transfer();
				case 7 -> closeAccount();
				default -> view.printMessage("존재하지 않는 메뉴입니다.");
			}
		} catch (Exception ex) {
			view.printError(ex.getMessage());
		}
	}
	
	// 1. 계좌 개설
	private void openAccount() {
		String name   = inputString("고객명: ");
        long balance  = inputLong("초기 잔액: ");
 
        String accountNumber = service.openAccount(name, balance);
        view.printMessage("계좌가 개설되었습니다. 계좌번호: " + accountNumber);
	}

	// 2. 전체 계좌 조회
	private void listAccounts() {
		view.printAccounts(service.findAll());
	}

	// 3. 단건 조회
	private void getAccount() {
		String accountNumber = inputString("계좌번호: ");
        view.printAccount(service.findById(accountNumber));
	}

	// 4. 입금
	private void deposit() {
		String accountNumber = inputString("계좌번호: ");
        long amount          = inputLong("입금액: ");
 
        service.deposit(accountNumber, amount);
        view.printMessage("입금이 완료되었습니다.")
	}

	// 5. 출금
	private void withdraw() {
		String accountNumber = inputString("계좌번호: ");
        long amount          = inputLong("출금액: ");
 
        service.withdraw(accountNumber, amount);
        view.printMessage("출금이 완료되었습니다.");
	}

	// 6. 이체
	private void transfer() {
		String fromAccount = inputString("출금 계좌번호: ");
		String toAccount = inputString("입금 계좌번호: ");
		long amount = inputLong("이체 금액: ");
		
		service.transfer(fromAccount, toAccount, amount);
		view.printMessage("이체가 완료되었습니다.");
	}

	// 7. 계좌 해지
	private void closeAccount() {
		String accountNumber = inputString("해지할 계좌번호: ");
		service.closeAccount(accountNumber);
		view.printMessage("계좌가 해지되었습니다.");
	}

	
	// 입력 헬퍼 메서드
	
	private String inputString(String string) {
		System.out.print(string);
        return sc.nextLine().trim();
	}

	private int inputInt(String string) {
		System.out.print(string);
        try {
            int val = Integer.parseInt(sc.nextLine().trim());
            return val;
        } catch (NumberFormatException e) {
            return -1; // handleMenu에서 default 처리
        }
	}
	
	private long inputLong(String prompt) {
        System.out.print(prompt);
        try {
            return Long.parseLong(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자를 입력해주세요.");
        }
    }
}
