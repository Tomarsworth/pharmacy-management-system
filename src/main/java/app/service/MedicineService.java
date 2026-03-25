package app.service;

import app.model.Medicine;
import app.repository.MedicineRepository;

import java.util.ArrayList;
import java.util.List;

public class MedicineService {
    private final MedicineRepository medicineRepository;
    private final AuthService authService;
    private final List<Medicine> medicines = new ArrayList<>();

    public MedicineService(MedicineRepository medicineRepository, AuthService authService){
        this.medicineRepository = medicineRepository;
        this.authService = authService;
        this.medicines.addAll(medicineRepository.findAll());
    }

    public void addMedicine(Medicine medicine){
        if(!authService.isAdmin()) return;

        Medicine existing = findMedicine(medicine.getName());
        if(existing != null){
            System.out.println("Лекарство уже существует.");
            return;                             // для выхода из метода
        }
        long id = medicineRepository.insert(medicine);
        medicine.setId(id);
        medicines.add(medicine);
        System.out.println("Лекарство добавлено.");
    }

    public void addMedicine(String name, double price, int amount, int shelfLife){
        Medicine medicine = new Medicine(name, price, amount, shelfLife);
        addMedicine(medicine);
    }

    public void removeMedicine(long id){
        if(!authService.isAdmin()){
            return;
        }
        Medicine medicine = findMedicineById(id);
        if(medicine == null){
            System.out.println("Лекарство не найдено.");
            return;
        }
        medicineRepository.deleteById(id);
        medicines.remove(medicine);
        System.out.println("Лекарство удалено");
    }

    public void sellMedicine(long id, int amount){
        if(!authService.canSell()) return;

        Medicine medicine = findMedicineById(id);
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
        medicineRepository.update(medicine);
        System.out.println(medicine.getName() + " продан в количестве " + amount + " шт.");
    }

    public void showAllMedicines(){
        if(medicines.isEmpty()){
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

    public Medicine findMedicineById(long id){
        for(Medicine m : medicines){
            if(m.getId() == id){
                return m;
            }
        }
        return null;
    }
}
