package com.work.service;

import com.work.constants.MyConstants;
import com.work.data.ApiResponse;
import com.work.data.Emp;
import com.work.data.Employee;
import com.work.dataprocess.EmployeeDataBuilder;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeDataBuilder employeeDataBuilder;

    @Autowired
    private RestTemplate restTemplate;

    public List<Employee> getEmployees() {
        return employeeDataBuilder.getEmployeesAsList();
    }

    public boolean addEmployees(Emp emp) {
        System.out.println(emp);
        return true;
    }

    //https://www.baeldung.com/spring-retry
    @Retry(name = "employeeServiceGetEmployeeById", fallbackMethod = "recoverGetEmployeeById")
    public ResponseEntity<ApiResponse> callExternalServiceGetEmployeeById(Long empId) throws ResourceAccessException {
        System.out.println("------------>> callExternalServiceGetEmployeeById");
        String employeeUrl = "http://localhost:3030/spring-test-proj/api/failover/getEmployee/"+empId;
        return restTemplate.exchange(employeeUrl, HttpMethod.GET, null, ApiResponse.class);
    }
    public ResponseEntity<ApiResponse> recoverGetEmployeeById(Long id, ResourceAccessException e) {
        System.out.println("------------>> recoverGetEmployeeById: "+Thread.currentThread().getName()+"###"+id+"###"+e.getMessage());
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(MyConstants.SUCCESS_CODE);
        apiResponse.setStatus(MyConstants.SERVICE_UNAVAILABLE);
        apiResponse.setMessage("RETRY_ATTEMPT_FAILED");
        return new ResponseEntity(apiResponse, HttpStatus.OK);
    }

    @CircuitBreaker(name = "employeeRestCall", fallbackMethod = "fallbackEmployee")
    @TimeLimiter(name = "callExternalServiceGetEmployees")
    public ResponseEntity<ApiResponse> callExternalServiceGetEmployees() {
        String employeeUrl = "http://localhost:3030/spring-test-proj/api/failover/getAllEmployees";
        return restTemplate.exchange(employeeUrl, HttpMethod.GET, null, ApiResponse.class);
    }
    // 2. The Fallback Method. This runs if the external call fails OR if the Circuit is OPEN.
    public ResponseEntity<ApiResponse> fallbackEmployee(Exception e) {
        System.out.println("------------>> fallbackPayment: "+Thread.currentThread().getName()+"###"+e.getMessage());
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(MyConstants.SUCCESS_CODE);
        apiResponse.setStatus(MyConstants.SERVICE_UNAVAILABLE);
        apiResponse.setMessage("DEPENDENCY_PAYMENT_SERVICE_UNAVAILABLE");
        return new ResponseEntity(apiResponse, HttpStatus.OK);
    }

    public void threadSleep(int ms) {
        try {
            Thread.currentThread().sleep(ms);
            System.out.println("------------>> threadSleep:"+Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
