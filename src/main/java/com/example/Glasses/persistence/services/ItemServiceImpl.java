package com.example.Glasses.persistence.services;

import com.example.Glasses.persistence.model.Item;
import com.example.Glasses.persistence.repositories.ItemRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ItemServiceImpl implements ItemService {

    @Autowired
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
    public Collection<Item> findByItemPrice(double loPrice, double hiPrice) {
        return itemRepository.findAll()
                .stream()
                .filter(i -> (i.getPrice() >= loPrice) && (i.getPrice() <= hiPrice))
                .sorted(Comparator.comparing(Item::getPrice))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Item> findByItemCorrection(double loCorrection, double hiCorrection) {
        return itemRepository.findAll()
                .stream()
                .filter(i -> (i.getCorrection() >= loCorrection) && (i.getCorrection() <= hiCorrection))
                .sorted(Comparator.comparing(Item::getCorrection))
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> findAll() {
        return itemRepository.findAll();
    }
}
