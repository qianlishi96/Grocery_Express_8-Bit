package edu.gatech.cs6310;

//import java.util.*;
//import java.util.Map;
import java.awt.*;
import java.util.Map;
import java.util.TreeMap;

public class Store {

    private String name;
    private int revenue;

    private Point location;

    private TreeMap<String, Item> itemMap;

    private TreeMap<String, Drone> droneMap;
    private TreeMap<String, Order> orderMap;

    private int purchase;
    private int overload;
    private int transfer;
    private boolean isActive;


//    public Store (String name, int revenue, int x, int y) {
//        this.name = name;
//        this.revenue = revenue;
//        this.location = new Point(x, y);
//        itemMap = new TreeMap<>();
//        droneMap = new TreeMap<>();
//        orderMap = new TreeMap<>();
//        purchase = 0;
//        overload = 0;
//        transfer = 0;
//    }

    public Store (String name, int revenue, int x, int y) {
        this.name = name;
        this.revenue = revenue;
        this.location = new Point(x, y);
        itemMap = new TreeMap<>();
        droneMap = new TreeMap<>();
        orderMap = new TreeMap<>();
        purchase = 0;
        overload = 0;
        transfer = 0;
        this.isActive = true;
    }

    public void displayStore() {
        System.out.println("name:" + name + ",revenue:" + revenue +
                ",location:[" + location.x + "," + location.y + "]");
    }

    public boolean checkItemExist(String itemName) {
        return itemMap.containsKey(itemName);
    }

    public void createItem(String name, int weight) {
        Item newItem = new Item(name, weight);
        itemMap.put(name, newItem);
    }

    public void displayItems() {
        for(Map.Entry<String, Item> m:itemMap.entrySet()){
            System.out.println(m.getKey() + "," + m.getValue().getWeight());
        }
    }

    public boolean checkDroneExist(String droneIdentifier) {
        return droneMap.containsKey(droneIdentifier);
    }

    public Item getItem(String item) {
        return itemMap.get(item);
    }

    public void createDrone(String identifier, int totalCapacity, int tripsLeft) {
        Drone newDrone = new Drone(identifier, totalCapacity, tripsLeft);
        droneMap.put(identifier, newDrone);
    }

    public void displayDrones() {
        for(Map.Entry<String, Drone> m:droneMap.entrySet()){
            if (m.getValue().getPilot() == null) {
                System.out.println("droneID:" + m.getKey() + ",total_cap:" + m.getValue().getTotalCapacity() +
                        ",num_orders:" + m.getValue().getNumberOfOrders() + ",remaining_cap:" +
                        m.getValue().getRemainingCapacity() + ",trips_left:" + m.getValue().getTripsLeft());

            } else {
                System.out.println("droneID:" + m.getKey() + ",total_cap:" + m.getValue().getTotalCapacity() +
                        ",num_orders:" + m.getValue().getNumberOfOrders() + ",remaining_cap:" +
                        m.getValue().getRemainingCapacity() + ",trips_left:" + m.getValue().getTripsLeft() +
                        ",flown_by:" + m.getValue().getPilot().getFirstName() + "_" + m.getValue().getPilot().getLastName());
            }
        }
    }

    public Drone getDrone(String drone) {
        return droneMap.get(drone);
    }

    public boolean checkOrderExist(String orderIdentifier) {
        return orderMap.containsKey(orderIdentifier);
    }

    public void createOrder(String identifier, Drone drone, Customer customer) {
        Order newOrder = new Order(identifier, drone, customer);
        orderMap.put(identifier, newOrder);
        drone.addOrder(newOrder);
    }

    public void displayOrders() {
        for(Map.Entry<String, Order> m:orderMap.entrySet()){
            System.out.println("orderID:" + m.getKey());
            m.getValue().displayLines();
            m.getValue().displaySummary();
        }
    }

    public Order getOrder(String order) {
        return orderMap.get(order);
    }

    public void purchase(Order order) {
//        order.getCustomer().pay(order);
        // actually, should verify coupon valid, by the time place order
        for(Map.Entry<String, Coupon> m:order.getAppliedCouponMap().entrySet()){
            m.getValue().setUsed(true);
        }
//        revenue += order.getCurrentOrderCost();
        purchase += 1;
        overload += order.getDrone().getNumberOfOrders();
        order.getDrone().deliver(order);
        order.getDrone().getPilot().deliver();
//        orderMap.remove(order.getIdentifier());
    }

    public void confirmDelivery(Order order) {
        order.getCustomer().pay(order);
        revenue += order.getCurrentOrderCost();
    }

    public TreeMap<String, Order> getOrderMap() {
        return orderMap;
    }

    public void transferOrder(Order order, Drone drone) {
        order.getDrone().removeOrder(order);
        order.updateDrone(drone);
        drone.addOrder(order);
        transfer += 1;
    }

    public void displayEfficiency() {
        System.out.println("name:" + name + ",purchases:" + purchase + ",overloads:" + overload + ",transfers:" +transfer);
    }

    public String getName() {
        return name;
    }

    public Point getLocation() {
        return location;
    }

    public TreeMap<String, Drone> getDroneMap() {
        return droneMap;
    }

    public void setDroneMap(TreeMap<String, Drone> droneMap) {
        this.droneMap = droneMap;
    }



    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
