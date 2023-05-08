package ru.rut.repair.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.rut.repair.dto.InventoryDto;
import ru.rut.repair.model.Inventory;
import ru.rut.repair.service.ActService;
import ru.rut.repair.service.InventoryService;

import java.util.List;

@RestController
public class InventoryRestController {

    private final ActService actService;
    private final InventoryService inventoryService;

    @Autowired
    public InventoryRestController(ActService actService, InventoryService inventoryService) {
        this.actService = actService;
        this.inventoryService = inventoryService;
    }

    @GetMapping("certificate/detail")
    public List<Inventory> get(@RequestParam int actId){
        return inventoryService.getList();
    }

    @PostMapping("certificate/detail")
    public void add(@RequestBody InventoryDto inventoryDto){
        Inventory inventory = new Inventory();
        inventory.setInventoryName(inventoryDto.getInventoryName());
        inventory.setNumber(inventoryDto.getNumber());
        inventory.setMeasureUnit(inventoryDto.getMeasureUnit());
        inventory.setQuantityNorm(inventory.getQuantityNorm());
        inventory.setQuantityFact(inventory.getQuantityFact());
        inventory.setAct(actService.getById(inventoryDto.getActId()));
        inventoryService.add(inventory);
    }

    @DeleteMapping("certificate/detail")
    public void delete(@RequestParam int id){
        inventoryService.remove(id);
    }

    @PutMapping("certificate/detail")
    public void edit(@RequestBody InventoryDto inventoryDto){
        inventoryService.edit(inventoryDto);
    }
}
