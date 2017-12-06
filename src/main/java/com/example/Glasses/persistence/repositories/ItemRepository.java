package com.example.Glasses.persistence.repositories;

import com.example.Glasses.persistence.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    Optional<Item> findByItemId(int id);
    Optional<Item> findByItemName(String name);
    List<Item> findAll();
}
