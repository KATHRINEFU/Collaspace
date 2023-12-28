package org.mercury.AccountService;

import lombok.extern.slf4j.Slf4j;
import org.mercury.AccountService.bean.Account;
import org.mercury.AccountService.dao.AccountDao;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName GetAccountListener
 * @Description TODO
 * @Author katefu
 * @Date 11/17/23 11:44 AM
 * @Version 1.0
 **/

//@Service
//@Slf4j
//public class GetAccountListener {
//
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    @Autowired
//    private AccountDao accountDao;
//
//    @RabbitListener(queues = {"q.get-account"})
//    public void onListenGetAccount(int accountId) {
//        Account account = accountDao.findById(accountId).orElse(null);
//        rabbitTemplate.convertAndSend("", "q.return-account", account);
//        log.info("Return-Employee_Teams return sent: {}", account.getAccountId());
//    }
//
//}
