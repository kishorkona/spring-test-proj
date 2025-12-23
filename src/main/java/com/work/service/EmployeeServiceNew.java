package com.work.service;

import com.work.data.Employee;
import com.work.dataprocess.EmployeeDataBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EmployeeServiceNew {
    @Autowired
    EmployeeDataBuilder employeeDataBuilder;

    public List<Employee> getEmployeesNew() {
        return employeeDataBuilder.getEmployeesAsList();
    }


}
