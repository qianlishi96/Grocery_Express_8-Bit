package edu.gatech.cs6310;

//import java.util.*;

import java.util.Map;

public class Pilot {


    private String account;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String taxID;
    private String licenseID;
    private int experienceLevel;
    private int experienceTime;
    private int salary;

    private boolean isActive;
    private Drone drone;

    public Pilot (String account, String firstName, String lastName, String phoneNumber, String taxID, String licenseID, int experienceLevel) {
        this.account = account;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.taxID = taxID;
        this.licenseID = licenseID;
        this.experienceLevel = experienceLevel;
        this.isActive = true;
    }

    public void displayPilot() {
        System.out.println("name:" + firstName + "_" + lastName + ",phone:" + phoneNumber + ",taxID:" + taxID + ",licenseID:" + licenseID + ",experience:" + experienceLevel);
    }

//    public boolean isFree() {
//        if (drone == null) {
//            return true;
//        }
//        else {
//            return false;
//        }
//    }

    public void setDrone(Drone drone) {
        this.drone = drone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void deliver() {
        experienceLevel += 1;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }


}
