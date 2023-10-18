package org.mercury.ClientService.listener;

import lombok.extern.slf4j.Slf4j;
import org.mercury.ClientService.bean.Company;
import org.mercury.ClientService.dao.CompanyDao;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName GetAccountCompanyListener
 * @Description TODO
 * @Author katefu
 * @Date 10/16/23 3:27 PM
 * @Version 1.0
 **/

@Service
@Slf4j
public class GetAccountCompanyListener {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private CompanyDao companyDao;

    @RabbitListener(queues = {"q.get-account-company"})
    public void onListenGetEmployeeTeams(int companyId){
        log.info("Get-Employee-Teams request received: {}", companyId);
        Company company = companyDao.findById(companyId).orElse(null);
        assert company != null;
        rabbitTemplate.convertAndSend("", "q.return-account-company", company);
        log.info("Return-Employee_Teams return sent: {}", company);
    }
}
