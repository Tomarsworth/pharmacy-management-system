import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        PharmacyService pharmacy = new PharmacyService();
        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.println();
            System.out.print("===== АПТЕКА =====" +
                    "\n1 – показать список лекарств" +
                    "\n2 – продать лекарство" +
                    "\n3 – добавить лекарство" +
                    "\n4 – удалить лекарство" +
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
                    System.out.print("\nВведите название: ");
                    String name2 = scanner.nextLine();
                    System.out.print("Введите количество: ");
                    int amount2 = scanner.nextInt();
                    scanner.nextLine();     // очистка буфера от \n (Enter)
                    pharmacy.sellMedicine(name2, amount2);
                    break;
                case "3":
                    System.out.print("\nВведите название: ");
                    String name3 = scanner.nextLine();
                    System.out.print("Введите цену: ");
                    double price3 = scanner.nextDouble();
                    scanner.nextLine();     // очистка буфера
                    System.out.print("Введите количество: ");
                    int amount3 = scanner.nextInt();
                    scanner.nextLine();     // очистка буфера
                    System.out.print("Введите срок годности: ");
                    int shelfLife3 = scanner.nextInt();
                    scanner.nextLine();     // очистка буфера
                    Medicine medicine3 = new Medicine(name3, price3, amount3, shelfLife3);
                    pharmacy.addMedicine(medicine3);
                    break;
                case "4":
                    System.out.print("\nВведите название: ");
                    String name4 = scanner.nextLine();
                    pharmacy.removeMedicine(name4);
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
