package edu.gatech.cs6310;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Storm {

    private String stormId;
//    private float probability;
//    private int frequency;
    private int intensity;
//    private int duration;
    Random rand = new Random();
    private Point center;
    private int radius;
    private List<Point> area;

    public Storm(String stormId, int centerX, int centerY, int radius,
                 int intensity) {
        this.stormId = stormId;
        this.center = new Point(centerX, centerY);
        this.radius = radius;
        this.intensity = intensity;

        area = new ArrayList<>();
        // generate a list of Points, as storm affected area
        for (int i = Math.max(centerX - radius, 0); i <= Math.min(centerX + radius, 9); i ++) {
            for (int j = Math.max(centerY - radius, 0); j <= Math.min(centerY + radius, 9); j ++) {
                area.add(new Point(i, j));
            }
        }
//        System.out.println(getArea());
    }

    public void update(int centerX, int centerY, int radius, int intensity) {
        this.center = new Point(centerX, centerY);
        this.radius = radius;
        this.intensity = intensity;
        area = new ArrayList<>();
        for (int i = Math.max(centerX - radius, 0); i <= Math.min(centerX + radius, 9); i ++) {
            for (int j = Math.max(centerY - radius, 0); j <= Math.min(centerY + radius, 9); j ++) {
                area.add(new Point(i, j));
            }
        }
    }

    // moved to Delivery class - checkOverlap()
    public boolean isDeliveryPathFallsinLightingStormArea (Point customerLocation) {
        if (customerLocation.getX() >=  center.getX()- intensity && customerLocation.getX() <=  center.getX() + intensity &&
                customerLocation.getY() >=  center.getY()- intensity && customerLocation.getY() <=  center.getY() + intensity) {
            return true;
        } else {
            return false;
        }
    }

    // moved to Delivery class - calculateRisk()
//    public boolean isAffectedbyStorm () {
//        if (probability < 0.3 || intensity < 3) {
//            return false;
////        } else if (frequency == "Low") {
////            return false;
//        } else {
//            return true;
//        }
//    }

    public boolean isHitbyStorm () {
        if (rand.nextInt()<0.8) {
            return false;
        } else {
            return true;
        }
    }

//    public void displayStorm() {
//        System.out.println("storeId:" + stormId + ",probability:" + probability + ",frequency:" + frequency + ",intensity:" + intensity + ",duration:" + duration);
//    }

    public void display() {
        System.out.println("stormId:" + stormId + ",center:[x=" + center.x +
                "," + center.y +
                "],radius:" + radius + ",intensity:" + intensity);
    }

    public List<Point> getArea() {
        return area;
    }

    public Point getCenter() {
        return center;
    }

    public String getStormId() {
        return stormId;
    }

    public int getIntensity() {
        return intensity;
    }

    public int getRadius() {
        return radius;
    }
}
