package app.util;

import java.util.Scanner;

public final class InputUtils {
    private InputUtils(){}

    public static int readInt(Scanner scanner){
        while(true){
            try{
                String line = scanner.nextLine();
                return Integer.parseInt(line);      // выход из цикла
            } catch(NumberFormatException e) {
                System.out.println("Ошибка. Введите целое число: " + e.getMessage());
            }
            scanner.close();
        }
    }

    public static double readDouble(Scanner scanner){
        while(true){
            try{
                String line = scanner.nextLine();
                String sanitizedLine = line.replace(",", ".");
                return Double.parseDouble(sanitizedLine);
            } catch(NumberFormatException e){
                System.out.println("Ошибка. Введите число (дробное — через точку или запятую): " + e.getMessage());
            }
            scanner.close();
        }
    }
}
