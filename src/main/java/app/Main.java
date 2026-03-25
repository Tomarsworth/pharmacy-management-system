package app;

import app.repository.PostgresMedicineRepository;
import app.service.AuthService;
import app.service.MedicineService;
import app.session.Session;
import app.ui.ConsoleUI;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){

        try(Scanner scanner = new Scanner(System.in)){
            Session session = new Session();
            AuthService authService = new AuthService(session);

            PostgresMedicineRepository medicineRepository = new PostgresMedicineRepository();
            MedicineService medicineService = new MedicineService(medicineRepository, authService);

            ConsoleUI ui = new ConsoleUI(medicineService, authService, scanner);

            ui.start();
        }
    }
}
