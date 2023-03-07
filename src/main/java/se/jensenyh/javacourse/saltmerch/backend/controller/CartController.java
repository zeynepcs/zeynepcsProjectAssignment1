package se.jensenyh.javacourse.saltmerch.backend.controller;


import jakarta.annotation.Nullable;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;
import se.jensenyh.javacourse.saltmerch.backend.model.CartItem;
import se.jensenyh.javacourse.saltmerch.backend.service.CartService;

import javax.swing.*;

@RestController
@CrossOrigin(origins = "http://localhost:3010")


public class CartController
{


    @Autowired
    CartService cartservice;
    @GetMapping("carts/{id}")
    public ResponseEntity<CartItem> getCartId(){
        cartservice.getCartService();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("carts/{id}")

    public Object addOrRemoveItemFromCart (@PathVariable("id") int id,@RequestParam String action,@RequestBody CartItem cartItem){

        if (action.equals("add")){
        new ResponseEntity<>(cartservice.addCartService(cartItem), HttpStatus.OK);

        }

        else  if (action.equals("remove")){
            new ResponseEntity<>(cartservice.removeCartService(cartItem), HttpStatus.OK);
        }
        return null;
    }



    @DeleteMapping("carts/{id}")
    public void  clearCard(@PathVariable("id") int id, @RequestParam(defaultValue ="") String buyout){
        if (buyout.equals("true")){
            cartservice.emptyCartCheckOut(false);
        }

        else if ( buyout.equals("")) {
            cartservice.emptyCart(true);

        }

    }

}
