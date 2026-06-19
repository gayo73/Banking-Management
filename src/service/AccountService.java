package service;

import java.util.List;
import java.util.NoSuchElementException;

import model.Account;
import repository.AccountRepository;

public class AccountService {
    private final AccountRepository repository;
    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    // 1. 계좌 개설
    public String openAccount(String customerName, long initialBalance) {
        if (customerName == null || customerName.isBlank()) {
            throw new IllegalArgumentException("고객명을 입력해주세요.");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("초기 잔액은 0 이상이어야 합니다.");
        }

        Account account = Account.create(customerName, initialBalance);
        repository.save(account);
        return account.getAccountNumber();
    }

    // 2. 전체 계좌 조회
    public List<Account> findAll() {
        return repository.findAll();
    }

    // 3. 단건 조회
    public Account findById(String accountNumber) {
        return repository.findById(accountNumber)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 계좌입니다: " + accountNumber));
    }

    // 4. 입금
    public void deposit(String accountNumber, long amount) {
        validateAmount(amount);
        Account account = findById(accountNumber);
        repository.update(accountNumber, account.getBalance() + amount);
    }

    // 5. 출금
    public void withdraw(String accountNumber, long amount) {
        validateAmount(amount);
        Account account = findById(accountNumber);

        if (account.getBalance() < amount) {
            throw new IllegalStateException("잔액이 부족합니다. 현재 잔액: " + account.getBalance());
        }
        repository.update(accountNumber, account.getBalance() - amount);
    }

    // 6. 이체
    public void transfer(String fromAccountNumber, String toAccountNumber, long amount) {
        if (fromAccountNumber.equals(toAccountNumber)) {
            throw new IllegalArgumentException("출금 계좌와 입금 계좌가 동일합니다.");
        }
        findById(toAccountNumber); // 입금 계좌 존재 여부 미리 체크

        withdraw(fromAccountNumber, amount);
        deposit(toAccountNumber, amount);
    }

    // 7. 계좌 해지
    public void closeAccount(String accountNumber) {
        Account account = findById(accountNumber);

        if (account.getBalance() != 0) {
            throw new IllegalStateException("잔액이 남아있어 해지할 수 없습니다. 현재 잔액: " + account.getBalance());
        }
        repository.delete(accountNumber);
    }

    private void validateAmount(long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("금액은 0보다 커야 합니다.");
        }
    }
}
