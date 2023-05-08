package ru.rut.repair.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rut.repair.dto.ActDto;
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
    public void add(ActDto actDto){
        Act act = new Act();
        act.setLocomotive(locomotiveRepository.getReferenceById(actDto.getLocomotiveId()));
        act.setCompany(actDto.getCompany());
        act.setDate(actDto.getDate());
        act.setNumber(actDto.getNumber());
        act.setWorkKind(actDto.getWorkKind());
        actRepository.save(act);
    }
    @Transactional
    public void add(Act act, int locId){
        act.setLocomotive(locomotiveRepository.getReferenceById(locId));
        actRepository.save(act);
    }
    @Transactional
    public void edit(ActDto actDto){
        Act act1 = actRepository.getReferenceById(actDto.getId());
        act1.setWorkKind(actDto.getWorkKind());
        act1.setNumber(actDto.getNumber());
        act1.setDate(actDto.getDate());
        act1.setCompany(actDto.getCompany());
        act1.setLocomotive(locomotiveRepository.getReferenceById(actDto.getLocomotiveId()));
        actRepository.save(act1);
    }

}
