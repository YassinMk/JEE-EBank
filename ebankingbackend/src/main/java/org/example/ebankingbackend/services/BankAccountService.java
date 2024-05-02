package org.example.ebankingbackend.services;

import org.example.ebankingbackend.dtos.CustomerDTO;
import org.example.ebankingbackend.entities.BankAccount;
import org.example.ebankingbackend.entities.CurrentAccount;
import org.example.ebankingbackend.entities.Customer;
import org.example.ebankingbackend.entities.SavingAccount;
import org.example.ebankingbackend.exceptions.BalanceNotSufficientException;
import org.example.ebankingbackend.exceptions.BankAccountNotFoundException;
import org.example.ebankingbackend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    SavingAccount saveCurrentBankAccount(double initialBalance, double interestRate, Long customerId);
    CurrentAccount saveSavingBankAccount(double initialBalance, double overDraft, Long customerId);
    List<CustomerDTO> listCustomers();
    BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId, double amount , String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId, double amount , String description) throws BankAccountNotFoundException;
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;


    List<BankAccount> bankAccountList();

    CustomerDTO getCustomer(Long id);

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long id) throws CustomerNotFoundException;
}
