package org.mercury.EmployeeService.listener;

import lombok.extern.slf4j.Slf4j;
import org.mercury.EmployeeService.bean.Employee;
import org.mercury.EmployeeService.dao.EmployeeDao;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName GetEmployeeListener
 * @Description TODO
 * @Author katefu
 * @Date 11/11/23 7:17 PM
 * @Version 1.0
 **/

@Service
@Slf4j
public class GetEmployeeListener {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private EmployeeDao employeeDao;

    @RabbitListener(queues = {"q.get-employee"})
    public void onListenGetEmployee(int employeeId) {
        log.info("Get-Employee request received: {}", employeeId);
        Employee employee = employeeDao.findById(employeeId).orElse(null);
        if (employee != null) {
            rabbitTemplate.convertAndSend("", "q.return-employee", employee);
            log.info("Return-Employee_Teams return sent: {}", employeeId);
        } else {
            // Handle the case where the employee is not found
            log.warn("Employee not found for id: {}", employeeId);
        }
    }
}
