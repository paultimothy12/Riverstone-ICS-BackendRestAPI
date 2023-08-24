package com.timothy.controller;

import com.timothy.entity.Product;
import com.timothy.exceptions.ProductEntityException;
import com.timothy.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestControllerDemo {

    ProductRepo productRepo;

    @Autowired
    public RestControllerDemo(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @GetMapping("/api/get/AllProduct")
    public List<Product> getAllProduct() {
        return productRepo.findAll();
    }

    @GetMapping("/api/get/SpecificProduct/{id}")
    public Product getSpecificProduct(@PathVariable Integer id) {
        Product product = null;
        if (!productRepo.existsById(id)) {
            throw new ProductEntityException("Product doesn't exists! Try searching for Existing product ID!");
        }
        if (productRepo.findById(id).isPresent()) {
            product = productRepo.findById(id).get();
        }
        return product;
    }

    @PostMapping("/api/post/NewProduct")
    public void postNewProduct(@RequestBody Product product) {
        if (productRepo.existsById(product.getProductId())) {
            throw new ProductEntityException("Product already exists! Try new Product ID!");
        }
        try {
            productRepo.save(product);
        } catch (Exception exception) {
            throw new ProductEntityException("Please check the details once again, cannot complete transaction!!");
        }
    }

    @PutMapping("/api/put/UpdateProduct")
    public void putUpdateProduct(@RequestBody Product product) {
        if (!productRepo.existsById(product.getProductId())) {
            throw new ProductEntityException("Product doesn't exist! Try updating existing Product ID!");
        }
        try {
            Product product1 = productRepo.findById(product.getProductId()).get();
            product1.setProductId(product.getProductId());
            product1.setProductName(product.getProductName());
            product1.setExpDate(product.getExpDate());
            product1.setPrice(product.getPrice());
            product1.setManDate(product.getManDate());
            product1.setQuantity(product.getQuantity());
            productRepo.save(product1);
        } catch (Exception exception) {
            throw new ProductEntityException("Please check the details once again, cannot complete transaction!!");
        }
    }

    @DeleteMapping("/api/delete/DeleteProduct/{id}")
    public void deleteProduct(@PathVariable Integer id) {
        if (!productRepo.existsById(id)) {
            throw new ProductEntityException("Product doesn't exist! Try deleting existing Product!");
        }
        productRepo.deleteById(id);
    }

    @GetMapping("/api/get/CheckExistence/{id}")
    public Boolean checkIfProductExist(@PathVariable Integer id) {
        return productRepo.existsById(id);
    }
}
