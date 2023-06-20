package ru.rut.repair.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rut.repair.model.Act;


@Repository
public interface ActRepository extends JpaRepository<Act, Integer> {

}
