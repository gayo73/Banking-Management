package view;

import java.util.List;

import model.Account;

public class AccountView {

	// 메인 메뉴 출력
	public void printMenu() {
		System.out.println("=============================");
		System.out.println("    은행 계좌 관리 시스템 ");
		System.out.println("=============================");
		System.err.println("  1. 계좌 개설");
		System.out.println("  2. 전체 계좌 조회");
		System.out.println("  3. 계좌 단건 조회");
		System.out.println("  4. 입금");
		System.out.println("  5. 출금");
		System.out.println("  6. 이체");
		System.out.println("  7. 계좌 해지");
		System.out.println("  0. 종료");
		System.out.println("======================================");
	}

	
	// 전체 계좌 목록 출력
	public void printAccounts(List<Account> accounts) {
		System.out.println("=======================================");
		System.out.printf(" %-15s %-10s %15s%n", "계좌번호", "고객명", "잔액 (원)");
		System.out.println("---------------------------------------");
		
		if(accounts.isEmpty() || accounts==null) {
			System.out.println(" 등록된 계좌가 없습니다.");
		} else {
			for(Account account:accounts) {
				System.out.printf("  %-15s %-10s %,15d%n",
						account.getAccountNumber(),
						account.getCustomerName(),
						account.getBalance());
			}
			System.out.println("---------------------------------------");
			System.out.printf("  총 %d건%n", accounts.size());
		}
		System.out.println("=======================================");
	}


	// 단건 계좌 상세 출력
	public void printAccount(Account acc) {
		if(acc==null) {
			printError("존재하지 않는 계좌번호입니다.");
			return;
		}
		System.out.println("=======================================");
		System.out.println(" [계좌 상세 정보]");
		System.out.printf(" 계좌번호 : %s%n", acc.getAccountNumber());
		System.out.printf(" 고객명 : %s%n", acc.getCustomerName());
		System.out.printf(" 잔액 : %,d 원%n", acc.getBalance());
		
		System.out.println("=======================================");
	}
	
	
	// 공통 메세지 출력
	
	public void printMessage(String message) {
		System.out.println(" [O]" + message);
	}

	public void printError(String message) {
		System.out.println(" [X]" + message);
	}
}
