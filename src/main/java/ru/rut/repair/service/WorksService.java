package ru.rut.repair.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rut.repair.dto.WorksDto;
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
    public List<Works> getListByActId(int actId){
        return worksRepository.findAllByActId(actId);
    }

    @Transactional
    public void add(WorksDto worksDto) {
        Works works = new Works();
        works.setName(worksDto.getName());
        works.setQuantity(worksDto.getQuantity());
        works.setAct(actRepository.getReferenceById(worksDto.getActId()));
        worksRepository.save(works);
    }

    public void remove(int id) {
        worksRepository.deleteById(id);
    }

    @Transactional
    public void edit(WorksDto worksDto) {
        Works works1 = worksRepository.getReferenceById(worksDto.getId());
        works1.setName(worksDto.getName());
        works1.setQuantity(worksDto.getQuantity());
        works1.setAct(actRepository.getReferenceById(worksDto.getActId()));
        worksRepository.save(works1);
    }
}
