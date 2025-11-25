package com.test.dataprocess;

import com.test.data.Employee;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class EmployeeDataBuilder {
    public static void main(String[] args) {
        EmployeeDataBuilder employeeDataBuilder = new EmployeeDataBuilder();
        //employeeDataBuilder.buildDataSetEmployeeCSV();

        
        Arrays.stream(employeeDataBuilder.getEmployeesAsArray()).forEach(x -> {
            System.out.println(x);
        });
        
        
        /*
        List<Employee> employees = employeeDataBuilder.getEmployeesAsList();
        employees.stream().forEach(employee -> {
            System.out.println(employee);
        });
        */
    }

    public List<Employee> getEmployeesAsList() {
        String employeeCSVFileName = "employee.csv";
        String[] lin = new String[1];
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(employeeCSVFileName)) {
            if (is == null) throw new RuntimeException("Resource not found: " + employeeCSVFileName);
            List<Employee> employees = new BufferedReader(new InputStreamReader(is))
                    .lines()
                    .skip(1)
                    .map(line -> {
                        lin[0] = line;
                        String[] values = line.split(",");
                        Employee e = new Employee();
                        e.setEmployeeId(validateInteger(values[0]));
                        e.setFirstName(validateString(values[1]));
                        e.setLastName(validateString(values[2]));
                        e.setEmail(validateString(values[3]));
                        e.setPhoneNumber(validatePhoneNo(values[4]));
                        e.setHireDate(validateLocalDate(values[5]));
                        e.setAge(validateInteger(values[6]));
                        e.setJobId(validateString(values[7]));
                        e.setSalary(validateDouble(values[8]));
                        e.setCommissionPct(validateDouble(values[9]));
                        e.setManagerId(validateInteger(values[10]));
                        e.setDepartmentId(validateInteger(values[11]));
                        return e;
                    }).collect(Collectors.toList());
            return employees;
        } catch (Exception ex) {
            System.out.println("line=" + lin[0]);
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }

    private String validateString(String col) {
        if(col.contains("-") || col.contains(" ")) {
            return null;
        }
        return col.trim();
    }
    private Double validateDouble(String col) {
        if(col.contains("-") || col.contains(" ")) {
            return null;
        }
        return Double.valueOf(col.trim());
    }
    private Integer validateInteger(String col) {
        if(col.contains("-") || col.contains(" ")) {
            return null;
        }
        return Integer.valueOf(col.trim());
    }
    private LocalDate validateLocalDate(String col) {
        if(col.contains("-") && col.trim().length() == 10) {
            return LocalDate.parse(col.trim());
        }
        return null;
    }
    private String validatePhoneNo(String col) {
        if(col.contains("-") && col.trim().length() == 12) {
            return generatePhoneNumber(col.trim());
        }
        return null;
    }
    /* Old way using CsvToBeanBuilder with preprocessing
    public List<Employee> getEmployeesAsList_old() {
        String employeeCSVFileName = "employee1.csv";
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(employeeCSVFileName)) {
            if (is == null) throw new RuntimeException("Resource not found: " + employeeCSVFileName);
            // Preprocess: replace " - " with empty
            String cleaned = new BufferedReader(new InputStreamReader(is))
                    .lines()
                    //.map(line -> line.replaceAll("\\s*-\\s*", ""))
                    .map(line -> line.replaceAll("(?<=,|^)\\s*-\\s*(?=,|$)", ""))
                    .collect(Collectors.joining("\n"));
            // Parse cleaned CSV
            System.out.println("Line="+cleaned);
            return new CsvToBeanBuilder<Employee>(new StringReader(cleaned))
                    .withType(Employee.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()
                    .parse();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }
    */
    public Employee[] getEmployeesAsArray() {
        try {
            return getEmployeesAsList().toArray(new Employee[0]);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new Employee[0];
    }
    private void buildDataSetEmployeeCSV() {
    	Integer[] ageIds = {30,45,28,5235,40,25,48,33,58};
        String[] jobIds = {"Marketing", "Sales", "Engineering", "Human Resources", "Finance", "Operations"};
        String[] domains = {"atnt.com", "verizon.com", "facebook.com", "yahoo.com","gmail.com"};
        Random rand = new Random();
        List<String[]> names = readNames();
        try (FileWriter writer = new FileWriter("large_employee.csv")) {
            writer.write("employeeId,firstName,lastName,email,phoneNumber,hireDate,ageId,jobId,salary,commissionPct,managerId,departmentId\n");
            for (int i = 1; i <= 1000; i++) {
                if(i==1000) {
                    String y = "dddd";
                }
                String[] arr = names.get(i-1);
                String email = arr[0] + "." + arr[1] + "@" + domains[rand.nextInt(domains.length)];
                String phone = "732450" + String.format("%04d", i);
                LocalDate hireDate = LocalDate.of(2000 + rand.nextInt(25), 1 + rand.nextInt(12), 1 + rand.nextInt(28));
                Integer ageId = ageIds[rand.nextInt(ageIds.length)];
                String jobId = jobIds[rand.nextInt(jobIds.length)];
                double salary = 3000 + rand.nextInt(20000);
                String commissionPct = rand.nextInt(10) == 0 ? " - " : String.format("%.2f", rand.nextDouble() * 10);
                int managerId = rand.nextInt(200) + 100;
                int departmentId = rand.nextInt(120) + 10;
                writer.write(String.format("%d,%s,%s,%s,%s,%s,%s,%s,%.2f,%s,%d,%d\n",
                        i, arr[0], arr[1], email, generatePhoneNumber(phone), hireDate, ageId, jobId, salary, commissionPct, managerId, departmentId));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private String generatePhoneNumber(String phoneNo) {
        String number = StringUtils.replaceAll(phoneNo, "-", "");
        if (number.length() == 10) {
            String formatted = number.substring(0, 3) + "-" +
                    number.substring(3, 6) + "-" +
                    number.substring(6);
            return formatted;
        }
        return null;
    }

    private List<String[]> readNames() {
        String fileName = "names.txt";
        String[] lin = new String[1];
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (is == null) throw new RuntimeException("Resource not found: " + fileName);
            List<String[]> data = new BufferedReader(new InputStreamReader(is))
                    .lines()
                    .skip(1)
                    .map(line -> {
                        lin[0] = line;
                        String[] values = line.split(" ");
                        String[] arr = new String[2];
                        arr[0] = values[0].trim();
                        arr[1] = values[1].trim();
                        return arr;
                    }).collect(Collectors.toList());
            return data;
        } catch (Exception ex) {
            System.out.println("line=" + lin[0]);
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }
}
