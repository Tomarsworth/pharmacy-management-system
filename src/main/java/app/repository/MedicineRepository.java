package app.repository;

import app.model.Medicine;

import java.util.List;
import java.util.Optional;

public interface MedicineRepository {
    public List<Medicine> findAll();

    public Optional<Medicine> findById(long id);

    public long insert(Medicine medicine);

    public void update(Medicine medicine);

    public void deleteById(long id);
}
