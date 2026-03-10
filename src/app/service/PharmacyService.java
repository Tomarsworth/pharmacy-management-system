package app.service;

import app.model.Medicine;
import app.model.Role;
import app.model.User;
import app.storage.FileManager;
import app.session.Session;

import java.util.List;

public class PharmacyService{

    private final FileManager fileManager;
    private final List<Medicine> medicines;
    private final Session session;

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

    public void addMedicine(String name, double price, int amount, int shelfLife){
        Medicine medicine = new Medicine(name, price, amount, shelfLife);
        addMedicine(medicine);
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
    /*
    public User getCurrentUser(){
        return session.getCurrentUser();
    }
    */
    public String getCurrentUserStatus(){
        if (!session.isLoggedIn()){
            return "Текущий пользователь: вход не выполнен";
        }
        User user = session.getCurrentUser();
        return "Текущий пользователь: "
                + user.getUsername()
                + " (" + user.getRole() + ")";
    }
    /*
    public boolean isLoggedIn(){
        return session.isLoggedIn();
    }
    */
    public void login(String username, String roleString){
        Role role;

        if (roleString.equalsIgnoreCase("admin")) {
            role = Role.ADMIN;
        } else {
            role = Role.CASHIER;
        }

        User user = new User(username, role);
        session.login(user);
    }

    public void logout(){
        session.logout();
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
