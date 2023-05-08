package ru.rut.repair.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rut.repair.dto.InventoryDto;
import ru.rut.repair.model.Inventory;
import ru.rut.repair.model.Works;
import ru.rut.repair.repository.ActRepository;
import ru.rut.repair.repository.InventoryRepository;

import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ActRepository actRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository, ActRepository actRepository) {
        this.inventoryRepository = inventoryRepository;
        this.actRepository = actRepository;
    }

    public List<Inventory> getList(){
        return inventoryRepository.findAll();
    }

    public List<Inventory> getListByActId(int actId){
        return inventoryRepository.findAllByActId(actId);
    }

    public void remove(int id){
        inventoryRepository.deleteById(id);
    }

    @Transactional
    public void add(Inventory inventory){
        inventoryRepository.save(inventory);
    }

    @Transactional
    public void edit(InventoryDto inventoryDto){
        Inventory inventory1 = inventoryRepository.getReferenceById(inventoryDto.getId());
        inventory1.setInventoryName(inventoryDto.getInventoryName());
        inventory1.setQuantityNorm(inventoryDto.getQuantityNorm());
        inventory1.setQuantityFact(inventoryDto.getQuantityFact());
        inventory1.setNumber(inventoryDto.getNumber());
        inventory1.setMeasureUnit(inventoryDto.getMeasureUnit());
        inventory1.setAct(actRepository.getReferenceById(inventoryDto.getActId()));
        inventoryRepository.save(inventory1);
    }
}
