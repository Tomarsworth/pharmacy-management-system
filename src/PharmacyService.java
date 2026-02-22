import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class PharmacyService{
    private List<Medicine> medicines;           // используем интерфейс List для гибкости
    FileManager fileManager;

    public PharmacyService(FileManager fileManager){
        this.fileManager = fileManager;
        this.medicines = fileManager.loadFromFile();
    }

    public void addMedicine(Medicine medicine){
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
            System.out.println("Cклад пуст.");
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
}
