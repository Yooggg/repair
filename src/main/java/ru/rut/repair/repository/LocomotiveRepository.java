package ru.rut.repair.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rut.repair.model.Locomotive;

@Repository
public interface LocomotiveRepository extends JpaRepository<Locomotive, Integer> {
}
