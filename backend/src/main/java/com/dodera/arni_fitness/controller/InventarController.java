package com.dodera.arni_fitness.controller;

import com.dodera.arni_fitness.dto.Response;
import com.dodera.arni_fitness.model.Inventar;
import com.dodera.arni_fitness.service.InventarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/inventory")
public class InventarController {
    private final InventarService inventarService;

    public InventarController(InventarService inventarService) {
        this.inventarService = inventarService;
    }

    @GetMapping("/items")
    public List<Inventar> getItems() {
        return inventarService.getItems();
    }

    @PostMapping("/items")
    public Inventar addItem(@RequestBody Inventar inventar) {
        return inventarService.addItem(inventar.getTitle(), inventar.getQuantity());
    }

    @PostMapping("/items/{id}")
    public ResponseEntity<?> updateItem(@PathVariable Inventar inventar) {
        inventarService.updateItem(inventar.getId(), inventar.getTitle(), inventar.getQuantity());
        return new ResponseEntity<>(new Response("Item updated successfully"), HttpStatus.OK);
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        inventarService.deleteItem(id);
        return new ResponseEntity<>(new Response("Item deleted successfully"), HttpStatus.OK);
    }
}
