package com.work.core.java17;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

import com.work.data.Emp;
import com.work.data.Employee;
import com.work.dataprocess.EmployeeDataBuilder;

public class TestingJava17 {
    public static void main(String[] args) {
        TestingJava17 ex = new TestingJava17();
        System.out.println("------- TestingJava17 -------");
        //ex.switchExample();
        //ex.instanceOfExample();
        ex.elseThrowExample();
    }
    
    private void testPrivateMethod() {
        var tempInt = new ArrayList<String>();
        tempInt = new ArrayList();
        System.out.println(tempInt);
    }
    
    private void switchExample() {
    	DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
    	
    	/*
    	boolean freeDay = switch (dayOfWeek) {
    	    case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY -> false;
    	    case SATURDAY, SUNDAY -> true;
    	};
    	System.out.println("freeDay="+freeDay);
    	*/
    	boolean freeDay = switch (dayOfWeek) {
	        case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY -> {
	          System.out.println("Work work work");
	          yield false;
	        }
	        case SATURDAY, SUNDAY -> {
	          System.out.println("Yey, a free day!");
	          yield true;
	        }
    	};
    	System.out.println("freeDay="+freeDay);
    }
    
    private void instanceOfExample() {
    	Object obj = new Emp(10, "kishor", 20);
    	if(obj instanceof Emp) {
    		Emp e = (Emp) obj;
    		System.out.println(e);
    	}

    	if(obj instanceof Emp e && e.isValid()) {
    		System.out.println(e);
    	}
    }
    
    private void elseThrowExample() {
    	EmployeeDataBuilder ex = new EmployeeDataBuilder();
    	Employee e = ex.getEmployeesAsList()
    	.stream()
    	.peek(e1 -> {
    		System.out.println(e1);
    	})
    	.findFirst()
    	.orElseThrow();
    }
    
}
