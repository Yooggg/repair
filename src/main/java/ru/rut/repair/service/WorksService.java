package ru.rut.repair.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rut.repair.model.Works;
import ru.rut.repair.repository.ActRepository;
import ru.rut.repair.repository.WorksRepository;

import java.util.List;

@Service
public class WorksService {
    private final WorksRepository worksRepository;
    private final ActRepository actRepository;

    @Autowired
    public WorksService(WorksRepository worksRepository, ActRepository actRepository) {
        this.worksRepository = worksRepository;
        this.actRepository = actRepository;
    }

    public List<Works> getList() {
        return worksRepository.findAll();
    }
    public List<Works> getListByActId(int act_id){
        return worksRepository.findAllByActId(act_id);
    }

    @Transactional
    public void add(Works works, Integer act_id) {
        works.setAct(actRepository.getReferenceById(act_id));
        worksRepository.save(works);
    }

    public void remove(int id) {
        worksRepository.deleteById(id);
    }

    @Transactional
    public void edit(int act_id ,int id, Works works) {
        Works works1 = worksRepository.getReferenceById(id);
        works1.copy(works);
        works1.setAct(actRepository.getReferenceById(act_id));
        worksRepository.save(works1);
    }
}
