package org.example.ebankingbackend.dtos;

import lombok.Data;
import org.example.ebankingbackend.enums.AccountStatus;

import java.util.Date;

@Data
public class CurrentBankAccountDTO {
    private String id ;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private String currency;
    private CustomerDTO customer;
    private double overdraft;
}
