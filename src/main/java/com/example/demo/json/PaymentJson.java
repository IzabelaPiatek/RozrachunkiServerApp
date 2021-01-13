package com.example.demo.json;

import com.example.demo.entity.Breakdown;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Blob;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class PaymentJson {

    private Integer id;
    private Integer idGroup;
    private Integer paidBy;
    private Integer amount;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private byte[] image = null;
    private String note;
    private boolean settled = false;
    private Integer payment_option;
    private ArrayList<Breakdown> breakdowns;

    public enum paymentOptions {
        PO_RÓWNO(0),
        PO_RÓWNO_WYBRANI(1),
        WG_KWOT(2),
        WG_KWOT_WYBRANI(3);

        private final int value;

        private paymentOptions(int value) {
            this.value = value;
        }
    }

    public PaymentJson(Integer id, Integer idGroup, Integer paidBy, Integer amount, String description, Date date, byte[] image, String note, boolean settled, Integer payment_option, ArrayList<Breakdown> breakdowns) {
        this.id = id;
        this.idGroup = idGroup;
        this.paidBy = paidBy;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.image = image;
        this.note = note;
        this.settled = settled;
        this.payment_option = payment_option;
        this.breakdowns = breakdowns;
    }

    public PaymentJson() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(Integer idGroup) {
        this.idGroup = idGroup;
    }

    public Integer getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(Integer paidBy) {
        this.paidBy = paidBy;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isSettled() {
        return settled;
    }

    public void setSettled(boolean settled) {
        this.settled = settled;
    }

    public Integer getPayment_option() {
        return payment_option;
    }

    public void setPayment_option(Integer payment_option) {
        this.payment_option = payment_option;
    }

    public ArrayList<Breakdown> getBreakdowns() {
        return breakdowns;
    }

    public void setBreakdowns(ArrayList<Breakdown> breakdowns) {
        this.breakdowns = breakdowns;
    }
}
