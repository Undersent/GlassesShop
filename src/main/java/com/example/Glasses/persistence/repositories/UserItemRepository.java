package com.example.Glasses.persistence.repositories;

import com.example.Glasses.persistence.model.UserItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserItemRepository extends JpaRepository<UserItem, Integer> {
    Optional<UserItem> findByUserItemId(int id);
}
