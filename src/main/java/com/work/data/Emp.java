package com.work.data;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@NoArgsConstructor
@Slf4j
@Data
public class Emp {
	private Integer empId;
	private String empName;
	private Integer empAge;
	public Emp(Integer empId, String empName, Integer empAge) {
		this.empId = empId;
		this.empName = empName;
		this.empAge = empAge;
	}
	@Override
	public String toString() {
		return "Emp [empId=" + empId + ", empName=" + empName + ", empAge=" + empAge + "]";
	}

}
