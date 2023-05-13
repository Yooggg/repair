package ru.rut.repair.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rut.repair.dto.ActDto;
import ru.rut.repair.model.Act;
import ru.rut.repair.model.Locomotive;
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

    @Transactional
    public void remove(int id){
        actRepository.deleteById(id);
    }

    public void add(ActDto actDto){
        try {
            Locomotive locomotive = new Locomotive(actDto.getLocomotiveDto().getSeries(),actDto.getLocomotiveDto().getFactoryNumber(),
                    actDto.getLocomotiveDto().getSectionIndex(),actDto.getLocomotiveDto().getHomeDepot(), actDto.getLocomotiveDto().getWorkFact());

            Act act = new Act(locomotive,actDto.getNumber(),actDto.getDate(),actDto.getCompany(),actDto.getWorkKind());

            actRepository.save(act);

        } catch (Exception e){
            e.printStackTrace();
        }

        try{
            Act act = new Act(locomotiveRepository.getReferenceById(actDto.getLocomotiveId()),actDto.getNumber(),
                    actDto.getDate(),actDto.getCompany(),actDto.getWorkKind());

            actRepository.save(act);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Transactional
    public void edit(ActDto actDto){
        Act act1 = actRepository.getReferenceById(actDto.getId());
        act1.setWorkKind(actDto.getWorkKind());
        act1.setNumber(actDto.getNumber());
        act1.setDate(actDto.getDate());
        act1.setCompany(actDto.getCompany());
        actRepository.save(act1);
    }

}
