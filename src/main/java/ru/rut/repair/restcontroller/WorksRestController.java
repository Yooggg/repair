package ru.rut.repair.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.rut.repair.dto.WorksDto;
import ru.rut.repair.model.Works;
import ru.rut.repair.service.WorksService;

import java.util.List;

@RestController
@RequestMapping("certificate/modernization")
public class WorksRestController {
    private final WorksService worksService;

    @Autowired
    public WorksRestController(WorksService worksService) {
        this.worksService = worksService;
    }

    @GetMapping()
    public List<Works> get(){
        return worksService.getList();
    }

    @PostMapping()
    public void add(@RequestBody WorksDto worksDto){
        worksService.add(worksDto);
    }

    @DeleteMapping()
    public void delete(@RequestParam int id){
        worksService.remove(id);
    }

    @PutMapping()
    public void edit(@RequestBody WorksDto worksDto){
        worksService.edit(worksDto);
    }
}
