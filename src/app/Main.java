import app.service.PharmacyService;
import app.session.Session;
import app.storage.FileManager;
import app.ui.ConsoleUI;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){

        try(Scanner scanner = new Scanner(System.in)){
            Session session = new Session();
            FileManager fileManager = new FileManager("data.txt");

            PharmacyService pharmacy = new PharmacyService(fileManager, session);

            ConsoleUI ui = new ConsoleUI(pharmacy, session, scanner);
            ui.start();
        }
    }
}
