package org.mercury.EmployeeService.service;

import jakarta.annotation.Resource;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mercury.EmployeeService.bean.Employee;
import org.mercury.EmployeeService.service.EmployeeService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @ClassName EmployeeServiceTest
 * @Description TODO
 * @Author katefu
 * @Date 11/23/23 3:16 PM
 * @Version 1.0
 **/

@SpringBootTest
@RunWith(SpringRunner.class)
public class EmployeeServiceTest {
    @Resource
    private EmployeeService employeeService;

    @Test
    public void getAllTest(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse("2020-10-26", dtf);
        LocalDate end = LocalDate.parse("2020-10-31", dtf);

        List<Employee> list =  employeeService.getAll();
        Assert.assertThat(list, Matchers.notNullValue());//assertThat断言后面介绍
    }
}
