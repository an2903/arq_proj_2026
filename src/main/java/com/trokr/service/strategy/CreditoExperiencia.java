package com.trokr.service.strategy;

import org.springframework.stereotype.Component;

import com.trokr.model.CategoriaItem;
import com.trokr.model.Item;

@Component 
public class CreditoExperiencia implements EstrategiaCredito{
    @Override 
    public CategoriaItem categoria() {
        return CategoriaItem.EXPERIENCIA;
    }

    @Override 
    public int calcular(Item item) {
        return 30;
    }
}
