package edu.gatech.cs6310;

public class Line {

    private String identifier;
    private Item item;
    private int quantity;
    private int unitPrice;
    private int cost;
    private int weight;

    public Line (String identifier, Item item, int quantity, int unitPrice) {
        this.identifier = identifier;
        this.item = item;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.cost = quantity * unitPrice;
        this.weight = quantity * item.getWeight();
    }

    public int getQuantity() {
        return quantity;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public int getCost() {
        return cost;
    }

    public int getWeight() {
        return weight;
    }


    public Item getItem() {
        return item;
    }
}
