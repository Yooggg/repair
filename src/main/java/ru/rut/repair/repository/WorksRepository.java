package ru.rut.repair.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rut.repair.model.Works;

import java.util.List;

public interface WorksRepository extends JpaRepository<Works, Integer> {
    List<Works> findAllByActId(int act_id);
}
