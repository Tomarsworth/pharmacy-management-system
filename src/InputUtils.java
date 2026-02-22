import java.util.Scanner;

public class InputUtils {

    static public int readInt(Scanner scanner){
        while(true){
            try{
                String line = scanner.nextLine();
                return Integer.parseInt(line);      // выход из цикла
            } catch(NumberFormatException e) {
                System.out.println("Введите целое число: " + e.getMessage());
            }
        }
    }

    static public double readDouble(Scanner scanner){
        while(true){
            try{
                String line = scanner.nextLine();
                return Double.parseDouble(line);
            } catch(NumberFormatException e){
                System.out.println("Ошибка: введите число (дробное — через точку или запятую).");
            }
        }
    }
}
