package edu.gatech.cs6310;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

public class Order {

    private String identifier;
    private Drone drone;
    private Customer customer;
    private Storm storm;
    private TreeMap<String, Line> lineMap;
    private TreeMap<String, Coupon> appliedCouponMap;
    private int currentOrderValue;
    private int currentOrderWeight;
    private int originalETA;

    private int latestETA;

    private LocalDate dsDate;
    private int totalDiscount = 0;
    private int lateDiscount = 10;

    private boolean isActive;
    private Delivery delivery;
    private int pastTimeDelayed;

    public Order (String identifier, Drone drone, Customer customer) {
        this.identifier = identifier;
        this.drone = drone;
        this.customer = customer;
        lineMap = new TreeMap<>();
        appliedCouponMap = new TreeMap<>();
        originalETA = 0;
        latestETA = 0;
        isActive = true;
        this.dsDate  = LocalDate.now();
        pastTimeDelayed = 0;
    }

    public void addTime(boolean isAffectbyStorm){
        originalETA = originalETA * 2;
    }

    public String getIdentifier() {
        return identifier;
    }

    public boolean checkLineExist(String lineName) {
        return lineMap.containsKey(lineName);
    }

    public void createLine(String identifier, Item item, int quantity, int unitPrice) {
        Line newLine = new Line(identifier, item, quantity, unitPrice);
        lineMap.put(identifier, newLine);
    }

    public void displayLines() {
        for(Map.Entry<String, Line> m:lineMap.entrySet()){
            // System.out.println(m.getKey() + "," + m.getValue().getWeight());
            System.out.println("item_name:" + m.getKey() +
                    ",total_quantity:" + m.getValue().getQuantity() +
                    ",total_cost:" + m.getValue().getCost() +
                    ",total_weight:" + m.getValue().getWeight());
        }
    }

    public void displaySummary() {
        if (totalDiscount == 0) {
            System.out.println("order_total_cost:" + getCurrentOrderValue());
        } else {
            System.out.println("order_total_value:" + getCurrentOrderValue() +
                    ",order_total_discount:" + totalDiscount +
                    ",order_total_cost:" + getCurrentOrderCost());
        }
    }

    public int getCurrentOrderValue() {
        currentOrderValue = 0;
        for(Map.Entry<String, Line> m:lineMap.entrySet()){
            currentOrderValue += m.getValue().getCost();
        }
        return currentOrderValue;
    }

    public int getCurrentOrderCost() {
        return Math.max(getCurrentOrderValue() - totalDiscount, 0);
    }

    public boolean checkEnoughBalance(int quantity, int unitPrice) {
        currentOrderValue = getCurrentOrderCost();
        int newLineCost = quantity * unitPrice;
        return customer.getCredit() >= (currentOrderValue + newLineCost);
    }

    public int getCurrentOrderWeight() {
        currentOrderWeight = 0;
        for(Map.Entry<String, Line> m:lineMap.entrySet()){
            currentOrderWeight += m.getValue().getWeight();
        }
        return currentOrderWeight;
    }

    public boolean checkEnoughCapacity(int quantity, Item item) {
        currentOrderWeight = getCurrentOrderWeight();
        int newLineWeight = quantity * item.getWeight();
        return drone.getRemainingCapacity() >= (currentOrderWeight + newLineWeight);
    }

    public Drone getDrone() {
        return drone;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void updateDrone(Drone drone) {
        this.drone = drone;
    }

    public boolean checkItemExist(Item item) {
        for(Map.Entry<String, Line> m:lineMap.entrySet()) {
            if (m.getValue().getItem().equals(item)) {
                return true;
            }
        }
        return false;
    }

    public void applyDiscount(int discount) {
        totalDiscount += discount;
    }

    public void applyCoupon(Coupon coupon) {
        appliedCouponMap.put(coupon.getId(), coupon);
        applyDiscount(coupon.getDiscount());
    }

    public TreeMap<String, Coupon> getAppliedCouponMap() {
        return appliedCouponMap;
    }


    public void setOriginalETA(int originalETA) {
        this.originalETA = originalETA;
    }

    public void setLatestETA(int latestETA) {
        this.latestETA = latestETA;
    }

    public int getOriginalETA() {
        return originalETA;
    }

    public int getLatestETA() {
        return latestETA;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void deliveryLate() {
        applyDiscount(lateDiscount);
    }

    public LocalDate getDsDate() {
        return dsDate;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public int getPastTimeDelayed() {
        return pastTimeDelayed;
    }

    public void setPastTimeDelayed(int pastTimeDelayed) {
        this.pastTimeDelayed = pastTimeDelayed;
    }
}
