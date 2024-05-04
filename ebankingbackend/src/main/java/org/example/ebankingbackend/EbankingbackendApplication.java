package org.example.ebankingbackend;

import org.example.ebankingbackend.dtos.BankAccountDTO;
import org.example.ebankingbackend.dtos.CurrentBankAccountDTO;
import org.example.ebankingbackend.dtos.CustomerDTO;
import org.example.ebankingbackend.dtos.SavingBankAccountDTO;
import org.example.ebankingbackend.entities.*;
import org.example.ebankingbackend.enums.AccountStatus;
import org.example.ebankingbackend.enums.OperationType;
import org.example.ebankingbackend.exceptions.BalanceNotSufficientException;
import org.example.ebankingbackend.exceptions.BankAccountNotFoundException;
import org.example.ebankingbackend.exceptions.CustomerNotFoundException;
import org.example.ebankingbackend.repositories.AccountOperationRepository;
import org.example.ebankingbackend.repositories.BankAccountRepository;
import org.example.ebankingbackend.repositories.CustomerRepository;
import org.example.ebankingbackend.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingbackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankingbackendApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService) {
        return args -> {
          Stream.of("Hassan", "Yassine", "Anas").forEach(name -> {
              CustomerDTO customer = new CustomerDTO();
              customer.setName(name);
              customer.setEmail(name + "@gmail.com");
              bankAccountService.saveCustomer(customer);
          });
          bankAccountService.listCustomers().forEach(cust -> {
              try {
                  bankAccountService.saveCurrentBankAccount(Math.random() * 90000, 5.5, cust.getId());
                  bankAccountService.saveSavingBankAccount(Math.random() * 15000, 9000, cust.getId());
                  List<BankAccountDTO> bankAccounts =  bankAccountService.bankAccountList();
                  for(BankAccountDTO bankAccount : bankAccounts) {
                      for (int i = 0; i < 10; i++) {
                          String accountId;
                          if(bankAccount instanceof SavingBankAccountDTO){
                                accountId = ((SavingBankAccountDTO) bankAccount).getId();
                          }else {
                              accountId = ((CurrentBankAccountDTO) bankAccount).getId();
                          }
                          bankAccountService.credit(accountId, 10000+Math.random() * 12000, "Credit");
                          bankAccountService.debit(accountId, 1000+Math.random() * 9000, "Debit");
                      }
                  }
              }catch (CustomerNotFoundException e) {
                  e.printStackTrace();
              }catch (BankAccountNotFoundException | BalanceNotSufficientException e) {
                  e.printStackTrace();
              }

          });

        };
    }
    //@Bean
    CommandLineRunner start(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository, AccountOperationRepository accountOperationRepository) {
        return args -> {
            Stream.of("Hassan", "Yassine", "Anas").forEach(name -> {
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name + "@gmail.com");
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(cust -> {
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random() * 1000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(cust);
                currentAccount.setOverdraft(1000);
                currentAccount.setCurrency("DH");
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random() * 1000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(cust);
                savingAccount.setCurrency("DH");
                savingAccount.setInterestRate(5.5);
                currentAccount.setCurrency("DH");
                bankAccountRepository.save(savingAccount);
            });
            bankAccountRepository.findAll().forEach(acc -> {
                for (int i = 0; i < 10; i++) {
                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random() * 1000);
                    accountOperation.setType(Math.random() > 0.5 ? OperationType.DEBIT : OperationType.CREDIT);
                    accountOperation.setBankAccount(acc);
                    accountOperationRepository.save(accountOperation);
                }
            });
        };
    }

}
