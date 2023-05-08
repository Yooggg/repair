package ru.rut.repair.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.rut.repair.dto.WorksDto;
import ru.rut.repair.model.Works;
import ru.rut.repair.service.ActService;
import ru.rut.repair.service.WorksService;

import java.util.List;

@RestController
public class WorksRestController {
    private final WorksService worksService;
    private final ActService actService;

    @Autowired
    public WorksRestController(WorksService worksService, ActService actService) {
        this.worksService = worksService;
        this.actService = actService;
    }

    @GetMapping("certificate/modernization")
    public List<Works> get(){
        return worksService.getList();
    }

    @PostMapping("certificate/modernization")
    public void add(@RequestBody WorksDto worksDto){
        worksService.add(worksDto);
    }

    @DeleteMapping("certificate/modernization")
    public void delete(@RequestParam int id){
        worksService.remove(id);
    }

    @PutMapping("certificate/modernization")
    public void edit(@RequestBody WorksDto worksDto){
        worksService.edit(worksDto);
    }
}
