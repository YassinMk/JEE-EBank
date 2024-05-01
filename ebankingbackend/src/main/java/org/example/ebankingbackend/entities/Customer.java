package org.example.ebankingbackend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Entity
@Data
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String name;
    private String email;
    @OneToMany(mappedBy = "customer")
    List<BankAccount> bankAccounts;
}
