package org.example.ebankingbackend.dtos;

import lombok.Data;

import java.util.List;
@Data
public class AccountHistoryDTO {

    private String accountId;
    private double balance;
    private int currrentPage;
    private int totalPages;
    private int pageSize;

    private List<AccountOperationDTO> accountOperationDTOS;
}
