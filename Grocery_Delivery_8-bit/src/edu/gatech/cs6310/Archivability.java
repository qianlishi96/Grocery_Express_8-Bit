package edu.gatech.cs6310;

import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Scanner;

public class Archivability {

    LocalDate dsDate = LocalDate.now();

    public static void archiveStore(Store store) throws IOException, ParseException {
        if (store.getIsActive() == false){
            Utility.archieveData(store.getName(), "store.json");
        }
    }

    public static void archivePilot(Pilot pilot) throws IOException, ParseException {
        if (pilot.getIsActive() == false){
            Utility.archieveData(pilot.getAccount(), "pilot.json");
        }
    }

    public static void archiveDrone(String fileName) throws IOException, ParseException {
        Scanner scanner = new Scanner(new File(fileName));
        String finalOutput = "";
        String archiveOutput = "";
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] fileLine = line.split(",");
            String status = fileLine[4].split(":")[2];

            if (status != " false") {
                finalOutput += line + System.lineSeparator();
            } else {
                archiveOutput += line + System.lineSeparator();
            }
            Files.write(Paths.get(fileName), finalOutput.getBytes());
            Files.write(Paths.get("archive_" + fileName), archiveOutput.getBytes());
        }

    }

    public static void archiveOrderGT6Month(String fileName) throws IOException, ParseException {
        Scanner scanner = new Scanner(new File(fileName));
        String finalOutput = "";
        String archiveOutput = "";
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] fileLine = line.split(",");
            String timeStamp = fileLine[4].split(":")[1];

            LocalDate dsDate = LocalDate.now();
            String[] str_1 = dsDate.toString().split("-");
            int now_year = Integer.parseInt(str_1[0]);
            int now_month = Integer.parseInt(str_1[1]);
            int now_date = Integer.parseInt(str_1[2]);

            String[] str_2 = timeStamp.split("-");
            int order_year = Integer.parseInt(str_2[0]);
            int order_month = Integer.parseInt(str_2[1]);
            int order_date = Integer.parseInt(str_2[2]);

            int year_diff = now_year - order_year;
            int month_diff = now_month + year_diff * 12 - order_month;
            int date_diff = now_date + month_diff * 30 - order_date;

            int total_inactive = date_diff / 30;

            if (total_inactive <= 6) {
                finalOutput += line + System.lineSeparator();
            } else {
                archiveOutput += line + System.lineSeparator();
            }
            Files.write(Paths.get(fileName), finalOutput.getBytes());
            Files.write(Paths.get("archive_" + fileName), archiveOutput.getBytes());
        }
    }

    public static void archiveCustomerGT6Month(String fileName) throws IOException, ParseException {
        Scanner scanner = new Scanner(new File(fileName));
        String finalOutput = "";
        String archiveOutput = "";
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] fileLine = line.split(",");
            String timeStamp = fileLine[4].split(":")[1];

            LocalDate dsDate = LocalDate.now();
            String[] str_1 = dsDate.toString().split("-");
            int now_year = Integer.parseInt(str_1[0]);
            int now_month = Integer.parseInt(str_1[1]);
            int now_date = Integer.parseInt(str_1[2]);

            String[] str_2 = timeStamp.split("-");
            int order_year = Integer.parseInt(str_2[0]);
            int order_month = Integer.parseInt(str_2[1]);
            int order_date = Integer.parseInt(str_2[2]);

            int year_diff = now_year - order_year;
            int month_diff = now_month + year_diff * 12 - order_month;
            int date_diff = now_date + month_diff * 30 - order_date;

            int total_inactive = date_diff / 30;

            if (total_inactive < 6) {
                finalOutput += line + System.lineSeparator();
            } else {
                archiveOutput += line + System.lineSeparator();
            }
            Files.write(Paths.get(fileName), finalOutput.getBytes());
            Files.write(Paths.get("archive_" + fileName), archiveOutput.getBytes());
        }
    }
}
