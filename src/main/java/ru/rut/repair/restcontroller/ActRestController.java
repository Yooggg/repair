package ru.rut.repair.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.rut.repair.model.Act;
import ru.rut.repair.service.ActService;

import java.util.List;

@RestController
@RequestMapping("/certificate")
public class ActRestController {
    private final ActService actService;

    @Autowired
    public ActRestController(ActService actService) {
        this.actService = actService;
    }

    @GetMapping()
    public List<Act> get(){
        return actService.getList();
    }

    @PostMapping()
    public void add(@RequestBody Act act, @RequestParam(required = false) int loc_id){
        actService.add(act);
        actService.add(act,loc_id);
    }
    @DeleteMapping()
    public void delete(@RequestParam int id){
        actService.remove(id);
    }

    @PutMapping()
    public void edit(@RequestParam int id, @RequestBody Act act){
        actService.edit(id,act);
    }
}
