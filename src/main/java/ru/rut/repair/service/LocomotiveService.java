package ru.rut.repair.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rut.repair.model.Locomotive;
import ru.rut.repair.repository.LocomotiveRepository;

import java.util.List;

@Service
public class LocomotiveService{
    private final LocomotiveRepository locomotiveRepository;

    @Autowired
    public LocomotiveService(LocomotiveRepository locomotiveRepository) {
        this.locomotiveRepository = locomotiveRepository;
    }

    public List<Locomotive> getList(){
        return locomotiveRepository.findAll();
    }

    public void remove(int id){
        locomotiveRepository.deleteById(id);
    }

    @Transactional
    public void edit(int id, Locomotive locomotive){
        Locomotive locomotive1 = locomotiveRepository.getReferenceById(id);
        locomotive1.copy(locomotive);
        locomotiveRepository.save(locomotive1);
    }

    @Transactional
    public void add(Locomotive locomotive){
        locomotiveRepository.save(locomotive);
    }
}
