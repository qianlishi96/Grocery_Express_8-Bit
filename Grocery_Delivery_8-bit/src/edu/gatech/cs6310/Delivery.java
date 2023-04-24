package edu.gatech.cs6310;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.sqrt;

// Path of drone delivery. Used to generate the path in integer grid map, and real-time position
public class Delivery {

    private Store store;
    private Customer customer;
    private Drone drone;
    private Point storeLocation;
    private Point customerLocation;
    private int speed;
    private double distance;
    private double baseEta;
    private int totalRisk;

    private int timePassed;
    private int timeDelayed;
    private int timeMoved;

    private List<Point> path;
    private Point realTimeLocation;


    // constructor. need store, customer, drone
    // generate path, ETA
    public Delivery(Store store, Order order) {
        this.store = store;
        customer = order.getCustomer();
        drone = order.getDrone();
        timeDelayed = 0;

        storeLocation = store.getLocation();
        customerLocation = customer.getLocation();
        speed = drone.getSpeed();

        // calculates the coordinates of the path between them
        // using the Bresenham's line algorithm
        path = new ArrayList<>();
        int x = storeLocation.x;
        int y = storeLocation.y;
        int dx = Math.abs(customerLocation.x - storeLocation.x);
        int dy = Math.abs(customerLocation.y - storeLocation.y);
        int sx = storeLocation.x < customerLocation.x ? 1 : -1;
        int sy = storeLocation.y < customerLocation.y ? 1 : -1;
        int err = dx - dy;
        while (true) {
            path.add(new Point(x, y));
            if (x == customerLocation.x && y == customerLocation.y) {
                break;
            }
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x += sx;
            }
            if (e2 < dx) {
                err += dx;
                y += sy;
            }
        }

        distance = sqrt(Math.pow(customerLocation.x - storeLocation.x,2) +
                Math.pow(customerLocation.y - storeLocation.y,2));
        baseEta = distance / speed;

    }

    public List<Point> getPath() {
        return path;
    }

    public Point getRealTimeLocation() {
        timeMoved = timePassed - timeDelayed;
//        System.out.println("timePassed:" + timePassed);
//        System.out.println("timeDelayed:" + timeDelayed);
//        System.out.println("timeMoved:" + timeMoved);
        double realTimeX = storeLocation.x + (customerLocation.x - storeLocation.x) * (timeMoved/ baseEta);
        double realTimeY = storeLocation.y + (customerLocation.y - storeLocation.y) * (timeMoved/ baseEta);
        return realTimeLocation = new Point((int) Math.round(realTimeX), (int) Math.round(realTimeY));
    }


    public double getDistance() {
        return distance;
    }

    public double getBaseEta() {
        return baseEta;
    }

    public boolean checkOverlap(Storm storm) {
        for (Point element : path) {
            if (storm.getArea().contains(element)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkStormArea(Storm storm) {
        if (storm.getArea().contains(realTimeLocation)) {
            return true;
        } else {
            return false;
        }
    }

    public void updateStormRisk(Storm storm) {
        int overlap = 0;
        for (Point element : path) {
            if (storm.getArea().contains(element)) {
                overlap ++;
            }
        }
        totalRisk = overlap * storm.getIntensity();
    }

    public void delay(int delay) {
        timeDelayed += delay;
    }

    public void setTimeDelayed(int timeDelayed) {
        this.timeDelayed = timeDelayed;
    }

    public int getTotalRisk() {
        return totalRisk;
    }

    public double getUpdatedEta() {
        return baseEta*((100.0+totalRisk)/100.0)*1.2;
    }

    public int getTimeMoved() {
        return timeMoved = timePassed - timeDelayed;
    }

    public void setTimeMoved(int timePassed) {
        timeMoved = timePassed - timeDelayed;
    }

    public int getTimePassed() {
        return timePassed;
    }

    public void setTimePassed(int timePassed) {
        this.timePassed = timePassed;
    }

    public int getTimeDelayed() {
        return timeDelayed;
    }
}
