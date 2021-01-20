package com.example.demo.json;

import com.example.demo.entity.Payment;

import java.sql.SQLException;
import java.util.Date;

public class PaymentWithOwnerJson {
    private Integer id;

    private Integer idGroup;

    private Integer paidBy;

    private Integer amount;

    private String description;

    private Date date;

    private byte[] image;

    private String note;

    private boolean settled = false;

    private Integer payment_option;

    private String ownerUsername;

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

    public PaymentWithOwnerJson(Integer id, Integer idGroup, Integer paidBy, Integer amount, String description, Date date, byte[] image, String note, boolean settled, Integer payment_option, String ownerUsername) {
        this.id = id;
        this.idGroup = idGroup;
        this.paidBy = paidBy;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.image = image;
        this.note = note;
        this.settled = settled;
        this.ownerUsername = ownerUsername;
    }

    public PaymentWithOwnerJson(Payment payment, String ownerUsername) throws SQLException {
        this.id = payment.getId();
        this.idGroup = payment.getIdGroup();
        this.paidBy = payment.getPaidBy();
        this.amount = payment.getAmount();
        this.description = payment.getDescription();
        this.date = payment.getDate();
        if (payment.getImage() != null) {
            this.image = payment.getImage().getBytes(1, (int) payment.getImage().length());
        } else {
            this.image = null;
        }

        this.note = payment.getNote();
        this.settled = payment.isSettled();
        this.payment_option = payment.getPayment_option();
        this.ownerUsername = ownerUsername;
    }

    public PaymentWithOwnerJson() {
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

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }
}
