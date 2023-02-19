package com.Estateapp.estate.Controller;


import com.Estateapp.estate.Entity.Transactions;
import com.Estateapp.estate.Service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/txns")
@Slf4j
public class TransactionController {

    @Autowired
    private TransactionService transactionService;


    @PostMapping("/log")
    public ResponseEntity<String> addNewTransactionRecord(@RequestParam("name") String name,
                                                          @RequestParam("email") String email,
                                                          @RequestParam("amount") double amount,
                                                          @RequestParam("ref") String ref, Transactions transactions){

        log.info("Inside Transaction Controller class");

        transactions.setName(name);
        transactions.setEmail(email);
        transactions.setAmount(amount);
        transactions.setTxnRef(ref);

        transactionService.saveTransactions(transactions);
        return ResponseEntity.ok("Transaction saved successfully");

    }
}
