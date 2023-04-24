package edu.gatech.cs6310;

import java.time.LocalDate;

public class Coupon {

    private String id;
    private int discount;
    private Customer customer;
    private Store store = null;
    private Item item = null;
    private LocalDate expirationDate;
    private boolean used = false;


    //public Coupon () {}

    public Coupon (String id, int discount, Customer customer,
                   LocalDate expirationDate) {
        this.id = id;
        this.discount = discount;
        this.customer = customer;
        this.expirationDate = expirationDate;
    }

    public Coupon (String id, int discount, Customer customer,
                   Store store, LocalDate expirationDate) {
        this.id = id;
        this.discount = discount;
        this.customer = customer;
        this.store = store;
        this.expirationDate = expirationDate;

    }

    public Coupon (String id, int discount, Customer customer,
                   Store store, Item item, LocalDate expirationDate) {
        this.id = id;
        this.discount = discount;
        this.customer = customer;
        this.store = store;
        this.item = item;
        this.expirationDate = expirationDate;
    }


    public void displayCoupon() {
        if (store == null && item == null) {
            System.out.println("CouponId:" + id + ",Discount:$" + discount +
                    ",customer:" + customer.getFirstName() + "_" + customer.getLastName() +
                    ",expiration:" + expirationDate);
        } else if (item == null) {
            System.out.println("CouponId:" + id + ",Discount:$" + discount +
                    ",customer:" + customer.getFirstName() + "_" + customer.getLastName() +
                    ",store:" + store.getName() +
                    ",expiration:" + expirationDate);
        } else {
            System.out.println("CouponId:" + id + ",Discount:$" + discount +
                    ",customer:" + customer.getFirstName() + "_" + customer.getLastName() +
                    ",store:" + store.getName() + ",item:" + item.getName() +
                    ",expiration:" + expirationDate);
        }
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getId() {
        return id;
    }

    public Store getStore() {
        return store;
    }

    public Item getItem() {
        return item;
    }

    public int getDiscount() {
        return discount;
    }

    public boolean isUsed() {
        return used;
    }

    public boolean isValid(LocalDate programDate) {
        return programDate.isBefore(expirationDate);
    }
    public void setUsed(boolean used) {
        this.used = used;
    }
}