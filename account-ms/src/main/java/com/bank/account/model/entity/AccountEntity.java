package com.bank.account.model.entity;

import com.bank.account.model.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "account")
@AllArgsConstructor
 public class AccountEntity {

    @Id
    private Integer id;

    @Column(name="account_number")
    private String accountNumber;

    @Column(name="account_type")
    private AccountType accountType;

}
