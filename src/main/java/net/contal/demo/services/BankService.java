package net.contal.demo.services;

import net.contal.demo.AccountNumberUtil;
import net.contal.demo.DbUtils;
import net.contal.demo.models.BankTransaction;
import net.contal.demo.models.CustomerAccount;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BankService {

    private final DbUtils dbUtils;
    @Autowired
    public BankService(DbUtils dbUtils) {
        this.dbUtils = dbUtils;
    }


    /**
     * Save customAccount to database
     * return AccountNumber
     * @param customerAccount populate this (firstName , lastName ) already provided
     * @return accountNumber
     */
    public int createAnAccount(CustomerAccount customerAccount){
        customerAccount.setAccountNumber(AccountNumberUtil.generateAccountNumber());
        Session session = dbUtils.openASession();
        session.saveOrUpdate(customerAccount);
        return customerAccount.getAccountNumber();
    }


    /**
     * @param accountNumber target account number
     * @param amount amount to register as transaction
     * @return boolean , if added as transaction
     */
    public boolean addTransactions(int accountNumber , Double amount){
         CustomerAccount account = getAccountByNumber(accountNumber);
         if(account == null) {
             return false;
         }
         BankTransaction transaction = new BankTransaction();
         transaction.setTransactionAmount(amount);
         transaction.setTransactionDate(new Date());
         transaction.setCustomerAccount(account);
         dbUtils.openASession().saveOrUpdate(transaction);
         return true;
    }

    private CustomerAccount getAccountByNumber(int accountNumber) {
        String hql = "from CustomerAccount where accountNumber=:accountNumber";
        List<CustomerAccount> accounts = this.dbUtils.openASession().createQuery(hql, CustomerAccount.class)
                .setParameter("accountNumber",accountNumber)
                .getResultList();
        if (accounts == null || accounts.size() == 0) {
            return null;
        } else {
            return accounts.get(0);
        }
    }


    /**
     * @param accountNumber target account
     * @return account balance
     */
    public double getBalance(int accountNumber){
        CustomerAccount account = getAccountByNumber(accountNumber);
        if(account == null) {
            return 0d;
        }
        return account.getTransactions().stream().mapToDouble(i -> i.getTransactionAmount()).sum();
    }


    /**
     * ADVANCE TASK
     * @param accountNumber accountNumber
     * @return HashMap [key: date , value: double]
     */
    public Map<Date,Double> getDateBalance(int accountNumber){
        CustomerAccount account = getAccountByNumber(accountNumber);
        if (account == null) {
            return null;
        }
        Map<Date, Double> dateBalanceMap= new HashMap<>();
        account.getTransactions().stream().forEach(bankTransaction -> {
            dateBalanceMap.put(bankTransaction.getTransactionDate(), bankTransaction.getTransactionAmount());
        });
        return dateBalanceMap;
    }
}
