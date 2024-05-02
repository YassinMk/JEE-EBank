package org.example.ebankingbackend.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.ebankingbackend.entities.AccountOperation;
import org.example.ebankingbackend.entities.Customer;
import org.example.ebankingbackend.enums.AccountStatus;

import java.util.Date;
import java.util.List;
@Data
public class SavingBankAccountDTO {
        private String id ;
        private double balance;
        private Date createdAt;
        private AccountStatus status;
        private String currency;
        private CustomerDTO customer;
        private double interestRate;
}

