package ru.rut.repair.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    public List<Inventory> getListByActId(int act_id){
        return inventoryRepository.findAllByActId(act_id);
    }

    public void remove(int id){
        inventoryRepository.deleteById(id);
    }

    @Transactional
    public void add(Inventory inventory, Integer act_id){
        inventory.setAct(actRepository.getReferenceById(act_id));
        inventoryRepository.save(inventory);
    }

    @Transactional
    public void edit(int act_id,int id, Inventory inventory){
        Inventory inventory1 = inventoryRepository.getReferenceById(id);
        inventory1.copy(inventory);
        inventory1.setAct(actRepository.getReferenceById(act_id));
        inventoryRepository.save(inventory1);
    }
}
