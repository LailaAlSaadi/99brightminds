package com.bank.account.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "statement")
@AllArgsConstructor
@Builder
public class AccountStatementEntity {

    @Id
    private Integer id;

    @Column(name = "account_id")
    private Integer accountId;

    @Column(name = "datefield")
    private LocalDate dateField;

    @Column(name = "amount")
    private Double amount;

}
