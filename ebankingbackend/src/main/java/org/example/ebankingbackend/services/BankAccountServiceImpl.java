package org.example.ebankingbackend.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ebankingbackend.dtos.CustomerDTO;
import org.example.ebankingbackend.entities.*;
import org.example.ebankingbackend.enums.OperationType;
import org.example.ebankingbackend.exceptions.BalanceNotSufficientException;
import org.example.ebankingbackend.exceptions.BankAccountNotFoundException;
import org.example.ebankingbackend.exceptions.CustomerNotFoundException;
import org.example.ebankingbackend.mappers.BankAccountMapperImpl;
import org.example.ebankingbackend.repositories.AccountOperationRepository;
import org.example.ebankingbackend.repositories.BankAccountRepository;
import org.example.ebankingbackend.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService{
    private CustomerRepository customerRepository;
    private BankAccountRepository BankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImpl dtoMapper;
    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("Saving customer");
        Customer customer = dtoMapper.toCustomer(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }

    @Override
    public SavingAccount saveCurrentBankAccount(double initialBalance, double interestRate, Long customerId)throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null){
            throw new CustomerNotFoundException("Customer not found");
        }
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setBalance(initialBalance);
        savingAccount.setCreatedAt(new Date());
        savingAccount.setInterestRate(interestRate);
        savingAccount.setCustomer(customer);
        savingAccount.setCurrency("DH");
        SavingAccount saveBankAccount= BankAccountRepository.save(savingAccount);
        return saveBankAccount;
    }

    @Override
    public CurrentAccount saveSavingBankAccount(double initialBalance, double overDraft, Long customerId)throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null){
            throw new CustomerNotFoundException("Customer not found");
        }
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setBalance(initialBalance);
        currentAccount.setCreatedAt(new Date());
        currentAccount.setOverdraft(overDraft);
        currentAccount.setCurrency("DH");
        currentAccount.setCustomer(customer);
        CurrentAccount saveBankAccount = BankAccountRepository.save(currentAccount);
        return saveBankAccount;
    }


    @Override
    public List<CustomerDTO> listCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> customerDTOS = customers.stream().map(cust->dtoMapper.fromCustomer(cust)).collect(Collectors.toList());
        return customerDTOS;
    }

    @Override
    public BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException{
        BankAccount bankAccount = BankAccountRepository.findById(accountId).orElseThrow(
                ()->new BankAccountNotFoundException("Bank Account not found")
        );
        return bankAccount;
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount = getBankAccount(accountId);
        if (bankAccount.getBalance() < amount) {
            throw new BalanceNotSufficientException("Insufficient balance");
        }
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperation.setDescription(description);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        BankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount = getBankAccount(accountId);

        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperation.setDescription(description);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        BankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {
        debit(accountIdSource, amount, "Transfer to "+accountIdDestination);
        credit(accountIdDestination, amount, "Transfer from "+accountIdSource);
    }
    @Override
    public List<BankAccount> bankAccountList(){
        return BankAccountRepository.findAll();
    }
    @Override
    public CustomerDTO getCustomer(Long id) throws CustomerNotFoundException{
        Customer customer = customerRepository.findById(id).orElseThrow(
                ()->new CustomerNotFoundException("Customer not found")
        );
        return dtoMapper.fromCustomer(customer);
    }
    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO){
        log.info("Updating customer with id {}", customerDTO.getId());
        Customer customer = dtoMapper.toCustomer(customerDTO);
        Customer updatedCustomer = customerRepository.save(customer);
        return dtoMapper.fromCustomer(updatedCustomer);
    }
    @Override
    public void deleteCustomer(Long id) throws CustomerNotFoundException{

        customerRepository.deleteById(id);
    }

}



