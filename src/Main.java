import java.util.Scanner;

public class Main {
    public static void main(String[] args){

        Session session = new Session();
        FileManager fileManager = new FileManager("data.txt");
        PharmacyService pharmacy = new PharmacyService(fileManager, session);
        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.println();
            if (session.isLoggedIn()) {
                System.out.print("Текущий пользователь: "
                        + session.getCurrentUser().getUsername()
                        + " (" + session.getCurrentUser().getRole() + ")" + ".");
            } else {
                System.out.print("Текущий пользователь: вход не выполнен.");
            }

            System.out.println();
            System.out.print("===== АПТЕКА =====" +
                    "\n1 – показать список лекарств" +
                    "\n2 – продать лекарство" +
                    "\n3 – добавить лекарство" +
                    "\n4 – удалить лекарство" +
                    "\n5 – войти / сменить пользователя" +
                    "\n6 – выйти из аккаунта" +
                    "\n0 – выйти из программы" +
                    "\nВыбор: ");

            String choice = scanner.next();
            scanner.nextLine();             // очистка буфера

            switch(choice){

                case "1":
                    System.out.println("\nСписок лекарств: ");
                    pharmacy.showAllMedicines();
                    break;

                case "2":
                    if (!session.isLoggedIn()) {
                        System.out.println("\nВойдите в систему (пункт 5).");
                        break;
                    }

                    System.out.print("\nВведите название: ");
                    String name2 = scanner.nextLine();
                    System.out.print("Введите количество: ");
                    int amount2 = InputUtils.readInt(scanner);
                    pharmacy.sellMedicine(name2, amount2);
                    break;

                case "3":
                    System.out.print("\nВведите название: ");
                    String name3 = scanner.nextLine();
                    System.out.print("Введите цену: ");
                    double price3 = InputUtils.readDouble(scanner);
                    System.out.print("Введите количество: ");
                    int amount3 = InputUtils.readInt(scanner);
                    System.out.print("Введите срок годности: ");
                    int shelfLife3 = InputUtils.readInt(scanner);
                    Medicine medicine3 = new Medicine(name3, price3, amount3, shelfLife3);
                    pharmacy.addMedicine(medicine3);
                    break;

                case "4":
                    System.out.print("\nВведите название: ");
                    String name4 = scanner.nextLine();
                    pharmacy.removeMedicine(name4);
                    break;

                case "5":
                    String username;
                    while(true){
                        System.out.print("\nВведите имя пользователя: ");
                        username = scanner.nextLine();
                        if (!username.isBlank()) {
                            break;
                        }
                        System.out.println("Ошибка. Имя не может быть пустым.");
                    }

                    Role role;
                    System.out.print("\nВведите роль (admin/cashier): ");
                    while(true){
                        String roleInput = scanner.nextLine();
                        if(roleInput.equalsIgnoreCase("admin")){
                            role = Role.ADMIN;
                            break;
                        } else if(roleInput.equalsIgnoreCase("cashier")){
                            role = Role.CASHIER;
                            break;
                        } else{
                            System.out.print("Ошибка. Введите роль (admin/cashier): ");
                        }
                    }

                    User user = new User(username, role);
                    session.login(user);
                    System.out.println("Выполнен вход как " + user.getUsername() + " (" + user.getRole() + ").");
                    break;

                case "6":
                    if(!session.isLoggedIn()){
                        System.out.println("\nВы не вошли в аккаунт.");
                        break;
                    }
                    session.logout();
                    System.out.println("\nВы вышли из аккаунта.");
                    break;

                case "0":
                    System.out.println("\nВы вышли из программы.");
                    scanner.close();
                    return;

                default:
                    System.out.println("\nНеизвестный пункт меню.");
                    break;
            }
        }
    }
}
