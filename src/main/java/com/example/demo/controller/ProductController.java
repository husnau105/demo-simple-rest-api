package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> createProduct(@RequestBody Product p) {
        productService.create(p);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
