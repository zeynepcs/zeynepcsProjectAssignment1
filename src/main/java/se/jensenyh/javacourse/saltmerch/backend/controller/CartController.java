package se.jensenyh.javacourse.saltmerch.backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;
import se.jensenyh.javacourse.saltmerch.backend.model.CartItem;
import se.jensenyh.javacourse.saltmerch.backend.service.CartService;

@RestController
@CrossOrigin(origins = "http://localhost:3010")
@RequestScope
public class CartController
{


    @Autowired
    CartService cartservice;

    @PatchMapping("/carts/{id}?action=add")

    public ResponseEntity<Integer> addItem(@PathVariable("id")  CartItem item){


        return ResponseEntity.ok(cartservice.addCartService(item));

    }

}
