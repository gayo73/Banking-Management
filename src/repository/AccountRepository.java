package repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Account;

public class AccountRepository {

    private final List<Account> accounts = new ArrayList<>();

    public void save(Account account) {
        accounts.add(account);
    }

    public List<Account> findAll() {
        return accounts;
    }
    //한건 조회
    public Optional<Account> findById(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return Optional.of(account);
            }
        }
        return Optional.empty();
    }

    //계좌 해지
    public boolean delete(String accountNumber) {
        return accounts.removeIf(account -> account.getAccountNumber().equals(accountNumber));
    }
}