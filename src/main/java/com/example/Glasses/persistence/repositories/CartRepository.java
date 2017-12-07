package com.example.Glasses.persistence.repositories;

import com.example.Glasses.persistence.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {

}
