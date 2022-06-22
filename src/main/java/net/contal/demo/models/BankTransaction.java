package net.contal.demo.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

//TODO complete this class
@Entity
@Table
public class BankTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private double transactionAmount;
    private Date transactionDate;

    @ManyToOne
    private CustomerAccount customerAccount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CustomerAccount getCustomerAccount() {
        return customerAccount;
    }

    public void setCustomerAccount(CustomerAccount customerAccount) {
        this.customerAccount = customerAccount;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return "BankTransaction{" +
                "id=" + id +
                ", transactionAmount=" + transactionAmount +
                ", transactionDate=" + transactionDate +
                ", customerAccount=" + customerAccount +
                '}';
    }
}
