package test.com.work.service;

import com.work.SpringProjApplicationStart;
import com.work.data.Employee;
import com.work.service.EmployeeService;
import com.work.service.EmployeeServiceNew;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = SpringProjApplicationStart.class)
@AutoConfigureMockMvc
public class EmployeeServiceNewTest {
    @Mock
    private EmployeeServiceNew employeeServiceNew;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    public void getEmployeesTest() throws Exception {
        when(employeeServiceNew.getEmployeesNew()).thenReturn(createMockEmployees());
        employeeService.getEmployeesNew();
    }

    private List<Employee> createMockEmployees() {
        List<Employee> lst = new ArrayList<>();
        lst.add(createMockEmployee(8, "Moses", "Anderson", "Moses.Vargas@gmail.com", "732-450-0008", "Finance", 22211.0d));
        lst.add(createMockEmployee(9, "Andrea", "Gutierrez", "Andrea.Gutierrez@gmail.com", "732-450-0009", "Sales", 22212.0d));
        lst.add(createMockEmployee(8, "Rylee", "Anderson", "Rylee.Anderson@gmail.com", "732-450-0002", "Engineering", 22213.0d));
        lst.add(createMockEmployee(8, "Jacob", "House", "Jacob.House@gmail.com", "732-450-0005", "Finance", 22214.0d));
        return lst;
    }
    private Employee createMockEmployee(Integer employeeId, String firstName, String lastName, String email,
                                        String phoneNumber, String jobId, Double salary){
        Employee e = new Employee();
        e.setEmployeeId(employeeId);
        e.setFirstName(firstName);
        e.setLastName(lastName);
        e.setEmail(email);
        e.setPhoneNumber(phoneNumber);
        e.setJobId(jobId);
        e.setSalary(salary);
        return e;
    }
}
class EmployeeService1 {
    private EmployeeServiceNew employeeServiceNew;

    public EmployeeService1(EmployeeServiceNew employeeServiceNew) {
        this.employeeServiceNew = employeeServiceNew;
    }
}
