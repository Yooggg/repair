package ru.rut.repair.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.rut.repair.dto.InventoryDto;
import ru.rut.repair.model.Inventory;
import ru.rut.repair.service.InventoryService;

import java.util.List;

@RestController
@RequestMapping("certificate/detail")
public class InventoryRestController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryRestController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping()
    public List<Inventory> get(){
        return inventoryService.getList();
    }

    @PostMapping()
    public void add(@RequestBody InventoryDto inventoryDto){
        inventoryService.add(inventoryDto);
    }

    @DeleteMapping()
    public void delete(@RequestParam int id){
        inventoryService.remove(id);
    }

    @PutMapping()
    public void edit(@RequestBody InventoryDto inventoryDto){
        inventoryService.edit(inventoryDto);
    }
}
