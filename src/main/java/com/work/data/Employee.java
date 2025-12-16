package com.work.data;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDate;

@Slf4j
@Data
public class Employee {
	private Integer employeeId;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private LocalDate hireDate;
	private String jobId;
	private Double salary;
	private Double commissionPct;
	private Integer managerId;
	private Integer departmentId;
	private Integer age;

	/*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return employeeId != null && employeeId.equals(employee.employeeId);
    }

    @Override
    public int hashCode() {
        return employeeId != null ? employeeId.hashCode() : 0;
    }
    */
    /*
    @Override
    public int compareTo(Object o) {
        // This is a natural ordering based on employeeId mainly used in streams sorting
    	System.out.println("-------- Employee-compareTo ---------");
        if (this == o) return 0;
        if (o == null || getClass() != o.getClass()) return 1;
        Employee other = (Employee) o;
        if (this.employeeId == null && other.employeeId == null) return 0;
        if (this.employeeId == null) return -1;
        if (other.employeeId == null) return 1;
        return this.employeeId.compareTo(other.employeeId);
    }
    */
	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", firstName=" + firstName + ", lastName=" + lastName + ", email="
				+ email + ", phoneNumber=" + phoneNumber + ", hireDate=" + hireDate + ", jobId=" + jobId + ", salary="
				+ salary + ", commissionPct=" + commissionPct + ", managerId=" + managerId + ", departmentId="
				+ departmentId + ", age=" + age + "]";
	}
}
