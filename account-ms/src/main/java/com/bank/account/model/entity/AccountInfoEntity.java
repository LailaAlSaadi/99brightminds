package com.bank.account.model.entity;

import com.bank.account.model.enums.AccountType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "statement")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountInfoEntity {

    @Id
    @JsonIgnore
    private Integer id;

    @Column(name = "account_type")
    private AccountType accountType;

    @Column(name = "account_number")
    private String accountNumber;

}
