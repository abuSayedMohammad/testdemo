package net.contal.demo.controllers;

import net.contal.demo.models.CustomerAccount;
import net.contal.demo.services.BankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/banks")
public class BankController {
    final Logger logger = LoggerFactory.getLogger(BankController.class);
    final BankService dataService;

    @Autowired
    public BankController(BankService dataService) {
        this.dataService = dataService;
    }


    /**
     *  TODO call properiate method in dataService to create an bank account , return generated bank account number
     * @param account {firstName:"" , lastName:"" }
     * @return bank account number
     */
     @PostMapping("/create")
    public int createBankAccount(@RequestBody CustomerAccount account){
        logger.info("{}" ,account.toString());
        return this.dataService.createAnAccount(account);
    }

    /**
     *TODO call related Method from Service class to do the process
     * @param accountNumber BankAccount number
     * @param amount Amount as Transaction
     */
    @PostMapping("/transaction")
    public boolean addTransaction(@RequestParam("accountNumber") int accountNumber, @RequestParam("amount") Double amount){
        logger.info("Bank Account number is :{} , Transaction Amount {}",accountNumber,amount);
        return this.dataService.addTransactions(accountNumber, amount);
    }


    /**
     * TODO call related Method from Service class to do the process
     * @param accountNumber customer  bank account  number
     * @return balance
     */
    @GetMapping("/balance")
    public Double getBalance(@RequestParam("accountNumber") int accountNumber){
        logger.info("Bank Account number is :{}",accountNumber);
        return this.dataService.getBalance(accountNumber);
    }

}
