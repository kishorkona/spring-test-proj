package com.test.core.mystreams;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectorsExample {
    public static void main(String[] args) {
        try {
            CollectorsExample ex = new CollectorsExample();
            //ex.collectingAndThen_Method();
            //ex.counting_Method();
            //ex.joining_Method();
            //ex.partitioningBy_Method();
            //ex.reduce_Method();
            //ex.summarizingInt_Method();
            //ex.summingInt_Method();
            ex.groupingBy_Method();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void averagingDouble_Method() {
        System.out.println("------- Collectors.averagingDouble(ToDoubleFunction mapper) --------");
        Stream<String> numbersStream = Stream.of("10.5", "20.0", "30.5");
        Double average = numbersStream
        		.collect(Collectors.averagingDouble(Double::parseDouble));
        System.out.println(average); //20.333333333333332
    }
    private void collectingAndThen_Method() {
        // Difference between max() and Collectors.maxBy()+Collectors.collectingAndThen
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        java.util.Optional<Integer> intVal = numbers.stream()
                .max(Integer::compare);// Use max for direct maximum.
        System.out.println("Max Value="+intVal);

        //Use collectingAndThen when you need "collect, then transform".
        Integer maxPlusOne = numbers.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.maxBy(Integer::compare),
                        opt -> opt.map(i -> i + 1).orElse(null)
                ));
        System.out.println("Max value collect, then transform="+maxPlusOne);
    }
    private void counting_Method() {
        List<String> words = Arrays.asList("apple", "banana", "apple", "orange", "banana", "apple");
        // Count all elements in the stream
        long totalCount = words.stream().collect(Collectors.counting());
        System.out.println("Total elements: " + totalCount); // Output: Total elements: 6

        // Count occurrences of each word
        Map<String, Long> wordCounts = words.stream()
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));
        // Output: Word counts: {banana=2, orange=1, apple=3}
        System.out.println("Word counts: " + wordCounts);
    }
    private void joining_Method() {
        System.out.println("------- joining() & joining(CharSequence delimiter) --------");
        List<String> lettersList = Arrays.asList("a", "b", "c");

        String letter = lettersList.stream().collect(Collectors.joining());
        System.out.println("letter: " + letter); // abc

        String letter1 = lettersList.stream().collect(Collectors.joining(","));
        System.out.println("letter1: " + letter1); // a,b,c

        String letter2 = lettersList.stream().collect(Collectors.joining(",", "[", "]"));
        System.out.println("letter2: " + letter2); // [a,b,c]
    }
    private void partitioningBy_Method() {
        //partitioningBy(Predicate<? super T> predicate)
        List<Integer> lettersList = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        Map<Boolean, List<Integer>> partitions = lettersList.stream()
                .collect(Collectors.partitioningBy(num -> num % 2 == 0));
        System.out.println("Even numbers: " + partitions.get(true)); //[2, 4, 6, 8, 10]
        System.out.println("Odd numbers: " + partitions.get(false)); //[1, 3, 5, 7, 9]

        //partitioningBy(Predicate<? super T> predicate, Collector<? super T, A, D> downstream)
        Stream<String> words = Stream.of("apple", "banana", "cat", "dog", "elephant");
        Map<Boolean, Long> wordLengths = words.collect(
                Collectors.partitioningBy(
                        s -> s.length() > 4, // Predicate: check if word length is greater than 4
                        Collectors.counting() // Downstream collector: count elements in each partition
                )
        );
        System.out.println("Words longer than 4 characters (count): " + wordLengths.get(true));
        System.out.println("Words 4 characters or less (count): " + wordLengths.get(false));
    }
    private void reduce_Method() {
        //reduce(BinaryOperator<T> accumulator)
        List<Integer> lettersList = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        Optional<Integer> sumOfNumbers = lettersList.stream()
                .collect(Collectors.reducing((a, b) -> a+b));
        System.out.println("Sum: " + sumOfNumbers.get()); //15

        //reduce(T identity, BinaryOperator<T> accumulator)
        List<Integer> lettersList1 = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        Integer sumOfNumbers1 = lettersList1.stream()
                .collect(Collectors.reducing(0, (a, b) -> a+b));
        System.out.println("Sum: " + sumOfNumbers1); // 15

        //reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner)
        List<String> words = Arrays.asList("hello", "world", "java");
        String combined = words.stream()
                .reduce("", (acc, word)
                        -> acc + " " + word, (acc1, acc2) -> acc1 + acc2);
        System.out.println("Combined: " + combined.trim()); // Output: Combined: hello world java
    }
    private void summarizingInt_Method() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        // Summarize the numbers directly
        IntSummaryStatistics stats = numbers.stream()
                .collect(Collectors.summarizingInt(Integer::intValue));

        System.out.println("Count="+stats.getCount()+", Min="+stats.getMin()+", Max="+stats.getMax() +
                ", Sum="+stats.getSum()+", Average="+stats.getAverage());

        List<Person2> people = Arrays.asList(
                new Person2("Alice", 30),
                new Person2("Bob", 25),
                new Person2("Charlie", 35)
        );
        // Summarize the ages of people
        IntSummaryStatistics ageStats = people.stream()
                .collect(Collectors.summarizingInt(Person2::getAge));

        System.out.println("\nAge Statistics:");
        System.out.println("Count="+ageStats.getCount()+", Min="+ageStats.getMin()+
                ", Max="+ageStats.getMax() +
                ", Sum="+ageStats.getSum()+", Average="+ageStats.getAverage());
    }
    private void summingInt_Method() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        // Summing a list of Integers directly
        Integer sum1 = numbers.stream()
                .collect(Collectors.summingInt(Integer::intValue));
        // Output: Sum of numbers: 15
        System.out.println("Sum of numbers: " + sum1);

        // Summing a property of custom objects
        List<Product> products = Arrays.asList(
                new Product("Laptop", 1200),
                new Product("Mouse", 50),
                new Product("Keyboard", 100)
        );
        // Summing the prices of products
        Integer totalProductPrice = products.stream()
                .collect(Collectors.summingInt(Product::getPrice));
        // Output: Sum of numbers: 15
        System.out.println("Total product price: " + totalProductPrice);
    }
    private void groupingBy_Method() {
        //https://salithachathuranga94.medium.com/java-8-streams-groupby-b15054d9e6c8
        List<Item1> items = Arrays.asList(
                new Item1("apple", 10, new BigDecimal("9.99")),
                new Item1("banana", 20, new BigDecimal("19.99")),
                new Item1("orange", 10, new BigDecimal("29.99")),
                new Item1("watermelon", 10, new BigDecimal("29.99")),
                new Item1("papaya", 20, new BigDecimal("9.99")),
                new Item1("apple", 30, new BigDecimal("9.99")),
                new Item1("banana", 10, new BigDecimal("19.99")),
                new Item1("apple", 20, new BigDecimal("9.99"))
        );
        System.out.println("- Map<K, List<T>> result groupingBy(Function var0) -");
        Map<String, List<Item1>> rslt = items.stream()
                .collect(Collectors.groupingBy(Item1::getName));
        System.out.println("Items groupedBy Name::"+rslt);

        System.out.println("------- Map<K, D> result groupingBy(Function var0, Collector var1) --");
        // If we want the Fruit names then we use the collectors
        Map<String, Integer> result = items.stream()
                .collect(Collectors.groupingBy(Item1::getName, Collectors.summingInt(Item1::getQty)));
        System.out.println("Items groupedBy Quantity::"+result);

        System.out.println("------- Map<K, D> result groupingBy(Function var0, Supplier var1, Collector var2) -");
        // If we want the Fruit names then we use the collectors and also sort based on fruit names
        Map<String, List<Item1>> rslt1 = items.stream()
                .collect(Collectors.groupingBy(Item1::getName, TreeMap::new, Collectors.toList()));
        System.out.println("Items groupedBy Quantity::"+rslt1);
        //If you want to remove duplicate apples will use "Collectors.toList()" to "Collectors.toSet()"

        Map<Integer, List<String>> result1 = items.stream()
                .collect(Collectors.groupingBy(Item1::getQty,
                        Collectors.mapping(Item1::getName, Collectors.toList())));
        System.out.println("Items groupedBy Quantity with Names::"+result1);
        //Items groupedBy Quantity with Names::{20=[banana, papaya, apple],
        // 10=[apple, orange, watermelon, banana], 30=[apple]}
    }
    
}
class Person2 {
    String name;
    int age;

    public Person2(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}
class Product {
    String name;
    int price;

    Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
class Item1 {
    private String name;
    private int qty;
    private BigDecimal price;
    public Item1(String name, int qty, BigDecimal price) {
        this.name = name;
        this.qty = qty;
        this.price = price;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getQty() {
        return qty;
    }
    public void setQty(int qty) {
        this.qty = qty;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", qty=" + qty +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item1 item = (Item1) o;
        return Objects.equals(name, item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}