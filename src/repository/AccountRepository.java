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
}