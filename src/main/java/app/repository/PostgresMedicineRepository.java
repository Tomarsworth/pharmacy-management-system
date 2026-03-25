package app.repository;

import app.model.Medicine;
import app.storage.jdbc.JdbcConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostgresMedicineRepository implements MedicineRepository{
    @Override
    public List<Medicine> findAll() {
        String sql = "SELECT id, name, price, amount, shelf_life FROM medicines ORDER BY id";
        List<Medicine> list = new ArrayList<>();
        try (Connection conn = JdbcConnectionProvider.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка чтения лекарств: " + e.getMessage(), e);
        }
        return list;
    }

    @Override
    public Optional<Medicine> findById(long id) {
        String sql = "SELECT id, name, price, amount, shelf_life FROM medicines WHERE id = ?";
        try (Connection conn = JdbcConnectionProvider.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка поиска по id: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public long insert(Medicine medicine) {
        String sql = "INSERT INTO medicines (name, price, amount, shelf_life) VALUES (?, ?, ?, ?)";
        try (Connection conn = JdbcConnectionProvider.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, medicine.getName());
            ps.setDouble(2, medicine.getPrice());
            ps.setInt(3, medicine.getAmount());
            ps.setInt(4, medicine.getShelfLife());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка вставки: " + e.getMessage(), e);
        }
        throw new RuntimeException("Не удалось получить id после INSERT");
    }

    @Override
    public void update(Medicine medicine) {
        String sql = "UPDATE medicines SET name = ?, price = ?, amount = ?, shelf_life = ? WHERE id = ?";
        try (Connection conn = JdbcConnectionProvider.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, medicine.getName());
            ps.setDouble(2, medicine.getPrice());
            ps.setInt(3, medicine.getAmount());
            ps.setInt(4, medicine.getShelfLife());
            ps.setLong(5, medicine.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка обновления: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(long id) {
        String sql = "DELETE FROM medicines WHERE id = ?";
        try (Connection conn = JdbcConnectionProvider.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка удаления: " + e.getMessage(), e);
        }
    }

    private Medicine mapRow(ResultSet rs) throws SQLException {
        return new Medicine(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getDouble("price"),
                rs.getInt("amount"),
                rs.getInt("shelf_life")
        );
    }
}
