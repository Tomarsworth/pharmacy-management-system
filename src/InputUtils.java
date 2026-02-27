import java.util.Scanner;

public class InputUtils {

    public static int readInt(Scanner scanner){
        while(true){
            try{
                String line = scanner.nextLine();
                return Integer.parseInt(line);      // выход из цикла
            } catch(NumberFormatException e) {
                System.err.println("Ошибка. Введите целое число: " + e.getMessage());
            }
        }
    }

    public static double readDouble(Scanner scanner){
        while(true){
            try{
                String line = scanner.nextLine();
                String sanitizedLine = line.replace(",", ".");
                return Double.parseDouble(sanitizedLine);
            } catch(NumberFormatException e){
                System.err.println("Ошибка. Введите число (дробное — через точку или запятую): " + e.getMessage());
            }
        }
    }
}
