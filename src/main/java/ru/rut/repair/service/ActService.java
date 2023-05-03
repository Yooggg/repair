package ru.rut.repair.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rut.repair.model.Act;
import ru.rut.repair.repository.ActRepository;
import ru.rut.repair.repository.LocomotiveRepository;

import java.util.List;

@Service
public class ActService{
    private final ActRepository actRepository;
    private final LocomotiveRepository locomotiveRepository;

    @Autowired
    public ActService(ActRepository actRepository, LocomotiveRepository locomotiveRepository) {
        this.actRepository = actRepository;
        this.locomotiveRepository = locomotiveRepository;
    }

    public Act getById(int id){
        return actRepository.getReferenceById(id);
    }
    public List<Act> getList(){
        return actRepository.findAll();
    }
    public void remove(int id){
        actRepository.deleteById(id);
    }
    @Transactional
    public void add(Act act){
        actRepository.save(act);
    }
    @Transactional
    public void add(Act act, int loc_id){
        act.setLocomotive(locomotiveRepository.getReferenceById(loc_id));
        actRepository.save(act);
    }
    @Transactional
    public void edit(int id, Act act){
        Act act1 = actRepository.getReferenceById(id);
        act1.copy(act);
        actRepository.save(act1);
    }
}
