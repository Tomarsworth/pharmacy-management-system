import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class PharmacyService{

    private List<Medicine> medicines;           // используем интерфейс List для гибкости
    FileManager fileManager;
    private Session session;

    public PharmacyService(FileManager fileManager, Session session){
        this.fileManager = fileManager;
        this.session = session;
        this.medicines = fileManager.loadFromFile();
    }

    public void addMedicine(Medicine medicine){
        if(!isAdmin()) return;

        Medicine existing = findMedicine(medicine.getName());
        if(existing != null){
            System.out.println("Лекарство уже существует.");
            return;                             // для выхода из метода
        }
        medicines.add(medicine);
        fileManager.saveToFile(medicines);
        System.out.println("Лекарство добавлено.");
    }

    public void removeMedicine(String name){
        if(!isAdmin()) return;

        Medicine medicine = findMedicine(name);
        if(medicine == null){
            System.out.println("Лекарство не найдено.");
            return;                             // для выхода из метода
        }
        medicines.remove(medicine);
        fileManager.saveToFile(medicines);
        System.out.println("Лекарство удалено");
    }

    public void sellMedicine(String name, int amount){
        if (!canSell()) return;

        Medicine medicine = findMedicine(name);
        if(medicine == null){
            System.out.println("Лекарство не найдено.");
            return;
        }
        if(amount <= 0){
            System.out.println("Количество должно быть больше нуля.");
            return;
        }
        if(medicine.getAmount() < amount){
            System.out.println("Недостаточно лекарств на складе.");
            return;
        }
        medicine.reduceAmount(amount);
        fileManager.saveToFile(medicines);
        System.out.println(medicine.getName() + " продан в количестве " + amount + " шт.");
    }

    public void showAllMedicines(){
        if (medicines.isEmpty()){
            System.out.println("Склад пуст.");
            return;
        }
        for (Medicine medicine : medicines){
            System.out.println(medicine);
        }
    }

    public Medicine findMedicine(String name){
        for(Medicine medicine : medicines){
            if(medicine.getName().equalsIgnoreCase(name)) return medicine;
        }
        return null;
    }

    private boolean isAdmin(){
        if(!session.isLoggedIn()){
            System.out.println("В системе никого нет.");
            return false;
        }
        if(session.getCurrentUser().getRole() != Role.ADMIN){
            System.out.println("Добавлять / удалять лекарства может только администратор.");
            return false;
        }
        return true;
    }

    private boolean canSell() {
        if(!session.isLoggedIn()){
            System.out.println("В системе никого нет.");
            return false;
        }
        Role role = session.getCurrentUser().getRole();
        if(role != Role.ADMIN && role != Role.CASHIER){
            System.out.println("Продавать могут только администратор и кассир.");
            return false;
        }
        return true;
    }
}
