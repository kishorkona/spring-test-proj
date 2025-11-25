package com.test.controllers;

import com.test.constants.MyConstants;
import com.test.data.ApiResponse;
import com.test.data.Emp;
import com.test.data.Employee;
import com.test.dataprocess.EmployeeDataBuilder;
import com.test.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/employee")
public class EmployeeRestController {

    @Autowired
    EmployeeService empService;

    @Autowired
    EmployeeDataBuilder employeeDataBuilder;

    @GetMapping("/getAllEmployees")
    public ResponseEntity<ApiResponse> geAllEmployees() {
        System.out.println("------------>> geAllEmployees:"+Thread.currentThread().getName());
        ApiResponse apiResponse = new ApiResponse();
        ResponseEntity<ApiResponse> responseEntity = empService.callExternalServiceGetEmployees();
        if(responseEntity != null) {
            if(responseEntity.getStatusCode()==HttpStatus.OK) {
                if(responseEntity.getBody().getStatus() != MyConstants.SUCCESS) {
                    apiResponse.setData(new HashMap<>());
                    apiResponse.getData().put("resp", responseEntity.getBody());
                    apiResponse.setCode(MyConstants.SUCCESS_CODE);
                    apiResponse.setStatus(MyConstants.SUCCESS);
                    return new ResponseEntity(apiResponse, HttpStatus.OK);
                } else if(responseEntity.getBody().getStatus() != MyConstants.DATA_UNAVAILABLE) {
                    apiResponse.setCode(MyConstants.SUCCESS_CODE);
                    apiResponse.setStatus(MyConstants.DATA_UNAVAILABLE);
                    return new ResponseEntity(new ArrayList<>(), HttpStatus.OK);
                }
            }
        }
        apiResponse.setCode(MyConstants.SUCCESS_CODE);
        apiResponse.setStatus(MyConstants.OTHER_ISSUE);
        return new ResponseEntity(new ArrayList<>(), HttpStatus.OK);
    }

    @GetMapping("/getEmployee/{id}")
    public ResponseEntity<ApiResponse> getEmployeeById(@PathVariable("id") Long id) {
        System.out.println("------------>> getEmployeeById:"+Thread.currentThread().getName());
        ApiResponse apiResponse = new ApiResponse();
        ResponseEntity<ApiResponse> responseEntity = empService.callExternalServiceGetEmployeeById(id);
        if(responseEntity != null) {
            if(responseEntity.getStatusCode()==HttpStatus.OK) {
                if(responseEntity.getBody().getStatus() != MyConstants.SUCCESS) {
                    apiResponse.setData(new HashMap<>());
                    apiResponse.getData().put("resp", responseEntity.getBody());
                    apiResponse.setCode(MyConstants.SUCCESS_CODE);
                    apiResponse.setStatus(MyConstants.SUCCESS);
                    return new ResponseEntity(apiResponse, HttpStatus.OK);
                } else if(responseEntity.getBody().getStatus() != MyConstants.DATA_UNAVAILABLE) {
                    apiResponse.setCode(MyConstants.SUCCESS_CODE);
                    apiResponse.setStatus(MyConstants.DATA_UNAVAILABLE);
                    return new ResponseEntity(new ArrayList<>(), HttpStatus.OK);
                }
            }
        }
        apiResponse.setCode(MyConstants.SUCCESS_CODE);
        apiResponse.setStatus(MyConstants.OTHER_ISSUE);
        return new ResponseEntity(new ArrayList<>(), HttpStatus.OK);
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

    /*
    @ExceptionHandler(value = NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNoSuchElementException(NoSuchElementException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }
    */
    private void threadSleep(int ms) {
        try {
            Thread.currentThread().sleep(ms);
            System.out.println("------------>> threadSleep:"+Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
