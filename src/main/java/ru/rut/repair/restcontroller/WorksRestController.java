package ru.rut.repair.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("certificate/{act_id}/modernization")
    public List<Works> get(@PathVariable("act_id") int act_id){
        return worksService.getList();
    }

    @PostMapping("certificate/{act_id}/modernization")
    public void add(@PathVariable("act_id") int act_id,@RequestBody Works works){
        worksService.add(works, act_id);
    }

    @DeleteMapping("certificate/{act_id}/modernization")
    public void delete(@PathVariable("act_id") int act_id, @RequestParam int id){
        worksService.remove(id);
    }

    @PutMapping("certificate/{act_id}/modernization")
    public void edit(@PathVariable("act_id") int act_id, @RequestParam int id, @RequestBody Works works){
        worksService.edit(act_id, id, works);
    }
}
