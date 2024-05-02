package org.example.ebankingbackend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import org.example.ebankingbackend.entities.BankAccount;

import java.util.List;
@Data
public class CustomerDTO {
    private Long id ;
    private String name;
    private String email;
}
