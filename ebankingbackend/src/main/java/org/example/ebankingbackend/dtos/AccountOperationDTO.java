package org.example.ebankingbackend.dtos;

import jakarta.persistence.*;
import lombok.Data;
import org.example.ebankingbackend.entities.BankAccount;
import org.example.ebankingbackend.enums.OperationType;

import java.util.Date;
@Data

public class AccountOperationDTO {

    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
    private String description;
}
