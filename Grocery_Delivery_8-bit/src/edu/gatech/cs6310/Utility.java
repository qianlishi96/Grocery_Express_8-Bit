package edu.gatech.cs6310;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Utility {

    public static void writeJSON(String key, String object, String fileName) throws IOException, ParseException {

        try {
            Path filePath = Path.of(fileName);
            String oldContent = Files.readString(filePath);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put(key, object);
            Files.write(Paths.get(fileName), (oldContent + "," + System.lineSeparator() + jsonObject.toJSONString()).getBytes());
        } catch (Exception e) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(key, object);
            Files.write(Paths.get(fileName), (jsonObject.toJSONString()).getBytes());
        }
    }

    public static void archieveData(String condition, String fileName) throws IOException, ParseException {
        Scanner scanner = new Scanner(new File(fileName));
        String finalOutput = "";
        String archiveOutput = "";
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (!line.contains(condition)){
                finalOutput += line + System.lineSeparator();
            } else {
                archiveOutput += line + System.lineSeparator();
            }
            Files.write(Paths.get(fileName), finalOutput.getBytes());
            Files.write(Paths.get("archive_"+fileName), archiveOutput.getBytes());
        }
    }

}
