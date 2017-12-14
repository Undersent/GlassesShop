package com.example.Glasses.persistence.services;

import com.example.Glasses.persistence.model.Item;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ItemService {
    Optional<Item> findByItemId(int id);
    Optional<Item> findByItemName(String name);
    Collection<Item> findByItemPrice(double loPrice, double hiPrice);
    Collection<Item> findByItemCorrection(double loCorrection, double hiCorrection);
    List<Item> findAll();
    Item save(Item item);

}
