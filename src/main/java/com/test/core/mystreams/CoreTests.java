package com.test.core.mystreams;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CoreTests {
    //https://salithachathuranga94.medium.com/java-8-streams-groupby-b15054d9e6c8
    public void testOne() {
        // Group frutes
        List<String> fruitNames = Arrays.asList("apple", "apple", "banana", "apple", "orange", "banana", "papaya");
        Map<String, Long> result = fruitNames.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println(result);
        //{papaya=1, orange=1, banana=2, apple=3}
    }
    public void testTwo() {
        List<Item> items = Arrays.asList(
                new Item("apple", 10, new BigDecimal("9.99")),
                new Item("banana", 20, new BigDecimal("19.99")),
                new Item("orange", 10, new BigDecimal("29.99")),
                new Item("watermelon", 10, new BigDecimal("29.99")),
                new Item("papaya", 20, new BigDecimal("9.99")),
                new Item("apple", 30, new BigDecimal("9.99")),
                new Item("banana", 10, new BigDecimal("19.99")),
                new Item("apple", 20, new BigDecimal("9.99"))
        );

        Map<BigDecimal, List<String>> result2 = items.stream().collect(
                Collectors.groupingBy(Item::getPrice, TreeMap::new, Collectors.mapping(Item::getName, Collectors.toList()))
        );
        System.out.println("Sort Items based on Prices::"+result2);

        /*
        // Items groupedBy Quantity
        Map<String, Set<Item>> rslt = items.stream().collect(Collectors.groupingBy(Item::getName, TreeMap::new, Collectors.toSet()));
        System.out.println("Items groupedBy Quantity::"+rslt);

        // Items groupedBy Quantity
        Map<String, Integer> result = items.stream().collect(Collectors.groupingBy(Item::getName, Collectors.summingInt(Item::getQty)));
        System.out.println("Items groupedBy Quantity::"+result);

        // Group Items based on Quantity
        Map<Integer, List<String>> result1 = items.stream().collect(
                Collectors.groupingBy(Item::getQty, Collectors.mapping(Item::getName, Collectors.toList()))
        );
        System.out.println("Group Items based on Quantity::"+result1);

        // Sort Items based on Prices
        Map<BigDecimal, List<String>> result2 = items.stream().collect(
                Collectors.groupingBy(Item::getPrice, HashMap::new, Collectors.mapping(Item::getName, Collectors.toList()))
        );
        System.out.println("Sort Items based on Prices::"+result2);
        // Sort Items based on Prices and save Inserssion Order
        Map<BigDecimal, List<String>> result3 = items.stream().collect(
                Collectors.groupingBy(Item::getPrice, LinkedHashMap::new, Collectors.mapping(Item::getName, Collectors.toList()))
        );
        System.out.println("Sort Items based on Prices and save Inserssion Order::"+result3);
        */
    }
}
class Item {
    private String name;
    private int qty;
    private BigDecimal price;
    public Item(String name, int qty, BigDecimal price) {
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
        Item item = (Item) o;
        return Objects.equals(name, item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}