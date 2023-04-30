package ru.rut.repair.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rut.repair.model.Inventory;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    List<Inventory> findAllByActId(int act_id);
}
