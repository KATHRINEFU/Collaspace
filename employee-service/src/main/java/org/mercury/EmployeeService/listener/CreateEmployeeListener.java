package org.mercury.EmployeeService.listener;

import lombok.extern.slf4j.Slf4j;
import org.mercury.EmployeeService.bean.Employee;
import org.mercury.EmployeeService.dao.EmployeeDao;
import org.mercury.EmployeeService.dto.EmployeeRegistration;
import org.mercury.EmployeeService.service.EmployeeService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName CreateEmployeeListener
 * @Description TODO
 * @Author katefu
 * @Date 12/19/23 10:25 AM
 * @Version 1.0
 **/

@Service
@Slf4j
public class CreateEmployeeListener {
    @Autowired
    private EmployeeService employeeService;

    @RabbitListener(queues = {"q.create-employee"})
    public void onListenCreateEmployee(EmployeeRegistration request) {
        log.info("Create employee request received: {}", request.getEmployeeEmail());
        employeeService.register(request);
    }
}
