package com.example.demo.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "breakdowns")
public class Breakdown {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_breakdown")
    private Integer id;
    
    @Column(name = "id_borrower")
    private Integer idBorrower;
    
    @Column(name = "amount")
    private Integer amount;
    
    @Column(name = "id_payment")
    private Integer idPayment;
    
    @Column(name = "settled")
    private boolean settled;

    public Breakdown(Integer id, Integer idBorrower, Integer amount, Integer idPayment, boolean settled) {
        this.id = id;
        this.idBorrower = idBorrower;
        this.amount = amount;
        this.idPayment = idPayment;
        this.settled = settled;
    }

    public Breakdown() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdBorrower() {
        return idBorrower;
    }

    public void setIdBorrower(Integer idBorrower) {
        this.idBorrower = idBorrower;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getIdPayment() {
        return idPayment;
    }

    public void setIdPayment(Integer idPayment) {
        this.idPayment = idPayment;
    }

    public boolean isSettled() {
        return settled;
    }

    public void setSettled(boolean settled) {
        this.settled = settled;
    }
}
