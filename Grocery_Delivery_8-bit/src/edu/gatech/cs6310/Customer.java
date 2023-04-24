package edu.gatech.cs6310;

import java.awt.*;
import java.time.LocalDate;
import java.util.TreeMap;

public class Customer {

    private String account;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private int rating;
    private int credit;

    private LocalDate dsDate;

    private boolean isActive;
    private Point location;



    private TreeMap<String, Order> pendingOrderMap;
    private int pendingOrderCost;

    public Customer (String account, String firstName, String lastName, String phoneNumber,
                     int rating, int credits, int x, int y) {
        this.account = account;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.rating = rating;
        this.credit = credits;
        this.location = new Point(x, y);
        this.isActive = true;
        this.dsDate  = LocalDate.now();
    }

    public void displayCustomer() {
        System.out.println("name:" + firstName + "_" + lastName + ",phone:" + phoneNumber +
                ",rating:" + rating + ",credit:" + credit +
                ",location:[" + location.x + "," + location.y + "]");
    }

    public int getCredit() {
        return credit;
    }

    public int getRating() {
        return rating;
    }

    public void pay(Order order) {
        credit -= order.getCurrentOrderCost();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Point getLocation() {
        return location;
    }

    public String getAccount() {
        return account;
    }

    public void setIsActive(boolean active_status) {
        isActive = active_status;
    }
    public boolean getIsActive() {
        return isActive;
    }

    public LocalDate getDsDate() {
        return dsDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}

