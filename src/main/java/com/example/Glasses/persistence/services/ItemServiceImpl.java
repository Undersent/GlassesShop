package com.example.Glasses.persistence.services;

import com.example.Glasses.persistence.model.Item;
import com.example.Glasses.persistence.repositories.ItemRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @_(@Autowired))
@NoArgsConstructor
public class ItemServiceImpl implements ItemService {

    private ItemRepository itemRepository;

    @Override
    public Optional<Item> findByItemId(int id) {
        return itemRepository.findByItemId(id);
    }

    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Optional<Item> findByItemName(String name) {
        return itemRepository.findByItemName(name);
    }

    @Override
    public List<Item> findAll() {
        return itemRepository.findAll();
    }
}
