package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Setter@Getter@ToString
@Builder
public class Account {
    private static int sequence = 1000;  // 계좌번호 자동 발급용 시퀀스(static)

    private String accountNumber;  // 계좌번호 (자동 발급)
    private String customerName;   // 고객명
    private long balance; //잔액
}