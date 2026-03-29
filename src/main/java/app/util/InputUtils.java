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
        }
    }

    public static long readLong(Scanner scanner) {
        while(true){
            try{
                String line = scanner.nextLine();
                return Long.parseLong(line);      // выход из цикла
            } catch(NumberFormatException e) {
                System.out.println("Ошибка. Введите целое число (id): " + e.getMessage());
            }
        }
    }
}
