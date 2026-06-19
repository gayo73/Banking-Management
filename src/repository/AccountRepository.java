package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import util.DBConnection;

import model.Account;

public class AccountRepository {
	
    // 계좌 저장 + DB 시퀀스로 계좌번호 발급
    public void save(Account account) {
    	
        String seqSql = "SELECT 'AC-' || ACCOUNT_SEQ.NEXTVAL AS ACC_NO FROM DUAL";
        String insertSql = "INSERT INTO ACCOUNT (ACCOUNT_NUMBER, CUSTOMER_NAME, BALANCE) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            // 1. 시퀀스로 계좌번호 생성
            String accountNumber;
            try (PreparedStatement seqPstmt = conn.prepareStatement(seqSql);
                 ResultSet rs = seqPstmt.executeQuery()) {
                rs.next();
                accountNumber = rs.getString("ACC_NO");
            }
            account.setAccountNumber(accountNumber);

            // 2. 계좌 INSERT
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                pstmt.setString(1, account.getAccountNumber());
                pstmt.setString(2, account.getCustomerName());
                pstmt.setLong(3, account.getBalance());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            throw new RuntimeException("계좌 저장 중 오류 발생", e);
        }
    }

    // 전체 조회
    public List<Account> findAll() {
        String sql = "SELECT ACCOUNT_NUMBER, CUSTOMER_NAME, BALANCE FROM ACCOUNT";
        List<Account> accounts = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                accounts.add(mapRow(rs));
            }
        } catch (Exception e) {
            throw new RuntimeException("전체 조회 중 오류 발생", e);
        }
        return accounts;
    }

    // 단건 조회
    public Optional<Account> findById(String accountNumber) {
        String sql = "SELECT ACCOUNT_NUMBER, CUSTOMER_NAME, BALANCE FROM ACCOUNT WHERE ACCOUNT_NUMBER = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, accountNumber);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new RuntimeException("단건 조회 중 오류 발생", e);
        }
    }

    // 잔액 변경 반영 (입금/출금/이체에서 필요)
    public void update(Account account) {
        String sql = "UPDATE ACCOUNT SET BALANCE = ? WHERE ACCOUNT_NUMBER = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, account.getBalance());
            pstmt.setString(2, account.getAccountNumber());

            int rows = pstmt.executeUpdate();
            if (rows == 0) {
                throw new NoSuchElementException("존재하지 않는 계좌입니다: " + account.getAccountNumber());
            }
        } catch (Exception e) {
            throw new RuntimeException("잔액 업데이트 중 오류 발생", e);
        }
    }

    // 계좌 해지
    public boolean delete(String accountNumber) {
        String sql = "DELETE FROM ACCOUNT WHERE ACCOUNT_NUMBER = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, accountNumber);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException("계좌 삭제 중 오류 발생", e);
        }
    }

    private Account mapRow(ResultSet rs) throws Exception {
        return Account.builder()
                .accountNumber(rs.getString("ACCOUNT_NUMBER"))
                .customerName(rs.getString("CUSTOMER_NAME"))
                .balance(rs.getLong("BALANCE"))
                .build();
    }
}