package com.test.core.mystreams;

import com.test.data.Employee;
import com.test.dataprocess.EmployeeDataBuilder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StreamsSortingExample {
    public static void main(String[] args) {
        try {
            StreamsSortingExample example = new StreamsSortingExample();
            //example.sortedMethodExample();
            //example.compare_MethodExample();
            //example.comparing_With_FunctionAndComparator_MethodExample();
            
            //example.thenComparing_With_Comparator_MethodExample();
            //example.thenComparing_With_Function_MethodExample();
            //example.thenComparing_With_FunctionAndComparator_MethodExample();
            
            example.thenComparingInt_With_Function_MethodExample();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sortedMethodExample() {
        EmployeeDataBuilder empData = new EmployeeDataBuilder();
        System.out.println("Sorting based on employeeId - natural ordering");
        empData.getEmployeesAsList().stream().sorted()
                .forEach(e -> {
                    System.out.println(e);
                });
    }

    private void compare_MethodExample() {
        try {
        	EmployeeDataBuilder empData = new EmployeeDataBuilder();
            System.out.println("------------- compare(T o1, T o2) and using reverse order also------------------");
            empData.getEmployeesAsList().stream().sorted(new Comparator<Employee>() {
    			@Override
    			public int compare(Employee o1, Employee o2) {
    				if(o1 == null && o2 == null)  {
    					return 0;
    				}
    				if(o1 == null) {
    					return 1;
    				}
    				if(o2 == null) {
    					return -1;
    				}
    				if(o1.getFirstName() == null && o2.getLastName() == null) {
    					return 0;
    				}
    				if(o1.getFirstName() == null) {
    					return 1;
    				}
    				if(o2.getLastName() == null) {
    					return -1;
    				}
    				String val1 = o1.getFirstName()+"#"+o1.getLastName();
    				String val2 = o2.getFirstName()+"#"+o2.getLastName();
    				return val1.compareTo(val2);
    			}
            }.reversed()).forEach(e -> {
            	System.out.println(e);
            });
        } catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    private void comparing_With_FunctionAndComparator_MethodExample() {
        try {
        	EmployeeDataBuilder empData = new EmployeeDataBuilder();
            System.out.println("---------- comparing(Function<? super T,? extends U> keyExtractor, Comparator<? super U> keyComparator) ------------");
            empData.getEmployeesAsList()
            .stream()
            .sorted(Comparator.comparing(Employee::getFirstName, String.CASE_INSENSITIVE_ORDER))
            .forEach(e -> {
            	System.out.println(e.getFirstName());
            });
            
        } catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    private void thenComparing_With_Comparator_MethodExample() {
        try {
        	EmployeeDataBuilder empData = new EmployeeDataBuilder();
            System.out.println("---------- thenComparing(Comparator<? super T> other) -------------");
            empData.getEmployeesAsList()
            .stream()
            .sorted(
            		Comparator.comparing(Employee::getFirstName)
            		.thenComparing(Comparator.comparing(Employee::getLastName))
            		).forEach(e -> {
            			System.out.println(e.getFirstName()+" ### "+e.getLastName());
            		});
        } catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    private void thenComparing_With_FunctionAndComparator_MethodExample() {
        try {
        	EmployeeDataBuilder empData = new EmployeeDataBuilder();
            System.out.println("---------- thenComparing(Function<? super T,? extends U> keyExtractor, Comparator<? super U> keyComparator) ------------");
            empData.getEmployeesAsList()
            .stream()
            .sorted(
            		Comparator.comparing(Employee::getFirstName)
            		.thenComparing(Employee::getAge, Comparator.reverseOrder())
            		).forEach(e -> {
            			System.out.println(e.getFirstName()+" ### "+e.getAge());
            		});
        } catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    private void thenComparing_With_Function_MethodExample() {
        try {
        	EmployeeDataBuilder empData = new EmployeeDataBuilder();
            System.out.println("---------- thenComparing(Function<? super T,? extends U> keyExtractor) ------------");
            empData.getEmployeesAsList()
            .stream()
            .sorted(Comparator.comparing(Employee::getFirstName)
            		.thenComparing(Employee::getAge)
            		)
            .forEach(e -> {
            	System.out.println(e.getFirstName()+" ### "+e.getAge());
            });
            
        } catch (Exception e) {
			e.printStackTrace();
		}
    }

    private void thenComparingInt_With_Function_MethodExample() {
        try {
        	EmployeeDataBuilder empData = new EmployeeDataBuilder();
            System.out.println("---------- thenComparingInt(ToIntFunction<? super T> keyExtractor) ------------");
            empData.getEmployeesAsList()
            .stream()
            .sorted(Comparator.comparing(Employee::getFirstName)
            		.thenComparingInt(Employee::getAge)
            		)
            .forEach(e -> {
            	System.out.println(e.getFirstName()+" ### "+e.getAge());
            });
            
        } catch (Exception e) {
			e.printStackTrace();
		}
    }


}