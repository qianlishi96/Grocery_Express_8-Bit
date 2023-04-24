package edu.gatech.cs6310;

import java.util.Map;
import java.util.TreeMap;

public class Drone {

    // private Store store;
    private String identifier;
    private int totalCapacity;
    private int tripsLeft;
    private int remainingCapacity;
    private int numberOfOrders;
    boolean returnStore;
    private Pilot pilot;
    private TreeMap<String, Order> orderMap;

    private int speed = 1;

    private boolean is_crashed = false;

    private boolean isActive;

    public boolean isIs_crashed() {
        return is_crashed;
    }

    private boolean alive;

    public Drone (String identifier, int totalCapacity, int tripsLeft) {
        // this.store = store;
        this.identifier = identifier;
        this.totalCapacity = totalCapacity;
        this.tripsLeft = tripsLeft;
        this.remainingCapacity = totalCapacity;
        this.orderMap = new TreeMap<>();
        this.isActive = true;
        alive = true;
    }

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public void addOrder(Order order) {
        orderMap.put(order.getIdentifier(), order);
    }

    public void removeOrder(Order order) {
        orderMap.remove(order.getIdentifier());
    }

    public int getNumberOfOrders() {
        numberOfOrders = orderMap.size();
        return numberOfOrders;
    }

    public int getRemainingCapacity() {
        remainingCapacity = totalCapacity;
        for(Map.Entry<String, Order> m:orderMap.entrySet()){
            remainingCapacity -= m.getValue().getCurrentOrderWeight();
        }
        return remainingCapacity;
    }

    public int getTripsLeft() {
        return tripsLeft;
    }

//    public boolean isFree() {
//        if (pilot == null) {
//            return true;
//        }
//        else {
//            return false;
//        }
//    }

    public void setPilot(Pilot pilot) {
        this.pilot = pilot;
    }

    public Pilot getPilot() {
        return pilot;
    }

    public void deliver(Order order) {
        tripsLeft -= 1;
        orderMap.remove(order.getIdentifier());
    }

    public TreeMap getOrderMap() {
        return orderMap;
    }

    public void refill() {

    }

    public void setReturnStore(boolean returnStore) {
        this.returnStore = returnStore;
    }

    public int getSpeed() {
        return speed;
    }

    public String getIdentifier() {
        return identifier;
    }


    public boolean getIsActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
