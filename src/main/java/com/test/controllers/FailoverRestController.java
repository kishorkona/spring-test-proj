package com.test.controllers;

import com.test.constants.MyConstants;
import com.test.data.ApiResponse;
import com.test.data.Emp;
import com.test.data.Employee;
import com.test.dataprocess.EmployeeDataBuilder;
import com.test.service.EmployeeService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/failover")
public class FailoverRestController {

    @Autowired
    EmployeeService empService;

    @Autowired
    EmployeeDataBuilder employeeDataBuilder;


    @GetMapping("/getAllEmployees")
    public ResponseEntity<ApiResponse> getEmployees() {
        System.out.println("------------>> geAllEmployees:"+Thread.currentThread().getName());
        empService.threadSleep(1000);
        ApiResponse apiResponse = new ApiResponse();
        List<Employee> data = employeeDataBuilder.getEmployeesAsList();
        apiResponse.setData(new HashMap<>());
        apiResponse.getData().put("resp", data);
        apiResponse.setCode(MyConstants.SUCCESS_CODE);
        apiResponse.setStatus(MyConstants.SUCCESS);
        return new ResponseEntity(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/getEmployee/{id}")
    public ResponseEntity<ApiResponse> getEmployeeById(@PathVariable("id") Long id) {
        Employee emp = getEmployeeByIdInternal(id);
        if(emp != null) {
            System.out.println("------------>> getEmployeeById:"+Thread.currentThread().getName());
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setData(new HashMap<>());
            apiResponse.getData().put("resp", emp);
            apiResponse.setCode(MyConstants.SUCCESS_CODE);
            apiResponse.setStatus(MyConstants.SUCCESS);
            return new ResponseEntity(apiResponse, HttpStatus.OK);
        } else {
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setCode(MyConstants.SUCCESS_CODE);
            apiResponse.setStatus(MyConstants.DATA_UNAVAILABLE);
            return new ResponseEntity(apiResponse, HttpStatus.OK);
        }
    }

    private Employee getEmployeeByIdInternal(Long id) {
    	List<Employee> data = employeeDataBuilder.getEmployeesAsList();
        List<Employee> filtered = data.stream()
        		.filter(e -> {
        			if(e.getEmployeeId() == id.intValue()) {
        				return true;
        			}
        			return false;
        		})
        		.collect(Collectors.toList());
        if(filtered != null && filtered.size() > 0) {
        	return filtered.get(0);
        }
        return null;
    }

    @GetMapping("/byjob/{job}")
    public ResponseEntity<List<Employee>> getEmployeeByJob(@PathVariable("job") String job) {
    	List<Employee> data = employeeDataBuilder.getEmployeesAsList();
        return new ResponseEntity(data.stream()
        		.filter(e -> {
        			return (e.getJobId() != null && e.getJobId().equalsIgnoreCase(job));
        		})
        		.collect(Collectors.toList()), HttpStatus.OK);
    }


    @PostMapping({ "addEmployee"})
    public ResponseEntity<String> addEmployees(@RequestBody Emp emp) {
        if(empService.addEmployees(emp)) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
