package com.work.core.mystreams;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamsMapExample {
    public static void main(String[] args) {
        try {
            StreamsMapExample example = new StreamsMapExample();
            example.multiMap_Example();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void example1() {
        System.out.println("------- Chaining Transformations on a Single Stream --------");
        List<String> words = Arrays.asList("hello", "world", "java", "streams");
        List<String> transformedWords = words.stream()
                .map(String::toUpperCase) // First map: convert to uppercase
                .map(s -> s + "!")      // Second map: append an exclamation mark
                .collect(Collectors.toList());

        System.out.println(transformedWords); // Output: [HELLO!, WORLD!, JAVA!, STREAMS!]
    }

    private void converting_ListToMap() {
    	System.out.println("------- Transforming Elements into Key-Value Pairs for a Map:  --------");
    	List<Person1> people = Arrays.asList(
                new Person1("Alice", 30),
                new Person1("Bob", 25),
                new Person1("Charlie", 35)
        );

        Map<String, Integer> nameToAgeMap = people.stream()
                .collect(Collectors.toMap(Person1::getName, Person1::getAge));

        System.out.println(nameToAgeMap); // Output: {Alice=30, Bob=25, Charlie=35}
    }

    private void flatMap_Example() {
    	System.out.println("------- Using flatMap:  --------");
    	List<Author> authors = Arrays.asList(
                new Author("Author A", Arrays.asList(new Book("Book 1"), new Book("Book 2"))),
                new Author("Author B", Arrays.asList(new Book("Book 3")))
        );
        List<String> allBookTitles = authors.stream()
                .flatMap(author -> author.getBooks().stream()) // flatMap: flatten list of books from each author
                .map(Book::getTitle)                           // map: extract title from each book
                .collect(Collectors.toList());

        System.out.println(allBookTitles); // Output: [Book 1, Book 2, Book 3]
    }
    
    private void multiMap_Example() {
    	System.out.println("------- Using MapMulti:  --------");
    	//https://www.youtube.com/watch?v=SqmO0NM0J98
    	List<String> strList = List.of("1"," ","3");
    	
    	/*
    	List<Integer> intList = strList.stream().map(str-> {
    		return Integer.parseInt(str); // this will return error because we have blank in the list.
    	}).collect(Collectors.toList());
    	*/
    	
    	/*
    	List<Integer> intList = strList.stream()
    			.filter(x -> {
    				// This works but we are doing partInt 2 times and returning true/false
    				try {
    					Integer.parseInt(x);
    					return true;
    				} catch (NumberFormatException e) {
						return false;
					}
    			})
    			.map(str -> {
    				return Integer.parseInt(str);
    			}).collect(Collectors.toList());
    	*/
    	
    	/*
    	// We can acheiving using flatMap. But the issue is we are using stream of stream which is lot of overhead.
    	List<Integer> intList = strList.stream()
    			.flatMap(s -> {
    				try {
    					return Stream.of(Integer.parseInt(s));
    				} catch (NumberFormatException e) {
						return Stream.empty();
					}
    			})
    			.collect(Collectors.toList());
    	*/
    	
    	// BiConsumer will take the value and if it success 
    	//	it will apply which will add to the list.
    	List<Integer> intList = strList.stream()
    			.<Integer> mapMulti((str, consumer) -> {
    				try {
    					consumer.accept(Integer.parseInt(str));
    				} catch (NumberFormatException e) {
						
					}
    			}).collect(Collectors.toList());
    	
        System.out.println(intList); //[1, 3]
    }
}
class Author {
    String name;
    List<Book> books;
    public Author(String name, List<Book> books) {
        this.name = name;
        this.books = books;
    }
    public List<Book> getBooks() {
        return books;
    }
}

class Book {
    String title;
    public Book(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
}

class Person1 {
    String name;
    int age;
    public Person1(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
}