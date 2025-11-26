package com.work.core.mystreams;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;


public class TestingStreams {
	public static void main(String[] args) {
		TestingStreams obj = new TestingStreams();
		obj.execuse();
	}
	public void execuse() {
		/*
		buildData()
		.stream()
		.sorted(
				Comparator.comparing(TestItem::getPrice)
				.thenComparing(Comparator.comparing(TestItem::getColor))
				.thenComparing(Comparator.comparing(TestItem::getName))
				)
		.forEach(ex -> {
			System.out.println(ex);
		});
		*/
		buildData()
		.stream()
		.sorted((Comparator.comparingInt(ti -> {
            return ti.getClass().getName().length();
        })).reversed())
		.forEach(ex -> {
			System.out.println(ex);
		});
		
	}
	public List<TestItem> buildData() {
		return Arrays.asList(
                new TestItem("apple", "Red", 10, new BigDecimal("9.99")),
                new TestItem("banana", "Green", 20, new BigDecimal("19.99")),
                new TestItem("orange", "Green", 10, new BigDecimal("29.99")),
                new TestItem("watermelon", "White", 10, new BigDecimal("29.99")),
                new TestItem("papaya", "Yelow", 20, new BigDecimal("9.99")),
                new TestItem("APPLE", "Green", 30, new BigDecimal("9.99")),
                new TestItem("BANANA", "Yellow", 10, new BigDecimal("19.99")),
                new TestItem("apple", "Yellow", 20, new BigDecimal("9.99"))
        );
	}
}
class TestItem {
    private String name;
    private String color;
    private int qty;
    private BigDecimal price;
    public TestItem(String name, String color, int qty, BigDecimal price) {
        this.name = name;
        this.color = color;
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
    public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	@Override
	public String toString() {
		return "TestItem [name=" + name + ", color=" + color + ", qty=" + qty + ", price=" + price + "]";
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestItem item = (TestItem) o;
        return Objects.equals(name, item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}