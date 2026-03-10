import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class FileManager {

    private final String filePath;

    public FileManager(String filePath) {
        this.filePath = filePath;
    }

    public void saveToFile(List<Medicine> medicines){
        try(PrintWriter writer = new PrintWriter(filePath)){
            for(Medicine medicine : medicines){
                writer.println(medicine.getName() + ";"
                        + medicine.getPrice() + ";"
                        + medicine.getAmount() + ";"
                        + medicine.getShelfLife());
            }
            System.out.println("Данные сохранены в файл: " + filePath);
        } catch(IOException e){
            System.out.println("Ошибка при сохранении в файл: " + e.getMessage());
        }
    }

    public List<Medicine> loadFromFile(){
        List<Medicine> medicines = new ArrayList<>();       // создаём объект класса ArrayList (список)
        File file = new File(filePath);

        if(!file.exists()) return medicines;                // проверка существования файла

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;
            while((line = reader.readLine()) != null){
                String[] parts = line.split(";");

                if(parts.length == 4){
                    try{
                        String name = parts[0].trim();      // trim() – удаление лишних пробелов с обеих сторон
                        double price = Double.parseDouble(parts[1].trim());
                        int amount = Integer.parseInt(parts[2].trim());
                        int shelfLife = Integer.parseInt(parts[3].trim());

                        medicines.add(new Medicine(name, price, amount, shelfLife));
                    } catch(NumberFormatException e){
                        System.out.println("Пропущена строка с ошибкой в числах: " + line);
                    }
                } else{
                    System.out.println("Пропущена некорректная строка (неверное кол-во полей): " + line);
                }
            }
        } catch(IOException e){
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
        return medicines;
    }
}
