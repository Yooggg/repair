package ru.rut.repair.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.rut.repair.model.Inventory;
import ru.rut.repair.service.InventoryService;

import java.util.List;

@RestController
public class InventoryRestController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryRestController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("certificate/{act_id}/detail")
    public List<Inventory> get(@PathVariable("act_id") int act_id){
        return inventoryService.getList();
    }

    @PostMapping("certificate/{act_id}/detail")
    public void add(@PathVariable("act_id") int act_id, @RequestBody Inventory inventory){
        inventoryService.add(inventory, act_id);
    }

    @DeleteMapping("certificate/{act_id}/detail")
    public void delete(@PathVariable("act_id") int act_id, @RequestParam int id){
        inventoryService.remove(id);
    }

    @PutMapping("certificate/{act_id}/detail")
    public void edit(@PathVariable("act_id") int act_id, @RequestParam int id ,@RequestBody Inventory inventory){
        inventoryService.edit(act_id, id, inventory);
    }
}
