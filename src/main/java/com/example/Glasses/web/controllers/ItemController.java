package com.example.Glasses.web.controllers;

import com.example.Glasses.persistence.model.Item;
import com.example.Glasses.persistence.services.ItemService;
import com.example.Glasses.web.dto.ItemDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor(onConstructor = @_(@Autowired))
@RequestMapping()
public class ItemController {

    ItemService itemService;

    @PostMapping("/admin/item")
    public ResponseEntity<?> postItem(@RequestBody ItemDto itemDto){

        this.validateItem(itemDto.getItemName());

        Item item = Item.builder()
                .itemName(itemDto.getItemName())
                .available(itemDto.isAvailable())
                .price(itemDto.getPrice())
                .correction(itemDto.getCorrection())
                .url(itemDto.getUrl())
                .build();

        itemService.save(item);

        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @GetMapping("/item/all")
    public List<Item> getItems(){
        return itemService.findAll();
    }

    @GetMapping("/item/{id}")
    public Item getItems(@PathVariable("id") int id){
        return itemService.findByItemId(id)
                .orElseThrow(() -> new RuntimeException("Item with that id not found"));
    }


    private void validateItem(String itemName) {
        this.itemService
                .findByItemName(itemName)
                .filter(i -> i.getItemName().equalsIgnoreCase(itemName))
                .ifPresent(i -> {
                    throw new RuntimeException("item with that name exists");
                });
    }
}
