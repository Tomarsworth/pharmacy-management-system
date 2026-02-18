import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class PharmacyService{
    private List<Medicine> medicines;           // используем интерфейс List для гибкости
    private String filePath = "data.txt";

    public PharmacyService(){
        this.medicines = new ArrayList<>();     // создаём объект класса ArrayList (список)
        loadFromFile();
    }

    public void addMedicine(Medicine medicine){
        Medicine existing = findMedicine(medicine.getName());
        if(existing != null){
            System.out.println("Лекарство уже существует.");
            return;                             // для выхода из метода
        }
        medicines.add(medicine);
        saveToFile();
        System.out.println("Лекарство добавлено.");
    }
    public void removeMedicine(String name){
        Medicine medicine = findMedicine(name);
        if(medicine == null){
            System.out.println("Лекарство не найдено.");
            return;                             // для выхода из метода
        }
        medicines.remove(medicine);
        saveToFile();
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
        saveToFile();
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
    public void saveToFile(){
        try(FileWriter writer = new FileWriter(filePath)){
            for(Medicine medicine : medicines){
                writer.write(medicine.getName() + ";"
                        + medicine.getPrice() + ";"
                        + medicine.getAmount() + ";"
                        + medicine.getShelfLife() + "\n");
            }
            System.out.println("Данные сохранены в файл: " + filePath);
        } catch(IOException e){
            System.out.println("Ошибка при сохранении в файл: " + e.getMessage());
        }
    }
    public void loadFromFile(){
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;
            while((line = reader.readLine()) != null){
                String[] parts = line.split(";");
                if(parts.length != 4) continue;     // проверка на битые данные
                String name = parts[0];
                double price = Double.parseDouble(parts[1]);
                int amount = Integer.parseInt(parts[2]);
                int shelfLife = Integer.parseInt(parts[3]);
                addMedicine(new Medicine(name, price, amount, shelfLife));
            }
        } catch (IOException e){
            System.out.println("Ошибка при чтении из файла: " + e.getMessage());
        }
    }
}
