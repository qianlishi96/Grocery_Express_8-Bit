package edu.gatech.cs6310;

public class Item {

    private String name;
    private int weight;
    private int basePrice;
    private int isFreeReplacement;

    public Item (String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }
}


