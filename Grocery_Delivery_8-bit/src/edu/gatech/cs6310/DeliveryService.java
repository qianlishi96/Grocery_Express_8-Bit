package edu.gatech.cs6310;

import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
// import java.util.*;


public class DeliveryService {

    private TreeMap<String, Store> storeMap;
    private TreeMap<String, Pilot> pilotMap;
    private TreeMap<String, String> pilotLicenseMap;
    private TreeMap<String, Customer> customerMap;
    private TreeMap<String, Point> locationMap;
    private TreeMap<String, Coupon> couponMap;
    private TreeMap<String, Storm> stormMap;

    private int randomCoupon;
    private int newCouponCount;

//    private StringBuilder str = new StringBuilder();


    /*Path tempDirectory = Path.of("");
    private Path createFile(String args) {
        return createFile(args, "file.txt");
    }
    private Path createFile(String contents, String fileName) {
        Path file = tempDirectory.resolve(fileName);
        try {
            Files.write(file, contents.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            errorMessage();
        }
        return file;
    }
    //ERROR MESSAGE
    private static void errorMessage() {
        System.err.println("ERROR");
    }
    //READ A FILE
//    private static String getFileContent(Path file) {
//        try {
//            return Files.readString(file, StandardCharsets.UTF_8);
//        } catch (IOException e) {
//            return "ERROR";
//        }
//    }
    private static void writeToFIle(String[] args) {
        String content = "";
        String filePath = "";
        StringBuilder str = new StringBuilder();
        try {
            File file = new File(filePath);
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    private void setFilePath(String filePath){
        this.filePath = filePath;
    }

    private String getFileContent(Path file){
        try{
            return Files.readString(file, StandardCharsets.UTF_8);
        }
        catch(IOException e){
            return "ERROR";
        }
    }

    *//*private void processString(String[] fileLine, String input1, String input2){
//        StringBuilder str = new StringBuilder();
//        for (int i = 0; i < fileLine.length; i++){
//            str.append("(").append(input1).append(", ").append(input2).append(")").append(System.lineSeparator());
//            Path storeFile = createFile(str.toString(), "store_file.txt");
//        }
    }*/

    public void commandLoop() throws IOException, ParseException {
        Scanner commandLineInput = new Scanner(System.in);
        String wholeInputLine;
        String[] tokens;
        final String DELIMITER = ",";

        storeMap = new TreeMap<>();
        pilotMap = new TreeMap<>();
        pilotLicenseMap = new TreeMap<>();
        customerMap = new TreeMap<>();
        locationMap = new TreeMap<>();
        couponMap = new TreeMap<>();
        stormMap = new TreeMap<>();

        randomCoupon = 0;

        // default date, if not updated in operating
        LocalDate programDate = LocalDate.now();
        LocalTime programTime = LocalTime.now();

        while (true) {
            try {
                // Determine the next command and echo it to the monitor for testing purposes
                wholeInputLine = commandLineInput.nextLine();
                tokens = wholeInputLine.split(DELIMITER);
                System.out.println("> " + wholeInputLine);

                // ██████  ██      ██████      ██    ██ ███████ ███████      ██████  █████  ███████ ███████ ███████
                //██    ██ ██      ██   ██     ██    ██ ██      ██          ██      ██   ██ ██      ██      ██
                //██    ██ ██      ██   ██     ██    ██ ███████ █████       ██      ███████ ███████ █████   ███████
                //██    ██ ██      ██   ██     ██    ██      ██ ██          ██      ██   ██      ██ ██           ██
                // ██████  ███████ ██████       ██████  ███████ ███████      ██████ ██   ██ ███████ ███████ ███████
                //https://patorjk.com/software/taag/#p=display&v=0&f=ANSI%20Regular&t=

                // old user input "make_store,kroger,33000"
                // new user input "make_store,kroger,33000,2,2"
                if (tokens[0].equals("make_store")) {
                    if (storeMap.containsKey(tokens[1])) {
                        System.out.println("ERROR:store_identifier_already_exists");
                    } else if (Integer.parseInt(tokens[3]) < 0 || Integer.parseInt(tokens[3]) > 9 ||
                            Integer.parseInt(tokens[4]) < 0 || Integer.parseInt(tokens[4]) > 9) {
                        System.out.println("ERROR:location_out_of_the_map");
                    } else if (locationMap.containsValue(new Point(Integer.parseInt(tokens[3]),
                            Integer.parseInt(tokens[4])))) {
                        System.out.println("ERROR:location_already_occupied");
                    } else {
                        Store newStore = new Store(tokens[1], Integer.parseInt(tokens[2]),
                                Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]));
                        StringBuilder str = new StringBuilder();
                        //write store info to json
                        str.append("{").
                                append("store:").append(tokens[1]).append(", ").
                                append("revenue:").append(tokens[2]).append(", ").
                                append("location_x:").append(tokens[3]).append(", ").
                                append("location_y:").append(tokens[4]).append(", ").
                                append("isActive:true").append("}");
                        Utility.writeJSON(tokens[1], String.valueOf(str),"store.json");

                        storeMap.put(tokens[1], newStore);
                        Point newPoint = new Point(Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]));
                        locationMap.put(tokens[1].toUpperCase(), newPoint);
                        System.out.println("OK:change_completed");
                    }
                }

                else if (tokens[0].equals("display_stores")) {
                    for(Map.Entry<String, Store> m:storeMap.entrySet()){
                        m.getValue().displayStore();
                    }
                    System.out.println("OK:display_completed");
                }

                else if (tokens[0].equals("sell_item")) {
                    if (!storeMap.containsKey(tokens[1])) {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    } else {
                        Store store = storeMap.get(tokens[1]);
                        if (store.checkItemExist(tokens[2])) {
                            System.out.println("ERROR:item_identifier_already_exists");
                        } else {
                            store.createItem(tokens[2], Integer.parseInt(tokens[3]));
                            System.out.println("OK:change_completed");
                        }
                    }
                }

                else if (tokens[0].equals("display_items")) {
                    if (!storeMap.containsKey(tokens[1])) {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    } else {
                        Store store = storeMap.get(tokens[1]);
                        store.displayItems();
                        System.out.println("OK:display_completed");
                    }
                }

                //123, kk, mm, 1234567, 131-121-191,K782,7, true
                else if (tokens[0].equals("make_pilot")) {
                    if (pilotMap.containsKey(tokens[1])) {
                        System.out.println("ERROR:pilot_identifier_already_exists");
                    } else if (pilotLicenseMap.containsValue(tokens[6])) {
                        System.out.println("ERROR:pilot_license_already_exists");
                    } else {
                        Pilot newPilot = new Pilot(tokens[1], tokens[2], tokens[3], tokens[4],
                                tokens[5], tokens[6], Integer.parseInt(tokens[7]));

                        //write pilot info to json
                        StringBuilder str = new StringBuilder();
                        str.append("{").
                                append("account:").append(tokens[1]).append(", ").
                                append("first_name:").append(tokens[2]).append(", ").
                                append("last_name:").append(tokens[3]).append(", ").
                                append("phone:").append(tokens[4]).append(", ").
                                append("taxID:").append(tokens[5]).append(", ").
                                append("license:").append(tokens[6]).append(", ").
                                append("experience:").append(tokens[7]).append(", ").
                                append("isActive:true").append("}");
                        Utility.writeJSON(tokens[1], String.valueOf(str),"pilot.json");

                        pilotMap.put(tokens[1], newPilot);
                        pilotLicenseMap.put(tokens[1], tokens[6]);
                        System.out.println("OK:change_completed");
                    }
                }

                else if (tokens[0].equals("display_pilots")) {
                    for(Map.Entry<String, Pilot> m:pilotMap.entrySet()){
                        m.getValue().displayPilot();
                    }
                    System.out.println("OK:display_completed");
                }

                else if (tokens[0].equals("make_drone")) {
                    if (!storeMap.containsKey(tokens[1])) {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    } else {Store store = storeMap.get(tokens[1]);
                        if (store.checkDroneExist(tokens[2])) {
                            System.out.println("ERROR:drone_identifier_already_exists");
                        } else {
                            store.createDrone(tokens[2], Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]));

                            //write drone info to json
                            StringBuilder str = new StringBuilder();
                            str.append("{").
                                    append("store:").append(tokens[1]).append(", ").
                                    append("drone:").append(tokens[2]).append(", ").
                                    append("capacity").append(tokens[3]).append(", ").
                                    append("fuel").append(tokens[4]).append(", ").
                                    append("isActive: true").append("}");
                            Utility.writeJSON(tokens[2], String.valueOf(str),"drone.json");
                            System.out.println("OK:change_completed");
                        }
                    }
                }

                else if (tokens[0].equals("display_drones")) {
                    if (!storeMap.containsKey(tokens[1])) {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    } else {
                        Store store = storeMap.get(tokens[1]);
                        store.displayDrones();
                        System.out.println("OK:display_completed");
                    }
                }

                else if (tokens[0].equals("fly_drone")) {
                    if (!storeMap.containsKey(tokens[1])) {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    } else {
                        Store store = storeMap.get(tokens[1]);
                        if (!store.checkDroneExist(tokens[2])) {
                            System.out.println("ERROR:drone_identifier_does_not_exist");
                        } else {
                            Drone drone = store.getDrone(tokens[2]);
                            if (!pilotMap.containsKey(tokens[3])) {
                                System.out.println("ERROR:pilot_identifier_does_not_exist");
                            } else {
                                Pilot pilot = pilotMap.get(tokens[3]);
                                pilot.setDrone(drone);
                                drone.setPilot(pilot);
                                System.out.println("OK:change_completed");
                            }
                        }
                    }
                }

                // old user input "make_customer,aapple2,Alana,Apple,222-222-2222,4,100"
                // new user input "make_customer,aapple2,Alana,Apple,222-222-2222,4,100,7,3"
                else if (tokens[0].equals("make_customer")) {
                    if (customerMap.containsKey(tokens[1])) {
                        System.out.println("ERROR:customer_identifier_already_exists");
                    } else if (Integer.parseInt(tokens[7]) < 0 || Integer.parseInt(tokens[7]) > 9 ||
                            Integer.parseInt(tokens[8]) < 0 || Integer.parseInt(tokens[8]) > 9) {
                        System.out.println("ERROR:location_out_of_the_map");
                    } else if (locationMap.containsValue(new Point(Integer.parseInt(tokens[7]),
                            Integer.parseInt(tokens[8])))) {
                        System.out.println("ERROR:location_already_occupied");
                    } else {
                        Customer newCustomer = new Customer(tokens[1], tokens[2], tokens[3],
                                tokens[4], Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6]),
                                Integer.parseInt(tokens[7]), Integer.parseInt(tokens[8]));

                        //write customer info to json
                        StringBuilder str = new StringBuilder();
                        str.append("{").
                                append("account:").append(tokens[1]).append(", ").
                                append("firstName:").append(tokens[2]).append(", ").
                                append("lastName:").append(tokens[3]).append(", ").
                                append("phoneNumber:").append(tokens[4]).append(", ").
                                append("rating:").append(tokens[5]).append(", ").
                                append("credit:").append(tokens[6]).append(", ").
                                append("location_x:").append(tokens[7]).append(", ").
                                append("location_y:").append(tokens[8]).append(", ").
                                append("localTime:").append(newCustomer.getDsDate()).append(", ").
                                append("isActive: true").append("}");
                        Utility.writeJSON(tokens[1], String.valueOf(str),"customer.json");

                        customerMap.put(tokens[1], newCustomer);
//                        System.out.println(tokens[7]);
//                        int x = Integer.parseInt(tokens[7]);
//                        int y = Integer.parseInt(tokens[8]);
                        Point newPoint = new Point(Integer.parseInt(tokens[7]), Integer.parseInt(tokens[8]));
                        locationMap.put(tokens[1], newPoint);
                        System.out.println("OK:change_completed");
                    }
                }

                else if (tokens[0].equals("display_customers")) {
                    for(Map.Entry<String, Customer> m:customerMap.entrySet()){
                        m.getValue().displayCustomer();
                    }
                    System.out.println("OK:display_completed");
                }

                //start_order, kroger, order_1, drone_1, kk
                else if (tokens[0].equals("start_order")) {
                    if (!storeMap.containsKey(tokens[1])) {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    } else {
                        Store store = storeMap.get(tokens[1]);
                        if (store.checkOrderExist(tokens[2])) {
                            System.out.println("ERROR:order_identifier_already_exists");
                        } else {
                            if (!store.checkDroneExist(tokens[3])) {
                                System.out.println("ERROR:drone_identifier_does_not_exist");
                            } else {
                                Drone drone = store.getDrone(tokens[3]);
                                if (!customerMap.containsKey(tokens[4])) {
                                    System.out.println("ERROR:customer_identifier_does_not_exist");
                                } else {
                                    Customer customer = customerMap.get(tokens[4]);
                                    store.createOrder(tokens[2], drone, customer);

                                    //write order info to json
                                    StringBuilder str = new StringBuilder();
                                    str.append("{").
                                            append("store:").append(tokens[1]).append(", ").
                                            append("order:").append(tokens[2]).append(", ").
                                            append("drone").append(tokens[3]).append(", ").
                                            append("customer").append(tokens[4]).append(", ").
                                            append("localTime:").append(store.getOrder(tokens[2]).getDsDate()).append(", ").
                                            append("isActive: true").append("}");
                                    Utility.writeJSON(tokens[2], String.valueOf(str),"order.json");


                                    System.out.println("OK:change_completed");
                                }
                            }
                        }
                    }
                }

                else if (tokens[0].equals("display_orders")) {
                    if (!storeMap.containsKey(tokens[1])) {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    } else {
                        Store store = storeMap.get(tokens[1]);
                        store.displayOrders();
                        System.out.println("OK:display_completed");
                    }
                }

                else if (tokens[0].equals("request_item")) {
                    if (!storeMap.containsKey(tokens[1])) {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    } else {
                        Store store = storeMap.get(tokens[1]);
                        if (!store.checkOrderExist(tokens[2])) {
                            System.out.println("ERROR:order_identifier_does_not_exist");
                        } else {
                            Order order = store.getOrder(tokens[2]);
                            if (!store.checkItemExist(tokens[3])) {
                                System.out.println("ERROR:item_identifier_does_not_exist");
                            } else {
                                Item item = store.getItem(tokens[3]);
                                if (order.checkLineExist(tokens[3])) {
                                    System.out.println("ERROR:item_already_ordered");
                                } else {
                                    if (!order.checkEnoughBalance(Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]))) {
                                        System.out.println("ERROR:customer_cant_afford_new_item");
                                    } else {
                                        if (!order.checkEnoughCapacity(Integer.parseInt(tokens[4]), item)) {
                                            System.out.println("ERROR:drone_cant_carry_new_item");
                                        } else {
                                            order.createLine(tokens[3], item, Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]));
                                            System.out.println("OK:change_completed");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                else if (tokens[0].equals("purchase_order")) {
                    if (!storeMap.containsKey(tokens[1])) {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    } else {
                        Store store = storeMap.get(tokens[1]);
                        if (!store.checkOrderExist(tokens[2])) {
                            System.out.println("ERROR:order_identifier_does_not_exist");
                        } else {
                            Order order = store.getOrder(tokens[2]);
                            Drone drone = order.getDrone();
                            Customer customer = order.getCustomer();
                            if (drone.getPilot() == null) {
                                System.out.println("ERROR:drone_needs_pilot");
                            }
                            else if (drone.getTripsLeft() <= 0) {
                                System.out.println("ERROR:drone_needs_fuel");
                            }
                            else {
                                store.purchase(order);

//                                if (storm.isDeliveryPathFallsinLightingStormArea(customer.getLocation())){
//                                    order.setOriginalETA((int) sqrt(((int) customer.getLocation().getX()^2)+((int) customer.getLocation().getX()^2))*2);
//                                    order.setLatestETA(order.getOriginalETA());
//                                }
//                                else {
//                                    order.setOriginalETA((int) sqrt(((int) customer.getLocation().getX()^2)+((int) customer.getLocation().getX()^2)));
//                                    order.setLatestETA(order.getOriginalETA());
//                                }
                                System.out.println("OK:change_completed");
                            }
                        }
                    }
                }

                else if (tokens[0].equals("cancel_order")) {
                    if (!storeMap.containsKey(tokens[1])) {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    } else {
                        Store store = storeMap.get(tokens[1]);
                        if (!store.checkOrderExist(tokens[2])) {
                            System.out.println("ERROR:order_identifier_does_not_exist");
                        } else {
                            Order order = store.getOrder(tokens[2]);
                            order.getDrone().getOrderMap().remove(order.getIdentifier());
                            store.getOrderMap().remove(order.getIdentifier());
                            System.out.println("OK:change_completed");
                        }
                    }
                }

                else if (tokens[0].equals("transfer_order")) {
                    if (!storeMap.containsKey(tokens[1])) {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    } else {
                        Store store = storeMap.get(tokens[1]);
                        if (!store.checkOrderExist(tokens[2])) {
                            System.out.println("ERROR:order_identifier_does_not_exist");
                        } else {
                            Order order = store.getOrderMap().get(tokens[2]);
                            if (!store.checkDroneExist(tokens[3])) {
                                System.out.println("ERROR:drone_identifier_does_not_exist");
                            } else {
                                Drone drone = store.getDrone(tokens[3]);
                                if (drone.getRemainingCapacity() < order.getCurrentOrderWeight()) {
                                    System.out.println("ERROR:new_drone_does_not_have_enough_capacity");
                                } else {
                                    if (drone == order.getDrone()) {
                                        System.out.println("OK:new_drone_is_current_drone_no_change");
                                    } else {
                                        store.transferOrder(order, drone);
                                        System.out.println("OK:change_completed");
                                    }
                                }
                            }
                        }
                    }
                }

                else if (tokens[0].equals("display_efficiency")) {
                    for(Map.Entry<String, Store> m:storeMap.entrySet()){
                        m.getValue().displayEfficiency();
                    }
                    System.out.println("OK:display_completed");
                }

                //███████ ████████  ██████  ██████  ███    ███
                //██         ██    ██    ██ ██   ██ ████  ████
                //███████    ██    ██    ██ ██████  ██ ████ ██
                //     ██    ██    ██    ██ ██   ██ ██  ██  ██
                //███████    ██     ██████  ██   ██ ██      ██

                // user input "make_storm,storm1,3,4,1,5"
                else if (tokens[0].equals("make_storm")) {
                    if (stormMap.containsKey(tokens[1])) {
                        System.out.println("ERROR:storm_identifier_already_exists");
                    } else {
                        Storm newStorm = new Storm(tokens[1],
                                Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]),
                                Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]));
                        stormMap.put(tokens[1], newStorm);
                        System.out.println("OK:change_completed");
                    }
                }

                // user input "update_storm,st1,7,1,1,5"
                else if (tokens[0].equals("update_storm")) {
                    if (!stormMap.containsKey(tokens[1])) {
                        System.out.println("ERROR:storm_identifier_does_not_exist");
                    } else {
                        Storm storm = stormMap.get(tokens[1]);
                        storm.update(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]),
                                Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]));
                        System.out.println("OK:change_completed");
                    }
                }

                // user input "remove_storm,st2"
                else if (tokens[0].equals("remove_storm")) {
                    if (!stormMap.containsKey(tokens[1])) {
                        System.out.println("ERROR:storm_identifier_does_not_exist");
                    } else {
                        Storm storm = stormMap.get(tokens[1]);
                        stormMap.remove(storm.getStormId());
                        System.out.println("OK:change_completed");
                    }
                }

                else if (tokens[0].equals("display_storms")) {
                    for(Map.Entry<String, Storm> m:stormMap.entrySet()){
                        Storm storm = m.getValue();
                        storm.display();
                    }
                    System.out.println("OK:display_completed");
                }

                // user input "struck_by_storm,kroger,kr1"
                else if (tokens[0].equals("struck_by_storm")) {
                    if (!storeMap.containsKey(tokens[1])) {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    } else {
                        Store store = storeMap.get(tokens[1]);
                        if (!store.checkDroneExist(tokens[2])) {
                            System.out.println("ERROR:drone_identifier_does_not_exist");
                        } else {
                            Drone drone = store.getDrone(tokens[2]);
                            drone.setAlive(false);
                            System.out.println("WARNING:drone_struck_by_storm,prepare_new_delivery_immediately");
                        }
                    }
                }

                // ██████  ██████  ██    ██ ██████   ██████  ███    ██
                //██      ██    ██ ██    ██ ██   ██ ██    ██ ████   ██
                //██      ██    ██ ██    ██ ██████  ██    ██ ██ ██  ██
                //██      ██    ██ ██    ██ ██      ██    ██ ██  ██ ██
                // ██████  ██████   ██████  ██       ██████  ██   ████

                // user input "make_general_coupon_random,10,5,2023-05-21"
                // tokens[2] stands for 1% winning rate, per customer's rating
                // DS will automatically generate couponID (up to 9999)
                else if (tokens[0].equals("make_general_coupon_random")) {
                    Random random = new Random();
                    newCouponCount = 0;
                    for(Map.Entry<String, Customer> m:customerMap.entrySet()){
                        Customer customer = m.getValue();
                        if (Integer.parseInt(tokens[1])*customer.getRating() > random.nextInt(100)) {
                            String couponId = "GCR" + String.format("%04d", randomCoupon);
                            randomCoupon += 1;
                            newCouponCount += 1;
                            Coupon newCoupon = new Coupon(couponId, Integer.parseInt(tokens[2]),
                                    customer, LocalDate.parse(tokens[3]));
                            couponMap.put(couponId, newCoupon);
                        }
                    }
                    System.out.println("Generated_" + newCouponCount + "_new_coupons");
                    System.out.println("OK:change_completed");
                    System.out.println("OK:txt_message_sent,customer_notified");
                }

                // user input "make_store_coupon_random,10,5,kroger,2023-05-21"
                else if (tokens[0].equals("make_store_coupon_random")) {
                    if (!storeMap.containsKey(tokens[3])) {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    } else {
                        Store store = storeMap.get(tokens[3]);
                        Random random = new Random();
                        newCouponCount = 0;
                        for (Map.Entry<String, Customer> m : customerMap.entrySet()) {
                            Customer customer = m.getValue();
                            if (Integer.parseInt(tokens[1]) * customer.getRating() > random.nextInt(100)) {
                                String couponId = "SCR" + String.format("%04d", randomCoupon);
                                randomCoupon += 1;
                                newCouponCount += 1;
                                Coupon newCoupon = new Coupon(couponId, Integer.parseInt(tokens[2]),
                                        customer, store, LocalDate.parse(tokens[4]));
                                couponMap.put(couponId, newCoupon);
                            }
                        }
                        System.out.println("Generated_" + newCouponCount + "_new_coupons");
                        System.out.println("OK:change_completed");
                        System.out.println("OK:txt_message_sent,customer_notified");

                    }
                }

                // user input "make_item_coupon_random,10,5,kroger,pot_roast,2023-05-21"
                else if (tokens[0].equals("make_item_coupon_random")) {
                    if (!storeMap.containsKey(tokens[3])) {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    } else {
                        Store store = storeMap.get(tokens[3]);
                        if (!store.checkItemExist(tokens[4])) {
                            System.out.println("ERROR:item_identifier_does_not_exist");
                        } else {
                            Item item = store.getItem(tokens[4]);
                            Random random = new Random();
                            newCouponCount = 0;
                            for (Map.Entry<String, Customer> m : customerMap.entrySet()) {
                                Customer customer = m.getValue();
                                if (Integer.parseInt(tokens[1]) * customer.getRating() > random.nextInt(100)) {
                                    String couponId = "ICR" + String.format("%04d", randomCoupon);
                                    randomCoupon += 1;
                                    newCouponCount += 1;
                                    Coupon newCoupon = new Coupon(couponId, Integer.parseInt(tokens[2]),
                                            customer, store, item, LocalDate.parse(tokens[5]));
                                    couponMap.put(couponId, newCoupon);
                                }
                            }
                            System.out.println("Generated_" + newCouponCount + "_new_coupons");
                            System.out.println("OK:change_completed");
                            System.out.println("OK:txt_message_sent,customer_notified");
                        }
                    }
                }

                // user input "returning_customer_coupon,GC0001,5,aapple2,yyyy-mm-dd"
                else if (tokens[0].equals("returning_customer_coupon")) {
                    if (couponMap.containsKey(tokens[1])) {
                        System.out.println("ERROR:coupon_identifier_already_exists");
                    } else {
                        if (!customerMap.containsKey(tokens[3])) {
                            System.out.println("ERROR:customer_identifier_does_not_exist");
                        } else {
                            Customer customer = customerMap.get(tokens[3]);
                            Coupon newCoupon = new Coupon(tokens[1], Integer.parseInt(tokens[2]),
                                    customer, LocalDate.parse(tokens[4]));
                            couponMap.put(tokens[1], newCoupon);
                            System.out.println("OK:change_completed");
                            System.out.println("OK:txt_message_sent,customer_notified");
                        }
                    }
                }

                // user input "make_general_coupon,GC0001,5,aapple2,yyyy-mm-dd"
                else if (tokens[0].equals("make_general_coupon")) {
                    if (couponMap.containsKey(tokens[1])) {
                        System.out.println("ERROR:coupon_identifier_already_exists");
                    } else {
                        if (!customerMap.containsKey(tokens[3])) {
                            System.out.println("ERROR:customer_identifier_does_not_exist");
                        } else {
                            Customer customer = customerMap.get(tokens[3]);
                            Coupon newCoupon = new Coupon(tokens[1], Integer.parseInt(tokens[2]),
                                    customer, LocalDate.parse(tokens[4]));
                            couponMap.put(tokens[1], newCoupon);
                            System.out.println("OK:change_completed");
                            System.out.println("OK:txt_message_sent,customer_notified");
                        }
                    }
                }

                // user input "make_store_coupon,SC0001,5,aapple2,kroger,yyyy-mm-dd"
                else if (tokens[0].equals("make_store_coupon")) {
                    if (couponMap.containsKey(tokens[1])) {
                        System.out.println("ERROR:coupon_identifier_already_exists");
                    } else {
                        if (!customerMap.containsKey(tokens[3])) {
                            System.out.println("ERROR:customer_identifier_does_not_exist");
                        } else {
                            Customer customer = customerMap.get(tokens[3]);
                            if (!storeMap.containsKey(tokens[4])) {
                                System.out.println("ERROR:store_identifier_does_not_exist");
                            } else {
                                Store store = storeMap.get(tokens[4]);
                                Coupon newCoupon = new Coupon(tokens[1], Integer.parseInt(tokens[2]),
                                        customer, store, LocalDate.parse(tokens[5]));
                                couponMap.put(tokens[1], newCoupon);
                                System.out.println("OK:change_completed");
                                System.out.println("OK:txt_message_sent,customer_notified");
                            }
                        }
                    }
                }

                // user input "make_item_coupon,IC0001,5,aapple2,kroger,cheesecake,yyyy-mm-dd"
                else if (tokens[0].equals("make_item_coupon")) {
                    if (couponMap.containsKey(tokens[1])) {
                        System.out.println("ERROR:coupon_identifier_already_exists");
                    } else {
                        if (!customerMap.containsKey(tokens[3])) {
                            System.out.println("ERROR:customer_identifier_does_not_exist");
                        } else {
                            Customer customer = customerMap.get(tokens[3]);
                            if (!storeMap.containsKey(tokens[4])) {
                                System.out.println("ERROR:store_identifier_does_not_exist");
                            } else {
                                Store store = storeMap.get(tokens[4]);
                                if (!store.checkItemExist(tokens[5])) {
                                    System.out.println("ERROR:item_identifier_does_not_exist");
                                } else {
                                    Item item = store.getItem(tokens[5]);
                                    Coupon newCoupon = new Coupon(tokens[1], Integer.parseInt(tokens[2]),
                                            customer, store, item, LocalDate.parse(tokens[6]));
                                    couponMap.put(tokens[1], newCoupon);
                                    System.out.println("OK:change_completed");
                                    System.out.println("OK:txt_message_sent,customer_notified");
                                }
                            }
                        }
                    }
                }

                // user input "notify,customer,notification_message"
                else if (tokens[0].equals("notify")) {
                    String notification = tokens[2];
                    Customer customer = customerMap.get(tokens[1]);
                    String phoneNumber = customer.getPhoneNumber();
                    // notify(customer, notification);
                    // customer receives a txt message
                    // telecommunication is beyond the scope of this project.
                    System.out.println("OK:txt_message_sent,customer_notified");
                }

                else if (tokens[0].equals("display_coupons")) {
                    for(Map.Entry<String, Coupon> m:couponMap.entrySet()){
                        m.getValue().displayCoupon();
                    }
                    System.out.println("OK:display_completed");
                }

                // user input "apply_coupon,kroger,purchaseA,GC0001 or SC0001 or IC0001"
                else if (tokens[0].equals("apply_coupon")) {
                    if (!storeMap.containsKey(tokens[1])) {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    } else {
                        Store store = storeMap.get(tokens[1]);
                        if (!store.checkOrderExist(tokens[2])) {
                            System.out.println("ERROR:order_identifier_does_not_exist");
                        } else {
                            Order order = store.getOrder(tokens[2]);
                            if (!couponMap.containsKey(tokens[3])) {
                                System.out.println("ERROR:coupon_identifier_does_not_exist");
                            } else {
                                Coupon coupon = couponMap.get(tokens[3]);
                                if (!coupon.getCustomer().equals(order.getCustomer())) {
                                    System.out.println("ERROR:coupons_customer_does_not_match");
                                } else if (!coupon.isValid(programDate)) {
                                    System.out.println("ERROR:coupon_expired");
                                } else if (coupon.isUsed()) {
                                    System.out.println("ERROR:coupon_already_used");
                                } else if (order.getAppliedCouponMap().containsKey(coupon.getId())) {
                                    System.out.println("ERROR:coupon_already_applied");
                                } else if ((coupon.getId().startsWith("SC") || coupon.getId().startsWith("IC")) &&
                                        !coupon.getStore().equals(store)) {
                                    System.out.println("ERROR:coupons_store_does_not_match");
                                } else if (coupon.getId().startsWith("IC") && !order.checkItemExist(coupon.getItem())) {
                                    System.out.println("ERROR:coupons_item_does_not_match");
                                } else {
                                    order.applyCoupon(coupon);
                                    System.out.println("OK:change_completed");
                                }
                            }
                        }
                    }
                }

                //███    ███  █████  ██████       ██████  ██████  ██ ██████
                //████  ████ ██   ██ ██   ██     ██       ██   ██ ██ ██   ██
                //██ ████ ██ ███████ ██████      ██   ███ ██████  ██ ██   ██
                //██  ██  ██ ██   ██ ██          ██    ██ ██   ██ ██ ██   ██
                //██      ██ ██   ██ ██           ██████  ██   ██ ██ ██████

                // user input "display_grid_map"
                else if (tokens[0].equals("display_grid_map")) {
                    // initialize grid map
                    Grid grid = new Grid();
                    // replace placeholders by stores/customers
                    for(Map.Entry<String, Storm> m:stormMap.entrySet()){
                        Storm storm = m.getValue();
                        grid.updateStorm(storm);
                    }
                    for (Map.Entry<String, Point> m:locationMap.entrySet()){
                        grid.updatePoint(m.getKey(), (int) m.getValue().getX(), (int) m.getValue().getY());
                    }
                    // print the map
                    grid.visualize();
                    System.out.println("OK:display_completed");
                }

                // user input "display_delivery_path,kroger,purchaseA"
                else if (tokens[0].equals("display_delivery_path")) {
                    if (!storeMap.containsKey(tokens[1])) {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    } else {
                        Store store = storeMap.get(tokens[1]);
                        if (!store.checkOrderExist(tokens[2])) {
                            System.out.println("ERROR:order_identifier_does_not_exist");
                        } else {
                            Order order = store.getOrder(tokens[2]);
                            Drone drone = order.getDrone();
                            Customer customer = order.getCustomer();
                            Grid grid = new Grid();
                            Delivery delivery = new Delivery(store, order);
                            for(Map.Entry<String, Storm> m:stormMap.entrySet()){
                                Storm storm = m.getValue();
                                grid.updateStorm(storm);
                                if (delivery.checkOverlap(storm)) {
                                    System.out.println("ALERT:delivery_path_overlaps_storm_" + storm.getStormId());
                                    delivery.updateStormRisk(storm);
                                }
                            }
                            if (delivery.getTotalRisk() > 0) {
                                System.out.printf("ALERT:delivery_risk_quotient:%d,estimated_eta_is:%.2f\n",
                                        delivery.getTotalRisk(),
                                        delivery.getUpdatedEta());
                            } else {
                                System.out.printf("delivery_path_is_clear,latest_eta_is:%.2f\n",
                                        delivery.getUpdatedEta());
                            }
                            grid.updatePath(delivery.getPath());
                            grid.updatePoint(store.getName().toUpperCase(),store.getLocation().x, store.getLocation().y);
                            grid.updatePoint(customer.getAccount(),customer.getLocation().x, customer.getLocation().y);
                            grid.visualize();
                            System.out.println("OK:display_completed");
                        }
                    }
                }

                // start delivery, create delivery object
                // user input "start_delivery,kroger,purchaseA"
                else if (tokens[0].equals("start_delivery")) {
                    if (!storeMap.containsKey(tokens[1])) {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    } else {
                        Store store = storeMap.get(tokens[1]);
                        if (!store.checkOrderExist(tokens[2])) {
                            System.out.println("ERROR:order_identifier_does_not_exist");
                        } else {
                            Order order = store.getOrder(tokens[2]);
                            Drone drone = order.getDrone();
                            Grid grid = new Grid();
                            Delivery delivery = new Delivery(store, order);
                            delivery.setTimeDelayed(order.getPastTimeDelayed());
                            order.setDelivery(delivery);

                            Customer customer = order.getCustomer();
                            for (Map.Entry<String, Storm> m : stormMap.entrySet()) {
                                Storm storm = m.getValue();
                                grid.updateStorm(storm);
                            }
                            grid.updatePath(delivery.getPath());
                            grid.updatePoint(store.getName().toUpperCase(), store.getLocation().x, store.getLocation().y);
                            grid.updatePoint(customer.getAccount(), customer.getLocation().x, customer.getLocation().y);
                            delivery.setTimePassed(0);
                            int timeMoved = delivery.getTimeMoved();

                            Point realTimeLocation = delivery.getRealTimeLocation();
                            // grid.updatePoint(drone.getIdentifier(), realTimeLocation.x, realTimeLocation.y);
                            grid.updatePoint(drone.getIdentifier(), store.getLocation().x, store.getLocation().y);
                            System.out.println("order_delivery_initiated");

                            grid.visualize();
                            System.out.println("OK:display_completed");

                        }
                    }
                }

                // continuously checking drone status
                // user input "track_delivery,kroger,purchaseA,0"
                // last token[3] is time passed
                else if (tokens[0].equals("track_delivery")) {
                    if (!storeMap.containsKey(tokens[1])) {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    } else {
                        Store store = storeMap.get(tokens[1]);
                        if (!store.checkOrderExist(tokens[2])) {
                            System.out.println("ERROR:order_identifier_does_not_exist");
                        } else {
                            Order order = store.getOrder(tokens[2]);
                            int timePassed = Integer.parseInt(tokens[3]);
                            Drone drone = order.getDrone();
                            if (!drone.isAlive()) {
                                System.out.println("ERROR:unable_to_track,drone_struck_by_storm,prepare_new_delivery");
                            } else {
                                Customer customer = order.getCustomer();
                                Grid grid = new Grid();
                                for (Map.Entry<String, Storm> m : stormMap.entrySet()) {
                                    Storm storm = m.getValue();
                                    grid.updateStorm(storm);
                                }
                                Delivery delivery = order.getDelivery();
                                grid.updatePath(delivery.getPath());
                                grid.updatePoint(store.getName().toUpperCase(), store.getLocation().x, store.getLocation().y);
                                grid.updatePoint(customer.getAccount(), customer.getLocation().x, customer.getLocation().y);
                                delivery.setTimePassed(timePassed);
                                int timeMoved = delivery.getTimeMoved();
                                if (timeMoved < 0) {
                                    System.out.println("ERROR:invalid_time_index");
//                                } else if (timeMoved == 0) {
//                                    Point realTimeLocation = delivery.getRealTimeLocation();
//                                    grid.updatePoint(drone.getIdentifier(), realTimeLocation.x, realTimeLocation.y);
//                                    System.out.println("order_delivery_initiated");
                                } else if (timeMoved < delivery.getBaseEta()) {
                                    Point realTimeLocation = delivery.getRealTimeLocation();
                                    grid.updatePoint(drone.getIdentifier(), realTimeLocation.x, realTimeLocation.y);
                                    System.out.println("order_on_the_way");
                                    for (Map.Entry<String, Storm> m : stormMap.entrySet()) {
                                        Storm storm = m.getValue();
                                        if (delivery.checkStormArea(storm)) {
                                            System.out.println("ALERT:drone_in_storm_" + storm.getStormId() +
                                                    ",check_drone_status");
                                        }
                                    }
                                } else if (delivery.getTimeMoved() == (int) Math.ceil(delivery.getBaseEta()) &&
                                        timePassed <= delivery.getUpdatedEta()) {
                                    store.confirmDelivery(order);
                                    Point realTimeLocation = delivery.getRealTimeLocation();
                                    grid.updatePoint(drone.getIdentifier(), realTimeLocation.x, realTimeLocation.y);
                                    System.out.printf("order_delivered_at_time_%.2f,processing_transaction\n",
                                            delivery.getBaseEta());
                                } else if (delivery.getTimeMoved() == (int) Math.ceil(delivery.getBaseEta()) &&
                                        timePassed > delivery.getUpdatedEta()) {
                                    order.deliveryLate();
                                    store.confirmDelivery(order);
                                    Point realTimeLocation = customer.getLocation();
                                    grid.updatePoint(drone.getIdentifier(), realTimeLocation.x, realTimeLocation.y);
                                    System.out.printf("order_delivered_at_time_%.2f,late_discount_applied," +
                                            "processing_transaction\n",
                                            (delivery.getBaseEta() + delivery.getTimeDelayed()));
                                } else {
                                    System.out.printf("order_delivered_at_time_%.2f,drone_returning," +
                                            "transaction_processed\n",
                                            (delivery.getBaseEta() + delivery.getTimeDelayed()));
                                }
                                grid.visualize();
                                System.out.println("OK:display_completed");
                            }
                        }
                    }
                }

                //███    ███ ██ ███████  ██████
                //████  ████ ██ ██      ██
                //██ ████ ██ ██ ███████ ██
                //██  ██  ██ ██      ██ ██
                //██      ██ ██ ███████  ██████

                // user input "update_date,yyyy-mm-dd"
                // to simulate passage of time
                else if (tokens[0].equals("update_date")) {
                    if (LocalDate.parse(tokens[1]).isBefore(programDate)) {
                        System.out.println("ERROR:new_date_is_before_current_date");
                    } else if (LocalDate.parse(tokens[1]).isEqual(programDate)) {
                        System.out.println("ERROR:new_date_is_current_date");
                    } else {
                        programDate = LocalDate.parse(tokens[1]);
                        System.out.println("OK:change_completed");
                    }
                }

                // user input "delivery_delay,kroger,purchaseA,1"
                else if (tokens[0].equals("delivery_delay")) {
                    if (!storeMap.containsKey(tokens[1])) {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    } else {
                        Store store = storeMap.get(tokens[1]);
                        if (!store.checkOrderExist(tokens[2])) {
                            System.out.println("ERROR:order_identifier_does_not_exist");
                        } else {
                            Order order = store.getOrder(tokens[2]);
                            Delivery delivery = order.getDelivery();
                            delivery.delay(Integer.parseInt(tokens[3]));
                            System.out.println("OK:change_completed");
                        }
                    }
                }

                // isn't it just transfer_order?
                // different, add time wasted to the order
                //token[1]: store; token[2]: order; token[3]: drone; tokens[4]: time_delayed
                // user input "refill_order,kroger,purchaseA,kr2,10"
                else if (tokens[0].equals("refill_order")) {
                    Store store = storeMap.get(tokens[1]);
                    Order order = store.getOrderMap().get(tokens[2]);
                    Drone newDrone = store.getDroneMap().get(tokens[3]);
                    Drone currentDrone = order.getDrone();
                    Delivery delivery = order.getDelivery();
                    order.setPastTimeDelayed(Integer.parseInt(tokens[4]));
//                    if (currentDrone.isIs_crashed()) {
                    if (newDrone.getRemainingCapacity() > order.getCurrentOrderWeight()) {
                        store.transferOrder(order, newDrone);
                        System.out.println("OK:change_completed");
                    } else {
                        System.out.println("ERROR:new_drone_does_not_have_enough_capacity");
                    }
                }

                //"display_refillOrders, kroger"
                else if (tokens[0].equals("display_refillOrders")) {
                    if (!storeMap.containsKey(tokens[1])) {
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    } else {
                        Store store = storeMap.get(tokens[1]);
                        store.displayOrders();
                        System.out.println("OK:display_completed");
                    }
                }
                //System.out.println("store: " + tokens[1]);



                // █████  ██████   ██████ ██   ██ ██ ██    ██
                //██   ██ ██   ██ ██      ██   ██ ██ ██    ██
                //███████ ██████  ██      ███████ ██ ██    ██
                //██   ██ ██   ██ ██      ██   ██ ██  ██  ██
                //██   ██ ██   ██  ██████ ██   ██ ██   ████

//                //input "archive_store,kroger"
//                else if (tokens[0].equals("archive_store")) {
//                    Store store = this.storeMap.get(tokens[1]);
//                    store.setIsActive(false);
//                    boolean status = store.getIsActive();
//                    Archivability.archiveStore(store);
//                    System.out.println("OK:display_completed");
//                }
//
//                //input "archive_pilot,pilot_1"
//                else if (tokens[0].equals("archive_pilot")) {
//                    Pilot pilot = this.pilotMap.get(tokens[1]);
//                    pilot.setIsActive(false);
//                    boolean status = pilot.getIsActive();
//                    Archivability.archivePilot(pilot);
//                    System.out.println("OK:display_completed");
//
//                }
//
//                //input "archive_order_more_than_six_month,kroger, order_1"
//                else if (tokens[0].equals("archive_order_more_than_six_month")) {
//                    //Store store = this.storeMap.get(tokens[1]);
//                    //store.getOrder(tokens[2]).setIsActive(false);
//                    //boolean status = store.getOrder(tokens[2]).getIsActive();
//                    Archivability.archiveOrderGT6Month("order.json");
//                    System.out.println("OK:display_completed");
//
//                }
//
//                //input "archive_drone,kroger, kr1"
//                else if (tokens[0].equals("archive_drone")) {
//                    Store store = this.storeMap.get(tokens[1]);
//                    store.getDrone(tokens[2]).setActive(false);
//                    boolean status = store.getDrone(tokens[2]).getIsActive();
//                    Archivability.archiveDrone("drone.json");
//                    System.out.println("OK:display_completed");
//
//
//                }
//                //input "archive_customer,kroger, customer"
//
//                else if (tokens[0].equals("archive_customer_more_than_six_month")) {
//                    /*Customer customer = this.customerMap.get(tokens[1]);
//                    customer.setIsActive(false);
//                    boolean status = customer.getIsActive();*/
//                    Archivability.archiveCustomerGT6Month("customer.json");
//                    System.out.println("OK:display_completed");
//                }
//
////                else if (tokens[0].equals("archive_old_order")) {
////                    // call order method to set false for old order
////
////                    // call utility function to archive data
////                }



                else if (tokens[0].equals("stop")) {
                    System.out.println("stop acknowledged");
                    break;
                }

                else if (tokens[0].startsWith("//")) {
                    // System.out.println(tokens[0]);
                }


                else {
                    // System.out.println(tokens[0]);

                    System.out.println("command " + tokens[0] + " NOT acknowledged");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println();
            }
        }

        System.out.println("simulation terminated");
        commandLineInput.close();
    }


}
