package com.dodera.arni_fitness.service;

import com.dodera.arni_fitness.model.Inventar;
import com.dodera.arni_fitness.repository.InventarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventarService {
    private final InventarRepository inventarRepository;

    public InventarService(InventarRepository inventarRepository) {
        this.inventarRepository = inventarRepository;
    }

    public void deleteItem(Long id) {
        var item = this.inventarRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid item id"));

        inventarRepository.deleteById(id);
    }

    public void updateItem(Long id, String title, Integer quantity) {
        var item = this.inventarRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid item id"));
        item.setTitle(title);
        item.setQuantity(quantity);
        inventarRepository.save(item);
    }

    public Inventar addItem(String title, Integer quantity) {
        var item = new Inventar();
        item.setTitle(title);
        item.setQuantity(quantity);
        return inventarRepository.save(item);
    }

    public List<Inventar> getItems() {
        return inventarRepository.findAll();
    }
}
