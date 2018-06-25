package com.controller;

import com.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wesharn
 * @version V1.0
 * @Title:
 * @Package ${package_name}
 * @Description: TODO
 * @date ${date}
 */
@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Qualifier("transactionService")
    @Autowired
    TransactionService transactionService;
    @RequestMapping("/test")
    public String test(){
        transactionService.testA();
        return "200";
    }
}
