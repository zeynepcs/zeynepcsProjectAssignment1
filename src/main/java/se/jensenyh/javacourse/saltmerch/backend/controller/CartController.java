package se.jensenyh.javacourse.saltmerch.backend.controller;


import jakarta.annotation.Nullable;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;
import se.jensenyh.javacourse.saltmerch.backend.model.CartItem;
import se.jensenyh.javacourse.saltmerch.backend.service.CartService;

@RestController
@CrossOrigin(origins = "http://localhost:3010")


public class CartController
{


    @Autowired
    CartService cartservice;
    @GetMapping("carts/{id}")
    public ResponseEntity<CartItem> getCartId(){
        cartservice.getCart();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("carts/{id}")

    public ResponseEntity<CartItem> addOrRemoveItemFromCart (@PathVariable("id") int id, @RequestParam String action, @RequestBody CartItem cartItem){
        cartservice.addCartService(cartItem);

        return new  ResponseEntity<CartItem>(HttpStatus.OK);

    }

    @DeleteMapping("carts/{id}")
    public ResponseEntity<CartItem> clearCard(@PathVariable("id") int id, @RequestParam @Nullable boolean buyout){
        if (id == 1){
            cartservice.emptyCart(buyout);
            return new ResponseEntity<>(HttpStatus.OK);
        }
       else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
