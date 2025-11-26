package com.work.core.mystreams;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.work.dataprocess.EmployeeDataBuilder;

public class OtherMethodsExample {
    public static void main(String[] args) {
        try {
            OtherMethodsExample ex = new OtherMethodsExample();
            ex.reduce_Method();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void distenct_Method() {
        System.out.println("------- Stream<T> distinct() --------");
        
        EmployeeDataBuilder empData = new EmployeeDataBuilder();
        List<Integer> ages = empData.getEmployeesAsList()
        		.stream()
        		.unordered()
        		.map(e -> {
        			return e.getAge();
        		})
        		.distinct()
        		.collect(Collectors.toList());
        System.out.println(ages);
        
    }
    
    private void peek_Method() {
        System.out.println("------- Stream<T> peek() --------");
        
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

        List<String> processedNames = names.stream()
            .filter(name -> name.length() > 3)
            // Debugging: print filtered names
            .peek(name -> System.out.println("Filtered name: " + name)) 
            .map(String::toUpperCase)
            // Debugging: print mapped names
            .peek(name -> System.out.println("Mapped name: " + name))    
            .collect(Collectors.toList());

        System.out.println("Final list: " + processedNames);
        
    }

    private void reduce_Method() {
        System.out.println("------- Stream<T> peek() --------");
        
        Stream<Integer> numbers = Stream.of(1, 2, 3, 4, 5);

        // Summing the elements using reduce with a BinaryOperator
        Optional<Integer> sumOptional = numbers.reduce((a, b) -> a + b);
        if (sumOptional.isPresent()) {
            System.out.println("Sum: " + sumOptional.get()); // Output: Sum: 15
        } else {
            System.out.println("Stream is empty.");
        }
        Stream<String> words = Stream.of("Hello", "World", "Java");

        // Concatenating strings using reduce
        Optional<String> concatenatedOptional = words.reduce((s1, s2) -> s1 + " " + s2);
        if (concatenatedOptional.isPresent()) {
        	// Output: Concatenated String: Hello World Java
            System.out.println("Concatenated String: " + concatenatedOptional.get()); 
        } else {
            System.out.println("Stream is empty.");
        }
        
    }
}