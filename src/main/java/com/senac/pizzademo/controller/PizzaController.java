package com.senac.pizzademo.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senac.pizzademo.model.Pizza;
import com.senac.pizzademo.repository.PizzaRepository;

@RestController
@RequestMapping("/pizza")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping
    public List<Pizza> getAllPizzas() {
        return pizzaRepository.findAll();
    }

    @PostMapping
    public Pizza createPizza(@RequestBody Pizza pizza) {
        return pizzaRepository.save(pizza);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pizza> updatePizza(@PathVariable Long id,
     @RequestBody Pizza updatedPizza){
        System.out.println("Put recebido para pizza id: " + id);
        return pizzaRepository.findById(id).map(pizza -> {
            pizza.setSabor(updatedPizza.getSabor());

            if (updatedPizza.getIngredientes() != null) {
                pizza.getIngredientes().clear();
                updatedPizza.getIngredientes().forEach(i -> i.setPizza(pizza));
                pizza.getIngredientes().addAll(updatedPizza.getIngredientes());
            }
            if (updatedPizza.getCardapio() != null){
                pizza.getCardapio().clear();
                updatedPizza.getCardapio().forEach(i -> i.setPizza(pizza));
                pizza.getCardapio().addAll(updatedPizza.getCardapio());
            }
            Pizza saved = pizzaRepository.save(pizza);
            return ResponseEntity.ok(saved);
        }).orElse(ResponseEntity.notFound().build());    
    }

    


    // Adicionar métodos para atualização e exclusão conforme necessário
}
