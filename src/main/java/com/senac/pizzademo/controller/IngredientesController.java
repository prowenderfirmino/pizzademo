package com.senac.pizzademo.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senac.pizzademo.model.Ingredientes;
import com.senac.pizzademo.repository.IngredientesRepository;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/ingredientes")
public class IngredientesController {

    @Autowired
    private IngredientesRepository ingredientesRepository;

    @GetMapping
    public List<Ingredientes> getAllIngredientes() {
        return ingredientesRepository.findAll();
    }

    @PostMapping
    public Ingredientes createPizza(@RequestBody Ingredientes ingredientes) {
        return ingredientesRepository.save(ingredientes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingredientes> updateIngrediente(
        @PathVariable Long id, @RequestBody 
        Ingredientes updatedIngredientes        ){
        return ingredientesRepository.findById(id).map(
            ingrediente -> 
            {
                ingrediente.setIngrediente(
                    updatedIngredientes.getIngrediente());
                ingrediente.setQuantidade(
                    updatedIngredientes.getQuantidade());
                return ResponseEntity.ok(
                    ingredientesRepository.save(ingrediente));                
            }).orElse(ResponseEntity.notFound().build());        
    }

    // Adicionar métodos para atualização e exclusão conforme necessário
}
