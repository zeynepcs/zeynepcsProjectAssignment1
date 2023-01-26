package se.jensenyh.javacourse.saltmerch.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;
import se.jensenyh.javacourse.saltmerch.backend.model.CartItem;
import se.jensenyh.javacourse.saltmerch.backend.model.Product;
import se.jensenyh.javacourse.saltmerch.backend.service.ProductService;


import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3010")

public class ProductController{

@Autowired
ProductService productService;

    @GetMapping("/products")
    public List<Product> getAllProducts(){


        return productService.getAllProduct();



    }


    @GetMapping("/products/hats")
    public List<Product> getProductHats(){

        return productService.getProductItems("hats");

    }
    @GetMapping("/products/tshirts")
    public List<Product> getProductTshirts(){

        return productService.getProductItems("tshirts");

    }

    @GetMapping("/products/bags")
    public List<Product> getProductBags(){

        return productService.getProductItems("bags");

    }

    @GetMapping("/products/jackets")
    public List<Product> getProductJackets(){

        return productService.getProductItems("jackets");

    }

    @GetMapping("/products/{id}")
    public Product getFromProductId(@PathVariable("id") int id){

        return productService.getProductId(id);

    }
}
