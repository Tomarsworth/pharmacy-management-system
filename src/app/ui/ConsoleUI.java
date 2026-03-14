package app.ui;

import app.service.PharmacyService;
import app.util.InputUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleUI {

    private boolean running = true;

    private final PharmacyService pharmacyService;
    private final Scanner scanner;
    private final Map<String, Runnable> commands = new HashMap<>();

    public ConsoleUI(PharmacyService pharmacyService, Scanner scanner){
        this.pharmacyService = pharmacyService;
        this.scanner = scanner;

        commands.put("1", this::handleShowMedicines);
        commands.put("2", this::handleSellMedicine);
        commands.put("3", this::handleAddMedicine);
        commands.put("4", this::handleRemoveMedicine);
        commands.put("5", this::handleLogin);
        commands.put("6", this::handleLogout);
        commands.put("0", this::handleExit);
    }

    // основной цикл
    public void start(){
        while(running){
            showMenu();
            String choice = scanner.nextLine();
            handleChoice(choice);
        }
    }

    private void showMenu() {
        System.out.println();
        pharmacyService.getCurrentUserStatus();

        System.out.println("""
                ===== АПТЕКА =====
                1 – показать список лекарств
                2 – продать лекарство
                3 – добавить лекарство
                4 – удалить лекарство
                5 – войти / сменить пользователя
                6 – выйти из аккаунта
                0 – выйти из программы
                """);

        System.out.print("Выбор: ");
    }

    public void handleChoice (String choice){
        Runnable action = commands.getOrDefault(choice, this::handleUnknownChoice);
        action.run();
    }

    private void handleShowMedicines(){
        System.out.println("\nСписок лекарств: ");
        pharmacyService.showAllMedicines();
    }
    private void handleSellMedicine(){
        System.out.print("\nВведите название: ");
        String name = scanner.nextLine();

        System.out.print("Введите количество: ");
        int amount = InputUtils.readInt(scanner);

        pharmacyService.sellMedicine(name, amount);
    }
    private void handleAddMedicine(){
        System.out.print("\nВведите название: ");
        String name = scanner.nextLine();

        System.out.print("Введите цену: ");
        double price = InputUtils.readDouble(scanner);

        System.out.print("Введите количество: ");
        int amount = InputUtils.readInt(scanner);

        System.out.print("Введите срок годности: ");
        int shelfLife = InputUtils.readInt(scanner);

        pharmacyService.addMedicine(name, price, amount, shelfLife);
    }
    private void handleRemoveMedicine(){
        System.out.print("\nВведите название: ");
        String name = scanner.nextLine();

        pharmacyService.removeMedicine(name);
    }
    private void handleLogin(){
        System.out.print("\nВведите имя пользователя: ");
        String username = scanner.nextLine();

        System.out.print("Введите роль (admin/cashier): ");
        String role = scanner.nextLine();

        pharmacyService.login(username, role);
    }
    private void handleLogout(){
        System.out.println("\nВы вышли из аккаунта.");
        pharmacyService.logout();
    }
    private void handleExit(){
        System.out.println("\nВы вышли из программы.");
        running = false;
    }
    private void handleUnknownChoice(){
        System.out.println("\nНеизвестный пункт меню.");
    }
}
