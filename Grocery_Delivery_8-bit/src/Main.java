import edu.gatech.cs6310.*;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {
        System.out.println("Welcome to the Grocery Express Delivery Service!");
        DeliveryService simulator = new DeliveryService();
        simulator.commandLoop();
    }
}
