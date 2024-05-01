package org.example.ebankingbackend;

import org.example.ebankingbackend.entities.*;
import org.example.ebankingbackend.enums.AccountStatus;
import org.example.ebankingbackend.enums.OperationType;
import org.example.ebankingbackend.repositories.AccountOperationRepository;
import org.example.ebankingbackend.repositories.BankAccountRepository;
import org.example.ebankingbackend.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingbackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankingbackendApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository, AccountOperationRepository accountOperationRepository){
        return args -> {
            BankAccount bankAccount = bankAccountRepository.findById("40e6c4df-56b2-4d43-923b-42073aff3d7a").orElse(null);
            if (bankAccount != null) {
                bankAccount.setBalance(1000);
                bankAccountRepository.save(bankAccount);
                System.out.println("*******************");
                System.out.println(bankAccount.getId());
                System.out.println(bankAccount.getBalance());
                System.out.println(bankAccount.getStatus());
                System.out.println(bankAccount.getCreatedAt());
                System.out.println(bankAccount.getCustomer().getName());
                System.out.println(bankAccount.getClass().getSimpleName());
                if (bankAccount instanceof CurrentAccount) {
                    CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                    System.out.println("Over Draft=>" + currentAccount.getOverdraft());
                } else if (bankAccount instanceof SavingAccount) {
                    SavingAccount savingAccount = (SavingAccount) bankAccount;
                    System.out.println("Rate =>" + savingAccount.getInterestRate());
                }

                bankAccount.getAccountOperations().forEach(op -> {
                    System.out.println(op.getId() + "\t" + op.getOperationDate() + "\t" + op.getAmount() + "\t" + op.getType());
                });
            }else {
                System.out.println("Bank Account Not Found");
            }



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
