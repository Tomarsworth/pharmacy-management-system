package app;

import app.service.AuthService;
import app.service.MedicineService;
import app.session.Session;
import app.storage.FileManager;
import app.ui.ConsoleUI;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){

        try(Scanner scanner = new Scanner(System.in)){

            Session session = new Session();
            AuthService authService = new AuthService(session);

            FileManager fileManager = new FileManager("data.txt");
            MedicineService medicineService = new MedicineService(fileManager, authService);

            ConsoleUI ui = new ConsoleUI(medicineService, authService, scanner);

            ui.start();
        }
    }
}
